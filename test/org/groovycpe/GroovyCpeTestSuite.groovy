package org.groovycpe
import groovy.util.GroovyTestSuite
import junit.framework.TestSuite

public class GroovyCpeTestSuite extends TestSuite {
     // Since Eclipse launches tests relative to the project root,
     // declare the relative path to the test scripts for convenience
     private static final String TEST_ROOT = "test/org/groovycpe/";
     public static TestSuite suite() throws Exception {
         TestSuite suite = new TestSuite();
         GroovyTestSuite gsuite = new GroovyTestSuite();         
         suite.addTestSuite(gsuite.compile(TEST_ROOT + "CpeActionTest.groovy"));
         suite.addTestSuite(gsuite.compile(TEST_ROOT + "CpeServerTest.groovy"));         
         return suite;
     }
}