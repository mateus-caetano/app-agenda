package adapters

import adapters.Adapter.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mateus.agenda.R
import model.Task
import com.mateus.agenda.databinding.FragmentListItemBinding
import com.mateus.agenda.databinding.FragmentListBinding



/*class Adapter(private val dataSet: Array<Task>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {*/
class Adapter(private val onItemClick: (Task) -> Unit): ListAdapter<Task, Adapter.ItemViewHolder>(
    DiffCallback){

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            FragmentListItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }
    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
       val current = getItem(position)
        viewHolder.itemView.setOnClickListener {
            onItemClick(current)
        }
        viewHolder.bind(current)
    }

    class ItemViewHolder(private var binding: FragmentListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Task) {
            binding.title.text = item.title
            binding.dateTime.text = item.dateTime
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }

}
