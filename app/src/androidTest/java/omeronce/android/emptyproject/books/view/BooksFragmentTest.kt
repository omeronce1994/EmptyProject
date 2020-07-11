package omeronce.android.emptyproject.books.view

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.nhaarman.mockitokotlin2.spy
import omeronce.android.emptyproject.*
import omeronce.android.emptyproject.books.repository.AndroidFakeBooksRepository
import omeronce.android.emptyproject.books.repository.BooksRepository
import omeronce.android.emptyproject.books.viewmodel.BooksViewModel
import omeronce.android.emptyproject.model.books.Book
import omeronce.android.emptyproject.model.books.PlaceholderColor
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.module.Module
import org.koin.dsl.module

@MediumTest
@RunWith(AndroidJUnit4ClassRunner::class)
class BooksFragmentTest {

    private lateinit var initialData: List<Book>

    @get:Rule
    val koinRule = AndroidTestKoinRule.create(InstrumentationRegistry.getInstrumentation()
        .targetContext.applicationContext as IKoinTestApplication,
        module { single { BooksViewModel(createRepository()) } })

    fun createRepository(): BooksRepository {
        initialData = listOf(
            Book("Harry Potter", PlaceholderColor(255, 100, 50), "Harry Potter Desc"),
            Book("Harry Potter2", PlaceholderColor(255, 150, 50), "Harry Potter Desc2"),
            Book("Harry Potter3", PlaceholderColor(25, 100, 50), "Harry Potter Desc3")
        )
        return spy(AndroidFakeBooksRepository(initialData))
    }

    @Test
    fun booksFragment_displayUi() {
        launchFragmentInContainer<BooksFragment>(Bundle(), R.style.AppTheme)
        onView(withText("Harry Potter")).check(matches(isDisplayed()))
        onView(withText("Harry Potter2")).check(matches(isDisplayed()))
        onView(withText("Harry Potter3")).check(matches(isDisplayed()))
    }
}