package controllers;

import connection.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.Candidato;

public class UsuarioController {

    public boolean validarLogin(String login, String senha) {
        try {
            Conexao.abreConexao();
            ResultSet rs = null;

            String SQL = "";
            SQL = " SELECT * ";
            SQL += " FROM usuarios ";
            SQL += " WHERE login = '" + login + "'";
            SQL += " AND senha = md5('" + senha + "')";

            try {
                System.out.println("Vai Executar Conexão em buscar");
                rs = Conexao.stmt.executeQuery(SQL);
                System.out.println("Executou Conexão em buscar");

                if (rs.next() == true) {
                    return true;
                }
            } catch (SQLException ex) {
                System.out.println("ERRO de SQL: " + ex.getMessage().toString());
                return false;
            }

        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage().toString());
            return false;
        }

        return false;
    }

}
