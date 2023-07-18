package com.example.notes.fragment


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.notes.App
import com.example.notes.R
import com.example.notes.databinding.FragmentAddNoteBinding
import com.example.notes.entities.Note
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.text.SimpleDateFormat
import java.util.*

class AddNoteFragment : Fragment() {

    private var selectedNoteColor: String = "#333333"
    private var selectedImagePath = ""
    private lateinit var binding: FragmentAddNoteBinding
    private var alertDialog: AlertDialog? = null
    private var alertDialogDelete: AlertDialog? = null
    private var noteBundle: Note? = null
    private lateinit var layoutMiscellaneous: LinearLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutMiscellaneous = view.findViewById(R.id.layoutMiscellaneous)

        binding.imageBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.textDateTime.text =
            SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                .format(Date())

        binding.imageSave.setOnClickListener {
            saveNote()
        }

        if (arguments != null) {
            noteBundle = requireArguments().getSerializable("key") as Note
            setViewOrUpdateNote()
        }

        binding.imageRemoveWebUrl.setOnClickListener {
            binding.textWenUrl.text = null
            binding.layoutWebUrl.visibility = View.GONE
        }

        binding.imageRemoveImage.setOnClickListener {
            binding.imageNote.setImageBitmap(null)
            binding.imageNote.visibility = View.GONE
            binding.imageRemoveImage.visibility = View.GONE
            selectedImagePath = ""
        }


        initMiscellaneous()
        setSubtitleIndicatorColor()

    }


    private fun saveNote() {
        if (binding.inputNoteTitle.text.toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "Note title can't be empty", Toast.LENGTH_SHORT).show()
            return
        } else if (binding.inputNoteSubtitle.text.toString().trim().isEmpty()
            && binding.inputNote.text.toString().trim().isEmpty()
        ) {
            Toast.makeText(requireContext(), "Note can't be empty", Toast.LENGTH_SHORT).show()
            return
        }
        var textWeb = ""

        if (binding.layoutWebUrl.visibility == View.VISIBLE) {
            textWeb = binding.textWenUrl.text.toString()
        }

        val note = Note(
            title = binding.inputNoteTitle.text.toString(),
            subtitle = binding.inputNoteSubtitle.text.toString(),
            noteText = binding.inputNote.text.toString(),
            dateTime = binding.textDateTime.text.toString(),
            color = selectedNoteColor,
            imagePath = selectedImagePath,
            webLink = textWeb
        )
        val noteUpdate = Note(
            id = noteBundle?.id,
            title = binding.inputNoteTitle.text.toString(),
            subtitle = binding.inputNoteSubtitle.text.toString(),
            noteText = binding.inputNote.text.toString(),
            dateTime = binding.textDateTime.text.toString(),
            color = selectedNoteColor,
            imagePath = selectedImagePath,
            webLink = textWeb
        )

        if (arguments != null) {
            App.db.noteDao().updateNote(noteUpdate)
        } else {

            App.db.noteDao().insertNote(note)
        }

        findNavController().navigateUp()


    }


    private fun initMiscellaneous() {
        val layoutMiscellaneous: LinearLayout = requireView().findViewById(R.id.layoutMiscellaneous)
        val bottomSheetBehavior = BottomSheetBehavior.from(layoutMiscellaneous)
        layoutMiscellaneous.findViewById<TextView>(R.id.textMiscellaneous).setOnClickListener {
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        val imageColor1 = layoutMiscellaneous.findViewById<ImageView>(R.id.imageColors1)
        val imageColor2 = layoutMiscellaneous.findViewById<ImageView>(R.id.imageColors2)
        val imageColor3 = layoutMiscellaneous.findViewById<ImageView>(R.id.imageColors3)
        val imageColor4 = layoutMiscellaneous.findViewById<ImageView>(R.id.imageColors4)
        val imageColor5 = layoutMiscellaneous.findViewById<ImageView>(R.id.imageColors5)

        layoutMiscellaneous.findViewById<View>(R.id.viewColor1).setOnClickListener {
            selectedNoteColor = "#333333"
            imageColor1.setImageResource(R.drawable.ic_done)
            imageColor2.setImageResource(0)
            imageColor3.setImageResource(0)
            imageColor4.setImageResource(0)
            imageColor5.setImageResource(0)
            setSubtitleIndicatorColor()
        }
        layoutMiscellaneous.findViewById<View>(R.id.viewColor2).setOnClickListener {
            selectedNoteColor = "#FDBE3B"
            imageColor1.setImageResource(0)
            imageColor2.setImageResource(R.drawable.ic_done)
            imageColor3.setImageResource(0)
            imageColor4.setImageResource(0)
            imageColor5.setImageResource(0)
            setSubtitleIndicatorColor()
        }
        layoutMiscellaneous.findViewById<View>(R.id.viewColor3).setOnClickListener {
            selectedNoteColor = "#FF4842"
            imageColor1.setImageResource(0)
            imageColor2.setImageResource(0)
            imageColor3.setImageResource(R.drawable.ic_done)
            imageColor4.setImageResource(0)
            imageColor5.setImageResource(0)
            setSubtitleIndicatorColor()
        }
        layoutMiscellaneous.findViewById<View>(R.id.viewColor4).setOnClickListener {
            selectedNoteColor = "#3A52FC"
            imageColor1.setImageResource(0)
            imageColor2.setImageResource(0)
            imageColor3.setImageResource(0)
            imageColor4.setImageResource(R.drawable.ic_done)
            imageColor5.setImageResource(0)
            setSubtitleIndicatorColor()
        }
        layoutMiscellaneous.findViewById<View>(R.id.viewColor5).setOnClickListener {
            selectedNoteColor = "#000000"
            imageColor1.setImageResource(0)
            imageColor2.setImageResource(0)
            imageColor3.setImageResource(0)
            imageColor4.setImageResource(0)
            imageColor5.setImageResource(R.drawable.ic_done)
            setSubtitleIndicatorColor()
        }

        if (arguments != null) {
            when (noteBundle!!.color) {
                "#FDBE3B" -> layoutMiscellaneous.findViewById<View>(R.id.viewColor2).performClick()
                "#FF4842" -> layoutMiscellaneous.findViewById<View>(R.id.viewColor3).performClick()
                "#3A52FC" -> layoutMiscellaneous.findViewById<View>(R.id.viewColor4).performClick()
                "#000000" -> layoutMiscellaneous.findViewById<View>(R.id.viewColor5).performClick()
            }
        }

        layoutMiscellaneous.findViewById<LinearLayout>(R.id.layoutAddImage).setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            if (ContextCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_STORAGE_PERMISSION
                )
            } else {
                selectImage()
            }
        }

        layoutMiscellaneous.findViewById<LinearLayout>(R.id.layoutAddUrl).setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            showAddUrlDialog()
        }

        if (arguments != null) {
            layoutMiscellaneous.findViewById<LinearLayout>(R.id.layoutDeleteNote).visibility =
                View.VISIBLE
            layoutMiscellaneous.findViewById<LinearLayout>(R.id.layoutDeleteNote)
                .setOnClickListener {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    showDeleteNoteDialog()
                }
        }
    }

    private fun showDeleteNoteDialog() {
        if (alertDialogDelete == null) {
            val builder = AlertDialog.Builder(requireContext())
            val view = LayoutInflater.from(requireContext()).inflate(
                R.layout.layout_delete_note,
                view?.findViewById(R.id.layoutAddUrlContainer)
            )
            builder.setView(view)
            alertDialogDelete = builder.create()
            if (alertDialogDelete!!.window != null) {
                alertDialogDelete!!.window?.setBackgroundDrawable(ColorDrawable(0))
            }

            view.findViewById<TextView>(R.id.textDeleteNote).setOnClickListener {
                noteBundle?.let { it1 -> App.db.noteDao().deleteNote(it1) }
                findNavController().navigateUp()
                alertDialogDelete!!.dismiss()
            }
            view.findViewById<TextView>(R.id.textCancel).setOnClickListener {
                alertDialogDelete!!.dismiss()
            }
        }

        alertDialogDelete!!.show()
    }

    companion object {
        const val REQUEST_CODE_STORAGE_PERMISSION = 1
        const val REQUEST_CODE_SELECT_IMAGE = 2
    }

    private fun setSubtitleIndicatorColor() {
        val gradientDrawable: GradientDrawable =
            binding.viewSubtitleIndicator.background as GradientDrawable
        gradientDrawable.setColor(Color.parseColor(selectedNoteColor))
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.isNotEmpty()) {
            selectImage()
        } else {
            Toast.makeText(requireContext(), "Permission Denied!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                val selectedImageUri = data.data
                if (selectedImageUri != null) {
                    try {
                        val inputStream =
                            requireActivity().contentResolver.openInputStream(selectedImageUri)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        binding.imageNote.setImageBitmap(bitmap)
                        binding.imageNote.visibility = View.VISIBLE
                        binding.imageRemoveImage.visibility = View.VISIBLE
                        selectedImagePath = getPathFromUri(selectedImageUri)


                    } catch (e: java.lang.Exception) {
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setViewOrUpdateNote() {
        binding.inputNoteTitle.setText(noteBundle?.title)
        binding.inputNoteSubtitle.setText(noteBundle?.subtitle)
        binding.inputNote.setText(noteBundle?.noteText)
        binding.textDateTime.text = noteBundle?.dateTime

        if (noteBundle?.imagePath != "") {
            binding.imageNote.setImageBitmap(BitmapFactory.decodeFile(noteBundle!!.imagePath))
            binding.imageNote.visibility = View.VISIBLE
            binding.imageRemoveImage.visibility = View.VISIBLE
            selectedImagePath = noteBundle!!.imagePath!!
        }

        if (noteBundle?.webLink != "") {
            binding.textWenUrl.text = noteBundle!!.webLink
            binding.layoutWebUrl.visibility = View.VISIBLE
        }

    }

    private fun getPathFromUri(content: Uri): String {
        var filePath = ""
        val cursor = requireActivity().contentResolver.query(content, null, null, null, null)
        if (cursor == null) {
            filePath = content.path.toString()
        } else {
            cursor.moveToFirst()
            val index = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath
    }

    private fun showAddUrlDialog() {
        if (alertDialog == null) {
            val builder = AlertDialog.Builder(requireContext())
            val view = LayoutInflater.from(requireContext()).inflate(
                R.layout.layout_add_url,
                requireView().findViewById(R.id.layoutAddUrlContainer)
            )
            builder.setView(view)

            alertDialog = builder.create()
            if (alertDialog!!.window != null) {
                alertDialog!!.window?.setBackgroundDrawable(ColorDrawable(0))
            }

            val inputUrl = view.findViewById<EditText>(R.id.inputURL)
            inputUrl.requestFocus()

            view.findViewById<TextView>(R.id.textAdd).setOnClickListener {
                if (inputUrl.text.toString().trim().isEmpty()) {
                    Toast.makeText(requireContext(), "Enter URL", Toast.LENGTH_SHORT).show()
                } else if (!Patterns.WEB_URL.matcher(inputUrl.text.toString()).matches()) {
                    Toast.makeText(requireContext(), "Enter valid URL", Toast.LENGTH_SHORT).show()
                } else {
                    binding.textWenUrl.setText(inputUrl.text.toString())
                    binding.layoutWebUrl.visibility = View.VISIBLE
                    alertDialog!!.dismiss()
                }
            }

            view.findViewById<TextView>(R.id.textCancel).setOnClickListener {
                alertDialog!!.dismiss()
            }

        }
        alertDialog!!.show()
    }
}