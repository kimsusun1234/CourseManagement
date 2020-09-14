package com.vilect.asm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vilect.asm.R;

import static com.vilect.asm.LibraryClass.studentsList;

public class MyCourseSpinnerCustomAdapter extends BaseAdapter {

    private Context context;

    public MyCourseSpinnerCustomAdapter(){

    }

    public MyCourseSpinnerCustomAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return studentsList.get(0).getRegCourses().size();
    }

    @Override
    public Object getItem(int position) {
        return studentsList.get(0).getRegCourses().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.custom_spinner_course, parent, false);


        TextView txtSemester = convertView.findViewById(R.id.txtSemesterNameSpinner);

        //lấy tên của semester
        txtSemester.setText(studentsList.get(0).getRegCourses().get(position).getId());
        return convertView;
    }
}
