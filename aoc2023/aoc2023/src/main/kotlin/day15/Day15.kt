package day15

import java.io.File

data class Lens(var name: String, var focal: Int)

class Day15 {
    init {
        //solve1()
        solve2()
    }

    private fun hash(string: String): Int {
        var sum = 0
        string.forEach {
            sum += it.toInt()
            sum *= 17
            sum %= 256
        }
        return sum
    }


    private fun solve2() {
        val boxes: HashMap<Int, List<Lens>> = HashMap()
        for (x in 0..255) {
            boxes[x] = ArrayList()
        }

        File("src/main/kotlin/day15/1.txt")
            .readLines()
            .first
            .split(",")
            .forEach {
                val name: String = Regex("[a-z]+").find(it)!!.value
                val hashedValue: Int = hash(name)
                if (it.contains("=")) {
                    val newFocal: Int = it.split("=")[1].toInt()
                    val existingBoxes: List<Lens> = boxes[hashedValue]!!.filter { box -> box.name == name }
                    if (existingBoxes.isNotEmpty()) {
                        existingBoxes.forEach { box ->
                            box.focal = newFocal
                        }
                    } else {
                        boxes[hashedValue]!!.addLast(Lens(name, newFocal))
                    }
                } else {
                    boxes[hashedValue] = boxes[hashedValue]!!.filter { box -> box.name != name }
                }
            }
        var sum: Long = 0L

        for (boxNumber in 0..255) {
            boxes[boxNumber]!!.forEachIndexed { index, box ->
                sum += (1 + boxNumber) * (index + 1) * box.focal * 1L
            }
        }

        println(sum)


    }

    private fun solve1() {
        val result: Int =
            File("src/main/kotlin/day15/1.txt")
                .readLines()
                .first
                .split(",")
                .sumOf { hash(it) }

        println(result)
    }
}