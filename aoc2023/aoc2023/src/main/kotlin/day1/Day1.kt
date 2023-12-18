package day1

import java.io.File

class Day1 {
    init {
        //solve1()
        solve2()
    }

    private fun solve2() {
        val file = File("src/main/kotlin/day1/2.txt")
        val lines: List<String> = file.readLines()
        val spelling: HashMap<String, Int> = HashMap()
        spelling.put("zero", 0)
        spelling.put("one", 1)
        spelling.put("two", 2)
        spelling.put("three", 3)
        spelling.put("four", 4)
        spelling.put("five", 5)
        spelling.put("six", 6)
        spelling.put("seven", 7)
        spelling.put("eight", 8)
        spelling.put("nine", 9)

        var sum: Int = 0;

        lines.forEach { line ->
            var firstDigit: Int = -1;
            var lastDigit: Int = -1;

            run breaking@{
                line.forEachIndexed { index, char ->
                    if (char.isDigit()) {
                        firstDigit = char.digitToInt()
                        return@breaking
                    } else {
                        spelling.keys.forEach { key ->
                            if (line.substring(index).startsWith(key)) {
                                firstDigit = spelling.get(key)!!
                                return@breaking
                            }
                        }
                    }
                }
            }
            line.forEachIndexed { index, char ->
                if (char.isDigit()) {
                    lastDigit = char.digitToInt()
                } else {
                    spelling.keys.forEach { key ->
                        if (line.substring(index).startsWith(key)) {
                            lastDigit = spelling.get(key)!!
                        }
                    }
                }
            }
            println("$firstDigit $lastDigit")
            sum += (firstDigit * 10 + lastDigit)


        }
        println(sum)

    }

    private fun solve1() {
        val file = File("src/main/kotlin/day1/1.txt")
        val lines: List<String> = file.readLines()
        var sum: Int = 0;
        lines.forEach { line ->
            var firstDigit: Int = line.first { it.isDigit() }.digitToInt()
            var lastDigit: Int = line.last { it.isDigit() }.digitToInt()
            sum += (firstDigit * 10 + lastDigit)
        }

        println(sum)
    }
}