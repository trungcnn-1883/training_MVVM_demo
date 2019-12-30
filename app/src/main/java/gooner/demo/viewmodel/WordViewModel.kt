package gooner.demo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import gooner.demo.entity.Word
import gooner.demo.repo.WordRepository

class WordViewModel(application: Application) : AndroidViewModel(application) {

    var mRepository: WordRepository
    var mAllWords: LiveData<List<Word>>

    var mAllSide: LiveData<ArrayList<String>> = MutableLiveData<ArrayList<String>>()
    var mNumberOfItem: LiveData<Int> = MutableLiveData<Int>()

    var mWord: MutableLiveData<String> = MutableLiveData<String>()

    var mListWord: LiveData<List<Word>>

    init {
        mRepository = WordRepository(application)
        mAllWords = mRepository.mAllWords

        mAllSide = Transformations.map(mAllWords) { word ->
            createListSide(word)
        }

        mListWord = Transformations.switchMap(mWord) { word ->
            mRepository.findWord(word)
        }

        mNumberOfItem = Transformations.map(mAllWords) { word ->
            countListItem(word)
        }
    }

    fun setWordToGet(word: String) {
        mWord.value = word
    }

    private fun countListItem(word: List<Word>?): Int? = word?.size ?: 0

    private fun createListSide(word: List<Word>): ArrayList<String>? {
        var listSides = ArrayList<String>()
        word.map { it.mSide }.distinct().forEach {
            listSides.add(it)
        }
        return listSides
    }

    fun insert(word: Word) {
        mRepository.insert(word)
    }


    fun deleteAllWord() {
        mRepository.deleteAll()
    }

}