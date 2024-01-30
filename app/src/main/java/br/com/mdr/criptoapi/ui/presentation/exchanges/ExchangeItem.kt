package br.com.mdr.criptoapi.ui.presentation.exchanges

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import br.com.mdr.criptoapi.R
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.ui.presentation.theme.Dimens.EXCHANGE_ICON_SIZE
import br.com.mdr.criptoapi.ui.presentation.theme.Dimens.LARGE_PADDING
import br.com.mdr.criptoapi.ui.presentation.theme.Dimens.MEDIUM_PADDING
import br.com.mdr.criptoapi.ui.presentation.theme.Dimens.SMALL_PADDING
import coil.compose.AsyncImage

@Composable
fun ExchangeItem(exchange: Exchange, onExchangeClick: (Exchange) -> Unit) {
    Surface(
        shape = RoundedCornerShape(size = LARGE_PADDING),
        onClick = {
            onExchangeClick(exchange)
        }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MEDIUM_PADDING)
        ) {
            val (icon, name, id, volume1day, last24) = createRefs()

            AsyncImage(
                modifier = Modifier
                    .size(EXCHANGE_ICON_SIZE)
                    .constrainAs(icon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    },
//                model = ImageRequest
//                    .Builder(LocalContext.current)
//                    .data(exchange.url)
//                    .size(Size.ORIGINAL)
//                    .placeholder(R.drawable.ic_placeholder)
//                    .build(),
                model = exchange.url,
                contentDescription = exchange.name
            )

            Text(
                modifier = Modifier
                    .constrainAs(name) {
                        bottom.linkTo(icon.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(icon.end, SMALL_PADDING)
                        top.linkTo(icon.top)
                        width = Dimension.fillToConstraints
                    },
                text = exchange.name
            )
            Text(
                modifier = Modifier
                    .constrainAs(id) {
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        top.linkTo(name.bottom, SMALL_PADDING)
                        width = Dimension.fillToConstraints
                    },
                text = exchange.exchangeId
            )
            Text(
                modifier = Modifier
                    .constrainAs(volume1day) {
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        top.linkTo(id.bottom, SMALL_PADDING)
                        width = Dimension.fillToConstraints
                    },
                text = exchange.volumeLast24,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .constrainAs(last24) {
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        top.linkTo(volume1day.bottom, SMALL_PADDING)
                        width = Dimension.fillToConstraints
                    },
                text = stringResource(id = R.string.last_24_hours),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview
@Composable
fun ExchangeItemPreview() {
    ExchangeItem(
        Exchange(
            "ETHERIUM",
            "Etherium",
//            "",
//            "",
//            "",
//            "",
//            "",
//            "",
//            "",
//            "",
//            0,
//            0,
//            0.0,
            3456538.57,
            654665865.0,
            3698218468.75,
//            0.0,
//            listOf(),
//            listOf(),
            null
        )
    ) {}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExchangeItemDarkPreview() {
    ExchangeItem(
        Exchange(
            "ETHERIUM",
            "Etherium",
//            "",
//            "",
//            "",
//            "",
//            "",
//            "",
//            "",
//            "",
//            0,
//            0,
//            0.0,
            3456538.57,
            654665865.0,
            3698218468.75,
//            0.0,
//            listOf(),
//            listOf(),
            null
        )
    ) {}
}
