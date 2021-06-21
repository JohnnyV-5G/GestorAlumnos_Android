package com.john.gestoralumnos.student;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.john.gestoralumnos.R;

/**
 * <h1>Gestor Alumnos</h1>
 * <h2>StudentItem</h2>
 * <p>Un Viewholder es básicamente un objeto que hace de puente entre una serie de componentes
 * visuales (views) y datos (variables, objetos). Esto es lo que se mete dentro de nuestro RecyclerView</p>
 *
 * @author JuanGregorioVazquezRodriguez JohnGVazquez@gmail.com
 * @version 1.0
 */

//notes: https://gist.github.com/grantland/cd70814fe4ac369e3e92

public class ViewHolderStudent extends RecyclerView.ViewHolder {
    TextView nombre;
    StudentItem studentItem;

    /**
     * <p> Conectar el textView con so correspondiente item/view layout</p>
     * @param itemView
     */
    public ViewHolderStudent(@NonNull View itemView) {
        super(itemView);

        nombre = (TextView)itemView.findViewById(R.id.studentNombre);

    }

    /**
     *<p>Asignamos datos.</p>
     * @param si
     * es:  pasamos erl nombre del estudiante aqui
     * eng: we pass the name of the student here
     */

    public void asignData(StudentItem si){
        nombre.setText(si.getNombre() +" " + si.getApellido());
        this.studentItem=si;

    }

}
