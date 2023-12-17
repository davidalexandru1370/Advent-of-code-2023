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
    val value: Int,
    var inlineCounter: Int,
    var facePointingInDirection: Direction
)

class Day17 : IDay {
    init {
        solve1()
    }

    override fun solve1() {
        val grid: Array<Array<Int>> = getInput()

        val distance: Int = dijkstra(grid)

        println(distance)
    }

    override fun solve2() {
        TODO("Not yet implemented")
    }

    private fun dijkstra(grid: Array<Array<Int>>): Int {
        var distance: Int = Int.MAX_VALUE
        val pq: PriorityQueue<Cell> = PriorityQueue<Cell>(compareBy { it.value })
        val visited: HashMap<Pair<Int, Int>, Int> = HashMap()
        val directionRow = intArrayOf(-1, 0, 0, 1)
        val directionCol = intArrayOf(0, -1, 1, 0)
        val nextNeighboursFromDirection: HashMap<Direction, Array<Pair<Int, Int>>> = hashMapOf(
            Direction.SOUTH to arrayOf(Pair(1, 0), Pair(0, -1), Pair(0, 1)),
            Direction.NORTH to arrayOf(Pair(-1, 0), Pair(0, -1), Pair(0, 1)),
            Direction.EAST to arrayOf(Pair(0, 1), Pair(-1, 0), Pair(1, 0)),
            Direction.WEST to arrayOf(Pair(0, -1), Pair(0, 1), Pair(-1, 0))
        )

        val nextDirectionFromNeighbour: HashMap<Pair<Int, Int>, Direction> = hashMapOf(
            Pair(1, 0) to Direction.SOUTH,
            Pair(-1, 0) to Direction.NORTH,
            Pair(0, 1) to Direction.EAST,
            Pair(0, -1) to Direction.WEST
        )

        pq.add(Cell(0, 0, 0, 1, Direction.EAST))
        visited[Pair(0, 0)] = 0

        while (pq.isNotEmpty()) {
            val cell: Cell = pq.poll()

            if (cell.row == grid.size - 1 && cell.column == grid[0].size - 1) {
                distance = min(distance, cell.value)
                break
            }

            for (neighbour in nextNeighboursFromDirection[cell.facePointingInDirection]!!) {
                val newRow: Int = cell.row + neighbour.first
                val newColumn: Int = cell.column + neighbour.second

                if (newRow < 0 || newRow >= grid.size || newColumn < 0 || newColumn >= grid[0].size ||
                    (cell.inlineCounter >= 3 && (cell.facePointingInDirection == Direction.NORTH || cell.facePointingInDirection == Direction.SOUTH) && neighbour.first != 0) ||
                    (cell.inlineCounter >= 3 && (cell.facePointingInDirection == Direction.EAST || cell.facePointingInDirection == Direction.WEST) && neighbour.second != 0)
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
                    cell.value + grid[newRow][newColumn],
                    inlineCounter,
                    nextDirectionFromNeighbour[neighbour]!!
                )

                if (newCell.value < visited.getOrDefault(Pair(newCell.row, newCell.column), Int.MAX_VALUE)) {
                    pq.add(newCell)
                    visited[Pair(newCell.row, newCell.column)] = newCell.value
                }
            }
        }

        grid.forEachIndexed { indexRow, row ->
            row.forEachIndexed { indexCol, cell ->
                print(visited[Pair(indexRow, indexCol)])
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
}