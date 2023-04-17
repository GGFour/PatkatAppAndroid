package com.example.mdp_frontend.data.repository

import com.example.mdp_frontend.depin.CategoryCollectionReference
import com.example.mdp_frontend.domain.model.Category
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.domain.repository.CategoriesResponse
import com.example.mdp_frontend.domain.repository.CategoryRepository
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    @CategoryCollectionReference private val categoryRef: CollectionReference
    ): CategoryRepository {

    override fun getCategories(): Flow<CategoriesResponse> = callbackFlow {
        val snapshotListener = categoryRef.orderBy("name").addSnapshotListener { snapshot, e ->
            val categoryResponse = if (snapshot != null) {
                val categories = snapshot.toObjects(Category::class.java)
                Response.Success(categories)
            } else {
                Response.Failure(e)
            } as CategoriesResponse
            trySend(categoryResponse)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }
}