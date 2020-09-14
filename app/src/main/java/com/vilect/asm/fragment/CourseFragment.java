package com.vilect.asm.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.vilect.asm.R;
import com.vilect.asm.activity.MainUIActivity;
import com.vilect.asm.adapter.CourseRecyclerViewCustomAdapter;
import com.vilect.asm.adapter.CourseSpinnerCustomAdapter;
import com.vilect.asm.dao.CoursesDAO;
import com.vilect.asm.model.CourseModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseFragment extends Fragment {

    private Context context;
    private Spinner spCourse;
    private RecyclerView rvCourse;
    private int[] selectedItemSpinner = new int[1];
    public static CourseRecyclerViewCustomAdapter courseAdapter = new CourseRecyclerViewCustomAdapter();
    private ImageView ivCourse;


    public CourseFragment() {
        // Required empty public constructor
    }

    public CourseFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        //mapping
        spCourse = view.findViewById(R.id.spCourse);
        rvCourse = view.findViewById(R.id.rvCourse);
        ivCourse = view.findViewById(R.id.ivCourse);

        ivCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainUIActivity)context).drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        setRecyclerViewAdapter();
        setSpinner();

        CoursesDAO coursesDAO = new CoursesDAO(context);
        coursesDAO.getAll();

        return view;
    }


    private void setSpinner()
    {
        spCourse.setAdapter(new CourseSpinnerCustomAdapter(context));
        spCourse.setSelection(0);
        spCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemSpinner[0] = position;
                setRecyclerViewAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setRecyclerViewAdapter()
    {
        int selected = selectedItemSpinner[0];
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rvCourse.setLayoutManager(layoutManager);
        courseAdapter = new CourseRecyclerViewCustomAdapter(context, selected);
        rvCourse.setAdapter(courseAdapter);
    }

}
