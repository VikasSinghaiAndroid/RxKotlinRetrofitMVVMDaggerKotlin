package com.example.rxjavaretrofitkotlin.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rxjavaretrofitkotlin.R
import com.example.rxjavaretrofitkotlin.model.DataProperty
import com.example.rxjavaretrofitkotlin.model.Row
import com.example.rxjavaretrofitkotlin.ui.adapter.DataListAdapter
import com.example.rxjavaretrofitkotlin.viewmodel.MainActivityViewModel
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity(), DataListAdapter.Interaction {

    private var dataListAdapter: DataListAdapter? = null

    @Inject
    lateinit var mainActivityViewModel: MainActivityViewModel

    private lateinit var dataList: List<Row>

    private var title: String? = "No Title"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        d("MainActivity", "Inside onCreateView")
        dataList = mutableListOf()

        initRecyclerView()
        observerLiveData()
        setUpPullDownRefresh()
    }

    private fun setUpPullDownRefresh() {
        d("MainActivity", "Inside setUpPullDownRefresh")
        // Set the colors for Pull To Refresh View
        items_swipe_to_refresh.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.colorPrimary
            )
        )
        items_swipe_to_refresh.setColorSchemeColors(Color.WHITE)

        //List refresh listener
        items_swipe_to_refresh.setOnRefreshListener {
            dataList = emptyList()
            observerLiveData()
        }
    }

    private fun observerLiveData() {
        d("MainActivity", "Inside observerLiveData")
        val dataFromServer = mainActivityViewModel.showDataFromApi()
        if (dataFromServer == null) {
            progressBar?.visibility = View.GONE
            initRecyclerView()
        } else dataFromServer.subscribe(object : Observer<DataProperty> {
            override fun onComplete() {
                d("MainActivity", "onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                d("MainActivity", "onSubscribe")
            }

            override fun onNext(dataProperty: DataProperty) {
                title = dataProperty.title
                dataList = dataProperty.rows
                dataListAdapter?.swap(dataProperty.rows)
                progressBar?.visibility = View.GONE
            }

            override fun onError(e: Throwable) {
                Log.e("MainActivity", "onError")
            }

        })
        items_swipe_to_refresh.isRefreshing = false
    }

    private fun initRecyclerView() {
        d("MainActivity", "Inside initRecyclerView")
        recyclerView.apply {
            dataListAdapter = DataListAdapter(
                dataList,
                this@MainActivity
            )
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = dataListAdapter
        }

        //Add divider for each list items
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onItemSelected(position: Int, item: Row) {
        d("MainActivity", "Item clicked : position : $position  and Item : $item")
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("itemRows", item)
        intent.putExtra("mainTitle", title)
        startActivity(intent)
    }
}

