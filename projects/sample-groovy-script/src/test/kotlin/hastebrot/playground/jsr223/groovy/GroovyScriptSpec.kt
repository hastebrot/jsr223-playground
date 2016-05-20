package hastebrot.playground.jsr223.groovy

import com.winterbe.expekt.expect
import groovy.lang.GroovyObject
import groovy.lang.GroovyShell
import io.damo.kspec.Spec
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

class GroovyScriptSpec : Spec({

    var shell: GroovyShell? = null

    val fixturesPath = "src/test/resources/jsr223/groovy/scripts"

    before {
        shell = GroovyShell()
    }

    test("script evaluate") {
        // expect:
        expect(shell!!.evaluate("2 + 3")).to.equal(5)
    }

    test("script import") {
        // when:
        shell!!.classLoader.parseClass(File(fixturesPath, "ScriptImport2.groovy"))
        val result = shell!!.run(File(fixturesPath, "ScriptImport1.groovy"), emptyArray())
                as GroovyObject

        // then:
        expect(result.getProperty("name")).to.equal("bar")
    }

    test("script output") {
        // when:
        val outputStream = ByteArrayOutputStream()
        shell!!.context.setProperty("out", PrintStream(outputStream, true))
        shell!!.evaluate(File(fixturesPath, "ScriptOutput.groovy").readText())

        // then:
        val newLine = String.format("%n")
        expect(outputStream.toString()).to.equal("foo" + newLine)
    }

})
