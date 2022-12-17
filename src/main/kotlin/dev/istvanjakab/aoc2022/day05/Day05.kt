package dev.istvanjakab.aoc2022.day05

import dev.istvanjakab.aoc2022.checkResult
import dev.istvanjakab.aoc2022.readInputToString
import kotlin.streams.toList

private const val SPACE_SIZE = 4

private const val CRATE_START_CHAR = '['

private const val PART1_TEST_CRITERIA = "CMZ"

private const val PART2_TEST_CRITERIA = "MCD"

data class Move(val count: Int, val from: Int, val to: Int)

data class PuzzleInput(val crateStacks: List<ArrayDeque<Char>>, val moveList: List<Move>)

fun main() {
    val testInputPart1 = readInput("day05/Day05_test.txt")
    val testInputPart2 = readInput("day05/Day05_test.txt")
    val inputPart1 = readInput("day05/Day05.txt")
    val inputPart2 = readInput("day05/Day05.txt")

    checkResult(PART1_TEST_CRITERIA, part1(testInputPart1))
    println(part1(inputPart1))

    checkResult(PART2_TEST_CRITERIA, part2(testInputPart2))
    println(part2(inputPart2))
}

fun readInput(fileName: String): PuzzleInput{
    val input = readInputToString(fileName)
    val split = input.split("\n\r")
    val crateStacks = getCrateStacks(split[0])
    val moveList = getMoveList(split[1])
    return PuzzleInput(crateStacks, moveList)
}

fun getTopCrates(crateStacks: List<ArrayDeque<Char>>) = crateStacks.map { it.lastOrNull() }.joinToString("")

fun getMoveList(input: String): List<Move> {
    return input.trim().split("\n").map { s ->
        val list = s.split(" ").mapNotNull { it.trim().toIntOrNull() }.toList()
        Move(list[0], list[1], list[2])
    }.toList()
}

fun part1(puzzleInput: PuzzleInput): String {
    val crateStacks = mutableListOf<ArrayDeque<Char>>()
    crateStacks.addAll(puzzleInput.crateStacks)
    puzzleInput.moveList.forEach { procedure ->
        for (i in 0 until procedure.count) {
            val crate = crateStacks[procedure.from - 1].removeLast()
            crateStacks[procedure.to - 1].addLast(crate)
        }
    }
    return getTopCrates(crateStacks)
}

fun part2(puzzleInput: PuzzleInput): String {
    val crateStacks = mutableListOf<ArrayDeque<Char>>()
    crateStacks.addAll(puzzleInput.crateStacks)
    puzzleInput.moveList.forEach { procedure ->
        val crates = crateStacks[procedure.from - 1].takeLast(procedure.count)
        for (i in crates.indices) {
            crateStacks[procedure.from - 1].removeLast()
        }
        crates.forEach {
            crateStacks[procedure.to - 1].addLast(it)
        }
    }
    return getTopCrates(crateStacks)
}

fun getCrateStacks(input: String): List<ArrayDeque<Char>> {
    val lines = input.split("\n")
    val maxStackNumber = getMaxStackNumber(lines.last())
    val stackList = initStackList(maxStackNumber)

    lines.forEach { line ->
        for (i in 0 until maxStackNumber) {
            val charIndex = i * SPACE_SIZE
            try {
                if (line[charIndex] == CRATE_START_CHAR) {
                    stackList[i].addFirst(line[charIndex + 1])
                }
            } catch (ex: StringIndexOutOfBoundsException) {
                // nothing to see here
            }
        }
    }
    return stackList
}

fun initStackList(maxStackNumber: Int): List<ArrayDeque<Char>> {
    val stackList = mutableListOf<ArrayDeque<Char>>()
    for (i in 0 until maxStackNumber) {
        stackList.add(i, ArrayDeque())
    }
    return stackList
}

fun getMaxStackNumber(line: String): Int = line.split(" ").stream().map { it.trim() }.filter { it.isNotEmpty() }.map { it.toInt() }.toList().max()
