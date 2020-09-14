package com.vilect.asm.adapter;

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
import com.vilect.asm.model.CourseModel;
import com.vilect.asm.service.ChangeDataService;

import static com.vilect.asm.LibraryClass.semesterList;
import static com.vilect.asm.LibraryClass.studentsList;
import static com.vilect.asm.LibraryClass.user;

public class MyCourseRecyclerViewCustomAdapter extends RecyclerView.Adapter<MyCourseRecyclerViewCustomAdapter.ViewHolder> {

    private Context context;
    private int selectedSemester;

    public MyCourseRecyclerViewCustomAdapter()
    {

    }

    public MyCourseRecyclerViewCustomAdapter(Context context, int selectedSemester) {
        this.context = context;
        this.selectedSemester = selectedSemester;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_recycler_view_my_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtName.setText(studentsList.get(0).getRegCourses().get(selectedSemester).getCourses().get(position).getName());
        holder.txtBeginDate.setText(studentsList.get(0).getRegCourses().get(selectedSemester).getCourses().get(position).getBeginDate());
        holder.txtEndDate.setText(studentsList.get(0).getRegCourses().get(selectedSemester).getCourses().get(position).getEndDate());
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Do you want to unregist this course?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //lấy các thông tin cần thiết
                        String semesterId = semesterList.get(selectedSemester).getId();
                        String studentId = user[0];
                        String courseId = semesterList.get(selectedSemester).getCourses().get(position).getId();

                        //truyền data vào intent
                        Intent intent = new Intent(context, ChangeDataService.class);
                        Bundle bundle = new Bundle();
                        bundle.putStringArray("course", new String[]{studentId, semesterId, courseId});
                        intent.putExtra("data", bundle);
                        intent.putExtra("act", "unReg");
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
        return studentsList.get(0).getRegCourses().get(selectedSemester).getCourses().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtBeginDate;
        TextView txtEndDate;
        Button btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtMyCourseName);
            txtBeginDate = itemView.findViewById(R.id.txtMyCourseBegin);
            txtEndDate = itemView.findViewById(R.id.txtMyCourseEnd);
            btnRemove = itemView.findViewById(R.id.btnRemove);

        }
    }
}
