package jsr223.groovy.scripts

class HelloSpock extends spock.lang.Specification {
    
    def "success"() {
        expect:
        2 + 2 == 4
    }
    
    def "fail"() {
        expect:
        2 + 2 == 5
    }
    
}
