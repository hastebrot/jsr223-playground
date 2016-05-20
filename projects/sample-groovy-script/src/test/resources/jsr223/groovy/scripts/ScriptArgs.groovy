package jsr223.groovy.scripts

class ScriptArgs {
    
    String foo
    String bar
    
    public ScriptArgs(Map<String, Object> args) {
        this.foo = args["foo"]
        this.bar = args["bar"]
    }
    
    String toString() {
        return "args: [foo: ${foo}, bar: ${bar}]"
    }

    static void main(String[] args) {
        def script = new ScriptArgs(foo: "inky", bar: "blinky")
        println script.toString()
    }
        
}
