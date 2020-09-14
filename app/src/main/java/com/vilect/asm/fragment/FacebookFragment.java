package com.vilect.asm.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.vilect.asm.R;

import org.json.JSONObject;

import java.lang.reflect.Array;

/**
 * A simple {@link Fragment} subclass.
 */
public class FacebookFragment extends Fragment {

    private LoginButton btnLoginFacebook;
    private Context context;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private LoginManager loginManager = LoginManager.getInstance();


    public FacebookFragment(Context context) {
        this.context = context;
        this.callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //mỗi khi mở app thì logout account đã login
        loginManager.logOut();


        View view = inflater.inflate(R.layout.fragment_facebook, container, false);
        btnLoginFacebook = view.findViewById(R.id.btnLoginFacebook);

        btnLoginFacebook.setFragment(this);
        String[] permission = {"profile-id", "email"};
        btnLoginFacebook.setPermissions(permission);
        //register  callback
        regCallback();







        return view;
    }

    private void regCallback()
    {
        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                //khi đăng nhập thành công thì hàm này sẽ được gọi
                Toast.makeText(context, "Logged in succesfully!", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancel() {

                //sau khi đăng nhập thành công thì hàm này sẽ được gọi

                //GrapgRequest để lấy về một "file" dạng JSON chứa các thông tin cần có
                GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("JSONfile", object.toString());
                    }
                });

            }

            @Override
            public void onError(FacebookException error) {

                //khi đăng nhập thất bại thì hàm này sẽ được gọi

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        accessToken = AccessToken.getCurrentAccessToken();
    }


}
