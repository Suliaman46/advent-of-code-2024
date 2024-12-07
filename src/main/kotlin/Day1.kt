import java.io.File
import kotlin.math.abs

private const val fileName = "./src/main/resources/day1.txt"
private const val delimiter = "   "

fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    val leftNumbers = mutableListOf<Int>()
    val rightNumbers = mutableListOf<Int>()

    File(fileName).useLines { it ->
        val lineIterator = it.iterator()
        while (lineIterator.hasNext()) {
            val line = lineIterator.next()

            val nums = line.split(delimiter).map { str -> str.toInt() }

            leftNumbers.add(nums[0])
            rightNumbers.add(nums[1])
        }
    }

    leftNumbers.sort()
    rightNumbers.sort()

    var total = 0
    for (idx in leftNumbers.indices) {
        total += abs(leftNumbers[idx] - rightNumbers[idx])
    }

    println("Total Part#1 : $total")
}

private fun partTwo() {
    val numberOccurrences = mutableMapOf<Int, Occurrence>()

    val lines = File(fileName).useLines {
        it.toList()
    }

    for (line in lines) {
        val nums = line.split(delimiter).map { str -> str.toInt() }
        putOrIncrementLeft(numberOccurrences, nums[0])
    }

    for (line in lines) {
        val nums = line.split(delimiter).map { str -> str.toInt() }
        incrementRightIfPresent(numberOccurrences, nums[1])
    }

    var total = 0
    for ((number, occur) in numberOccurrences) {
        total += (number * occur.rightOccur) * occur.leftOccur
    }

    println("Total Part#2 : $total")
}

data class Occurrence(var leftOccur: Int, var rightOccur: Int)

fun putOrIncrementLeft(occurrences: MutableMap<Int, Occurrence>, key: Int) {
    val num = occurrences[key]

    if (num == null) {
        occurrences[key] = Occurrence(1, 0)
        return
    }

    num.leftOccur += 1
}

fun incrementRightIfPresent(occurrences: MutableMap<Int, Occurrence>, num: Int) {
    val num = occurrences[num]

    if (num != null) {
        num.rightOccur += 1
    }
}



