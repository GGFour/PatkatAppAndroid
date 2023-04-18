package com.example.mdp_frontend.ui.screen

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

import com.example.mdp_frontend.viewmodel.CreateListingFormViewModel
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
private fun PickCategoryScreen(
    category: String?,
    updateCategory: (String?) -> Unit,
    categoriesResponse: CategoriesResponse,
    onNextPressed: () -> Unit,
    onCancelPressed: () -> Unit,
) {
    ListingScreenContainer(onNextPressed = onNextPressed, onCancelPressed = onCancelPressed) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var expanded by remember { mutableStateOf(false) }
            when (categoriesResponse) {
                is Response.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
                is Response.Success -> {
                    TextButton(
                        onClick = { expanded = true},
                    ) {
                        Text(category ?: "Select the category")
                        Icon(
                            imageVector = if (expanded) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                            contentDescription = if (expanded) "collapse" else "expand",
                        )
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        categoriesResponse.data.forEach { category: Category ->
                            DropdownMenuItem(text = { Text(category.name) }, onClick = {
                                updateCategory(category.name)
                                expanded = false
                            })
                        }
                    }
                }
                is Response.Failure -> {
                    Text("Smth went wrong. . .")
                }
            }

        }
    }
}

@Composable
private fun GetLocationScreen(
    latitude: Double,
    longitude: Double,
    updateLatitude: (Double) -> Unit,
    updateLongitude: (Double) -> Unit,
    onNextPressed: () -> Unit,
    onCancelPressed: () -> Unit,
) {
    val context = LocalContext.current
    var latInput: String by remember { mutableStateOf(latitude.toString()) }
    var lonInput: String by remember { mutableStateOf(longitude.toString()) }

    // Location hell -- needs to be refactored, but not now
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            for (lo in p0.locations) {
                // Update UI with location data
                updateLatitude(lo.latitude)
                latInput = lo.latitude.toString()
                updateLongitude(lo.longitude)
                lonInput = lo.longitude.toString()
            }
        }
    }
    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        locationCallback.let {
            val locationRequest = LocationRequest.create().apply {
                interval = 10000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                launcherMultiplePermissions.launch(permissions)
                return
            }
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }

    ListingScreenContainer(onNextPressed = onNextPressed, onCancelPressed = onCancelPressed) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = latInput,
                onValueChange = {
                    latInput = it.replace(',', '.')
                    updateLatitude(latInput.toDoubleOrNull() ?: 0.0)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Latitude") }
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                value = lonInput,
                onValueChange = {
                    lonInput = it.replace(',', '.')
                    updateLongitude(lonInput.toDoubleOrNull() ?: 0.0)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Longitude") }
            )
            Button(onClick = {
                if (permissions.all {
                        ContextCompat.checkSelfPermission(
                            context,
                            it
                        ) == PackageManager.PERMISSION_GRANTED
                    }) {
                    // Get the location
                    startLocationUpdates()
                } else {
                    launcherMultiplePermissions.launch(permissions)
                }
            }) {
                Text(text = "Get current location")
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
