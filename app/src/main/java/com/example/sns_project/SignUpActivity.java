package com.example.sns_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signupbotton).setOnClickListener(onClickListener);
        findViewById(R.id.gotologinbotton).setOnClickListener(onClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }

    }

    View.OnClickListener onClickListener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.signupbotton:
                    signup();
                    break;

                case R.id.gotologinbotton:
                    startloginactivity();
                    break;
            }
        }
    };
    private void signup(){
        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
        String passwordcheck = ((EditText)findViewById(R.id.passwordcheckEditText)).getText().toString();

        if(email.length() > 0 && password.length() >0 && passwordcheck.length() > 0){
            if (password.equals(passwordcheck)){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    starttoast("??????????????? ?????????????????????.");
                                } else {
                                    if (task.getException() != null) {
                                        starttoast(task.getException().toString());

                                    }
                                }
                            }
                        });

            }else{
                starttoast("??????????????? ???????????? ????????????.");
            }
        }else{
            starttoast("????????? ?????? ??????????????? ??????????????????");
        }
    }
    private void starttoast(String msg){
        Toast.makeText(this, msg , Toast.LENGTH_SHORT).show();
    }

    private  void  startloginactivity(){
        Intent intent=new Intent(this,loginActivity.class);
        startActivity(intent);
    }
}