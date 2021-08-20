package com.mateus.agenda

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.mateus.agenda.repositories.EventRepository
import com.mateus.agenda.viewModels.EventVewModel
import com.mateus.agenda.viewModels.factory.EventsListViewModelFactory
import model.Task
import java.util.*

class EditEventListDialogFragment : BottomSheetDialogFragment(){
    val args by navArgs<EditEventListDialogFragmentArgs>()

    val repository = EventRepository.instance()
    val viewModel: EventVewModel by activityViewModels<EventVewModel>({ EventsListViewModelFactory(repository) })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog, container, false)

        viewModel.getEventById(args.taskId).observe(viewLifecycleOwner, Observer { event ->
            view.findViewById<TextInputEditText>(R.id.new_title).setText(event?.title)
            view.findViewById<TextInputEditText>(R.id.new_description).setText(event?.details)
            view.findViewById<TextView>(R.id.new_date_time).setText(event?.dateTime)
            view.findViewById<TextInputEditText>(R.id.new_link).setText(event?.link)

            setButtons(view, event.id)

        })

        return view
    }

    private fun newTask(view: View?, id: String): Task? {
        val title = view?.findViewById<TextInputEditText>(R.id.new_title)?.text.toString()
        val description = view?.findViewById<TextInputEditText>(R.id.new_description)?.text.toString()
        val dateTime = view?.findViewById<TextView>(R.id.new_date_time)?.text.toString()
        val location = Location(LocationManager.NETWORK_PROVIDER)
        val link = view?.findViewById<TextInputEditText>(R.id.new_link)?.text.toString()
        return Task(
            id,
            title,
            description,
            dateTime,
            location,
            link
        )
    }

    private fun setButtons(view: View?, id: String) {
        view?.findViewById<Button>(R.id.edit_cancel_button)?.setOnClickListener {
            findNavController().popBackStack()
        }

        view?.findViewById<Button>(R.id.edit_confirm_button)?.setOnClickListener {
            editEvent(view, id)
        }
    }

    fun editEvent(view: View?, id: String) {

        val event = newTask(view, id)
        val result: Boolean? = event?.let { it -> viewModel.editEvent(it.id, event) }
        if(result == true) {
            Toast.makeText(context, "Operação concluída com sucesso", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Erro ao realizar alteração", Toast.LENGTH_SHORT).show()
        }
        val action = EditEventListDialogFragmentDirections.actionEditEventListDialogFragmentToListFragment()
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}