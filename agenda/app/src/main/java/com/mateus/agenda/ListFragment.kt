package com.mateus.agenda

import adapters.Adapter
import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.mateus.agenda.repositories.EventRepository
import com.mateus.agenda.viewModels.EventVewModel
import com.mateus.agenda.viewModels.factory.EventsListViewModelFactory
import androidx.fragment.app.FragmentManager as FragmentManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val repository = EventRepository.instance()
    private val viewModel: EventVewModel by activityViewModels<EventVewModel>({ EventsListViewModelFactory(repository) })
    private lateinit var linearLayoutManager: LinearLayoutManager

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
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        var adapter: Adapter
        val topAppBar = view.findViewById<MaterialToolbar>(R.id.topAppBar)
        topAppBar.setOnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.logout -> {
                    repository.logout()
                    view.findNavController().navigate(R.id.action_listFragment_to_loginFragment)
                }
            }
            true
        }
            adapter = viewModel.getEventsList().value?.toTypedArray()?.let { Adapter(it) }!!
        viewModel.getEventsList().observe(viewLifecycleOwner, Observer { eventsList ->
            adapter = Adapter(eventsList.toTypedArray())
            linearLayoutManager = LinearLayoutManager(context)
            val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.adapter = adapter
        })
        view.findViewById<FloatingActionButton>(R.id.floatButton).setOnClickListener{
            eventDialog()
        }
        return view
    }
    fun retornar(view: View){
        view.findNavController().navigate(R.id.action_listFragment_to_loginFragment)
    }
    fun eventDialog() {
        val dialog = NewEventDialog()
        activity?.supportFragmentManager?.let { dialog.show(it, "newEvent") }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}