import android.content.ComponentCallbacks
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "MyDatabase.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "MyProduct"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_EMPLACEMENT = "emplacement"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_TEMPERATURE_MAX = "TempMax"
        private const val COLUMN_TEMPERATURE_MIN = "TempMin"
        private const val COLUMN_HUMIDITY_MAX = "HumidityMax"
        private const val COLUMN_HUMIDITY_MIN = "HumidityMin"
        private const val COLUMN_LATITUDE = "Latitude"
        private const val COLUMN_LONGITUDE = "Longitude"
        private const val COLUMN_ADRESSE = "Adresse"
        private const val COLUMN_HEURE = "heure"

        private const val CREATE_TABLE_QUERY = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_EMPLACEMENT TEXT," +
                "$COLUMN_DATE TEXT," +
                "$COLUMN_TEMPERATURE_MAX REAL," +
                "$COLUMN_TEMPERATURE_MIN REAL," +
                "$COLUMN_HUMIDITY_MAX REAL," +
                "$COLUMN_HUMIDITY_MIN REAL," +
                "$COLUMN_LATITUDE REAL," +
                "$COLUMN_LONGITUDE REAL," +
                "$COLUMN_ADRESSE TEXT,)"
        "
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference = database.getReference("MyProduct")

    fun addProduct(product: Product):Long {
        val db =this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, product.name)
            put(COLUMN_EMPLACEMENT, product.emplacement)
            put(COLUMN_DATE, product.date)
            put(COLUMN_TEMPERATURE_MAX, product.TempMax)
            put(COLUMN_TEMPERATURE_MIN, product.TempMin)
            put(COLUMN_HUMIDITY_MAX, product.HumidityMax)
            put(COLUMN_HUMIDITY_MIN, product.HumidityMin)
            put(COLUMN_LATITUDE, product.Latitude)
            put(COLUMN_LONGITUDE, product.Longitude)
            put(COLUMN_ADRESSE, product.Adresse)

        }
        val id = db.insert(TABLE_NAME, null, values)

        reference.child(id.toString()).setValue(product)
        return id
    }

    fun getAllProducts(callback:(List<Product>)->Unit ):  {
        reference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot:DataSnapshot) {
                val Prod = mutableListOf<Product>()
                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(Product::class.java)
                    if (product != null) {
                        Prod.add(it)
                    }

                }
                callback(Prod)

            }
            override fun onCancelled(error: DatabaseError) {
                callback(emptyList())
            }
        })
    }


    fun supprimer(id: Long) :Int{
        val  db=this.writableDatabase
        val result=db.delete(TABLE_NAME,"$COLUMN_ID=?", arrayOf(id.toString()))
        reference.child(id.toString()).removeValue()
        return result
    }

    fun update(product: Product): Int {
        val db = this.writableDatabase
val values=ContentValues().apply {
    put(COLUMN_NAME,product.name)
    put(COLUMN_EMPLACEMENT,product.emplacement)
    put(COLUMN_DATE,product.date)
    put(COLUMN_TEMPERATURE_MAX,product.TempMax)
    put(COLUMN_TEMPERATURE_MIN,product.TempMin)
    put(COLUMN_HUMIDITY_MAX,product.HumidityMax)
    put(COLUMN_HUMIDITY_MIN,product.HumidityMin)
    put(COLUMN_LATITUDE,product.Latitude)
    put(COLUMN_LONGITUDE,product.Longitude)
    put(COLUMN_ADRESSE,product.Adresse)

}
        val result=db.update(TABLE_NAME,values,"$COLUMN_ID=?", arrayOf(product.id.toString()))
        reference.child(product.id.toString()).setValue(product)
        return result
    }

}