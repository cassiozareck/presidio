/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import com.mycompany.sistema_carcerario.model.SaudeMulher;
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
public class SaudeMulherDao {
    
    public boolean insertSaudeMulher(SaudeMulher saudeMulher) {
        String sql = "INSERT INTO saude_mulher (id_prisioneiro, gestacao, idade_gestacional, qual_contraceptivo, " +
                    "exame_preventivo_papanicolau, exame_preventivo_papanicolau_ano, ofertar_continuidade_contraceptivo, " +
                    "ofertar_consulta_preventivo, encaminhar_pre_natal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, saudeMulher.getIdPrisioneiro());
            ps.setInt(2, saudeMulher.getGestacao());
            ps.setObject(3, saudeMulher.getIdadeGestacional());
            ps.setString(4, saudeMulher.getQualContraceptivo());
            ps.setBoolean(5, saudeMulher.isExamePreventivoPapanicolau());
            ps.setObject(6, saudeMulher.getExamePreventivoPapanicolauAno());
            ps.setBoolean(7, saudeMulher.isOfertarContinuidadeContraceptivo());
            ps.setBoolean(8, saudeMulher.isOfertarConsultaPreventivo());
            ps.setBoolean(9, saudeMulher.isEncaminharPreNatal());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao inserir dados de saúde da mulher");
            return false;
        }
    }
    
    public boolean updateSaudeMulher(SaudeMulher saudeMulher) {
        String sql = "UPDATE saude_mulher SET gestacao = ?, idade_gestacional = ?, qual_contraceptivo = ?, " +
                    "exame_preventivo_papanicolau = ?, exame_preventivo_papanicolau_ano = ?, " +
                    "ofertar_continuidade_contraceptivo = ?, ofertar_consulta_preventivo = ?, " +
                    "encaminhar_pre_natal = ? WHERE id = ?";
        
        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, saudeMulher.getGestacao());
            ps.setObject(2, saudeMulher.getIdadeGestacional());
            ps.setString(3, saudeMulher.getQualContraceptivo());
            ps.setBoolean(4, saudeMulher.isExamePreventivoPapanicolau());
            ps.setObject(5, saudeMulher.getExamePreventivoPapanicolauAno());
            ps.setBoolean(6, saudeMulher.isOfertarContinuidadeContraceptivo());
            ps.setBoolean(7, saudeMulher.isOfertarConsultaPreventivo());
            ps.setBoolean(8, saudeMulher.isEncaminharPreNatal());
            ps.setInt(9, saudeMulher.getId());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar dados de saúde da mulher");
            return false;
        }
    }
    
    public SaudeMulher buscarSaudeMulherPorPrisioneiro(int idPrisioneiro) {
        String sql = "SELECT * FROM saude_mulher WHERE id_prisioneiro = ?";
        
        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, idPrisioneiro);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SaudeMulher sm = new SaudeMulher();
                    sm.setId(rs.getInt("id"));
                    sm.setIdPrisioneiro(rs.getInt("id_prisioneiro"));
                    sm.setGestacao(rs.getInt("gestacao"));
                    sm.setIdadeGestacional(rs.getObject("idade_gestacional", Integer.class));
                    sm.setQualContraceptivo(rs.getString("qual_contraceptivo"));
                    sm.setExamePreventivoPapanicolau(rs.getBoolean("exame_preventivo_papanicolau"));
                    sm.setExamePreventivoPapanicolauAno(rs.getObject("exame_preventivo_papanicolau_ano", Integer.class));
                    sm.setOfertarContinuidadeContraceptivo(rs.getBoolean("ofertar_continuidade_contraceptivo"));
                    sm.setOfertarConsultaPreventivo(rs.getBoolean("ofertar_consulta_preventivo"));
                    sm.setEncaminharPreNatal(rs.getBoolean("encaminhar_pre_natal"));
                    
                    return sm;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao buscar dados de saúde da mulher");
        }
        
        return null;
    }
    
    public SaudeMulher buscarSaudeMulherPorId(int id) {
        String sql = "SELECT * FROM saude_mulher WHERE id = ?";
        
        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SaudeMulher sm = new SaudeMulher();
                    sm.setId(rs.getInt("id"));
                    sm.setIdPrisioneiro(rs.getInt("id_prisioneiro"));
                    sm.setGestacao(rs.getInt("gestacao"));
                    sm.setIdadeGestacional(rs.getObject("idade_gestacional", Integer.class));
                    sm.setQualContraceptivo(rs.getString("qual_contraceptivo"));
                    sm.setExamePreventivoPapanicolau(rs.getBoolean("exame_preventivo_papanicolau"));
                    sm.setExamePreventivoPapanicolauAno(rs.getObject("exame_preventivo_papanicolau_ano", Integer.class));
                    sm.setOfertarContinuidadeContraceptivo(rs.getBoolean("ofertar_continuidade_contraceptivo"));
                    sm.setOfertarConsultaPreventivo(rs.getBoolean("ofertar_consulta_preventivo"));
                    sm.setEncaminharPreNatal(rs.getBoolean("encaminhar_pre_natal"));
                    
                    return sm;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao buscar dados de saúde da mulher por ID");
        }
        
        return null;
    }
    
    public boolean deleteSaudeMulher(int id) {
        String sql = "DELETE FROM saude_mulher WHERE id = ?";
        
        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao deletar dados de saúde da mulher");
            return false;
        }
    }
} 