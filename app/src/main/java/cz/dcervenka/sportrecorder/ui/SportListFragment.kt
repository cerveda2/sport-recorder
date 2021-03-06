package cz.dcervenka.sportrecorder.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cz.dcervenka.sportrecorder.R
import cz.dcervenka.sportrecorder.databinding.FragmentSportListBinding
import cz.dcervenka.sportrecorder.db.Sport
import cz.dcervenka.sportrecorder.other.SortType
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SportListFragment : Fragment(), SportAdapter.ItemListener {

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
        setupRecyclerView()

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        when (viewModel.sortType) {
            SortType.ALL -> binding.spFilter.setSelection(0)
            SortType.LOCAL -> binding.spFilter.setSelection(1)
            SortType.REMOTE -> binding.spFilter.setSelection(2)
        }

        binding.spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                pos: Int,
                id: Long
            ) {
                when (pos) {
                    0 -> viewModel.sortRuns(SortType.ALL)
                    1 -> viewModel.sortRuns(SortType.LOCAL)
                    2 -> viewModel.sortRuns(SortType.REMOTE)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        viewModel.sports.observe(viewLifecycleOwner) {
            sportAdapter.submitList(it)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_new_sport -> {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() = binding.rvSport.apply {
        sportAdapter = SportAdapter(requireContext(), this@SportListFragment)
        adapter = sportAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onLongClick(sport: Sport) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Do you really want to delete this entry?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { _, _ -> viewModel.deleteSport(sport) }
        builder.setNegativeButton("No") { dialog, _ -> dialog.cancel() }

        val alert11: AlertDialog = builder.create()
        alert11.show()

        //viewModel.deleteSport(sport)
    }
}