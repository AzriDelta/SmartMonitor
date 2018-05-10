package com.example.azri.smartmonitor;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ReceivefromServer extends AppCompatActivity {
    private static final String TAG = null;


    TextView encryptedUsername, encryptedPassword;
    TextView decryptedUsername, decryptedPassword;
    Button getIt, sendIt, decryptIt;
    DatabaseReference myRef,userRef, user3Ref;
    String str_encryptedUsername, str_encryptedPassword;
    String str_decryptedUsername, str_decryptedPassword;
    String str_usernameFetched, str_passwordFetched;

    dataCrypt pushData = new dataCrypt();
    //User user = new User();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivefrom_server);
        encryptedUsername = findViewById(R.id.encryptedUsername);
        encryptedPassword = findViewById(R.id.encryptedPassword);
        decryptedUsername = findViewById(R.id.decryptedUsername);
        decryptedPassword = findViewById(R.id.decryptedPassword);
        getIt = findViewById(R.id.getIt);
        sendIt = findViewById(R.id.sendIt);
        decryptIt = findViewById(R.id.decryptIt);

        //database reference pointing to root of database
        myRef = FirebaseDatabase.getInstance().getReference();

        userRef = myRef.child("user"); //database reference pointing to user node from root
        user3Ref = userRef.child("user3"); //pointing to node user3 under user

        //send It Button
        sendIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_encryptedUsername = pushData.encryptAndPush("delta");
                str_encryptedPassword = pushData.encryptAndPush("delta12345");

                userRef.child("user3").child("username").setValue(str_encryptedUsername);
                userRef.child("user3").child("password").setValue(str_encryptedPassword);

            }
        });

        //get It button
        getIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user3Ref.addValueEventListener(new ValueEventListener() { //for every child nodes (user1, user2) under node user
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        str_usernameFetched = dataSnapshot.child("username").getValue(String.class);
                        str_passwordFetched = dataSnapshot.child("password").getValue(String.class);
                        Log.d(TAG, "Username is: " + str_usernameFetched);
                        Log.d(TAG, "Password is: " + str_passwordFetched);

                        encryptedUsername.setText(str_usernameFetched);
                        encryptedPassword.setText(str_passwordFetched);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
                }
            }
        );

        decryptIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_decryptedUsername = pushData.decryptAndRetrieve(str_usernameFetched);
                str_decryptedPassword = pushData.decryptAndRetrieve(str_passwordFetched);

                decryptedUsername.setText(str_decryptedUsername);
                decryptedPassword.setText(str_decryptedPassword);

            }
        });
    }
}