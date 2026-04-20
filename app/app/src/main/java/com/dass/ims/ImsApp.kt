package com.dass.ims

import android.app.Application
import com.dass.ims.data.AppContainer

class ImsApp : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
