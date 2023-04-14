package com.example.mdp_frontend.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EuroSymbol
import androidx.compose.material.icons.outlined.EuroSymbol
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.mdp_frontend.R
import com.example.mdp_frontend.domain.model.Listing
import com.example.mdp_frontend.model.TopBarItem
import com.example.mdp_frontend.ui.components.TopBar
import com.example.mdp_frontend.ui.screen.subscreen.ListingDetailScreen

import com.example.mdp_frontend.viewmodel.CreateListingFormViewModel

enum class CreateListing(@StringRes val title: Int) {
    Title(title = R.string.create_listing_title_title),
    Description(title = R.string.create_listing_title_description),
    Photo(title = R.string.create_listing_title_photo),
    Price(title = R.string.create_listing_title_price),
    Location(title = R.string.create_listing_title_location),
    Category(title = R.string.create_listing_title_category),
    Review(title = R.string.create_listing_title_review),;
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateListingScreen (
    viewModel: CreateListingFormViewModel = hiltViewModel(),
    closeActivity: () -> Unit = {},
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CreateListing.valueOf(
        backStackEntry?.destination?.route ?: CreateListing.Title.name
    )
    val uiState = viewModel.uiState.collectAsState()
    Scaffold (
        topBar = { TopBar(TopBarItem(
            title = stringResource(id = currentScreen.title),
            drawNavUp = currentScreen != CreateListing.Title,
            drawClose = currentScreen == CreateListing.Title,
            onNavUpPressed = { navController.navigateUp() },
            onClosePressed = { closeActivity() },
        )) },
            ){
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NavHost(navController = navController, startDestination = CreateListing.Title.name) {
                composable(CreateListing.Title.name) {
                    PromptTextScreen(
                        value= uiState.value.title,
                        onValueChanged = { viewModel.updateTitle(it) },
                        onNextPressed = { navController.navigate(CreateListing.Description.name) },
                        onCancelPressed = { cancelAndNavigateToStart(viewModel, navController) },
                        title = stringResource(id = currentScreen.title),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.headlineSmall,
                    )
                }
                composable(CreateListing.Description.name) {
                    PromptTextScreen(
                        value= uiState.value.description,
                        onValueChanged = {viewModel.updateDescription(it)},
                        onNextPressed = { navController.navigate(CreateListing.Photo.name) },
                        onCancelPressed = { cancelAndNavigateToStart(viewModel, navController) },
                        title = stringResource(id = currentScreen.title),
                        singleLine = false,
                    )
                }
                composable(CreateListing.Photo.name) {
                    SelectPicture(
                        selectedPicture = uiState.value.pictureUri,
                        updatePicture = { viewModel.updateImageUri(it) },
                        onNextPressed = { navController.navigate(CreateListing.Price.name) },
                        onCancelPressed = { cancelAndNavigateToStart(viewModel, navController) },

                    )
                }
                composable(CreateListing.Price.name) {
                    PromptTextScreen(
                        value= uiState.value.priceStr,
                        onValueChanged = {viewModel.updatePrice(it)},
                        onNextPressed = { navController.navigate(CreateListing.Review.name) },
                        onCancelPressed = { cancelAndNavigateToStart(viewModel, navController) },
                        title = stringResource(id = currentScreen.title),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.headlineLarge,
                        trailingIcon = { Icon(Icons.Outlined.EuroSymbol, null) }
                    )
                }
            //  TODO:
            //  (CreateListing.Photo.name) {}
            //  composable(CreateListing.Location.name) {}
            //  composable(CreateListing.Category.name) {}
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

@Composable
fun PromptTextScreen(
    value: String,
    onValueChanged: (String) -> Unit,
    onNextPressed: () -> Unit,
    onCancelPressed: () -> Unit,
    title: String,
    singleLine: Boolean,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    trailingIcon: @Composable () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChanged,
            label = { Text(title) },
            singleLine = singleLine,
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            textStyle = textStyle,
            trailingIcon = trailingIcon,

        )
        ElevatedButton(onClick = onNextPressed ) {
            Text(text = "Continue")
        }
        OutlinedButton(onClick = onCancelPressed) {
            Text(text = "Cancel")
        }
    }
}

@Composable
fun SelectPicture(
    selectedPicture: Uri?,
    updatePicture: (Uri?) -> Unit,
    onNextPressed: () -> Unit,
    onCancelPressed: () -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(), onResult = updatePicture)
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (selectedPicture != null) {
            Image(
                painter = rememberAsyncImagePainter(model = selectedPicture),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        launcher.launch("image/*")
                    }
            )
        } else {
            ElevatedButton(onClick = {
                launcher.launch("image/*")
            }
            ) {
                Text(text = "Select Image")
            }
        }
        ElevatedButton(onClick = onNextPressed ) {
            Text(text = "Continue")
        }
        OutlinedButton(onClick = onCancelPressed) {
            Text(text = "Cancel")
        }
    }
}

@Composable
fun ReviewListingScreen(
    listing: Listing,
    onSubmitPressed: () -> Unit,
    onCancelPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.Start,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ElevatedButton(onClick = onSubmitPressed ) {
                Text(text = "Submit")
            }
            OutlinedButton(onClick = onCancelPressed) {
                Text(text = "Cancel")
            }
        }
        //temporary!!
        val navController = rememberNavController()
        ListingDetailScreen(listing = listing , onNavUp = {navController.navigateUp()})
    }
}
