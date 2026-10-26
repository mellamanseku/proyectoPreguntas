package com.example.pglpreguntas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pglpreguntas.navigation.Navigation
import com.example.pglpreguntas.ui.theme.PGLTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PGLTheme { Navigation() }
        }
    }
}
