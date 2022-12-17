package app.krishiyog.data.remote

import app.krishiyog.data.Resource
import app.krishiyog.data.dto.list.ListData

internal interface RemoteDataSource {
    suspend fun requestListData(): Resource<ListData>
}
