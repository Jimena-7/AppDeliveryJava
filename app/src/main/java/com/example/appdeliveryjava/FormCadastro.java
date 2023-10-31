package com.example.appdeliveryjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormCadastro extends AppCompatActivity {

    private EditText edit_nome, edit_email,edit_telefone, edit_endereco, edit_senhaCadastro;
    private Button bt_cadastrar;
    String[] mensagens = {"Preencha todos os campos", "Cadastro realizado com sucesso"};
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);
        IniciarComponentes();

        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = edit_nome.getText().toString();
                String email = edit_email.getText().toString();
                String telefone = edit_telefone.getText().toString();
                String endereco = edit_endereco.getText().toString();
                String senhaCadastro = edit_senhaCadastro.getText().toString();

                if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || endereco.isEmpty() || senhaCadastro.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(v, mensagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else {
                    CadastrarUsuario(v);
                }
            }
        });
            }

        private void CadastrarUsuario(View v){
            String email = edit_email.getText().toString();
            String senhaCadastro = edit_senhaCadastro.getText().toString();

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senhaCadastro).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){

                        SalvarDadosUsuario();
                        Snackbar snackbar = Snackbar.make(v, mensagens[1], Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();

                    }

                }
            });

        }

        private void SalvarDadosUsuario(){
            String nome = edit_nome.getText().toString();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> usuarios = new HashMap<>();
            usuarios.put("nome", nome);

            usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
            documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("db","Sucessp ao salvar os dados");

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("db_error","Erro ao salvar os dados" + e.toString());

                        }
                    });
    }
        private  void IniciarComponentes(){
        edit_nome = findViewById(R.id.edit_nome);
        edit_email = findViewById(R.id.edit_email);
        edit_telefone = findViewById(R.id.edit_telefone);
        edit_endereco = findViewById(R.id.edit_endereco);
        edit_senhaCadastro = findViewById(R.id.edit_senhaCadastro);
        bt_cadastrar = findViewById(R.id.bt_cadastrar);
            }
        }



