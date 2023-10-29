package gitinternals

import java.nio.file.Files
import java.nio.file.Paths

/**
 * Created with IntelliJ IDEA.
$ Project: Git Internals
 * User: rodrigotroy
 * Date: 29-10-23
 * Time: 18:00
 */
interface FileReader {
    fun readBytes(filePath: String): ByteArray
}

class NioFileReader : FileReader {
    override fun readBytes(filePath: String): ByteArray {
        val path = Paths.get(filePath)
        return Files.readAllBytes(path)
    }
}
