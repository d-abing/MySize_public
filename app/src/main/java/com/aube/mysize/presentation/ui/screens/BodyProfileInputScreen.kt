package com.aube.mysize.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aube.mysize.domain.model.BodyProfile
import com.aube.mysize.presentation.viewmodel.BodyProfileViewModel
import java.time.LocalDate

@Composable
fun BodyProfileInputScreen(
    viewModel: BodyProfileViewModel,
    onSaved: () -> Unit
) {
    var gender by remember { mutableStateOf("남성") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var chest by remember { mutableStateOf("") }
    var waist by remember { mutableStateOf("") }
    var hip by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("신체 정보 입력", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(value = height, onValueChange = { height = it }, label = { Text("키 (cm)") })
        OutlinedTextField(value = weight, onValueChange = { weight = it }, label = { Text("몸무게 (kg)") })
        OutlinedTextField(value = chest, onValueChange = { chest = it }, label = { Text("가슴둘레 (cm)") })
        OutlinedTextField(value = waist, onValueChange = { waist = it }, label = { Text("허리둘레 (cm)") })
        OutlinedTextField(value = hip, onValueChange = { hip = it }, label = { Text("엉덩이둘레 (cm)") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val profile = BodyProfile(
                id = 0,
                gender = gender,
                height = height.toFloatOrNull() ?: 0f,
                weight = weight.toFloatOrNull() ?: 0f,
                chest = chest.toFloatOrNull() ?: 0f,
                waist = waist.toFloatOrNull() ?: 0f,
                hip = hip.toFloatOrNull() ?: 0f,
                date = LocalDate.now()
            )
            viewModel.saveProfile(profile)
            onSaved()
        }) {
            Text("저장")
        }
    }
}
