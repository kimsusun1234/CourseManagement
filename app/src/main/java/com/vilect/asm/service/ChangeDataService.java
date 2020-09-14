package com.vilect.asm.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.vilect.asm.dao.StudentDAO;
import com.vilect.asm.model.CourseModel;


public class ChangeDataService extends Service {


    public ChangeDataService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getStringExtra("act").equals("reg"))
        {
            Bundle bundle = intent.getBundleExtra("data");

            CourseModel courseModel = new CourseModel(bundle.getStringArray("course")[2], bundle.getStringArray("course")[3], bundle.getStringArray("course")[4], bundle.getStringArray("course")[5]);
            StudentDAO studentDAO = new StudentDAO(this);

            studentDAO.regCourse(bundle.getStringArray("course")[0], bundle.getStringArray("course")[1], courseModel);
            //dừng service để tiết kiệm tài nguyên
            stopSelf();
        }
        else
        {
            if (intent.getStringExtra("act").equals("unReg"))
            {
                Bundle bundle = intent.getBundleExtra("data");
                StudentDAO studentDAO = new StudentDAO(this);
                studentDAO.unRegCourse(bundle.getStringArray("course")[0], bundle.getStringArray("course")[1], bundle.getStringArray("course")[2]);
                //dừng service để tiết kiệm tài nguyên
                stopSelf();
            }
        }


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
