package day20

import helpers.IDay
import java.io.File

enum class Pulse {
    Low,
    High
}

abstract class Module(open var fromModule: String, open var toModules: List<String>) {
    abstract fun execute(pulse: Pulse)
}

data class FlipFlop(var isOn: Boolean = false, override var fromModule: String, override var toModules: List<String>) :
    Module(fromModule, toModules) {
    override fun execute(pulse: Pulse) {
        
    }
}

data class Broadcaster(override var fromModule: String, override var toModules: List<String>) :
    Module(fromModule, toModules) {

    override fun execute(pulse: Pulse) {

    }
}

class Day20 : IDay {
    init {
        solve1()
    }

    //    Flip-flop modules (prefix %) are either on or off; they are initially off. If a flip-flop module receives a high pulse,
//    it is ignored and nothing happens. However, if a flip-flop module receives a low pulse, it flips between on and off.
//    If it was off, it turns on and sends a high pulse. If it was on, it turns off and sends a low pulse.
//    Conjunction modules (prefix &) remember the type of the most recent pulse received from each of their connected input modules;
//    they initially default to remembering a low pulse for each input. When a pulse is received, the conjunction module first
//    updates its memory for that input. Then, if it remembers high pulses for all inputs, it sends a low pulse; otherwise, it sends a high pulse.
//    There is a single broadcast module (named broadcaster). When it receives a pulse, it sends the same pulse to all of its destination modules.
    override fun solve1() {
        val lines = File("src/main/kotlin/day20/1.txt").readLines()
            //var broadcast: Module = Module("", listOf())
        lines.forEach { line ->
            if (line.matches("^broadcaster -> .*".toRegex())) {
                val toModules: List<String> = line.split("->")[1].split(",").filter { it.isNotEmpty() }.toList()
                //broadcast = Module("broadcaster", toModules)
            } else {

            }
        }
    }

    override fun solve2() {
        TODO("Not yet implemented")
    }
}