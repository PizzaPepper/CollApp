package VEM.Movil.collapp

import java.util.*
import kotlin.collections.ArrayList

/*
* NOTA:
* La imagen del proyecto no se si guardarla con solo el nombre del archivo
* o lo guardariamos literalmente en una variable (creo que es con un Int).
* */

data class Project (var Emails:ArrayList<String>,var Image:String,val Name:String,
                    val Start_date: Date,val Deadline: Date,var Stages: ArrayList<Stage>)