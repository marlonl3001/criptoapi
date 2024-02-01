package br.com.mdr.criptoapi.ui.components

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.mdr.criptoapi.common.Constants.CLOSE_BUTTON
import br.com.mdr.criptoapi.common.Constants.SEARCH_TEXT_FIELD
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchTopBarTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    private val query = "BINANCE"

    @Test
    fun openSearchWidget_addInputText_assertInputText() {
        val text = mutableStateOf("")
        composeTestRule.setContent {
            SearchTopBar(
                text = text.value,
                onTextChange = {
                    text.value = it
                },
                onCloseClicked = {},
                onSearchClicked = {}
            )
        }
        composeTestRule.onNodeWithContentDescription(SEARCH_TEXT_FIELD)
            .performTextInput(query)
        composeTestRule.onNodeWithContentDescription(SEARCH_TEXT_FIELD)
            .assertTextEquals(query)
    }

    @Test
    fun openSearchWidget_addInputText_pressCloseButtonOnce_assertEmptyInputText() {
        val text = mutableStateOf("")
        composeTestRule.setContent {
            SearchTopBar(
                text = text.value,
                onTextChange = {
                    text.value = it
                },
                onCloseClicked = {},
                onSearchClicked = {}
            )
        }
        composeTestRule.onNodeWithContentDescription(SEARCH_TEXT_FIELD)
            .performTextInput(query)
        composeTestRule.onNodeWithContentDescription(CLOSE_BUTTON)
            .performClick()
        composeTestRule.onNodeWithContentDescription(SEARCH_TEXT_FIELD)
            .assertTextContains("")
    }

    @Test
    fun openSearchWidget_addInputText_pressCloseButton_assertCloseButtonIsGone() {
        val text = mutableStateOf("")
        val searchWidgetShown = mutableStateOf(true)
        composeTestRule.setContent {
            if (searchWidgetShown.value) {
                SearchTopBar(
                    text = text.value,
                    onTextChange = {
                        text.value = it
                    },
                    onCloseClicked = {
                        searchWidgetShown.value = false
                    },
                    onSearchClicked = {}
                )
            }
        }
        composeTestRule.onNodeWithContentDescription(SEARCH_TEXT_FIELD)
            .performTextInput(query)
        composeTestRule.onNodeWithContentDescription(CLOSE_BUTTON)
            .performClick()
        composeTestRule.onNodeWithContentDescription(CLOSE_BUTTON)
            .assertDoesNotExist()
    }
}
