package gitinternals

/**
 * Created with IntelliJ IDEA.
$ Project: Git Internals
 * User: rodrigotroy
 * Date: 29-10-23
 * Time: 18:39
 */
interface DataConverter {
    fun convert(result: InflationResult): String
}

class DefaultDataConverter : DataConverter {
    override fun convert(result: InflationResult): String {
        return String(result.data, 0, result.length)
    }
}
