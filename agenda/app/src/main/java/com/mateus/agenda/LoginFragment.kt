package com.mateus.agenda

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

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
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var ref: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val exception = task.exception
                if (task.isSuccessful) {
                    try {
                        // Google Sign In was successful, authenticate with Firebase
                        val account = task.getResult(ApiException::class.java)!!
                        Log.d("TAG", "firebaseAuthWithGoogle:" + account.id)
                        firebaseAuthWithGoogle(account.idToken!!)
                    } catch (e: ApiException) {
                        // Google Sign In failed, update UI appropriately
                        Log.w("TAG", "Google sign in failed", e)
                    }
                }
            }
        }

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
        ref = FirebaseAuth.getInstance()
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val loginWithPhoneButton = view.findViewById<Button>(R.id.login_with_phone_button)
        val loginWithGoogleButton = view.findViewById<SignInButton>(R.id.login_with_google_button)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()


        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        ref = FirebaseAuth.getInstance()

        activity?.getWindow()?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        /*if (getLoginState() == true) {
                findNavController().navigate(R.id.action_loginFragment_to_listFragment)
                return view
            }*/
        loginWithGoogleButton.setOnClickListener {
            resultLauncher.launch(Intent(googleSignInClient.signInIntent))
        }
        loginWithPhoneButton.setOnClickListener {
            //saveLoginState()
            findNavController().navigate(R.id.action_loginFragment_to_loginEmail)
        }

        return view
    }
        private fun firebaseAuthWithGoogle(idToken: String) {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            ref.signInWithCredential(credential)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithCredential:success")
                        val user = ref.currentUser
                        findNavController().navigate(R.id.action_loginFragment_to_listFragment)
                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithCredential:failure", task.exception)
                        //updateUI(null)
                    }
                }
        }

        fun saveLoginState() {
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
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

            // TODO: Rename and change types of parameters
            const val RC_SIGN_IN = 1
        }

    }
