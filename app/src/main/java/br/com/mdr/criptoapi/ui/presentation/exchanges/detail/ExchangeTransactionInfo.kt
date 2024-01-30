package br.com.mdr.criptoapi.ui.presentation.exchanges.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import br.com.mdr.criptoapi.R
import br.com.mdr.criptoapi.ui.presentation.theme.Dimens

@Composable
fun ExchangeTransactionInfo(modifier: Modifier, price: String, interval: String) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimens.SMALL_PADDING)
        ,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.price_traded),
                textAlign = TextAlign.Center
            )
            Text(
                text = price,
                fontWeight = FontWeight.SemiBold
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.price_traded_interval_label),
                textAlign = TextAlign.Center
            )
            Text(
                text = interval,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}