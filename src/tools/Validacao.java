package tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validacao {
    
    public static boolean validarEmail(String email) {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }
    
    public static boolean validarData(String data){
        try {
            //SimpleDateFormat é usada para trabalhar com formatação de datas
            //neste caso o formatador irá utilizar o formato "dd/MM/yyyy"
            //dd = dia, MM = mes, yyyy = ano
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            //a mágica desse método acontece aqui, pois o setLenient() é usado para setar
            //sua escolha sobre datas estranhas, quando setamos para "false" estamos dizendo
            //que não aceitamos datas falsas como 31/02/2020
            
            sdf.setLenient(false);
            
            //aqui tentamos converter a String em um objeto do tipo date, se funcionar
            //sua data é valida
            sdf.parse(data);
            //se nada deu errado retorna true (verdadeiro)
            return true;
            
        } catch (ParseException ex) {
            //se algum passo dentro do "try" der errado quer dizer que sua data é falsa, então,
            //retorna falso
            return false;
        }
    }
    
}
