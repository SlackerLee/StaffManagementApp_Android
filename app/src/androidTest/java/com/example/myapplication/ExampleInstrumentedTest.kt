package com.example.myapplication

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myapplication.managers.LoginManager
import com.example.myapplication.models.enum.error.LoginError
import com.example.myapplication.models.network.login.LoginRespData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.myapplication", appContext.packageName)
    }
    @Test
    fun testLoginSuccess() {
        val latch = CountDownLatch(1)
        var resultData: Result<LoginRespData>? = null

        // Use valid test credentials (adjust as needed)
        val email = "eve.holt@reqres.in"
        val password = "cityslicka"

        LoginManager.login(email, password) { result ->
            resultData = result
            latch.countDown() // release the latch
        }

        resultData?.onSuccess { resp ->
            assertNotNull("Token should not be null", resp.token)
            assertTrue("Token should not be empty", resp.token!!.isNotEmpty())
        }?.onFailure { error ->
            fail("Expected success but got error: ${error.message}")
        }
    }
    @Test
    fun testLoginFailure() {
        val latch = CountDownLatch(1)
        var resultData: Result<LoginRespData>? = null

        // Use invalid credentials to simulate failure
        val email = "invalid@example.com"
        val password = "wrongpass"

        LoginManager.login(email, password) { result ->
            resultData = result
            latch.countDown()
        }

        resultData?.onSuccess { resp ->
            fail("Expected failure but got success with token: ${resp.token}")
        }?.onFailure { error ->
            // Should be a LoginError
            assertTrue("Error should be a LoginError", error is LoginError)
        }
    }
}