package day14

import java.io.File

class Day14 {
    init {
        solve1()
    }

    private fun solve2(){
        val cycles: Int = 1_000_000_000

        
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