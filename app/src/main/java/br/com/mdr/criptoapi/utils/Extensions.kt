package br.com.mdr.criptoapi.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.snapshotFlow
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

private const val ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'"
fun Double.getDollarAmount(): String {
    val formatter = DecimalFormat("$ #,###.00")
    return formatter.format(this)
}

@Composable
fun LazyListState.isScrollingUp(): State<Boolean> {
    return produceState(initialValue = true) {
        var lastIndex = 0
        var lastScroll = Int.MAX_VALUE
        snapshotFlow {
            firstVisibleItemIndex to firstVisibleItemScrollOffset
        }.collect { (currentIndex, currentScroll) ->
            if (currentIndex != lastIndex || currentScroll != lastScroll) {
                value = currentIndex < lastIndex ||
                    (currentIndex == lastIndex && currentScroll < lastScroll)
                lastIndex = currentIndex
                lastScroll = currentScroll
            }
        }
    }
}

fun Date.toIsoFormat(): String =
    SimpleDateFormat(
        ISO_DATE_FORMAT,
        Locale("en", "US", "POSIX")
    ).format(this)

fun String.hourFormatted(): String {
    val formatter = DateTimeFormatter.ofPattern(ISO_DATE_FORMAT)
    val date = LocalDateTime.parse(this, formatter)

    return DateTimeFormatter.ofPattern("HH:mm").format(date)
}

fun String.dateFormatted(): String {
    val formatter = DateTimeFormatter.ofPattern(ISO_DATE_FORMAT)
    val date = LocalDateTime.parse(this, formatter)

    return DateTimeFormatter.ofPattern("dd/MM/yy").format(date)
}
