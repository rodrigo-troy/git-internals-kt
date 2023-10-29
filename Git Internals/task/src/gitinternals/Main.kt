package gitinternals

fun main() {
    val userInputReader = Stage2ConsoleUserInputReader()
    val fileReader = GitObjectDataReader()
    val dataInflater = ZipInflater()
    val dataConverter = DefaultDataConverter()
    val displayer = GitObjectDataDisplayer()

    try {
        val fileBytes = fileReader.readObjectData(userInputReader.readUserInput())
        val result = dataInflater.inflate(fileBytes)
        val output = dataConverter.convert(result)
        displayer.display(output)
    } catch (e: Exception) {
        e.printStackTrace()
        println("An error occurred: ${e.message}")
    }
}
