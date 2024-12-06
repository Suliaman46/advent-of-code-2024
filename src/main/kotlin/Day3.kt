fun main(args: Array<String>) {
    val fileName = "./src/main/resources/day3.txt"

    dayThreePartOne(fileName)
}

fun dayThreePartOne(fileName: String) {

    val pattern = "mul\\((\\d+),(\\d+)\\)"
    val regex = Regex(pattern)

    readFile(fileName) { lines ->
        var total = 0
        for (line in lines) {
            total += regex.findAll(line)
                .map { res -> res.groupValues.subList(1, res.groupValues.size).map { it.toInt() } }
                .map { match -> match.reduce{ acc, next -> acc * next}}
                .reduce {acc, next -> acc + next}
        }

        println("Total Part#1 : $total")
    }
}


