import java.lang.IllegalStateException

private const val mulPattern = "mul\\((\\d+),(\\d+)\\)"
private const val fileName = "./src/main/resources/day3.txt"

fun main() {
    partOne()
    partTwo()
}

private fun partOne() {

    val regex = Regex(mulPattern)

    useFileLines(fileName) { lines ->
        var total = 0
        for (line in lines) {
            total += regex.findAll(line)
                .map { res -> res.groupValues.subList(1, res.groupValues.size).map { it.toInt() } }
                .map { match -> match.reduce { acc, next -> acc * next } }
                .reduce { acc, next -> acc + next }
        }

        println("Total Part#1 : $total")
    }
}

private fun partTwo() {

    val mulRegex = Regex(mulPattern)
    val enablerRegex = Regex("do\\(\\)")
    val disablerRegex = Regex("don't\\(\\)")

    useFile(fileName) { input ->
        var total = 0

        val mulMatches = mulRegex.findAll(input).toList()
        val enablerIndices = enablerRegex.findAll(input).map { it -> it.range.last }.toList()
        val disablerIndices = disablerRegex.findAll(input).map { it -> it.range.last }.toList()

        for (mul in mulMatches) {
            if (isEnabled(mul.range.first, enablerIndices, disablerIndices)) {
                total += mul.groupValues.subList(1, mul.groupValues.size).map { it.toInt() }
                    .reduce { acc, next -> acc * next }
            }
        }
        println("Total Part#2 : $total")
    }
}


fun isEnabled(mulIdx: Int, enablerIndices: List<Int>, disablerIndices: List<Int>): Boolean {
    val closestDisabler = largestNumSmallerThan(disablerIndices, mulIdx)
    when (closestDisabler) {
        -1 -> return true

        -2 -> throw IllegalStateException("Something went wrong with finding closest idx of enabler/disabler")

        else -> {
            val closestEnabler = largestNumSmallerThan(enablerIndices, mulIdx)

            return closestEnabler > closestDisabler
        }
    }
}

fun largestNumSmallerThan(arr: List<Int>, num: Int): Int {
    //arr is sorted and num does not exist in it
    //binary search

    var low = 0
    var high = arr.size
    var idx: Int

    if (num < arr.first()) {
        // num is too small
        return -1
    }

    if (num > arr.last()) {
        return arr.last()
    }


    while (high != low) {
        idx = (high + low) / 2

        if (arr[idx] > num) {
            high = idx
            continue
        }

        if (arr[idx + 1] > num) {
            return arr[idx]
        }
        low = idx
    }

    // something went wrong
    return -2
}

