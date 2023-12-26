package day25

import helpers.IDay
import java.io.File
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.math.min

data class Edge(val to: String, var capacity: Int)


class Day25 : IDay {
    private lateinit var parent: HashMap<String, String>

    init {
        solve1()
    }

    private fun getInput(): HashMap<String, HashSet<Edge>> {
        val graph: HashMap<String, HashSet<Edge>> = HashMap()
        File("src/main/kotlin/day25/1.txt")
            .readLines()
            .forEach { line ->
                val nodes = line.split(":")
                val first = nodes[0]
                if (first !in graph) {
                    graph[first] = HashSet()
                }
                nodes[1]
                    .split("\\s".toRegex())
                    .filter { it.isNotEmpty() }
                    .forEach { node ->
                        graph[first]!!.add(Edge(node, 1))
                        if (node !in graph) {
                            graph[node] = HashSet()
                        }
                        graph[node]!!.add(Edge(first, 1))
                    }
            }
        return graph
    }

    private fun bfs(
        source: String,
        target: String,
        graph: HashMap<String, HashSet<Edge>>
    ): Boolean {
        val queue: Queue<String> = LinkedList()
        graph.keys.forEach {
            this.parent[it] = ""
        }
        parent[source] = source

        queue.add(source)

        while (queue.isNotEmpty()) {
            val node = queue.poll()
            graph[node]!!.forEach { neighbour ->
                if (parent[neighbour.to] == "" && neighbour.capacity > 0) {
                    parent[neighbour.to] = node
                    queue.add(neighbour.to)
                }
            }
        }

        return this.parent[target]!! != ""
    }

    private fun fordFulkersonMinCut(source: String, target: String, graph: HashMap<String, HashSet<Edge>>): Int {
        graph.entries.forEach {
            it.value.forEach {
                it.capacity = 1
            }
        }

        var maxFlow: Int = 0

        while (bfs(source, target, graph)) {
            var flow: Int = Int.MAX_VALUE
            var nodeIterator = target
            while (nodeIterator != source) {
                val parent = this.parent[nodeIterator]!!
                flow = min(flow, graph[parent]!!.first { it.to == nodeIterator }.capacity)
                nodeIterator = parent
            }

            nodeIterator = target
            maxFlow += flow

            while (nodeIterator != source) {
                val parent = this.parent[nodeIterator]!!
                graph[parent]!!.first { it.to == nodeIterator }.capacity -= flow
                graph[nodeIterator]!!.first { it.to == parent }.capacity += flow
                nodeIterator = parent
            }
        }
        return maxFlow

    }

    override fun solve1() {
        val graph = getInput()
        this.parent = HashMap()

        val start = graph.keys.first()

        for (target in graph.keys) {
            if (start != target && fordFulkersonMinCut(start, target, graph) == 3) {
                break
            }
        }

        val length = parent.filter { it.value.isNotEmpty() }.size

        println(length * (parent.size - length))
    }

    override fun solve2() {
        TODO("Not yet implemented")
    }
}