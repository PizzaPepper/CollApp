package VEM.Movil.collapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_new_project_image.*

class NewProjectImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project_image)

        supportActionBar!!.hide()

        btn_cancelNewProject.setOnClickListener {
            var intent: Intent = Intent(this,NewProjectActivity::class.java)
            startActivity(intent)
        }



    }
}