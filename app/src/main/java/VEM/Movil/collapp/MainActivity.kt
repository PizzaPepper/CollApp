package VEM.Movil.collapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val intent = Intent(this, Login::class.java)

        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, 5000)


    }
}