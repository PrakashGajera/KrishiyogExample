package app.krishiyog.data

import app.krishiyog.data.dto.list.ListData
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun requestListData(): Flow<Resource<ListData>>
}
