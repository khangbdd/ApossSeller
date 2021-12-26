package com.example.apossseller.uicontroler.fragment

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment
import com.example.apossseller.R

class SelectStatusDialog (myActivity: Activity, val selectStatusInterface: SelectStatusInterface, val currentStatus: String) {
    private var activity: Activity = myActivity
    private lateinit var dialog: AlertDialog

    interface SelectStatusInterface{
        fun onSaveClick(status: String)
    }

    fun startLoading(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.fragment_select_status,null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()

        val btnCancel: AppCompatButton  = dialog.findViewById(R.id.btn_cancel)
        val btnSave: AppCompatButton = dialog.findViewById(R.id.btn_save)
        val actvStatus: AutoCompleteTextView = dialog.findViewById(R.id.actv_status)
        val statusList = listOf("Pending", "Confirmed", "Delivering", "Cancel")
        val genderAdapter = ArrayAdapter(activity, R.layout.status_list_item, statusList)
        actvStatus.setAdapter(genderAdapter)
        actvStatus.setText(currentStatus, false)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnSave.setOnClickListener {
            selectStatusInterface.onSaveClick(actvStatus.text.toString())
        }
    }
    fun dismissDialog(){
        dialog.dismiss()
    }
}