package VEM.Movil.collapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar!!.hide()
        db = FirebaseFirestore.getInstance();
        auth = Firebase.auth

        btn_wrap_it_up.setOnClickListener {
            val email = et_remail.text.toString()
            val pass = et_rpassword.text.toString()
            val fn = et_firstname.text.toString()
            val ln = et_lastname.text.toString()
            if (checkAllFields()) {
                // Code Firebase
                registerFirebase(email, pass, fn, ln)
            }
        }
    }

    private fun checkAllFields(): Boolean {
        return (checkFieldsName() && checkFieldEmail() && checkFieldsPass())
    }

    private fun checkFieldsName(): Boolean {
        val fn = et_firstname.text
        val ln = et_lastname.text

        if (fn.isNotBlank() && ln.isNotBlank()) {
            return true
        } else {
            Toast.makeText(this, "The first and last name fields are empty", Toast.LENGTH_LONG)
                .show()
            return false
        }

    }

    private fun checkFieldEmail(): Boolean {
        val email = et_remail.text
        if (email.isNotBlank()) {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                return true
            } else {
                Toast.makeText(this, "the email format is wrong", Toast.LENGTH_LONG).show()
                return false
            }
        } else {
            Toast.makeText(this, "the email is void", Toast.LENGTH_LONG).show()
            return false
        }
    }

    private fun checkFieldsPass(): Boolean {
        val pass1 = et_rpassword.text
        val pass2 = et_rpasswordrepeat.text

        println(pass1)
        println(pass2)

        if (pass1.isNotBlank() && pass2.isNotBlank()) {
            if (pass1.toString().equals(pass2.toString())) {
                if (pass1.length >= 8) {
                    return true
                } else {
                    Toast.makeText(
                        this,
                        "Password size is less than 8 characters",
                        Toast.LENGTH_LONG
                    ).show()
                    return false
                }
            } else {
                Toast.makeText(this, "The passwords are not the same", Toast.LENGTH_LONG).show()
                return false
            }
        } else {
            Toast.makeText(this, "The passwords are void", Toast.LENGTH_LONG).show()
            return false
        }
    }

    private fun registerDataBaseFirebase(email: String, password: String, fn: String, ln: String) {
        var user = hashMapOf(
            "email" to email.toString(),
            "pass" to password.toString(),
            "firstname" to fn.toString(),
            "lastname" to ln.toString()
        )

        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Registered!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("Error adding document", e)
            }

    }


    private fun registerFirebase(
        email: String,
        password: String,
        firstName: String,
        lastname: String
    ) {
        var username: UserProfileChangeRequest =
            UserProfileChangeRequest.Builder().setDisplayName(firstName).build()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    user.updateProfile(username)
                    Toast.makeText(
                        baseContext, "${user.email} it was created correctly.",
                        Toast.LENGTH_SHORT
                    ).show()
                    registerDataBaseFirebase(email, password, firstName, lastname)
                    updateUI()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed: The email could be registered.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI()
                }
            }
    }

    private fun updateUI() {
        val email = et_remail.text.toString()
        val pass = et_rpassword.text.toString()
        clearFields()

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("email", email)
        intent.putExtra("pass", pass)

        startActivity(intent)
    }

    private fun clearFields() {
        et_firstname.text.clear()
        et_lastname.text.clear()
        et_remail.text.clear()
        et_rpassword.text.clear()
        et_rpasswordrepeat.text.clear()
    }


}