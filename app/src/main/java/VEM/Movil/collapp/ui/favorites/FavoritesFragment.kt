package VEM.Movil.collapp.ui.favorites

import VEM.Movil.collapp.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.widget.ListView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.util.*
import kotlin.collections.ArrayList

class FavoritesFragment : Fragment() {

    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var db: FirebaseFirestore
    private lateinit var root: View
    var projects: ArrayList<Project> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoritesViewModel =
            ViewModelProvider(this).get(FavoritesViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_favorites, container, false)
        db = FirebaseFirestore.getInstance();

        loadProjects()


        return root
    }

    private fun loadProjects() {
        projects.clear()
        var listview: ListView = root.findViewById(R.id.list_projects_favorites) as ListView
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
                            projects.add(parseProject(doc))
                        }
                        //Set adapter with projects and projects favorites
                        var adapter = AdapterFavoritesProjects(root.context, projects)
                        listview.adapter = adapter
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