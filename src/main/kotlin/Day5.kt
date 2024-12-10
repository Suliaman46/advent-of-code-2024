private const val fileName = "./src/main/resources/Day5.txt"
private const val rulePattern = "(\\d+)\\|(\\d+)"

fun main() {
    partOne()
    partTwo()
}

private class Order(val before: Int, val after: Int)


private fun partOne() {
    var total = 0

    useFileLines(fileName) { lines ->
        val (rules, updatePageNums) = parse(lines)
        for (update in updatePageNums) {
            total += getMiddleForCorrectlyOrdered(update, rules)

        }

    }

    println("Total Part#1: $total")


}

private fun parse(lines: Iterator<String>): Pair<MutableList<Order>, MutableList<List<Int>>> {
    val rules = mutableListOf<Order>()
    val updates = mutableListOf<List<Int>>()
    val rulesRegex = Regex(rulePattern)

    var seenNewLine = false

    for (line in lines) {
        if (line == "") {
            seenNewLine = true
            continue
        }

        if (!seenNewLine) {
            val (bef, aft) = rulesRegex.find(line)!!.destructured
            rules.add(Order(bef.toInt(), aft.toInt()))
            continue
        }

        updates.add(line.split(",").map { it.toInt() })
    }
    return Pair(rules, updates)
}

private fun getMiddleForCorrectlyOrdered(update: List<Int>, rules: List<Order>): Int {
    for (pageNum in update.withIndex()) {
        val disallowedPageNums = getDisallowedNums(pageNum.value, rules)
        for (idx in pageNum.index until update.size) {
            if (disallowedPageNums.contains(update[idx])) {
                return 0;
            }
        }
    }
    return update[update.size / 2]
}

private fun getDisallowedNums(aft: Int, rules: List<Order>): List<Int> {
    return rules.filter { it -> it.after == aft }.map { it.before }
}

private fun partTwo() {

}
