package gooner.demo.repo

import android.app.Application
import android.os.AsyncTask
import android.provider.UserDictionary
import androidx.lifecycle.LiveData
import gooner.demo.dao.WordDao
import gooner.demo.database.WordRoomDatabase
import gooner.demo.entity.Word

class WordRepository(application: Application) {


    lateinit var mWordDao: WordDao

    init {
        WordRoomDatabase.getDatabase(application)?.let {
            mWordDao = it.wordDao()
        }
    }

    fun getAllWords(): LiveData<List<Word>> = mWordDao.getAllWords()


    fun insert(word: Word) {
        InsertAysncTask(mWordDao).execute(word)
    }

    fun deleteAll() {
        DeleteAysncTask(mWordDao).execute()
    }

    class InsertAysncTask(var mAsyncTaskDao: WordDao) : AsyncTask<Word, Void, Void>() {

        override fun doInBackground(vararg params: Word?): Void? {
            params[0]?.let { mAsyncTaskDao.insert(it) }
            return null
        }

    }

    class DeleteAysncTask(var mAsyncTaskDao: WordDao) : AsyncTask<Word, Void, Void>() {

        override fun doInBackground(vararg params: Word?): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }


}