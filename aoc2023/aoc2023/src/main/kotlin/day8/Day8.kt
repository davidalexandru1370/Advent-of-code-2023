package day8

import java.io.File
import java.util.LinkedList
import java.util.Queue

class Day8 {
    init {
        part1()
    }

    fun part1() {
        val input: List<String> = File("src/main/kotlin/day8/1.txt").readLines()
        val steps: List<String> = input[0].map { it.toString() }
        val neighbours: HashMap<String, Pair<String, String>> = HashMap()
        val visited: HashMap<String, Boolean> = HashMap()
        val queue: Queue<String> = LinkedList()
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

    fun part2(input: List<String>): Int {
        return 0
    }
}