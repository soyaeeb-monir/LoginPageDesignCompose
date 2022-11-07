package com.soyaeeb.loginpagedesigncompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.soyaeeb.loginpagedesigncompose.component.HomeScreen
import com.soyaeeb.loginpagedesigncompose.component.LoadingScreen
import com.soyaeeb.loginpagedesigncompose.component.LoginScreen
import com.soyaeeb.loginpagedesigncompose.ui.theme.LoginPageDesignComposeTheme
import com.soyaeeb.loginpagedesigncompose.util.Screen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageDesignComposeTheme {
                val viewModel by viewModels<MainViewModel>()
                val context = LocalContext.current
                val scope = rememberCoroutineScope()
                val visibleScreen by viewModel.visibleScreenState.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LaunchedEffect(true) {
                        viewModel.uiEvent.collect { event ->
                            when (event) {
                                is MainUiEvent.ShowToastMessage -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                    when(visibleScreen){
                        Screen.LOGIN -> LoginScreen(
                            viewModel = viewModel,
                            onLoginButtonClicked = { phone, password ->
                              if(viewModel.validPhoneNumberAndPassword(phone,password)){
                                  viewModel.action.invoke(MainUiAction.LoginAction(phone,password))
                              }
                            })
                        Screen.LOADING -> LoadingScreen()
                        Screen.HOME -> HomeScreen()
                    }
                }
            }
        }
    }
}
