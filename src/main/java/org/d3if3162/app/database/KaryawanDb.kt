package org.d3if3162.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3162.app.model.Karyawan

@Database(entities = [Karyawan::class], version = 1, exportSchema = false)
    abstract class KaryawanDb : RoomDatabase() {
        abstract val dao: KaryawanDao
    companion object {
        @Volatile
        private var INSTANCE: KaryawanDb? = null
        fun getInstance(context: Context): KaryawanDb {
        synchronized(this) {
            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    KaryawanDb::class.java,
                    "karyawan.db"
                ).build()
                INSTANCE = instance
            }
            return instance
            }
        }
    }
}