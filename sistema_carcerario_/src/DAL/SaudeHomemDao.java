/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import com.mycompany.sistema_carcerario.model.SaudeHomem;
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
public class SaudeHomemDao {
    
    public boolean insertSaudeHomem(SaudeHomem saudeHomem) {
        String sql = "INSERT INTO saude_homem (id_prisioneiro, realizou_exame_prostata, ano_exame_prostata, " +
                    "historico_cancer_prostata_familia, qual_familiar_cancer_prostata, realizou_vasectomia, " +
                    "parceira_gestante, participa_pre_natal, ofertar_encaminhamento_vasectomia, " +
                    "ofertar_encaminhamento_pre_natal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, saudeHomem.getIdPrisioneiro());
            ps.setBoolean(2, saudeHomem.isRealizouExameProstata());
            ps.setObject(3, saudeHomem.getAnoExameProstata());
            ps.setBoolean(4, saudeHomem.isHistoricoCancerProstataFamilia());
            ps.setString(5, saudeHomem.getQualFamiliarCancerProstata());
            ps.setBoolean(6, saudeHomem.isRealizouVasectomia());
            ps.setBoolean(7, saudeHomem.isParceiraGestante());
            ps.setBoolean(8, saudeHomem.isParticipaPreNatal());
            ps.setBoolean(9, saudeHomem.isOfertarEncaminhamentoVasectomia());
            ps.setBoolean(10, saudeHomem.isOfertarEncaminhamentoPreNatal());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao inserir dados de saúde do homem");
            return false;
        }
    }
    
    public boolean updateSaudeHomem(SaudeHomem saudeHomem) {
        String sql = "UPDATE saude_homem SET realizou_exame_prostata = ?, ano_exame_prostata = ?, " +
                    "historico_cancer_prostata_familia = ?, qual_familiar_cancer_prostata = ?, " +
                    "realizou_vasectomia = ?, parceira_gestante = ?, participa_pre_natal = ?, " +
                    "ofertar_encaminhamento_vasectomia = ?, ofertar_encaminhamento_pre_natal = ? WHERE id = ?";
        
        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setBoolean(1, saudeHomem.isRealizouExameProstata());
            ps.setObject(2, saudeHomem.getAnoExameProstata());
            ps.setBoolean(3, saudeHomem.isHistoricoCancerProstataFamilia());
            ps.setString(4, saudeHomem.getQualFamiliarCancerProstata());
            ps.setBoolean(5, saudeHomem.isRealizouVasectomia());
            ps.setBoolean(6, saudeHomem.isParceiraGestante());
            ps.setBoolean(7, saudeHomem.isParticipaPreNatal());
            ps.setBoolean(8, saudeHomem.isOfertarEncaminhamentoVasectomia());
            ps.setBoolean(9, saudeHomem.isOfertarEncaminhamentoPreNatal());
            ps.setInt(10, saudeHomem.getId());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar dados de saúde do homem");
            return false;
        }
    }
    
    public SaudeHomem buscarSaudeHomemPorPrisioneiro(int idPrisioneiro) {
        String sql = "SELECT * FROM saude_homem WHERE id_prisioneiro = ?";
        
        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, idPrisioneiro);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SaudeHomem sh = new SaudeHomem();
                    sh.setId(rs.getInt("id"));
                    sh.setIdPrisioneiro(rs.getInt("id_prisioneiro"));
                    sh.setRealizouExameProstata(rs.getBoolean("realizou_exame_prostata"));
                    sh.setAnoExameProstata(rs.getObject("ano_exame_prostata", Integer.class));
                    sh.setHistoricoCancerProstataFamilia(rs.getBoolean("historico_cancer_prostata_familia"));
                    sh.setQualFamiliarCancerProstata(rs.getString("qual_familiar_cancer_prostata"));
                    sh.setRealizouVasectomia(rs.getBoolean("realizou_vasectomia"));
                    sh.setParceiraGestante(rs.getBoolean("parceira_gestante"));
                    sh.setParticipaPreNatal(rs.getBoolean("participa_pre_natal"));
                    sh.setOfertarEncaminhamentoVasectomia(rs.getBoolean("ofertar_encaminhamento_vasectomia"));
                    sh.setOfertarEncaminhamentoPreNatal(rs.getBoolean("ofertar_encaminhamento_pre_natal"));
                    
                    return sh;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao buscar dados de saúde do homem");
        }
        
        return null;
    }
    
    public SaudeHomem buscarSaudeHomemPorId(int id) {
        String sql = "SELECT * FROM saude_homem WHERE id = ?";
        
        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SaudeHomem sh = new SaudeHomem();
                    sh.setId(rs.getInt("id"));
                    sh.setIdPrisioneiro(rs.getInt("id_prisioneiro"));
                    sh.setRealizouExameProstata(rs.getBoolean("realizou_exame_prostata"));
                    sh.setAnoExameProstata(rs.getObject("ano_exame_prostata", Integer.class));
                    sh.setHistoricoCancerProstataFamilia(rs.getBoolean("historico_cancer_prostata_familia"));
                    sh.setQualFamiliarCancerProstata(rs.getString("qual_familiar_cancer_prostata"));
                    sh.setRealizouVasectomia(rs.getBoolean("realizou_vasectomia"));
                    sh.setParceiraGestante(rs.getBoolean("parceira_gestante"));
                    sh.setParticipaPreNatal(rs.getBoolean("participa_pre_natal"));
                    sh.setOfertarEncaminhamentoVasectomia(rs.getBoolean("ofertar_encaminhamento_vasectomia"));
                    sh.setOfertarEncaminhamentoPreNatal(rs.getBoolean("ofertar_encaminhamento_pre_natal"));
                    
                    return sh;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao buscar dados de saúde do homem por ID");
        }
        
        return null;
    }
    
    public boolean deleteSaudeHomem(int id) {
        String sql = "DELETE FROM saude_homem WHERE id = ?";
        
        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao deletar dados de saúde do homem");
            return false;
        }
    }
} 