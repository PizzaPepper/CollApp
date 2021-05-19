package VEM.Movil.collapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar!!.hide()
        tv_signup.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            if (checkFields()) {
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
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




}