private const val fileName = "./src/main/resources/Day6.txt"
private const val obstruction = '#'


private enum class Dir(val char: Char) {
    UP('^'),
    DOWN('v'),
    RIGHT('>'),
    LEFT('<');

    companion object {
        val fromChar: Map<Char, Dir> = entries.associateBy { it.char }
        fun valueFrom(c: Char): Dir = fromChar[c]!!
    }

    fun rotateRight(): Dir {
        return when (this) {
            UP -> RIGHT
            DOWN -> LEFT
            RIGHT -> DOWN
            LEFT -> UP
        }
    }
}

private data class Coord(val x: Int, val y: Int) {
    fun move(dirToMov: Dir): Coord {
        return when (dirToMov) {
            Dir.UP -> Coord(x - 1, y)
            Dir.DOWN -> Coord(x + 1, y)
            Dir.RIGHT -> Coord(x, y + 1)
            Dir.LEFT -> Coord(x, y - 1)
        }
    }
}

fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    val (map, start) = parse()
    val dim = Pair(map.size, map[0].size)

    val visitedCoordinates = mutableMapOf(start to 1)
    var currentPos = start
    var currentDir = Dir.valueFrom(map[start.x][start.y])
    while (true) {

        currentPos = currentPos.move(currentDir)


        addToVisited(visitedCoordinates, currentPos)

        val nextCoord = currentPos.move(currentDir)
        if (isOutOfBound(nextCoord, dim)) {
            break
        }

        val nextCoordValue = get(map, nextCoord)
        if (isObstacle(nextCoordValue)) {
            currentDir = currentDir.rotateRight()
        }

    }

    println("Total Part#1: ${visitedCoordinates.size}")

}

private fun addToVisited(visitedCoord: MutableMap<Coord, Int>, currentPos: Coord) {
    visitedCoord[currentPos] = visitedCoord.getOrPut(currentPos) { 0 } + 1
}


private fun parse(): Pair<List<CharArray>, Coord> {
    val map = mutableListOf<CharArray>()
    var guardLocation = Coord(-1, -1)

    useFileLines(fileName) { lines ->
        for ((i, line) in lines.withIndex()) {
            val arr = CharArray(line.length)
            for (j in line.indices) {
                arr[j] = line[j]
                if (isGuard(line[j])) {
                    guardLocation = Coord(i, j)
                }
            }
            map.add(arr)
        }
    }

    return Pair(map, guardLocation)
}

private fun get(map: List<CharArray>, coordinate: Coord): Char {
    return map[coordinate.x][coordinate.y]
}

private fun isOutOfBound(coordinate: Coord, dim: Pair<Int, Int>): Boolean {
    return coordinate.x < 0 || coordinate.x >= dim.first || coordinate.y < 0 || coordinate.y >= dim.second
}

fun isGuard(char: Char): Boolean {
    return Dir.entries.map { it.char }.contains(char)
}

fun isObstacle(char: Char): Boolean {
    return char == obstruction
}

private fun partTwo() {

}
