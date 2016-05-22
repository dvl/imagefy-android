package com.vanhackathon.imagefy;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.vanhackathon.imagefy.service.data.auth.FacebookLoginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {

    private CallbackManager callbackManager;
    private LoginButton loginButton;

    public LoginActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginButton.setVisibility(View.GONE);

                // App code
                String token = loginResult.getAccessToken().getToken();
                String code = loginResult.getAccessToken().getUserId();

                FacebookLoginData facebook = new FacebookLoginData();
                facebook.access_token = token;
                facebook.code = code;
                Call<String> call = AuthService.getInstance().imagefyAuthApi.facebook(facebook);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String loginToken = response.body();
                        login(loginToken);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        loginButton.setVisibility(View.VISIBLE);
                        LoginManager.getInstance().logOut();
                    }
                });

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    private void login(String login) {
        LocalLoginManager.logeIn(getContext(), login);

        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);

        if(getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
