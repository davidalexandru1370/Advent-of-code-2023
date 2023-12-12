package day12

import java.io.File
import kotlin.math.max

data class StringTemplate(val template: String, val pattern: List<Int>)

class Day12 {
    init {
        solve1()
        solve2()
    }

    private fun solve2(){

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
            for(index in questionMarksIndices){
                val rightMost = cpMask and 1L
                if(rightMost != 0L){
                    stringBuilder[index] = '#'
                }
                else{
                    stringBuilder[index] = '.'
                }
                cpMask = cpMask shr 1
            }
            if(isCombinationValid(stringBuilder.toString(), template.pattern)){
                if(stringBuilder.toString() !in visited){
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