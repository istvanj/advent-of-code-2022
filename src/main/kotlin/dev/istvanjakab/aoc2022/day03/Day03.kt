package dev.istvanjakab.aoc2022.day03

import dev.istvanjakab.aoc2022.NotFoundException
import dev.istvanjakab.aoc2022.checkResult
import dev.istvanjakab.aoc2022.readInputToStringList
import kotlin.streams.toList

private const val PART1_TEST_CRITERIA = 157

private const val PART2_TEST_CRITERIA = 70

private const val CAPITAL_LETTER_DIFF_TO_CHAR_CODE = -38

private const val SMALL_LETTER_DIFF_TO_CHAR_CODE = -96

fun main() {
    val testInput = readInputToStringList("day03/Day03_test.txt")
    val puzzleInput = readInputToStringList("day03/Day03.txt")
    checkResult(PART1_TEST_CRITERIA, part1(testInput))
    println(part1(puzzleInput))

    checkResult(PART2_TEST_CRITERIA, part2(testInput))
    println(part2(puzzleInput))
}

private fun part1(input: List<String>): Int {
    var sumOfMisplacedItemPriorities = 0
    input.forEach { s ->
        val list = s.chunked(s.length / 2).stream().map { it.toCharArray() }.toList()
        sumOfMisplacedItemPriorities += findMisplacedItem(list[0], list[1]).getPriority()
    }
    return sumOfMisplacedItemPriorities
}

private fun findMisplacedItem(compartment1: CharArray, compartment2: CharArray): Char {
    compartment1.forEach {
        if (compartment2.contains(it)) {
            return it
        }
    }
    throw NotFoundException("Misplaced item not found.")
}

private fun part2(input: List<String>): Int {
    var sumOfBadgePriorities = 0
    input.windowed(3, 3).forEach { groupRucksacks ->
        groupRucksacks[0].toCharArray().run groupRucksacks@{
            forEach {
                if (groupRucksacks[1].contains(it) && groupRucksacks[2].contains(it)) {
                    sumOfBadgePriorities += it.getPriority()
                    return@groupRucksacks
                }
            }
        }
    }
    return sumOfBadgePriorities
}

private fun Char.getPriority(): Int {
    return if (this.isLowerCase()) {
        this.code + SMALL_LETTER_DIFF_TO_CHAR_CODE
    } else if (this.isUpperCase()) {
        this.code + CAPITAL_LETTER_DIFF_TO_CHAR_CODE
    } else 0
}
