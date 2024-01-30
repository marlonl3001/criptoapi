package br.com.mdr.criptoapi.ui.presentation.exchanges.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import br.com.mdr.criptoapi.R
import br.com.mdr.criptoapi.domain.model.Exchange
import br.com.mdr.criptoapi.ui.presentation.theme.Dimens.MEDIUM_PADDING
import br.com.mdr.criptoapi.ui.presentation.theme.Dimens.SMALL_PADDING

@Composable
fun ExchangeDetailInfo(modifier: Modifier, exchange: Exchange) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(MEDIUM_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.volumes),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.vol_last_hour),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = exchange.volumeLast24,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = stringResource(id = R.string.last_24_hours),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = exchange.volumeLastHour,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = stringResource(id = R.string.vol_last_month),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = exchange.volumeLastMonth,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview
@Composable
fun ExchangeDetailInfoPreview() {
    ExchangeDetailInfo(modifier = Modifier, exchange = Exchange(
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
    ))
}