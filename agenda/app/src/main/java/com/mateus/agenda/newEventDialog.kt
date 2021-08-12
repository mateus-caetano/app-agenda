package com.mateus.agenda

import android.app.AlertDialog
import android.app.Dialog
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

    private var btn: FragmentListBinding? = null
    private val addbtn get() = btn!!


    lateinit var item: Task

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

    private val navigationArgs: ListFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.new_event_dialog, null))
                // Add action buttons
                .setPositiveButton("Salvar",
                    DialogInterface.OnClickListener { _, _ -> // save event
                        addNewItem()
                    })
                .setNegativeButton("Cancelar",
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
    private fun addingNewItem(){
        MaterialAlertDialogBuilder(requireContext())
            .setPositiveButton("Salvar"){_,_->
                //addNewItem()
            }
            .setNegativeButton("Cancelar"){dialog,_ -> dialog.cancel()}
            .show()
    }
    private fun bind(item: Task){
        binding.apply {
            textView.setText(item.title, TextView.BufferType.SPANNABLE)
            textView3.setText(item.details, TextView.BufferType.SPANNABLE)
            dateTime.setText(item.dateTime, TextView.BufferType.SPANNABLE)
            nelocation.setText(item.location, TextView.BufferType.SPANNABLE)
            nelink.setText(item.link, TextView.BufferType.SPANNABLE)
        }
        addbtn.addbtn.setOnClickListener { addingNewItem() }
    }

    private fun isEntryValid(): Boolean{
        return viewModel.isEntryValid("binding.textView.text.toString()",
                "binding.nelocation.text.toString()",
                "binding.textView3.text.toString()",
            "binding.nelink.text.toString()")
    }

    private fun addNewItem(){
        if(isEntryValid()){
            viewModel.addNewItem("binding.textView.text.toString()"
                ,"binding.nelocation.text.toString()"
                ,"binding.textView3.text.toString()","binding.nelink.text.toString()")
        }
        val action = NewEventDialogDirections.actionNewEventDialogToListFragment()
        findNavController().navigate(action)
    }
}