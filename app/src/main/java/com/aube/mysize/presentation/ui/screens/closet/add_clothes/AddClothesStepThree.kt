package com.aube.mysize.presentation.ui.screens.closet.add_clothes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.size.BodySize

@Composable
fun ColumnScope.AddClothesStepThree(
    bodySize: BodySize?,
    selectedKeys: Set<String>,
    onSelectionChanged: (Set<String>) -> Unit,
    onPrevious: () -> Unit,
    onComplete: () -> Unit
) {
    val availableFields = listOf(
        "height" to "키",
        "weight" to "몸무게",
        "gender" to "성별",
        "chest" to "가슴 둘레",
        "waist" to "허리 둘레",
        "hip" to "엉덩이 둘레",
        "neck" to "목 둘레",
        "shoulder" to "어깨 너비",
        "armLength" to "팔 길이",
        "leg" to "다리 안쪽 길이",
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(text = "공개할 신체 정보", style = MaterialTheme.typography.bodyLarge)

            availableFields.forEach { (key, label) ->
                val isSelected = key in selectedKeys
                val value = bodySize?.let { getValueByKey(it, key) } ?: "-"

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable {
                            val newSet = if (isSelected) selectedKeys - key else selectedKeys + key
                            onSelectionChanged(newSet)
                        }
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(label, modifier = Modifier.weight(1f))
                        Text(value, modifier = Modifier.weight(1f), color = Color.Gray)
                        Checkbox(checked = isSelected, onCheckedChange = null)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Button(
                onClick = onPrevious,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text("이전")
            }
            Button(
                onClick = onComplete,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text("등록")
            }
        }
    }
}

fun getValueByKey(bodySize: BodySize, key: String): String = when (key) {
    "height" -> "${bodySize.height}cm"
    "weight" -> "${bodySize.weight}kg"
    "chest" -> "${bodySize.chest}cm"
    "waist" -> "${bodySize.waist}cm"
    "hip" -> "${bodySize.hip}cm"
    "neck" -> "${bodySize.neck}cm"
    "shoulder" -> "${bodySize.shoulder}cm"
    "armLength" -> "${bodySize.arm}cm"
    "leg" -> "${bodySize.leg}cm"
    else -> "-"
}
