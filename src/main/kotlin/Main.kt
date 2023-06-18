import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.file
import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.grammar.tryParseToEnd
import com.github.h0tk3y.betterParse.parser.ErrorResult
import com.github.h0tk3y.betterParse.parser.ParseException
import com.github.h0tk3y.betterParse.parser.Parsed
import com.github.h0tk3y.betterParse.parser.parseToEnd
import java.io.File


class RdfSurfaceToFol : CliktCommand() {
    override fun run() = Unit
}

class Transform : CliktCommand(help = "Transform RDF Surface graph to first-order formula in TPTP format") {
    val ignoreQuerySurface by option("--ignoreQuerySurface", "-iqs").flag(
        "--includeQuerySurface",
        "-qs",
        default = false
    )
    val path by argument(help = "Path to the RDF Surface graph").file()

    val rdfSurfaceToFOLController = RDFSurfaceToFOLController()

    override fun run() {
        val rdfSurfaceGraph = path.readText()

        val (parseError, parseResultValue) = rdfSurfaceToFOLController.transformRDFSurfaceGraphToFOL(
            rdfSurfaceGraph,
            ignoreQuerySurface
        )

        val outputString =
            if (parseError) ("Failed to parse " + path.name + ":\n" + parseResultValue + "\n") else parseResultValue

        println(outputString)
    }
}

class Check : CliktCommand() {
    val axiomFile by argument(
        name = "PATH_TO_RDF_SURFACE_GRAPH",
        help = "Path of the file with the RDF Surface graph"
    ).file()
    val conjectureFile by argument(
        name = "PATH_TO_CONCLUSIONS",
        help = "Path of the file with the expected solution"
    ).file().optional()
    val vampireExecFile by option(help = "Path of the vampire execution file").file()
        .default(File("/home/rebekka/Programs/Vampire-qa/tmp/build/bin/"))
    val short by option("--short", "-s", help = "Short output").flag(default = false)

    val rdfSurfaceToFOLController = RDFSurfaceToFOLController()

    override fun run() {

        val computedAnswerFile =
            conjectureFile ?: File(axiomFile.parentFile.path + "/" + axiomFile.nameWithoutExtension + "-answer.n3")

        val graph = axiomFile.readText()
        val answerGraph = computedAnswerFile.readText()

        val (parseError, parseResultValue) = rdfSurfaceToFOLController.transformRDFSurfaceGraphToFOL(
            graph,
            ignoreQuerySurface = true
        )

        //TODO("beetle7.n3: fol query?")
        val (answerParseError, answerParseResultValue) = rdfSurfaceToFOLController.transformRDFSurfaceGraphToFOLConjecture(
            answerGraph
        )

        val errorMessage =
            if (parseError) ("Failed to parse " + axiomFile.name + ":\n" + parseResultValue + "\n") else "" + if (answerParseError) ("Failed to parse " + (computedAnswerFile.name) + ":\n" + answerParseResultValue + "\n") else ""

        if (parseError or answerParseError) {
            if (!short) println(errorMessage)
            println(axiomFile.name + "  --->  " + "Transforming Error")
            return
        }

        if (!short) println("Transformation was successful!")

        File("TransformationResults/").mkdir()
        val file = File("TransformationResults/" + axiomFile.nameWithoutExtension + ".p")
        file.writeText("$parseResultValue\n$answerParseResultValue")
        val absolutePath = file.absolutePath

        if (!short) {
            println("Problem output file: " + file.path)
            println("Starting Vampire...")
        }

        val vampireProcess = "./vampire_z3_rel_qa_6176 --output_mode smtcomp $absolutePath".runCommand(vampireExecFile)

        vampireProcess?.waitFor()

        val vampireResultString = vampireProcess?.inputStream?.reader()?.readText()?.lines()

        if (vampireResultString == null) {
            println(axiomFile.name + "  --->  " + "Vampire Error")
            return
        }

        if (!short) {
            vampireResultString.drop(1).forEach { println(it) }
        }

        println(axiomFile.name + "  --->  " + vampireResultString.drop(1).dropLastWhile
        { it.isBlank() }
            .joinToString(separator = " --- "))
    }
}

class CheckWithVampireQueryAnswering : CliktCommand() {
    val axiomFile by argument(
        name = "PATH_TO_RDF_SURFACE_GRAPH",
        help = "Path of the file with the RDF Surface graph"
    ).file()
    val vampireExecFile by option(help = "Path of the vampire execution file").file()
        .default(File("/home/rebekka/Programs/Vampire-qa/tmp/build/bin/"))
    val short by option("--short", "-s", help = "Short output").flag(default = false)

    val casc by option("--casc", "-c").flag(default = false)

    val rdfSurfaceToFOLController = RDFSurfaceToFOLController()
    override fun run() {

        val cascCommand = if (casc) " --mode casc" else ""

        val graph = axiomFile.readText()

        val (parseError, parseResultValue) = rdfSurfaceToFOLController.transformRDFSurfaceGraphToFOL(
            graph,
            false
        )

        val resultString =
            if (parseError) ("Failed to parse " + axiomFile.name + ":\n" + parseResultValue + "\n") else ""

        if (parseError) {
            if (!short) println(resultString)
            println(axiomFile.name + "  --->  " + "Transforming Error")
            return
        }

        if (!short) println("Transformation was successful!")

        File("TransformationResults/").mkdir()
        val file = File("TransformationResults/" + axiomFile.nameWithoutExtension + ".p")
        file.writeText("$parseResultValue")
        val absolutePath = file.absolutePath

        if (!short) {
            println("Problem output file: " + file.path)
            println("Starting Vampire...")
        }

        println("./vampire_z3_rel_qa_6176 -av off -qa answer_literal $absolutePath -qa_io on$cascCommand")

        val vampireProcess =
            "./vampire_z3_rel_qa_6176 -av off -qa answer_literal $absolutePath -qa_io on$cascCommand".runCommand(vampireExecFile)

        vampireProcess?.waitFor()

        val vampireParsingResult = vampireProcess?.inputStream?.reader()?.useLines {
            val vampireQuestionResults = it.last().trimStart { char -> (char != '[') }.trimEnd { char -> char != ']' }
            return@useLines when (val parserResult = VampireQuestionAnsweringResultsParser.tryParseToEnd(vampireQuestionResults)) {
                is Parsed -> {
                    if (parserResult.value.first.isEmpty() && parserResult.value.second.isEmpty()) {
                        "No results"
                    } else {
                        parserResult.value.second.let { list ->
                            return@let if (list.isEmpty()) "" else list.joinToString(
                                separator = "\n  -  ",
                                prefix = "Or Results:\n  -  "
                            )
                        } + parserResult.value.first.let { list ->
                            return@let if (list.isEmpty()) "" else list.joinToString(
                                separator = "\n  -  ",
                                prefix = "\nResults:\n  -  "
                            )
                        }
                    }
                }
                is ErrorResult -> vampireQuestionResults
            }
        }
        println(vampireParsingResult)
    }
}

fun String.runCommand(workingDir: File): Process? {
    return ProcessBuilder(*split(" ").toTypedArray())
        .directory(workingDir)
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()
}

fun main(args: Array<String>) =
    RdfSurfaceToFol().subcommands(Transform(), Check(), CheckWithVampireQueryAnswering()).main(args)