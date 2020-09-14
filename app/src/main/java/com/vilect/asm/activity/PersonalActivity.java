package com.vilect.asm.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.vilect.asm.R;
import com.vilect.asm.dao.StudentDAO;

import static com.vilect.asm.LibraryClass.studentsList;
import static com.vilect.asm.LibraryClass.user;

public class PersonalActivity extends AppCompatActivity {

    private TextView txtId;
    private EditText etName;
    private EditText etBirthday;
    private Button btnUpdate;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private StudentDAO studentDAO = new StudentDAO(PersonalActivity.this);
    private ActionBar actionBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);




        mapping();
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Personal Information");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        displayData();
        setBtnUpdateOnClick();
        setEtBirthdayOnClick();
        setOnTextChange();
        onNavItemOnClick();
    }

    private void mapping()
    {
        txtId = findViewById(R.id.etUserName);
        etName = findViewById(R.id.etUserName);
        etBirthday = findViewById(R.id.etBirthday);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setEnabled(false);
        drawerLayout = findViewById(R.id.drawerPersonal);
        navigationView = findViewById(R.id.navPersonal);
        toolbar = findViewById(R.id.toolbar);
    }


    //menu part
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.itChangePassword:
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalActivity.this);
                builder.setView(LayoutInflater.from(PersonalActivity.this).inflate(R.layout.custom_dialog_change_pass, null, false));
                builder.setPositiveButton("Change Password", null);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                //mapping
                final TextInputEditText etOld = alertDialog.findViewById(R.id.etPassDialog);
                final TextInputEditText etNew = alertDialog.findViewById(R.id.etNewPassDialog);
                final TextInputEditText etConfirm = alertDialog.findViewById(R.id.etPassConfirmDialog);
                Button btnChange = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

                btnChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etOld.getText().toString().equals(""))
                        {
                            Toast.makeText(PersonalActivity.this, "Old password must not be blank!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if (!etOld.getText().toString().equals(user[2]))
                            {
                                Toast.makeText(PersonalActivity.this, "Old password is not correst!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                if (etNew.getText().toString().equals(""))
                                {
                                    Toast.makeText(PersonalActivity.this, "New password must not be blank!", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    if (etConfirm.getText().toString().equals(""))
                                    {
                                        Toast.makeText(PersonalActivity.this, "Confirm password must not be blank!", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        if (!etNew.getText().toString().equals(etConfirm.getText().toString()))
                                        {
                                            Toast.makeText(PersonalActivity.this, "Confirm pass word must be the same with new password!", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            String newPass = etConfirm.getText().toString();
                                            user[1] = newPass;
                                            studentDAO.updatePassword(user[0], newPass);
                                            Toast.makeText(PersonalActivity.this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
                                            alertDialog.dismiss();
                                        }
                                    }
                                }
                            }
                        }
                    }
                });

            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void onNavItemOnClick()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.itHome:{
                        Intent intent = new Intent(PersonalActivity.this, MainUIActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    }

                    case R.id.itNews:{
                        Intent intent = new Intent(PersonalActivity.this, NewsActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    }

                    case R.id.itPersonal:{
                        drawerLayout.closeDrawers();
                        break;
                    }

                    default: break;
                }
                return false;
            }
        });
    }

    //elements part

    private void displayData()
    {
        for (int i = 0; i < studentsList.size(); i++)
        {
            if (studentsList.get(i).getId().equals(user[0]))
            {
                txtId.setText(studentsList.get(i).getId());
                etName.setText(studentsList.get(i).getName());
                etBirthday.setText(studentsList.get(i).getBirthday());
                break;
            }
        }
    }

    private void setBtnUpdateOnClick()
    {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        studentDAO.update(user[0], etName.getText().toString(), etBirthday.getText().toString());
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

    private void setEtBirthdayOnClick()
    {
        etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalActivity.this);
                builder.setView(LayoutInflater.from(PersonalActivity.this).inflate(R.layout.date_picker, null, false));
                builder.setPositiveButton("Set Date", null);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                //mapping
                final DatePicker datePicker = alertDialog.findViewById(R.id.date_picker);
                Button btnSetDate = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

                btnSetDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String date = datePicker.getDayOfMonth()+ "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
                        etBirthday.setText(date);
                    }
                });
            }
        });
    }

    private void setOnTextChange()
    {
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnUpdate.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
