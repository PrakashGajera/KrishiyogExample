package app.krishiyog.data.remote

import app.krishiyog.data.Resource
import app.krishiyog.data.dto.list.ListData
import app.krishiyog.data.dto.list.ListDataItem
import app.krishiyog.data.error.NETWORK_ERROR
import app.krishiyog.data.error.NO_INTERNET_CONNECTION
import app.krishiyog.data.remote.service.ListService
import app.krishiyog.utils.NetworkConnectivity
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteData @Inject
constructor(private val serviceGenerator: ServiceGenerator, private val networkConnectivity: NetworkConnectivity) : RemoteDataSource {
    override suspend fun requestListData(): Resource<ListData> {
        val listService = serviceGenerator.createService(ListService::class.java)
        return when (val response = processCall(listService::fetchRecipes)) {
            is List<*> -> {
                Resource.Success(data = ListData(response as ArrayList<ListDataItem>))
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}
