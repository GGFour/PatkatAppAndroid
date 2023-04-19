package com.example.mdp_frontend.ui.create_listing.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter

@Composable
fun SelectPictureScreen(
    selectedPicture: Uri?,
    updatePicture: (Uri?) -> Unit,
    onNextPressed: () -> Unit,
    onCancelPressed: () -> Unit,
) {
    val galleryLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(), onResult = updatePicture)
    ListingScreenContainer(onNextPressed = onNextPressed, onCancelPressed = onCancelPressed) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (selectedPicture != null) {
                Image(
                    painter = rememberAsyncImagePainter(model = selectedPicture),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxWidth()
                )
                ElevatedButton(onClick = {
                    galleryLauncher.launch("image/*")
                }
                ) {
                    Text(text = "Update Image")
                }
            } else {
                ElevatedButton(onClick = {
                    galleryLauncher.launch("image/*")
                }
                ) {
                    Text(text = "Select Image")
                }
            }
        }
    }
}
