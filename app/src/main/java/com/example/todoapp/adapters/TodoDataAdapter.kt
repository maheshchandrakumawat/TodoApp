package com.example.todoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTodoBinding
import com.example.todoassignment.models.Todos
import com.example.todoassignment.viewmodels.TodosViewModel

class TodoDataAdapter(
    private val viewModel: TodosViewModel,
    private val context: Context,
    private val content: List<Todos>
) :
    RecyclerView.Adapter<TodoDataAdapter.CustomViewHolder>() {
    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        CustomViewHolder(
            ItemTodoBinding
                .inflate(LayoutInflater.from(context), viewGroup, false)
                .root
        )

    override fun getItemCount() = content.size

    override fun onBindViewHolder(viewHolder: CustomViewHolder, position: Int) {
        ItemTodoBinding.bind(viewHolder.itemView).apply {
            title.text = content[position].title
            description.text = content[position].description
            if(content[position].completed) {
                completed.visibility = View.GONE
            } else {
                completed.visibility = View.VISIBLE
            }
            content[position].completed = true
            completed.setOnClickListener { viewModel.updateTodo(content[position]) }
        }
    }

    fun remove(position: Int) {
        viewModel.removeTodo(todo = content[position])
    }
}