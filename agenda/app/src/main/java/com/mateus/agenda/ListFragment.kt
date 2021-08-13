package com.mateus.agenda

import adapters.Adapter
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mateus.agenda.databinding.FragmentListBinding
import model.Task
import java.util.*

class ListFragment : Fragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        val adapter = Adapter{
            val action = ListFragmentDirections.actionListFragmentToDetailsFragment(it.id)
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        viewModel.allItems.observe(this.viewLifecycleOwner){items->
            items.let {
                adapter.submitList(items)
            }

        }
        binding.addbtn.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToNewEventDialog()
            this.findNavController().navigate(action)
        }

    }
}