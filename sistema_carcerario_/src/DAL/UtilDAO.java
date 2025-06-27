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
            ps.setInt(4, prisioneiro.getIdOrientacao());
            ps.setInt(5, prisioneiro.getIdGenero());
            ps.setInt(6, prisioneiro.getIdSexo());
            ps.setInt(7, prisioneiro.getIdRaca());
            ps.setInt(8, prisioneiro.getIdNacionalidade());
            ps.setInt(9, prisioneiro.getIdEstadoCivil());
            ps.setInt(10, prisioneiro.getIdEscolaridade());
            ps.executeUpdate();
     
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
