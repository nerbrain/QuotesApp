package com.example.mvvm.ui.quotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.R
import com.example.mvvm.data.Quote
import com.example.mvvm.utilities.InjectorUtils
import kotlinx.android.synthetic.main.activity_quotes.*
import java.lang.StringBuilder
import kotlin.text.Typography.quote

class QuotesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotes)
        initializeUi()
    }

    private fun initializeUi(){
        val factory = InjectorUtils.provideQuotesViewModelFactory()
        val viewModel = ViewModelProvider(this,factory).get(QuoteViewModel::class.java)
        viewModel.getQuote().observe(this, Observer { quotes ->
            val stringBuilder = StringBuilder()
            quotes.forEach{ quote ->
                stringBuilder.append("$quote\n\n")
            }
            textView_quotes.text = stringBuilder.toString()
        })
        button_add_quote.setOnClickListener{
            val quote = Quote(editText_quote.text.toString(),editText_author.text.toString())
            viewModel.addQuote(quote)
            editText_quote.setText("")
            editText_author.setText("")
            Log.d("TAG", "initializeUi: clicked")
        }
    }
}