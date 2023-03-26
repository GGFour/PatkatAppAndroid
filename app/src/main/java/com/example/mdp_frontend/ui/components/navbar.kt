package com.example.mdp_frontend.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mdp_frontend.model.NavTabItem


@Composable
fun BottomNavigationBar(
    items: List<NavTabItem>,
    navController: NavController,
    modifier: Modifier,
    onItemClick: (NavTabItem) -> Unit,
    ) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        modifier = modifier,
    ) {
        items.forEachIndexed {index, item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                icon = {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                        if (item.badgeCount >= 0) {
                            BadgedBox(
                                badge = {
                                    Badge {
                                        if (item.badgeCount > 0) {
                                            Text(text = item.badgeCount.toString())
                                        }
                                    }
                                }
                                
                            ) {
                                Icon(imageVector = item.icon, contentDescription = item.label)
                            }
                        } else {
                            Icon(imageVector = item.icon, contentDescription = item.label)
                        }
                    }
                }
            )
        }
    }

}