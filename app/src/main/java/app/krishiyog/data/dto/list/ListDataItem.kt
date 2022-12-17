package app.krishiyog.data.dto.list

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = false)
@Parcelize
data class ListDataItem(
        @Json(name = "name")
        val bname: String = "",
        @Json(name = "author")
        val author: String = "",
        @Json(name = "avatar")
        val avatar: String = "",
        @Json(name = "description")
        val description: String = "",
        @Json(name = "language")
        val language: String = "",
        @Json(name = "stars")
        val stars: String = "",
        @Json(name = "forks")
        val forks: String = "",
//        @Json(name = "builtBy")
//        val user: BuiltBy = BuiltBy(),
) : Parcelable
