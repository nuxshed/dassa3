package com.dass.ims.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf

enum class UserRole { ADMIN, STUDENT }

class RoleState {
    var role by mutableStateOf(UserRole.ADMIN)
    var currentuserid by mutableStateOf(1L)

    fun switchrole() {
        if (role == UserRole.ADMIN) {
            role = UserRole.STUDENT
            currentuserid = 2L
        } else {
            role = UserRole.ADMIN
            currentuserid = 1L
        }
    }
}

val LocalRole = staticCompositionLocalOf { RoleState() }
