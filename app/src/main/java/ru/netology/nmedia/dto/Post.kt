package ru.netology.nmedia.dto

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Long,
    val author: String?,
    val content: String?,
    val published: String?,
    val likes: Int = 0,
    val likedByMe: Boolean = false,
    val share: Int = 0,
    val video: String? = null
) : Parcelable, java.io.Serializable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(author)
        parcel.writeString(content)
        parcel.writeString(published)
        parcel.writeInt(likes)
        parcel.writeByte(if (likedByMe) 1 else 0)
        parcel.writeInt(share)
        parcel.writeString(video)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}