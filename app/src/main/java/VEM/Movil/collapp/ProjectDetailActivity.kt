package VEM.Movil.collapp

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_project_detail.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ProjectDetailActivity : AppCompatActivity() {
    lateinit var project: Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)
        supportActionBar!!.hide()

        val bundle = intent.extras
        val iv_image_project: ImageView = findViewById(R.id.iv_image_project)
        val tv_name_project: TextView = findViewById(R.id.tv_name_project)
        val tv_detail_deadline: TextView = findViewById(R.id.tv_detail_deadline)
        val liststages: ExpandableListView = findViewById(R.id.list_stages_exp)
        val float_stage: FloatingActionButton = findViewById(R.id.float_add_stage)
        val layout: LinearLayout = findViewById(R.id.ll_main)


        if (bundle != null) {
            project = bundle.getSerializable("project") as Project
            iv_image_project.setImageResource(project.Image)
            tv_name_project.text = project.Name
            tv_detail_deadline.text = formatter(project.Deadline)
            val adapter = AdapterCustomExpandableList(this, project.Stages)
            liststages.setAdapter(adapter)
        }

        btn_cancelDetailProject.setOnClickListener {
            var intent: Intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        float_stage.setOnClickListener {
            val layoutInflater= LayoutInflater.from(this)
            val view = layoutInflater.inflate(R.layout.popup_stage,null)
            var listnames: ArrayList<String> = getListNames(project.Stages)


            val close: ImageButton = view.findViewById(R.id.btn_cancelNewStage)
            val spinner: Spinner = view.findViewById(R.id.spinner_stages)
            val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,listnames)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter

            val pop = PopupWindow(view)
            pop.width=450
            pop.height=780


            pop.showAtLocation(layout,Gravity.CENTER,0,0)

            close.setOnClickListener {
                pop.dismiss()
            }


        }

    }

    private fun formatter(date: Date): String {
        val c: Calendar = Calendar.getInstance()
        c.time = date
        val format1 = SimpleDateFormat("dd", Locale.US)
        val day: String = daySuffix(Integer.parseInt(format1.format(date)))
        val format2 = SimpleDateFormat("MMM", Locale.US)
        val mount: String = format2.format(c.time)


        return "${mount} ${day}"
    }

    private fun daySuffix(day: Int): String {
        return when (day % 10) {
            1 -> "${day}st"
            2 -> "${day}nd"
            3 -> "${day}rd"
            else -> "${day}th"
        }
    }

    private fun getListNames(list: ArrayList<Stage>): ArrayList<String>{
        val listStr = ArrayList<String>()

        for (item in list){
            listStr.add(item.Name)
        }
        return listStr
    }


}