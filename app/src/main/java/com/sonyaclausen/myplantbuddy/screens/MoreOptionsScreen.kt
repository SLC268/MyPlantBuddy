package com.sonyaclausen.myplantbuddy.screens


//import androidx.compose.ui.text.intl.Locale
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sonyaclausen.myplantbuddy.CareBot
import com.sonyaclausen.myplantbuddy.Community
import com.sonyaclausen.myplantbuddy.Profile
import com.sonyaclausen.myplantbuddy.R
import com.sonyaclausen.myplantbuddy.SavedSearches
import com.sonyaclausen.myplantbuddy.generic.MoreOptionsCard
import java.util.Locale

@Composable
fun MoreOptionsScreen(
    selectedLocale: Locale,
    onClick: (Locale) -> Unit,
    onRouteClick: (String) -> Unit
) {
    ScreenContent(selectedLocale = selectedLocale, onClick = onClick, onRouteClick = onRouteClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    selectedLocale: Locale,
    onClick: (Locale) -> Unit,
    onRouteClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.more),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            SwitchLanguageToggle(selectedLocale = selectedLocale, onClick = onClick)
            Spacer(modifier = Modifier.height(16.dp))
            CardGridOptions(
                cards = cardsList(),
                onCardClick = {
                    onRouteClick(it.route)
                }
            )

        }
    }
}


@Composable
private fun CardGridOptions(
    cards: List<MenuOption>,
    onCardClick: (MenuOption) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(40.dp),
        verticalArrangement = Arrangement.spacedBy(34.dp)
    ) {
        items(cards) { card ->
            CardItem(card = card, onClick = { onCardClick(card) })
        }
    }
}

@Composable
private fun CardItem(card: MenuOption, onClick: () -> Unit) {
    MoreOptionsCard(
        image = painterResource(id = card.imageRes),
        title = stringResource(id = card.titleRes),
        onClick = onClick
    )
}

@Composable
private fun cardsList(): List<MenuOption> {
    return MenuOption.entries.toList()
}

@Composable
private fun SwitchLanguageToggle(selectedLocale: Locale, onClick: (Locale) -> Unit) {
    val list = listOf(Locale("da"), Locale("en"))

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
    ) {
        Column {
            Text(text = stringResource(id = R.string.current_lang) + selectedLocale.toLanguageTag())
            Text(text = stringResource(id = R.string.switch_lang))
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Row {
                list.forEach {
                    Button(onClick = {
                        onClick(it)
                    }) {
                        Text(text = it.toLanguageTag())
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MoreOptionsScreenPreview() {
    MoreOptionsScreen(selectedLocale = Locale("en"), onRouteClick = { "" }, onClick = {})
}


enum class MenuOption(
    @DrawableRes val imageRes: Int,
    @StringRes val titleRes: Int,
    val route: String
) {
    CARE_BOT(R.drawable.bot_icon, R.string.care_bot, CareBot.toString()),
    PROFILE(R.drawable.profile, R.string.profile, Profile.toString()),
    COMMUNITY(R.drawable.community_icon, R.string.community, Community.toString()),
    SAVED_SEARCHES(R.drawable.saved_heart, R.string.saved, SavedSearches.toString()),
}