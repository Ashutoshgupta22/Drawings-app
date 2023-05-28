package com.aspark.drawings.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.aspark.drawings.AddMarkerViewModel
import com.aspark.drawings.databinding.AddMarkerBottomSheetBinding
import com.aspark.drawings.room.AppDatabase.Companion.getDatabase
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddMarkerBottomSheet(
    private val drawingId: Int,
    private val markerCount: Int,
    private val doubleTapX: Float,
    private val doubleTapY: Float) : BottomSheetDialogFragment() {

    private lateinit var binding: AddMarkerBottomSheetBinding
    private val viewModel: AddMarkerViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = AddMarkerBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {

            val db = getDatabase(requireContext())
            val markerDao = db.markerDao()
            val drawingDao = db.drawingDao()

            val title = binding.etMarkerTitle.text.toString().trim()
            val details = binding.etMarkerDetails.text.toString().trim()

            viewModel.saveMarker(drawingId,title,details,doubleTapX,doubleTapY,markerDao)
            viewModel.addMarkerCount(drawingId ,(markerCount+1), drawingDao)
            dismiss()
        }

    }

}