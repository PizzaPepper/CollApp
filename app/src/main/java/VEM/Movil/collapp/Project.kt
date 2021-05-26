package VEM.Movil.collapp

import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

/*
* NOTA:
* La imagen del proyecto no se si guardarla con solo el nombre del archivo
* o lo guardariamos literalmente en una variable (creo que es con un Int).
* */

data class Project(
    var id: String,
    var Emails: java.util.ArrayList<String>, var Image: Int, var Name: String,
    var Start_date: Date, var Deadline: Date, var Stages: java.util.ArrayList<Stage>
) : Serializable