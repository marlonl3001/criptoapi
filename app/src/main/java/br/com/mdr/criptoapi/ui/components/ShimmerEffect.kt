package br.com.mdr.criptoapi.ui.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import br.com.mdr.criptoapi.ui.presentation.theme.Dimens.LARGE_PADDING
import br.com.mdr.criptoapi.ui.presentation.theme.Dimens.MEDIUM_PADDING
import br.com.mdr.criptoapi.ui.presentation.theme.Dimens.SMALL_PADDING
import br.com.mdr.criptoapi.ui.presentation.theme.ShimmerColor
import br.com.mdr.criptoapi.ui.presentation.theme.ShimmerMediumColor

@Composable
fun ShimmerEffect() {
    LazyColumn(
        modifier = Modifier
            .semantics { contentDescription = "Shimmer" },
        contentPadding = PaddingValues(all = SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {
        items(count = 7) {
            AnimatedShimmerItem()
        }
    }
}

@Composable
fun AnimatedShimmerItem() {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val alphaAnim by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer"
    )

    ShimmerItem(alpha = alphaAnim)
}

@Composable
fun ShimmerItem(alpha: Float) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(alpha),
        color = ShimmerColor,
        shape = RoundedCornerShape(size = LARGE_PADDING)
    ) {
        Column(
            modifier = Modifier
                .padding(MEDIUM_PADDING)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (icon, name, item3, item4, item5) = createRefs()
                Surface(
                    modifier = Modifier
                        .size(24.dp)
                        .constrainAs(icon) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        },
                    color = ShimmerMediumColor,
                    shape = RoundedCornerShape(MEDIUM_PADDING)
                ) {}
                Surface(
                    modifier = Modifier
                        .height(10.dp)
                        .constrainAs(name) {
                            bottom.linkTo(icon.bottom)
                            end.linkTo(parent.end)
                            start.linkTo(icon.end, MEDIUM_PADDING)
                            top.linkTo(icon.top)
                            width = Dimension.fillToConstraints
                        },
                    color = ShimmerMediumColor,
                    shape = RoundedCornerShape(MEDIUM_PADDING)
                ) {}
                Surface(
                    modifier = Modifier
                        .height(15.dp)
                        .constrainAs(item3) {
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                            top.linkTo(icon.bottom, SMALL_PADDING)
                            width = Dimension.fillToConstraints
                        },
                    color = ShimmerMediumColor,
                    shape = RoundedCornerShape(SMALL_PADDING)
                ) {}
                Surface(
                    modifier = Modifier
                        .height(10.dp)
                        .constrainAs(item4) {
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                            top.linkTo(item3.bottom, SMALL_PADDING)
                            width = Dimension.fillToConstraints
                        },
                    color = ShimmerMediumColor,
                    shape = RoundedCornerShape(SMALL_PADDING)
                ) {}

                Surface(
                    modifier = Modifier
                        .height(10.dp)
                        .constrainAs(item5) {
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                            top.linkTo(item4.bottom, SMALL_PADDING)
                            width = Dimension.fillToConstraints
                        },
                    color = ShimmerMediumColor,
                    shape = RoundedCornerShape(SMALL_PADDING)
                ) {}
            }
        }
    }
}

@Preview
@Composable
fun ShimmerItemPreview() {
    AnimatedShimmerItem()
}
