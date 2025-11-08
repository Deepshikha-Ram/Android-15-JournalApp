package com.example.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button loginBTN, createAccountBTN;

    private EditText emailET, passET;

    //Firebase Authentication
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        createAccountBTN = findViewById(R.id.create_account);


        createAccountBTN.setOnClickListener(v -> {
            /// OnClick()
            Intent i = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(i);

        });

        loginBTN = findViewById(R.id.email_signin);
        emailET = findViewById(R.id.email);
        passET = findViewById(R.id.password);

        firebaseAuth = FirebaseAuth.getInstance();

        loginBTN.setOnClickListener(v -> {
            loginEmailPassUser(emailET.getText().toString().trim(), passET.getText().toString().trim());

        });

    }

    private void loginEmailPassUser(String email, String pwd) {
        //Checking for empty texts
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd)){
            firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Intent i = new Intent(MainActivity.this, JournalListActivity.class);
                    startActivity(i);
                }
            });
        }
    }
}