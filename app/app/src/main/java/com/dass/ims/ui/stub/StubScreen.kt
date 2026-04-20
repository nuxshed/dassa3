package com.dass.ims.ui.stub

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun StubScreen(navController: NavController, module: String) {
    Text("Stub: $module")
}
