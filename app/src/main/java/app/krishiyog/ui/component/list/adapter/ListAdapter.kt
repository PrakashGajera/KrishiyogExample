package app.krishiyog.ui.component.list.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.krishiyog.data.dto.list.ListDataItem
import app.krishiyog.databinding.RawListItemBinding
import app.krishiyog.ui.base.listeners.RecyclerItemListener
import app.krishiyog.ui.component.list.ListViewModel
import kotlinx.android.synthetic.main.raw_list_item.view.*

class ListAdapter(private val listViewModel: ListViewModel, private val dataItems: List<ListDataItem>) : RecyclerView.Adapter<ListViewHolder>() {

    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(dataItem: ListDataItem) {
            listViewModel.listDetail(dataItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemBinding = RawListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(dataItems[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return dataItems.size
    }
}

