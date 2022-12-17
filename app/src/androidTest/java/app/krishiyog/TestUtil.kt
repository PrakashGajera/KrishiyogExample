package app.krishiyog

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import app.krishiyog.data.dto.list.ListData
import app.krishiyog.data.dto.list.ListDataItem
import app.krishiyog.data.remote.moshiFactories.MyKotlinJsonAdapterFactory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.InputStream
import java.lang.reflect.Type

object TestUtil {
    var dataStatus: DataStatus = DataStatus.Success
    var listData: ListData = ListData(arrayListOf())
    fun initData(): ListData {
        val moshi = Moshi.Builder()
                .add(MyKotlinJsonAdapterFactory())
                .add(_root_ide_package_.app.krishiyog.data.remote.moshiFactories.MyStandardJsonAdapters.FACTORY)
                .build()
        val type: Type = Types.newParameterizedType(List::class.java, ListDataItem::class.java)
        val adapter: JsonAdapter<List<ListDataItem>> = moshi.adapter(type)
        val jsonString = getJson("RecipesApiResponse.json")
        adapter.fromJson(jsonString)?.let {
            listData = ListData(ArrayList(it))
            return listData
        }
        return ListData(arrayListOf())
    }

    private fun getJson(path: String): String {
        val ctx: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val inputStream: InputStream = ctx.classLoader.getResourceAsStream(path)
        return inputStream.bufferedReader().use { it.readText() }
    }
}
