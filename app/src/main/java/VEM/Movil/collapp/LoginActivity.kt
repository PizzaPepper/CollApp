package VEM.Movil.collapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar!!.hide()
        auth = Firebase.auth

        var bundle = intent.extras

        if(bundle != null){
            val email: String? = bundle.getString("email")
            val pass: String? = bundle.getString("pass")

            et_email.setText(email)
            et_password.setText(pass)
        }




        tv_signup.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            val textEmail = et_email.text.toString()
            val textPass = et_password.text.toString()
            if (checkFields()) {
                loginFirebase(textEmail,textPass)
            } else {
                Toast.makeText(this, "The fields are empty!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkFields(): Boolean {
        val textEmail = et_email.text
        val textPass = et_password.text

        return !(textEmail.isBlank() && textPass.isBlank())
    }


    private fun loginFirebase(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Authentication Denied.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?){
        if(user != null){
            val intent: Intent = Intent(this, Home::class.java)
            intent.putExtra("email",user.email.toString())
            intent.putExtra("name",user.displayName.toString())

            //Toast.makeText(this,"${user.email.toString()}, ${user.displayName.toString()}",Toast.LENGTH_LONG).show()

            startActivity(intent)
        }

    }








}