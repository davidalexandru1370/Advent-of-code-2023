package day17

import helpers.IDay
import java.io.File
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.min

enum class Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST
}

data class Cell(
    var row: Int,
    var column: Int,
    val cost: Int,
    var inlineCounter: Int,
    var facePointingInDirection: Direction
)

class Day17 : IDay {
    init {
        //solve1()
        run_tests()
        solve2()
    }

    override fun solve1() {
        val grid: Array<Array<Int>> = getInput()

        val distance: Int = dijkstra(grid)

        println(distance)
    }

    override fun solve2() {
        val grid: Array<Array<Int>> = getInput()

        val distance: Int = ultraCrucibles(grid)

        println(distance)
    }

    private fun ultraCrucibles(grid: Array<Array<Int>>): Int {
        var distance: Int = Int.MAX_VALUE
        val pq: PriorityQueue<Cell> = PriorityQueue<Cell>(compareBy { it.cost })
        val seen: HashMap<Triple<Pair<Int, Int>, Direction, Int>, Int> = HashMap()
        val nextNeighboursFromDirection: HashMap<Direction, Array<Pair<Int, Int>>> = hashMapOf(
            Direction.SOUTH to arrayOf(Pair(1, 0), Pair(0, -1), Pair(0, 1)),
            Direction.NORTH to arrayOf(Pair(-1, 0), Pair(0, -1), Pair(0, 1)),
            Direction.EAST to arrayOf(Pair(0, 1), Pair(-1, 0), Pair(1, 0)),
            Direction.WEST to arrayOf(Pair(0, -1), Pair(-1, 0), Pair(1, 0))
        )

        val nextDirectionFromNeighbour: HashMap<Pair<Int, Int>, Direction> = hashMapOf(
            Pair(-1, 0) to Direction.NORTH,
            Pair(1, 0) to Direction.SOUTH,
            Pair(0, 1) to Direction.EAST,
            Pair(0, -1) to Direction.WEST
        )

        pq.add(Cell(0, 0, 0, 0, Direction.EAST))


        while (pq.isNotEmpty()) {
            val cell: Cell = pq.poll()

            if (cell.row == grid.size - 1 && cell.column == grid[0].size - 1 && cell.inlineCounter >= 4) {
                distance = min(distance, cell.cost)
                break
            }

            val key = Triple(
                Pair(cell.row, cell.column),
                cell.facePointingInDirection,
                cell.inlineCounter
            )

            if (seen.containsKey(key)) {
                continue
            }

            seen[key] = 1

            for (neighbour in nextNeighboursFromDirection[cell.facePointingInDirection]!!) {
                val newRow: Int = cell.row + neighbour.first
                val newColumn: Int = cell.column + neighbour.second

                if (newRow < 0 || newRow >= grid.size || newColumn < 0 || newColumn >= grid[0].size ||
                    (cell.inlineCounter == 10 && (cell.facePointingInDirection == Direction.NORTH || cell.facePointingInDirection == Direction.SOUTH) && neighbour.first != 0) ||
                    (cell.inlineCounter == 10 && (cell.facePointingInDirection == Direction.EAST || cell.facePointingInDirection == Direction.WEST) && neighbour.second != 0)
                ) {
                    continue
                }

                if (cell.inlineCounter < 4 && (cell.facePointingInDirection == Direction.NORTH || cell.facePointingInDirection == Direction.SOUTH) && neighbour.second != 0 ||
                    cell.inlineCounter < 4 && (cell.facePointingInDirection == Direction.WEST || cell.facePointingInDirection == Direction.EAST) && neighbour.first != 0
                ) {
                    continue
                }

                val inlineCounter = if (cell.facePointingInDirection != nextDirectionFromNeighbour[neighbour]!!) {
                    1
                } else {
                    cell.inlineCounter + 1
                }

                val newCell: Cell = Cell(
                    newRow,
                    newColumn,
                    cell.cost + grid[newRow][newColumn],
                    inlineCounter,
                    nextDirectionFromNeighbour[neighbour]!!
                )

                pq.add(newCell)

            }
        }

        return distance
    }

    private fun dijkstra(grid: Array<Array<Int>>): Int {
        var distance: Int = Int.MAX_VALUE
        val pq: PriorityQueue<Cell> = PriorityQueue<Cell>(compareBy { it.cost })
        val distances: HashMap<Pair<Int, Int>, Int> = HashMap()
        val seen: HashMap<Triple<Pair<Int, Int>, Direction, Int>, Int> = HashMap()
        val nextNeighboursFromDirection: HashMap<Direction, Array<Pair<Int, Int>>> = hashMapOf(
            Direction.SOUTH to arrayOf(Pair(1, 0), Pair(0, -1), Pair(0, 1)),
            Direction.NORTH to arrayOf(Pair(-1, 0), Pair(0, -1), Pair(0, 1)),
            Direction.EAST to arrayOf(Pair(0, 1), Pair(-1, 0), Pair(1, 0)),
            Direction.WEST to arrayOf(Pair(0, -1), Pair(-1, 0), Pair(1, 0))
        )

        val nextDirectionFromNeighbour: HashMap<Pair<Int, Int>, Direction> = hashMapOf(
            Pair(-1, 0) to Direction.NORTH,
            Pair(1, 0) to Direction.SOUTH,
            Pair(0, 1) to Direction.EAST,
            Pair(0, -1) to Direction.WEST
        )

        pq.add(Cell(0, 0, 0, 0, Direction.EAST))


        while (pq.isNotEmpty()) {
            val cell: Cell = pq.poll()

            if (cell.row == grid.size - 1 && cell.column == grid[0].size - 1) {
                distance = min(distance, cell.cost)
                break
            }

            val key = Triple(
                Pair(cell.row, cell.column),
                cell.facePointingInDirection,
                cell.inlineCounter
            )

            if (seen.containsKey(key)) {
                continue
            }

            seen[key] = 1

            for (neighbour in nextNeighboursFromDirection[cell.facePointingInDirection]!!) {
                val newRow: Int = cell.row + neighbour.first
                val newColumn: Int = cell.column + neighbour.second

                if (newRow < 0 || newRow >= grid.size || newColumn < 0 || newColumn >= grid[0].size ||
                    (cell.inlineCounter == 3 && (cell.facePointingInDirection == Direction.NORTH || cell.facePointingInDirection == Direction.SOUTH) && neighbour.first != 0) ||
                    (cell.inlineCounter == 3 && (cell.facePointingInDirection == Direction.EAST || cell.facePointingInDirection == Direction.WEST) && neighbour.second != 0)
                ) {

                    continue
                }


                val inlineCounter = if (cell.facePointingInDirection != nextDirectionFromNeighbour[neighbour]!!) {
                    1
                } else {
                    cell.inlineCounter + 1
                }

                val newCell: Cell = Cell(
                    newRow,
                    newColumn,
                    cell.cost + grid[newRow][newColumn],
                    inlineCounter,
                    nextDirectionFromNeighbour[neighbour]!!
                )

                pq.add(newCell)

            }
        }

        grid.forEachIndexed { indexRow, row ->
            row.forEachIndexed { indexCol, cell ->
                print(distances[Pair(indexRow, indexCol)])
                print(" ")
            }
            println()
        }

        return distance
    }

    private fun getInput(): Array<Array<Int>> {
        val lines: List<String> = File("src/main/kotlin/day17/1.txt").readLines()
        val grid: Array<Array<Int>> = Array<Array<Int>>(lines.size) {
            Array<Int>(lines[0].length) { 0 }
        }

        lines.forEachIndexed { row, line ->
            line.forEachIndexed { col, c ->
                grid[row][col] = c.toString().toInt()
            }
        }

        return grid
    }

    private fun run_tests() {
        tests_part2()
    }

    private fun tests_part2() {
        val input1: Array<Array<Int>> = arrayOf(
            arrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
            arrayOf(9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 1),
            arrayOf(9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 1),
            arrayOf(9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 1),
            arrayOf(9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 1)
        )

        val input2: Array<Array<Int>> = arrayOf(
            arrayOf(2, 4, 1, 3, 4, 3, 2, 3, 1, 1, 3, 2, 3),
            arrayOf(3, 2, 1, 5, 4, 5, 3, 5, 3, 5, 6, 2, 3),
            arrayOf(3, 2, 5, 5, 2, 4, 5, 6, 5, 4, 2, 5, 4),
            arrayOf(3, 4, 4, 6, 5, 8, 5, 8, 4, 5, 4, 5, 2),
            arrayOf(4, 5, 4, 6, 6, 5, 7, 8, 6, 7, 5, 3, 6),
            arrayOf(1, 4, 3, 8, 5, 9, 8, 7, 9, 8, 4, 5, 4),
            arrayOf(4, 4, 5, 7, 8, 7, 6, 9, 8, 7, 7, 6, 6),
            arrayOf(3, 6, 3, 7, 8, 7, 7, 9, 7, 9, 6, 5, 3),
            arrayOf(4, 6, 5, 4, 9, 6, 7, 9, 8, 6, 8, 8, 7),
            arrayOf(4, 5, 6, 4, 6, 7, 9, 9, 8, 6, 4, 5, 3),
            arrayOf(4, 3, 2, 2, 6, 7, 4, 6, 5, 5, 5, 3, 3),
            arrayOf(2, 5, 4, 6, 5, 4, 8, 8, 8, 7, 7, 3, 5),
            arrayOf(4, 3, 2, 2, 6, 7, 4, 6, 5, 5, 5, 3, 3)
        )

        val distance1: Int = ultraCrucibles(input1)
        val distance2: Int = ultraCrucibles(input2)

        println("distance1 = $distance1")
        println("distance2 = $distance2")

        assert(distance1 == 71)
        assert(distance2 == 94)
    }
}

