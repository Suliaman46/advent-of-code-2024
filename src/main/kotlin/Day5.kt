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

    var total = 0

    useFileLines(fileName) { lines ->
        val (rules, updatePageNums) = parse(lines)
        val illegalUpdates = getIllegalUpdates(updatePageNums, rules)

        val res = illegalUpdates.map {
            it.sortedWith() { a, b ->
                val acsd = rules.filter { it.before == a && it.after == b }.getOrNull(0)
                if (acsd != null) {
                    return@sortedWith -1
                }
                val desc = rules.filter { it.before == b && it.after == a }.getOrNull(0)
                if (desc != null) {
                    return@sortedWith 1
                }
                0
            }
        }

        total = res.fold(0) { acc, next -> acc + next[next.size / 2] }
    }

    println("Total Part#2: $total")
}


private fun getIllegalUpdates(updates: List<List<Int>>, rules: List<Order>): List<List<Int>> {
    return updates.map {
        Pair(it, getMiddleForCorrectlyOrdered(it, rules))
    }.filter { it.second == 0 }.map { it.first }
}
