package app.krishiyog

import app.krishiyog.TestUtil.dataStatus
import app.krishiyog.TestUtil.initData
import app.krishiyog.data.DataRepositorySource
import app.krishiyog.data.Resource
import app.krishiyog.data.dto.list.ListData
import app.krishiyog.data.error.NETWORK_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TestDataRepository @Inject constructor() : DataRepositorySource {

    override suspend fun requestListData(): Flow<Resource<ListData>> {
        return when (dataStatus) {
            DataStatus.Success -> {
                flow { emit(Resource.Success(initData())) }
            }
            DataStatus.Fail -> {
                flow { emit(Resource.DataError<ListData>(errorCode = NETWORK_ERROR)) }
            }
            DataStatus.EmptyResponse -> {
                flow { emit(Resource.Success(ListData(arrayListOf()))) }
            }
        }
    }
}
