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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author m138824
 */
public class PrisioneiroDao {
    
    public ArrayList<Prisioneiro> getNomesPrisioneiros(){
        ArrayList<Prisioneiro> prisioneiros = new ArrayList<>();
        String sql = "SELECT nome_completo,id FROM prisioneiro";

        try (Connection conexao = ConexaoBanco.conectar()){
            Statement stmt = conexao.createStatement();  
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Prisioneiro prisioneiro = new Prisioneiro();
                prisioneiro.setNomeCompleto(rs.getString("nome_completo"));
                prisioneiro.setId(rs.getInt("id"));
                prisioneiros.add(prisioneiro);   
            }

            return prisioneiros;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao listar prisioneiros com filtro");
        }
        return null;
    }
    
    public List<Prisioneiro> listarPrisioneiros(String filter) {
    List<Prisioneiro> prisioneiros = new ArrayList<>();
    String sql = "SELECT * FROM prisioneiro WHERE nome_completo LIKE ?";

    try (Connection conexao = ConexaoBanco.conectar();
         PreparedStatement ps = conexao.prepareStatement(sql)) {

        
        // Vai fazer um LIke para pegar todos que contenham pelo menos um pedaço
        // do que foi passado como filtro
        ps.setString(1, "%" + filter + "%"); 

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Prisioneiro p = new Prisioneiro();
                p.setId(rs.getInt("id"));
                p.setNomeCompleto(rs.getString("nome_completo"));
                p.setNomeSocial(rs.getString("nome_social"));
                p.setNomeMae(rs.getString("nome_mae"));
                p.setDataNascimento(rs.getDate("data_nascimento"));
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
                p.setNomeCompleto(rs.getString("nome_completo"));
                p.setNomeSocial(rs.getString("nome_social"));
                p.setNomeMae(rs.getString("nome_mae"));
                p.setDataNascimento(rs.getDate("data_nascimento"));
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
    String sql = "UPDATE prisioneiro SET nome_completo = ?, nome_social = ?, nome_mae = ?, data_nascimento = ?, cpf = ?, " +
                 "orientacao = ?, genero = ?, sexo = ?, raca = ?, nacionalidade = ?, " +
                 "estado_civil = ?, escolaridade = ? WHERE id = ?";
    
    try (Connection conexao = ConexaoBanco.conectar();
         PreparedStatement ps = conexao.prepareStatement(sql)) {
        
        ps.setString(1, prisioneiro.getNomeCompleto());
        ps.setString(2, prisioneiro.getNomeSocial());
        ps.setString(3, prisioneiro.getNomeMae());
        ps.setDate(4, prisioneiro.getDataNascimento());
        ps.setString(5, prisioneiro.getCpf());
        ps.setString(6, prisioneiro.getOrientacao());
        ps.setString(7, prisioneiro.getGenero());
        ps.setString(8, prisioneiro.getSexo());
        ps.setString(9, prisioneiro.getRaca());
        ps.setString(10, prisioneiro.getNacionalidade());
        ps.setString(11, prisioneiro.getEstadoCivil());
        ps.setString(12, prisioneiro.getEscolaridade());
        ps.setInt(13, prisioneiro.getId());
        
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
            String sql = "INSERT INTO prisioneiro (data_nascimento, nome_completo, cpf, id_orientacao, id_genero, id_sexo, "
                    + "id_raça, id_nacionalidade, id_estado_civil, id_escolaridade)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, prisioneiro.getNomeCompleto());
            ps.setDate(2, prisioneiro.getDataNascimento());
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
    
    public int insertPrisioneiro(Prisioneiro prisioneiro) {
        String sql = "INSERT INTO prisioneiro (" +
                     "nome_completo, nome_social, nome_mae, data_nascimento, cpf, " +
                     "orientacao, genero, sexo, raca, nacionalidade, estado_civil, escolaridade, " +
                     "beneficio_familia, beneficio_especificado, possui_filhos, quantos_filhos, " +
                     "possui_dependentes, quantos_dependentes, ofertar_neeja, ofertar_assistencia_social, " +
                     "possui_deficiencia, qual_deficiencia, possui_alergias, quais_alergias, " +
                     "realizou_cirurgias, quais_cirurgias, nao_sabe_responder_cirurgias, " +
                     "hipertencao, diabetes, hiv, autoimune, outras_doencas_cronicas, nao_sabe_responder_condicoes_cronicas, observacao_condicoes_cronicas, " +
                     "sifilis, hpv, tuberculose, hepatite_b, hepatite_c, outras_doencas_infecciosas, nao_sabe_responder_doencas_infecciosas, observacao_historico_doencas_infecciosas, " +
                     "doenca_pele, quais_doencas_pele, nao_sabe_responder_doencas_pele, observacao_historico_doencas_pele, " +
                     "medicamentos_continuos, quais_medicamentos, tipo_sanguineo, " +
                     "vinculo_caps, nome_municio_caps, ansiedade, depressao, bipolaridade, esquizofrenia, autismo, outra_saude_mental, nao_sabe_responder_saude_mental, " +
                     "medicamento_controlado, qual_medicamento_controlado, acompanhamento_mental_momento_prisao, motivo_acompanhamento_mental, " +
                     "alcool, cigarro, maconha, crack, cocaina, anfetaminas, drogas, outras_drogas, " +
                     "tratamento_reabilitacao, tratamento_qual_substancia, quer_reabilitacao, reabilitacao_qual_substancia, " +
                     "ofertar_psicologa, ofertar_psiquiatra, encaminhar_receitas, encaminhar_grupo_apoio, " +
                     "vacina_covid, vacina_influenza, vacina_tetano, vacina_hepatite, " +
                     "ofertar_vacinas, ofertar_vacina_febre_amarela, ofertar_vacina_hepatite_b, ofertar_vacina_covid_19, ofertar_vacina_influenza, ofertar_vacina_dupla_adulto, ofertar_vacina_triplice_viral, " +
                     "outra_vacina, ofertar_carteira_vacinacao, encaminhamentos_finais" +
                     ") VALUES (" +
                     "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                     "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                     "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                     "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                     "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                     "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                     "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                     "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                     "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                     "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + "?)";

            try (Connection conn = ConexaoBanco.conectar()) {
               PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);


               ps.setString(1, prisioneiro.getNomeCompleto());
               ps.setString(2, prisioneiro.getNomeSocial());
               ps.setString(3, prisioneiro.getNomeMae());
               ps.setDate(4, prisioneiro.getDataNascimento());
               ps.setString(5, prisioneiro.getCpf());
               ps.setString(6, prisioneiro.getOrientacao());
               ps.setString(7, prisioneiro.getGenero());
               ps.setString(8, prisioneiro.getSexo());
               ps.setString(9, prisioneiro.getRaca());
               ps.setString(10, prisioneiro.getNacionalidade());
               ps.setString(11, prisioneiro.getEstadoCivil());
               ps.setString(12, prisioneiro.getEscolaridade());
               ps.setBoolean(13, prisioneiro.isBeneficioFamilia());
               ps.setString(14, prisioneiro.getBeneficioEspecificado());
               ps.setBoolean(15, prisioneiro.isPossuiFilhos());
               ps.setInt(16, prisioneiro.getQuantosFilhos());
               ps.setBoolean(17, prisioneiro.isPossuiDependentes());
               ps.setInt(18, prisioneiro.getQuantosDependentes());
               ps.setBoolean(19, prisioneiro.isOfertarNeeja());
               ps.setBoolean(20, prisioneiro.isOfertarAssistenciaSocial());
               ps.setBoolean(21, prisioneiro.isPossuiDeficiencia());
               ps.setString(22, prisioneiro.getQualDeficiencia());
               ps.setBoolean(23, prisioneiro.isPossuiAlergias());
               ps.setString(24, prisioneiro.getQuaisAlergias());
               ps.setBoolean(25, prisioneiro.isRealizouCirurgias());
               ps.setString(26, prisioneiro.getQuaisCirurgias());
               ps.setBoolean(27, prisioneiro.isNaoSabeResponderCirurgias());
               ps.setBoolean(28, prisioneiro.isHipertencao());
               ps.setBoolean(29, prisioneiro.isDiabetes());
               ps.setBoolean(30, prisioneiro.isHiv());
               ps.setBoolean(31, prisioneiro.isAutoimune());
               ps.setString(32, prisioneiro.getOutrasDoencasCronicas());
               ps.setBoolean(33, prisioneiro.isNaoSabeResponderCondicoesCronicas());
               ps.setString(34, prisioneiro.getObservacaoCondicoesCronicas());
               ps.setBoolean(35, prisioneiro.isSifilis());
               ps.setBoolean(36, prisioneiro.isHpv());
               ps.setBoolean(37, prisioneiro.isTuberculose());
               ps.setBoolean(38, prisioneiro.isHepatiteB());
               ps.setBoolean(39, prisioneiro.isHepatiteC());
               ps.setString(40, prisioneiro.getOutrasDoencasInfecciosas());
               ps.setBoolean(41, prisioneiro.isNaoSabeResponderDoencasInfecciosas());
               ps.setString(42, prisioneiro.getObservacaoHistoricoDoencasInfecciosas());
               ps.setBoolean(43, prisioneiro.isDoencaPele());
               ps.setString(44, prisioneiro.getQuaisDoencasPele());
               ps.setBoolean(45, prisioneiro.isNaoSabeResponderDoencasPele());
               ps.setString(46, prisioneiro.getObservacaoHistoricoDoencasPele());
               ps.setString(47, prisioneiro.getMedicamentosContinuos());
               ps.setString(48, prisioneiro.getQuaisMedicamentos());
               ps.setString(49, prisioneiro.getTipoSanguineo());
               ps.setString(50, prisioneiro.getVinculoCaps());
               ps.setString(51, prisioneiro.getNomeMunicioCaps());
               ps.setBoolean(52, prisioneiro.isAnsiedade());
               ps.setBoolean(53, prisioneiro.isDepressao());
               ps.setBoolean(54, prisioneiro.isBipolaridade());
               ps.setBoolean(55, prisioneiro.isEsquizofrenia());
               ps.setBoolean(56, prisioneiro.isAutismo());
               ps.setString(57, prisioneiro.getOutraSaudeMental());
               ps.setBoolean(58, prisioneiro.isNaoSabeResponderSaudeMental());
               ps.setString(59, prisioneiro.getMedicamentoControlado());
               ps.setString(60, prisioneiro.getQualMedicamentoControlado());
               ps.setBoolean(61, prisioneiro.isAcompanhamentoMentalMomentoPrisao());
               ps.setString(62, prisioneiro.getMotivoAcompanhamentoMental());
               ps.setBoolean(63, prisioneiro.isAlcool());
               ps.setBoolean(64, prisioneiro.isCigarro());
               ps.setBoolean(65, prisioneiro.isMaconha());
               ps.setBoolean(66, prisioneiro.isCrack());
               ps.setBoolean(67, prisioneiro.isCocaina());
               ps.setBoolean(68, prisioneiro.isAnfetaminas());
               ps.setBoolean(69, prisioneiro.isDrogas());
               ps.setString(70, prisioneiro.getOutrasDrogas());
               ps.setBoolean(71, prisioneiro.isTratamentoReabilitacao());
               ps.setString(72, prisioneiro.getTratamentoQualSubstancia());
               ps.setBoolean(73, prisioneiro.isQuerReabilitacao());
               ps.setString(74, prisioneiro.getReabilitacaoQualSubstancia());
               ps.setBoolean(75, prisioneiro.isOfertarPsicologa());
               ps.setBoolean(76, prisioneiro.isOfertarPsiquiatra());
               ps.setBoolean(77, prisioneiro.isEncaminharReceitas());
               ps.setBoolean(78, prisioneiro.isEncaminharGrupoApoio());
               ps.setBoolean(79, prisioneiro.isVacinaCovid());
               ps.setBoolean(80, prisioneiro.isVacinaInfluenza());
               ps.setBoolean(81, prisioneiro.isVacinaTetano());
               ps.setBoolean(82, prisioneiro.isVacinaHepatite());
               ps.setBoolean(83, prisioneiro.isOfertarVacinas());
               ps.setBoolean(84, prisioneiro.isOfertarVacinaFebreAmarela());
               ps.setBoolean(85, prisioneiro.isOfertarVacinaHepatiteB());
               ps.setBoolean(86, prisioneiro.isOfertarVacinaCovid19());
               ps.setBoolean(87, prisioneiro.isOfertarVacinaInfluenza());
               ps.setBoolean(88, prisioneiro.isOfertarVacinaDuplaAdulto());
               ps.setBoolean(89, prisioneiro.isOfertarVacinaTripliceViral());
               ps.setString(90, prisioneiro.getOutraVacina());
               ps.setBoolean(91, prisioneiro.isOfertarCarteiraVacinacao());
               ps.setString(92, prisioneiro.getEncaminhamentosFinais());


               int rowsAffected = ps.executeUpdate();

               if (rowsAffected > 0) {
                   try (ResultSet rs = ps.getGeneratedKeys()) {
                       if (rs.next()) {
                           return rs.getInt(1);
                       }
                   }
               }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao inserir prisioneiro");
        }
        
        return -1; // Retorna -1 se houver erro
    }
}
