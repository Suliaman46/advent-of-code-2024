import kotlin.math.abs

private const val fileName = "./src/main/resources/day2.txt"
private const val delimiter = " "

fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    var numOfSafeReports = 0

    useFileLines(fileName) { lines ->
        for (line in lines) {
            val report = line.split(delimiter).map { it.toInt() }
            if (isValid(report)) {
                numOfSafeReports++
            }
        }
    }

    println("Total Part#1 : $numOfSafeReports")

}

private fun partTwo() {
    var numOfSafeReports = 0

    useFileLines(fileName) { lines ->
        for (line in lines) {
            val report = line.split(delimiter).map { it.toInt() }
            if (isValid(report)) {
                numOfSafeReports++
            } else {
                numOfSafeReports += dropElmAndCheckIfValid(report)
            }
        }
    }

    println("Total Part#2 : $numOfSafeReports")
}

fun dropElmAndCheckIfValid(report: List<Int>): Int {
    for (idx in report.indices) {
        if (dropElmAndCheckIfValid(report, idx)) {
            return 1
        }
    }

    return 0
}

fun dropElmAndCheckIfValid(report: List<Int>, idx: Int): Boolean {
    val temp = report.toMutableList()
    temp.removeAt(idx)

    return isValid(temp)
}


fun isValid(report: List<Int>): Boolean {
    var isAsc = false

    if (report.size > 1) {
        if (report[1] > report[0]) {
            isAsc = true
        }
    }


    for (i in 1 until report.size) {
        val diff = abs(report[i] - report[i - 1])

        if (diff < 1 || diff > 3) {
            return false
        }

        if (isAsc) {
            if (report[i] < report[i - 1]) {
                return false
            }

        } else {
            if (report[i] > report[i - 1]) {
                return false
            }
        }
    }

    return true
}

