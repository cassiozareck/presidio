
package DAL;

import java.sql.Connection;
import java.sql.DriverManager;

//classe de metodos estáticos
public final class ConexaoBanco {
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://10.17.9.29/" + "sistema_carcerario"; //usamos banco compartilhado em rede lan
    private static final String usuario = "gabe";
    private static final String senha = "123123";//aqui no lab, "" (VAZIO)
    private Connection conexao;
    
    //esta classe retorna um Connection, tipo da conexao com o banco de dados, vem de java.sql
    public static Connection conectar(){
        try {
            Class.forName(driver);//carregar o driver para o Java
            return DriverManager.getConnection(url,usuario,senha);//abrir a conexão
            
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }    
    }
}
