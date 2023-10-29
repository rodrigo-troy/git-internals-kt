package gitinternals

import java.util.zip.Inflater

interface DataInflater {
    fun inflate(input: ByteArray): InflationResult
}

data class InflationResult(val data: ByteArray, val length: Int)

class ZipInflater(private val inflater: Inflater = Inflater()) : DataInflater {

    override fun inflate(input: ByteArray): InflationResult {
        return runCatching {
            inflater.run {
                setInput(input)
                val decompressedData = ByteArray(1000)  // Consider dynamic sizing based on input
                val decompressedDataLength = inflate(decompressedData)
                end()
                InflationResult(decompressedData.copyOfRange(0, decompressedDataLength), decompressedDataLength)
            }
        }.getOrElse {
            inflater.end()
            throw InflationException("Failed to inflate data", it)
        }
    }
}

class InflationException(message: String, cause: Throwable) : Exception(message, cause)
