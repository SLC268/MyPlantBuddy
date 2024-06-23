package com.sonyaclausen.myplantbuddy.screens


//import androidx.compose.ui.text.intl.Locale
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.sonyaclausen.myplantbuddy.R
import java.util.Locale

@Composable
fun MoreOptionsScreen(selectedLocale: Locale, onClick: (Locale) -> Unit) {
    ScreenContent(selectedLocale = selectedLocale, onClick = onClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    selectedLocale: Locale,
    onClick: (Locale) -> Unit
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
                cards = CardsList(),
                onCardClick = {}
            )

        }
    }
}


@Composable
private fun CardGridOptions(
    cards: List<CardItem>,
    onCardClick: (CardItem) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        for (i in cards.indices.chunked(3)) {
            Row(Modifier.fillMaxWidth()) {
                for (card in cards.subList(i.first(), i.last() + 1)) {
                    CardItem(card = card, onClick = { onCardClick(card) })
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
//private fun CardItem(card: CardItem, onClick: () -> Unit) {
//    Button(onClick = onClick) {
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            Icon(
//                painter = painterResource(id = card.imageRes),
//                contentDescription = null,
//                modifier = Modifier.size(48.dp)
//            )
//            Text(text = card.text)
//        }
//    }
//}

fun CardItem(card: CardItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(card.imageRes),
                contentDescription = null, // You can provide a meaningful description here
                modifier = Modifier
                    .size(120.dp)
                    .clip(shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = card.text, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun CardsList(): List<CardItem> {
    return listOf(
        CardItem(R.raw.bot_blink, stringResource(id = R.string.care_bot)),
        CardItem(R.raw.profile, stringResource(id = R.string.profile)),
        CardItem(R.raw.community_hands, stringResource(id = R.string.community)),
        CardItem(R.raw.saved_heart, stringResource(id = R.string.saved)),
    )
}

@Composable
private fun SwitchLanguageToggle(selectedLocale: Locale, onClick: (Locale) -> Unit) {
    val list = listOf(Locale("da"), Locale("en"))
    Text(text = stringResource(id = R.string.current_lang) + selectedLocale.toLanguageTag())
    Text(text = stringResource(id = R.string.switch_lang))
    Column {
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

@Preview
@Composable
fun MoreOptionsScreenPreview() {
    MoreOptionsScreen(selectedLocale = Locale("en")) {}
}

data class CardItem(val imageRes: Int, val text: String)
