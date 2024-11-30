package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.example.todoapp.adapters.TabsAdapter
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoassignment.viewmodels.TodosViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private lateinit var mainActivityMainBinding: ActivityMainBinding
    private val viewModel: TodosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityMainBinding.root)
        setActionBar(mainActivityMainBinding.toolbar)
        createTabs()
        initClicks()
    }

    private fun initClicks() {
        mainActivityMainBinding.btnNew.setOnClickListener {
            val intent = Intent(this, NewTodoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createTabs() {
        val adapterM = TabsAdapter(fragmentManager = supportFragmentManager, lifecycle = lifecycle)
        adapterM.addFragment(UnCompletedFragments())
        adapterM.addFragment(CompletedFragments())
        mainActivityMainBinding.viewPager.apply {
            adapter = adapterM
        }
        mainActivityMainBinding.tabLayout.apply {
            addTab(this.newTab().setText("UnCompleted"))
            addTab(this.newTab().setText("Completed"))
            addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    mainActivityMainBinding.viewPager.currentItem =
                        mainActivityMainBinding.tabLayout.selectedTabPosition
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                }
            })
        }
    }
}

