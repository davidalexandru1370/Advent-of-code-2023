package day8

import java.io.File
import java.util.LinkedList
import java.util.Queue
import kotlin.math.max
import kotlin.math.min

class Day8 {
    init {
        //part1()
        part2()
    }

    fun part2() {
        val input: List<String> = File("src/main/kotlin/day8/1.txt").readLines()
        val steps: List<String> = input[0].map { it.toString() }
        val neighbours: HashMap<String, Pair<String, String>> = HashMap()
        val distances: List<Long> = ArrayList()
        input.drop(2)
            .forEach { line ->
                val matches: List<String> = Regex("[aA-zZ0-9]{3}").findAll(line).map { it.value }.toList()
                neighbours[matches[0]] = Pair(matches[1], matches[2])
            }

        neighbours.keys.filter { it.last() == 'A' }.forEach { node ->
            var nextNode: String = node
            var distance: Int = 0
            var index: Int = 0

            while (nextNode.last() != 'Z') {
                index %= steps.size
                nextNode = if (steps[index] == "L") {
                    neighbours[nextNode]!!.first
                } else {
                    neighbours[nextNode]!!.second
                }
                index += 1
                distance += 1
            }

            distances.addLast(distance * 1L)
        }


        var numberOfSteps: Long = distances.reduce { a, b -> lcm(a, b) }
        println(numberOfSteps)
    }


    private fun gcd(a: Long, b: Long): Long {
        if (a == 0L) {
            return b
        }
        return gcd(b % a, a)
    }

    private fun lcm(a: Long, b: Long): Long {
        return a * b / gcd(a, b)
    }


    fun part1() {
        val input: List<String> = File("src/main/kotlin/day8/1.txt").readLines()
        val steps: List<String> = input[0].map { it.toString() }
        val neighbours: HashMap<String, Pair<String, String>> = HashMap()
        input.drop(2)
            .forEach { line ->
                val matches: List<String> = Regex("[A-Z]{3}").findAll(line).map { it.value }.toList()
                neighbours[matches[0]] = Pair(matches[1], matches[2])
            }

        var currentNode: String = "AAA"
        var index: Int = 0
        var numberOfSteps: Int = 0
        while (currentNode != "ZZZ") {
            if (index >= steps.size) {
                index = 0
            }
            if (steps[index] == "L") {
                currentNode = neighbours[currentNode]!!.first
            } else {
                currentNode = neighbours[currentNode]!!.second
            }
            numberOfSteps += 1
            index += 1
        }

        println(numberOfSteps)
    }
}
