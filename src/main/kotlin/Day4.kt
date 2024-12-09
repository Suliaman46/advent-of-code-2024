private const val fileName = "./src/main/resources/Day4.txt"
private val MMSS = listOf('M', 'M', 'S', 'S')
fun main() {
    partOne()
    partTwo()
}

private data class Coordinate(var x: Int, var y: Int)
private data class MLocation(val coord: Coordinate, val dir: Direction)
private data class Dimension(val length: Int, val width: Int)
private enum class Direction {
    TOP_LEFT, TOP, TOP_RIGHT, RIGHT, BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT, LEFT
}


private fun partOne() {
    useFileLines(fileName) { lines ->
        val (puzzle, xLocations) = readMap(lines, 'X')
        var total = 0

        for (xLoc in xLocations) {
            val neighbours = neighbours(xLoc, Dimension(puzzle.size, puzzle[0].size))

            val neighbouringMs = neighbours.filter { coord -> puzzle[coord.x][coord.y] == 'M' }
                .map { MLocation(it, resolveDirection(xLoc, it)) }

            for (m in neighbouringMs) {
                if (wordFoundInDirection(puzzle, m.coord, m.dir)) {
                    total += 1
                }
            }
        }

        println("Total Part#1 : $total")
    }

}

private fun readMap(lines: Iterator<String>, char: Char): Pair<List<CharArray>, List<Coordinate>> {
    val map = mutableListOf<CharArray>()
    val xLocations = mutableListOf<Coordinate>()

    for ((i, line) in lines.withIndex()) {
        val arr = CharArray(line.length)
        for (j in line.indices) {
            arr[j] = line[j]
            if (line[j] == char) {
                xLocations.add(Coordinate(i, j))
            }
        }
        map.add(arr)
    }

    return Pair(map, xLocations)
}

private fun wordFoundInDirection(map: List<CharArray>, coord: Coordinate, direction: Direction): Boolean {
    // 'AS' to be found
    val aCoordinate = nextCoordinate(coord, direction, Dimension(map.size, map[0].size)) ?: return false

    if (map[aCoordinate.x][aCoordinate.y] != 'A') {
        return false
    }

    val sCoordinate = nextCoordinate(aCoordinate, direction, Dimension(map.size, map[0].size)) ?: return false

    return map[sCoordinate.x][sCoordinate.y] == 'S'
}


private fun nextCoordinate(coord: Coordinate, direction: Direction, dim: Dimension): Coordinate? {
    return when (direction) {
        Direction.TOP_LEFT -> getOrNull(coord.x - 1, coord.y - 1, dim)
        Direction.TOP -> getOrNull(coord.x - 1, coord.y, dim)
        Direction.TOP_RIGHT -> getOrNull(coord.x - 1, coord.y + 1, dim)
        Direction.RIGHT -> getOrNull(coord.x, coord.y + 1, dim)
        Direction.BOTTOM_RIGHT -> getOrNull(coord.x + 1, coord.y + 1, dim)
        Direction.BOTTOM -> getOrNull(coord.x + 1, coord.y, dim)
        Direction.BOTTOM_LEFT -> getOrNull(coord.x + 1, coord.y - 1, dim)
        Direction.LEFT -> getOrNull(coord.x, coord.y - 1, dim)
    }
}

private fun getOrNull(x: Int, y: Int, dim: Dimension): Coordinate? {

    if (isOutOfBound(x, y, dim)) {
        return null
    }

    return Coordinate(x, y)
}

private fun resolveDirection(start: Coordinate, end: Coordinate): Direction {
    //End is in upper row
    if (end.x < start.x) {

        //End is top Left
        if (end.y < start.y) {
            return Direction.TOP_LEFT
        }

        if (end.y == start.y) {
            return Direction.TOP
        }

        return Direction.TOP_RIGHT
    }

    //End is in the row below
    if (end.x > start.x) {

        //End is top Right
        if (end.y > start.y) {
            return Direction.BOTTOM_RIGHT
        }

        if (end.y == start.y) {
            return Direction.BOTTOM
        }

        return Direction.BOTTOM_LEFT
    }

    //Same x / row
    if (end.y > start.y) {
        return Direction.RIGHT
    }

    if (end.y < start.y) {
        return Direction.LEFT
    }

    //Should be all states already but to make the compiler happy
    return Direction.LEFT

}


private fun neighbours(coordinate: Coordinate, dim: Dimension): List<Coordinate> {
    val neighbours = mutableListOf<Coordinate>()

    val addends = listOf(0, -1, 1)

    for (xAddend in addends) {
        for (yAddend in addends) {
            if (xAddend == 0 && yAddend == 0) {
                continue
            }

            if (isOutOfBound(coordinate.x + xAddend, coordinate.y + yAddend, dim)) {
                continue
            }

            neighbours.add(Coordinate(coordinate.x + xAddend, coordinate.y + yAddend))
        }
    }

    return neighbours
}

private fun isOutOfBound(x: Int, y: Int, dim: Dimension): Boolean {

    return x < 0 || x >= dim.length || y < 0 || y >= dim.width
}

private fun partTwo() {
    useFileLines(fileName) { lines ->
        val (puzzle, aLocations) = readMap(lines, 'A')
        var total = 0

        val seenAs = mutableMapOf<Coordinate, Int>()

        for (aLoc in aLocations) {
            val neighbours = diagonalNeighbours(aLoc, Dimension(puzzle.size, puzzle[0].size))

            if (neighbours.size == 4) {
                if (isXMas(neighbours, puzzle)) {
                    total++
                    seenAs[aLoc] = 1
                }

            }
        }
        println("Total Part#2 : $total")
    }

}

private fun isXMas(neighbours: List<Coordinate>, puzzle: List<CharArray>): Boolean {
    val diagonalChars = neighbours
        .map { puzzle[it.x][it.y] }
        .sorted()


    val first = neighbours[0]
    val opp = neighbours.filter { (it.x - first.x != 0) && (it.y - first.y != 0) }[0]

    val oppAreNotTheSame = puzzle[first.x][first.y] != puzzle[opp.x][opp.y]

    return oppAreNotTheSame && (diagonalChars == MMSS)
}

private fun diagonalNeighbours(coordinate: Coordinate, dim: Dimension): List<Coordinate> {
    val neighbours = mutableListOf<Coordinate>()

    val addends = listOf(-1, 1)

    for (xAddend in addends) {
        for (yAddend in addends) {
            if (isOutOfBound(coordinate.x + xAddend, coordinate.y + yAddend, dim)) {
                continue
            }
            neighbours.add(Coordinate(coordinate.x + xAddend, coordinate.y + yAddend))
        }
    }

    return neighbours
}




