package app.krishiyog.ui.base.listeners

import app.krishiyog.data.dto.list.ListDataItem
import app.krishiyog.databinding.RawListItemBinding

interface RecyclerItemListener {
    fun onItemSelected(recipe : ListDataItem)
}
