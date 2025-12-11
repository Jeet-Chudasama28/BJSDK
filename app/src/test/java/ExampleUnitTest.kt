import com.example.myapplication.api.di.appModule
import com.example.myapplication.data.DatabaseHelper
import org.junit.After
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MyAppUnitTest: KoinTest {

    @Before
    fun setup() {
        startKoin {
            modules(appModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    val dbHelper : DatabaseHelper by inject()

    /*@Test
    fun `Return true if user list is not empty`() {
        val userList = dbHelper.getAllUsers2()
        assertTrue(userList.isNotEmpty())
    }*/
}