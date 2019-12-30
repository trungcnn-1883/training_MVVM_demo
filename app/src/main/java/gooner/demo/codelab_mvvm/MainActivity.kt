package gooner.demo.codelab_mvvm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gooner.demo.adapter.WordListAdapter
import gooner.demo.codelab_mvvm.MainActivity.Companion.NEW_WORD_ACTIVITY_REQUEST_CODE
import gooner.demo.entity.Word
import gooner.demo.viewmodel.WordViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val NEW_WORD_ACTIVITY_REQUEST_CODE = 1
    }

    lateinit var mWordViewModel: WordViewModel
    lateinit var mWordListAdapter: WordListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mWordListAdapter = WordListAdapter(this)
        mWordListAdapter.run {
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
            recyclerView.setAdapter(this)
            recyclerView.setLayoutManager(LinearLayoutManager(this@MainActivity))
        }

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)

        mWordViewModel
            .mAllWords.observe(this, Observer<List<Word>> { listWords ->
                mWordListAdapter.setWords(listWords)
            })

        mWordViewModel.mAllSide.observe(this, Observer<List<String>> {
                it?.let {
                    Log.d("AllSide", it.toString())
                }
            })

//        mWordViewModel.mNumberOfItem.observe(this, Observer<Int> {
//                it?.let {
//                    Log.d("AllSide", it.toString())
//                }
//            })

        mWordViewModel.mListWord.observe(this, Observer<List<Word>> {
                it?.let {
                    it.forEach {
                        Log.d("AllSide", "ListWord: " + it.toString())
                    }
                }
            })


        fab.setOnClickListener {
            Intent(this@MainActivity, NewWordActivity::class.java).also { intent ->
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE)
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.apply {
                val inputWord = getStringExtra(NewWordActivity.EXTRA_REPLY)
                try {
                    inputWord?.apply {
                        val word = Word(inputWord.split(" ").get(0), inputWord.split(" ").get(1))
                        mWordViewModel.insert(word)
                        mWordViewModel.setWordToGet(inputWord.split(" ").get(1))
                    }
                } catch (exp: Exception) {

                }


            }
        } else {
            Toast.makeText(this@MainActivity, R.string.empty_not_saved, Toast.LENGTH_SHORT).show()
        }
    }
}
