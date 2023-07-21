package com.example.ndt_is_exciting_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.ndt_is_exciting_app.directory_layout.MainActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.MicrosoftBuilder
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class SignInMenu : ComponentActivity() {
    

    private lateinit var auth: FirebaseAuth

    private lateinit var signInButton : Button
    private lateinit var googleSignIn : ImageView
    private lateinit var microsoftSignIn : ImageView
    private lateinit var emailSignIn : ImageView

//    private val signInLauncher = registerForActivityResult(
//        FirebaseAuthUIActivityResultContract(),
//    ) { res ->
//        this.onSignInResult(res)
//    }

    companion object {
        private var TAG = "dbug Sign In Activity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_menu)

        googleSignIn = findViewById(R.id.google_Icon)
        microsoftSignIn = findViewById(R.id.microsoft_Icon)
        emailSignIn = findViewById(R.id.email_Icon)

        auth = FirebaseAuth.getInstance();

        googleSignIn.setOnClickListener{googleSignin()}
        microsoftSignIn.setOnClickListener{microsoftSignIn()}
        emailSignIn.setOnClickListener{emailSignIn()}



        signInButton = findViewById(R.id.signInButton)
        signInButton.setOnClickListener{
            startActivity(Intent(this@SignInMenu, MainActivity::class.java))
        }
    }

    private fun googleSignin() {
        Log.i(TAG,"Google Sign In")
    }

    private fun microsoftSignIn(){
        Log.i(TAG,"Microsoft Sign In")
    }

    private fun emailSignIn(){
        Log.i(TAG,"Email Sign In")
    }

//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            updateUI(currentUser)
//        }
//    }
//
//    private fun googleSignIn(){
//
//        var signInRequest = BeginSignInRequest.builder()
//            .setGoogleIdTokenRequestOptions(
//                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    // Your server's client ID, not your Android client ID.
//                    .setServerClientId(getString(R.string.default_web_client_id))
//                    // Only show accounts previously used to sign in.
//                    .setFilterByAuthorizedAccounts(true)
//                    .build()
//            )
//    }
//
//    private fun createAccount(email : String ,password : String){
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "createUserWithEmail:success")
//                    val user = auth.currentUser
//                    updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(
//                        baseContext,
//                        "Authentication failed.",
//                        Toast.LENGTH_SHORT,
//                    ).show()
//                    updateUI(null)
//                }
//            }
//    }
//
//
//
//    private fun createSignInIntent() {
//        // [START auth_fui_create_intent]
//        // Choose authentication providers
//        val providers = arrayListOf(
//            AuthUI.IdpConfig.EmailBuilder().build(),
//            AuthUI.IdpConfig.PhoneBuilder().build(),
//            AuthUI.IdpConfig.GoogleBuilder().build(),
//            AuthUI.IdpConfig.FacebookBuilder().build(),
//            AuthUI.IdpConfig.TwitterBuilder().build(),
//        )
//
//        // Create and launch sign-in intent
//        val signInIntent = AuthUI.getInstance()
//            .createSignInIntentBuilder()
//            .setAvailableProviders(providers)
//            .build()
//        signInLauncher.launch(signInIntent)
//        // [END auth_fui_create_intent]
//    }
//
//    // [START auth_fui_result]
//    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
//        val response = result.idpResponse
//        if (result.resultCode == RESULT_OK) {
//            // Successfully signed in
//            val user = FirebaseAuth.getInstance().currentUser
//            // ...
//        } else {
//            // Sign in failed. If response is null the user canceled the
//            // sign-in flow using the back button. Otherwise check
//            // response.getError().getErrorCode() and handle the error.
//            // ...
//        }
//    }
//    // [END auth_fui_result]
//
//    private fun signOut() {
//        // [START auth_fui_signout]
//        AuthUI.getInstance()
//            .signOut(this)
//            .addOnCompleteListener {
//                // ...
//            }
//        // [END auth_fui_signout]
//    }
//
//    private fun delete() {
//        // [START auth_fui_delete]
//        AuthUI.getInstance()
//            .delete(this)
//            .addOnCompleteListener {
//                // ...
//            }
//        // [END auth_fui_delete]
//    }
//
//    private fun themeAndLogo() {
//        val providers = emptyList<AuthUI.IdpConfig>()
//
//        // [START auth_fui_theme_logo]
//        val signInIntent = AuthUI.getInstance()
//            .createSignInIntentBuilder()
//            .setAvailableProviders(providers)
//            .setLogo(R.drawable.app_icon_your_company) // Set logo drawable
//            .setTheme(R.style.Theme_NDT_is_Exciting_App) // Set theme
//            .build()
//        signInLauncher.launch(signInIntent)
//        // [END auth_fui_theme_logo]
//    }
//
//    private fun privacyAndTerms() {
//        val providers = emptyList<AuthUI.IdpConfig>()
//        // [START auth_fui_pp_tos]
//        val signInIntent = AuthUI.getInstance()
//            .createSignInIntentBuilder()
//            .setAvailableProviders(providers)
//            .setTosAndPrivacyPolicyUrls(
//                "https://example.com/terms.html",
//                "https://example.com/privacy.html",
//            )
//            .build()
//        signInLauncher.launch(signInIntent)
//        // [END auth_fui_pp_tos]
//    }
//
//    fun emailLink() {
//        // [START auth_fui_email_link]
//        val actionCodeSettings = ActionCodeSettings.newBuilder()
//            .setAndroidPackageName( // yourPackageName=
//                "...", // installIfNotAvailable=
//                true, // minimumVersion=
//                null,
//            )
//            .setHandleCodeInApp(true) // This must be set to true
//            .setUrl("https://google.com") // This URL needs to be whitelisted
//            .build()
//
//        val providers = listOf(
//            AuthUI.IdpConfig.EmailBuilder()
//                .enableEmailLinkSignIn()
//                .setActionCodeSettings(actionCodeSettings)
//                .build(),
//        )
//        val signInIntent = AuthUI.getInstance()
//            .createSignInIntentBuilder()
//            .setAvailableProviders(providers)
//            .build()
//        signInLauncher.launch(signInIntent)
//        // [END auth_fui_email_link]
//    }
//
//    fun catchEmailLink() {
//        val providers: List<AuthUI.IdpConfig> = emptyList()
//
//        // [START auth_fui_email_link_catch]
//        if (AuthUI.canHandleIntent(intent)) {
//            val extras = intent.extras ?: return
//            val link = extras.getString("email_link_sign_in")
//            if (link != null) {
//                val signInIntent = AuthUI.getInstance()
//                    .createSignInIntentBuilder()
//                    .setEmailLink(link)
//                    .setAvailableProviders(providers)
//                    .build()
//                signInLauncher.launch(signInIntent)
//            }
//        }
//        // [END auth_fui_email_link_catch]
//    }
}