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
                p.setIdade(rs.getInt("idade"));

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

public int getIdByNomePrisioneiro(String nome) {
    String sql = "SELECT id FROM prisioneiro WHERE nome_completo = ?";
    
    try (Connection conexao = ConexaoBanco.conectar();
         PreparedStatement ps = conexao.prepareStatement(sql)) {
        
        ps.setString(1, nome);
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");                
            }
        }
          
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Erro ao buscar id do prisioneiro por Nome");
    }
    
    return -1;
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
                     "nome_completo, nome_social, nome_mae, data_nascimento, cpf, idade, " +
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
                     "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                     "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                     "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                     "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                     "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                     "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                     "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                     "?, ?, ?, ?, ?, ?, ?,  " +
                     "?)";

            try (Connection conn = ConexaoBanco.conectar()) {
               PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);


               ps.setString(1, prisioneiro.getNomeCompleto());
               ps.setString(2, prisioneiro.getNomeSocial());
               ps.setString(3, prisioneiro.getNomeMae());
               ps.setDate(4, prisioneiro.getDataNascimento());
               ps.setString(5, prisioneiro.getCpf());
               ps.setInt(6, prisioneiro.getIdade());
               ps.setString(7, prisioneiro.getOrientacao());
               ps.setString(8, prisioneiro.getGenero());
               ps.setString(9, prisioneiro.getSexo());
               ps.setString(10, prisioneiro.getRaca());
               ps.setString(11, prisioneiro.getNacionalidade());
               ps.setString(12, prisioneiro.getEstadoCivil());
               ps.setString(13, prisioneiro.getEscolaridade());
               ps.setBoolean(14, prisioneiro.isBeneficioFamilia());
               ps.setString(15, prisioneiro.getBeneficioEspecificado());
               ps.setBoolean(16, prisioneiro.isPossuiFilhos());
               ps.setInt(17, prisioneiro.getQuantosFilhos());
               ps.setBoolean(18, prisioneiro.isPossuiDependentes());
               ps.setInt(19, prisioneiro.getQuantosDependentes());
               ps.setBoolean(20, prisioneiro.isOfertarNeeja());
               ps.setBoolean(21, prisioneiro.isOfertarAssistenciaSocial());
               ps.setBoolean(22, prisioneiro.isPossuiDeficiencia());
               ps.setString(23, prisioneiro.getQualDeficiencia());
               ps.setBoolean(24, prisioneiro.isPossuiAlergias());
               ps.setString(25, prisioneiro.getQuaisAlergias());
               ps.setBoolean(26, prisioneiro.isRealizouCirurgias());
               ps.setString(27, prisioneiro.getQuaisCirurgias());
               ps.setBoolean(28, prisioneiro.isNaoSabeResponderCirurgias());
               ps.setBoolean(29, prisioneiro.isHipertencao());
               ps.setBoolean(30, prisioneiro.isDiabetes());
               ps.setBoolean(31, prisioneiro.isHiv());
               ps.setBoolean(32, prisioneiro.isAutoimune());
               ps.setString(33, prisioneiro.getOutrasDoencasCronicas());
               ps.setBoolean(34, prisioneiro.isNaoSabeResponderCondicoesCronicas());
               ps.setString(35, prisioneiro.getObservacaoCondicoesCronicas());
               ps.setBoolean(36, prisioneiro.isSifilis());
               ps.setBoolean(37, prisioneiro.isHpv());
               ps.setBoolean(38, prisioneiro.isTuberculose());
               ps.setBoolean(39, prisioneiro.isHepatiteB());
               ps.setBoolean(40, prisioneiro.isHepatiteC());
               ps.setString(41, prisioneiro.getOutrasDoencasInfecciosas());
               ps.setBoolean(42, prisioneiro.isNaoSabeResponderDoencasInfecciosas());
               ps.setString(43, prisioneiro.getObservacaoHistoricoDoencasInfecciosas());
               ps.setBoolean(44, prisioneiro.isDoencaPele());
               ps.setString(45, prisioneiro.getQuaisDoencasPele());
               ps.setBoolean(46, prisioneiro.isNaoSabeResponderDoencasPele());
               ps.setString(47, prisioneiro.getObservacaoHistoricoDoencasPele());
               ps.setString(48, prisioneiro.getMedicamentosContinuos());
               ps.setString(49, prisioneiro.getQuaisMedicamentos());
               ps.setString(50, prisioneiro.getTipoSanguineo());
               ps.setString(51, prisioneiro.getVinculoCaps());
               ps.setString(52, prisioneiro.getNomeMunicioCaps());
               ps.setBoolean(53, prisioneiro.isAnsiedade());
               ps.setBoolean(54, prisioneiro.isDepressao());
               ps.setBoolean(55, prisioneiro.isBipolaridade());
               ps.setBoolean(56, prisioneiro.isEsquizofrenia());
               ps.setBoolean(57, prisioneiro.isAutismo());
               ps.setString(58, prisioneiro.getOutraSaudeMental());
               ps.setBoolean(59, prisioneiro.isNaoSabeResponderSaudeMental());
               ps.setString(60, prisioneiro.getMedicamentoControlado());
               ps.setString(61, prisioneiro.getQualMedicamentoControlado());
               ps.setBoolean(62, prisioneiro.isAcompanhamentoMentalMomentoPrisao());
               ps.setString(63, prisioneiro.getMotivoAcompanhamentoMental());
               ps.setBoolean(64, prisioneiro.isAlcool());
               ps.setBoolean(65, prisioneiro.isCigarro());
               ps.setBoolean(66, prisioneiro.isMaconha());
               ps.setBoolean(67, prisioneiro.isCrack());
               ps.setBoolean(68, prisioneiro.isCocaina());
               ps.setBoolean(69, prisioneiro.isAnfetaminas());
               ps.setBoolean(70, prisioneiro.isDrogas());
               ps.setString(71, prisioneiro.getOutrasDrogas());
               ps.setBoolean(72, prisioneiro.isTratamentoReabilitacao());
               ps.setString(73, prisioneiro.getTratamentoQualSubstancia());
               ps.setBoolean(74, prisioneiro.isQuerReabilitacao());
               ps.setString(75, prisioneiro.getReabilitacaoQualSubstancia());
               ps.setBoolean(76, prisioneiro.isOfertarPsicologa());
               ps.setBoolean(77, prisioneiro.isOfertarPsiquiatra());
               ps.setBoolean(78, prisioneiro.isEncaminharReceitas());
               ps.setBoolean(79, prisioneiro.isEncaminharGrupoApoio());
               ps.setBoolean(80, prisioneiro.isVacinaCovid());
               ps.setBoolean(81, prisioneiro.isVacinaInfluenza());
               ps.setBoolean(82, prisioneiro.isVacinaTetano());
               ps.setBoolean(83, prisioneiro.isVacinaHepatite());
               ps.setBoolean(84, prisioneiro.isOfertarVacinas());
               ps.setBoolean(85, prisioneiro.isOfertarVacinaFebreAmarela());
               ps.setBoolean(86, prisioneiro.isOfertarVacinaHepatiteB());
               ps.setBoolean(87, prisioneiro.isOfertarVacinaCovid19());
               ps.setBoolean(88, prisioneiro.isOfertarVacinaInfluenza());
               ps.setBoolean(89, prisioneiro.isOfertarVacinaDuplaAdulto());
               ps.setBoolean(90, prisioneiro.isOfertarVacinaTripliceViral());
               ps.setString(91, prisioneiro.getOutraVacina());
               ps.setBoolean(92, prisioneiro.isOfertarCarteiraVacinacao());
               ps.setString(93, prisioneiro.getEncaminhamentosFinais());


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
