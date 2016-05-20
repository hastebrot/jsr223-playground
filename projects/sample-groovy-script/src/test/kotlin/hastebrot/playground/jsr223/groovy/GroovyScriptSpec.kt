package hastebrot.playground.jsr223.groovy

import com.winterbe.expekt.expect
import groovy.lang.GroovyObject
import groovy.lang.GroovyShell
import io.damo.kspec.Spec
import io.damo.kspec.expectException
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

    test("script arguments") {
        // when:
        val resultMain = shell!!.run(File(fixturesPath, "ScriptArgs.groovy"), emptyArray())

        // then:
        expect(resultMain).to.equal(null)

        // when:
        val cls = shell!!.classLoader.parseClass(File(fixturesPath, "ScriptArgs.groovy"))
        val resultObject = cls.declaredConstructors.first()
                .newInstance(mapOf("foo" to "inky", "invalid" to "blinky")) as GroovyObject

        // then:
        expect(resultObject.getProperty("foo")).to.equal("inky")
        expect(resultObject.getProperty("bar")).to.equal(null)
    }

    test("script exception") {
        // expect:
        expectException(RuntimeException::class, "script" ){
            shell!!.run(File(fixturesPath, "ScriptException.groovy"), emptyArray())
        }
    }

    test("script junit") { }

    test("script spock") { }

})
