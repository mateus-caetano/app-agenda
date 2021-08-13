package com.mateus.agenda

import android.app.AlertDialog
import android.app.Dialog
import android.content.ClipDescription
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mateus.agenda.databinding.FragmentListBinding
import model.Task
import com.mateus.agenda.databinding.NewEventDialogBinding


class NewEventDialog : DialogFragment() {

    private var _binding: NewEventDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NewEventDialogBinding.inflate(inflater, container, false)
        return binding.root
    }
    private val viewModel: AgendaViewModel by activityViewModels{
        AgendaModelFactory(
            (activity?.application as AgendaApplication).database.itemFirebaseRepository()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnYes.setOnClickListener {
            addNewItem()
        }
        binding.btnCancel.setOnClickListener {
            val action = NewEventDialogDirections.actionNewEventDialogToListFragment()
            findNavController().navigate(action)
        }
    }

    private fun isEntryValid(): Boolean{
        return viewModel.isEntryValid(binding.textView.text.toString(),
                binding.nelocation.text.toString(),
                binding.textView3.text.toString(),
            binding.nelink.text.toString())
    }

    private fun addNewItem(){
        if(isEntryValid()){
            viewModel.addNewItem(binding.textView.text.toString(),
                binding.nelocation.text.toString(),
                binding.textView3.text.toString(),
                binding.nelink.text.toString())
        }
        val action = NewEventDialogDirections.actionNewEventDialogToListFragment()
        findNavController().navigate(action)
    }
}