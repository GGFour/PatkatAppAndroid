package com.example.mdp_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mdp_frontend.ui.create_listing.CreateListingScreen
import com.example.mdp_frontend.ui.theme.MDPfrontendTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateListingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MDPfrontendTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // TODO: return listing id from activity
                    // TODO: add creator of the listing
                    CreateListingScreen(
                        closeActivity = { finish() },
                        onListingCreated = { finish() }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateListingActivityPreview() {
    MDPfrontendTheme {
        CreateListingScreen()
    }
}