package com.john.gestoralumnos.subject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.john.gestoralumnos.R;
import com.john.gestoralumnos.database.SQLite_Helper;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Gestor Alumnos</h1>
 * <h2>SubjectFragment</h2>
 * <p>A fragment representing a list of Items.
 * Activities containing this fragment MUST implement
 * {@link OnListFragmentInteractionListener} interface.</p>
 * @author JuanGregorioVazquezRodriguez JohnGVazquez@gmail.com
 * @version 1.0
 */
public class SubjectFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private SubjectFragment.OnListFragmentInteractionListener mListener;
    private ArrayList<String> mSubjects;
    private MySubjectRecyclerViewAdapter rvAdapter;
    private RecyclerView recyclerView;

    /**
     *<p>Aqui vamos cojiendo los datos de estudiante y rellenando la List del estudiante</p>
     */
    private void retrieveSubjectNames() {

        //retrieve all the students from the database and fill up the ArrayList
        //initializer our arraylist
        mSubjects = new ArrayList<String>();

        //we fill up the arrayList
        List<String> subjectItems = SQLite_Helper.getInstance(getContext()).selectAllSubjects();
        //populate the list with the names of the studentItems
        for (String s : subjectItems) {

            mSubjects.add(s);

        }

    }

    /**
     * <p>El metodo de inflar en el Rycyler view los datos y de como se tiene que comportar, tanto
     * la informacion, como las intruciones de los botones.</p>
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject, container, false);
        //fetching a view from the XML layout

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_Subjects);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        retrieveSubjectNames();

        //here we are inflating the view with data that is being fetched
        rvAdapter = new MySubjectRecyclerViewAdapter(mSubjects, mListener);
        recyclerView.setAdapter(rvAdapter);

        FloatingActionButton fabAddSub;

        fabAddSub = (FloatingActionButton) view.findViewById(R.id.fabAddSubject);
        fabAddSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //https://stackoverflow.com/questions/10903754/input-text-dialog-android
                String subjectToInsert;
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.DialogSubjectStyle);
                builder.setTitle("Agregar asignatura");

// Set up the input
                final EditText input = new EditText(getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    /**
                     *
                     * @param dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String subjectToInsert;
                        // we would insert a subject in the db
                        subjectToInsert = input.getText().toString();
                        SQLite_Helper.getInstance(getContext()).insertSubject(subjectToInsert);


                        retrieveSubjectNames();
                        //here we are inflating the view with data that is being fetched
                        rvAdapter = new MySubjectRecyclerViewAdapter(mSubjects, mListener);
                        recyclerView.setAdapter(rvAdapter);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });
        return view;
    }

    /**
     *
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(String item);
    }
}
