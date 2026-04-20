package com.dass.ims

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.dass.ims.data.LocalRole
import com.dass.ims.data.RoleState
import com.dass.ims.navigation.ImsNavHost
import com.dass.ims.ui.theme.ImsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val rolestate = remember { RoleState() }
            ImsTheme {
                CompositionLocalProvider(LocalRole provides rolestate) {
                    ImsNavHost()
                }
            }
        }
    }
}
