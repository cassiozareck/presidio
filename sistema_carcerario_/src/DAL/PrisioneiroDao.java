/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import com.mycompany.sistema_carcerario.model.Atendimento;
import com.mycompany.sistema_carcerario.model.Prisioneiro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author m138824
 */
public class PrisioneiroDao {
    
    
    public List<Prisioneiro> listarPrisioneiros() {
        
        Connection conexao = ConexaoBanco.conectar();
        
        List<Prisioneiro> prisioneiros = new ArrayList<>();

        String sql = "SELECT id, nome, data_nascimento, cpf, orientacao, genero, sexo, " +
                     "raca, nacionalidade, estado_civil, escolaridade FROM prisioneiro";

        try (PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Prisioneiro p = new Prisioneiro();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                p.setCpf(rs.getString("cpf"));
                p.setIdOrientacao(rs.getInt("orientacao"));
                p.setIdGenero(rs.getInt("genero"));
                p.setIdSexo(rs.getInt("sexo"));
                p.setIdRaca(rs.getInt("raca"));
                p.setIdNacionalidade(rs.getInt("nacionalidade"));
                p.setIdEstadoCivil(rs.getInt("estado_civil"));
                p.setIdEscolaridade(rs.getInt("escolaridade"));

                prisioneiros.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Ou jogue uma exceção personalizada
            System.out.println("erro");
        }

        return prisioneiros;
    }
    
    public static void insertPrisioneiro(Prisioneiro prisioneiro, Atendimento atendimento){
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
