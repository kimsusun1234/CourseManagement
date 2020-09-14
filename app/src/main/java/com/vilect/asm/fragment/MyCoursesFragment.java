package com.vilect.asm.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.vilect.asm.adapter.MyCourseRecyclerViewCustomAdapter;
import com.vilect.asm.adapter.MyCourseSpinnerCustomAdapter;
import com.vilect.asm.dao.StudentDAO;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCoursesFragment extends Fragment {

    private Context context;
    private Spinner spMyCourse;
    private RecyclerView rvMyCourse;
    private int[] selectedSemester = new int[1];
    public static MyCourseRecyclerViewCustomAdapter myCourseAdapter = new MyCourseRecyclerViewCustomAdapter();
    private ImageView ivMyCourse;

    public MyCoursesFragment() {
        // Required empty public constructor
    }

    public MyCoursesFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_courses, container, false);
        spMyCourse = view.findViewById(R.id.spMyCourse);
        rvMyCourse = view.findViewById(R.id.rvMyCourse);
        ivMyCourse = view.findViewById(R.id.ivMyCourse);

        ivMyCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainUIActivity)context).drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        setSpinner();
        setAdapter();

        StudentDAO studentDAO = new StudentDAO(context);
        studentDAO.getAll();

        return view;
    }

    private void setSpinner()
    {
        spMyCourse.setAdapter(new MyCourseSpinnerCustomAdapter(context));
        spMyCourse.setSelection(0);
        spMyCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSemester[0] = position;
                setAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setAdapter()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rvMyCourse.setLayoutManager(linearLayoutManager);

        myCourseAdapter = new MyCourseRecyclerViewCustomAdapter(context, selectedSemester[0]);
        rvMyCourse.setAdapter(myCourseAdapter);
    }



}
