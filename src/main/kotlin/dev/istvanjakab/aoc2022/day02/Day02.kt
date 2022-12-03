package dev.istvanjakab.aoc2022.day02

import dev.istvanjakab.aoc2022.checkResult
import dev.istvanjakab.aoc2022.readInputToStringList

const val PART1_CRITERIA = 15

const val PART2_CRITERIA = 12

data class Shape(val shapeCodePair: Pair<String, String>, val scoreValue: Int)

data class RoundPart1(val opponentShape: String, val playerShape: String)

data class RoundPart2(val opponentShape: String, val desiredOutcomeCode: String)

data class ShapeNotFoundException(override val message: String?) : Exception(message)

val rock = Shape(Pair("A", "X"), 1)
val paper = Shape(Pair("B", "Y"), 2)
val scissors = Shape(Pair("C", "Z"), 3)

val shapeList = listOf(rock, paper, scissors)

fun main() {
    val testInputPart1 = readInputToRoundPart1List("day02/Day02_test.txt")

    checkResult(PART1_CRITERIA, part1(testInputPart1))

    println(part1(readInputToRoundPart1List("day02/Day02.txt")))

    val testInputPart2 = readInputToRoundPart2List("day02/Day02_test.txt")

    checkResult(PART2_CRITERIA, part2(testInputPart2))

    println(part2(readInputToRoundPart2List("day02/Day02.txt")))

}

fun readInputToRoundPart1List(inputFileName: String): List<RoundPart1> {
    return readInputToStringList(inputFileName).map {
        val split = it.split(" ")
        RoundPart1(split.first(), split.last())
    }
}

fun readInputToRoundPart2List(inputFileName: String): List<RoundPart2> {
    return readInputToStringList(inputFileName).map {
        val split = it.split(" ")
        RoundPart2(split.first(), split.last())
    }
}

fun part1(input: List<RoundPart1>): Int {
    var score = 0
    input.forEach {
        score += calculateOutcome(it) + findShapeByCode(it.playerShape).scoreValue
    }
    return score
}

fun part2(input: List<RoundPart2>): Int {
    var score = 0
    input.forEach {
        score += desiredOutcomeScore(it.desiredOutcomeCode) + findShapeByCode(shapeToPlay(it)).scoreValue
    }
    return score
}

fun findShapeByCode(shapeCode: String): Shape {
    val foundShape = shapeList.find { shape -> shape.shapeCodePair.toList().any { it == shapeCode } }
    if (foundShape != null) return foundShape else throw ShapeNotFoundException("Invalid shapeCode $shapeCode")
}

fun calculateOutcome(round: RoundPart1): Int {

    var result = -1

    when (round.opponentShape) {
        "A" -> {
            when (round.playerShape) {
                "X" -> result = 3
                "Y" -> result = 6
                "Z" -> result = 0
            }
        }

        "B" -> {
            when (round.playerShape) {
                "X" -> result = 0
                "Y" -> result = 3
                "Z" -> result = 6
            }
        }

        "C" -> {
            when (round.playerShape) {
                "X" -> result = 6
                "Y" -> result = 0
                "Z" -> result = 3
            }
        }
    }

    return result

}

fun desiredOutcomeScore(desiredOutcomeCode: String): Int {
    var desiredOutcomeScore = -1
    when (desiredOutcomeCode) {
        "X" -> desiredOutcomeScore = 0
        "Y" -> desiredOutcomeScore = 3
        "Z" -> desiredOutcomeScore = 6
    }
    return desiredOutcomeScore
}

fun shapeToPlay(round: RoundPart2): String {
    var shapeToPlay = " "

    when (round.opponentShape) {
        "A" -> {
            when (round.desiredOutcomeCode) {
                "X" -> shapeToPlay = "C"
                "Y" -> shapeToPlay = "A"
                "Z" -> shapeToPlay = "B"
            }
        }

        "B" -> {
            when (round.desiredOutcomeCode) {
                "X" -> shapeToPlay = "A"
                "Y" -> shapeToPlay = "B"
                "Z" -> shapeToPlay = "C"
            }
        }

        "C" -> {
            when (round.desiredOutcomeCode) {
                "X" -> shapeToPlay = "B"
                "Y" -> shapeToPlay = "C"
                "Z" -> shapeToPlay = "A"
            }
        }
    }

    return shapeToPlay
}

