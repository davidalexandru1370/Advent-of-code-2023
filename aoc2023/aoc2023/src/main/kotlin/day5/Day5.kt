package day5

import java.io.File
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.min
import kotlin.streams.asStream

data class RangeMap(val destination: Long, val source: Long, val rangeLength: Long)

class Day5 {

    lateinit var seedToSoil: List<RangeMap>
    lateinit var soilToFertilizer: List<RangeMap>
    lateinit var fertilizerToWater: List<RangeMap>
    lateinit var waterToLight: List<RangeMap>
    lateinit var lightToTemperature: List<RangeMap>
    lateinit var temperatureToHumidity: List<RangeMap>
    lateinit var humidityToLocation: List<RangeMap>
    lateinit var seeds: List<Long>

    init {

        val lines: List<String> = File("src/main/kotlin/day5/1.txt").readLines()
        val linesTogether: String = lines.joinToString(separator = "\n")
        seeds = "\\d+".toRegex().findAll(lines[0]).map { it.value.toLong() }.toList()
        seedToSoil = convertToRangeMap(linesTogether, "seed", "soil")
        soilToFertilizer = convertToRangeMap(linesTogether, "soil", "fertilizer")
        fertilizerToWater = convertToRangeMap(linesTogether, "fertilizer", "water")
        waterToLight = convertToRangeMap(linesTogether, "water", "light")
        lightToTemperature = convertToRangeMap(linesTogether, "light", "temperature")
        temperatureToHumidity = convertToRangeMap(linesTogether, "temperature", "humidity")
        humidityToLocation = convertToRangeMap(linesTogether, "humidity", "location")

        //solve1()
        solve2()
    }

    private fun solve2() {
        val result: AtomicLong = AtomicLong(Long.MAX_VALUE)
        seeds
            .chunked(2)
            .map { (start, range) -> start..<(start + range) }
            .forEach { range ->
                range.asSequence().asStream().parallel().forEach { seed ->
                    result.getAndAccumulate(solve(seed), ::min)
                }
            }

        println("Day 5, Part 2: ${result.get()}")
    }

    private fun solve1() {
        var result: Long = Long.MAX_VALUE
        seeds.forEach { seed ->
            result = min(solve(seed), result)
        }

        println("Day 5, Part 1: $result")
    }

    private fun solve(initialSeed: Long): Long {
        var nextSeed: Long = initialSeed
        nextSeed = lookup(nextSeed, seedToSoil)
        nextSeed = lookup(nextSeed, soilToFertilizer)
        nextSeed = lookup(nextSeed, fertilizerToWater)
        nextSeed = lookup(nextSeed, waterToLight)
        nextSeed = lookup(nextSeed, lightToTemperature)
        nextSeed = lookup(nextSeed, temperatureToHumidity)
        nextSeed = lookup(nextSeed, humidityToLocation)

        return nextSeed
    }

    private fun lookup(seed: Long, rangeMap: List<RangeMap>): Long {
        var seeds: List<Long> = listOf()
        for (rm in rangeMap) {
            val (destination, source, rangeLength) = rm
            if ((seed >= source) && seed < (source + rangeLength)) {
                return seed - source + destination
            }
        }

        return seed
    }

    private fun convertToRangeMap(lines: String, from: String, to: String): List<RangeMap> {
        val pattern: String = "${from}-to-${to} map:[\n]((\\d+ \\d+ \\d+)[\n])+"
        return pattern
            .toRegex()
            .find(lines)!!
            .value
            .split("\n")
            .drop(1)
            .mapNotNull { it ->
                if (!it.isEmpty()) {
                    it.split(" ").let { v -> RangeMap(v[0].toLong(), v[1].toLong(), v[2].toLong()) }
                } else null
            }.toList()
    }
}
