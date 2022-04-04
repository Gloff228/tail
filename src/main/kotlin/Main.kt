import kotlinx.cli.*
import java.io.File

fun main(args: Array<String>) {
    val parser = ArgParser("tail")
    val extractSymbols by parser.option(
        ArgType.Int,
        shortName = "c",
        description = "Number of symbols for output. Cannot be used with -n"
    )
    val extractStrings by parser.option(
        ArgType.Int,
        shortName = "n",
        description = "Number of strings for output. Cannot be used with -c"
    )
    val output by parser.option(ArgType.String, shortName = "o", fullName = "out", description = "Output file")
        .default("console")
    val input by parser.argument(ArgType.String, description = "Input file").vararg()

    parser.parse(args)

    when {
        extractSymbols != null && extractStrings != null -> throw IllegalArgumentException("Can't use both -c and -n at the same time")
        extractSymbols == null && extractStrings == null -> extractingStrings(input, output, 10)
        extractSymbols != null -> extractingSymbols(input, output, extractSymbols!!)
        extractStrings != null -> extractingStrings(input, output, extractStrings!!)
    }
}

fun extractingSymbols(inputFile: List<String>, outputFile: String, count: Int) {
    val list = mutableListOf<String>()
    var isFile = true
    for (file in inputFile) {
        var lines: List<String>

        if (file.substring(file.length - 4, file.length) != ".txt"){
            isFile = false
            lines = file.split("\\n")
            if (inputFile.size > 1) list.add("---cmd String---")
        } else {
            if (!isFile) throw IllegalArgumentException("Can't be txt files and cmd text at the same time")
            if (inputFile.size > 1) list.add("---$file---")
            lines = File(file).readLines()
        }

        var string = ""
        for (line in lines.reversed()) {
            string = line + string
            if (string.length >= count) {
                string = string.substring(string.length - count, string.length)
                break
            }
        }
        list.add(string)
    }
    output(list, outputFile)
}

fun extractingStrings(inputFile: List<String>, outputFile: String, count: Int) {
    val list = mutableListOf<String>()
    var isFile = true
    for (file in inputFile) {
        var lines: List<String>

        if (file.substring(file.length - 4, file.length) != ".txt"){
            isFile = false
            lines = file.split("\\n")
            if (inputFile.size > 1) list.add("---cmd String---")
        } else {
            if (!isFile) throw IllegalArgumentException("Can't be txt files and cmd text at the same time")
            if (inputFile.size > 1) list.add("---$file---")
            lines = File(file).readLines()
        }

        var countForFile = count
        val strings =lines
        if (count >= strings.size) countForFile = strings.size
        for (line in (strings.size - countForFile) until strings.size) {
            list.add(strings[line])
        }
    }
    output(list, outputFile)
}

fun output(list: List<String>, outputFile: String) {
    if (outputFile == "console") {
        for (line in list) println(line)
    } else {
        val writer = File(outputFile).bufferedWriter()
        for (line in list) {
            writer.write(line)
            writer.newLine()
        }
        writer.close()
    }
}