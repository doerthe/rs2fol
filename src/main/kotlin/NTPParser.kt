import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.grammar.parser
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken
import com.github.h0tk3y.betterParse.parser.Parser

object N3sToFolParser : Grammar<String?>() {

    val lpar by literalToken("(")
    val rpar by literalToken(")")
    val lparcurl by literalToken("{")
    val rparcurl by literalToken("}")
    val simpleSpace by literalToken(" ", ignore = true)
    val space by regexToken("\\s*", ignore = true)
    val alspace by regexToken("\\s+", ignore = true)
    val dot by literalToken(".")
    val semicolon by literalToken(";")
    val comma by literalToken(",")

    val negativeSurfaceIRI by literalToken("<http://www.w3.org/2000/10/swap/log#onNegativeSurface>")
    val positiveSurfaceIRI by literalToken("<http://www.w3.org/2000/10/swap/log#onPositiveSurface>")

    val variable by regexToken("_:\\w+")
    val v by variable use { this.text.drop(2).replaceFirstChar { it.uppercaseChar() } }

    val literal by regexToken("\\w+")
    val l by literal use {this.text}

    val emptyvarList by (lpar and rpar) use { "" }
    val variableList by emptyvarList or (-lpar and oneOrMore(v and (0..1 times space)) and -rpar).map {
        it.joinToString(
            separator = ","
        ) { tuple2 -> tuple2.t1 }
    }

    val ressource by regexToken("^<\\w*:[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*>")
    val r by ressource use { "'" + this.text + "'" }

    //    val triple by (v or r) and -simpleSpace and (v or r) and -simpleSpace and (v or r) use { "triple(" + this.t1 + ", " + this.t2 + ", " + this.t3 + ")" }
    val triple by (v or r or l) and -simpleSpace and (v or r or l) and -simpleSpace and (v or r or l)

    val tripleComb by triple and zeroOrMore(
        -optional(space) and -semicolon and -optional(space) and (v or r or l) and -optional(
            space
        ) and (v or r or l)
    ) map { (triple, list) ->
        if (list.isEmpty()) {
            createTripleString(str1 = triple.t1, str2 = triple.t2, str3 = triple.t3)
        } else {
            "(" + createTripleString(str1 = triple.t1, str2 = triple.t2, str3 = triple.t3) + " & " + list.joinToString(
                separator = " & "
            ) { (pred, obj) -> createTripleString(triple.t1, pred, obj) } + ")"
        }
    }


    val N3sToFolParser: Parser<String> by
    oneOrMore(
        (variableList and
                -optional(alspace) and
                (negativeSurfaceIRI or positiveSurfaceIRI) and
                -optional(alspace) and
                -lparcurl and
                -optional(alspace) and
                (parser(this::N3sToFolParser) or optional(alspace).use { "\$true" }) and
                -optional(alspace) and
                -rparcurl) map { (variableList, surface, rest) ->
            val variableListNotNull = variableList.isNotEmpty()
            var outputString = ""
            if (surface.type == positiveSurfaceIRI) {
                if (variableListNotNull) {
                    outputString = "? [$variableList] : "
                }
            } else {
                if (variableListNotNull) {
                    outputString = "! [$variableList] : "
                }
                outputString = "$outputString~"
            }
            outputString += rest
            outputString
        } or
                tripleComb
                and -optional(dot)) use { this.joinToString(prefix = "(", postfix = ")", separator = " & ") }

    override val rootParser: Parser<String> by N3sToFolParser use { this }

    private fun createTripleString(str1: String, str2: String, str3: String): String = "triple($str1,$str2,$str3)"
}