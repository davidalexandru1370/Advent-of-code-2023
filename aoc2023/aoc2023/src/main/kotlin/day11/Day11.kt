package day11

import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Day11 {
    init {
        //solve1()
        solve2()
    }

    private fun solve1() {
        val lines: List<String> = File("src/main/kotlin/day11/1.txt").readLines()

        val linesToExpand: List<Int> = ArrayList()
        val columnsToExpand: List<Int> = ArrayList()
        var planets: List<Pair<Int, Int>> = ArrayList()
        val columns: MutableList<String> = ArrayList<String>().apply {
            repeat(lines[0].length) {
                add("")
            }
        }
        lines.forEachIndexed { lineIndex, line ->
            val containsPlanet: Boolean = line.contains("#")
            if (!containsPlanet) {
                linesToExpand.addLast(lineIndex)
            }
            line.forEachIndexed { index, c ->
                columns[index] = columns[index] + c
            }
        }

        columns.forEachIndexed { index, col ->
            if (!col.contains("#")) {
                columnsToExpand.addLast(index)
            }
        }
        val n: Int = lines.size + linesToExpand.size
        val m: Int = lines[0].length + columnsToExpand.size

        val grid: ArrayList<ArrayList<String>> = ArrayList()

        for (i in 0..<lines.size) {
            if (i in linesToExpand) {
                val nextLine: ArrayList<String> = ArrayList<String>().apply {
                    repeat(m) {
                        add(".")
                    }
                }
                grid.add(ArrayList(nextLine))
                grid.add(ArrayList(nextLine))
                continue
            }
            val nextColumn: ArrayList<String> = ArrayList<String>()
            for (j in 0..<lines[0].length) {
                if (j in columnsToExpand) {
                    nextColumn.add(".")
                    nextColumn.add(".")
                } else {
                    nextColumn.add(lines[i][j].toString())
                }
            }
            grid.add(ArrayList(nextColumn))
        }

        grid.forEachIndexed() { row, line ->
            line.forEachIndexed() { col, c ->
                if (c == "#") {
                    planets.addLast(Pair(row, col))
                    grid[row][col] = planets.size.toString()
                }
            }
        }

        val distances: HashMap<Pair<Int, Int>, Int> = HashMap()

        for (planet in planets) {
            val pairs = bfs(planet, grid)
            pairs.forEach { pair ->
                if (pair.key !in distances) {
                    distances[pair.key] = pair.value
                }
            }
        }
        println(distances.keys.size)
        println(distances.values.sum())

    }

    private fun bfs(planet: Pair<Int, Int>, grid: ArrayList<ArrayList<String>>): HashMap<Pair<Int, Int>, Int> {
        val queue: Queue<Pair<Int, Int>> = LinkedList<Pair<Int, Int>>()
        val visited: HashMap<Pair<Int, Int>, Int> = HashMap<Pair<Int, Int>, Int>()
        val distances: HashMap<Pair<Int, Int>, Int> = HashMap()
        val directionLine = listOf(1, -1, 0, 0)
        val directionCol = listOf(0, 0, 1, -1)
        queue.add(planet)
        val startValue = grid[planet.first][planet.second].toInt()
        val n = grid.size
        val m = grid[0].size
        visited[planet] = 0
        while (!queue.isEmpty()) {
            val current: Pair<Int, Int> = queue.poll()
            val (row, col) = current
            for (i in 0..<4) {
                var nextRow = row + directionLine[i]
                var nextCol = col + directionCol[i]
                if (isInBounds(nextRow, nextCol, n, m) && Pair(nextRow, nextCol) !in visited) {
                    var distance: Int = visited[Pair(row, col)]!! + 1
                    visited[Pair(nextRow, nextCol)] = distance
                    if (grid[nextRow][nextCol].toString().matches("\\d+".toRegex())) {
                        var value: Int = grid[nextRow][nextCol].toInt()
                        if (startValue < value) {
                            distances[Pair(startValue, value)] = distance
                        } else {
                            distances[Pair(value, startValue)] = distance
                        }
                    }
                    queue.add(Pair(nextRow, nextCol))
                }
            }
        }
        return distances
    }

    private fun isInBounds(row: Int, col: Int, n: Int, m: Int): Boolean {
        return row in 0..<n && col in 0..<m
    }

    private fun solve2() {
        val lines: List<String> = File("src/main/kotlin/day11/1.txt").readLines()

        val planets: List<Pair<Int, Int>> = ArrayList()

        val n: Int = lines.size
        val m: Int = lines[0].length
        val grid: ArrayList<ArrayList<String>> = ArrayList<ArrayList<String>>().apply {
            repeat(n) {
                add(ArrayList<String>().apply {
                    addAll(List(n) { "" })
                })
            }
        }

        val noPlanetLines: HashMap<Int, Int> = HashMap()
        val noPlanetColumns: HashMap<Int, Int> = HashMap()

        for (i in 0..<n) {
            if (!lines[i].contains("#")) {
                noPlanetLines[i] = i
            }
            for (j in 0..<m) {
                val value: String = lines[i][j].toString()
                if (value == "#") {
                    planets.addLast(Pair(i, j))
                    grid[i][j] = planets.size.toString()
                } else {
                    grid[i][j] = value
                }
            }
        }

        for (i in 0..<m) {
            val column: StringBuilder = StringBuilder()
            for (j in 0..<n) {
                column.append(lines[j][i])
            }
            if (!column.toString().contains("#")) {
                noPlanetColumns[i] = i
            }
        }

        val distances: HashMap<Pair<Int, Int>, Long> = HashMap()

        for (planet in planets) {
            val pairs = bfsWithCellsMissing(planet, grid, noPlanetLines, noPlanetColumns)
            pairs.forEach { pair ->
                if (pair.key !in distances) {
                    distances[pair.key] = pair.value
                }
            }
        }

        println(distances.keys.size)
        println(distances.values.sum() * 1L)

    }

    private fun bfsWithCellsMissing(
        planet: Pair<Int, Int>,
        grid: ArrayList<ArrayList<String>>,
        noPlanetLines: HashMap<Int, Int>,
        noPlanetColumns: HashMap<Int, Int>
    ): HashMap<Pair<Int, Int>, Long> {
        val queue: Queue<Pair<Int, Int>> = LinkedList<Pair<Int, Int>>()
        val visited: HashMap<Pair<Int, Int>, Long> = HashMap<Pair<Int, Int>, Long>()
        val distances: HashMap<Pair<Int, Int>, Long> = HashMap()
        val directionLine = listOf(1, -1, 0, 0)
        val directionCol = listOf(0, 0, 1, -1)
        queue.add(planet)
        val factor: Int = 1_000_000 - 1
        val startValue = grid[planet.first][planet.second].toInt()
        val n = grid.size
        val m = grid[0].size
        visited[planet] = 0
        while (!queue.isEmpty()) {
            val current: Pair<Int, Int> = queue.poll()
            val (row, col) = current
            for (i in 0..<4) {
                val nextRow = row + directionLine[i]
                val nextCol = col + directionCol[i]
                if (isInBounds(nextRow, nextCol, n, m) && Pair(nextRow, nextCol) !in visited) {
                    var distance: Long = visited[Pair(row, col)]!! + 1

                    if (nextRow in noPlanetLines) {
                        distance += factor
                    }
                    if (nextCol in noPlanetColumns) {
                        distance += factor
                    }
                    visited[Pair(nextRow, nextCol)] = distance
                    if (grid[nextRow][nextCol].toString().matches("\\d+".toRegex())) {
                        var value: Int = grid[nextRow][nextCol].toInt()
                        if (startValue < value) {
                            distances[Pair(startValue, value)] = distance
                        } else {
                            distances[Pair(value, startValue)] = distance
                        }
                    }
                    queue.add(Pair(nextRow, nextCol))
                }
            }
        }
        return distances
    }
}