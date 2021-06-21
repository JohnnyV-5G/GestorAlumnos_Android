package com.john.gestoralumnos.student;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.john.gestoralumnos.R;
import com.john.gestoralumnos.database.SQLite_Helper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;



/**
 * <h1>Gestor Alumnos</h1>
 * <h2>StudentActivity</h2>
 * <p>Datos y opciones para los alumnos/as.</p>
 * @author JuanGregorioVazquezRodriguez JohnGVazquez@gmail.com
 * @version 1.0
 */
public class StudentActivity extends AppCompatActivity {
    //this is the key for the value where we store the student I.D. (Look at "Map")
    public final static String STUDENT_ID_KEY = "STUDENT_ID_KEY";
    ArrayList<String> subjectBuffer = new ArrayList<String>();

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        // we get the extras from the intent
        Intent intent = getIntent();
        //doesn't matter what value we put in, as long as it's under 1
        final int studentID = intent.getIntExtra(STUDENT_ID_KEY, -5); //getIntExtra gets the asigned value for the student if there is one


        Button buttonConfirmStudent;
        final EditText editText_StudentName = (EditText) findViewById(R.id.editText_studentName);
        final EditText editText_StudentSurname = (EditText) findViewById(R.id.editText_studentSurname);
        final EditText editText_Email = (EditText) findViewById(R.id.editText_studentEmail);
        final ImageView imageView_Student = (ImageView) findViewById(R.id.imageView_Student);
        final ListView listView = (ListView) findViewById(R.id.listView_Subjects);
        buttonConfirmStudent = (Button) findViewById(R.id.btn_confirmStudentCreation);

        //here we are going to populate the fields if we have a valid student ID
        if (studentID != -5) {
            StudentItem si = SQLite_Helper.getInstance(this).selectStudent(studentID);
            editText_StudentName.setText(si.getNombre());
            editText_StudentSurname.setText(si.getApellido());
            editText_Email.setText(si.getEmail());
            buttonConfirmStudent.setText("Actualizar alumno");
            buttonConfirmStudent.setOnClickListener(new View.OnClickListener() {
                /**
                 *
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    byte[] empty = new byte[10];
                    Toast.makeText(StudentActivity.this, "being called", Toast.LENGTH_SHORT).show();
                    SQLite_Helper.getInstance(StudentActivity.this).updateStudent(studentID, editText_StudentName.getText().toString(), editText_StudentSurname.getText().toString(), editText_Email.getText().toString(), empty);

                    //https://www.codota.com/code/java/methods/android.widget.Adapter/getItem
                    Adapter adapter = listView.getAdapter();
                    int count = adapter.getCount(); //gets the number of item in the adapter
                    //declare string for the item/s that are in the list
                    String[] items = new String[count]; //delcare an array of int calling it items
                    for (int i = 0; i < items.length; i++) { //
                        //an array of strings of what is in the list
                        items[i] = (String) adapter.getItem(i);

                    }
                    //todo how to populate list, not working/saving
                    //I need to get the id of the data(math, english, Bases de datos, etc) of the string in the list
                    //try catch was suggested by Android Studio
                    try {
                        SQLite_Helper.getInstance(StudentActivity.this).getAsignatura(items);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    //subjectID
                   // SQLite_Helper.getInstance(StudentActivity.this).insertStuSub(studentID);
                   // finish();

                }
            });

        } else {


            buttonConfirmStudent.setOnClickListener(new View.OnClickListener() {
                /**
                 *
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    byte[] imageInByte = {};
                    Drawable drawable = imageView_Student.getDrawable();
                    if (drawable != null) {
                        //got this from StackOverflow: https://stackoverflow.com/questions/37779515/how-can-i-convert-an-imageview-to-byte-array-in-android-studio
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        imageInByte = baos.toByteArray();
                    }
                    //here we get all the data from the fields and save it into our DB
                    SQLite_Helper.getInstance(StudentActivity.this).insertStudent(editText_StudentName.getText().toString(), editText_StudentSurname.getText().toString(),
                            editText_Email.getText().toString(), imageInByte);
                    // insert the subjects for that student
                    int studentID = SQLite_Helper.getInstance(StudentActivity.this).selectLastInsertedStudentID();

                    finish();
                }
            });
        }
        /*This is the code to add the subjects into the list*/
        //https://stackoverflow.com/questions/9157887/android-how-to-use-spinner-in-an-alertdialog
        FloatingActionButton fabAddAsignatura = (FloatingActionButton) findViewById(R.id.fabAddAsignatura);
        fabAddAsignatura.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                //teacher will add his/her students here
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(StudentActivity.this, R.style.DialogSubjectStyle);   //could have also passed context;
                View mView = getLayoutInflater().inflate(R.layout.dialog_subject_spinner, null);
                mBuilder.setTitle("Asignar asignatura para alumno");
                final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner_subject);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(StudentActivity.this,
                        android.R.layout.simple_spinner_item,
                        SQLite_Helper.getInstance(StudentActivity.this).selectAllSubjects());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //here we add the subject to the student
                        subjectBuffer.add(mSpinner.getSelectedItem().toString());
                        //we populate our list
                        // This is the array adapter, it takes the context of the activity as a
                        // first parameter, the type of list view as a second parameter and your
                        // array as a third parameter.
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(StudentActivity.this, android.R.layout.simple_list_item_1, subjectBuffer);
                        listView.setAdapter(arrayAdapter);
                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                mBuilder.setView(mView);

                AlertDialog dialog = mBuilder.create();
                //background color fue cambiado en values/styles.xml
                //dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(BLACK);
                //dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(BLACK);
                dialog.show();

            }
        });

        Toast.makeText(this, "I.D. del estudiante" + getIntent().getIntExtra(STUDENT_ID_KEY, -1), Toast.LENGTH_LONG).show();


    }


}
