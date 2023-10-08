package gitinternals

import java.nio.file.Files
import java.nio.file.Paths
import java.util.zip.Inflater

fun main() {
    println("Enter git object location:")
    var filePath = readlnOrNull()

    try {
        val fileBytes = Files.readAllBytes(Paths.get(filePath))

        val inflater = Inflater()
        inflater.setInput(fileBytes)

        val result = ByteArray(1000)
        val resultLength = inflater.inflate(result)
        inflater.end()

        val output = String(result, 0, resultLength)
        output.split("\u0000").forEach { println(it) }

    } catch (e: Exception) {
        e.printStackTrace()
        println("An error occurred: ${e.message}")
    }
}
