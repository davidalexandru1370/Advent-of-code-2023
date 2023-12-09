package day9

import java.io.File
import java.util.LinkedList

class Day9 {
    init {
        //solve1()
        solve2()
    }


    private fun solve2() {
        val lines: List<String> = File("src/main/kotlin/day9/1.txt").readLines()
        var sum: Int = 0

        lines.forEach { line ->
            val datagram: List<List<Int>> = LinkedList()
            val numbers: List<Int> = line.split(" ").map { it.toInt() }
            datagram.addLast(numbers)

            while (!datagram.last().all { number -> number == 0 }) {
                val nextLine: List<Int> = ArrayList()
                val last = datagram.last()
                last.forEachIndexed { index, number ->
                    if (index < last.size - 1) {
                        nextLine.addLast(last[index + 1] - number)
                    }
                }
                datagram.addLast(nextLine)
            }

            datagram.last().addFirst(0)

            for (index in datagram.size - 2 downTo 0) {
                datagram[index].addFirst(datagram[index].first() - datagram[index + 1].first())
            }
            sum += datagram[0].first()
        }

        println(sum)
    }

    private fun solve1() {
        val lines: List<String> = File("src/main/kotlin/day9/1.txt").readLines()
        var sum: Int = 0
        lines.forEach { line ->
            val datagram: List<List<Int>> = ArrayList()
            val numbers: List<Int> = line.split(" ").map { it.toInt() }
            datagram.addLast(numbers)

            while (!datagram.last().all { number -> number == 0 }) {
                val nextLine: List<Int> = ArrayList()
                val last = datagram.last()
                last.forEachIndexed { index, number ->
                    if (index < last.size - 1) {
                        nextLine.addLast(last[index + 1] - number)
                    }
                }
                datagram.addLast(nextLine)
            }

            for (index in datagram.size - 2 downTo 0) {
                datagram[index].addLast(datagram[index + 1].last() + datagram[index].last())
            }
            sum += datagram[0].last()
        }

        println(sum)
    }
}