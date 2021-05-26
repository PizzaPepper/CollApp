package VEM.Movil.collapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class AdapterStage : BaseAdapter {
    lateinit var project: Project
    private var listStages = ArrayList<String>()
    private var context: Context? = null


    constructor(context: Context, listStages: ArrayList<String>) {
        this.context = context
        this.listStages = listStages
    }

    public fun set_Project(project: Project){
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
        val view = inflater.inflate(R.layout.item_stage, null)

        val tv_name_task: TextView = view.findViewById(R.id.tv_name_stage_text)

        tv_name_task.text = stage

        return view
    }
}
