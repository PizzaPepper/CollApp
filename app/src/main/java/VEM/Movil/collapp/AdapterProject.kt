package VEM.Movil.collapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.firebase.database.collection.LLRBNode
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterProject : BaseAdapter {
    private lateinit var db: FirebaseFirestore
    private var projects: ArrayList<Project> = ArrayList<Project>()
    private var projectsFav: ArrayList<Project> = ArrayList<Project>()
    private var context: Context? = null
    lateinit var view: View


    constructor(context: Context, projects: ArrayList<Project>){
        this.context = context
        this.projects = projects
        db = FirebaseFirestore.getInstance();
    }

    public fun setFavs(projectsFav: ArrayList<Project>){
        this.projectsFav = projectsFav
    }

    override fun getCount(): Int {
        return projects.size
    }

    override fun getItem(position: Int): Any {
        return projects[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val project = projects[position]
        val inflater = LayoutInflater.from(context)
        view = inflater.inflate(R.layout.project, null)
        val adapterStage = AdapterProjectStage(view.context, project.Stages)

        var tv_name_project: TextView = view.findViewById(R.id.tv_name_project) //Name Project
        var ib_star: ImageButton = view.findViewById(R.id.ic_star) //Button Star (Favorites)
        var iv_image_details: ImageView =
            view.findViewById(R.id.imageView2) //Image open Details Project (onclick)
        var iv_image_project: ImageView = view.findViewById(R.id.image_project)
        var lv_list: ListView = view.findViewById(R.id.list_stages) // List of Stages
        var tv_date_project: TextView = view.findViewById(R.id.tv_date_project) // DeadLine

        val ta = view.resources.obtainTypedArray(R.array.colorsProject)
        view.background.colorFilter = PorterDuffColorFilter(ta.getColor(position%4,0), PorterDuff.Mode.SRC_ATOP)

        //Set icon favorites projects
        for (p in projectsFav ){
            if (project.id == p.id){
                ib_star.setImageResource(R.drawable.ic_star_on)
            }
        }

        ib_star.setOnClickListener {

                ib_star.isSelected = !ib_star.isSelected

            if (ib_star.isSelected) {
                ib_star.isSelected = true
                ib_star.setImageResource(R.drawable.state_star)
                modeFavoritesDB(project.id, "Add")
            } else {
                ib_star.isSelected = false
                ib_star.setImageResource(R.drawable.state_star)
                modeFavoritesDB(project.id, "Delete")
            }
        }


        iv_image_details.setOnClickListener {
            var p = project
            var intent: Intent = Intent(view.context, ProjectDetailActivity::class.java)
            intent.putExtra("project", p)
            view.context.startActivity(intent)
        }

        //Set Info
        tv_name_project.text = project.Name
        iv_image_project.setImageResource(project.Image)
        lv_list.adapter = adapterStage
        tv_date_project.text = formatter(project.Deadline)


        return view
    }


    private fun formatter(date: Date): String {
        var c: Calendar = Calendar.getInstance()
        c.time = date
        var format1 = SimpleDateFormat("dd", Locale.US)
        var day: String = daySuffix(Integer.parseInt(format1.format(date)))
        var format2 = SimpleDateFormat("MMM", Locale.US)
        var mount: String = format2.format(c.time)


        var dateStr = "${mount} ${day}"
        return dateStr
    }

    private fun daySuffix(day: Int): String {
        return when (day % 10) {
            1 -> "${day}st"
            2 -> "${day}nd"
            3 -> "${day}rd"
            else -> "${day}th"
        }
    }

    private fun modeFavoritesDB(idProject: String, mode: String) {
        var idUser: String = "null"

        if (mode == "Add") {
            db.collection("users")
                .whereEqualTo("email", Home.email)
                .get()
                .addOnSuccessListener { docs ->
                    for (doc in docs) {
                        idUser = doc.id
                    }
                    db.collection("users")
                        .document(idUser)
                        .update("projectsfav", FieldValue.arrayUnion(idProject))
                        .addOnSuccessListener {
                        }
                }
        }
        if (mode == "Delete") {
            db.collection("users")
                .whereEqualTo("email", Home.email)
                .get()
                .addOnSuccessListener { docs ->
                    for (doc in docs) {
                        idUser = doc.id
                    }
                    db.collection("users")
                        .document(idUser)
                        .update("projectsfav", FieldValue.arrayRemove(idProject))
                        .addOnSuccessListener {
                        }
                }
        }


    }



}





