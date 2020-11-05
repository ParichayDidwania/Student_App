package android.com.jeevika;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {


    EditText emailId;
    EditText passwd;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        emailId = findViewById(R.id.login_email);
        passwd = findViewById(R.id.login_password);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void startHomeIntent() {
        Intent start_intent = new Intent(LoginPage.this,HomeActivity.class);
        startActivity(start_intent);
    }

    public void login(View v)
    {
        String emailID = emailId.getText().toString();
        String paswd = passwd.getText().toString();
        if (emailID.isEmpty()) {
            emailId.setError("Provide your Email first!");
            emailId.requestFocus();
        } else if (paswd.isEmpty()) {
            passwd.setError("Set your password");
            passwd.requestFocus();
        } else if (emailID.isEmpty() && paswd.isEmpty()) {
            Toast.makeText(LoginPage.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
        } else if (!(emailID.isEmpty() && paswd.isEmpty())) {
            firebaseAuth.signInWithEmailAndPassword(emailID, paswd).addOnCompleteListener(LoginPage.this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginPage.this.getApplicationContext(),
                                "Login unsuccessful: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginPage.this.getApplicationContext(),
                                "Login successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginPage.this, HomeActivity.class));
                    }
                }
            });
        } else {
            Toast.makeText(LoginPage.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

}