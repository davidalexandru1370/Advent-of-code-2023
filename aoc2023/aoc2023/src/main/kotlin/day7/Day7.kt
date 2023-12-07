package day7

import java.io.File

class Day7 {
    init {
        solve1()
    }

    private enum class CardType {
        HighCard, OnePair, TwoPair, ThreeOfAKind, FullHouse, FourOfAKind, FiveOfAKind
    }

    private fun getCardType(card: String): CardType {
        val elements: MutableMap<Char, Int> = HashMap<Char, Int>().withDefault { 0 }

        card.forEach { letter ->
            val currentValue: Int = elements.getOrDefault(letter, 0)
            elements[letter] = currentValue + 1
        }

        if (elements.size == 5) {
            return CardType.HighCard
        }

        val pattern = elements.entries.map { it.value }.sortedDescending().joinToString(separator = "")

        when (pattern) {
            "5" -> {
                return CardType.FiveOfAKind
            }

            "41" -> {
                return CardType.FourOfAKind
            }

            "32" -> {
                return CardType.FullHouse
            }

            "311" -> {
                return CardType.ThreeOfAKind
            }

            "221" -> {
                return CardType.TwoPair
            }

            else -> {
                return CardType.OnePair
            }
        }
    }


    private fun solve1() {
        val lines: List<String> = File("src/main/kotlin/day7/1.txt").readLines()
        val cards: List<Pair<String, Long>> = ArrayList()
        val cardTypes: MutableMap<String, CardType> = HashMap()
        lines.forEach { line ->
            val (card, bid) = line.split(" ")
            cards.addLast(Pair(card, bid.toLong()))
            cardTypes[card] = getCardType(card)
        }

        //cardTypes.entries.forEach { println("${it.key} -> ${it.value}") }
        val orderForLetter = mapOf(
            'A' to 10,
            'K' to 9,
            'Q' to 8,
            'J' to 7,
            'T' to 6
        )

        val customComparator = Comparator<Pair<String, Long>> { o1, o2 ->
            if (cardTypes[o1.first] == cardTypes[o2.first]) {
                var value: Int = 0
                for (index in 0..<o1.first.length) {
                    if (o1.first[index].toString().matches("[A-Z]".toRegex())
                        && o2.first[index].toString().matches("[A-Z]".toRegex())
                        && o1.first[index] != o2.first[index]
                    ) {
                        var first = orderForLetter.get(o1.first[index])
                        var second = orderForLetter.get(o2.first[index])
                        value = first!!.compareTo(second!!)
                        break
                    } else if (o1.first[index] != o2.first[index]) {
                        value = o1.first[index].compareTo(o2.first[index])
                        break
                    }
                }
                value
            } else {
                cardTypes[o1.first]!!.compareTo(cardTypes[o2.first]!!)
            }
        }

        println(cards.sortedWith(customComparator))
        val sum: Long =
            cards.sortedWith(customComparator)
                .foldIndexed(0L) { index, acc, element -> acc + element.second * (index + 1) }

        println(sum)
    }
}