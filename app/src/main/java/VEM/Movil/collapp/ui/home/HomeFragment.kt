package VEM.Movil.collapp.ui.home

import VEM.Movil.collapp.*
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var db: FirebaseFirestore
    private lateinit var root: View
    var projects: ArrayList<Project> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_home, container, false)
        var welcome: TextView = root.findViewById(R.id.tv_welcome)
        var name = Home.name
        db = FirebaseFirestore.getInstance();

        welcome.setText("Welcome " + name)
        loadProjects()

        var float: FloatingActionButton = root.findViewById(R.id.float_add_stage)

        float.setOnClickListener {
            Toast.makeText(root.context, projects.toString(), Toast.LENGTH_LONG).show()
            var intent: Intent = Intent(root.context, NewProjectActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    private fun testLoadProjects() {
        projects.clear()
        var listview: ListView = root.findViewById(R.id.list_projects) as ListView
        var stagesSample = ArrayList<Stage>()

        stagesSample.add(
            Stage(
                "On going task:",
                arrayListOf("One", "Two", "Three", "Four", "Five")
            )
        )
        stagesSample.add(Stage("To be done task:", arrayListOf("One", "Two", "Three", "Four")))
        stagesSample.add(Stage("Completed tasks:", arrayListOf("One", "Two", "Three")))

        projects.add(
            Project(
                "0",
                arrayListOf("supergonzalez360@gmail.com"),
                R.drawable.png_009_leaf,
                "Project 1",
                Date(),
                Date(),
                stagesSample
            )
        )


        projects.add(
            Project(
                "0",
                arrayListOf("correo@gmail.com"), R.drawable.png_005_tree, "Project 2", Date(),
                Date(), ArrayList<Stage>()
            )
        )

        var adapter = AdapterProject(root.context, projects)
        listview.adapter = adapter

    }

    private fun loadProjects() {
        projects.clear()
        var listview: ListView = root.findViewById(R.id.list_projects) as ListView
        var emailUser = Home.email
        var list = ArrayList<Project>()
        var listfav = ArrayList<String>()

        //Long queries... Sorry

        //Check the list of favorites projects
        db.collection("users")
            .whereEqualTo("email", Home.email)
            .get()
            .addOnSuccessListener { docs ->
                for (doc in docs) {
                    //Get id's projects
                    listfav = doc["projectsfav"] as ArrayList<String>
                }
                //Query and add a list of favorites projects
                db.collection("project")
                    .whereIn(FieldPath.documentId(), listfav)
                    .get()
                    .addOnSuccessListener { docs ->
                        for (doc in docs) {
                            //add list of favorites projects
                            list.add(parseProject(doc))
                        }
                        //Consult and add all registered projects of the user
                        db.collection("project")
                            .whereArrayContains("emails", emailUser)
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    //
                                    projects.add(parseProject(document))
                                }
                                //Set adapter with projects and projects favorites
                                var adapter = AdapterProject(root.context, projects)
                                adapter.setFavs(list)
                                listview.adapter = adapter
                            }
                    }
            }
    }

    private fun parseProject(document: QueryDocumentSnapshot): Project {
        var projectDoc = Project("-1", ArrayList(), -1, "", Date(), Date(), ArrayList())
        var stagesDoc: ArrayList<Stage> = ArrayList()

        var mapStages: Map<String, Objects>
        var id = document.id
        var name = document["name"] as String
        var image = document["image"] as Long
        var emails = document["emails"] as ArrayList<String>
        var start = document["start_date"] as Timestamp
        var deadline = document["deadline"] as Timestamp
        var stages = document["stages"] as ArrayList<*>

        projectDoc.id = id
        projectDoc.Name = name
        projectDoc.Image = image.toInt()
        projectDoc.Emails = emails
        projectDoc.Start_date = start.toDate()
        projectDoc.Deadline = deadline.toDate()


        stages.forEach { item ->
            mapStages = item as Map<String, Objects>
            val name = mapStages["name"] as String
            var stagenames = ArrayList<String>()
            if (mapStages.containsKey("stagesnames")) {
                stagenames = mapStages["stagesnames"] as ArrayList<String>
            }

            stagesDoc.add(Stage(name, stagenames))
        }

        projectDoc.Stages = stagesDoc
        return projectDoc
    }


}