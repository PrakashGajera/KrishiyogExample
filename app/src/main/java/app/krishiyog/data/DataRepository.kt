package app.krishiyog.data

import app.krishiyog.data.dto.list.ListData
import app.krishiyog.data.local.LocalData
import app.krishiyog.data.remote.RemoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DataRepository @Inject constructor(private val remoteRepository: RemoteData, private val localRepository: LocalData, private val ioDispatcher: CoroutineContext) : DataRepositorySource {

    override suspend fun requestListData(): Flow<Resource<ListData>> {
        return flow {
            emit(remoteRepository.requestListData())
        }.flowOn(ioDispatcher)
    }
}
