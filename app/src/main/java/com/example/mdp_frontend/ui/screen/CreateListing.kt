package com.example.mdp_frontend.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EuroSymbol
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.model.TopBarItem
import com.example.mdp_frontend.ui.components.TopBar

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
                        onNextPressed = { navController.navigate(CreateListing.Photo.name) },
                        onCancelPressed = { cancelAndNavigateToStart(viewModel, navController) },
                        title = stringResource(id = currentScreen.title),
                    )
                }
                composable(CreateListing.Photo.name) {
                    SelectPictureScreen(
                        selectedPicture = uiState.value.pictureUri,
                        updatePicture = { viewModel.updateImageUri(it) },
                        onNextPressed = { navController.navigate(CreateListing.Price.name) },
                        onCancelPressed = { cancelAndNavigateToStart(viewModel, navController) },
                    )
                }
                composable(CreateListing.Price.name) {
                    SelectPriceScreen(
                        value= uiState.value.priceStr,
                        onValueChanged = { viewModel.updatePrice(it) },
                        onNextPressed = { navController.navigate(CreateListing.Review.name) },
                        onCancelPressed = { cancelAndNavigateToStart(viewModel, navController) },
                        title = stringResource(id = currentScreen.title),
                    )
                }
            //  TODO:
            //  upload listing photo
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
fun WriteTitleScreen(
    title: String,
    value: String,
    onValueChanged: (String) -> Unit,
    onNextPressed: () -> Unit,
    onCancelPressed: () -> Unit,
) {
    ListingScreenContainer(onNextPressed = onNextPressed, onCancelPressed = onCancelPressed) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChanged,
                label = { Text(title) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                textStyle = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}
@Composable
private fun WriteDescriptionScreen(
    title: String,
    value: String,
    onValueChanged: (String) -> Unit,
    onNextPressed: () -> Unit,
    onCancelPressed: () -> Unit,
) {
    ListingScreenContainer(onNextPressed = onNextPressed, onCancelPressed = onCancelPressed) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChanged,
                label = { Text(title) },
                singleLine = false,
                minLines = 3,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                textStyle = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
private fun SelectPriceScreen(
    title: String,
    value: String,
    onValueChanged: (String) -> Unit,
    onNextPressed: () -> Unit,
    onCancelPressed: () -> Unit,
) {
    ListingScreenContainer(onNextPressed = onNextPressed, onCancelPressed = onCancelPressed) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChanged,
                label = { Text(title) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number,
                ),
                textStyle = MaterialTheme.typography.headlineLarge,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.EuroSymbol,
                        contentDescription = null
                    )
                },
            )
        }
    }
}

@Composable
private fun SelectPictureScreen(
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

@Composable
private fun ReviewListingScreen(
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
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Title", style = MaterialTheme.typography.headlineSmall)
            Text(text = listing.title, style = MaterialTheme.typography.bodyMedium)
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            Text(text = "Description", style = MaterialTheme.typography.headlineSmall)
            Text(text = listing.description, style = MaterialTheme.typography.bodyMedium)
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            Text(text = "Price", style = MaterialTheme.typography.headlineSmall)
            Text(text = listing.priceStr, style = MaterialTheme.typography.bodyMedium)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ElevatedButton(onClick = onSubmitPressed ) {
                Text(text = "Submit")
            }
            OutlinedButton(onClick = onCancelPressed) {
                Text(text = "Cancel")
            }
        }
    }
}

@Composable
private fun ListingScreenContainer(
    onNextPressed: () -> Unit,
    onCancelPressed: () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        content()
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(onClick = onCancelPressed) {
                Text(text = "Cancel")
            }
            ElevatedButton(onClick = onNextPressed) {
                Text(text = "Next")
            }
        }
    }
}
