package gooner.demo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.nio.file.Files.size
import gooner.demo.entity.Word
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import gooner.demo.codelab_mvvm.R


class WordListAdapter constructor(context: Context) :
    RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    private val mInflater: LayoutInflater
    private var mWords: List<Word> = mutableListOf() // Cached copy of words

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        mWords.let {
            if (mWords[position].mWord.isNotEmpty() || mWords[position].mSide.isNotEmpty())
                holder.wordItemView.text = mWords[position].mWord + " " + mWords[position].mSide
            else {
                // Covers the case of data not being ready yet.
                holder.wordItemView.text = "No Word"
            }
        }

    }

    fun setWords(words: List<Word>) {
        mWords = words
        notifyDataSetChanged()
    }

    // getItemCount() is called many times, and when it is first called,
// mWords has not been updated (means initially, it's null, and we can't return null).
    override fun getItemCount(): Int {
        return mWords?.size ?: 0
    }

    inner class WordViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView

        init {
            wordItemView = itemView.findViewById(R.id.textView)
        }
    }
}