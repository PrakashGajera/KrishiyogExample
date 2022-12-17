package app.krishiyog.data.dto.list

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class BuiltBy(
    @Json(name = "href")
    val href: String = "",
    @Json(name = "avatar")
    val avatar: String = "",
    @Json(name = "username")
    val username: String = ""
) : Parcelable
