package gooner.demo.viewmodel

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import gooner.demo.entity.Word
import gooner.demo.repo.WordRepository

class WordViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var mRepository: WordRepository
    lateinit var mAllWords: LiveData<List<Word>>

    init {
        mRepository = WordRepository(application)
        mAllWords = mRepository.mAllWords
    }

    fun insert(word: Word) {
        mRepository.insert(word)
    }

}