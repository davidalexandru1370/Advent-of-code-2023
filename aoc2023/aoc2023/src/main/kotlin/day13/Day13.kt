package day13

import java.io.File

class Day13 {
    init {
        solve1()
    }

    private fun solve1() {
        val templates = File("src/main/kotlin/day13/1.txt").readLines().joinToString("\n")
        var matrices = templates.split("\n\n")
        var sum: Long = 0L

        for(matrix in matrices){
            val lines: List<String> = matrix.split("\n")
            val linesIdentical: HashMap<String, List<Int>> = HashMap()
            val columnsIdentical: HashMap<String, List<Int>> = HashMap()

            //2,3,4,5,6,7,8,9
            var left: Int = 0
            var right: Int = 0
            //2,3,4,5,6,7
            val columns: ArrayList<String> = ArrayList<String>().apply {
                repeat(lines[0].length) {
                    add("")
                }
            }

            lines.forEachIndexed { index, line ->
                if (line !in linesIdentical) {
                    linesIdentical[line] = ArrayList()
                }
                line.forEachIndexed { indexColumn, c ->
                    columns[indexColumn] = columns[indexColumn] + c
                }
                linesIdentical[line]!!.addLast(index)
            }

            columns.forEachIndexed { index, column ->
                if (column !in columnsIdentical) {
                    columnsIdentical[column] = ArrayList()
                }
                columnsIdentical[column]!!.addLast(index)
            }

            val indicesLines: List<Int> = linesIdentical.values.filter { it.size == 2 }.flatten().sorted()
            val indicesColumn: List<Int> = columnsIdentical.values.filter { it.size == 2 }.flatten().sorted()

            if (indicesLines.size > indicesColumn.size) {
                val mirrorIndex = indicesLines[indicesLines.size / 2]
                sum += (mirrorIndex * 100L)
            } else {
                val mirrorIndex = indicesColumn[indicesColumn.size / 2]
                sum += (mirrorIndex * 1L)
            }
        }


        println(sum)
    }
}