package com.john.gestoralumnos.ui.main;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

/**
 * <h1>Gestor Alumnos</h1>
 * <h2>Page View Model</h2>
 * @author JuanGregorioVazquezRodriguez JohnGVazquez@gmail.com
 * @version 1.0
 */
public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        /**
         *
         * @param input
         * @return
         */
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });

    /**
     * @param index
     */
    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    /**
     * @return
     */
    public LiveData<String> getText() {
        return mText;
    }
}