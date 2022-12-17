package app.krishiyog.ui.component.list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import app.krishiyog.R
import app.krishiyog.data.dto.list.ListDataItem
import app.krishiyog.databinding.RawListItemBinding
import app.krishiyog.ui.base.listeners.RecyclerItemListener
import com.squareup.picasso.Picasso

class ListViewHolder(private val itemBinding: RawListItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(listDataItem: ListDataItem, recyclerItemListener: RecyclerItemListener) {

        itemBinding.listdata = listDataItem

        Picasso.get().load(listDataItem.avatar).placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher).into(itemBinding.ivRecipeItemImage)
        itemBinding.layout.setOnClickListener {
            if (itemBinding.llcollapse.tag.equals("collapse")) {
                itemBinding.llcollapse.visibility = View.VISIBLE
                itemBinding.llcollapse.tag = "expand"
            } else {
                itemBinding.llcollapse.tag = "collapse"
                itemBinding.llcollapse.visibility = View.GONE
            }
        }
    }
}

