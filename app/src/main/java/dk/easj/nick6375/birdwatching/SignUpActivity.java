package dk.easj.nick6375.birdwatching;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {


    ProgressBar progressBar;

    EditText editTextEmail, editTextPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

      editTextEmail = (EditText) findViewById(R.id.SignUpEmail);
      editTextPassword = (EditText) findViewById(R.id.SignUpPassword);
      progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.Back).setOnClickListener(this);
        findViewById(R.id.SignUp).setOnClickListener(this);



    }

    private void registerUser()
    {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty())
        {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }


        if (password.isEmpty())
        {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful())
                {

                    Toast.makeText(getApplicationContext(), "User Registered Succesfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SignUp:
                registerUser();

                break;

            case R.id.Back:
                startActivity(new Intent(this, MainActivity.class));

                break;
        }
    }
}
