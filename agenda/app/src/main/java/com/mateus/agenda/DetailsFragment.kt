package com.mateus.agenda

import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.MaterialToolbar
import com.mateus.agenda.repositories.EventRepository
import com.mateus.agenda.viewModels.EventVewModel
import com.mateus.agenda.viewModels.factory.EventsListViewModelFactory
import model.Task

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val repository = EventRepository.instance()
    private val viewModel: EventVewModel by activityViewModels<EventVewModel>({ EventsListViewModelFactory(repository) })
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        viewModel.getEventById(args.taskId).observe(viewLifecycleOwner, Observer { task ->
            val link = view.findViewById<TextView>(R.id.details_link)

            val title = view.findViewById<TextView>(R.id.details_title)
            val description = view.findViewById<TextView>(R.id.details_description)
            val dateTime = view.findViewById<TextView>(R.id.details_date_time)
            title.setText(task?.title)
            description.setText(task?.details)
            dateTime.setText(task?.dateTime)
            link.setText(task?.link)

            val topAppBar = view.findViewById<MaterialToolbar>(R.id.topAppBar)

            topAppBar.setNavigationOnClickListener {
                view.findNavController().navigate(R.id.action_detailsFragment_to_listFragment)
            }

            topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.edit -> {
                        editEvent(task.id)
                        true
                    }
                    R.id.delete -> {
                        deleteEvent(view, task)
                        true
                    }
                    else -> false
                }
            }
        })

        val location = view.findViewById<LinearLayout>(R.id.linear_layout_location)

        location.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsFragmentToMapsFragment(Location(
                LocationManager.NETWORK_PROVIDER))
            view.findNavController().navigate(action)
        }

        return view
    }

    private fun editEvent(id: String) {
        val action = DetailsFragmentDirections.actionDetailsFragmentToEditEventListDialogFragment(
            id
        )
        findNavController().navigate(action)
    }

    fun deleteEvent(view: View, event: Task?): Boolean {
        val result = event?.let { viewModel.deleteEvent(it.id) }

        if(result == true) {
            Toast.makeText(context, "Evento excluído com sucesso", Toast.LENGTH_LONG).show()
            view.findNavController()
                .navigate(R.id.action_detailsFragment_to_listFragment)
        } else {
            Toast.makeText(context, "Falha ao excluir o evento", Toast.LENGTH_LONG).show()
        }
        return result == true
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}