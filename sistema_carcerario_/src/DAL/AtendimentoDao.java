/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import com.mycompany.sistema_carcerario.model.Atendimento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author m138824
 */
public class AtendimentoDao {
    
    public boolean insertAtendimento(Atendimento atendimento) {
        String sql = "INSERT INTO atendimento (id_atendente, id_prisioneiro, data_hora, data_entrada_unidade, is_transferencia, procedencia) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, atendimento.getIdAtendente());
            ps.setInt(2, atendimento.getIdPrisioneiro());
            ps.setTimestamp(3, atendimento.getDataHora());
            ps.setDate(4, new java.sql.Date(atendimento.getDataEntradaUnidade().getTime()));
            ps.setBoolean(5, atendimento.isTransferencia());
            ps.setString(6, atendimento.getProcedencia());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao inserir atendimento");
            return false;
        }
    }
    
    public List<Atendimento> listarAtendimentosPorPrisioneiro(int idPrisioneiro) {
        List<Atendimento> atendimentos = new ArrayList<>();
        String sql = "SELECT * FROM atendimento WHERE id_prisioneiro = ? ORDER BY data_hora DESC";
        
        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, idPrisioneiro);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Atendimento a = new Atendimento();
                    a.setId(rs.getInt("id"));
                    a.setIdAtendente(rs.getInt("id_atendente"));
                    a.setIdPrisioneiro(rs.getInt("id_prisioneiro"));
                    a.setDataHora(rs.getTimestamp("data_hora"));
                    a.setDataEntradaUnidade(rs.getDate("data_entrada_unidade"));
                    a.setIsTransferencia(rs.getBoolean("is_transferencia"));
                    a.setProcedencia(rs.getString("procedencia"));
                    
                    atendimentos.add(a);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao listar atendimentos");
        }
        
        return atendimentos;
    }
    
    public Atendimento buscarAtendimentoPorId(int id) {
        String sql = "SELECT * FROM atendimento WHERE id = ?";
        
        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Atendimento a = new Atendimento();
                    a.setId(rs.getInt("id"));
                    a.setIdAtendente(rs.getInt("id_atendente"));
                    a.setIdPrisioneiro(rs.getInt("id_prisioneiro"));
                    a.setDataHora(rs.getTimestamp("data_hora"));
                    a.setDataEntradaUnidade(rs.getDate("data_entrada_unidade"));
                    a.setIsTransferencia(rs.getBoolean("is_transferencia"));
                    a.setProcedencia(rs.getString("procedencia"));
                    
                    return a;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao buscar atendimento por ID");
        }
        
        return null;
    }
} 