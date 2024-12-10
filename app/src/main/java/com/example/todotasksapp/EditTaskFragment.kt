package com.example.todotasksapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.todotasksapp.databinding.FragmentEditTaskBinding
import com.example.todotasksapp.databinding.FragmentTasksBinding


class EditTaskFragment : Fragment() {

    private var _binding:FragmentEditTaskBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditTaskBinding.inflate(inflater,container,false)
        val view = binding.root
        val taskId = EditTaskFragmentArgs.fromBundle(requireArguments()).taskId

        val application = requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application).taskDao
        val viewModelFactory = EditTaskViewModelFactory(taskId,dao)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(EditTaskViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.navigateToList.observe(viewLifecycleOwner, Observer{ navigate ->
            if (navigate){
                view.findNavController().navigate(R.id.action_editTask_to_tasks)
                viewModel.onNavigateToLiST()
            }

        })
        // Inflate the layout for this fragment
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}