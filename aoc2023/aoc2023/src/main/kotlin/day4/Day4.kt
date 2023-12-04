package day4

import java.io.File
import java.util.*
import kotlin.collections.HashMap

class Day4 {
    init {
        solve1()
    }

    private fun solve1() {
        val file: File = File("src/main/kotlin/day4/1.txt")
        val lines: List<String> = file.readLines()
        var totalPoints: Int = 0;
        var maxCard: Int = lines.last()
            .split(":")[0]
            .split(" ")[1]
            .toInt()
        val queue: Queue<String> = LinkedList(lines)
        val freq: HashMap<Int, Int> = HashMap()

        while (queue.size > 0) {
            val line: String = queue.poll()
            val elements: List<String> = line.split(":")[1]
                .split("|")
                .map { it.trim() }

            val givenCards: HashMap<Int, Int> =
                elements[1]
                    .split(" ")
                    .filter { it.trim() != "" }
                    .associate { it.toString().toInt() to 1 } as HashMap<Int, Int>

            var points: Int = 0;
            elements[0]
                .split(" ")
                .filter { it.trim() != "" }
                .forEach { card ->
                    val cardInt: Int = card.toString().toInt()
                    if (givenCards.contains(cardInt)) {
                        points += 1
                    }
                }

            var card: Int = line
                .split(":")[0]
                .split(" ")
                .filter { it.trim() != "" }[1]
                .toInt()

            if(!freq.containsKey(card)){
                freq.put(card, 1)
            }
            else{
                freq.put(card, freq.get(card)!! + 1)
            }

            if (points != 0) {
                for (i in 1..points) {
                    if (card + i > maxCard) {
                        break
                    }
                    queue.add(lines[card + i - 1])
                }
            }
        }

        println(freq.keys.sumOf { freq.get(it)!!  })

    }
}