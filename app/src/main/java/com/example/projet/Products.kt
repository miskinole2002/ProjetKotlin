package com.example.projet

import android.os.Parcel
import android.os.Parcelable



data class Products(

var id :Long=0,
var name :String="",
var TempMax :Double=0.0,
var TempMin :Double=0.0,
var HumidityMax :Double=0.0,
var HumidityMin :Double=0.0,
var date :String="",
var heure :String="",
var Latitude :Double=0.0,
var Longitude :Double=0.0,
var adresse :String=""
) : Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Products> {
        override fun createFromParcel(parcel: Parcel): Products {
            return Products(parcel)
        }

        override fun newArray(size: Int): Array<Products?> {
            return arrayOfNulls(size)
        }
    }
}
