package day3

import java.io.File

class Day3 {
    init {
        //solve1()
        solve2()
    }

    private fun solve2() {
        val file: File = File("src/main/kotlin/day3/2.txt")
        val lines: List<String> = file.readLines()
        val board: Array<Array<String>> = Array(1000) { Array(1000) { "" } }
        var row: Int = 0
        var n: Int = lines.size
        var m: Int = lines[0].length

        lines.forEach { line ->
            line.forEachIndexed { index, char ->
                board[row][index] = char.toString()
            }
            row += 1
        }
        var sum: Int = 0
        var starsSurroundings: HashMap<Pair<Int, Int>, List<Int>> = HashMap()

        lines.forEachIndexed { index, line ->
            Regex("\\d+")
                .findAll(line)
                .map { match -> arrayOf(match.value.toInt(), index, match.range.first) }
                .toList()
                .forEach { pair ->
                    val (value, row, _) = pair
                    var isSurrounded: Boolean = false
                    value.toString().forEachIndexed { index, _ ->
                        val column = pair[2] + index
                        if (index == 0) {
                            if (isPositionValid(row, column - 1, n, m) && board[row][column - 1] == "*") {
                                starsSurroundings[Pair(row, column - 1)] =
                                    starsSurroundings.getOrDefault(Pair(row, column - 1), listOf()) + listOf(value)
                            }
                            if (isPositionValid(row - 1, column - 1, n, m) && board[row - 1][column - 1] == "*") {
                                starsSurroundings[Pair(row - 1, column - 1)] =
                                    starsSurroundings.getOrDefault(Pair(row - 1, column - 1), listOf()) + listOf(value)
                            }
                            if (isPositionValid(row + 1, column - 1, n, m) && board[row + 1][column - 1] == "*") {
                                starsSurroundings[Pair(row + 1, column - 1)] =
                                    starsSurroundings.getOrDefault(Pair(row + 1, column - 1), listOf()) + listOf(value)
                            }
                        }
                        if (index + 1 == value.toString().length) {
                            if (isPositionValid(row, column + 1, n, m) && board[row][column + 1] == "*") {
                                starsSurroundings[Pair(row, column + 1)] =
                                    starsSurroundings.getOrDefault(Pair(row, column + 1), listOf()) + listOf(value)
                            }
                            if (isPositionValid(row - 1, column + 1, n, m) && board[row - 1][column + 1] == "*") {
                                starsSurroundings[Pair(row - 1, column + 1)] =
                                    starsSurroundings.getOrDefault(Pair(row - 1, column + 1), listOf()) + listOf(value)
                            }
                            if (isPositionValid(row + 1, column + 1, n, m) && board[row + 1][column + 1] == "*") {
                                starsSurroundings[Pair(row + 1, column + 1)] =
                                    starsSurroundings.getOrDefault(Pair(row + 1, column + 1), listOf()) + listOf(value)
                            }
                        }
                        if (isPositionValid(row + 1, column, n, m) && board[row + 1][column] == "*") {
                            starsSurroundings[Pair(row + 1, column)] =
                                starsSurroundings.getOrDefault(Pair(row + 1, column), listOf()) + listOf(value)
                        }
                        if (isPositionValid(row - 1, column, n, m) && board[row - 1][column] == "*") {
                            starsSurroundings[Pair(row - 1, column)] =
                                starsSurroundings.getOrDefault(Pair(row - 1, column), listOf()) + listOf(value)
                        }
                    }

                }
        }
        starsSurroundings.keys.forEach { key ->
            if (starsSurroundings[key]!!.size == 2) {
                sum += (starsSurroundings[key]!![0] * starsSurroundings[key]!![1])
            }
        }

        println(sum)
    }

    private fun solve1() {
        val file: File = File("src/main/kotlin/day3/1.txt")
        val lines: List<String> = file.readLines()
        val board: Array<Array<String>> = Array(1000) { Array(1000) { "" } }
        var row: Int = 0
        var n: Int = lines.size
        var m: Int = lines[0].length

        lines.forEach { line ->
            line.forEachIndexed { index, char ->
                board[row][index] = char.toString()
            }
            row += 1
        }
        var sum: Int = 0
        lines.forEachIndexed { index, line ->
            Regex("\\d+")
                .findAll(line)
                .map { match -> arrayOf(match.value.toInt(), index, match.range.first) }
                .toList()
                .forEach { pair ->
                    val (value, row, _) = pair
                    var isSurrounded: Boolean = false
                    value.toString().forEachIndexed { index, _ ->
                        var column = pair[2] + index
                        if (index == 0) {
                            if (isPositionValid(row, column - 1, n, m) && board[row][column - 1] != ".") {
                                isSurrounded = true
                            }
                            if (isPositionValid(row - 1, column - 1, n, m) && board[row - 1][column - 1] != "." &&
                                !board[row - 1][column - 1].matches(Regex("\\d"))
                            ) {
                                isSurrounded = true
                            }
                            if (isPositionValid(row + 1, column - 1, n, m) && board[row + 1][column - 1] != "." &&
                                !board[row + 1][column - 1].matches(Regex("\\d"))
                            ) {
                                isSurrounded = true
                            }
                        }
                        if (index + 1 == value.toString().length) {
                            if (isPositionValid(row, column + 1, n, m) && board[row][column + 1] != ".") {
                                isSurrounded = true
                            }
                            if (isPositionValid(row - 1, column + 1, n, m) && board[row - 1][column + 1] != "." &&
                                !board[row - 1][column + 1].matches(Regex("\\d"))
                            ) {
                                isSurrounded = true
                            }
                            if (isPositionValid(row + 1, column + 1, n, m) && board[row + 1][column + 1] != "." &&
                                !board[row + 1][column + 1].matches(Regex("\\d"))
                            ) {
                                isSurrounded = true
                            }
                        }
                        if (isPositionValid(row + 1, column, n, m) && board[row + 1][column] != "." &&
                            !board[row + 1][column].matches(Regex("\\d"))
                        ) {
                            isSurrounded = true
                        }
                        if (isPositionValid(row - 1, column, n, m) && board[row - 1][column] != "." &&
                            !board[row - 1][column].matches(Regex("\\d"))
                        ) {
                            isSurrounded = true
                        }
                    }
                    if (isSurrounded) {
                        sum += value
                    }
                }

        }

        println(sum)
    }

    private fun isPositionValid(i: Int, j: Int, n: Int, m: Int): Boolean {
        return i in 0..<n && j in 0..<m
    }

}