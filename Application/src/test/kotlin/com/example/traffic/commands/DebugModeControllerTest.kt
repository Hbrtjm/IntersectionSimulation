//package com.example.traffic.commands
//
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import java.lang.reflect.Field
//import java.util.*
//
//
//
//class DebugModeControllerTest {
//
//    // Hack credit to: Edward Campbell, posted @https://stackoverflow.com/questions/318239/how-do-i-set-environment-variables-from-java, by pushy https://stackoverflow.com/users/663130/pushy
//    @Throws(Exception::class)
//    private fun setEnv(newenv: Map<String, String>?) {
//        try {
//            // Attempt to modify environment variables for Unix-like environments
//            val processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment")
//            val theEnvironmentField: Field = processEnvironmentClass.getDeclaredField("theEnvironment")
//            theEnvironmentField.isAccessible = true
//            val env = theEnvironmentField.get(null) as MutableMap<String, String>
//            env.putAll(newenv!!)
//
//            // Attempt to modify the case-insensitive environment variables for Windows
//            val theCaseInsensitiveEnvironmentField: Field =
//                processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment")
//            theCaseInsensitiveEnvironmentField.isAccessible = true
//            val cienv = theCaseInsensitiveEnvironmentField.get(null) as MutableMap<String, String>
//            cienv.putAll(newenv)
//        } catch (e: NoSuchFieldException) {
//            // Fall back to modifying System.getenv() on older JDK versions
//            val classes = Collections::class.java.declaredClasses
//            val env = System.getenv()
//            for (cl in classes) {
//                if ("java.util.Collections\$UnmodifiableMap" == cl.name) {
//                    val field: Field = cl.getDeclaredField("m")
//                    field.isAccessible = true
//                    val obj: Any = field.get(env)
//                    val map = obj as MutableMap<String, String>
//                    map.clear()
//                    map.putAll(newenv!!)
//                }
//            }
//        }
//    }
//
//    @BeforeEach
//    fun setUp() {
//        try {
//            setEnv(mapOf("DEBUG_MODE" to "true"))
//        } catch (e: Exception) {
//            // Handle the exception properly by throwing it again or fail the test
//            throw RuntimeException("Test setup failed: $e")
//        }
//    }
//
//    @Test
//    fun isDebugModeOnTest() {
//        // Assert that the environment variable is set to true
//        assertTrue(DebugModeController.isDebugModeOn())
//
//        try {
//            // Set DEBUG_MODE to false and verify that the method reflects the change
//            setEnv(mapOf("DEBUG_MODE" to "false"))
//            assertFalse(DebugModeController.isDebugModeOn())
//        } catch (e: Exception) {
//            println("Test could not run $e")
//        }
//
//        // Clear the property and check the default behavior
//        System.clearProperty("DEBUG_MODE")
//        assertFalse(DebugModeController.isDebugModeOn())
//    }
//}