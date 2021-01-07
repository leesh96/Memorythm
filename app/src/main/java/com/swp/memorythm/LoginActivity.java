package com.swp.memorythm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = null;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signInButton = findViewById(R.id.login_btn);
        mAuth = FirebaseAuth.getInstance();

        //로그인 돼 있으면 메인으로 넘어감
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(getApplication(), MainActivity.class);
            intent.putExtra("uid", mAuth.getCurrentUser().getUid());
            intent.putExtra("name", mAuth.getCurrentUser().getDisplayName());
            intent.putExtra("profile", mAuth.getCurrentUser().getPhotoUrl().toString());
            intent.putExtra("email", mAuth.getCurrentUser().getEmail());
            startActivity(intent);
            finish();
        }

        //로그인 구성
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //로그인 버튼 눌렸을 때
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    //로그인하는 함수?
    private void signIn() {
        mGoogleSignInClient.signOut();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
            }
        }
    }

    //파이어베이스 인증
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            updateUI(null);
                        }
                    }
                });
    }

    //로그인 성공시 메인으로 넘어감
    private void updateUI(FirebaseUser user) { //update ui code here
        if (user != null) {

            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReference();

            try {
                File data = Environment.getDataDirectory(); // 경로(data/)

                File currentDB = new File(data, "/data/com.swp.memorythm/databases/memorythm.db");
                Uri uri = Uri.fromFile(currentDB); //파일로부터 uri 생성?
                StorageReference mRef = storageReference.child("backup/" + user.getUid()); //스토리지 참조

                mRef.getFile(uri).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        // 업로드 실패알림
                        Log.d("오류", e.getLocalizedMessage());

                        if(Objects.equals(e.getLocalizedMessage(), "Object does not exist at location.")) {

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("uid", user.getUid());
                            intent.putExtra("name", user.getDisplayName());
                            intent.putExtra("profile", user.getPhotoUrl().toString());
                            intent.putExtra("email", user.getEmail());
                            startActivity(intent);
                            finish();
                        }
                    }
                }).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        Log.d("reuslt", "복원 성공");

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("uid", user.getUid());
                        intent.putExtra("name", user.getDisplayName());
                        intent.putExtra("profile", user.getPhotoUrl().toString());
                        intent.putExtra("email", user.getEmail());
                        startActivity(intent);
                        finish();
                    }
                });
            } catch (Exception e) {
                Log.d("reuslt", "파일 다운로드 실패");
            }
        }
    }
}