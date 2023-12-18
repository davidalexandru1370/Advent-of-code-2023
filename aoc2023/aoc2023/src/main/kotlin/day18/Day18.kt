package day18

import helpers.IDay
import java.io.File
import java.util.HashMap
import kotlin.math.abs

class Day18 : IDay {

    init {
        test()
        //solve1()
        solve2()
    }

    override fun solve1() {
        val sparseMatrix: HashMap<Pair<Int, Int>, Int> = HashMap()
        val points: ArrayList<Pair<Int, Int>> = ArrayList()
        var currentX: Int = 0
        var currentY: Int = 0
        var perimeter: Int = 0
        File("src/main/kotlin/day18/1.txt")
            .forEachLine { line ->
                val words = line.split(" ")
                val direction = words[0]
                var distance = words[1].toInt()
                perimeter += distance
                when (direction) {
                    "L" -> {
                        currentX -= distance
                    }

                    "R" -> {
                        currentX += distance
                    }

                    "U" -> {
                        currentY += distance
                    }

                    "D" -> {
                        currentY -= distance
                    }
                }
                val point: Pair<Int, Int> = Pair(currentX, currentY)
                points.add(point)
            }

        println(points)
        println(perimeter)
        val area = abs(computeArea(points))
        var insidePoints: Int = area - perimeter / 2 + 1

        println(insidePoints + perimeter)
    }

    private fun test() {
        var points: ArrayList<Pair<Int, Int>> = ArrayList(
            listOf(
                Pair(0, 0),
                Pair(1, 0),
                Pair(2, 0),
                Pair(2, 1),
                Pair(2, 2),
                Pair(1, 2),
                Pair(0, 2),
                Pair(0, 1)
            )
        )

        val area = abs(computeArea(points))

        assert(area == 4)
    }

    private fun computeArea(sparseMatrix: ArrayList<Pair<Long, Long>>): Long {
        var left: Long = 0
        var right: Long = 0
        for (i in 0..<sparseMatrix.size) {
            left += sparseMatrix[i].first * sparseMatrix[(i + 1) % sparseMatrix.size].second
        }

        for (i in 0..<sparseMatrix.size) {
            right += sparseMatrix[(i + 1) % sparseMatrix.size].first * sparseMatrix[i].second
        }

        return (left - right) / 2L

    }

    private fun computeArea(sparseMatrix: ArrayList<Pair<Int, Int>>): Int {
        return computeArea(sparseMatrix.map {
            Pair(
                it.first.toLong(),
                it.second.toLong()
            )
        } as ArrayList<Pair<Long, Long>>).toInt()
    }

    override fun solve2() {
        val points: ArrayList<Pair<Long, Long>> = ArrayList()
        var currentX: Long = 0
        var currentY: Long = 0
        var perimeter: Long = 0
        File("src/main/kotlin/day18/1.txt")
            .forEachLine { line ->
                val words = line.split(" ")
                val direction = when (words[2].dropLast(1).last().toString()) {
                    "0" -> "R"
                    "1" -> "D"
                    "2" -> "L"
                    "3" -> "U"
                    else -> ""
                }

                val distance: Long = words[2].drop(2).dropLast(2).toLong(radix = 16)
                perimeter += distance
                when (direction) {
                    "L" -> {
                        currentX -= distance
                    }

                    "R" -> {
                        currentX += distance
                    }

                    "U" -> {
                        currentY += distance
                    }

                    "D" -> {
                        currentY -= distance
                    }
                }
                val point: Pair<Long, Long> = Pair(currentX, currentY)
                points.add(point)
            }

//        println(points)
//        println(perimeter)
        //pick & shoelace theorems
        val area = abs(computeArea(points))
        val insidePoints: Long = area - perimeter / 2 + 1

        println(insidePoints + perimeter)
    }
}