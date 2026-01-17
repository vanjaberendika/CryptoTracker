package com.berendika.currencytracker.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.berendika.currencytracker.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CurrencyPickerBottomSheet(
    private val title: String,
    private val items: List<String>,
    private val onSelected: (String) -> Unit
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_sheet_currency_picker, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tvPickerTitle).text = title

        val lv = view.findViewById<ListView>(R.id.lvPicker)
        lv.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)

        lv.setOnItemClickListener { _, _, position, _ ->
            onSelected(items[position])
            dismiss()
        }
    }
}