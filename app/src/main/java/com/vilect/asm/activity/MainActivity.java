package com.vilect.asm.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vilect.asm.R;
import com.vilect.asm.dao.CoursesDAO;
import com.vilect.asm.dao.StudentDAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.vilect.asm.LibraryClass.studentsList;
import static com.vilect.asm.LibraryClass.user;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private final int REQUEST_CODE =  11330;
    private EditText etUser;
    private EditText etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkPermission())
        {
            requestPermission();
        }

        mapping();

        StudentDAO studentDAO = new StudentDAO(MainActivity.this);
        studentDAO.getAllRuntime();

        CoursesDAO coursesDAO = new CoursesDAO(MainActivity.this);
        coursesDAO.getAllRuntime();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = etUser.getText().toString();
                String pass = etPass.getText().toString();
                boolean result = false;
                if (id.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please enter the id!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (pass.equals(""))
                    {
                        Toast.makeText(MainActivity.this, "Please enter the password!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        for (int i = 0; i < studentsList.size(); i++)
                        {
                            if (id.equals(studentsList.get(i).getId()) && pass.equals(studentsList.get(i).getPassword()))
                            {
                                result = true;
                                break;
                            }
                        }
                    }
                }
                if (result)
                {
                    user[0] = id;
                    user[1] = pass;
                    Intent intent =new Intent(MainActivity.this, MainUIActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Username or password is not correct!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        printKeyHash(MainActivity.this);
    }

    private void mapping()
    {
        btnLogin = findViewById(R.id.btnLogin);
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
    }


    private boolean checkPermission()
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return false;
        }
        else
        {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }

    private void requestPermission()
    {
        //danh sach quyen
        String[] permissionList = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(MainActivity.this, permissionList, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_CODE:
            {
                //nếu cấp quyền thành công
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Permissions granted successful!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Permissions are not granted completely. Some function may not work!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    //get KeyHash
    public static String printKeyHash(Context context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        }
        catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        }
        catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }
}
