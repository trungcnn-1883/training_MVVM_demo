package gooner.demo.codelab_mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gooner.demo.adapter.WordListAdapter
import gooner.demo.entity.Word
import gooner.demo.viewmodel.WordViewModel


class MainActivity : AppCompatActivity() {

    lateinit var mWordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)

        mWordViewModel.let {
            it.getAllWords().observe(this@MainActivity,
                Observer<List<Word>> { t -> adapter.setWords(t) })
            it.deleteAllWord()
            it.insert(Word("Tiger"))
            it.insert(Word("Dolphin"))
            it.insert(Word("Kiki"))
        }


    }
}
