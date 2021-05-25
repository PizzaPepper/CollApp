package VEM.Movil.collapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_custom_dialog_new_stage.*
import kotlinx.android.synthetic.main.activity_register.*



class CustomDialog_NewStage : DialogFragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_et);
        return inflater.inflate(R.layout.activity_custom_dialog_new_stage, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        btn_Add_New_Stage.setOnClickListener {
            val name = et_StageName.text.toString()

            if (checkFieldsName()) {
                // Code Firebase
                registerDataBaseFirebase(name)
            }
        }
    }


    private fun checkFieldsName(): Boolean {
        val sn = et_StageName.text

        if (sn.isNotBlank()) {
            return true
        } else {
            Toast.makeText(this.context, "The name is empty", Toast.LENGTH_LONG).show()
            return false
        }
    }

    private fun registerDataBaseFirebase(name: String) {
        var user = hashMapOf(
            "name" to name.toString(),
        )
        db.collection("stages")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("${documentReference.id}", "Stage Added!")
            }
            .addOnFailureListener { e ->
                Log.w("Error adding Stage", e)
            }

    }


}



