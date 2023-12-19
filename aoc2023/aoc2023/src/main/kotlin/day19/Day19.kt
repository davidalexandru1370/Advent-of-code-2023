package day19

import helpers.IDay
import java.io.File

data class Condition(
    var letter: Char,
    val condition: (x: Int, y: Int) -> Boolean,
    val value: Int,
    val nextState: String
) {
    fun isTrue(x: Int): Boolean = condition(x, value)
}

data class Workflow(val conditions: List<Condition>, val finalState: String) {
    fun nextWorkflow(x: Int, m: Int, a: Int, s: Int): String {
        val result: String = conditions.firstOrNull {
            when (it.letter) {
                'x' -> it.isTrue(x)
                'm' -> it.isTrue(m)
                'a' -> it.isTrue(a)
                's' -> it.isTrue(s)
                else -> false
            }
        }?.nextState ?: finalState
        return result
    }

    fun nextWorkFlowRange(): String {

    }
}

class Day19 : IDay {
    init {
//        solve1()
        solve2()
    }

    private fun smallerThan(x: Int, y: Int): Boolean = x < y

    private fun greaterThan(x: Int, y: Int): Boolean = x > y

    override fun solve1() {
        val lines = File("src/main/kotlin/day19/1.txt").readLines()
        val inputParts = lines
            .joinToString("\n")
            .split("\n\n")
        val finalStates: List<String> = listOf("A", "R")
        var initialState: Workflow = Workflow(listOf(), "")
        val workflow: HashMap<String, Workflow> = inputParts[0]
            .split("\n")
            .associateTo(HashMap()) {
                val key: String = "[a-z]+".toRegex().find(it)!!.value
                val conditions: List<Condition> = ArrayList()
                var finalState: String = ""
                "[{].+[}]"
                    .toRegex()
                    .find(it)!!
                    .value
                    .drop(1)
                    .dropLast(1)
                    .split(",")
                    .map {
                        if (it.contains(":")) {
                            val parts = it.split(":")
                            val left: String = parts[0].trim()
                            val right: String = parts[1].trim()
                            if (left.contains("<")) {
                                val value: Int = left.substring(left.indexOf("<") + 1).trim().toInt()
                                conditions.addLast(Condition(left[0], ::smallerThan, value, right))
                            } else {
                                val value: Int = left.substring(left.indexOf(">") + 1).trim().toInt()
                                conditions.addLast(Condition(left[0], ::greaterThan, value, right))
                            }
                        } else {
                            finalState = it.trim()
                        }
                    }
                val stateWorkflow = Workflow(conditions, finalState)
                if (key == "in") {
                    initialState = stateWorkflow
                }
                key to stateWorkflow
            }

        var sum: Long = 0L
        inputParts[1]
            .split("\n")
            .map { it.substring(1, it.length - 1) }
            .forEach { query ->

                val numbers: List<Int> = "[0-9]+".toRegex().findAll(query).map { it.value.toInt() }.toList()
                val x: Int = numbers[0]
                val m: Int = numbers[1]
                val a: Int = numbers[2]
                val s: Int = numbers[3]

                var state: String = initialState.nextWorkflow(x, m, a, s)
                print(state + " -> ")
                while (state !in finalStates) {
                    state = workflow[state]!!.nextWorkflow(x, m, a, s)
                    print(state + " -> ")
                }
                println()
                if (state == "A") {
                    sum += 1L * (x + m + a + s)
                }


            }

        println(sum)
    }

    override fun solve2() {
        val lines = File("src/main/kotlin/day19/1.txt").readLines()
        val inputParts = lines
            .joinToString("\n")
            .split("\n\n")
        val finalStates: List<String> = listOf("A", "R")
        var initialState: Workflow = Workflow(listOf(), "")
        val workflow: HashMap<String, Workflow> = inputParts[0]
            .split("\n")
            .associateTo(HashMap()) {
                val key: String = "[a-z]+".toRegex().find(it)!!.value
                val conditions: List<Condition> = ArrayList()
                var finalState: String = ""
                "[{].+[}]"
                    .toRegex()
                    .find(it)!!
                    .value
                    .drop(1)
                    .dropLast(1)
                    .split(",")
                    .map {
                        if (it.contains(":")) {
                            val parts = it.split(":")
                            val left: String = parts[0].trim()
                            val right: String = parts[1].trim()
                            if (left.contains("<")) {
                                val value: Int = left.substring(left.indexOf("<") + 1).trim().toInt()
                                conditions.addLast(Condition(left[0], ::smallerThan, value, right))
                            } else {
                                val value: Int = left.substring(left.indexOf(">") + 1).trim().toInt()
                                conditions.addLast(Condition(left[0], ::greaterThan, value, right))
                            }
                        } else {
                            finalState = it.trim()
                        }
                    }
                val stateWorkflow = Workflow(conditions, finalState)
                if (key == "in") {
                    initialState = stateWorkflow
                }
                key to stateWorkflow
            }


        inputParts[1]
            .split("\n")
            .map { it.substring(1, it.length - 1) }
            .forEach { query ->


                var state: String = initialState.nextWorkflow(x, m, a, s)
                print(state + " -> ")
                while (state !in finalStates) {
                    state = workflow[state]!!.nextWorkflow(x, m, a, s)
                    print(state + " -> ")
                }
                println()
                if (state == "A") {
                    sum += 1L * (x + m + a + s)
                }

            }
    }
}