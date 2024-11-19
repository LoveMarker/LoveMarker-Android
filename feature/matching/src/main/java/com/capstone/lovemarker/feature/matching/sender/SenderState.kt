package com.capstone.lovemarker.feature.matching.sender

data class SenderState(
    val anniversary: String = "",
    val invitationCode: String = "",
    val showDialog: Boolean = false,
    val buttonEnabled: Boolean = false,
    val codeCreated: Boolean = false,
)
