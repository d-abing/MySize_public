package com.aube.mysize.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aube.mysize.presentation.viewmodel.BodyProfileViewModel

@Composable
fun BodyProfileHistoryScreen(viewModel: BodyProfileViewModel) {
    val list by viewModel.bodyProfiles.collectAsState()

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(list) { profile ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("날짜: ${profile.date}")
                    Text("키: ${profile.height} cm")
                    Text("몸무게: ${profile.weight} kg")
                    Text("가슴/허리/엉덩이: ${profile.chest}/${profile.waist}/${profile.hip} cm")
                }
            }
        }
    }
}
