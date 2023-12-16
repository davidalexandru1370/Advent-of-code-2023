package day16

import helpers.IDay
import java.io.File
import java.util.LinkedList
import java.util.Queue
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

enum class Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST
}

data class Cell(var row: Int, var column: Int, var direction: Direction) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cell

        if (row != other.row) return false
        if (column != other.column) return false
        if (direction != other.direction) return false

        return true
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + column
        result = 31 * result + direction.hashCode()
        return result
    }
}

class Day16 : IDay {
    init {
        //solve1()
        solve2()
    }

    private fun getInput(): Array<Array<String>> {
        val lines: List<String> = File("src/main/kotlin/day16/1.txt").readLines()
        val grid: Array<Array<String>> = Array<Array<String>>(lines.size) {
            Array<String>(lines[0].length) { "" }
        }

        lines.forEachIndexed { row, line ->
            line.forEachIndexed { col, c ->
                grid[row][col] = c.toString()
            }
        }

        return grid
    }

    private fun bfs(startingCell: Cell = Cell(0, 0, Direction.EAST)): Int {
        val grid: Array<Array<String>> = getInput()

        val visited: HashMap<Cell, Int> = HashMap()
        val next: Queue<Cell> = LinkedList()
        val n: Int = grid.size
        val m: Int = grid[0].size
        next.add(startingCell)
        visited[startingCell] = 1

        val nextNeighbour = hashMapOf(
            Direction.EAST to Pair(0, 1),
            Direction.NORTH to Pair(-1, 0),
            Direction.WEST to Pair(0, -1),
            Direction.SOUTH to Pair(1, 0)
        )

        while (next.isNotEmpty()) {
            val front: Cell = next.poll()

            val row = front.row
            val column = front.column

            if (!isInsideBounds(row, column, n, m)) {
                continue
            }

            when (grid[row][column]) {
                "-" -> {
                    if (front.direction == Direction.EAST || front.direction == Direction.WEST) {
                        val nextCell = Cell(
                            row + nextNeighbour[front.direction]!!.first,
                            column + nextNeighbour[front.direction]!!.second,
                            front.direction
                        )
                        if (nextCell !in visited) {
                            next.add(nextCell)
                            visited[nextCell] = 1
                        }
                    } else {
                        val nextCell = Cell(row, column, front.direction)
                        visited[nextCell] = 1
                        val right = Cell(
                            row,
                            column + nextNeighbour[Direction.EAST]!!.second,
                            Direction.EAST
                        )
                        val left = Cell(
                            row,
                            column + nextNeighbour[Direction.WEST]!!.second,
                            Direction.WEST
                        )

                        if (isInsideBounds(right.row, right.column, n, m) && right !in visited) {
                            next.add(right)
                            visited[right] = 1
                        }
                        if (isInsideBounds(left.row, left.column, n, m) && left !in visited) {
                            next.add(left)
                            visited[left] = 1
                        }
                    }
                }

                "." -> {
                    val nextCell = Cell(
                        row + nextNeighbour[front.direction]!!.first,
                        column + nextNeighbour[front.direction]!!.second,
                        front.direction
                    )
                    if (nextCell !in visited) {
                        next.add(nextCell)
                        visited[nextCell] = 1
                    }
                }

                "/" -> {
                    val nextCell = Cell(row, column, front.direction)
                    visited[nextCell] = 1
                    when (front.direction) {
                        Direction.SOUTH -> {
                            nextCell.column -= 1
                            nextCell.direction = Direction.WEST

                        }

                        Direction.NORTH -> {
                            nextCell.column += 1
                            nextCell.direction = Direction.EAST
                        }

                        Direction.EAST -> {
                            nextCell.row -= 1
                            nextCell.direction = Direction.NORTH

                        }

                        Direction.WEST -> {
                            nextCell.row += 1
                            nextCell.direction = Direction.SOUTH
                        }
                    }

                    if (nextCell !in visited) {
                        next.add(nextCell)
                        visited[nextCell] = 1
                    }
                }

                "\\" -> {
                    val nextCell = Cell(row, column, front.direction)
                    visited[nextCell] = 1
                    when (front.direction) {
                        Direction.SOUTH -> {
                            nextCell.column += 1
                            nextCell.direction = Direction.EAST
                        }

                        Direction.NORTH -> {
                            nextCell.column -= 1
                            nextCell.direction = Direction.WEST
                        }

                        Direction.EAST -> {
                            nextCell.row += 1
                            nextCell.direction = Direction.SOUTH
                        }

                        Direction.WEST -> {
                            nextCell.row -= 1
                            nextCell.direction = Direction.NORTH
                        }
                    }

                    if (nextCell !in visited) {
                        next.add(nextCell)
                        visited[nextCell] = 1
                    }


                }

                "|" -> {
                    if (front.direction == Direction.SOUTH || front.direction == Direction.NORTH) {
                        val nextCell = Cell(
                            row + nextNeighbour[front.direction]!!.first,
                            column + nextNeighbour[front.direction]!!.second,
                            front.direction
                        )
                        if (nextCell !in visited) {
                            next.add(nextCell)
                            visited[nextCell] = 1
                        }
                    } else {
                        val nextCell = Cell(row, column, front.direction)

                        visited[nextCell] = 1
                        val top = Cell(
                            row + nextNeighbour[Direction.NORTH]!!.first,
                            column,
                            Direction.NORTH
                        )
                        val bottom = Cell(
                            row + nextNeighbour[Direction.SOUTH]!!.first,
                            column,
                            Direction.SOUTH
                        )

                        if (isInsideBounds(top.row, top.column, n, m) && top !in visited) {
                            next.add(top)
                            visited[top] = 1
                        }
                        if (isInsideBounds(bottom.row, bottom.column, n, m) && bottom !in visited) {
                            next.add(bottom)
                            visited[bottom] = 1
                        }
                    }
                }
            }
        }

        val visitedCells: Int =
            visited.keys.mapNotNull { v -> if (isInsideBounds(v.row, v.column, n, m)) Pair(v.row, v.column) else null }
                .toSet().size

        return visitedCells
    }

    override fun solve1() {
        println(bfs())
    }

    private fun isInsideBounds(row: Int, column: Int, n: Int, m: Int): Boolean {
        return row in 0..<n && column in 0..<m
    }

    override fun solve2() {
        val cells: ArrayList<Cell> = ArrayList()
        val grid = getInput()
        val maxTiles: AtomicInteger = AtomicInteger(0)

        for (i in 0..<grid[0].size) {
            cells.add(Cell(0, i, Direction.SOUTH))
            cells.add(Cell(grid.size - 1, i, Direction.NORTH))
        }

        for (i in 0..<grid.size) {
            cells.add(Cell(i, 0, Direction.EAST))
            cells.add(Cell(i, grid[0].size - 1, Direction.WEST))
        }

        val executionSequential = measureTimeMillis {
            cells.map { cell -> bfs(cell) }.max()
        }

        val executionParallel = measureTimeMillis {
            cells.stream().parallel().forEach { cell ->
                maxTiles.getAndAccumulate(bfs(cell), ::maxOf)
            }
        }
        println(executionParallel)

        println(executionSequential)

        println(maxTiles.get())
    }
}