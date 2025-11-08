package com.example.journalapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    EditText username_create, password_create, email_create;
    Button createBTN;

    //FirebaseAuth
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private FirebaseUser currentUser;

    //Firebase connection
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference collectionRef = db.collection("Users");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        createBTN = findViewById(R.id.acc_signUp_btn);
        username_create = findViewById(R.id.username_create_ET);
        password_create = findViewById(R.id.password_create);
        email_create = findViewById(R.id.email_create);

        //Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        //Listening for the changes in the authentication
        //State and responds accordingly when the state changes
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();

                //Check if the user logged in or not
                if(currentUser != null){
                    //User already logged in
                }else{
                    //The user signed out
                }

            }
        };

        createBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(username_create.getText().toString()) && !TextUtils.isEmpty(password_create.getText().toString()) && !TextUtils.isEmpty(email_create.getText().toString())) {
                    String email = email_create.getText().toString().trim();
                    String pass = password_create.getText().toString().trim();
                    String username = username_create.getText().toString().trim();

                    createUserEmailAccount(email, pass, username);
                }
                else{
                    Toast.makeText(SignUpActivity.this, "No Empty fields are allowed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void createUserEmailAccount(String email, String pass, String username){

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(username)){
            firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //User has created account successfully
                                Toast.makeText(SignUpActivity.this, "Account is created successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }
}