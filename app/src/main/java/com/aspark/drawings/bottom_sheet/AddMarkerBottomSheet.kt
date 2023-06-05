package com.aspark.drawings.bottom_sheet

import android.content.DialogInterface
import android.content.DialogInterface.OnDismissListener
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.aspark.drawings.databinding.AddMarkerBottomSheetBinding
import com.aspark.drawings.model.Marker
import com.aspark.drawings.room.AppDatabase.Companion.getDatabase
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddMarkerBottomSheet(
    private val drawingId: Int,
    private val doubleTapX: Float,
    private val doubleTapY: Float,
    private val marker: Marker?,
    private val listener: OnDismissListener) : BottomSheetDialogFragment() {

    private lateinit var binding: AddMarkerBottomSheetBinding
    private val viewModel: AddMarkerViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = AddMarkerBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (marker != null) {

            binding.etMarkerTitle.text = marker.title.toEditable()
            binding.etMarkerDetails.text = marker.details.toEditable()
        }


        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {

            val db = getDatabase(requireContext())
            val markerDao = db.markerDao()
            val drawingDao = db.drawingDao()

            val title = binding.etMarkerTitle.text.toString().trim()
            val details = binding.etMarkerDetails.text.toString().trim()

            viewModel.saveMarker(drawingId, title, details, doubleTapX, doubleTapY,
                markerDao, drawingDao)
            dismiss()

            listener.onDismiss(object : DialogInterface {
                override fun cancel() {
                    TODO("Not yet implemented")
                }

                override fun dismiss() {
                    TODO("Not yet implemented")
                }

            })

        }

    }

}

//extension function to convert string to editable
private fun String.toEditable(): Editable? {

    return Editable.Factory.getInstance().newEditable(this)
}
