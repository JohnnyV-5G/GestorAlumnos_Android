package com.john.gestoralumnos.users;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.john.gestoralumnos.R;
import com.john.gestoralumnos.database.SQLite_Helper;

/**
 * <h1>Gestor Alumnos</h1>
 *  <h2>Create Account Activity</h2>
 *  <p>La Activity para crear la cuenta del usuario.</p>
 *  @author JuanGregorioVazquezRodriguez JohnGVazquez@gmail.com
 *  @version 1.0
 */
public class CreateAccountActivity extends AppCompatActivity {
    //declaro los variables
    Button btn_ConfirmCreation;
    EditText editText_Nombre,editText_Apellidos, editText_Email,editText_UserName, editText_Password, editText_ConfirmPW;
    Context context;

    /**
     *
     * @param savedInstanceState
     * <p>Un "bundle" se usa pasa intercambiar datos entre 'Activities'.</p>
     * <p>Aqui es donde damos instruciones de como esta partye del software tiene que comportar. Aqui tambien insertamos los datos del
     * usuario a la base de datos.</p>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        context = this;

        //asignar variables a objetos
        btn_ConfirmCreation = (Button)findViewById(R.id.btn_Acceptar);
        editText_Nombre = (EditText) findViewById(R.id.editText_Nombre);
        editText_Apellidos = (EditText) findViewById(R.id.editText_Apellidos);
        editText_Email = (EditText) findViewById(R.id.editText_Email);
        editText_UserName = (EditText) findViewById(R.id.editText_Usuario);
        editText_Password = (EditText)findViewById(R.id.editText_Contrasena);
        editText_ConfirmPW = (EditText) findViewById(R.id.editText_ConfirmarContrasena);
        btn_ConfirmCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * <p>No permite campos vacios</p>
                 */

                int errors = 0;
                if(editText_Nombre.getText().toString().equals("")){
                    errors++;
                }
                if(editText_Apellidos.getText().toString().equals("")){
                    errors++;
                }
                if(editText_Email.getText().toString().equals("")){
                    errors++;
                }
                if(editText_UserName.getText().toString().equals("")){

                    errors++;
                }
                if(editText_Password.getText().toString().equals("")){
                    errors++;
                }
                if(editText_ConfirmPW.getText().toString().equals("")){
                    errors++;
                }
                if(errors>0){
                    Toast toast =  Toast.makeText(context,"Tiene que rellenar todos los campos.", Toast.LENGTH_LONG);
                    toast.show();
                }
                /**
                 * <p>Comprobar si el usuario ya exsiste o no en la base de datos y avisa de su exsistencia o no</p>
                 */
                else if(SQLite_Helper.getInstance(context).userExiste(editText_UserName.getText().toString())){
                    Toast toast =  Toast.makeText(context,"Usuario ya existe.", Toast.LENGTH_LONG);
                    toast.show();

                }
                /**
                 *<p> en este if, empezamos a insertar datos en la base de datos</p>
                 */
                else if(editText_Password.getText().toString().equals(editText_ConfirmPW.getText().toString())){
                    //insert into DB
                    String usuario = editText_UserName.getText().toString();
                    String contrasena = editText_Password.getText().toString();
                    String nombre = editText_Nombre.getText().toString();
                    String apellidos = editText_Apellidos.getText().toString();
                    String email = editText_Email.getText().toString();
                    SQLite_Helper.getInstance(context).insertUser(usuario, contrasena, nombre, apellidos, email);
                    //close the activity
                    finish();
                }
                else{
                    Toast toast =  Toast.makeText(context,"Contraseñas no coinciden.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}
