package com.john.gestoralumnos.subject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.john.gestoralumnos.R;

/**
 * <h1>Gestor Alumnos</h1>
 * <h2>ViewHolderSubject</h2>
 * <p> Un Viewholder es básicamente un objeto que hace de puente entre una serie de componentes
 * visuales (views) y datos (variables, objetos). Es lo que metemos dentro de nuestro RecyclerView</p>
 * @author JuanGregorioVazquezRodriguez JohnGVazquez@gmail.com
 * @version 1.0
 */

public class ViewHolderSubject extends RecyclerView.ViewHolder {
    TextView nombre;

    public ViewHolderSubject(@NonNull View itemView) {
        super(itemView);
        //link textview with its corresponding item/view in the layout
        nombre = (TextView) itemView.findViewById(R.id.subjectNombre);

    }

    /**
     * <p>We pass the name of the student here.</p>
     *
     * @param name
     */
    public void asignData(String name) {
        nombre.setText(name);

    }
}
