package com.john.gestoralumnos.subject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.john.gestoralumnos.R;

import java.util.ArrayList;

/**
 * <h1>Gestor Alumnos</h1>
 * <h2>Recycler View Adapter para las asignaturas.</h2>
 * @author JuanGregorioVazquezRodriguez JohnGVazquez@gmail.com
 * @version 1.0
 */
public class MySubjectRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolderSubject> {
    private final ArrayList<String> mValues;
    private final SubjectFragment.OnListFragmentInteractionListener mListener;

    /**
     * <p>El RecycleViewAdpater de asignaturas</p>
     * @param items
     * @param listener
     */
    public MySubjectRecyclerViewAdapter(ArrayList<String> items, SubjectFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    /**
     * <p>el View donde vamos inflar (inflate) los datos.</p>
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolderSubject onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_list_item, parent, false);
        return new ViewHolderSubject(view);
    }

    /**
     * <p>Asignar datos al holder Object</p>
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolderSubject holder, int position) {
        holder.asignData(mValues.get(position));
    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mValues.size();
    }


}
