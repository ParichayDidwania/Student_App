package android.com.jeevika;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;

public class SignUpPage extends AppCompatActivity {
    private Button mSignUpBtn;
    TextView name;
    TextView emailId;
    TextView passwd;
    FirebaseAuth firebaseAuth;
    HashMap<String,String> hashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        hashMap = new HashMap<>();
        name = (EditText)findViewById(R.id.signup_fullname);
        emailId = (EditText)findViewById(R.id.signup_email);
        passwd = (EditText)findViewById(R.id.signup_password);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void startHomeIntent() {
        Intent home_intent = new Intent(SignUpPage.this,HomeActivity.class);
        startActivity(home_intent);
    }

    public void signup(View v)
    {

        String emailID = emailId.getText().toString();
        String paswd = passwd.getText().toString();
        String name1 = name.getText().toString();

        hashMap.put("Name",name1);
        hashMap.put("Email",emailID);

        if (emailID.isEmpty()) {
            emailId.setError("Provide your Email first!");
            emailId.requestFocus();
        } else if (paswd.isEmpty()) {
            passwd.setError("Set your password");
            passwd.requestFocus();
        }
        else if(name1.isEmpty())
        {
            name.setError("Enter your name");
            name.requestFocus();
        }
        else if (emailID.isEmpty() && paswd.isEmpty() && name1.isEmpty()) {
            Toast.makeText(SignUpPage.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
        } else if (!(emailID.isEmpty() && paswd.isEmpty() && name1.isEmpty())) {
            firebaseAuth.createUserWithEmailAndPassword(emailID, paswd).addOnCompleteListener(SignUpPage.this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(SignUpPage.this.getApplicationContext(),
                                "Signup unsuccessful: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        FirebaseUser user = firebaseAuth.getCurrentUser();

                        FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).setValue(hashMap);

                        Toast.makeText(SignUpPage.this.getApplicationContext(),
                                "Signup successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpPage.this, HomeActivity.class));
                    }
                }
            });
        } else {
            Toast.makeText(SignUpPage.this, "User already exits", Toast.LENGTH_SHORT).show();
        }
    }
}
