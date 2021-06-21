package com.john.gestoralumnos.users;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.john.gestoralumnos.ui.main.MainActivity;
import com.john.gestoralumnos.R;
import com.john.gestoralumnos.UserNotFoundException;
import com.john.gestoralumnos.database.SQLite_Helper;

/**
 * <h1>Gestor Alumnos</h1>
 * <h2>Login Activity</h2>
 * <p>La clase y metodos para crear nuestra pagina de login y sus intrucciones.</p>
 * @author JuanGregorioVazquezRodriguez JohnGVazquez@gmail.com
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * la variable USER_NAME_KEY
     */
    public static final String USER_NAME_KEY = "user name key";

    //declaro los variables
    Button btn_login, btn_CrearCuenta;
    EditText editText_UserName, editText_password;

    Context context;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       //asignar variables a objetos
       btn_login = (Button)findViewById(R.id.btn_Login);
       btn_CrearCuenta = (Button)findViewById(R.id.btn_Crear);
       editText_UserName = (EditText) findViewById(R.id.editText_UserName);
       editText_password = (EditText)findViewById(R.id.editText_PassWord);
       context = this;

        System.out.println(SQLite_Helper.getInstance(context).selectAllUsers());

//los listners

       //al darle al usuario, limpiamos el campo
        editText_UserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_UserName.setText("");
            }
        });

        editText_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_password.setText("");
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = editText_UserName.getText().toString();
                // look for user & password in db
                if(editText_password.getText().toString().equals("")){
                    Toast toast =  Toast.makeText(context,"No se permite valores vacios.", Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    //here we check that that pw input by user is the same as that in the db with a corresponding User
                  try {
/**
 * <p>
 * Declaramos un booleano para comprobar si lo que esta en la base de datos coincide con lo que popne (input) el usuario. Si coincide, permite el
 * paso (intent) a la pagina (activity) principal para gestionar y ver datos. Sino, le da un aviso y no permite acceder.</p>
 */
                      boolean matchPW = editText_password.getText().toString().equals(SQLite_Helper.getInstance(context).getUserPassword(usuario));
                      System.out.println(SQLite_Helper.getInstance(context).getUserPassword(usuario));

                      if (matchPW) {

                          Intent intent = new Intent(context, MainActivity.class);
                          intent.putExtra(USER_NAME_KEY,usuario);
                          startActivity(intent);

                          /*intent.putExtra(EXTRA_MESSAGE, message);
                          startActivity(intent);*/
                      }else{
                          Toast toast =  Toast.makeText(context,"Contraseña incorrecta..", Toast.LENGTH_LONG);
                          toast.show();
                      }

                  }catch (UserNotFoundException e){
                      Toast toast =  Toast.makeText(context,"Contraseña incorrecta.", Toast.LENGTH_LONG);
                      toast.show();
                  }

                }}
        });
/**
 *  <p>when button crear cuenta is clicked, all we do is change activity</p>
  */

        btn_CrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreateAccountActivity.class);
                startActivity(intent);
            }
        });



    }



}
