package VEM.Movil.collapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_new_project.*

class NewProjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project)

        supportActionBar!!.hide()

        btn_chooseImg.setOnClickListener {
            var intent: Intent = Intent(this,NewProjectImageActivity::class.java)
            startActivity(intent)
        }

        btn_cancelNewProject.setOnClickListener {
            var intent: Intent = Intent(this,Home::class.java)
            startActivity(intent)
        }


    }
}