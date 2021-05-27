package VEM.Movil.collapp

import android.content.Context
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

class AdapterStage : BaseAdapter {
    private lateinit var view: View
    private lateinit var project: Project
    private var listStages = ArrayList<String>()
    private var context: Context? = null
    private lateinit var db: FirebaseFirestore

    constructor(context: Context, listStages: ArrayList<String>) {
        this.context = context
        this.listStages = listStages
        db = FirebaseFirestore.getInstance()
    }

    public fun set_Project(project: Project) {
        this.project = project
    }

    override fun getCount(): Int {
        return listStages.size
    }

    override fun getItem(position: Int): Any {
        return listStages[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val stage: String = listStages[position]
        val inflater = LayoutInflater.from(context)
        view = inflater.inflate(R.layout.item_stage, null)

        val tv_name_task: TextView = view.findViewById(R.id.tv_name_stage_text)
        val bg : LinearLayout = view.findViewById(R.id.bg_stage)


        tv_name_task.text = stage

        val ta = bg.resources.obtainTypedArray(R.array.colorsProject)
        bg.background.colorFilter = PorterDuffColorFilter(ta.getColor(position%4,0), PorterDuff.Mode.SRC_ATOP)


        tv_name_task.setOnClickListener {
            val layoutInflater = LayoutInflater.from(view.context)
            val view = layoutInflater.inflate(R.layout.popup_change_stage, null)
            var listnames: ArrayList<String> = getListNames(project.Stages)


            val close: ImageButton = view.findViewById(R.id.btn_cancelChangeStage)
            val spinner: Spinner = view.findViewById(R.id.spinner_stages_1)
            val nameStage: TextView = view.findViewById(R.id.text_stage)
            val save: AppCompatButton = view.findViewById(R.id.btn_save_stage_1)
            val arrayAdapter =
                ArrayAdapter(view.context, android.R.layout.simple_list_item_1, listnames)

            nameStage.text = tv_name_task.text.toString()

            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter

            val pop = PopupWindow(view)
            pop.width = 450
            pop.height = 780
            pop.isFocusable = true
            pop.update()

            pop.showAtLocation(view, Gravity.CENTER, 0, 0)

            close.setOnClickListener {
                pop.dismiss()
            }



            save.setOnClickListener {
                val task: String = nameStage.text.toString()
                val stage: String = spinner.selectedItem as String


                if (!task.isNullOrBlank()) {
                    changeStage(task, stage)
                    pop.dismiss()
                } else {
                    Toast.makeText(view.context, "The field 'name' is empty!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        return view
    }

    private fun changeStage(task: String, stage: String) {
        val idProject = project.id
        var map: HashMap<String, Any>? = null
        db.collection("project")
            .document(idProject)
            .get()
            .addOnSuccessListener { doc ->
                map = doc.data as HashMap<String, Any>?
                map = setStageMap(map, task, stage)
                map?.let {
                    db.collection("project")
                        .document(idProject) //idProject
                        .set(it)
                        .addOnSuccessListener {
                            Toast.makeText(view.context, "Done!", Toast.LENGTH_LONG).show()
                        }
                }
                val liststages: ExpandableListView =
                    view.rootView.rootView.findViewById(R.id.list_stages_exp)
                val adapter = AdapterCustomExpandableList(view.context, project.Stages)
                adapter.set_project(project)
                liststages.setAdapter(adapter)


            }
    }

    private fun setStageMap(
        docMap: HashMap<String, Any>?,
        task: String,
        stage: String
    ): HashMap<String, Any>? {
        var list = docMap?.get("stages") as ArrayList<HashMap<String, Any>>
        //remove
        list.forEach { s ->
            if (s.containsKey("stagesnames")) {
                val l = s["stagesnames"] as ArrayList<String>
                val index = l.indexOf(task)
                if (index != -1) {
                    l.removeAt(index)
                }
            }
        }

        project.Stages.forEach { it ->
            val l = it.stagesnames
            val index = l.indexOf(task)
            if (index != -1) {
                l.removeAt(index)
            }
        }
        docMap["stages"] = list
        //update
        list.forEach { s ->
            if (s.containsKey("stagesnames")) {
                val l = s["stagesnames"] as ArrayList<String>
                if (s["name"] == stage) {
                    l.add(task)
                }
            } else {
                s["stagesnames"] = arrayListOf(task)
            }
        }

        project.Stages.forEach { it ->
            if (it.Name == stage) {
                it.stagesnames.add(task)
            }
        }
        docMap["stages"] = list
        return docMap
    }

    private fun getListNames(list: ArrayList<Stage>): ArrayList<String> {
        val listStr = ArrayList<String>()

        for (item in list) {
            listStr.add(item.Name)
        }
        return listStr
    }


}
