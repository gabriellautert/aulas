package controllers;

import connection.Conexao;
import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import models.Candidato;

/**
 *
 * @author Jonas Dhein
 */
public class CandidatoController {

    //Candidato objCandidato;
    //JTable jtbCandidatos = null;
    /*public CandidatoController(Candidato objCandidato, JTable jtbCandidatos) {
        this.objCandidato = objCandidato;
        this.jtbCandidatos = jtbCandidatos;
    }*/
    public boolean incluir(Candidato objeto) {

        Conexao.abreConexao();
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO candidatos(nome, id_bairro, dt_nascimento, email, telefone) VALUES(?,?,?,?,?)");
            stmt.setString(1, objeto.getNome());
            stmt.setInt(2, objeto.getId_bairro());
            Date data_nasc = Date.valueOf(objeto.getDt_nascimento());
            stmt.setDate(3, data_nasc);
            stmt.setString(4, objeto.getEmail());
            stmt.setString(5, objeto.getTelefone());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            Conexao.closeConnection(con, stmt);
            return true;
        }
    }

    public boolean alterar(Candidato objeto) {

        Conexao.abreConexao();
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE candidatos SET nome=?, id_bairro=?, dt_nascimento=?, email=?, telefone=? WHERE id=?");
            stmt.setString(1, objeto.getNome());
            stmt.setInt(2, objeto.getId_bairro());
            Date data_nasc = Date.valueOf(objeto.getDt_nascimento());
            stmt.setDate(3, data_nasc);
            stmt.setString(4, objeto.getEmail());
            stmt.setString(5, objeto.getTelefone());
            stmt.setInt(6, objeto.getId());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            Conexao.closeConnection(con, stmt);
            return true;
        }

    }

    public void preencher(JTable jtbCandidatos) {

        Conexao.abreConexao();

        Vector<String> cabecalhos = new Vector<String>();
        Vector dadosTabela = new Vector(); //receber os dados do banco

        cabecalhos.add("#");
        cabecalhos.add("Nome");
        cabecalhos.add("Nascimento");
        cabecalhos.add("E");

        ResultSet result = null;

        try {

            String SQL = "";
            SQL = " SELECT id, nome, TO_CHAR(dt_nascimento, 'dd/mm/yyyy') as data_formatada ";
            SQL += " FROM candidatos ";
            SQL += " ORDER BY nome ";

            result = Conexao.stmt.executeQuery(SQL);

            Vector<Object> linha;
            while (result.next()) {
                linha = new Vector<Object>();

                linha.add(result.getInt(1));
                linha.add(result.getString(2));
                linha.add(result.getString(3));
                linha.add("X");

                dadosTabela.add(linha);
            }

        } catch (Exception e) {
            System.out.println("problemas para popular tabela...");
            System.out.println(e);
        }

        jtbCandidatos.setModel(new DefaultTableModel(dadosTabela, cabecalhos) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            // permite seleção de apenas uma linha da tabela
        });

        // permite seleção de apenas uma linha da tabela
        jtbCandidatos.setSelectionMode(0);

        // redimensiona as colunas de uma tabela
        TableColumn column = null;
        for (int i = 0; i <= 3; i++) {
            column = jtbCandidatos.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(60);
                    break;
                case 1:
                    column.setPreferredWidth(200);
                    break;
                case 2:
                    column.setPreferredWidth(90);
                    break;
                case 3:
                    column.setPreferredWidth(10);
                    break;
            }
        }

        jtbCandidatos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected,
                        hasFocus, row, column);
                if (row % 2 == 0) {
                    setBackground(Color.LIGHT_GRAY);
                } else {
                    setBackground(Color.WHITE);
                }

                return this;
            }
        });
        //return (true);
    }

    public Candidato buscar(String id) {
        Candidato objCandidato = new Candidato();

        try {
            Conexao.abreConexao();
            ResultSet rs = null;

            String SQL = "";
            SQL = " SELECT id, nome, id_bairro, dt_nascimento, COALESCE(email, '') AS email, COALESCE(telefone, '') AS telefone ";
            SQL += " FROM candidatos ";
            SQL += " WHERE id = '" + id + "'";

            try {
                System.out.println("Vai Executar Conexão em buscar");
                rs = Conexao.stmt.executeQuery(SQL);
                System.out.println("Executou Conexão em buscar");

                if (rs.next() == true) {
                    objCandidato.setId(rs.getInt(1));
                    objCandidato.setNome(rs.getString(2));
                    objCandidato.setId_bairro(rs.getInt(3));
                    objCandidato.setDt_nascimento(rs.getString(4));
                    objCandidato.setEmail(rs.getString(5));
                    objCandidato.setTelefone(rs.getString(6));
                }
            } catch (SQLException ex) {
                System.out.println("ERRO de SQL: " + ex.getMessage().toString());
                return null;
            }

        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage().toString());
            return null;
        }

        System.out.println("Executou buscar area com sucesso");
        return objCandidato;
    }

    /*public boolean excluir(){
        
        Conexao.abreConexao();
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("???");
            stmt.setInt(1, objCandidato.getId());
                        
            stmt.executeUpdate();
            
            return true;
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }*/
}
