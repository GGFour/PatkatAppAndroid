package com.example.mdp_frontend.depin


import com.example.mdp_frontend.data.repository.*
import com.example.mdp_frontend.domain.repository.AuthRepository
import com.example.mdp_frontend.domain.repository.CategoryRepository
import com.example.mdp_frontend.domain.repository.ListingRepository
import com.example.mdp_frontend.domain.use_case.category.CategoriesUseCases
import com.example.mdp_frontend.domain.use_case.category.GetCategories
import com.example.mdp_frontend.domain.use_case.listing.*
import com.example.mdp_frontend.domain.use_case.user.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ListingCollectionReference

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CategoryCollectionReference


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @ListingCollectionReference
    fun provideListingRef() = Firebase.firestore.collection("listings")

    @Provides
    fun provideListingRepository(
        @ListingCollectionReference
        listingRef: CollectionReference
    ): ListingRepository = ListingRepositoryImpl(listingRef)

    @Provides
    fun provideListingUseCases(
        repo: ListingRepository
    ) = ListingUseCases(
        addListing = AddListing(repo),
        getListingById = GetListingById(repo),
        getListingsByCategory = GetListingsByCategory(repo),
        getListings = GetListings (repo),
        reactOnListing = ReactOnListing(repo),
        acceptCallBack = AcceptCallBack(repo),
        rejectCallBack = RejectCallBack(repo),
        finishListing = FinishListing(repo),
    )

    @Provides
    @CategoryCollectionReference
    fun provideCategoryRef() = Firebase.firestore.collection("categories")

    @Provides
    fun provideCategoryRepository(
        @CategoryCollectionReference
        categoryRef: CollectionReference
    ): CategoryRepository = CategoryRepositoryImpl(categoryRef)

    @Provides
    fun provideCategoriesUseCases(
        repo: CategoryRepository
    ) = CategoriesUseCases(
        getCategories = GetCategories(repo)
    )

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl


    @Provides
    fun provideUserUseCases(
        repo: AuthRepository
    ) = UserUseCases(
        currentUser = CurrentUser(repo),
        login = Login(repo),
        register = Register(repo),
        logout = Logout(repo),
    )
}
