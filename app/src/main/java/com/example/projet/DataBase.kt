package com.example.projet

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DataBase(context: Context):SQLiteOpenHelper(context,DATABASE_NAME, null,DATABASE_VERSION )
{
    // nom de la table et colonnes
    companion object{

        private const val DATABASE_NAME = "MyDatabase.db"
        private const val DATABASE_VERSION = 1
        private const  val TABLE_NAME= "Stock"
        private const val  COLUMN_ID="id"
        private const val COLUMN_NAME_PRODUCT="nom_produit "
        private const val COLUMN_EMPLACEMENT="emplacement"
        private const val COLUMN_TEMPMAX= "temperatureMax"
        private const val COLUMN_TEMPMIN= "temperatureMin"
        private const val COLUMN_HUMIDITYMAX= "humiditeMax"
        private const val COLUMN_HUMIDITYMIN= "humiditeMin"
        private const val COLUMN_DATE= "Date"
        private const val COLUMN_HEURE= "Heure"
        private const val COLUMN_LATITUDE = "latitude"
        private const val COLUMN_LONGITUDE = "longitude"
        private const val COLUMN_ADRESSE = "adresse"


        private const val TABLE_CREATE = "CREATE TABLE $TABLE_NAME (" +
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

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLE_CREATE) // Création de la table SQLite

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference = database.getReference("Stock")

    fun ajouterProduit(produit:Products ): Long {
        // Ajout à SQLite
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
        val id = db.insert(TABLE_NAME, null, values)

        // Ajout à Firebase
        produit.id = id
        reference.child(id.toString()).setValue(produit)

        return id
    }
    fun allProduct(callback: (List<Products>) -> Unit) {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val produits = mutableListOf<Products>()
                for (data in snapshot.children) {
                    val produit = data.getValue(Products::class.java)
                    if (produit!= null) {
                        produits.add(produit)
                    }
                }
                callback(produits)
            }

            override fun onCancelled(error: DatabaseError) {
                // Gestion des erreurs
            }
        })
    }

    fun updateProduct(produit: Products): Int {
        // Mise à jour dans SQLite
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME_PRODUCT, produit.name)
            put(COLUMN_EMPLACEMENT, produit.emplacement)
            put(COLUMN_DATE, produit.date )
            put(COLUMN_HEURE, produit.heure)
            put(COLUMN_ADRESSE, produit.adresse)
            put(COLUMN_HUMIDITYMAX, produit.HumidityMax)
            put(COLUMN_HUMIDITYMIN, produit.HumidityMin)

            put(COLUMN_LATITUDE, produit.Latitude)
            put(COLUMN_LONGITUDE, produit.Longitude)
            put(COLUMN_TEMPMAX, produit.TempMax)
            put(COLUMN_TEMPMIN, produit.TempMin)
        }

        val rowsUpdated = db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(produit.id.toString()))

        // Mise à jour dans Firebase
        reference.child(produit.id.toString()).setValue(produit)

        return rowsUpdated
    }

    fun deleteProduct(id: Long): Int {
        // Suppression de SQLite
        val db = this.writableDatabase
        val rowsDeleted = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))

        // Suppression de Firebase
        reference.child(id.toString()).removeValue()

        return rowsDeleted
    }
}


