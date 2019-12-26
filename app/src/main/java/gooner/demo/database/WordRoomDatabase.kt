package gooner.demo.database

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import gooner.demo.entity.Word
import gooner.demo.dao.WordDao
import android.os.AsyncTask
import android.icu.lang.UCharacter.GraphemeClusterBreak.V


@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao


    companion object {

        @JvmField
        var INSTANCE: WordRoomDatabase? = null

        val sRoomDatabaseCallback = object : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE).execute()
            }
        }

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
                            .addCallback(sRoomDatabaseCallback)
                            .build()
                    }
                }
            }
            return INSTANCE
        }


        private class PopulateDbAsync constructor(db: WordRoomDatabase?) :
            AsyncTask<Void, Void, Void>() {

            private var mDao: WordDao? = null
            internal var words = arrayOf("dolphin", "crocodile", "cobra")

            init {
                db?.let {
                    mDao = it.wordDao()
                }

            }

            override fun doInBackground(vararg params: Void): Void? {
                // Start the app with a clean database every time.
                // Not needed if you only populate the database
                // when it is first created
                mDao?.let {
                    it.deleteAll()

                    for (i in 0..words.size - 1) {
                        val word = Word(words[i])
                        it.insert(word)
                    }
                }

                return null
            }
        }
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