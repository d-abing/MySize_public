import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aube.mysize.R
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.presentation.model.size.toUi
import com.aube.mysize.ui.theme.MySizeTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecommendedSizeTab(
    modifier: Modifier = Modifier,
    isRecommendSizeStep: Boolean = false,
    maxItemsInEachRow: Int = 3,
    itemHeight: Dp = 120.dp,
    onClick: (SizeCategory) -> Unit
) {
    val categoryItems = SizeCategory.entries.filter { it != SizeCategory.BODY }
    val spacing = 12.dp
    val shape = RoundedCornerShape(16.dp)

    BoxWithConstraints(
        modifier = modifier.fillMaxWidth()
    ) {
        val itemSize = (maxWidth - spacing * (maxItemsInEachRow - 1) - 1.dp) / maxItemsInEachRow

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalArrangement = Arrangement.spacedBy(spacing),
            maxItemsInEachRow = maxItemsInEachRow
        ) {
            categoryItems.forEach { category ->
                val categoryUi = category.toUi()
                Box(
                    modifier = Modifier
                        .width(itemSize)
                        .height(itemHeight)
                        .clip(shape)
                        .background(
                            color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f),
                            shape = shape
                        )
                        .clickable { onClick(category) }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = categoryUi.icon,
                            fontSize = 28.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isRecommendSizeStep && category == SizeCategory.ACCESSORY)
                                stringResource(R.string.text_accessory_label_in_recommend_step)
                            else categoryUi.label,
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendedSizeTabPreview() {
    MySizeTheme {
        RecommendedSizeTab(
            onClick = {},
            isRecommendSizeStep = true
        )
    }
}
