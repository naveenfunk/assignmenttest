package com.naveen.assignmenttest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naveen.assignmenttest.adapters.HitsAdapter
import com.naveen.assignmenttest.databinding.ActivityMainBinding
import com.naveen.assignmenttest.models.Hit
import com.naveen.assignmenttest.models.HitsJson
import com.naveen.assignmenttest.networking.APIFactory
import com.naveen.assignmenttest.utils.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), HitsAdapter.OnItemClickListener {

    lateinit var binding: ActivityMainBinding
    var adapter: HitsAdapter? = null
    var list = ArrayList<Hit>()
    var listLoading: Boolean = false
    var pageNum: Int = 1
    var selectedCounter = 0
    lateinit var actionModeCallback: ActionModeCallback
    var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)
        setTitle(R.string.app_name)
        actionModeCallback = ActionModeCallback()

        binding.hitsList.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        binding.hitsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!listLoading && (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() == list.size - 1) {
                    pageNum += 1
                    fetchListFromServer()
                }
            }
        })

        binding.refresh.setOnRefreshListener {
            pageNum = 1
            list.clear()
            if (adapter != null)
                adapter?.notifyDataSetChanged()
            fetchListFromServer()
        }
        fetchListFromServer()
    }

    private fun fetchListFromServer() {
        listLoading = true
        APIFactory().getApi().widgetList(pageNum).enqueue(object : Callback<HitsJson> {
            override fun onFailure(call: Call<HitsJson>, t: Throwable) {
                binding.refresh.isRefreshing = false
                listLoading = false
                showToast(this@MainActivity, getString(R.string.something_went_wrong))
            }

            override fun onResponse(call: Call<HitsJson>, response: Response<HitsJson>) {
                binding.refresh.isRefreshing = false
                listLoading = false
                if (response.isSuccessful) {
                    setupList(response.body()?.hits)
                } else {
                    showToast(this@MainActivity, getString(R.string.something_went_wrong))
                }
            }
        })
    }

    private fun setupList(hitList: ArrayList<Hit>?) {
        if (adapter == null) {
            if (hitList != null) {
                list.addAll(hitList)
            }
            adapter = HitsAdapter(list, this@MainActivity)
            binding.hitsList.adapter = adapter
        } else {
            if (hitList != null) {
                list.addAll(hitList)
            }
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onItemClicked(view: View, position: Int, data: Hit?) {
        if (data?.on!!) {
            data?.on = false
            selectedCounter -= 1
            view.isActivated = false
        } else {
            view.isActivated = true
            data?.on = true
            selectedCounter += 1
        }
        list.set(position, data)
        adapter?.notifyDataSetChanged()
        enableActionMode()
    }

    fun enableActionMode() {
        if (actionMode != null) {
            actionMode = startSupportActionMode(actionModeCallback)!!
        }
        toggleSelection()
    }

    fun toggleSelection() {
        if (selectedCounter == 0) {
            actionMode?.finish()
            actionMode = null;
        } else {
            actionMode?.title = selectedCounter.toString()
            actionMode?.invalidate()
        }
    }

    class ActionModeCallback : ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return false
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.main_menu, menu)
            return true;
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
        }

    }
}
