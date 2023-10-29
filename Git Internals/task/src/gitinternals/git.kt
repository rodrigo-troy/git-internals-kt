package gitinternals

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Created with IntelliJ IDEA.
$ Project: Git Internals
 * User: rodrigotroy
 * Date: 29-10-23
 * Time: 18:47
 */
class GitObjectDataReader {
    fun readObjectData(userInput: Stage2UserInput): ByteArray {
        val objectPath = getObjectPath(userInput.directoryPath, userInput.objectHash)
        return Files.readAllBytes(objectPath)
    }

    private fun getObjectPath(directoryPath: String, objectHash: String): Path {
        return Paths.get(directoryPath, "objects", objectHash.substring(0, 2), objectHash.substring(2))
    }
}

class GitObjectDataDisplayer {
    fun display(data: String) {
        val type = data.substringBefore(' ')
        val length = data.drop(type.length + 1).substringBefore("\u0000")
        println("type:$type length:$length")
    }
}
