package app.krishiyog.data.remote.service

import app.krishiyog.data.dto.list.ListDataItem
import retrofit2.Response
import retrofit2.http.GET

interface ListService {
    @GET("testing")
    suspend fun fetchRecipes(): Response<List<ListDataItem>>
}
