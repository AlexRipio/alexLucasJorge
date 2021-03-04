package com.siglas.appname;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class Registration extends AppCompatActivity {
    private EditText ETEmailRegistration;
    private EditText ETPasswordRegistration;
    private EditText ETNameRegistration;
    private Button btnregisterRegistration;

    private String name = "";
    private String email = "";
    private String password = "";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        ETEmailRegistration = (EditText) findViewById(R.id.ETEmailRegistration);
        ETPasswordRegistration = (EditText) findViewById(R.id.ETPasswordRegistration);
        ETNameRegistration = (EditText) findViewById(R.id.ETNameRegistration);
        btnregisterRegistration = (Button) findViewById(R.id.btnregister);


        btnregisterRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = ETNameRegistration.getText().toString();
                email = ETEmailRegistration.getText().toString();
                password = ETPasswordRegistration.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    if (password.length() >= 6){
                        registerUser();
                    }
                    else{
                        Toast.makeText(Registration.this, "la contraseña tiene que tener minimo 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(Registration.this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser() {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("name",name);
                    map.put("email",email);
                    map.put("password",password);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity(new Intent(Registration.this, Login.class));
                                finish();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(Registration.this, "no se ha podido registrar a este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}