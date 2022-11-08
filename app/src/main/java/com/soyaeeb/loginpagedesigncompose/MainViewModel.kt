package com.soyaeeb.loginpagedesigncompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyaeeb.loginpagedesigncompose.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.internal.ChannelFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.seconds

sealed interface  MainUiEvent{
    data class ShowToastMessage(val message: Int) : MainUiEvent
}

sealed interface MainUiAction{
    data class LoginAction(val phoneNumber: String, val password: String) : MainUiAction
}


@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    val action : (MainUiAction) -> Unit

    private val _phoneNumberState = MutableStateFlow("")
    val phoneNumberState = _phoneNumberState.asStateFlow()

    private val _passwordState = MutableStateFlow("")
    val passwordState = _passwordState.asStateFlow()

    private val _uiEvent = Channel<MainUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _visibleScreenState = MutableStateFlow(Screen.LOGIN)
    val visibleScreenState = _visibleScreenState.asStateFlow()


    init {
        action = {
            when (it) {
                is MainUiAction.LoginAction -> {
                    viewModelScope.launch {
                        _visibleScreenState.value = Screen.LOADING
                        delay(5000L)
                        sentEvent(MainUiEvent.ShowToastMessage(R.string.login_success))
                        _visibleScreenState.value = Screen.HOME
                    }
                }
            }
        }
    }

    fun updatePhoneNumber(phoneNumber : String){
        _phoneNumberState.value = phoneNumber
    }

    fun updatePassword(password : String){
        _passwordState.value = password
    }

    fun validPhoneNumberAndPassword(phoneNumber: String, password: String) : Boolean{
        return if(phoneNumber.isEmpty()){
            sentEvent(MainUiEvent.ShowToastMessage(R.string.error_message_empty_number))
            false
        }else if(phoneNumber.length < 11){
            sentEvent(MainUiEvent.ShowToastMessage(R.string.error_msg_invalid_number))
            false
        }else if(password.isEmpty()){
            sentEvent(MainUiEvent.ShowToastMessage(R.string.error_msg_enter_password))
            false
        }else if(password.length < 6){
            sentEvent(MainUiEvent.ShowToastMessage(R.string.password_length))
            false
        }
        else true
    }

    private fun sentEvent(event : MainUiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}