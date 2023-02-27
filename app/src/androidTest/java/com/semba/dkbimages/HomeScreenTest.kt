package com.semba.dkbimages

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import com.semba.dkbimages.data.model.ImageModel
import com.semba.dkbimages.design.ui.theme.DkbimagesappTheme
import com.semba.dkbimages.feature.homescreen.ui.HomeScreen
import com.semba.dkbimages.feature.homescreen.ui.HomeScreenUiState
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    lateinit var fakeUiState: HomeScreenUiState

    @Before
    fun init() {
        fakeUiState = HomeScreenUiState.Loading
    }

    @Test
    fun testLoadingUIState() {
        fakeUiState = HomeScreenUiState.Loading
        val loadingString = composeTestRule.activity.getString(R.string.loading)

        composeTestRule.setContent {
            DkbimagesappTheme() {
                HomeScreen(uiState = fakeUiState)
            }
        }

        composeTestRule.onNodeWithText(loadingString).assertIsDisplayed()
    }

    @Test
    fun testFailureUIState() {
        fakeUiState = HomeScreenUiState.Error(-1)
        val errorString = composeTestRule.activity.getString(R.string.error)

        composeTestRule.setContent {
            DkbimagesappTheme() {
                HomeScreen(uiState = fakeUiState)
            }
        }

        composeTestRule.onNodeWithText(errorString).assertIsDisplayed()
    }

    @Test
    fun testSuccessUIState() {
        fakeUiState = HomeScreenUiState.Success(images)

        val loadingString = composeTestRule.activity.getString(R.string.loading)
        val errorString = composeTestRule.activity.getString(R.string.error)

        composeTestRule.setContent {
            DkbimagesappTheme() {
                HomeScreen(uiState = fakeUiState)
            }
        }

        composeTestRule.onNodeWithText(loadingString).assertDoesNotExist()
        composeTestRule.onNodeWithText(errorString).assertDoesNotExist()
        composeTestRule.onNodeWithTag("images_lazy_grid").assertExists()
    }

    @Test
    fun testLazyColumnDisplaysCorrectData() {
        fakeUiState = HomeScreenUiState.Success(images)

        composeTestRule.setContent {
            DkbimagesappTheme() {
                HomeScreen(uiState = fakeUiState)
            }
        }

        val firstItem = composeTestRule.onNodeWithTag("images_lazy_grid").onChildAt(0)
        val secondItem = composeTestRule.onNodeWithTag("images_lazy_grid").onChildAt(1)
        firstItem.assertIsDisplayed()
        secondItem.assertIsDisplayed()
        firstItem.assert(hasText("Title1"))
        secondItem.assert(hasText("Title2"))

    }


    private val images = listOf(
        ImageModel(1,1,"Title1","",""),
        ImageModel(2,2,"Title2","",""),
        ImageModel(3,3,"Title3","",""),
        ImageModel(4,4,"Title4","",""),
        ImageModel(5,5,"Title5","",""),
        ImageModel(6,6,"Title6","",""),
    )

}