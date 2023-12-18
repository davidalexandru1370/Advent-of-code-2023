package day10

import java.io.File
import java.util.*
import kotlin.collections.ArrayList

enum class Direction {
    NORTH, SOUTH, EAST, WEST
}

data class Pipe(val previousDirection: Direction, val row: Int, val column: Int)

class Day10 {
    init {
        run_tests_part1()
        val lines: List<String> = File("src/main/kotlin/day10/1.txt").readLines()
        solve1(lines)
    }


    private fun solve2(input: List<String>): Int{
        var tiles: Int = 0
        val grid: List<List<Char>> = ArrayList()
        var start: Pair<Int, Int> = Pair(0, 0)

        input.forEachIndexed { row, line ->
            var column: ArrayList<Char> = ArrayList()
            line.forEachIndexed { col, c ->
                column.add(c)
                if (c == 'S') {
                    start = Pair(row, col)
                }
            }
            grid.addLast(column)
        }

        return tiles
    }

    private fun solve1(input: List<String>): Int {
        val grid: List<List<Char>> = ArrayList()
        var start: Pair<Int, Int> = Pair(0, 0)

        input.forEachIndexed { row, line ->
            var column: ArrayList<Char> = ArrayList()
            line.forEachIndexed { col, c ->
                column.add(c)
                if (c == 'S') {
                    start = Pair(row, col)
                }
            }
            grid.addLast(column)
        }

//      | is a vertical pipe connecting north and south.
//      - is a horizontal pipe connecting east and west.
//      L is a 90-degree bend connecting north and east.
//      J is a 90-degree bend connecting north and west.
//      7 is a 90-degree bend connecting south and west.
//      F is a 90-degree bend connecting south and east.

        val directions: Map<String, List<Pair<Int, Int>>> = mapOf(
            "|" to listOf(Pair(1, 0), Pair(-1, 0)),
            "-" to listOf(Pair(0, 1), Pair(0, -1)),
            "L" to listOf(Pair(1, 0), Pair(0, 1)),
        )

        var maxSteps: Int = 0

        val queue: Queue<Pipe> = LinkedList<Pipe>()

        val (row, col) = start
        val n = grid.size
        val m = grid[0].size
        val visited: ArrayList<ArrayList<Int>> =
            ArrayList<ArrayList<Int>>(n).apply {
                repeat(n) {
                    add(ArrayList<Int>().apply {
                        addAll(List(m) { 0 })
                    })
                }
            }
        visited[start.first][start.second] = 0

        val top: Pair<Int, Int> = Pair(row - 1, col)
        val bottom: Pair<Int, Int> = Pair(row + 1, col)
        val left: Pair<Int, Int> = Pair(row, col - 1)
        val right: Pair<Int, Int> = Pair(row, col + 1)
        if (isInBounds(top.first, top.second, n, m) && listOf<Char>(
                '|',
                '7',
                'F'
            ).contains(grid[top.first][top.second])
        ) {
            visited[top.first][top.second] = visited[row][col] + 1
            queue.add(Pipe(Direction.SOUTH, top.first, top.second))
        }
        if (isInBounds(bottom.first, bottom.second, n, m) && listOf<Char>(
                '|',
                'L',
                'J'
            ).contains(grid[bottom.first][bottom.second])
        ) {
            visited[bottom.first][bottom.second] = visited[row][col] + 1
            queue.add(Pipe(Direction.NORTH, bottom.first, bottom.second))
        }

        if (isInBounds(left.first, left.second, n, m) && listOf<Char>(
                '-',
                'L',
                'F'
            ).contains(grid[left.first][left.second])
        ) {
            visited[left.first][left.second] = visited[row][col] + 1
            queue.add(Pipe(Direction.EAST, left.first, left.second))
        }

        if (isInBounds(right.first, right.second, n, m) && listOf<Char>(
                '-',
                'J',
                '7'
            ).contains(grid[right.first][right.second])
        ) {
            visited[right.first][right.second] = visited[row][col] + 1
            queue.add(Pipe(Direction.WEST, right.first, right.second))
        }
        maxSteps = 1
        queue.poll()
        while (queue.isNotEmpty()) {
            val front: Pipe = queue.poll()
            if (front.row == start.first && front.column == start.second) {
                println(maxSteps / 2 + maxSteps % 2)
                break
            }
            when (grid[front.row][front.column]) {
                'L' -> {
                    if (front.previousDirection == Direction.NORTH) {
                        val next: Pipe = Pipe(Direction.WEST, front.row, front.column + 1)
                        queue.add(next)

                    } else {
                        val next: Pipe = Pipe(Direction.SOUTH, front.row - 1, front.column)
                        queue.add(next)
                    }
                }

                'J' -> {
                    if (front.previousDirection == Direction.NORTH) {
                        val next: Pipe = Pipe(Direction.EAST, front.row, front.column - 1)
                        val distance: Int = visited[front.row][front.column] + 1
                        queue.add(next)

                    } else {
                        val next: Pipe = Pipe(Direction.SOUTH, front.row - 1, front.column)
                        val distance: Int = visited[front.row][front.column] + 1
                        queue.add(next)
                    }
                }

                '7' -> {
                    if (front.previousDirection == Direction.SOUTH) {
                        val next: Pipe = Pipe(Direction.EAST, front.row, front.column - 1)
                        val distance: Int = visited[front.row][front.column] + 1
                        queue.add(next)
                    } else {
                        val next: Pipe = Pipe(Direction.NORTH, front.row + 1, front.column)
                        val distance: Int = visited[front.row][front.column] + 1
                        queue.add(next)
                    }
                }

                '|' -> {
                    if (front.previousDirection == Direction.NORTH) {
                        val next: Pipe = Pipe(Direction.NORTH, front.row + 1, front.column)
                        val distance: Int = visited[front.row][front.column] + 1
                        queue.add(next)
                    } else {
                        val next: Pipe = Pipe(Direction.SOUTH, front.row - 1, front.column)
                        val distance: Int = visited[front.row][front.column] + 1
                        queue.add(next)
                    }
                }

                '-' -> {
                    if (front.previousDirection == Direction.WEST) {
                        val next: Pipe = Pipe(Direction.WEST, front.row, front.column + 1)
                        val distance: Int = visited[front.row][front.column] + 1
                        queue.add(next)
                    } else {
                        val next: Pipe = Pipe(Direction.EAST, front.row, front.column - 1)
                        val distance: Int = visited[front.row][front.column] + 1
                        queue.add(next)
                    }
                }

                'F' -> {
                    if (front.previousDirection == Direction.EAST) {
                        val next: Pipe = Pipe(Direction.NORTH, front.row + 1, front.column)
                        val distance: Int = visited[front.row][front.column] + 1
                        queue.add(next)
                    } else {
                        val next: Pipe = Pipe(Direction.WEST, front.row, front.column + 1)
                        val distance: Int = visited[front.row][front.column] + 1
                        queue.add(next)
                    }
                }
            }
            maxSteps += 1
        }


        return maxSteps / 2 + maxSteps % 2
    }

    private fun isInBounds(row: Int, col: Int, n: Int, m: Int): Boolean {
        return row in 0..<n && col in 0..<m
    }

    private fun run_tests_part1() {
        val input1: List<String> = listOf(
            "..F7.",
            ".FJ|.",
            "SJ.L7",
            "|F--J",
            "LJ...",
        )

        val answer: Int = solve1(input1)
        assert(answer == 8)

        val input2: List<String> = listOf(
            ".....",
            ".S-7.",
            ".|.|.",
            ".L-J.",
            "....."
        )

        val answer2: Int = solve1(input2)

        assert(answer2 == 4)
    }
}