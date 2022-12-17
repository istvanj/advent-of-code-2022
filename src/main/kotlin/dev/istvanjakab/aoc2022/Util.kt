package dev.istvanjakab.aoc2022

import java.io.File

const val ROOT_PACKAGE = "main/kotlin/dev/istvanjakab/aoc2022"

data class NotFoundException(override val message: String?) : Exception(message)

fun <T> checkResult(expected: T, actual: T) = check(expected == actual) { "Expected value to be $expected but was $actual" }

fun readInputToStringList(inputFileName: String) = File("src", "$ROOT_PACKAGE/$inputFileName").readLines()

fun readInputToString(inputFileName: String) = File("src", "$ROOT_PACKAGE/$inputFileName").readText()