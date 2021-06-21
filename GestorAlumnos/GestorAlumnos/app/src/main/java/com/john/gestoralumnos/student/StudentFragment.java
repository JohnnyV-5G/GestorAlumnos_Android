package com.john.gestoralumnos.student;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.john.gestoralumnos.R;
import com.john.gestoralumnos.database.SQLite_Helper;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * <h1>Gestor Alumnos</h1>
 * <h2>Student Fragment</h2>
 * <p>Los fragments es como Android parte una Activity en dos. Dos tabs. Esta aqui es como se va comportar la del estudiante.</p>
 * @author JuanGregorioVazquezRodriguez JohnGVazquez@gmail.com
 * @version 1.0
 */
public class StudentFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final int EDIT_STUDENT_ACTIVITY = 1;  // The request code

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private ArrayList<StudentItem> mStudents;

    private RecyclerView recyclerView;

    /**
     * <p>Consultar de la base de datos todos los alumnos en ella y rellenar un ArrayList con esos datos.
     * Con el SQLite Helper rellenamos nuestra lista en lo llamado studentList pata mostrar en el dispositivo.</p>
     */
    private void retrieveStudentNames() {

        //retrieve all the students from the database and fill up the ArrayList
        //initializer our arraylist
        mStudents = new ArrayList<StudentItem>();

        //we fill up the arrayList
        List<StudentItem> studentItems = SQLite_Helper.getInstance(getContext()).selectAllStudents();
        //populate the list with the names of the studentItems
        for (StudentItem si : studentItems) {
            System.out.println(si);
            mStudents.add(si);

        }

    }

    /**
     * <p>El Create View</p>
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student, container, false);
        //fetching a view from the XML layout
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_Students);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        retrieveStudentNames();

        //here we are inflating the view with data that is being fetched
        recyclerView.setAdapter(
                new MyStudentRecyclerViewAdapter(getContext(), mStudents, new ItemClickListener() {
                    /**
                     *
                     * @param view
                     * @param position
                     */
                    @Override
                    public void onItemClick(View view, int position) {
                        //Toast.makeText(getContext(),"Position of clicked item: "+ position,Toast.LENGTH_SHORT).show();
                        StudentItem studentItem = getItem(position);
                        Intent intent = new Intent(view.getContext(), StudentActivity.class);
                        //http://jonsegador.com/2012/02/paso-de-datos-variables-entre-actividades-android/
                        intent.putExtra(StudentActivity.STUDENT_ID_KEY, studentItem.getId());

                        //https://developer.android.com/training/basics/intents/result?hl=es-419
                        startActivityForResult(intent, EDIT_STUDENT_ACTIVITY);

                    }
                }));

        FloatingActionButton fab;

        fab = (FloatingActionButton) view.findViewById(R.id.fabAddStudent);
        fab.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), StudentActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    /**
     * <p>Donde cojemos los datos del alumno</p>
     * @param position
     * @return
     */
    public StudentItem getItem(int position) {
        return mStudents.get(position);
    }

    /**
     * <p>Rellenamos los datos al fragment del estudiante</p>
     * @param requestCode
     * @param resultCode
     * @param data
     */
    //https://developer.android.com/training/basics/intents/result?hl=es-419
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == EDIT_STUDENT_ACTIVITY) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // refresh the student list
                //fetching a view from the XML layout
                //https://stackoverflow.com/questions/17023376/get-view-of-fragment-from-fragmentactivity
                recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView_Students);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                retrieveStudentNames();

                //here we are inflating the view with data that is being fetched
                recyclerView.setAdapter(
                        new MyStudentRecyclerViewAdapter(getContext(), mStudents, new ItemClickListener() {

                            @Override
                            public void onItemClick(View view, int position) {
                                //Toast.makeText(getContext(),"Position of clicked item: "+ position,Toast.LENGTH_SHORT).show();
                                StudentItem studentItem = getItem(position);
                                Intent intent = new Intent(view.getContext(), StudentActivity.class);
                                //http://jonsegador.com/2012/02/paso-de-datos-variables-entre-actividades-android/
                                intent.putExtra(StudentActivity.STUDENT_ID_KEY, studentItem.getId());

                                //https://developer.android.com/training/basics/intents/result?hl=es-419
                                startActivityForResult(intent, EDIT_STUDENT_ACTIVITY);

                            }
                        }));
            }
        }
    }
}
