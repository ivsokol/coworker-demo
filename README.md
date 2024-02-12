Steps to reproduce bug:

1. Refresh gradle dependencies
2. run gradle test - all should pass
3. replace line 12 in build.gradle.kts from 8.2.7 to 8.4.1
4. refresh gradle dependencies
4.1 check if correct version pulled in gradle dependencies
   
5. run tests again, it should fail with java.lang.NoSuchMethodError

DMN picked from https://github.com/camunda/camunda-bpm-examples/blob/master/dmn-engine/dmn-engine-drg/src/main/resources/org/camunda/bpm/example/drg/dinnerDecisions.dmn

