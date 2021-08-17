package com.mateus.agenda

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputEditText
import com.mateus.agenda.repositories.EventRepository
import com.mateus.agenda.viewModels.EventVewModel
import com.mateus.agenda.viewModels.factory.EventsListViewModelFactory
import model.Task
import java.util.*

class NewEventDialog : DialogFragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var globalDialogLayout: View

    private lateinit var title: String
    private lateinit var description: String
    private var dateTime: String = "Sem data definda"
    private lateinit var link: String

    private val c = Calendar.getInstance()
    private val year = c.get(Calendar.YEAR)
    private val month = c.get(Calendar.MONTH)
    private val day = c.get(Calendar.DAY_OF_MONTH)
    private val hour = c.get(Calendar.HOUR_OF_DAY)
    private val minute = c.get(Calendar.MINUTE)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater;
        val dialogLayout = inflater.inflate(R.layout.new_event_dialog, null)
        globalDialogLayout = dialogLayout

        val dateTimeButton = dialogLayout.findViewById<LinearLayout>(R.id.linearlayoutDateTime)
        dateTimeButton.setOnClickListener {
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setView(dialogLayout)
                .setPositiveButton("Salvar",
                    DialogInterface.OnClickListener { dialog, id ->
                        saveEvent()
                    })
                .setNegativeButton("Cancelar",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun saveEvent() {
        val repository = EventRepository.Companion.instance()
        val viewModel: EventVewModel by activityViewModels<EventVewModel>({ EventsListViewModelFactory(repository) })

        title = globalDialogLayout?.findViewById<TextInputEditText>(R.id.new_title)?.text.toString()
        description = globalDialogLayout?.findViewById<TextInputEditText>(R.id.new_description)?.text.toString()
//        dateTime = DateFormat.format("dd-MM-yyyy hh:mm a", Date()).toString()
        link = globalDialogLayout?.findViewById<TextInputEditText>(R.id.new_link)?.text.toString()

        val result = viewModel.saveNewEvent(
            Task(
                title,
                description,
                dateTime,
                Location(LocationManager.NETWORK_PROVIDER),
                link)
        )
        if(result) {
            Toast.makeText(context, "Evento adicionado com sucesso", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "O evento não pôde ser adicionado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        dateTime = day.toString() + "/" + month.toString() + "/" + year.toString()
        TimePickerDialog(context, this, hour, minute, DateFormat.is24HourFormat(activity)).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        dateTime = dateTime + " às " + hourOfDay.toString() + "h" + minute.toString() + "m"
        globalDialogLayout.findViewById<TextView>(R.id.new_date_time).setText(dateTime)
    }
}