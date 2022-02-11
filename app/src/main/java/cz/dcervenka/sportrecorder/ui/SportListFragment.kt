package cz.dcervenka.sportrecorder.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cz.dcervenka.sportrecorder.R
import cz.dcervenka.sportrecorder.databinding.FragmentSportListBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SportListFragment : Fragment() {

    private var _binding: FragmentSportListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private lateinit var sportAdapter: SportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSportListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        setupRecyclerView()

        /*viewModel.sports.observe(viewLifecycleOwner) {
            sportAdapter.submitList(it)
        }*/

        /*binding.fab.setOnClickListener { vw ->
            Snackbar.make(vw, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
    }

    private fun setupRecyclerView() = binding.rvSport.apply {
        sportAdapter = SportAdapter()
        adapter = sportAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}