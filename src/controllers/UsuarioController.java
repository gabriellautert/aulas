package controllers;

import connection.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public Usuario buscarLoginRecente() {
        Usuario objUsuario;
        try {

            Conexao.abreConexao();
            ResultSet rs = null;

            long tempo = System.currentTimeMillis() + (5 * 60 * 1000); //5 = minutos

            String wSql = "";
            wSql = " SELECT * ";
            wSql += " FROM usuarios ";
            wSql += " WHERE (ultimo_login + (5 * 60 * 1000)) <= " + tempo;
            wSql += " ORDER BY ultimo_login DESC LIMIT 1 ";

            try {
                System.out.println("Vai Executar Conexão em buscar");
                rs = Conexao.stmt.executeQuery(wSql);
                System.out.println("Executou Conexão em buscar");

                if (rs.next() == true) {
                    objUsuario = new Usuario();
                    objUsuario.setId(rs.getInt("id"));
                    objUsuario.setLogin(rs.getString("login"));
                    objUsuario.setNome(rs.getString("nome"));

                    atualizarUltimoLogin(objUsuario.getId());

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

    public boolean atualizarUltimoLogin(int id) {

        Conexao.abreConexao();
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        long tempo = System.currentTimeMillis();

        try {
            stmt = con.prepareStatement("UPDATE usuarios SET ultimo_login=? WHERE id=?");
            stmt.setLong(1, tempo);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            Conexao.closeConnection(con, stmt);
            return true;
        }

    }

}
