package day2

import java.io.File

class Day2 {

    init {
        //solve1()
        solve2()
    }

    private fun solve2() {

        val file = File("src/main/kotlin/day2/2.txt")
        val lines: List<String> = file.readLines()


        var sum: Int = 0

        lines.forEach { line ->
            val gameId = Regex("\\d+").find(line, 0)!!.value.toInt()
            var respectConstraint: Boolean = true
            var maxRed: Int = 0
            var maxGreen: Int = 0
            var maxBlue: Int = 0
            Regex("\\d+ (red|green|blue)")
                .findAll(line)
                .forEach { matchResult ->
                    val match = matchResult.value.split(" ")
                    val cubeCount = match[0].toInt()
                    val cubeColor = match[1]
                    when (cubeColor) {
                        "red" -> {
                            maxRed = Math.max(maxRed, cubeCount)
                        }

                        "green" -> {
                            maxGreen = Math.max(maxGreen, cubeCount)
                        }

                        "blue" -> {
                            maxBlue = Math.max(maxBlue, cubeCount)
                        }
                    }
                }
            val product = maxRed * maxGreen * maxBlue
            //println(product)
            sum += product

        }

        println(sum)
    }


    private fun solve1() {
        val file = File("src/main/kotlin/day2/1.txt")
        val lines: List<String> = file.readLines()

        val redCubes: Int = 12
        val greenCubes: Int = 13
        val blueCubes: Int = 14
        var sum: Int = 0

        lines.forEach { line ->
            val gameId = Regex("\\d+").find(line, 0)!!.value.toInt()

            var respectConstraint: Boolean = true

            Regex("\\d+ (red|green|blue)")
                .findAll(line)
                .forEach { matchResult ->
                    val match = matchResult.value.split(" ")
                    val cubeCount = match[0].toInt()
                    val cubeColor = match[1]
                    if (cubeColor == "red" && cubeCount > redCubes) {
                        respectConstraint = false
                    } else if (cubeColor == "green" && cubeCount > greenCubes) {
                        respectConstraint = false
                    } else if (cubeColor == "blue" && cubeCount > blueCubes) {
                        respectConstraint = false
                    }
                }

            if (respectConstraint) {
                sum += gameId
            }
        }

        println(sum)
    }
}