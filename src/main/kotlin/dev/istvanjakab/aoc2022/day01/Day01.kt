package dev.istvanjakab.aoc2022.day01

import dev.istvanjakab.aoc2022.checkResult
import dev.istvanjakab.aoc2022.readInputToStringList

private const val PART1_TEST_CRITERIA = 24000

private const val PART2_TEST_CRITERIA = 45000

private const val TOP_N = 3

/**
 * [Description](https://adventofcode.com/2022/day/1)
 */
fun main() {
    val testInput = readInputToStringList("day01/Day01_test.txt")
    val part1TestResult = part1(testInput)
    checkResult(PART1_TEST_CRITERIA, part1TestResult)

    val puzzleInput = readInputToStringList("day01/Day01.txt")
    println(part1(puzzleInput))

    val part2TestResult = part2(testInput)
    checkResult(PART2_TEST_CRITERIA, part2TestResult)
    println(part2(puzzleInput))
}

private fun part1(input: List<String>): Int {
    return sumCaloriesList(input).max()
}

private fun part2(input: List<String>): Int {
    return sumCaloriesList(input).sortedDescending().take(TOP_N).sum()
}

private fun sumCaloriesList(input: List<String>): List<Int> {
    val sumCalories = mutableListOf<Int>()
    var sum = 0
    var index = 0

    sumCalories.add(index, 0)
    for (s in input) {
        if (s.isNotBlank()) {
            sum += s.toInt()
            sumCalories[index] = sum
        } else {
            sum = 0
            index++
            sumCalories.add(index, 0)
        }
    }
    return sumCalories
}
