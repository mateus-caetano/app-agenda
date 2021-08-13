package com.mateus.agenda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mateus.agenda.databinding.FragmentDetailsBinding
import androidx.navigation.fragment.navArgs
import model.Task

class DetailsFragment : Fragment() {

    private val navigationArgs: DetailsFragmentArgs by navArgs()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AgendaViewModel by activityViewModels{
        AgendaModelFactory(
            (activity?.application as AgendaApplication).database.itemFirebaseRepository()
        )
    }

    lateinit var item: Task

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun bind(item: Task){
        binding.apply {
            binding.textView.text = item.title
            binding.textView3.text = item.details
            binding.dlocation.text = item.location
            binding.dateTime.text = item.dateTime
            binding.dlink.text = item.link
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner){selected->
            item = selected
            bind(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}