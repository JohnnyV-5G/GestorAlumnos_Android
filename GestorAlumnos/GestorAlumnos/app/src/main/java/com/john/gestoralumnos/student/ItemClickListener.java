package com.john.gestoralumnos.student;

import android.view.View;

/**
 * <h1>Gestor Alumnos</h1>
 * <h2>ItemClickListener</h2>
 * <p>Notes: an interface, the blueprint defines a method but does not enforce its behavior.
 *  Classes that want to implement an interface must implement its methods too.</p>
 * @author JuanGregorioVazquezRodriguez JohnGVazquez@gmail.com
 * @version 1.0
 *
 */

public interface ItemClickListener {
    void onItemClick(View view, int position);
}
