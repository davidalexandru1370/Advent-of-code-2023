package day6

import java.io.File

class Day6 {
    init {
        //solve1()
        solve2()
    }


    private fun solve2() {
        val lines: List<String> = File("src/main/kotlin/day6/1.txt").readLines()
        val cv = lines[1].split(":")[1]
            .trim()
            .split("\\s".toRegex())
            .joinToString(separator = "") { if (it.matches("\\d+".toRegex())) it else "" }
        val time: Long =
            lines[0].split(":")[1]
                .trim()
                .split(" ")
                .joinToString(separator = "") { if (it.matches("\\d+".toRegex())) it else "" }
                .toLong()


        val maxDistance: Long =
            lines[1].split(":")[1]
                .trim()
                .split(" ")
                .joinToString(separator = "") { if (it.matches("\\d+".toRegex())) it else "" }
                .toLong()

        var ways: Long = 0

        for (hold in 0..time) {
            val distance: Long = hold * (time - hold)
            if (distance > maxDistance) {
                ways++
            }
        }
        print("Day 6, Part 2: $ways")
    }

    private fun solve1() {
        val lines: List<String> = File("src/main/kotlin/day6/1.txt").readLines()
        val times: List<Int> =
            lines[0].split(":")[1]
                .trim()
                .split(" ")
                .mapNotNull { if (it != "") it.toInt() else null }

        val distances: List<Int> =
            lines[1].split(":")[1]
                .trim()
                .split(" ")
                .mapNotNull { if (it != "") it.toInt() else null }

        var totalWays: Int = 1
        for ((index, time) in times.withIndex()) {
            var ways: Int = 0
            for (hold in 0..time) {
                val distance = hold * (time - hold)
                if (distance > distances[index]) {
                    ways++
                }
            }
            if (ways > 0) {
                totalWays *= ways
            }
        }

        println("Day 6, Part 1: $totalWays")
    }


}