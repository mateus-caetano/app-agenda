package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mateus.agenda.ListFragmentDirections
import com.mateus.agenda.R
import model.Task


class Adapter(private val dataSet: Array<Task>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val dateTime: TextView
        val cardView: CardView

        init {
            // Define click listener for the ViewHolder's View.
            title = view.findViewById(R.id.title)
            dateTime = view.findViewById(R.id.date_time)
            cardView = view.findViewById(R.id.list_item)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.title.text = dataSet[position].title
        viewHolder.dateTime.text = dataSet[position].dateTime
        viewHolder.cardView.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToDetailsFragment(dataSet[position].id)
            it.findNavController().navigate(action)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
