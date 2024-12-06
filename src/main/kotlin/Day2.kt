import kotlin.math.abs

fun main(args: Array<String>) {
    val fileName = "./src/main/resources/day2.txt"

    partOne(fileName)
}

private fun partOne(fileName: String) {
    val delimiter = " "
    var numOfSafeReports = 0

    readFile (fileName){ lines ->
        for (line in lines) {
            val report = line.split(delimiter).map { it.toInt() }
            if (isValid(report)) {
                numOfSafeReports++
            }
        }
    }

    println("Total Part#1 : $numOfSafeReports")

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

