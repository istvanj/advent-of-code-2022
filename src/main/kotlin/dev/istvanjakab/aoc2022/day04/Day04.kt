package dev.istvanjakab.aoc2022.day04

import dev.istvanjakab.aoc2022.checkResult
import dev.istvanjakab.aoc2022.readInputToStringList

private const val PART1_TEST_CRITERIA = 2

private const val PART2_TEST_CRITERIA = 4

private data class ElfPair(val firstElfSectionSet: Set<Int>, val secondElfSectionSet: Set<Int>)

fun main() {
    val testInput = readInput("day04/Day04_test.txt")
    val input = readInput("day04/Day04.txt")

    checkResult(PART1_TEST_CRITERIA, part1(testInput))
    println(part1(input))

    checkResult(PART2_TEST_CRITERIA, part2(testInput))
    println(part2(input))
}

private fun readInput(input: String): List<ElfPair> {
    return readInputToStringList(input).map {
        val split = it.split(",")
        Pair(split[0], split[1])
    }.map {
        val firstElfSectionSet = toSectionNumbers(it.first)
        val secondElfSectionSet = toSectionNumbers(it.second)
        ElfPair(firstElfSectionSet, secondElfSectionSet)
    }.toList()
}

private fun toSectionNumbers(ranges: String): Set<Int> {
    val split = ranges.split("-").map { it.toInt() }
    return (split[0]..split[1]).toSet()
}

private fun part1(elfPairList: List<ElfPair>) = elfPairList.count {
    val intersect = it.firstElfSectionSet.intersect(it.secondElfSectionSet)
    intersect.containsAll(it.firstElfSectionSet) || intersect.containsAll(it.secondElfSectionSet)
}

private fun part2(elfPairList: List<ElfPair>) = elfPairList.count { it.firstElfSectionSet.intersect(it.secondElfSectionSet).isNotEmpty() }