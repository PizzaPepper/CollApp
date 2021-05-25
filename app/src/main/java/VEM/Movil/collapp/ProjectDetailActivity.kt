package VEM.Movil.collapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_project_detail.*
import kotlinx.android.synthetic.main.stage.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ProjectDetailActivity : AppCompatActivity() {
    lateinit var project: Project
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)
        supportActionBar!!.hide()
        db = FirebaseFirestore.getInstance();


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
            val layoutInflater = LayoutInflater.from(this)
            val view = layoutInflater.inflate(R.layout.popup_stage, null)
            var listnames: ArrayList<String> = getListNames(project.Stages)


            val close: ImageButton = view.findViewById(R.id.btn_cancelNewStage)
            val spinner: Spinner = view.findViewById(R.id.spinner_stages)
            val nameStage: EditText = view.findViewById(R.id.ed_stage_name)
            val save: AppCompatButton = view.findViewById(R.id.btn_save_stage)
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listnames)

            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter

            val pop = PopupWindow(view)
            pop.width = 450
            pop.height = 780
            pop.isFocusable = true
            pop.update()

            pop.showAtLocation(layout, Gravity.CENTER, 0, 0)

            close.setOnClickListener {
                pop.dismiss()
            }

            save.setOnClickListener {
                val stage: String = spinner.selectedItem as String
                val name: String = nameStage.text.toString()

                if (!name.isNullOrBlank()) {
                    updatestage(stage, name)
                    pop.dismiss()
                } else {
                    Toast.makeText(view.context, "The field  '' is empty!", Toast.LENGTH_SHORT)
                        .show()
                }
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

    private fun getListNames(list: ArrayList<Stage>): ArrayList<String> {
        val listStr = ArrayList<String>()

        for (item in list) {
            listStr.add(item.Name)
        }
        return listStr
    }

    private fun updatestage(stage: String, name: String) {
        val idProject = project.id
        var map: HashMap<String, Any>? = null

        db.collection("project")
            .document(idProject)
            .get()
            .addOnSuccessListener { doc ->
                map = doc.data as HashMap<String, Any>?
                map = setStageMap(map, stage, name)

                map?.let {
                    db.collection("project")
                        .document(idProject)
                        .set(it)
                        .addOnSuccessListener {
                            Toast.makeText(this,"Added!",Toast.LENGTH_LONG).show()
                        }
                }
            }

    }

    private fun setStageMap(docMap: HashMap<String, Any>?, stage: String, name: String): HashMap<String,Any>? {
        var list = docMap?.get("stages") as ArrayList<HashMap<String, Any>>

        list.forEach { s ->
            if (s["name"] == stage) {
                if (s.containsKey("stagesnames")) {
                    var l = s["stagesnames"] as ArrayList<String>
                    l.add(name)
                    s["stagesnames"] = l
                } else {
                    s["stagesnames"] = arrayListOf(name)
                }
            }
        }
        docMap["stages"] = list
        return docMap
    }


}