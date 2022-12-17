package app.util

import app.krishiyog.data.dto.list.ListData
import app.krishiyog.data.dto.list.ListDataItem
import app.krishiyog.data.remote.moshiFactories.MyKotlinJsonAdapterFactory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.File
import java.lang.reflect.Type

class TestModelsGenerator {
    private var listData: ListData = ListData(arrayListOf())

    init {
        val moshi = Moshi.Builder()
                .add(MyKotlinJsonAdapterFactory())
                .add(_root_ide_package_.app.krishiyog.data.remote.moshiFactories.MyStandardJsonAdapters.FACTORY)
                .build()
        val type: Type = Types.newParameterizedType(List::class.java, ListDataItem::class.java)
        val adapter: JsonAdapter<List<ListDataItem>> = moshi.adapter(type)
        val jsonString = getJson("RecipesApiResponse.json")
        adapter.fromJson(jsonString)?.let {
            listData = ListData(ArrayList(it))
        }
        print("this is $listData")
    }

    fun generateRecipes(): ListData {
        return listData
    }

    fun generateRecipesModelWithEmptyList(): ListData {
        return ListData(arrayListOf())
    }

    fun generateRecipesItemModel(): ListDataItem {
        return listData.listDataItems[0]
    }

    fun getStubSearchTitle(): String {
        return listData.listDataItems[0].bname
    }


    /**
     * Helper function which will load JSON from
     * the path specified
     *
     * @param path : Path of JSON file
     * @return json : JSON from file at given path
     */

    private fun getJson(path: String): String {
        // Load the JSON response
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }
}
