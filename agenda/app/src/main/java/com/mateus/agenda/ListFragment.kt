package com.mateus.agenda

import adapters.Adapter
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mateus.agenda.databinding.FragmentListBinding
import com.mateus.agenda.databinding.NewEventDialogBinding
import model.Task
import java.util.*

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

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AgendaViewModel by activityViewModels{
        AgendaModelFactory(
            (activity?.application as AgendaApplication).database.itemFirebaseRepository()
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val dataSet: MutableList<Task> = mutableListOf()
    private lateinit var linearLayoutManager: LinearLayoutManager


    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        val adapter = Adapter{}
        binding.recyclerView.adapter = adapter



        viewModel.allitems.observe(this.viewLifecycleOwner){items->
            adapter.submitList(items)
        }

        binding.addbtn.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToNewEventDialog()
            this.findNavController().navigate(action)
        }

    }

    /*companion object {
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
    }*/

    fun initializeDataSet() {
        for (i in 1..20) {
            dataSet.add(
                Task("test",
                    "task title " + i.toString(),
                    "this task is a mock task",
                    DateFormat.format("dd-MM-yyyy hh:mm a", Date()).toString(),
                    "4.9793584,-39.0585479,17",
                    "https://meet.google.com/")
            )
        }
    }

    public fun addEvent(event: Task) {
        dataSet.add(event)
    }
}