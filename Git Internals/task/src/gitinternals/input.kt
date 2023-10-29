package gitinternals

/**
 * Created with IntelliJ IDEA.
$ Project: Git Internals
 * User: rodrigotroy
 * Date: 28-10-23
 * Time: 17:56
 */

data class Stage1UserInput(val objectLocation: String)
data class Stage2UserInput(val directoryPath: String, val objectHash: String)

interface Stage1UserInputReader {
    fun readUserInput(): Stage1UserInput
}

interface Stage2UserInputReader {
    fun readUserInput(): Stage2UserInput
}

class ConsoleUserInputReader : Stage1UserInputReader {
    override fun readUserInput(): Stage1UserInput {
        println("Enter git object location:")
        val filePath = readlnOrNull()

        if (filePath.isNullOrEmpty()) {
            println("No input provided")
            return readUserInput();
        }

        return Stage1UserInput(filePath)
    }
}

class Stage2ConsoleUserInputReader : Stage2UserInputReader {
    override fun readUserInput(): Stage2UserInput {
        println("Enter .git directory location:")
        val directoryPath = readlnOrNull() ?: throw IllegalArgumentException("Directory path required")

        println("Enter git object hash:")
        val objectHash = readlnOrNull() ?: throw IllegalArgumentException("Object hash required")

        return Stage2UserInput(directoryPath, objectHash)
    }
}
