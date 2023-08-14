package com.ptk.pweather.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.pweather.model.RemoteResource
import com.ptk.pweather.repository.UserRepository
import com.ptk.pweather.roomdb.entity.UserEntity
import com.ptk.pweather.ui.ui_states.UserUIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
    private val application: Application,

    ) : ViewModel() {
    private val _uiStates = MutableStateFlow(UserUIStates())
    val uiStates = _uiStates.asStateFlow()

    fun userNameEmpty() {
        _uiStates.update { it.copy(userNameEmpty = true) }
    }

    fun toggleUserName(userName: String) {

        _uiStates.update {
            it.copy(
                userNameEmpty = false,
                userNameNotExist = null,
                userName = userName
            )
        }
    }

    fun passwordEmpty() {
        _uiStates.update { it.copy(passwordEmpty = true) }
    }

    fun passwordLengthShort() {
        _uiStates.update { it.copy(passwordLengthShort = true) }
    }

    fun showAlertDialog(showAlertDialog: Boolean) {
        _uiStates.update { it.copy(showAlertDialog = showAlertDialog) }
    }

    fun setGGSignInErrorMessage(ggSignInErrMsg: String) {
        _uiStates.update { it.copy(ggSignInErrMsg = ggSignInErrMsg) }
    }

    fun setGGSignInSuccess(ggSignInSuccess:Boolean){
        _uiStates.update { it.copy(ggSignInSuccess = ggSignInSuccess) }
    }

    fun setErrorMessage(errorMessage: String) {
        _uiStates.update { it.copy(errorMessage = errorMessage) }
    }

    fun togglePassword(password: String) {

        _uiStates.update {
            it.copy(
                passwordEmpty = false,
                passwordLengthShort = false,
                passCPassNotSame = false,
                password = password
            )
        }
    }

    fun confirmPasswordEmpty() {
        _uiStates.update { it.copy(confirmPasswordEmpty = true) }
    }

    fun passCPassNotSame() {
        _uiStates.update { it.copy(passCPassNotSame = true) }
    }


    fun toggleConfirmPassword(cPassword: String) {

        _uiStates.update {
            it.copy(
                confirmPasswordEmpty = false,
                passwordLengthShort = false,
                passCPassNotSame = false,
                confirmPassword = cPassword
            )
        }
    }

    fun checkUserNameExist(
        userName: String,
        password: String
    ) {
        repository.checkUserName(userName).onEach { remoteResource ->
            when (remoteResource) {
                is RemoteResource.Loading ->
                    _uiStates.update {
                        it.copy(showLoadingDialog = true)
                    }

                is RemoteResource.Success -> {
                    if (remoteResource.data.isNotEmpty()) {
                        _uiStates.update {
                            it.copy(
                                userNameNotExist = false,
                                showLoadingDialog = false

                            )
                        }
                    } else {
                        _uiStates.update {
                            it.copy(
                                userNameNotExist = true

                            )
                        }
                        register(userName, password)
                    }
                }

                is RemoteResource.Failure -> {
                    _uiStates.update {
                        it.copy(
                            errorMessage = "Something went wrong, : ${remoteResource.errorMessage}"
                        )
                    }
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun register(
        userName: String,
        password: String
    ) {
        val userEntity = UserEntity(userName, password)

        _uiStates.update { it.copy(errorMessage = "", registerSuccess = false) }
        repository.register(userEntity).onEach { remoteResource ->
            when (remoteResource) {
                is RemoteResource.Loading -> {}

                is RemoteResource.Success -> {
                    if (remoteResource.data == 0L) {
                        _uiStates.update {
                            it.copy(
                                showLoadingDialog = false,
                                errorMessage = "Some Error Occurred"

                            )
                        }
                    } else {
                        _uiStates.update {
                            it.copy(
                                showLoadingDialog = false,
                                registerSuccess = true,
                                errorMessage = ""
                            )
                        }
                    }
                }

                is RemoteResource.Failure -> {
                    _uiStates.update {
                        it.copy(
                            showLoadingDialog = false,
                            errorMessage = "Something went wrong, : ${remoteResource.errorMessage}"
                        )
                    }
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun login(
        userName: String,
        password: String
    ) {

        _uiStates.update { it.copy(errorMessage = "", registerSuccess = false) }
        repository.login(userName, password).onEach { remoteResource ->
            when (remoteResource) {
                is RemoteResource.Loading -> {
                    _uiStates.update {
                        it.copy(showLoadingDialog = true)
                    }
                }

                is RemoteResource.Success -> {
                    if (remoteResource.data.isEmpty()) {
                        _uiStates.update {
                            it.copy(
                                showLoadingDialog = false,
                                errorMessage = "Username or password incorrect"
                            )
                        }
                    } else {
                        _uiStates.update {
                            it.copy(
                                showLoadingDialog = false,
                                loginSuccess = true,
                                errorMessage = ""
                            )
                        }
                    }
                }

                is RemoteResource.Failure -> {
                    _uiStates.update {
                        it.copy(
                            showLoadingDialog = false,
                            errorMessage = "Something went wrong, : ${remoteResource.errorMessage}"
                        )
                    }
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }


}
