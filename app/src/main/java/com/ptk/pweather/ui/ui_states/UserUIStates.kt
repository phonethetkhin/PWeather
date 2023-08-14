package com.ptk.pweather.ui.ui_states

data class UserUIStates(
    val userNameEmpty: Boolean = false,
    val passwordEmpty: Boolean = false,
    val passwordLengthShort: Boolean = false,
    val confirmPasswordEmpty: Boolean = false,
    val passCPassNotSame: Boolean = false,
    val userName: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val showLoadingDialog: Boolean = false,
    val showAlertDialog: Boolean = false,
    val errorMessage: String = "",
    val userNameNotExist: Boolean? = null,
    val registerSuccess: Boolean = false,
    val loginSuccess: Boolean = false,
    val ggSignInErrMsg: String = "",
    val ggSignInSuccess: Boolean = false,
)