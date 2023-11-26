package gitinternals

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

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

        when (type) {
            "blob" -> displayBlob(data)
            "commit" -> displayCommit(data)
            else -> println("Unknown git object type")
        }
    }

    private fun displayBlob(data: String) {
        val content = data.substringAfter("\u0000")
        println("*BLOB*")
        println(content)
    }

    private fun displayCommit(data: String) {
        val payload = data.substringAfter("\u0000")
        val lines = payload.split("\n")
        val headers = lines.takeWhile { it.isNotBlank() }
        val message = lines.dropWhile { it.isNotBlank() }.drop(1).joinToString("\n").trimEnd()

        println("*COMMIT*")

        var tree = ""
        var parentLine = ""
        var author = ""
        var committer = ""
        headers.forEach { line ->
            when {
                line.startsWith("tree") -> tree = line.substringAfter("tree ")
                line.startsWith("parent") -> parentLine += "${line.substringAfter("parent ")} | "
                line.startsWith("author") -> author = formatUserLine(line.substringAfter("author "))
                line.startsWith("committer") -> committer = formatCommiter(line.substringAfter("committer "))
            }
        }

        println("tree: $tree")

        if (parentLine.isNotBlank()) {
            println("parents: ${parentLine.dropLast(3)}")
        }

        println("author: $author")
        println("committer: $committer")
        println("commit message:")
        println(message)
    }


    private fun formatUserLine(line: String): String {
        val parts = line.split(" ")
        val name = parts.takeWhile { !it.contains("@") }.joinToString(" ")
        val email = parts.first { it.contains("@") }.removeSurrounding("<", ">")
        val timestamp = parts[parts.size - 2].toLong()          // Unix timestamp
        val timeZone = parts.last()

        val formattedDate = Instant.ofEpochSecond(timestamp)
            .atOffset(ZoneOffset.of(timeZone.replaceFirst(":", "")))  // Removing ":" to get valid timezone
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX"))
        return "$name $email original timestamp: $formattedDate"
    }

    private fun formatCommiter(line: String): String {
        val parts = line.split(" ")
        val name = parts.takeWhile { !it.contains("@") }.joinToString(" ")
        val email = parts.first { it.contains("@") }.removeSurrounding("<", ">")
        val timestamp = parts[parts.size - 2].toLong()          // Unix timestamp
        val timeZone = parts.last()

        val formattedDate = Instant.ofEpochSecond(timestamp)
            .atOffset(ZoneOffset.of(timeZone.replaceFirst(":", "")))  // Removing ":" to get valid timezone
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX"))
        return "$name $email commit timestamp: $formattedDate"
    }
}
