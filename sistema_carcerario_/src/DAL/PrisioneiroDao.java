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

        String sql = "SELECT * FROM prisioneiro";

        try (PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Prisioneiro p = new Prisioneiro();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setNomeMae(rs.getString("nome_mae"));
                p.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                p.setCpf(rs.getString("cpf"));
                p.setOrientacao(rs.getString("orientacao"));
                p.setGenero(rs.getString("genero"));
                p.setSexo(rs.getString("sexo"));
                p.setRaca(rs.getString("raca"));
                p.setNacionalidade(rs.getString("nacionalidade"));
                p.setEstadoCivil(rs.getString("estado_civil"));
                p.setEscolaridade(rs.getString("escolaridade"));

                prisioneiros.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace(); 
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
            ps.setString(4, prisioneiro.getOrientacao());
            ps.setString(5, prisioneiro.getGenero());
            ps.setString(6, prisioneiro.getSexo());
            ps.setString(7, prisioneiro.getRaca());
            ps.setString(8, prisioneiro.getNacionalidade());
            ps.setString(9, prisioneiro.getEstadoCivil());
            ps.setString(10, prisioneiro.getEscolaridade());
            ps.executeUpdate();
     
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
}
