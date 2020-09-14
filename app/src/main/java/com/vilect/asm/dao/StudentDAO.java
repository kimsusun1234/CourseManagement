package com.vilect.asm.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vilect.asm.model.CourseModel;
import com.vilect.asm.model.SemesterModel;
import com.vilect.asm.model.StudentModel;


import java.util.ArrayList;

import static com.vilect.asm.LibraryClass.studentsList;
import static com.vilect.asm.fragment.MyCoursesFragment.myCourseAdapter;

public class StudentDAO {

    private DatabaseReference db;
    private Context context;

    public StudentDAO(Context context) {
        this.context = context;
        this.db = FirebaseDatabase.getInstance().getReference();
    }

    public void getAllRuntime()
    {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentsList.clear();
                for (DataSnapshot data:dataSnapshot.getChildren())
                {
                    //mỗi data là một key student
                    StudentModel studentModel = new StudentModel();
                    studentModel.setId(data.getKey());
                    studentModel.setName(data.child("name").getValue(String.class));
                    studentModel.setPassword(data.child("password").getValue(String.class));
                    studentModel.setBirthday(data.child("birthday").getValue(String.class));

                    ArrayList<SemesterModel> regCourse = new ArrayList<>();
                    for (DataSnapshot data1:data.child("regCourses").getChildren())
                    {
                        //mỗi data1 là một key semester
                        SemesterModel semesterModel = new SemesterModel();
                        semesterModel.setId(data1.getKey());
                        ArrayList<CourseModel> courseModelArrayList = new ArrayList<>();
                        for (DataSnapshot data2:data1.getChildren())
                        {
                            courseModelArrayList.add(data2.getValue(CourseModel.class));
                        }
                        semesterModel.setCourses(courseModelArrayList);
                        regCourse.add(semesterModel);
                    }
                    studentModel.setRegCourses(regCourse);
                    studentsList.add(studentModel);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        db.child("students").addListenerForSingleValueEvent(valueEventListener);
    }

    public void getAll()
    {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentsList.clear();
                for (DataSnapshot data:dataSnapshot.getChildren())
                {
                    //mỗi data là một key student
                    StudentModel studentModel = new StudentModel();
                    studentModel.setId(data.getKey());
                    studentModel.setName(data.child("name").getValue(String.class));
                    studentModel.setPassword(data.child("password").getValue(String.class));
                    studentModel.setBirthday(data.child("birthday").getValue(String.class));

                    ArrayList<SemesterModel> regCourse = new ArrayList<>();
                    for (DataSnapshot data1:data.child("regCourses").getChildren())
                    {
                        //mỗi data1 là một key semester
                        SemesterModel semesterModel = new SemesterModel();
                        semesterModel.setId(data1.getKey());
                        ArrayList<CourseModel> courseModelArrayList = new ArrayList<>();
                        for (DataSnapshot data2:data1.getChildren())
                        {
                            courseModelArrayList.add(data2.getValue(CourseModel.class));
                        }
                        semesterModel.setCourses(courseModelArrayList);
                        regCourse.add(semesterModel);
                    }
                    studentModel.setRegCourses(regCourse);
                    studentsList.add(studentModel);
                }
                myCourseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        db.child("students").addValueEventListener(valueEventListener);

    }

    public void regCourse(String studentId, String semesterId, CourseModel course)
    {
        db.child("students").child(studentId).child("regCourses").child(semesterId).child(course.getId()).setValue(course).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Course registed successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void unRegCourse(String studentId, String semesterId, String courseId)
    {
        db.child("students").child(studentId).child("regCourses").child(semesterId).child(courseId).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(context, "Course unregisted successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void update(String id, String name, String birthday)
    {
        final boolean confirm[] = new boolean[2];
        confirm[0] = false;
        confirm[1] = false;
        db.child("students").child("name").setValue(name, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                confirm[0] = true;
            }
        });
        db.child("students").child("birthday").setValue(birthday, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                confirm[1] = true;
            }
        });

        if (confirm[0] && confirm[1])
        {
            Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Some error happen! Please try again later.", Toast.LENGTH_SHORT).show();
        }

    }

    public void updatePassword(String id, String newPass)
    {
        db.child("students").child(id).child("password").setValue(newPass);
    }
}
