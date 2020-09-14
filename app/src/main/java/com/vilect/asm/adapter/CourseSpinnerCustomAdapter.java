package com.vilect.asm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vilect.asm.R;

import static com.vilect.asm.LibraryClass.semesterList;

public class CourseSpinnerCustomAdapter extends BaseAdapter {

    private Context context;

    public CourseSpinnerCustomAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return semesterList.size();
    }

    @Override
    public Object getItem(int position) {
        return semesterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.custom_spinner_course, parent, false);

        TextView txtSemestername = convertView.findViewById(R.id.txtSemesterNameSpinner);
        txtSemestername.setText(semesterList.get(position).getId());
        return convertView;
    }
}
