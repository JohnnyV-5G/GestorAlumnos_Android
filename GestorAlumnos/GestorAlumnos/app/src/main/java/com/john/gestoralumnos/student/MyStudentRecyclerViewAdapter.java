package com.john.gestoralumnos.student;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.john.gestoralumnos.R;

import java.util.ArrayList;

/**
 * <h1>Gestor Alumnos</h1>
 * <h2>Recycler View Adapter for my student </h2>
 * <p>{@link RecyclerView.Adapter} that can display a {@link StudentItem} </p>
 * @author JuanGregorioVazquezRodriguez JohnGVazquez@gmail.com
 * @version 1.0

 */

//notes: https://hackernoon.com/android-recyclerview-onitemclicklistener-getadapterposition-a-better-way-3c789baab4db
public class MyStudentRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolderStudent> {

    private final ArrayList<StudentItem> mValues;
    private ItemClickListener onItemClickListener;

    /**
     * @param context
     * @param items
     * @param listener
     */
    public MyStudentRecyclerViewAdapter(Context context, ArrayList<StudentItem> items, ItemClickListener listener) {
        mValues = items;
        onItemClickListener = listener;
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolderStudent onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_list_item, parent, false);
        return new ViewHolderStudent(view);
    }

    /**
     * @param clickListener
     */
    public void setItemClickListener(ItemClickListener clickListener) {
        onItemClickListener = clickListener;
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolderStudent holder, int position) {
        //here we asign data to the holder Object
        holder.asignData(mValues.get(position));
        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {
                        onItemClickListener.onItemClick(v, holder.getAdapterPosition());
                    }
                }
        );
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return mValues.size();
    }


}
