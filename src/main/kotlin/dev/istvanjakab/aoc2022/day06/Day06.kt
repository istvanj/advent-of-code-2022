package dev.istvanjakab.aoc2022.day06

import dev.istvanjakab.aoc2022.checkResult
import dev.istvanjakab.aoc2022.readInputToString

private const val PART1_TEST_CRITERIA_1 = 7

private const val PART1_TEST_CRITERIA_2 = 11

private const val PART2_TEST_CRITERIA_1 = 19

private const val PART1_DISTINCT_CHAR_COUNT = 4

private const val PART2_DISTINCT_CHAR_COUNT = 14

private const val PART2_TEST_CRITERIA_2 = 26


fun main() {
    val testInput1 = readInputToString("day06/Day06_test1.txt")
    val testInput2 = readInputToString("day06/Day06_test2.txt")
    val input = readInputToString("day06/Day06.txt")

    checkResult(PART1_TEST_CRITERIA_1, part1(testInput1))
    checkResult(PART1_TEST_CRITERIA_2, part1(testInput2))
    println(part1(input))

    checkResult(PART2_TEST_CRITERIA_1, part2(testInput1))
    checkResult(PART2_TEST_CRITERIA_2, part2(testInput2))
    println(part2(input))
}

private fun part1(input: String) = findMarker(input, PART1_DISTINCT_CHAR_COUNT)

private fun part2(input: String) = findMarker(input, PART2_DISTINCT_CHAR_COUNT)

private fun findMarker(input: String, distinctCharCount: Int): Int {
    return input.windowedSequence(distinctCharCount, 1).indexOfFirst { string ->
        val charArray = string.toCharArray()
        charArray.all { c -> charArray.count { it == c } == 1 }
    } + distinctCharCount
}