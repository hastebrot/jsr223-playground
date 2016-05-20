package jsr223.groovy.scripts

import org.junit.Test
import static org.junit.Assert.assertEquals

class MyClassTest {

    @Test
    void testSuccess() {
        assertEquals(4, 2 + 2)
    }
    
    @Test
    void testFail() {
        assertEquals(5, 2 + 2)
    }
    
}
