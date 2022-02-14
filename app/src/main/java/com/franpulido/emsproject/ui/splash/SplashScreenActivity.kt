package com.franpulido.emsproject.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.franpulido.emsproject.ui.common.startActivity
import com.franpulido.emsproject.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity<MainActivity> {}
        finishAfterTransition()
    }

}
