package day15

import java.io.File

class Day15 {
    init {
        solve1()
    }

    private fun hash(string: String): Int {
        var sum = 0
        string.forEach {
            sum += it.toInt()
            sum *= 17
            sum %= 256
        }
        return sum
    }

    private fun solve1() {
        var result: Int =
            File("src/main/kotlin/day15/1.txt")
                .readLines()
                .first
                .split(",")
                .sumOf { hash(it) }

        println(result)
    }
}