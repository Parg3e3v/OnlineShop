package com.parg3v.tz_effective.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.parg3v.tz_effective.R
import com.parg3v.tz_effective.model.SortType
import com.parg3v.tz_effective.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun SortingDropdown(
    sortingMethod: (SortType) -> Unit,
    listState: LazyGridState,
    clickable: Boolean,
    sortingType: MutableState<Int>
) {

    val showMenu = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_catalog_menu_between)),
            modifier = if (clickable) Modifier.clickable { showMenu.value = !showMenu.value } else Modifier
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_sort),
                contentDescription = null
            )
            Text(
                text = stringResource(id = sortingType.value),
                style = Typography.displayMedium
            )

            Icon(
                painter = painterResource(id = R.drawable.icon_dropdown),
                contentDescription = null
            )
        }
        DropdownMenu(
            expanded = showMenu.value,
            onDismissRequest = { showMenu.value = false }) {
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.by_popularity)) },
                onClick = {
                    scope.launch {
                        sortItemsBy(
                            SortType.POPULARITY,
                            sortingType,
                            showMenu,
                            sortingMethod,
                            listState
                        )
                    }
                },
                modifier = Modifier.background(
                    Color.White
                )
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.by_price_to_min)) },
                onClick = {
                    scope.launch {
                        sortItemsBy(
                            SortType.PRICE_TO_MIN,
                            sortingType,
                            showMenu,
                            sortingMethod,
                            listState
                        )
                    }
                },
                modifier = Modifier.background(
                    Color.White
                )
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.by_price_to_max)) },
                onClick = {
                    scope.launch {
                        sortItemsBy(
                            SortType.PRICE_TO_MAX,
                            sortingType,
                            showMenu,
                            sortingMethod,
                            listState
                        )
                    }
                },
                modifier = Modifier.background(
                    Color.White
                )
            )
        }
    }
}

suspend fun sortItemsBy(
    sortBy: SortType,
    sortingTypeId: MutableState<Int>,
    showMenu: MutableState<Boolean> = mutableStateOf(false),
    sortingMethod: (SortType) -> Unit,
    listState: LazyGridState
) {
    sortingMethod(sortBy)
    sortingTypeId.value = sortBy.stringResourceId
    showMenu.value = false
    listState.animateScrollToItem(index = 0)
}