package com.mateus.agenda

import adapters.Adapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.mateus.agenda.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AgendaViewModel by activityViewModels{
        AgendaModelFactory(
            (activity?.application as AgendaApplication).database.itemFirebaseRepository()
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = Adapter{
            val action = ListFragmentDirections.actionListFragmentToDetailsFragment(it.id)
            findNavController().navigate(action)
        }
        viewModel.allItems.observe(this.viewLifecycleOwner){items->
            adapter.submitList(items)
        }
        binding.loginWithGoogleButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToListFragment()
            this.findNavController().navigate(action)
        }
        binding.loginWithPhoneButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToListFragment()
            this.findNavController().navigate(action)
        }
    }

}