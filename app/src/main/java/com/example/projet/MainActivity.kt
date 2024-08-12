 package com.example.projet

import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

 class MainActivity : AppCompatActivity() {

    private  lateinit var  dataBase: DataBase
    private lateinit var monAdapteur: MonAdapteur
    private lateinit var temp: TextView
    private lateinit var heur: TextView
    private lateinit var  sensorManager:SensorManager
    private lateinit var notification: NotificationHelper
    private lateinit var listView: ListView
    private var temperatureSen: Sensor? = null
    private  val produit= mutableListOf<Products>()
    private  lateinit var  addButton:FloatingActionButton

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        // listview init

        listView=findViewById(R.id.listView)
        temp= findViewById(R.id.temperatureTxt)
        heur=findViewById(R.id.DateTimeTxt)
        addButton=findViewById(R.id.fab)
        dataBase=DataBase(this)
        monAdapteur=MonAdapteur(this,produit)
        listView.adapter=monAdapteur
        temp.text="la Temperature actuelle est de:25"
        notification= NotificationHelper(this)
        NotificationHelper.createNotificationChannel(this)
        NotificationHelper.checkAndRequestNotificationPermission(this)
        sensorManager=getSystemService(SENSOR_SERVICE) as SensorManager
        temperatureSen=sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        addButton.setOnClickListener{
            val intent =Intent(this,AjouterProduit::class.java)
            startActivity(intent)
        }

//        listView.onItemClickListener= AdapterView.OnItemClickListener { _, _, position, _ ->
//            val prod = produit[position]
//            val intent = Intent(this, AddProduct::class.java)
//            intent.putExtra("produit", prod)
//            startActivity(intent)
//        }
//
//        listView.onItemLongClickListener = AdapterView.OnItemLongClickListener { _, _, position, _ ->
//            val prod = produit[position]
//            AlertDialog.Builder(this)
//                .setTitle("Supprimer Tâche")
//                .setMessage("Êtes-vous sûr de vouloir supprimer cette tâche?")
//                .setPositiveButton("Oui") { dialog, _ ->
//                    // Suppression de la tâche dans SQLite et Firebase
//                    dataBase.DeleteProduct(prod.id)
//                    produit.removeAt(position)
//                    // MonAdapteur.notifyDataSetChanged()
//                    dialog.dismiss()
//                }
//                .setNegativeButton("Non") { dialog, _ ->
//                    dialog.dismiss()
//                }
//                .create()
//                .show()
//            true
//        }

    }
     override fun onResume() {
         super.onResume()
         temperatureSen?.also { temperatureSen ->
         sensorManager.registerListener(this, temperatureSen, SensorManager.SENSOR_DELAY_NORMAL)}
     }
     override fun onPause() {
         super.onPause()
         sensorManager.unregisterListener(this)
     }


     override fun onSensorChanged(event: SensorEvent?) {
         if (event?.sensor?.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
             val temperature = event.values[0].toDouble()

             temp.text = "la Temperature actuelle est de: $temperature"
             for(produit in produit){
                 if(temperature>produit.TempMax||temperature<produit.TempMin){
                     notification.sendNotification("attention","la temperature est anormal pour le produit ${produit.name}")
                 }
             }
     }

     }
     override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults)
         if (requestCode == NotificationHelper.NOTIFICATION_PERMISSION_REQUEST_CODE) {
             if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                 // Permission de notification acceptée : l'utilisateur a autorisé les notifications
                 Toast.makeText(this, "Permission de notification acceptée", Toast.LENGTH_SHORT).show()
             } else {
                 // Permission de notification refusée : l'utilisateur a refusé les notifications
                 Toast.makeText(this, "Permission de notification refusée", Toast.LENGTH_SHORT).show()
             }
         }
     }

}

