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

                a.setPeso(rs.getInt("peso"));
                a.setAltura(rs.getFloat("altura"));
                a.setImc(rs.getFloat("imc"));

                a.setPa(rs.getString("pa"));
                a.setFc(rs.getFloat("fc"));
                a.setSat(rs.getFloat("sat"));
                a.setTemp(rs.getFloat("temp"));

                a.setTosse(rs.getBoolean("tosse"));
                a.setCoriza(rs.getBoolean("coriza"));
                a.setEspirros(rs.getBoolean("espirros"));
                a.setFebre(rs.getBoolean("febre"));
                a.setCalafrios(rs.getBoolean("calafrios"));

                a.setOutrosSistemasRespiratorios(rs.getString("outros_sistemas_respiratorios"));
                a.setDataSintomas(rs.getDate("data_sintomas"));

                a.setApresentaLesoes(rs.getBoolean("apresenta_lesoes"));
                a.setLocalLesoes(rs.getString("local_lesoes"));
                a.setCondutaLesoesClinica(rs.getString("conduta_lesoes_clinica"));

                a.setHiv12Lote(rs.getString("hiv_1_2_lote"));
                a.setHiv12Validade(rs.getDate("hiv_1_2_validade"));
                a.setHiv12Reativo(rs.getObject("hiv_1_2_reativo", Boolean.class));

                a.setHiv22Lote(rs.getString("hiv_2_2_lote"));
                a.setHiv22Validade(rs.getDate("hiv_2_2_validade"));
                a.setHiv22Reativo(rs.getObject("hiv_2_2_reativo", Boolean.class));

                a.setSifilisLote(rs.getString("sifilis_lote"));
                a.setSifilisValidade(rs.getDate("sifilis_validade"));
                a.setSifilisReativo(rs.getObject("sifilis_reativo", Boolean.class));

                a.setHepatiteBLote(rs.getString("hepatite_b_lote"));
                a.setHepatiteBValidade(rs.getDate("hepatite_b_validade"));
                a.setHepatiteBReativo(rs.getObject("hepatite_b_reativo", Boolean.class));

                a.setHepatiteCLote(rs.getString("hepatite_c_lote"));
                a.setHepatiteCValidade(rs.getDate("hepatite_c_validade"));
                a.setHepatiteCReativo(rs.getObject("hepatite_c_reativo", Boolean.class));

                a.setCovidLote(rs.getString("covid_lote"));
                a.setCovidValidade(rs.getDate("covid_validade"));
                a.setCovidReativo(rs.getObject("covid_reativo", Boolean.class));

                a.setTesteGravidez(rs.getObject("teste_gravidez", Boolean.class));
                a.setColetaEscarro(rs.getBoolean("coleta_escarro"));

                a.setApresentaQueixasTesteRapido(rs.getBoolean("apresenta_queixas_teste_rapido"));
                a.setQueixaTesteRapido(rs.getString("queixa_teste_rapido"));
                a.setCondutaTesteRapido(rs.getString("conduta_teste_rapido"));
                a.setCondutaClinica(rs.getString("conduta_clinica"));

                a.setTemQueixaOdontologica(rs.getBoolean("tem_queixa_odontologica"));
                a.setQueixaOdontologica(rs.getString("queixa_odontologica"));
                a.setNecessitaDentista(rs.getBoolean("necessita_dentista"));
                a.setCondutaOdontologica(rs.getString("conduta_odontologica"));
                
                a.setEncaminhamentosFinais(rs.getString("encaminhamentos_finais"));

                return a;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Erro ao buscar atendimento pelo ID");
    }

    return null;
}
} 