package com.mateus.agenda

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class NewEventDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.new_event_dialog, null))
                // Add action buttons
                .setPositiveButton("",
                    DialogInterface.OnClickListener { dialog, id ->
                        // sign in the user ...
                    })
                .setNegativeButton("",
                    DialogInterface.OnClickListener { dialog, id ->
//                        getDialog().cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}