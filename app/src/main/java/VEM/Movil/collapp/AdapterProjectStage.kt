package VEM.Movil.collapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class AdapterProjectStage : BaseAdapter {
    private var stages: ArrayList<Stage> = ArrayList<Stage>()
    private var context: Context? = null

    constructor(context: Context, stages: ArrayList<Stage>) {
        this.context = context
        this.stages = stages
    }

    override fun getCount(): Int {
        return stages.size
    }

    override fun getItem(position: Int): Any {
        return stages[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val stage = stages[position]
        var inflater = LayoutInflater.from(context)
        var view = inflater.inflate(R.layout.project_stage,null)

        var tv_stagetext: TextView = view.findViewById(R.id.tv_stagetext)
        var tv_stagenum: TextView = view.findViewById(R.id.tv_stagenum)

        if(stages.isNotEmpty()){
            tv_stagetext.text = stage.Name+" Tasks: "
            tv_stagenum.text = stage.stagesNames.size.toString()
        }

        return view


    }
}
