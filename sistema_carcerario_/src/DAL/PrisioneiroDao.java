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
    
    
    public List<Prisioneiro> listarPrisioneiros(String filter) {
    List<Prisioneiro> prisioneiros = new ArrayList<>();
    String sql = "SELECT * FROM prisioneiro WHERE nome LIKE ?";

    try (Connection conexao = ConexaoBanco.conectar();
         PreparedStatement ps = conexao.prepareStatement(sql)) {

        
        // Vai fazer um LIke para pegar todos que contenham pelo menos um pedaço
        // do que foi passado como filtro
        ps.setString(1, "%" + filter + "%"); 

        try (ResultSet rs = ps.executeQuery()) {
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
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Erro ao listar prisioneiros com filtro");
    }

    return prisioneiros;
}

public Prisioneiro buscarPrisioneiroPorId(int id) {
    String sql = "SELECT * FROM prisioneiro WHERE id = ?";
    
    try (Connection conexao = ConexaoBanco.conectar();
         PreparedStatement ps = conexao.prepareStatement(sql)) {
        
        ps.setInt(1, id);
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
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
                
                return p;
            }
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Erro ao buscar prisioneiro por ID");
    }
    
    return null;
}

public boolean updatePrisioneiro(Prisioneiro prisioneiro) {
    String sql = "UPDATE prisioneiro SET nome = ?, nome_mae = ?, data_nascimento = ?, cpf = ?, " +
                 "orientacao = ?, genero = ?, sexo = ?, raca = ?, nacionalidade = ?, " +
                 "estado_civil = ?, escolaridade = ? WHERE id = ?";
    
    try (Connection conexao = ConexaoBanco.conectar();
         PreparedStatement ps = conexao.prepareStatement(sql)) {
        
        ps.setString(1, prisioneiro.getNome());
        ps.setString(2, prisioneiro.getNomeMae());
        ps.setDate(3, java.sql.Date.valueOf(prisioneiro.getDataNascimento()));
        ps.setString(4, prisioneiro.getCpf());
        ps.setString(5, prisioneiro.getOrientacao());
        ps.setString(6, prisioneiro.getGenero());
        ps.setString(7, prisioneiro.getSexo());
        ps.setString(8, prisioneiro.getRaca());
        ps.setString(9, prisioneiro.getNacionalidade());
        ps.setString(10, prisioneiro.getEstadoCivil());
        ps.setString(11, prisioneiro.getEscolaridade());
        ps.setInt(12, prisioneiro.getId());
        
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;
        
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Erro ao atualizar prisioneiro");
        return false;
    }
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
