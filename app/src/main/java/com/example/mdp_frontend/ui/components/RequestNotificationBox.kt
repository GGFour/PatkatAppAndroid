package com.example.mdp_frontend.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.mdp_frontend.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mdp_frontend.model.RequestNotificationBoxItem
import com.example.mdp_frontend.ui.theme.md_theme_light_tertiaryContainer

@Composable
fun RequestNotificationBox(
    jobRequest: RequestNotificationBoxItem,
    onAcceptClick: () -> Unit,
    onDeclineClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = md_theme_light_tertiaryContainer,
                shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        bitmap = jobRequest.taskerPicture,
                        contentDescription = "Tasker Picture",
                        modifier = Modifier.size(40.dp).clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))


                    Column {
                        Text(text = jobRequest.taskerName)
                        /*Rating(
                            value = jobRequest.taskerRating,
                            size = 20.dp,
                            modifier = Modifier.padding(end = 2.dp),
                            isIndicator = true
                        )*/


                    }
                }
                Text(text = jobRequest.requestTime)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Service", style = MaterialTheme.typography.headlineSmall)
            Text(text = jobRequest.serviceName)
            Spacer(modifier = Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()) {
                Button(onClick = onDeclineClick) {
                    Text(text = "Decline")
                }
                Button(onClick = onAcceptClick) {
                    Text(text = "Accept")
                }
            }
        }
    }
}
//it will be deleted!


@Preview
@Composable
fun RequestNotificationBoxPreview() {
    val jobRequest = RequestNotificationBoxItem(
        taskerPicture = ImageBitmap.imageResource(R.drawable.demo),
        taskerName = "John Doe",
        taskerRating = 4.5f,
        requestTime = "5 mins ago",
        //serviceTitle = "Service",
        serviceName = "Cleaning"
    )

    RequestNotificationBox(
        jobRequest= jobRequest,
        onAcceptClick = { /* TODO: Handle accept click */ },
        onDeclineClick = { /* TODO: Handle decline click */ }
    )
}
