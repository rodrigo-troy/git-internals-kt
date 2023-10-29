package gitinternals

/**
 * Created with IntelliJ IDEA.
$ Project: Git Internals
 * User: rodrigotroy
 * Date: 28-10-23
 * Time: 17:56
 */

data class UserInput(val objectLocation: String)

interface UserInputReader {
    fun readUserInput(): UserInput
}

class ConsoleUserInputReader : UserInputReader {
    override fun readUserInput(): UserInput {
        println("Enter git object location:")
        val filePath = readlnOrNull()

        if (filePath.isNullOrEmpty()) {
            println("No input provided")
            return readUserInput();
        }

        return UserInput(filePath)
    }
}
