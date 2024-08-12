package com.example.projet

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Products(

var id :Long=0,
var name :String="",
var emplacement :String="",
var TempMax :Double=0.0,
var TempMin :Double=0.0,
var HumidityMax :Double=0.0,
var HumidityMin :Double=0.0,
var date :String="",
var heure :String="",
var Latitude :Double=0.0,
var Longitude :Double=0.0,
var adresse :String=""
) : Parcelable

