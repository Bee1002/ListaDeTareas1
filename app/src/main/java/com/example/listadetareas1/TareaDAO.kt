package com.example.listadetareas1

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TareaDao {
    @Insert
    suspend fun insertar(tarea: Tarea)

    @Query("SELECT * FROM tareas")
    suspend fun obtenerTodas(): List<Tarea>

    @Delete
    suspend fun eliminar(tarea: Tarea)
}
