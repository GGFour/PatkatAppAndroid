package com.example.mdp_frontend

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mdp_frontend.model.NavTabItem
import com.example.mdp_frontend.ui.all_categories.AllCategories
import com.example.mdp_frontend.ui.components.BottomNavigationBar
import com.example.mdp_frontend.ui.screen.*
import com.example.mdp_frontend.ui.screen.subscreen.*
import com.example.mdp_frontend.ui.theme.MDPfrontendTheme

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MDPfrontendTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    App()
                }
            }
        }
    }
}

enum class MainScreen(@StringRes val label: Int? = null, val icon: ImageVector? = null) {
    Home(label = R.string.bottom_nav_bar_item_label_home, icon = Icons.Outlined.Home),
    Map(label = R.string.bottom_nav_bar_item_label_map, icon = Icons.Outlined.Map),
    Chat(label = R.string.bottom_nav_bar_item_label_chat, icon = Icons.Outlined.Chat),
    Profile(label = R.string.bottom_nav_bar_item_label_profile, icon = Icons.Filled.Person),
    AllCategories,
    CategorySpecific,
    ListingDetails,
    PersonalInfo,
    Notifications,
    MyServices,
    MyTasks
}

@Composable
fun App() {
    val items = listOf(
        NavTabItem(stringResource(MainScreen.Home.label!!), MainScreen.Home.name, MainScreen.Home.icon!!),
        NavTabItem(stringResource(MainScreen.Map.label!!), MainScreen.Map.name, MainScreen.Map.icon!!),
        NavTabItem(stringResource(MainScreen.Chat.label!!), MainScreen.Chat.name, MainScreen.Chat.icon!!),
        NavTabItem(stringResource(MainScreen.Profile.label!!), MainScreen.Profile.name, MainScreen.Profile.icon!!),
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(
            items = items,
            modifier = Modifier,
            navController = navController,
            onItemClick = {
                navController.popBackStack()
                navController.navigate(it.route)
            }
        )},
    ) {
        Navigation(navController, modifier = Modifier.padding(paddingValues = it))
    }
}

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier) {
    val context = LocalContext.current as Activity
    NavHost(navController = navController, startDestination = MainScreen.Home.name) {
        composable(MainScreen.Home.name) {
            HomeScreen(
                modifier,
                onViewCategoriesClick = { navController.navigate(MainScreen.AllCategories.name) },
                onViewListingCardClick = { listingId ->
                    navController.navigate("ListingDetails/$listingId")
                },
                onCreateListingClick = {
                   val intent = Intent(context, CreateListingActivity::class.java)
                    context.startActivity(intent)
                },
                context = context
            )
        }
        composable(MainScreen.Map.name) {
            MapScreen(modifier)
        }
        composable(MainScreen.Chat.name) {
            ChatScreen(modifier)
        }
        composable(MainScreen.Profile.name) {
            ProfileScreen(
                modifier,
                onPersonalInfoChecked = { navController.navigate(MainScreen.PersonalInfo.name)},
                onNotificationsChecked = { navController.navigate(MainScreen.Notifications.name)},
                onMyServicesChecked = { navController.navigate(MainScreen.MyServices.name)},
                onMyTasksChecked = { navController.navigate(MainScreen.MyTasks.name)},

            )
        }
        composable(MainScreen.AllCategories.name) {
            AllCategories(
                onNavUp = { navController.navigateUp() },
                onCategoryBoxChecked = { navController.navigate(MainScreen.CategorySpecific.name) }
            )
        }
        composable(MainScreen.CategorySpecific.name) {
            Category_specificListing(
                modifier,
                onNavUp = { navController.navigateUp() },
                onListingCardClick = { listingId ->
                    // Navigate to the ListingDetails destination here
                    navController.navigate("ListingDetails/$listingId")
                },
                context = LocalContext.current,
            )
        }
        /*
         composable(MainScreen.ListingDetails.name) {
                 ListingDetailScreen(
                     Listing(
             id = "1",
             pictureUrl = "https://picsum.photos/200",
             title = "Sample Listing",
             description = "This is a sample listing for preview purposes." +
                     "senectus et netus et malesuada fames ac turpis egestas. " +
                     "Vestibulum tortor quam, feugiat vitae, ultricies eget, " +
                     "tempor sit amet, ante. Donec eu libero sit amet quam egestas semper." +
                     "Aenean ultricies mi vitae est",
                         publisher = User(name = "John Doe", imageUrl = "https://picsum.photos/40"),
             rating = 4,
             price = 99.99
         ),
                 onNavUp = { navController.navigateUp()}
         ) }

         */

        //new
        composable(
            route = "ListingDetails/{listingId}",
            arguments = listOf(navArgument("listingId") { type = NavType.StringType })
        ) { backStackEntry ->
            val listingId = backStackEntry.arguments?.getString("listingId")
            ListingDetailScreen(
                listingId = listingId,
                onNavUp = { navController.navigateUp() }
            )
        }

        //navigation for profile sub-screens
        composable(MainScreen.PersonalInfo.name) {
            PersonalInfo( onNavUp = { navController.navigateUp() })
        }
        composable(MainScreen.Notifications.name) {
            Notifications( onNavUp = { navController.navigateUp() })
        }
            /*
        composable(MainScreen.MyServices.name) {
            MyServices( onNavUp = { navController.navigateUp() })
        }

        composable(MainScreen.MyTasks.name) {
            MyTasks( onNavUp = { navController.navigateUp() })
        }
             */



    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MDPfrontendTheme {
        App()
    }
}