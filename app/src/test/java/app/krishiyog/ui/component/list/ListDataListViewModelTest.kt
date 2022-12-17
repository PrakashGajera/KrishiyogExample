package app.krishiyog.ui.component.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.krishiyog.data.DataRepository
import app.krishiyog.data.Resource
import app.krishiyog.data.dto.list.ListData
import app.krishiyog.data.error.NETWORK_ERROR
import app.util.InstantExecutorExtension
import app.util.MainCoroutineRule
import app.util.TestModelsGenerator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class ListDataListViewModelTest {
    // Subject under test
    private lateinit var listViewModel: ListViewModel

    // Use a fake UseCase to be injected into the viewModel
    private val dataRepository: DataRepository = mockk()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var recipeTitle: String
    private val testModelsGenerator: TestModelsGenerator = TestModelsGenerator()

    @Before
    fun setUp() {
        // Create class under test
        // We initialise the repository with no tasks
        recipeTitle = testModelsGenerator.getStubSearchTitle()
    }

    @Test
    fun `get Recipes List`() {
        // Let's do an answer for the liveData
        val recipesModel = testModelsGenerator.generateRecipes()

        //1- Mock calls
        coEvery { dataRepository.requestListData() } returns flow {
            emit(Resource.Success(recipesModel))
        }

        //2-Call
        listViewModel = ListViewModel(dataRepository)
        listViewModel.getListData()
        //active observer for livedata
        listViewModel.listDataLiveData.observeForever { }

        //3-verify
        val isEmptyList = listViewModel.listDataLiveData.value?.data?.listDataItems.isNullOrEmpty()
        assertEquals(recipesModel, listViewModel.listDataLiveData.value?.data)
        assertEquals(false,isEmptyList)
    }

    @Test
    fun `get Recipes Empty List`() {
        // Let's do an answer for the liveData
        val recipesModel = testModelsGenerator.generateRecipesModelWithEmptyList()

        //1- Mock calls
        coEvery { dataRepository.requestListData() } returns flow {
            emit(Resource.Success(recipesModel))
        }

        //2-Call
        listViewModel = ListViewModel(dataRepository)
        listViewModel.getListData()
        //active observer for livedata
        listViewModel.listDataLiveData.observeForever { }

        //3-verify
        val isEmptyList = listViewModel.listDataLiveData.value?.data?.listDataItems.isNullOrEmpty()
        assertEquals(recipesModel, listViewModel.listDataLiveData.value?.data)
        assertEquals(true, isEmptyList)
    }

    @Test
    fun `get Recipes Error`() {
        // Let's do an answer for the liveData
        val error: Resource<ListData> = Resource.DataError(NETWORK_ERROR)

        //1- Mock calls
        coEvery { dataRepository.requestListData() } returns flow {
            emit(error)
        }

        //2-Call
        listViewModel = ListViewModel(dataRepository)
        listViewModel.getListData()
        //active observer for livedata
        listViewModel.listDataLiveData.observeForever { }

        //3-verify
        assertEquals(NETWORK_ERROR, listViewModel.listDataLiveData.value?.errorCode)
    }
}
