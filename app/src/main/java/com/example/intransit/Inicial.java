package com.example.intransit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Inicial extends AppCompatActivity {
    private Button Paralogin;
    private Button Paracadastro;

    private static final String DB_URL = "jdbc:mysql://seu_servidor_mysql:8080/Intransit";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "wrs.exe@config23";

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicial);

            Paralogin = findViewById(R.id.Paralogin);
            Paracadastro = findViewById(R.id.Paracadastro);

            Paralogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Inicial.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            });
            Paracadastro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Inicial.this, Cadastro.class);
                    startActivity(intent);
                    finish();
                }
            });
        }


    private boolean realizarAutenticacao() {
        // Conectar ao banco de dados
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Executar a lógica de autenticação no banco de dados
            // ...

            // Retornar true se a autenticação for bem-sucedida, caso contrário, retorne false
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fechar a conexão com o banco de dados
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    private boolean realizarCadastro() {
        // Conectar ao banco de dados
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Executar a lógica de cadastro no banco de dados
            // ...

            // Retornar true se o cadastro for bem-sucedido, caso contrário, retorne false
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fechar a conexão com o banco de dados
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }
}