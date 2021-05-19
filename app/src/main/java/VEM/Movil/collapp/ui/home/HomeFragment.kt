package VEM.Movil.collapp.ui.home

import VEM.Movil.collapp.Home
import VEM.Movil.collapp.NewProjectActivity
import VEM.Movil.collapp.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        var welcome: TextView = root.findViewById(R.id.tv_welcome)

        var name= Home.name
        welcome.setText("Welcome "+name)

        /*val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        var float: FloatingActionButton = root.findViewById(R.id.float_add)

        float.setOnClickListener {
            var intent: Intent = Intent(root.context,NewProjectActivity::class.java)
            startActivity(intent)
        }

        return root
    }

   /* private fun openFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }*/

}