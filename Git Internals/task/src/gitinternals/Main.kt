package gitinternals

fun main() {
    val userInputReader = ConsoleUserInputReader()
    val fileReader = NioFileReader()
    val dataInflater = ZipInflater()
    val dataConverter = DefaultDataConverter()

    try {
        val fileBytes = fileReader.readBytes(userInputReader.readUserInput().objectLocation)
        val result = dataInflater.inflate(fileBytes)
        val output = dataConverter.convert(result)

        output.split("\u0000").forEach { println(it) }
    } catch (e: Exception) {
        e.printStackTrace()
        println("An error occurred: ${e.message}")
    }
}
