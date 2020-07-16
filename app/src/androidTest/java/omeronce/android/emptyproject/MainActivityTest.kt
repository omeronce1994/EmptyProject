package omeronce.android.emptyproject

import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.nhaarman.mockitokotlin2.spy
import omeronce.android.emptyproject.books.repository.AndroidFakeBooksRepository
import omeronce.android.emptyproject.books.repository.BooksRepository
import omeronce.android.emptyproject.books.viewmodel.BooksViewModel
import omeronce.android.emptyproject.model.books.Book
import omeronce.android.emptyproject.model.books.PlaceholderColor
import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.dsl.module

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    private lateinit var initialData: List<Book>

    @get:Rule
    val koinRule = AndroidTestKoinRule.create(InstrumentationRegistry.getInstrumentation()
        .targetContext.applicationContext as IKoinTestApplication,
        module { factory { BooksViewModel(createRepository()) } })

    fun createRepository(): BooksRepository {
        initialData = listOf(
            Book("Harry Potter", PlaceholderColor(255, 100, 50), "Harry Potter Desc"),
            Book("Harry Potter2", PlaceholderColor(255, 150, 50), "Harry Potter Desc2"),
            Book("Harry Potter3", PlaceholderColor(25, 100, 50), "Harry Potter Desc3")
        )
        return spy(AndroidFakeBooksRepository(initialData))
    }
}