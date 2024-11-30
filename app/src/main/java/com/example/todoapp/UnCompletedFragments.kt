package com.example.todoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.adapters.TodoDataAdapter
import com.example.todoapp.databinding.TodoScreenBinding
import com.example.todoassignment.viewmodels.TodosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UnCompletedFragments : Fragment(){

    lateinit var todoScreenBinding: TodoScreenBinding
    private val viewModel: TodosViewModel by activityViewModels()
    var mAdapter: TodoDataAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        todoScreenBinding = TodoScreenBinding.inflate(layoutInflater)
        loadData()
        return todoScreenBinding.root
    }

    private fun loadData() {
        viewModel.allTodos()
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.todosUnCompleted.collect { data ->
                    mAdapter = TodoDataAdapter(
                        viewModel = viewModel,
                        context = requireActivity(),
                        content = data
                    )
                    todoScreenBinding.recyclerView.apply {
                        layoutManager = LinearLayoutManager(requireActivity())
                        adapter = mAdapter
                    }
                }
            }
        }

        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                mAdapter?.remove(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(todoScreenBinding.recyclerView)
    }

}