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
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
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
    
    public void insertAtendimento2(Atendimento atendimento) throws SQLException {
        String sql = "INSERT INTO atendimento (" +
                "id_atendente, id_prisioneiro, data_hora, data_entrada_unidade, is_transferencia, procedencia," +
                "peso, altura, imc, pa, fc, sat, temp," +
                "tosse, coriza, espirros, febre, calafrios," +
                "outros_sistemas_respiratorios, data_sintomas," +
                "apresenta_lesoes, local_lesoes, conduta_lesoes_clinica," +
                "hiv_1_2_lote, hiv_1_2_validade, hiv_1_2_reativo," +
                "hiv_2_2_lote, hiv_2_2_validade, hiv_2_2_reativo," +
                "sifilis_lote, sifilis_validade, sifilis_reativo," +
                "hepatite_b_lote, hepatite_b_validade, hepatite_b_reativo," +
                "hepatite_c_lote, hepatite_c_validade, hepatite_c_reativo," +
                "covid_lote, covid_validade, covid_reativo," +
                "teste_gravidez, coleta_escarro," +
                "apresenta_queixas_teste_rapido, queixa_teste_rapido, conduta_teste_rapido, conduta_clinica," +
                "tem_queixa_odontologica, queixa_odontologica, necessita_dentista, conduta_odontologica," +
                "encaminhamentos_finais" +
            ") VALUES (" +
                "?, ?, ?, ?, ?, ?," +    // ids, datas, flags iniciais
                "?, ?, ?, ?, ?, ?, ?," + // dados clínicos
                "?, ?, ?, ?, ?," +       // sintomas binários
                "?, ?," +                // outros sistemas
                "?, ?, ?," +             // lesões
                "?, ?, ?," +             // HIV 1/2
                "?, ?, ?," +             // HIV 2/2
                "?, ?, ?," +             // sífilis
                "?, ?, ?," +             // hepatite B
                "?, ?, ?," +             // hepatite C
                "?, ?, ?," +             // COVID
                "?, ?," +                // gravidez, escarro
                "?, ?, ?, ?," +          // teste rápido
                "?, ?, ?, ?," +          // odontologia
                "?" +                    // encaminhamentos
            ")";

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            ps.setInt(i++, atendimento.getIdAtendente());
            ps.setInt(i++, atendimento.getIdPrisioneiro());
            ps.setTimestamp(i++, atendimento.getDataHora());
            ps.setDate(i++, new java.sql.Date(atendimento.getDataEntradaUnidade().getTime()));
            ps.setBoolean(i++, atendimento.isTransferencia());
            ps.setString(i++, atendimento.getProcedencia());

            ps.setInt(i++, atendimento.getPeso());
            ps.setFloat(i++, atendimento.getAltura());
            ps.setFloat(i++, atendimento.getImc());
            ps.setString(i++, atendimento.getPa());
            ps.setFloat(i++, atendimento.getFc());
            ps.setFloat(i++, atendimento.getSat());
            ps.setFloat(i++, atendimento.getTemp());

            ps.setBoolean(i++, atendimento.isTosse());
            ps.setBoolean(i++, atendimento.isCoriza());
            ps.setBoolean(i++, atendimento.isEspirros());
            ps.setBoolean(i++, atendimento.isFebre());
            ps.setBoolean(i++, atendimento.isCalafrios());

            ps.setString(i++, atendimento.getOutrosSistemasRespiratorios());
            if (atendimento.getDataSintomas() != null) {
                ps.setDate(i++, new java.sql.Date(atendimento.getDataSintomas().getTime()));
            } else {
                ps.setNull(i++, Types.DATE);
            }

            ps.setBoolean(i++, atendimento.isApresentaLesoes());
            ps.setString(i++, atendimento.getLocalLesoes());
            ps.setString(i++, atendimento.getCondutaLesoesClinica());

            // HIV 1/2
            ps.setString(i++, atendimento.getHiv12Lote());
            ps.setDate(i++, new java.sql.Date(atendimento.getHiv12Validade().getTime()));
            ps.setBoolean(i++, Boolean.TRUE.equals(atendimento.getHiv12Reativo()));

            // HIV 2/2
            ps.setString(i++, atendimento.getHiv22Lote());
            ps.setDate(i++, new java.sql.Date(atendimento.getHiv22Validade().getTime()));
            ps.setBoolean(i++, Boolean.TRUE.equals(atendimento.getHiv22Reativo()));

            // Sífilis
            ps.setString(i++, atendimento.getSifilisLote());
            ps.setDate(i++, new java.sql.Date(atendimento.getSifilisValidade().getTime()));
            ps.setBoolean(i++, Boolean.TRUE.equals(atendimento.getSifilisReativo()));

            // Hepatite B
            ps.setString(i++, atendimento.getHepatiteBLote());
            ps.setDate(i++, new java.sql.Date(atendimento.getHepatiteBValidade().getTime()));
            ps.setBoolean(i++, Boolean.TRUE.equals(atendimento.getHepatiteBReativo()));

            // Hepatite C
            ps.setString(i++, atendimento.getHepatiteCLote());
            ps.setDate(i++, new java.sql.Date(atendimento.getHepatiteCValidade().getTime()));
            ps.setBoolean(i++, Boolean.TRUE.equals(atendimento.getHepatiteCReativo()));

            // COVID
            ps.setString(i++, atendimento.getCovidLote());
            ps.setDate(i++, new java.sql.Date(atendimento.getCovidValidade().getTime()));
            ps.setBoolean(i++, Boolean.TRUE.equals(atendimento.getCovidReativo()));

            ps.setBoolean(i++, Boolean.TRUE.equals(atendimento.getTesteGravidez()));
            ps.setBoolean(i++, atendimento.isColetaEscarro());

            ps.setBoolean(i++, atendimento.isApresentaQueixasTesteRapido());
            ps.setString(i++, atendimento.getQueixaTesteRapido());
            ps.setString(i++, atendimento.getCondutaTesteRapido());
            ps.setString(i++, atendimento.getCondutaClinica());

            ps.setBoolean(i++, atendimento.isTemQueixaOdontologica());
            ps.setString(i++, atendimento.getQueixaOdontologica());
            ps.setBoolean(i++, atendimento.isNecessitaDentista());
            ps.setString(i++, atendimento.getCondutaOdontologica());

            ps.setString(i++, atendimento.getEncaminhamentosFinais());

            // Executa
            ps.executeUpdate();

            // Recupera id gerado (opcional)
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    atendimento.setId(rs.getInt(1));
                }
            }
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