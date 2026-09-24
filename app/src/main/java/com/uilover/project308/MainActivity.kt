package com.uilover.project308

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.uilover.project308.ui.navigation.SorceNavHost
import com.uilover.project308.ui.theme.Project308Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Project308Theme {
                SorceNavHost()
            }
        }
    }
}