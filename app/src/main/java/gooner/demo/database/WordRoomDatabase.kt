package gooner.demo.database

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import gooner.demo.dao.WordDao
import gooner.demo.entity.Word
import androidx.room.Room

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao


    companion object {

        @JvmField
        var INSTANCE: WordRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): WordRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(WordRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            WordRoomDatabase::class.java, "word_database"
                        )
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }


    fun doSt() {

    }

}

//public class Singleton private constructor() {
//    init {
//        println("This ($this) is a singleton")
//    }
//
//    private object Holder {
//        val INSTANCE = Singleton()
//    }
//
//    companion object {
//        val instance: Singleton by lazy { Holder.INSTANCE }
//    }
//
//}