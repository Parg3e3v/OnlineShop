package com.parg3v.tz_effective.components

import android.annotation.SuppressLint
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.parg3v.tz_effective.R
import com.parg3v.tz_effective.config.Config
import com.parg3v.tz_effective.model.SortType
import com.parg3v.tz_effective.ui.theme.DarkBlue
import com.parg3v.tz_effective.ui.theme.Grey
import com.parg3v.tz_effective.ui.theme.LightGrey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@ExperimentalMaterial3Api
@Composable
fun TagSelectionButtons(
    modifier: Modifier = Modifier,
    tags: List<Int>,
    selectedOption: Int,
    clickable: Boolean,
    onClick: (String) -> Unit,
    sortingType: MutableState<Int>,
    sortingMethod: (SortType) -> Unit,
    listState: LazyGridState
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    Row(
        modifier = modifier.horizontalScroll(scrollState),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        tags.forEach { tag ->
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                if (selectedOption == tag) {
                    Button(
                        onClick = {
                            if (clickable) {
                                filter(
                                    onClick = onClick,
                                    tag = tag,
                                    sortingType = sortingType,
                                    sortingMethod = sortingMethod,
                                    listState = listState,
                                    scope = scope
                                )
                            }
                        }, colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
                    ) {
                        Text(text = stringResource(id = tag))
                        Icon(
                            painter = painterResource(id = R.drawable.icon_clear_small),
                            contentDescription = null
                        )
                    }
                } else {
                    Button(
                        enabled = clickable,
                        onClick = {
                            if (clickable) {
                                filter(
                                    onClick = onClick,
                                    tag = tag,
                                    sortingType = sortingType,
                                    sortingMethod = sortingMethod,
                                    listState = listState,
                                    scope = scope
                                )
                            }
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = LightGrey, contentColor = Grey
                        )
                    ) {
                        Text(text = stringResource(id = tag))
                    }
                }
            }
        }
    }
}

private fun filter(
    scope: CoroutineScope,
    onClick: (String) -> Unit,
    tag: Int,
    sortingType: MutableState<Int>,
    sortingMethod: (SortType) -> Unit,
    listState: LazyGridState,
) {
    onClick(Config.TAGS.filterValues { it == tag }.keys.first())
    scope.launch {
        sortItemsBy(
            sortBy = SortType.POPULARITY,
            sortingTypeId = sortingType,
            sortingMethod = sortingMethod,
            listState = listState
        )
    }
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}