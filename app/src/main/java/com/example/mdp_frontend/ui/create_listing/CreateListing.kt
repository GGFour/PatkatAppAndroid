package com.example.mdp_frontend.ui.create_listing

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EuroSymbol
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.mdp_frontend.R
import com.example.mdp_frontend.domain.model.Category
import com.example.mdp_frontend.domain.model.Listing
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.domain.repository.CategoriesResponse
import com.example.mdp_frontend.model.TopBarItem
import com.example.mdp_frontend.ui.components.TopBar
import com.example.mdp_frontend.ui.create_listing.screen.*

import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

enum class CreateListing(@StringRes val title: Int) {
    Title(title = R.string.create_listing_title_title),
    Description(title = R.string.create_listing_title_description),
    Photo(title = R.string.create_listing_title_photo),
    Price(title = R.string.create_listing_title_price),
    Location(title = R.string.create_listing_title_location),
    Category(title = R.string.create_listing_title_category),
    Review(title = R.string.create_listing_title_review),;
}
@Composable
fun CreateListingScreen (
    viewModel: CreateListingFormViewModel = hiltViewModel(),
    closeActivity: () -> Unit = {},
    onListingCreated: () -> Unit = {},
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CreateListing.valueOf(
        backStackEntry?.destination?.route ?: CreateListing.Title.name
    )
    val uiState = viewModel.uiState.collectAsState()
    Scaffold (
        topBar = { TopBar(
            TopBarItem(
            title = stringResource(id = currentScreen.title),
            drawNavUp = currentScreen != CreateListing.Title,
            drawClose = currentScreen == CreateListing.Title,
            onNavUpPressed = { navController.navigateUp() },
            onClosePressed = { closeActivity() },
        )
        ) },
            ){
        if (viewModel.addListingResponse == Response.Success(true)) {
            onListingCreated()
        }
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NavHost(navController = navController, startDestination = CreateListing.Title.name) {
                composable(CreateListing.Title.name) {
                    WriteTitleScreen(
                        value= uiState.value.title,
                        onValueChanged = { viewModel.updateTitle(it) },
                        onNextPressed = { navController.navigate(CreateListing.Description.name) },
                        onCancelPressed = { cancelAndNavigateToStart(viewModel, navController) },
                        title = stringResource(id = currentScreen.title),
                    )
                }
                composable(CreateListing.Description.name) {
                    WriteDescriptionScreen(
                        value= uiState.value.description,
                        onValueChanged = {viewModel.updateDescription(it)},
                        onNextPressed = { navController.navigate(CreateListing.Category.name) },
                        onCancelPressed = { cancelAndNavigateToStart(viewModel, navController) },
                        title = stringResource(id = currentScreen.title),
                    )
                }
                composable(CreateListing.Category.name) {
                    PickCategoryScreen(
                        category = uiState.value.category,
                        updateCategory = { viewModel.updateCategory(it) },
                        categoriesResponse = viewModel.categoriesResponse,
                        onNextPressed = { navController.navigate(CreateListing.Photo.name) },
                        onCancelPressed = { cancelAndNavigateToStart(viewModel, navController) }
                    )
                }
                composable(CreateListing.Photo.name) {
                    SelectPictureScreen(
                        selectedPicture = if (uiState.value.pictureUri != null) Uri.parse(uiState.value.pictureUri) else null,
                        updatePicture = { viewModel.updateImageUri(it) },
                        onNextPressed = { navController.navigate(CreateListing.Price.name) },
                        onCancelPressed = { cancelAndNavigateToStart(viewModel, navController) },
                    )
                }
                composable(CreateListing.Price.name) {
                    SelectPriceScreen(
                        value = uiState.value.priceStr,
                        onValueChanged = { viewModel.updatePrice(it) },
                        onNextPressed = { navController.navigate(CreateListing.Location.name) },
                        onCancelPressed = { cancelAndNavigateToStart(viewModel, navController) },
                        title = stringResource(id = currentScreen.title),
                    )
                }
            //  TODO:
            //  upload listing photo
                composable(CreateListing.Location.name) {
                    GetLocationScreen(
                        latitude = uiState.value.latitude ?: 0.0,
                        longitude = uiState.value.longitude ?: 0.0,
                        updateLatitude = { viewModel.updateLatitude(it) },
                        updateLongitude = { viewModel.updateLongitude(it) },
                        onNextPressed = { navController.navigate(CreateListing.Review.name) },
                        onCancelPressed = { cancelAndNavigateToStart(viewModel, navController) },
                    )
                }
                composable(CreateListing.Review.name) {
                    ReviewListingScreen(
                        listing = uiState.value,
                        onSubmitPressed = { viewModel.addListing() },
                        onCancelPressed = { cancelAndNavigateToStart(viewModel, navController) })
                }
            }
        }
    }
}

private fun cancelAndNavigateToStart(
    viewModel: CreateListingFormViewModel,
    navController: NavHostController,
) {
    viewModel.reset()
    navController.popBackStack(CreateListing.Title.name, inclusive = false)
}
