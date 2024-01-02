package com.focalors.hireai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.focalors.hireai.ui.navigation.Screen
import com.focalors.hireai.ui.screen.splash.SplashViewModel
import com.focalors.hireai.ui.theme.PrimaryGray200
import com.focalors.hireai.ui.theme.VirtuHireTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition {
            splashViewModel.splashState.value.isLoading
        }

        setContent {
            VirtuHireTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PrimaryGray200
                ) {
                    val startDestination =
                        if (splashViewModel.splashState.value.isLoggedIn) {
                            if (splashViewModel.splashState.value.data?.profile != null) {
                                Screen.Home.route
                            } else {
                                Screen.Introduction.route
                            }
                        } else {
                            Screen.Onboard.route
                        }
                    // Workaround, soon to be fixed
                    if (splashViewModel.splashState.value.isLoading) {
                        Box(modifier = Modifier)
                    } else {
                        VirtuHireApp(startDestination = startDestination)
                    }

                }
            }
        }
    }
}