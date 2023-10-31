package com.example.appdeliveryjava;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class tela_principal extends AppCompatActivity {
    private TextView nomeUsuario, emailUsuario;
    private Button bt_deslogar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
        IniciarComponentes();
        bt_deslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(tela_principal.this, FormLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }

   protected void onStart(){

        super.onStart();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

       DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
       documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
           @Override
           public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
               if (documentSnapshot != null){
                   nomeUsuario.setText(documentSnapshot.getString("nome"));
                   emailUsuario.setText(email);
               }
           }
       });
   }

private void IniciarComponentes() {
        nomeUsuario = findViewById(R.id.nomeUsuario);
        emailUsuario = findViewById(R.id.emailUsuario);
        bt_deslogar = findViewById(R.id.bt_deslogar);
}

    }

