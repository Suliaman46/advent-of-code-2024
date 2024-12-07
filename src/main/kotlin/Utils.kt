import java.io.File

fun useFileLines(fileName: String, runnable: (Iterator<String>) -> Unit) {

    File(fileName).useLines { seq ->
        val iter = seq.iterator()
        runnable(iter)
    }
}

fun useFile(fileName: String, runnable: (String) -> Unit) {

    val input = File(fileName).readText(Charsets.UTF_8)

    runnable(input)
}
