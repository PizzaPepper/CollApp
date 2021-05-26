package VEM.Movil.collapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ProjectDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)

        supportActionBar!!.hide()

    }
}