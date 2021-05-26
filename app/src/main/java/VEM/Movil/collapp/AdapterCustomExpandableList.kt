package VEM.Movil.collapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView

class AdapterCustomExpandableList : BaseExpandableListAdapter {
    private lateinit var project: Project
    private var listStages = ArrayList<Stage>()
    private var context: Context? = null

    constructor(context: Context, stages: ArrayList<Stage>) {
        this.context = context
        this.listStages = stages
    }

    public fun set_project(project: Project){
        this.project=project
    }

    override fun getGroupCount(): Int {
        return this.listStages.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return 1
    }

    override fun getGroup(groupPosition: Int): Any {
        return this.listStages[groupPosition].Name
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return this.listStages[groupPosition].stagesnames[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var stageName: String = listStages[groupPosition].Name
        val inflater = LayoutInflater.from(context)
        var view = inflater.inflate(R.layout.list_group_stages, null)

        var tv_name_stage: TextView = view.findViewById(R.id.tv_name_stage)
        var ic_arrows: ImageView = view.findViewById(R.id.ic_arrows)

        if (isExpanded) {
            ic_arrows.setImageResource(R.drawable.ic_arrow_open)
        } else {
            ic_arrows.setImageResource(R.drawable.ic_arrow_close)
        }

        tv_name_stage.text = stageName

        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val stages: ArrayList<String> = listStages[groupPosition].stagesnames
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.list_items_stages, null)

        val gridview: GridView = view.findViewById(R.id.gridview_stages)
        val adapter = AdapterStage(view.context, stages)
        adapter.set_Project(project)
        gridview.adapter = adapter

        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }
}