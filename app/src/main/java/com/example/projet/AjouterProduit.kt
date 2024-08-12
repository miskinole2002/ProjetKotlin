package com.example.projet

import android.app.DatePickerDialog
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AjouterProduit : AppCompatActivity() {
    private lateinit var dataBase: DatabaseHelper
    private lateinit var name: EditText
    private lateinit var emplacement: EditText
    private lateinit var TempMax: EditText
    private lateinit var TempMin: EditText
    private lateinit var HumMax: EditText
    private lateinit var HumMin: EditText
    private lateinit var Latitude: EditText
    private lateinit var Longitude: EditText
    private lateinit var buttonConvertToAddress: Button
    private lateinit var Address: TextView
    private lateinit var buttonSave: Button
    private lateinit var buttonCancel: Button
    private lateinit var heure: EditText
    private lateinit var date: EditText

    private val calendar = Calendar.getInstance()
    private var produit: Products? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.mon_template)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main1)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//
//        dataBase = DatabaseHelper(this)
        name = findViewById(R.id.produitTxt)
        emplacement = findViewById(R.id.emplacemtTxt)
        TempMax = findViewById(R.id.TemperatureMaxTxt)
        TempMin = findViewById(R.id.TemperatureMinTxtTxt)
        HumMax = findViewById(R.id.HumidityMaxTxt)
        HumMin = findViewById(R.id.HumidityMinTxtTxt)
        Latitude = findViewById(R.id.LatitudeTxt)
        Longitude = findViewById(R.id.LongitudeTxt)
        buttonConvertToAddress = findViewById(R.id.convertBTn)
        Address = findViewById(R.id.AdresseTxt)
        buttonSave = findViewById(R.id.saveBtn)
        buttonCancel = findViewById(R.id.cancelBtn)
        heure = findViewById(R.id.HeureTxt)
        date = findViewById(R.id.DateTxt)


        date.setOnClickListener {
            showDatePickerDialog()
        }

        val currentTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val formattedTime = formatter.format(currentTime)
        heure.setText(formattedTime)

//
//
        buttonConvertToAddress.setOnClickListener {
            val lat = Latitude.text.toString().toDoubleOrNull()
            val Long = Longitude.text.toString().toDoubleOrNull()
            if (lat != null && Long != null) {
                convertir(this, lat, Long)
            } else {
                Toast.makeText(this, "veuillez entrez des coordonnees  svp", Toast.LENGTH_SHORT)
                    .show()
            }

        }
}
//
//        buttonSave.setOnClickListener {
//            if (Validate()) {
//                val nom = name.text.toString()
//                val emplac = emplacement.text.toString()
//                val TmpMax = TempMax.text.toString().toIntOrNull() ?: 0
//                val TmpMin = TempMin.text.toString().toIntOrNull() ?: 0
//                val HmMax = HumMax.text.toString().toDoubleOrNull() ?: 0
//                val HmMin = HumMin.text.toString().toDoubleOrNull() ?: 0
//                val La = Latitude.text.toString().toDoubleOrNull() ?: 0.0
//                val Lo = Longitude.text.toString().toDoubleOrNull() ?: 0.0
//                val h = heure.text.toString()
//                val d = date.text.toString()
//                val adr= Address.text.toString()
//
//
//                if (produit != null){
//                    val values = Products(0, nom, emplac, TmpMax.toDouble(), TmpMin.toDouble(), HmMax.toDouble(), HmMin.toDouble(),d,h,La,Lo, adr)
//                    dataBase.UpdateProduct(values)
//                }else{
//
//                    val Update=produit!!.copy(
//
//                        name=nom,
//                        emplacement=emplac,
//                        TempMax = TmpMax.toDouble(),
//                        TempMin = TmpMin.toDouble(),
//                        HumidityMax = HmMax.toDouble(),
//                        HumidityMin = HmMin.toDouble(),
//                        date = d,
//                        heure = h,
//                        Latitude = La,
//                        Longitude = Lo,
//                        adresse = adr
//
//                    )
//                    dataBase.UpdateProduct(Update)
//
//                }
//                finish()
//            }
//            buttonCancel.setOnClickListener{
//                finish()
//            }
//
//            produit=if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU){
//                intent.getParcelableExtra("produit",Products::class.java)
//            }else{
//                @Suppress("DEPRECATION")
//                intent.getParcelableExtra("produit")
//            }
//            if(produit!=null){
//                populateFields(produit!!)
//            }
//
//        }
//    }
//
//    private fun Validate(): Boolean {
//        val nom = name.text.toString()
//        val emplac = emplacement.text.toString()
//        val TmpMax = TempMax.text.toString()
//        val TmpMin = TempMin.text.toString()
//        val HmMax = HumMax.text.toString()
//        val HmMin = HumMin.text.toString()
//        val La = Latitude.text.toString()
//        val Lo = Longitude.text.toString()
//        val h = heure.text.toString()
//        val d = date.text.toString()
//        if (nom.isEmpty() || emplac.isEmpty() || TmpMax.isEmpty() || TmpMin.isEmpty() || HmMax.isEmpty() || HmMin.isEmpty() || La.isEmpty() || Lo.isEmpty() || h.isEmpty() || d.isEmpty()) {
//            Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show()
//            return false
//        }
//        val currentDate = Date()
//        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//        val selectedDate = dateFormat.parse(d)
//        if (selectedDate != null && !selectedDate.after(currentDate)) {
//            Toast.makeText(
//                this,
//                "La date limite doit être supérieure à la date actuelle",
//                Toast.LENGTH_SHORT
//            ).show()
//            return false
//        }
//        if (TmpMax.toIntOrNull() == null || TmpMin.toIntOrNull() == null || HmMax.toIntOrNull() == null || HmMin.toIntOrNull() == null) {
//            Toast.makeText(
//                this,
//                "Les températures doivent être des nombres entiers",
//                Toast.LENGTH_SHORT
//            ).show()
//            return false
//        }
//        if (La.toDoubleOrNull() == null || Lo.toDoubleOrNull() == null) {
//            Toast.makeText(
//                this,
//                "Les coordonnées doivent être des nombres à virgule flottante",
//                Toast.LENGTH_SHORT
//            ).show()
//            return false
//        }
//        return true
//    }
//
//    // conversion
    private fun convertir(context: Context, latitude: Double, longitude: Double) {
        val geocoder = Geocoder(context, Locale.getDefault())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            geocoder.getFromLocation(latitude, longitude, 1, object : Geocoder.GeocodeListener {
                override fun onGeocode(addresses: MutableList<Address>) {
                    runOnUiThread {
                        if (addresses.isNotEmpty()) {
                            val adresse: Address = addresses[0]
                            Address.text = adresse.getAddressLine(0)
                        } else {
                            Toast.makeText(context, "Aucune adresse trouvée", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onError(errorMessage: String?) {
                    runOnUiThread {
                        Toast.makeText(context, "Erreur lors de la conversion des coordonnées", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        } else {

            try {
                @Suppress("DEPRECATION")
                val adresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
                if (!adresses.isNullOrEmpty()) {
                    val adresse: Address = adresses[0]
                    Address.text = adresse.getAddressLine(0)
                } else {
                    Toast.makeText(context, "Aucune adresse trouvée", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(context, "Erreur lors de la conversion des coordonnées", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePickerDialog() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }
//
        DatePickerDialog(
            this,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // Format de la date
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        date.setText(sdf.format(calendar.time))
    }

//    private fun populateFields(produit: Products) {
//        name.setText(produit.name)
//        emplacement.setText(produit.emplacement)
//        date.setText(produit.date)
//        Address.text = produit.adresse
//
//        Latitude.setText(produit.Latitude.toString())
//        Longitude.setText(produit.Longitude.toString())
//        TempMax.setText(produit.TempMax.toInt().toString())
//        TempMin.setText(produit.TempMin.toInt().toString())
//    }
    }
