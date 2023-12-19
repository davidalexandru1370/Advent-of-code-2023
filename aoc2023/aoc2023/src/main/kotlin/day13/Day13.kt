package day13

import helpers.IDay

import java.io.File

class Day13 : IDay {
    init {
        //solve1()
        solve2()
    }

    private fun findMirror(lines: List<String>): Int {
        for(split in 1..<lines.size) {
            var first = lines.subList(0, split).reversed()
            var second = lines.subList(split, lines.size)

            if(second.size < first.size){
                first = first.subList(0, second.size)
            }
            if(first.size < second.size){
                second = second.subList(0, first.size)
            }

            if(first == second) {
                return split
            }
        }

        return 0
    }

    override fun solve1() {
        val templates = File("src/main/kotlin/day13/1.txt").readLines().joinToString("\n")
        val matrices: List<String> = templates.split("\n\n")
        var sum: Long = 0L


        matrices.forEach { matrix ->
            val lines: List<String> = matrix.split("\n")
            val columns: List<String> =
                List<String>(lines[0].length) { i -> lines.joinToString(separator = "") { it.getOrNull(i).toString() } }

            val linesMirror: Int = findMirror(lines)
            val columnsMirror: Int = findMirror(columns)

            sum += (linesMirror * 100 + columnsMirror)
        }

        println(sum)
    }


    private fun findMirrorSmudge(lines: List<String>): Int {
        for(split in 1..<lines.size) {
            var first = lines.subList(0, split).reversed()
            var second = lines.subList(split, lines.size)

            if(second.size < first.size){
                first = first.subList(0, second.size)
            }
            if(first.size < second.size){
                second = second.subList(0, first.size)
            }

            val levenshtein = first
                .zip(second)
                .sumOf { (a, b) -> a.zip(b).count  { (c, d) -> c != d } }

            if(levenshtein == 1){
                return split
            }
        }

        return 0
    }
    override fun solve2() {
        val templates = File("src/main/kotlin/day13/1.txt").readLines().joinToString("\n")
        val matrices: List<String> = templates.split("\n\n")
        var sum: Long = 0L

        matrices.forEach { matrix ->
            val lines: List<String> = matrix.split("\n")
            val columns: List<String> =
                List<String>(lines[0].length) { i -> lines.joinToString(separator = "") { it.getOrNull(i).toString() } }

            val linesMirror: Int = findMirrorSmudge(lines)
            val columnsMirror: Int = findMirrorSmudge(columns)

            sum += (linesMirror * 100 + columnsMirror)
        }

        println(sum)

    }
}