package DAL;

import com.mycompany.sistema_carcerario.model.Atendimento;
import com.mycompany.sistema_carcerario.model.Prisioneiro;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author m138824
 */
public class UtilDAO {
    
    public static void InserirAtendimento(Prisioneiro prisioneiro, Atendimento atendimento){
        try{
            Connection conexao = ConexaoBanco.conectar();
            String sql = "INSERT INTO prisioneiro (data_nascimento, nome, cpf, id_orientacao, id_genero, id_sexo, "
                    + "id_raça, id_nacionalidade, id_estado_civil, id_escolaridade)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, prisioneiro.getNome());
            ps.setDate(2, java.sql.Date.valueOf(prisioneiro.getDataNascimento()));
            ps.setString(3, prisioneiro.getCpf());
            ps.setString(4, prisioneiro.getOrientacao());
            ps.setString(5, prisioneiro.getGenero());
            ps.setString(6, prisioneiro.getSexo());
            ps.setString(7, prisioneiro.getRaca());
            ps.setString(8, prisioneiro.getNacionalidade());
            ps.setString(9, prisioneiro.getEstadoCivil());
            ps.setString(10, prisioneiro.getEscolaridade());
            ps.executeUpdate();
     
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
