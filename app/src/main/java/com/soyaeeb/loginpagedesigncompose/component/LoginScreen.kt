package com.soyaeeb.loginpagedesigncompose.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyaeeb.loginpagedesigncompose.MainViewModel
import com.soyaeeb.loginpagedesigncompose.R


@Composable
@ExperimentalMaterial3Api
fun LoginScreen(
    viewModel: MainViewModel,
    onLoginButtonClicked: (String, String) -> Unit
) {
    val phoneNumber by viewModel.phoneNumberState.collectAsState()
    val password by viewModel.passwordState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.title_welcome),
            fontSize = 25.sp,
            fontWeight = Bold
        )
        Spacer(modifier = Modifier.height(60.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = phoneNumber,
            onValueChange = { viewModel.updatePhoneNumber(it) },
            label = { Text(text = stringResource(id = R.string.label_enter_phone_number)) },
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { viewModel.updatePassword(it) },
            label = { Text(text = stringResource(id = R.string.label_enter_password)) },
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            onClick = {
                onLoginButtonClicked.invoke(phoneNumber,password)
            }
        ) {
            Text(text = stringResource(id = R.string.btn_text_login))
        }
    }
}

