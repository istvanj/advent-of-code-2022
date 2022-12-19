package dev.istvanjakab.aoc2022.day07

import dev.istvanjakab.aoc2022.checkResult
import dev.istvanjakab.aoc2022.readInputToStringList
import kotlin.math.absoluteValue

private const val PART1_TEST_CRITERIA = 95437L

private const val PART2_TEST_CRITERIA = 24933642L


private open class FileSystemItem(val name: String, val parent: FileSystemItem?, var size: Long, val contents: MutableList<FileSystemItem>?) {
    override fun toString(): String {
        return "(name='$name', size=$size, contents=$contents)"
    }
}

private class Directory(name: String, parent: FileSystemItem?, size: Long, contents: MutableList<FileSystemItem>) : FileSystemItem(name, parent, size, contents) {
    override fun toString(): String {
        return "Directory${super.toString()}"
    }
}

private class File(name: String, parent: FileSystemItem, size: Long) : FileSystemItem(name, parent, size, null) {
    override fun toString(): String {
        return "File${super.toString()}"
    }
}

private fun main() {
    val testInput = readInputToStringList("day07/Day07_test.txt")
    val testFileSystem: MutableList<FileSystemItem> = mutableListOf(Directory("/", null, 0L, mutableListOf()))
    val testRootDir = testFileSystem[0]
    parseTerminal(testInput, testFileSystem, testRootDir)


    val input = readInputToStringList("day07/Day07.txt")
    val fileSystem: MutableList<FileSystemItem> = mutableListOf(Directory("/", null, 0L, mutableListOf()))
    val rootDir = fileSystem[0]
    parseTerminal(input, fileSystem, rootDir)

    checkResult(PART1_TEST_CRITERIA, part1(testRootDir))
    println(part1(rootDir))
    checkResult(PART2_TEST_CRITERIA, part2(testRootDir))
    println(part2(rootDir))
}

private fun part1(rootDir: FileSystemItem): Long {
    recalculateDirectorySizes(rootDir)
    return findDirectoriesUnderSize(rootDir, 0L)
}

private fun part2(rootDir: FileSystemItem): Long {
    return findDirectoryToDelete(rootDir, (70000000L - rootDir.size - 30000000L).absoluteValue)
}

private fun findDirectoryToDelete(currDir: FileSystemItem, spaceRequired: Long, currentBest: Long = 70000000L): Long {
    var currBest = currentBest
    currDir.contents!!.forEach {
        if (it is Directory) {
            currBest = findDirectoryToDelete(it, spaceRequired, currBest)
        }
    }
    return if (currDir.size in spaceRequired..currBest) currDir.size else currBest
}

private fun findDirectoriesUnderSize(currDir: FileSystemItem, sum: Long, size: Long = 100000L): Long {
    var currSum = sum
    currDir.contents!!.forEach {
        if (it is Directory) {
            currSum = findDirectoriesUnderSize(it, currSum)
        }
    }
    return if (currDir.size <= size) currSum + currDir.size else currSum
}

private fun parseTerminal(input: List<String>, fileSystem: MutableList<FileSystemItem>, currentDirectory: FileSystemItem) {
    var currDir = currentDirectory
    input.forEach {
        if (it.startsWith("$")) {
            if (it.contains("cd")) {
                currDir = when (it.takeLast(2).trim()) {
                    "/" -> {
                        fileSystem[0]
                    }

                    ".." -> {
                        currDir.parent!!
                    }

                    else -> {
                        val newDirectory = Directory(it.last().toString(), currDir, 0L, mutableListOf())
                        currDir.contents!!.add(newDirectory)
                        newDirectory
                    }
                }
            }
        } else {
            val split = it.split(" ")
            val fileSize = split[0].toLongOrNull()
            if (fileSize != null) {
                val file = File(split[1], currDir, fileSize)
                currDir.contents!!.add(file)
                currDir.size += fileSize
            }
        }
    }
}

private fun recalculateDirectorySizes(currDir: FileSystemItem) {
    currDir.size = 0
    currDir.contents!!.forEach {
        if (it is Directory) {
            recalculateDirectorySizes(it)
        }
        currDir.size += it.size
    }
}