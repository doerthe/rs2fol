import com.github.h0tk3y.betterParse.parser.ParseException
import parser.RDFSurfacesParser
import rdfSurfaces.QuerySurface
import util.RDFSurfacesParseException
import util.TransformerException


public sealed class RdfSurfacesResult

public class Success(val value: String, val querySurfaces: List<QuerySurface>? = null) : RdfSurfacesResult()

public class Failure(val failureMessage: String) : RdfSurfacesResult()

class RDFSurfaceToFOLController {

    private val generalParseErrorString = "Please check the syntax of your RDF surfaces graph."

    fun transformRDFSurfaceGraphToFOL(
        rdfSurfaceGraph: String,
        ignoreQuerySurface: Boolean,
        rdfLists: Boolean = false,
        verbose: Boolean = false,
    ): RdfSurfacesResult {
        return try {
            val parserResult = RDFSurfacesParser(rdfLists).parseToEnd(rdfSurfaceGraph)
            Success(
                Transformer().toFOL(parserResult, ignoreQuerySurface),
                parserResult.getQuerySurfaces()
            )
        } catch (exc: Exception) {
            when (exc) {
                is ParseException -> Failure(if (verbose) exc.stackTraceToString() else generalParseErrorString)
                is RDFSurfacesParseException, is TransformerException -> Failure(
                    if (verbose) exc.stackTraceToString() else (exc.message ?: exc.toString())
                )

                else -> throw exc
            }
        }
    }

    fun transformRDFSurfaceGraphToFOLConjecture(
        rdfSurfaceGraph: String,
        rdfLists: Boolean = false,
        verbose: Boolean = false,
    ): RdfSurfacesResult {
        return try {
            val parserResult = RDFSurfacesParser(rdfLists).parseToEnd(rdfSurfaceGraph)
            Success(
                Transformer().toFOL(parserResult, false, "conjecture", "conjecture"),
                null
            )
        } catch (exc: Exception) {
            when (exc) {
                is ParseException -> Failure(if (verbose) exc.stackTraceToString() else generalParseErrorString)
                is RDFSurfacesParseException, is TransformerException -> Failure(
                    if (verbose) exc.stackTraceToString() else (exc.message ?: exc.toString())
                )

                else -> throw exc
            }
        }
    }

    fun transformRDFSurfaceGraphToNotation3(
        rdfSurfaceGraph: String,
        rdfLists: Boolean = false,
        verbose: Boolean = false,
    ): RdfSurfacesResult {
        return try {
            val parserResult = RDFSurfacesParser(rdfLists).parseToEnd(rdfSurfaceGraph)
            Success(Transformer().toNotation3Sublanguage(parserResult), parserResult.getQuerySurfaces())

        } catch (exc: Exception) {
            when (exc) {
                is ParseException -> Failure(if (verbose) exc.stackTraceToString() else generalParseErrorString)
                is RDFSurfacesParseException, is TransformerException -> Failure(
                    if (verbose) exc.stackTraceToString() else (exc.message ?: exc.toString())
                )

                else -> throw exc
            }
        }
    }
}