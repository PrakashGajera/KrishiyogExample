package app.krishiyog.ui.component.list

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import app.krishiyog.R
import app.krishiyog.data.Resource
import app.krishiyog.data.dto.list.ListData
import app.krishiyog.data.dto.list.ListDataItem
import app.krishiyog.data.error.SEARCH_ERROR
import app.krishiyog.databinding.HomeActivityBinding
import app.krishiyog.ui.base.BaseActivity
import app.krishiyog.ui.component.list.adapter.ListAdapter
import app.krishiyog.utils.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListActivity : BaseActivity() {

    private lateinit var binding: HomeActivityBinding

    private val listViewModel: ListViewModel by viewModels()
    private lateinit var listAdapter: ListAdapter

    override fun initViewBinding() {
        binding = HomeActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        supportActionBar?.title = getString(R.string.app_name)
        val layoutManager = LinearLayoutManager(this)
        binding.rvListData.layoutManager = layoutManager
        binding.rvListData.setHasFixedSize(true)
        listViewModel.getListData()
        binding.btnRetry.setOnClickListener({ listViewModel.getListData() })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> listViewModel.getListData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindListData(listData: ListData) {
        if (!(listData.listDataItems.isNullOrEmpty())) {
            listAdapter = ListAdapter(listViewModel, listData.listDataItems)
            binding.rvListData.adapter = listAdapter
            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    private fun navigateToDetailsScreen(navigateEvent: SingleEvent<ListDataItem>) {
        Toast.makeText(applicationContext,"success",Toast.LENGTH_SHORT).show()
    }

    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun showSearchError() {
        listViewModel.showToastMessage(SEARCH_ERROR)
    }

    private fun showDataView(show: Boolean) {
        binding.rlNodata.visibility = if (show) GONE else VISIBLE
        binding.rvListData.visibility = if (show) VISIBLE else GONE
        binding.shimmerViewContainer.toGone()
    }

    private fun showLoadingView() {
        binding.shimmerViewContainer.toVisible()
        binding.rlNodata.toGone()
        binding.rvListData.toGone()
    }

    private fun showSearchResult(listDataItem: ListDataItem) {
        listViewModel.listDetail(listDataItem)
        binding.shimmerViewContainer.toGone()
    }

    private fun noSearchResult(unit: Unit) {
        showSearchError()
        binding.shimmerViewContainer.toGone()
    }

    private fun handleList(status: Resource<ListData>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let { bindListData(listData = it) }
            is Resource.DataError -> {
                showDataView(false)
                status.errorCode?.let { listViewModel.showToastMessage(it) }
            }
        }
    }

    override fun observeViewModel() {
        observe(listViewModel.listDataLiveData, ::handleList)
        observe(listViewModel.listSearchFound, ::showSearchResult)
        observe(listViewModel.noSearchFound, ::noSearchResult)
        observeEvent(listViewModel.openDetails, ::navigateToDetailsScreen)
        observeSnackBarMessages(listViewModel.showSnackBar)
        observeToast(listViewModel.showToast)
    }
}
