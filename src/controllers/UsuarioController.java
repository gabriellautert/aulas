package controllers;

import connection.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.Candidato;
import models.Usuario;

public class UsuarioController {

    public Usuario validarLogin(String login, String senha) {
        try {
            Conexao.abreConexao();
            ResultSet rs = null;

            String SQL = "";
            SQL = " SELECT * ";
            SQL += " FROM usuarios ";
            SQL += " WHERE login = '" + login + "'";
            SQL += " AND senha = md5('" + senha + "')";

            Usuario objUsuario;
            try {

                System.out.println("Vai Executar Conexão em buscar");
                rs = Conexao.stmt.executeQuery(SQL);
                System.out.println("Executou Conexão em buscar");

                if (rs.next() == true) {
                    objUsuario = new Usuario();

                    objUsuario.setId(rs.getInt("id"));
                    objUsuario.setLogin(rs.getString("login"));
                    objUsuario.setNome(rs.getString("nome"));

                    return objUsuario;

                }
            } catch (SQLException ex) {
                System.out.println("ERRO de SQL: " + ex.getMessage().toString());
                return null;
            }

        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage().toString());
            return null;
        }

        return null;
    }

}
