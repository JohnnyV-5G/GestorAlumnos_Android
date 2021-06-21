package com.john.gestoralumnos.dummy;

import android.content.Context;

import com.john.gestoralumnos.database.SQLite_Helper;
import com.john.gestoralumnos.student.StudentItem;

import java.util.List;

/**
 * <h1>Gestor alumnos</h1>
 * <h2>StudentContent</h2>
 * <p> Esta clase se crea automaticamente cuando se crea un "fragment"
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards. <p>
 * @author JuanGregorioVazquezRodriguez JohnGVazquez@gmail.com
 * @Version 1.0
 * TODO: Replace all uses of this class before publishing your app.
 */
public class StudentContent {
    SQLite_Helper dbHelper;

    /**
     * <p>An array of sample (dummy) items. El "sample", ejemplar, fue cambiado por mi lista de estudiantes</p>
     */
    public static List<StudentItem> STUDENTS;


    /**
     * <p>Hacemos un select de todos los estudiantes de la base de datos para cada fila, crea un objeto estudiante "student"
     * Agrega ese objeto "student" a la lista</p>
     *
     * @param context
     */
    public void populateStudentList(Context context) {

        STUDENTS = SQLite_Helper.getInstance(context).selectAllStudents();
    }


}
