package VEM.Movil.collapp

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_new_project.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class NewProjectActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var newProject: Project

    private var idImage = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project)
        supportActionBar!!.hide()
        val bundle = intent.extras

        db = FirebaseFirestore.getInstance();
        newProject =
            Project("-1", arrayListOf(Home.email), -1, "null", Date(), Date(), ArrayList<Stage>())


        if (bundle != null) {
            idImage = bundle.getInt("idImage")
            newProject.Image = idImage
            btn_chooseImg.setImageResource(idImage)
        }

        et_StartDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, month)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    et_StartDate.setText(SimpleDateFormat("yyyy-MM-dd").format(cal.time))
                    newProject.Start_date = cal.time
                }

            DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        et_deadline.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, month)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    et_deadline.setText(SimpleDateFormat("yyyy-MM-dd").format(cal.time))
                    newProject.Deadline = cal.time
                }

            DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        btn_chooseImg.setOnClickListener {
            val intent = Intent(this, NewProjectImageActivity::class.java)
            startActivity(intent)
        }

        btn_cancelNewProject.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        btn_addnewstage.setOnClickListener {
            if (newProject.Stages.size <= 3) {
                addStage()
            } else {
                Toast.makeText(this, "Exceeds the number of Stages", Toast.LENGTH_LONG).show()
            }
        }
        btn_Ready_NewProject.setOnClickListener {
            newProject.Name = et_ProjectName.text.toString()
            storeProjectDB(newProject)
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
    }

    private fun storeProjectDB(newProject: Project) {
        newProject.Name = et_ProjectName.text.toString()
        val project = hashMapOf(
            "emails" to newProject.Emails,
            "name" to newProject.Name,
            "image" to newProject.Image,
            "start_date" to newProject.Start_date,
            "deadline" to newProject.Deadline,
            "stages" to newProject.Stages
        )

        db.collection("project")
            .add(project)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Added Project!", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error adding document", Toast.LENGTH_LONG).show()
            }
    }

    private fun addStage() {
        var layout: LinearLayout = findViewById(R.id.layout_new_project)
        val view = layoutInflater.inflate(R.layout.popup_new_stage, null)
        val close: ImageButton = view.findViewById(R.id.btn_cancel)
        val namestage: EditText = view.findViewById(R.id.ed_newstage_name)
        val save: AppCompatButton = view.findViewById(R.id.btn_save_newstage)

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
            val name: String = namestage.text.toString()

            if (!name.isNullOrBlank()) {
                updateStage(name)
                pop.dismiss()
            } else {
                Toast.makeText(view.context, "The field 'name' is empty!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    private fun updateStage(name: String) {
        var list: ListView = findViewById(R.id.lv_Stages)
        newProject.Stages.add(Stage(name, ArrayList<String>()))
        val adapter = AdaptadorStages(this, newProject.Stages)
        adapter.setProject(newProject)
        list.adapter = adapter
    }

    private fun checkFields(): Boolean {
        val image = idImage
        val numS = newProject.Stages.size
        val nameP: EditText = findViewById(R.id.et_ProjectName)
        val startD: EditText = findViewById(R.id.et_StartDate)
        val deadlineD: EditText = findViewById(R.id.et_deadline)

        if (image == -1) {
            Toast.makeText(this, "An image was not selected", Toast.LENGTH_SHORT).show()
            return false
        }

        if (numS == 0) {
            Toast.makeText(this, "The stages are empty", Toast.LENGTH_SHORT).show()
            return false
        }

        if (nameP.text.isNullOrBlank()) {
            Toast.makeText(this, "The project name field is empty", Toast.LENGTH_SHORT).show()
            return false
        }

        if (startD.text.isNullOrBlank() && deadlineD.text.isNullOrBlank()) {
            Toast.makeText(this, "Dates are empty", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


    private class AdaptadorStages : BaseAdapter {
        var stages = ArrayList<Stage>()
        var contexto: Context? = null
        private lateinit var newProject: Project

        constructor(contexto: Context, stages: ArrayList<Stage>) {
            this.stages = stages
            this.contexto = contexto
        }

        public fun setProject(project: Project){
            this.newProject = project
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
            var close = vista.findViewById(R.id.btn_cancelNewProject) as ImageButton

            stagename.setText(stg.Name)

            close.setOnClickListener {
                if(newProject != null){
                    newProject.Stages.remove(stg)
                    this.notifyDataSetChanged()
                }
            }



            return vista
        }
    }
}