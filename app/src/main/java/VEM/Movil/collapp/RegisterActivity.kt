package VEM.Movil.collapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_wrap_it_up.setOnClickListener {
            if(checkAllFields()){
            //TODO: code Firebase
            Toast.makeText(this,"PASS!",Toast.LENGTH_LONG).show()
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
            Toast.makeText(this, "The first and last name fields are empty", Toast.LENGTH_LONG).show()

            return false
        }

    }

    private fun checkFieldEmail(): Boolean {
        val email = et_remail.text
        if (email.isNotBlank()) {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                return true
            }else{
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
}