package com.example.listadetareas1

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Tarea::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tareaDao(): TareaDao
}