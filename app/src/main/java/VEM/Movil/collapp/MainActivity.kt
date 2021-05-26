package VEM.Movil.collapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.hide()



        val intent = Intent(this, LoginActivity::class.java)

        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, 5000)


    }
}