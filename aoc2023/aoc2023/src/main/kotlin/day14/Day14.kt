package day14

import java.io.File
import java.util.Collections

class Day14 {
    init {
        //solve1()
        solve2()
    }

    private fun solve2() {
        val cycles: Int = 1_000_000_000

        val lines = File("src/main/kotlin/day14/1.txt").readLines()
        var grid: Array<Array<String>> = Array(lines.size) { Array(lines[0].length) { "" } }
        lines.forEachIndexed { row, line ->
            line.forEachIndexed { col, c ->
                grid[row][col] = c.toString()
            }
        }

        val visited: HashSet<String> = HashSet()
        visited.add(grid.contentDeepToString())
        val order: ArrayList<Pair<String, Array<Array<String>>>> = ArrayList()
        order.add(Pair(grid.contentDeepToString(), grid.map { it.copyOf() }.toTypedArray()))
        var iteration: Int = 1
        var first: Int = -1
        while (iteration < cycles) {
            for (i in 1..4) {
                val nextGrid = fallRocks(grid)
                grid = rotateClockwise(nextGrid)
            }

            if (grid.contentDeepToString() in visited) {
                break
            } else {
                visited.add(grid.contentDeepToString())
                order.add(Pair(grid.contentDeepToString(), grid.map { it.copyOf() }.toTypedArray()))
            }
            iteration += 1
        }
        first = order.indexOfFirst { it.first == grid.contentDeepToString() }
        println("$first, $iteration")
        val ans = computeLoad(order[(cycles - first) % (iteration - first) + first].second)
        println(ans)
    }

    private fun computeLoad(grid: Array<Array<String>>): Long {
        var rows: Int = grid.size
        var sum: Long = 0
        grid.forEach { line ->
            val matches: Int = Regex("[O]").findAll(line.joinToString(separator = "")).toList().size
            sum += 1L * rows * matches
            rows -= 1
        }

        return sum
    }

    private fun fallRocks(grid: Array<Array<String>>): Array<Array<String>> {
        val n: Int = grid.size
        val m: Int = grid[0].size

        for (columnIndex in 0..<m) {
            val column: String = grid.joinToString("") { it[columnIndex] }
            val blockers: MutableList<Int> = Regex("[#]").findAll(column).map { it.range.first }.toMutableList()
            blockers.addFirst(0)
            blockers.addLast(column.length - 1)
            var index = 0

            while (index < blockers.size - 1) {
                val left = blockers[index]
                val right = blockers[index + 1]

                val substr = column.substring(left, right + 1)
                if (substr.length > 0) {
                    var rocks: List<String> = "[O]".toRegex().findAll(substr).map { it.value }.toList()
                    var rocksIndex: Int = 0
                    for (p in left..right) {
                        if (grid[p][columnIndex] == "#") {
                            continue
                        }
                        if (rocksIndex < rocks.size) {
                            grid[p][columnIndex] = "O"
                            rocksIndex += 1
                        } else {
                            grid[p][columnIndex] = "."
                        }
                    }
                    index += 1
                }
            }
        }

        return grid
    }

    private fun rotateClockwise(grid: Array<Array<String>>): Array<Array<String>> {
        return Array<Array<String>>(grid[0].size) { i ->
            Array(grid.size) { j ->
                grid[grid.size - 1 - j][i]
            }
        }
    }

    private fun solve1() {
        val lines = File("src/main/kotlin/day14/1.txt").readLines()
        val grid: Array<Array<String>> = Array(lines.size) { Array(lines[0].length) { "" } }
        lines.forEachIndexed { row, line ->
            line.forEachIndexed { col, c ->
                grid[row][col] = c.toString()
            }
        }

        for (columnIndex in 0..<lines[0].length) {
            var column: String = grid.joinToString("") { it[columnIndex] }
            var blockers: MutableList<Int> = Regex("[#]").findAll(column).map { it.range.first }.toMutableList()
            blockers.addFirst(0)
            blockers.addLast(column.length - 1)
            var index = 0

            while (index < blockers.size - 1) {
                val left = blockers[index]
                val right = blockers[index + 1]

                val substr = column.substring(left, right + 1)
                if (substr.length > 0) {
                    var rocks: List<String> = "[O]".toRegex().findAll(substr).map { it.value }.toList()
                    var rocksIndex: Int = 0
                    for (p in left..right) {
                        if (grid[p][columnIndex] == "#") {
                            continue
                        }
                        if (rocksIndex < rocks.size) {
                            grid[p][columnIndex] = "O"
                            rocksIndex += 1
                        } else {
                            grid[p][columnIndex] = "."
                        }
                    }
                    index += 1
                }
            }
        }
        var rows: Int = grid.size
        var sum: Int = 0
        grid.forEach { line ->
//            grid[index].forEach { print(it) }
//            println()
            val matches: Int = Regex("[O]").findAll(line.joinToString(separator = "")).toList().size
            sum += rows * matches
            rows -= 1
        }
        println(sum)

    }
}