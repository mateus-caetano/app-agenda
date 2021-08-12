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



/*class Adapter(private val dataSet: Array<Task>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {*/
class Adapter(private val onItemClick: (Task) -> Unit): ListAdapter<Task, Adapter.ItemViewHolder>(
    DiffCallback){

    class ItemViewHolder(private var binding: FragmentListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task) {
            binding.apply {
                title.text  = item.title
                dateTime.text = item.dateTime
            }
        }
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    /*class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val dateTime: TextView

        init {
            // Define click listener for the ViewHolder's View.
            title = view.findViewById(R.id.title)
            dateTime = view.findViewById(R.id.date_time)
        }
    }*/


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // Create a new view, which defines the UI of the list item
        /*val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_list_item, viewGroup, false)

        return ViewHolder(view)*/
        return ItemViewHolder(
            FragmentListItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        /*viewHolder.title.text = dataSet[position].title
        viewHolder.dateTime.text = dataSet[position].dateTime*/
       val current = getItem(position)
        viewHolder.itemView.setOnClickListener { onItemClick(current) }
        viewHolder.bind(current)
    }

    // Return the size of your dataset (invoked by the layout manager)
    //fun getItemCount(position: Int) = dataSet.size

    companion object{
        private val DiffCallback = object : DiffUtil.ItemCallback<Task>(){
            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        }

    }

}
