package com.mateus.agenda

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.navigation.fragment.findNavController


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
const val LOGIN_STATE = "LOGIN_STATE"
/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val loginWithPhoneButton = view.findViewById<Button>(R.id.login_with_phone_button)
        val loginWithGoogleButton = view.findViewById<Button>(R.id.login_with_google_button)

        activity?.getWindow()?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        if(getLoginState() == true) {
            findNavController().navigate(R.id.action_loginFragment_to_listFragment)
            return view
        }

        loginWithPhoneButton.setOnClickListener {
            saveLoginState()
            findNavController().navigate(R.id.action_loginFragment_to_listFragment)
        }
        loginWithGoogleButton.setOnClickListener {
            saveLoginState()
            findNavController().navigate(R.id.action_loginFragment_to_listFragment)
        }

        return view
    }

    fun saveLoginState() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putBoolean(LOGIN_STATE, true)
            commit()
        }
    }

    fun getLoginState(): Boolean? {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        return sharedPref?.getBoolean(LOGIN_STATE, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}