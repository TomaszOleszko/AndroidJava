package com.example.file_downloader2

import android.os.Parcel
import android.os.Parcelable

class PostepInfo() : Parcelable{
    var mPobranychBajtow = 0
    var mRozmiar = 0
    var mStatus = ""

    constructor(parcel: Parcel) : this() {
        mPobranychBajtow = parcel.readInt()
        mRozmiar = parcel.readInt()
        mStatus = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(mPobranychBajtow)
        parcel.writeInt(mRozmiar)
        parcel.writeString(mStatus)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostepInfo> {
        override fun createFromParcel(parcel: Parcel): PostepInfo {
            return PostepInfo(parcel)
        }

        override fun newArray(size: Int): Array<PostepInfo?> {
            return arrayOfNulls(size)
        }
    }
}