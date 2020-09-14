package com.vilect.asm.dao;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vilect.asm.model.CourseModel;
import com.vilect.asm.model.SemesterModel;

import java.util.ArrayList;

import static com.vilect.asm.LibraryClass.semesterList;
import static com.vilect.asm.fragment.CourseFragment.courseAdapter;

public class CoursesDAO {
    private DatabaseReference db;
    private Context context;

    public CoursesDAO(Context context) {
        this.db = FirebaseDatabase.getInstance().getReference();
        this.context = context;
    }

    public void getAllRuntime()
    {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                semesterList.clear();
                for (DataSnapshot data:dataSnapshot.getChildren())
                {
                    //mỗi children ở trong courses là một semester
                    //lấy id semester
                    String key = data.getKey();
                    SemesterModel semesterModel = new SemesterModel(key);
                    ArrayList<CourseModel> courses = new ArrayList<>();
                    //truy cập các node con của "key"
                    //sẽ lấy được các course của semester đó để truyền vào ArrayList
                    for (DataSnapshot data1:dataSnapshot.child(key).getChildren())
                    {

                        courses.add(data1.getValue(CourseModel.class));
                    }
                    semesterModel.setCourses(courses);
                    semesterList.add(semesterModel);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        db.child("courses").addListenerForSingleValueEvent(listener);
    }

    public void getAll()
    {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                semesterList.clear();
                for (DataSnapshot data:dataSnapshot.getChildren())
                {
                    //mỗi children ở trong courses là một semester
                    //lấy id semester
                    String key = data.getKey();
                    SemesterModel semesterModel = new SemesterModel(key);
                    ArrayList<CourseModel> courses = new ArrayList<>();
                    //truy cập các node con của "key"
                    //sẽ lấy được các course của semester đó để truyền vào ArrayList
                    for (DataSnapshot data1:dataSnapshot.child(key).getChildren())
                    {

                        courses.add(data1.getValue(CourseModel.class));
                    }
                    semesterModel.setCourses(courses);
                    semesterList.add(semesterModel);

                }

                //set lại Adapter
                courseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        db.child("courses").addValueEventListener(listener);
    }
}
