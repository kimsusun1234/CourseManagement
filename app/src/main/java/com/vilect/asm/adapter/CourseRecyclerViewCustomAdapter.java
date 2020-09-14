package com.vilect.asm.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vilect.asm.R;
import com.vilect.asm.activity.MainUIActivity;
import com.vilect.asm.model.CourseModel;
import com.vilect.asm.service.ChangeDataService;

import static com.vilect.asm.LibraryClass.semesterList;
import static com.vilect.asm.LibraryClass.user;

public class CourseRecyclerViewCustomAdapter extends RecyclerView.Adapter<CourseRecyclerViewCustomAdapter.ViewHolder>{

    private Context context;
    private int selectedSemester;

    public CourseRecyclerViewCustomAdapter()
    {

    }

    public CourseRecyclerViewCustomAdapter(Context context, int selectedSemester) {
        this.context = context;
        this.selectedSemester = selectedSemester;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_recycler_view_course, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtName.setText(semesterList.get(selectedSemester).getCourses().get(position).getName());
        holder.txtBegin.setText(semesterList.get(selectedSemester).getCourses().get(position).getBeginDate());
        holder.txtEnd.setText(semesterList.get(selectedSemester).getCourses().get(position).getEndDate());
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Do you want to regist this course?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //lấy các thông tin cần thiết
                        String semesterId = semesterList.get(selectedSemester).getId();
                        String studentId = user[0];
                        CourseModel courseModel = semesterList.get(selectedSemester).getCourses().get(position);
                        String courseId = courseModel.getId();
                        String courseName = courseModel.getName();
                        String courseBegin = courseModel.getBeginDate();
                        String courseEnd = courseModel.getEndDate();

                        //truyền data vào intent
                        Intent intent = new Intent(context, ChangeDataService.class);
                        Bundle bundle = new Bundle();
                        bundle.putStringArray("course", new String[]{studentId, semesterId, courseId, courseName, courseBegin, courseEnd});
                        intent.putExtra("data", bundle);
                        intent.putExtra("act", "reg");
                        context.startService(intent);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return semesterList.get(selectedSemester).getCourses().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtBegin;
        TextView txtEnd;
        Button btnAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //mapping
            txtName = itemView.findViewById(R.id.txtCourseName);
            txtEnd = itemView.findViewById(R.id.txtCourseEnd);
            txtBegin = itemView.findViewById(R.id.txtCourseBegin);
            btnAdd = itemView.findViewById(R.id.btnCourseAdd);

        }
    }
}
