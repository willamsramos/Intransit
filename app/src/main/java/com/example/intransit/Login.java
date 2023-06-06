package com.example.intransit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private EditText insert_usuario;
    private EditText insert_senha;
    private Button botao_de_login;
    private ProgressBar barra_de_carregamento;
    private CheckBox mostrar_senha;
    private Button botao_de_voltar;

    private static final String DB_URL = "jdbc:mysql://seu_servidor_mysql:8080/intr";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "wrs.exe@config23";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        insert_usuario = findViewById(R.id.insert_usuario);
        insert_senha = findViewById(R.id.insert_senha);
        botao_de_login = findViewById(R.id.botao_de_login);
        barra_de_carregamento = findViewById(R.id.barra_de_carregamento);
        mostrar_senha = findViewById(R.id.mostrar_senha);
        botao_de_voltar = findViewById(R.id.botao_de_voltar);

        botao_de_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matricula = insert_usuario.getText().toString();
                String senha = insert_senha.getText().toString();

                if (!TextUtils.isEmpty(matricula) && !TextUtils.isEmpty(senha)) {
                    barra_de_carregamento.setVisibility(View.VISIBLE);

                    // Verificar as credenciais no banco de dados
                    if (verificarCredenciais(matricula, senha)) {
                        abrirTelaPrincipal();
                    } else {
                        Toast.makeText(Login.this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
                        barra_de_carregamento.setVisibility(View.INVISIBLE);
                    }
                } else {
                    Toast.makeText(Login.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mostrar_senha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    insert_senha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    insert_senha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        botao_de_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Inicial.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean verificarCredenciais(String usuario, String senha) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Estabelecer a conexão com o banco de dados MySQL
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Preparar a consulta SQL
            String query = "SELECT * FROM tabela_usuarios WHERE usuario = ? AND senha = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, usuario);
            statement.setString(2, senha);

            // Executar a consulta
            resultSet = statement.executeQuery();

            // Verificar se há um resultado retornado
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fechar os recursos
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(Login.this, Opcoes.class);
        startActivity(intent);
        finish();
    }
}