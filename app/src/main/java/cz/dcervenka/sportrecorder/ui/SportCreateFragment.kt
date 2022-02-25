package cz.dcervenka.sportrecorder.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import cz.dcervenka.sportrecorder.databinding.FragmentSportCreateBinding
import cz.dcervenka.sportrecorder.db.Sport
import cz.dcervenka.sportrecorder.other.SortType
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class SportCreateFragment : Fragment() {

    private var _binding: FragmentSportCreateBinding? = null

    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSportCreateBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttonCancel.setOnClickListener {
                findNavController().navigateUp()
            }
            buttonSave.setOnClickListener {
                createSport()
            }
        }
    }

    private fun createSport() {
        val dateTimeStamp = Calendar.getInstance().timeInMillis
        val name = binding.etName.text.toString()
        val place = binding.etPlace.text.toString()
        val duration = binding.etDuration.text
        val distance = binding.etDistance.text
        val selectedId = binding.radioGroup.checkedRadioButtonId
        if (inputCheck(name, place, duration, distance, selectedId)) {
            val durationValid = TimeUnit.MINUTES.toMillis(duration.toString().toLong())
            val distanceValid = distance.toString().toInt()
            val storageEntryName = resources.getResourceEntryName(selectedId).uppercase()
            val storageType = SortType.fromString(storageEntryName)
            val sport = Sport(dateTimeStamp, name, place, durationValid, distanceValid, storageType!!)
            if (storageType == SortType.LOCAL) {
                viewModel.insertSport(sport)
                Toast.makeText(requireContext(), "Item inserted", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            } else {
                // TODO read documentation for creating documents in remote db
                //viewModel.postSport(sport)
            }
        } else {
            Toast.makeText(requireContext(), "You have to fill all fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(
        name: String,
        place: String,
        duration: Editable,
        distance: Editable,
        isSelected: Int
    ): Boolean {
        return !(TextUtils.isEmpty(name)
                || TextUtils.isEmpty(place)
                || duration.isEmpty()
                || distance.isEmpty()
                || isSelected == -1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}