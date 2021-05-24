package VEM.Movil.collapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_new_project.*

class NewProjectActivity : AppCompatActivity() {

    var stages = ArrayList<Stage>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project)

        addStage()



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

    fun addStage(){

    }

    private class AdaptadorStages:BaseAdapter{
        var stages = ArrayList<Stage>()
        var contexto: Context? = null

        constructor(contexto: Context, stages: ArrayList<Stage>){
            this.stages = stages
            this.contexto = contexto
        }

        override fun getCount(): Int {
            return stages.size
        }

        override fun getItem(p0: Int): Any {
            return stages[0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var stg = stages[p0]
            var inflador = LayoutInflater.from(contexto)
            var vista = inflador.inflate(R.layout.stage, null)

            var stagename = vista.findViewById(R.id.stagename) as TextView

            return vista
        }
    }
}