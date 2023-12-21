package day21

import helpers.IDay
import java.io.File
import java.util.ArrayList

class Day21 : IDay {
    init {
        //solve1()
        solve2()
    }

    private fun getInput(): Array<Array<String>> {
        val lines: List<String> = File("src/main/kotlin/day21/1.txt").readLines()

        val grid = Array<Array<String>>(lines.size) { Array<String>(lines[0].length) { "" } }

        lines.forEachIndexed { row, line ->
            line.forEachIndexed { col, c ->
                grid[row][col] = c.toString()
            }
        }

        return grid
    }

    override fun solve1() {
        val grid = getInput()

        var start: Pair<Int, Int> = Pair(0, 0)

        grid.forEachIndexed { row, line ->
            line.forEachIndexed { col, s ->
                if (grid[row][col] == "S") {
                    start = Pair(row, col)
                }
            }
        }

        var positions: HashSet<Pair<Int, Int>> = HashSet()
        val directionRow: List<Int> = listOf(1, -1, 0, 0)
        val directionColumn: List<Int> = listOf(0, 0, -1, 1)
        val visited: HashMap<Pair<Int, Int>, Int> = HashMap()
        var steps: Int = 64
        var ans = 0
        positions.add(start)

        while (positions.isNotEmpty() && steps > 0) {
            val nextItems: HashSet<Pair<Int, Int>> = HashSet()

            for (front in positions) {
                for (i in 0..3) {
                    val nextRow = front.first + directionRow[i]
                    val nextColumn = front.second + directionColumn[i]

                    if (nextRow >= 0 && nextRow < grid.size && nextColumn >= 0 && nextColumn < grid[0].size
                        && grid[nextRow][nextColumn] != "#"
                    ) {
                        nextItems.add(Pair(nextRow, nextColumn))
                        //visited[Pair(nextRow, nextColumn)] = 1
                    }
                }

            }
            positions = nextItems
            steps -= 1

        }

        println(positions.size)
        //println(visited.keys.size / 2)

    }


    override fun solve2() {
        val grid = getInput()

        var start: Pair<Int, Int> = Pair(0, 0)

        grid.forEachIndexed { row, line ->
            line.forEachIndexed { col, s ->
                if (grid[row][col] == "S") {
                    start = Pair(row, col)
                }
            }
        }

        var positions: HashSet<Pair<Int, Int>> = HashSet()
        val directionRow: List<Int> = listOf(1, -1, 0, 0)
        val directionColumn: List<Int> = listOf(0, 0, -1, 1)
        val visited: HashMap<Pair<Int, Int>, Int> = HashMap()
        var steps: Int = 65 + 131 * 2
        var ans = 0
        positions.add(start)
        var p1 = 0
        val p2: ArrayList<Int> = ArrayList()
        for (step in 0..steps) {
            if (step % 131 == 65) {
                p2.add(positions.size)
            }
            if (step == 64) {
                p1 = positions.size
            }
            val nextItems: HashSet<Pair<Int, Int>> = HashSet()

            for (front in positions) {
                for (i in 0..3) {
                    val nextRow = front.first + directionRow[i]
                    val nextColumn = front.second + directionColumn[i]

                    if (grid[nextRow.mod(grid.size)][nextColumn.mod(grid.size)] != "#") {
                        nextItems.add(Pair(nextRow, nextColumn))
                        //visited[Pair(nextRow, nextColumn)] = 1
                    }
                }

            }
            positions = nextItems

        }


        print(p1)
        print(p2)
        //println(positions.size)
    }
}