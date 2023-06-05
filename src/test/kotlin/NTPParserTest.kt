import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.parser.parseToEnd
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import java.io.File

class NTPParserTest : ShouldSpec(
    {

        afterTest {
            N3sToFolParser.resetBlankNodeCounter()
        }

        should("transform example2.n3 without exception"){
            val file = File("src/test/resources/turtle/example2.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example2.p")
            println(N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())))
            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }

        should("transform example3.n3 without exception"){
            val file = File("src/test/resources/turtle/example3.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example3.p")
            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }
        should("transform example4.n3 without exception"){
            val file = File("src/test/resources/turtle/example4.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example4.p")
            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }
        should("transform example5.n3 without exception"){
            val file = File("src/test/resources/turtle/example5.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example5.p")
            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }
        should("transform example6.n3 without exception"){
            val file = File("src/test/resources/turtle/example6.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example6.p")
            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }
        should("transform example7.n3 without exception"){
            val file = File("src/test/resources/turtle/example7.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example7.p")
            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }
        should("transform example8.n3 without exception"){
            val file = File("src/test/resources/turtle/example8.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example8.p")
            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }
        should("transform example9.n3 without exception"){
            val file = File("src/test/resources/turtle/example9.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example9.p")
            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }
        should("transform example10.n3 without exception"){
            val file = File("src/test/resources/turtle/example10.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example10.p")
            println(N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())))
            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }
        //TODO("Multiline Support, multiple ' in TPTP Format")
        should("transform example11.n3 without exception"){
            val file = File("src/test/resources/turtle/example11.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example11.p")
            println(N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())))
            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }
        should("transform example12.n3 without exception"){
            val file = File("src/test/resources/turtle/example12.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example12.p")
            println(N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())))
            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }
        should("transform example13.n3 without exception"){
            val file = File("src/test/resources/turtle/example13.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example13.p")
            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }
        should("transform example14.n3 without exception"){
            val file = File("src/test/resources/turtle/example14.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example14.p")
            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }
        should("transform example15.n3 without exception"){
            val file = File("src/test/resources/turtle/example15.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example15.p")
            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }
        should("transform example16.n3 without exception"){
            val file = File("src/test/resources/turtle/example16.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example16.p")
            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()

        }
        should("transform example17.n3 without exception"){
            val file = File("src/test/resources/turtle/example17.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example17.p")
            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }
        should("transform example18.n3 without exception"){
            val file = File("src/test/resources/turtle/example18.n3")
            //val solutionFile = File("src/test/resources/turtle-fol/example18.p")
            println(N3sToFolParser.parseToEnd(file.readText()))
        }
        should("transform example19.n3 without exception"){
            val file = File("src/test/resources/turtle/example19.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example19.p")
            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }
        should("transform example20.n3 without exception"){
            val file = File("src/test/resources/turtle/example20.n3")
            //val solutionFile = File("src/test/resources/turtle-fol/example20.p")
            println(N3sToFolParser.parseToEnd(file.readText()))
        }
        should("transform example21.n3 without exception"){
            val file = File("src/test/resources/turtle/example21.n3")
            //N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()

            println(N3sToFolParser.parseToEnd(file.readText()))
        }
        should("transform example22.n3 without exception"){
            val file = File("src/test/resources/turtle/example22.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example22.p")

            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }
        should("transform example23.n3 without exception"){
            val file = File("src/test/resources/turtle/example23.n3")
            //val solutionFile = File("src/test/resources/turtle-fol/example23.p")

            println(N3sToFolParser.parseToEnd(file.readText()))
        }
        should("transform example24.n3 without exception"){
            val file = File("src/test/resources/turtle/example24.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example24.p")

            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }
        should("transform example25.n3 without exception"){
            val file = File("src/test/resources/turtle/example25.n3")
            //val solutionFile = File("src/test/resources/turtle-fol/example25.p")

            println(N3sToFolParser.parseToEnd(file.readText()))
        }
        should("transform example26.n3 without exception"){
            val file = File("src/test/resources/turtle/example26.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example26.p")

            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }
        should("transform example27.n3 without exception"){
            val file = File("src/test/resources/turtle/example27.n3")
            val solutionFile = File("src/test/resources/turtle-fol/example27.p")

            N3sToFolParser.createFofAnnotatedAxiom(N3sToFolParser.parseToEnd(file.readText())) shouldBeEqualComparingTo solutionFile.readText()
        }
        should("transform example28.n3 without exception"){
            val file = File("src/test/resources/turtle/example28.n3")
            println(N3sToFolParser.parseToEnd(file.readText()))
        }


        context("Literals"){
            should("not throw exception"){
                println(N3sToFolParser.iri.parseToEnd(N3sToFolParser.tokenizer.tokenize("show:123")))
            }
            should("not throw exception"){
                println(N3sToFolParser.literal.parseToEnd(N3sToFolParser.tokenizer.tokenize("2")))
            }
            should("not throw exception"){
                println(N3sToFolParser.literal.parseToEnd(N3sToFolParser.tokenizer.tokenize("2")))
            }
        }

    }


)