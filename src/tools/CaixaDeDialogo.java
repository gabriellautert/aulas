/*
 * Dialog.java
 *
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package tools;

import javax.swing.JOptionPane;

/**
 *
 * @author Mouriac
 */
public class CaixaDeDialogo {

    final public static char ERRO = 'e';
    final public static char INFO = 'i';
    final public static char ALERTA = 'a';
    final public static char PERGUNTA = 'p';

    private static CaixaDeDialogo instancia = new CaixaDeDialogo();

    private CaixaDeDialogo() {
        // construtor vazio e privado
    }

    public static CaixaDeDialogo obterinstancia() {
        return (instancia);
    }

    public void exibirMensagem(String frase) {
        exibirMensagem(frase, "Mensagem", 'i');
    }

    public void exibirMensagem(String frase, char tipo) {
        exibirMensagem(frase, "Mensagem", tipo);
    }

    public void exibirMensagem(String frase, String boxFrase, char tipo) {
        /* Erro 'e'
         * Informativa 'i'
         * Advert�ncia 'a'
         * Pergunta 'p'
         */
        //JOptionPane.showMessageDialog(null,"Est� conectado","Mensagem",JOptionPane.WARNING_MESSAGE);
        String iconTypes = "eiap";
        JOptionPane.showMessageDialog(null, frase, boxFrase, iconTypes.indexOf(tipo));
    }

//    public boolean pedirConfirmacao( String frase ) {
//        return( pedirConfirmacao( frase, "Confirmacao", 'p' ));
//    }
//
//    public boolean pedirConfirmacao( String frase, char tipo ) {
//        return( pedirConfirmacao( frase, "Confirmacao", tipo ));
//    }
    public boolean pedirConfirmacao(String frase, String boxFrase, char tipo) {
        String[] opcoes = {"Sim", "Não"};
        int opcaoPadrao = 0;
        String iconTypes = "eiap";
        int escolha = JOptionPane.showOptionDialog(null, frase, boxFrase, JOptionPane.YES_NO_OPTION, iconTypes.indexOf(tipo), null, opcoes, opcoes[opcaoPadrao]);
        return (escolha == 0);
    }

    public void exibirMensagem(String erro_ao_alterarincluir, String erro, String e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
