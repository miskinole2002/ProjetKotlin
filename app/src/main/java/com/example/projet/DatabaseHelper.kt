package com.example.projet

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.projet.DataBase.Companion

class DatabaseHelper(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
        companion object{
             const val DATABASE_NAME="MyDatabase.db"
             const val DATABASE_VERSION=1

             const  val TABLE_NAME= "Stock"
             const val  COLUMN_ID="id"
             const val COLUMN_NAME_PRODUCT="nom_produit "
             const val COLUMN_EMPLACEMENT="emplacement"
             const val COLUMN_TEMPMAX= "temperatureMax"
             const val COLUMN_TEMPMIN= "temperatureMin"
             const val COLUMN_HUMIDITYMAX= "humiditeMax"
             const val COLUMN_HUMIDITYMIN= "humiditeMin"
             const val COLUMN_DATE= "Date"
             const val COLUMN_HEURE= "Heure"
             const val COLUMN_LATITUDE = "latitude"
             const val COLUMN_LONGITUDE = "longitude"
             const val COLUMN_ADRESSE = "adresse"

        }

    override fun onCreate(db: SQLiteDatabase) {
        val TABLE_CREATE = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME_PRODUCT TEXT, " +
                "$COLUMN_EMPLACEMENT TEXT, " +
                "$COLUMN_HEURE TEXT, " +
                "$COLUMN_DATE TEXT, " +
                "$COLUMN_ADRESSE TEXT, " +
                "$COLUMN_HUMIDITYMAX INTEGER, " +
                "$COLUMN_HUMIDITYMIN INTEGER, " +
                "$COLUMN_LATITUDE REAL, " +
                "$COLUMN_LONGITUDE REAL, " +
                "$COLUMN_TEMPMAX REAL, " +
                "$COLUMN_TEMPMIN REAL)"
        db.execSQL(TABLE_CREATE)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun add(produit: Products) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME_PRODUCT, produit.name)
            put(COLUMN_EMPLACEMENT, produit.emplacement)
            put(COLUMN_DATE, produit.date)
            put(COLUMN_ADRESSE, produit.adresse)
            put(COLUMN_HEURE, produit.heure)
            put(COLUMN_LATITUDE, produit.Latitude)
            put(COLUMN_LONGITUDE, produit.Longitude)
            put(COLUMN_TEMPMAX, produit.TempMax)
            put(COLUMN_TEMPMIN, produit.TempMin)
            put(COLUMN_HUMIDITYMAX, produit.HumidityMax)
            put(COLUMN_HUMIDITYMIN, produit.HumidityMin)
        }
            db.insert(TABLE_NAME, null, values)
            db.close()

    }
}