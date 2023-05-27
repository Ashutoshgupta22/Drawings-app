package com.aspark.drawings.bottom_sheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.aspark.drawings.databinding.AddDrawingBottomSheetBinding
import com.aspark.drawings.room.AppDatabase.Companion.getDatabase
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddDrawingBottomSheet: BottomSheetDialogFragment() {

    private lateinit var binding: AddDrawingBottomSheetBinding
    private val viewModel: AddDrawingViewModel by viewModels()
    private lateinit var  imagePath:String
    private lateinit var picker: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        registerPhotoPicker()
        binding = AddDrawingBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.cvAddDrawingImg.setOnClickListener {

            picker.launch(PickVisualMediaRequest(ActivityResultContracts
                .PickVisualMedia.ImageOnly))

        }

        binding.btnSaveDrawing.setOnClickListener {

            val drawingDao = getDatabase(requireContext()).drawingDao()

            val title = binding.etDrawingName.text.toString().trim()
            val timeAdded = System.currentTimeMillis()

            viewModel.addDrawing(title,imagePath,timeAdded,drawingDao)
            dismiss()

        }

        binding.btnCancelDrawing.setOnClickListener {

            dismiss()
        }


    }

    private fun registerPhotoPicker() {

        picker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){

            if (it != null){

                Log.d("AddDrawingBottomSheet", "registerPhotoPicker: image uri: $it")
                imagePath = it.toString()
                binding.imgAddDrawing.setImageURI(it)

            }
            else
                Log.i("AddDrawingBottomSheet", "registerPhotoPicker: No image selected")
        }

    }


}