package VEM.Movil.collapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_project_image.*

class NewProjectImageActivity : AppCompatActivity() {
    var id: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project_image)
        supportActionBar!!.hide()
        val grid: GridLayout = findViewById(R.id.grid_images)
        val btnFine: Button = findViewById(R.id.fine)
        eventSelectedImage(grid)


        btn_cancelNewProject.setOnClickListener {
            var intent: Intent = Intent(this, NewProjectActivity::class.java)
            startActivity(intent)
        }

        btnFine.setOnClickListener {
            if(id != -1){
                var intent: Intent = Intent(this, NewProjectActivity::class.java)
                intent.putExtra("idImage",id)
                startActivity(intent)
            }else{
                Toast.makeText(this,"An image was not chosen",Toast.LENGTH_SHORT).show()
            }

        }




    }

    private fun eventSelectedImage(grid: GridLayout) {
        var listimage = ImageIndex()
        for (i in 0 until grid.childCount) {
            val image: ImageView = grid.getChildAt(i) as ImageView
            image.setOnClickListener {
                for (i in 0 until grid.childCount) {
                    val image2: ImageView = grid.getChildAt(i) as ImageView
                    image2.background = null
                }
                image.background = getDrawable(R.drawable.image_border)
                id = listimage.getIDImage(i)
            }
        }
    }
}