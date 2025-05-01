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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aube.mysize.presentation.model.SizeCategory

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecommendedSizeGrid(
    modifier: Modifier = Modifier,
    onClick: (SizeCategory) -> Unit
) {
    val categoryItems = SizeCategory.entries.filter { it != SizeCategory.BODY }

    val spacing = 12.dp

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val itemSize = (maxWidth - (spacing * 2) - 1.dp) / 3

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(spacing),
            horizontalArrangement = Arrangement.spacedBy(spacing),
            maxItemsInEachRow = 3
        ) {
            categoryItems.forEach { categoryItem ->
                Box(
                    modifier = Modifier
                        .width(itemSize)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onClick(categoryItem) }
                        .background(
                            color = MaterialTheme.colorScheme.tertiary.copy(0.2f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = categoryItem.icon,
                            fontSize = 28.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = categoryItem.label,
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
