package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import com.example.todoapp.databinding.ActivityNewTodoBinding
import com.example.todoassignment.models.Todos
import com.example.todoassignment.viewmodels.TodosViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
@AndroidEntryPoint
class NewTodoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewTodoBinding
    private val viewModel: TodosViewModel by viewModels()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.save.setOnClickListener {
            val titleText = binding.title.text.toString()
            val descText = binding.description.text.toString()
            if(titleText.isNotEmpty() && descText.isNotEmpty()) {
                val id = (1..1000).random()
                viewModel.addTodo(Todos(id = id, title = titleText, description = descText))
                finish()
            }
        }
    }
}