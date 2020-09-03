package com.sample.app

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.realmutf8issue.R
import com.sample.realmutf8issue.StringHaver

class StringHaverAdapter : RecyclerView.Adapter<StringHaverAdapter.StringItemHolder>() {
    var items = listOf<StringHaverViewObject>()

    fun setData(newItems: List<StringHaverViewObject>) {
        handleDataChanged(this.items, newItems)
    }

    // IGNORE THIS
    private fun handleDataChanged(
        existingData: List<StringHaverViewObject>,
        newData: List<StringHaverViewObject>
    ) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return existingData[oldItemPosition].id == newData[newItemPosition].id
            }

            override fun getOldListSize(): Int = existingData.size


            override fun getNewListSize(): Int = newData.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return existingData[oldItemPosition].hashCode() == newData[newItemPosition].hashCode()
            }
        }, true)
        items = newData
        diff.dispatchUpdatesTo(this)
    }


    companion object Mapper {
        @JvmStatic
        fun toViewObject(stringHaver: StringHaver): StringHaverViewObject =
            StringHaverViewObject(
                stringHaver.coolString
            )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StringItemHolder =
        StringItemHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item,
                parent,
                false
            )
        )


    override fun getItemViewType(position: Int): Int = 0

    override fun onBindViewHolder(holder: StringItemHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int {
        Log.v(ExampleJavaApplication.EXAMPLE_TAG, "getItemCount result: ${items.size}")
        return items.size
    }

    // ViewHolders
    class StringItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            vo: StringHaverViewObject
        ) {
            itemView.findViewById<TextView>(R.id.text).text = vo.id
        }
    }
}

data class StringHaverViewObject(
    val id: String = ""
)
