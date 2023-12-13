package day12

import java.io.File
import kotlin.math.max

data class StringTemplate(val template: String, val pattern: List<Int>)

class Day12 {
    private var cache: HashMap<Triple<Int, Int, Int>, Long> = HashMap()

    init {
        //solve1()
        solve2()
    }

    private fun solve2() {
        cache.clear()
        val templates: List<StringTemplate> = File("src/main/kotlin/day12/1.txt")
            .readLines()
            .map { line ->
                val template: String = line.substringBefore(" ")
                val pattern: List<Int> = ArrayList()
                repeat(5) { line.substringAfter(" ").split(",").forEach { pattern.addLast(it.toInt()) } }
                StringTemplate(
                    List(5) { template }.joinToString(separator = "?"),
                    pattern
                )
            }
        var sum: Long = 0L
        templates.forEach { stringTemplate ->
            sum += dfs(stringTemplate.template, stringTemplate.pattern, 0, 0, 0)
            cache.clear()
        }

        println(sum)
    }

    private fun dfs(
        template: String,
        pattern: List<Int>,
        templateIndex: Int,
        patternIndex: Int,
        currentBlockLength: Int
    ): Long {
        var key: Triple<Int, Int, Int> = Triple(templateIndex, patternIndex, currentBlockLength)
        if (key in cache) {
            return cache[key]!!
        }

        if (templateIndex >= template.length) {
            if (patternIndex == pattern.size && currentBlockLength == 0) {
                return 1
            }
            if (patternIndex == pattern.size - 1 && pattern[patternIndex] == currentBlockLength) {
                return 1
            }
            return 0
        }
        var combinations: Long = 0L

        for (c in listOf('.', '#')) {
            if (template[templateIndex] == c || template[templateIndex] == '?') {
                if (c == '.' && currentBlockLength == 0) {
                    combinations += dfs(template, pattern, templateIndex + 1, patternIndex, 0)
                } else if (c == '.' && patternIndex < pattern.size && currentBlockLength < 0 && currentBlockLength == pattern[patternIndex]) {
                    combinations += dfs(template, pattern, templateIndex + 1, patternIndex + 1, 0)
                } else if (c == '#') {
                    combinations += dfs(template, pattern, templateIndex + 1, patternIndex, currentBlockLength + 1)
                }
            }
        }

        cache[key] = combinations

        return combinations;
    }

    private fun solve1() {
        val templates: List<StringTemplate> = File("src/main/kotlin/day12/1.txt")
            .readLines()
            .map { line ->
                StringTemplate(
                    line.substringBefore(" "),
                    line.substringAfter(" ").split(",").map { it.toInt() }
                )
            }

        println(templates.sumOf { generateNumberOfPossibleCombinations(it) })
    }

    private fun generateNumberOfPossibleCombinations(template: StringTemplate): Int {
        var combinations: Int = 0
        val questionMarksIndices: List<Int> =
            template.template.withIndex().filter { it.value == '?' }.map { it.index }
        val masksNumber: Long = 1L shl questionMarksIndices.size
        val visited: HashMap<String, Int> = HashMap()
        for (mask in 0..masksNumber) {
            val stringBuilder: StringBuilder = StringBuilder(template.template)
            var cpMask = mask
            for (index in questionMarksIndices) {
                val rightMost = cpMask and 1L
                if (rightMost != 0L) {
                    stringBuilder[index] = '#'
                } else {
                    stringBuilder[index] = '.'
                }
                cpMask = cpMask shr 1
            }
            if (isCombinationValid(stringBuilder.toString(), template.pattern)) {
                if (stringBuilder.toString() !in visited) {
                    combinations += 1
                    visited[stringBuilder.toString()] = 1
                }
            }
        }

        //println("${template.template} -> ${combinations}")
        return combinations
    }

    private fun isCombinationValid(combination: String, template: List<Int>): Boolean {
        val hashtags: List<Int> = "#+".toRegex().findAll(combination).map { it.value.length }.toList()

        return hashtags == template
    }
}