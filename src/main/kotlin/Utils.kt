import java.io.File

fun readFile(fileName: String, runnable: (Iterator<String>) -> Unit) {

    File(fileName).useLines { seq ->
        val iter = seq.iterator()
        runnable(iter)
    }
}