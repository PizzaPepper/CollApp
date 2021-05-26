package VEM.Movil.collapp

import java.io.Serializable
import java.util.*

/*
* NOTA:
* La imagen del proyecto no se si guardarla con solo el nombre del archivo
* o lo guardariamos literalmente en una variable (creo que es con un Int).
* */

data class Project(
    var id: String,
    var Emails: ArrayList<String>, var Image: Int, var Name: String,
    var Start_date: Date, var Deadline: Date, var Stages: ArrayList<Stage>
) : Serializable