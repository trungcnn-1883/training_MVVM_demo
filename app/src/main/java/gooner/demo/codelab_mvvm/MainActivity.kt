package gooner.demo.codelab_mvvm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gooner.demo.adapter.WordListAdapter
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

        mWordViewModel.mAllWords.observe(this, Observer<List<Word>> { listWords ->
            mWordListAdapter.setWords(listWords)
        })

        fab.setOnClickListener {
            Intent(this@MainActivity, NewWordActivity::class.java).also { intent ->
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE)
            }
        }

//        plusMinus(arrayOf(3, 4, -4, 5, -3, 0))
    }

//    fun plusMinus(arr: Array<Int>) {
//
//        var positiveCounter = 0
//        var nagativeCounter = 0
//        var zeroCounter = 0
//
//        for (i in 0..arr.size - 1) {
//            if (arr[i] > 0) {
//                positiveCounter++
//            } else if (arr[i] < 0) {
//                nagativeCounter++
//            } else zeroCounter++
//        }
//
//        print(positiveCounter / (arr.size - 1))
//        print(nagativeCounter / (arr.size - 1))
//        print(zeroCounter / (arr.size - 1))
//
//    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.apply {
                getStringExtra(NewWordActivity.EXTRA_REPLY)?.apply {
                    val word = Word(this)
                    mWordViewModel.insert(word)
                }
            }
        } else {
            Toast.makeText(this@MainActivity, R.string.empty_not_saved, Toast.LENGTH_SHORT).show()
        }
    }
}
