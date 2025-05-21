package com.example.listadetareas1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var tareaDao: TareaDao
    private lateinit var adapter: ArrayAdapter<String>
    private val listaTareas = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        val lvTareas = findViewById<ListView>(R.id.lvTareas)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaTareas)
        lvTareas.adapter = adapter

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "tareas-db"
        ).build()
        tareaDao = db.tareaDao()

        btnAgregar.setOnClickListener {
            val descripcion = etDescripcion.text.toString()
            if (descripcion.isNotEmpty()) {
                val tarea = Tarea(descripcion = descripcion)
                CoroutineScope(Dispatchers.IO).launch {
                    tareaDao.insertar(tarea)
                    val tareas = tareaDao.obtenerTodas()
                    listaTareas.clear()
                    listaTareas.addAll(tareas.map { it.descripcion })
                    runOnUiThread {
                        adapter.notifyDataSetChanged()
                        etDescripcion.text.clear()
                    }
                }
            }
        }
    }
}
