/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.sistema_carcerario.view;

import DAL.AtendenteDao;
import DAL.PrisioneiroDao;
import DAL.AtendimentoDao;
import DAL.SaudeHomemDao;
import DAL.SaudeMulherDao;
import com.mycompany.sistema_carcerario.controller.CheckBoxController;
import com.mycompany.sistema_carcerario.controller.RadioButtonController;
import com.mycompany.sistema_carcerario.model.Atendente;
import com.mycompany.sistema_carcerario.model.Prisioneiro;
import com.mycompany.sistema_carcerario.model.Atendimento;
import com.mycompany.sistema_carcerario.model.SaudeHomem;
import com.mycompany.sistema_carcerario.model.SaudeMulher;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JCheckBox;

/**
 *
 * @author m138824
 */
public class AtendimentoPanel extends javax.swing.JPanel {

    final MainFrame parent;
    private final RadioButtonController rbController = new RadioButtonController();
    private final CheckBoxController cbController = new CheckBoxController();

    private final PrisioneiroDao prisioneiroDao = new PrisioneiroDao();
    private final AtendimentoDao atendimentoDao = new AtendimentoDao();
    private final SaudeHomemDao saudeHomemDao = new SaudeHomemDao();
    private final SaudeMulherDao saudeMulherDao = new SaudeMulherDao();

    private Prisioneiro prisioneiroAtual;
    private SaudeHomem saudeHomemAtual;
    private SaudeMulher saudeMulherAtual;

    private final AtendenteDao atendenteDao = new AtendenteDao();

    /**
     * Creates new form AtendimentoPanel2
     */
    public AtendimentoPanel(MainFrame parent) {
        initComponents();
        this.parent = parent;
        setComboBoxResponsavel();
        //Setando a data atual 
        jLabelData.setText(getDataAtual());

        configurarTextFieldCondicionais();
        configurarCheckBoxesCondicionais();
        configurarComboBoxOrientacaoSex();
    }

    // Passa todos os chackboxes que precisam desativar/ativar algum campo de 
    // texto estes são configurados pela função do RadioButtonController 
    // para tal comportamento
    private void configurarTextFieldCondicionais() {

        // Identificação
        rbController.configurarRadioGroup(bg_transferencia, rb_transferencia_sim, rb_transferencia_nao, tf_transferencia);
        rbController.configurarRadioGroup(bg_nacionalidade, rb_nacionalidade_estrangeiro, rb_nacionalidade_brasileira, rb_nacionalidade_naturalizado, tf_nacionalidade_qual_pais);

        // Dados sociais e econômicos
        rbController.configurarRadioGroup(bg_fam_rec_beneficio, rb_fam_rec_beneficio_sim, rb_fam_rec_beneficio_nao, tf_fam_rec_beneficio_quais);
        rbController.configurarRadioGroup(bg_possui_filhos, rb_poss_filhos_sim, rb_poss_filhos_nao, tf_poss_filhos_quantos, tf_idade_filhos);
        rbController.configurarRadioGroup(bg_possuio_outro_dependente, rb_possui_outro_dependente_sim, rb_possui_outro_dependente_nao, tf_possui_outro_dependente_qts);

        // Condições de Saúde
        rbController.configurarRadioGroup(bg_possui_deficiencia, rb_possui_deficiencia_sim, rb_possui_deficiencia_nao, rb_possui_deficiencia_nao_sei, tf_deficiencia_quais);
        rbController.configurarRadioGroup(bg_possui_intolerancia, rb_possui_alergia_sim, rb_possui_alergia_nao, rb_possui_alergia_nao_sei, tf_possui_intolerancia_quais);
        rbController.configurarRadioGroup(bg_realizou_cirurgia, rb_realizou_cirurgia_sim, rb_realizou_cirurgia_nao, rb_realizou_cirurgia_nao_sei, tf_realizou_cirurgia_quais);

        // Histórico doenças infecciosas
        rbController.configurarRadioGroup(bg_possui_denca_de_pele, rb_doenca_pele_sim, rb_doenca_pele_nao, tf_doenca_pele_quais);
        rbController.configurarRadioGroup(bg_med_continuo, rb_usa_med_continuo_sim, rb_usa_med_continuo_nao, tf_med_continuo);

        // Saúde da mulher
        rbController.configurarRadioGroup(bg_realizou_papanicolau, rb_papanicolau_sim, rb_papanicolau_nao, tf_papanicolau_ano);

        // Saúde do Homem
        rbController.configurarRadioGroup(bg_exame_prostata, rb_exame_prostata_sim, rb_exame_prostata_nao, tf_exame_prostata_ano);
        rbController.configurarRadioGroup(bg_historico_prostata_familia, rb_historico_prostata_sim, rb_historico_prostata_nao, rb_historico_prostata_familiar);
        rbController.configurarRadioGroup(bg_parceira_gestante, rb_parceira_gestante_sim, rb_parceira_gestante_nao, rb_esta_participando_pre_natal_nao, rb_esta_participando_pre_natal_sim, bg_esta_participando_pre_natal);

        // Saúde mental e uso de substâncias
        rbController.configurarRadioGroup(bg_vinculo_caps, rb_vinculo_caps_sim, rb_vinculo_caps_nao, tf_vinculo_caps_nome, tf_vinculo_caps_municipio);
        rbController.configurarRadioGroup(bg_usa_medicamento_controlado, rb_usa_med_controlado_sim, rb_usa_med_controlado_nao, rb_usa_med_controlado_nao_sabe, tf_usa_med_controlado_qual);
        rbController.configurarRadioGroup(bg_estava_acomp_saude_mental_momento_prisao, rb_acompanhamento_saude_mental_no_momento_da_prisao_sim, rb_acompanhamento_saude_mental_no_momento_da_prisao_nao, tf_acompanhamento_saude_mental_no_momento_da_prisao_qual);
        rbController.configurarRadioGroup(bg_ja_realizou_trat_para_cessar_reduzir_uso, rb_ja_realizou_trat_para_cessar_reduzir_uso_sim, rb_ja_realizou_trat_para_cessar_reduzir_uso_nao, tf_ja_realizou_trat_para_cessar_reduzir_uso_qual);
    }

    private void configurarCheckBoxesCondicionais() {
        // Condições especiais
        List<JCheckBox> checkBoxList_cond_cron = Arrays.asList(cb_cond_cron_possui_diabetes, cb_cond_cron_possui_HIV, cb_cond_cron_possui_autoimune, cb_cond_cron_possui_hipertensao);
        cbController.configurarCheckBox(cb_cond_cron_nao_sabe_responder, checkBoxList_cond_cron);

        // Doenças infecciosas
        List<JCheckBox> checkBoxList_doencas_infec = Arrays.asList(cb_hist_doencas_infec_sifilis, cb_hist_doencas_infec_tuberculose, cb_hist_doencas_infec_hpv, cb_hist_doencas_infec_hepatite_c, cb_hist_doencas_infec_hepatite_b);
        cbController.configurarCheckBox(cb_hist_doencas_infec_nao_sabe_responder, checkBoxList_doencas_infec);

        // Possui transtornos mentais
        List<JCheckBox> checkBoxList_transt_mentais = Arrays.asList(cb_possui_ansiedade, cb_possui_autismo, cb_possui_bipolaridade, cb_possui_depressao, cb_possui_esquisofrenia);
        cbController.configurarCheckBox(cb_transt_mentais_n_sabe_resp, checkBoxList_transt_mentais);

        // Ofertar Vacinas disponieis
        List<JCheckBox> checkBoxList_vac_dispo = Arrays.asList(cb_ofertar_vacina_covid, cb_ofertar_vacina_dupla_adulto, cb_ofertar_vacina_febre_amarela, cb_ofertar_vacina_hepatite_b, cb_ofertar_vacina_influenza, cb_ofertar_vaciana_triplice_viral);
        cbController.configurarCheckBox(cb_ofertar_vaciana_nao, checkBoxList_vac_dispo);

        // Gostaria de realizar algum tratamento para cessar ou reduzir o uso?
        cbController.configurarCheckBoxECampoDeTexto(cb_gostaria_realizar_trat_para_cessar_reduzir_uso, tf_gostaria_realizar_trat_para_cessar_reduzir_uso_qual_subs);
    }

    // Se o ComboBox tiver na Opção Outra, ele habilita o texto apra o usuário 
    // poder digitar qual aopção que seria a tal da outra
    private void configurarComboBoxOrientacaoSex() {

        cb_orientacao_sexual.addActionListener(e -> {
            String selecionado = (String) cb_orientacao_sexual.getSelectedItem();
            if ("Outra".equals(selecionado)) {
                tf_orientacao_sexual_outra.setEnabled(true);
                tf_orientacao_sexual_outra.setEditable(true);
            } else {
                tf_orientacao_sexual_outra.setEnabled(false);
                tf_orientacao_sexual_outra.setEditable(false);
                tf_orientacao_sexual_outra.setText("");
            }
        });
    }

    // Retorna uma String contendo a data atual no formato "dd/MM/yyyy"
    public String getDataAtual() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = LocalDate.now().format(formatter);
        return dataFormatada;
    }

    // Função responsável por carregar um prisioneiro existente pelo ID
    // Vai chamar query no DAO e preencher UI
    public void carregarPrisioneiro(int idPrisioneiro) {
        prisioneiroAtual = prisioneiroDao.buscarPrisioneiroPorId(idPrisioneiro);
        saudeMulherAtual = saudeMulherDao.buscarSaudeMulherPorId(idPrisioneiro);
        saudeHomemAtual = saudeHomemDao.buscarSaudeHomemPorId(idPrisioneiro);

        System.out.println(saudeMulherAtual);
        System.out.println(saudeHomemAtual);

        if (prisioneiroAtual == null) {
            return;
        }

        // Esconde painel quando carrega novo prisioneiro
        atendimento_panel.setVisible(false);

        tf_nome.setText(prisioneiroAtual.getNomeCompleto());
        tf_nome_social.setText(prisioneiroAtual.getNomeCompleto()); // Assuming nome social is the same as nome for now
        tf_data_nascimento.setText(prisioneiroAtual.getDataNascimento().toString());
        tf_nome_mae.setText(prisioneiroAtual.getNomeMae());
        tf_cpf.setText(prisioneiroAtual.getCpf());
        //tf_idade.setText(String.valueOf(prisioneiroAtual.calcularIdade()));
        tf_etinia.setText(prisioneiroAtual.getRaca());

        setComboBoxValue(cb_sexo_biologico, prisioneiroAtual.getSexo());
        setComboBoxValue(cb_identidade_genero, prisioneiroAtual.getGenero());
        setComboBoxValue(cb_orientacao_sexual, prisioneiroAtual.getOrientacao());
        setComboBoxValue(jComboBox1, prisioneiroAtual.getRaca());

        carregarDadosRadioButtons(prisioneiroAtual);
        carregarDadosTextField(prisioneiroAtual);
        carregarDadosCheckBox(prisioneiroAtual);
        configSaudePanels();
    }

    private void carregarDadosRadioButtons(Prisioneiro prisioneiro) {
        rbController.selecionarRadioButtonPorValor(bg_nacionalidade, prisioneiro.getNacionalidade());
        rbController.selecionarRadioButtonPorValor(bg_escolaridade, prisioneiro.getEscolaridade());
        rbController.selecionarRadioButtonPorValorSimNao(bg_fam_rec_beneficio, prisioneiro.isBeneficioFamilia());

        rbController.selecionarRadioButtonPorValorSimNao(bg_possui_filhos, prisioneiro.isPossuiFilhos());
        rbController.selecionarRadioButtonPorValorSimNao(bg_possuio_outro_dependente, prisioneiro.isPossuiDependentes());
        rbController.selecionarRadioButtonPorValorSimNao(bg_neeja, prisioneiro.isOfertarNeeja());
        rbController.selecionarRadioButtonPorValorSimNao(bg_conversa_assistencia_social, prisioneiro.isOfertarAssistenciaSocial());

        // Condições de saúde
        rbController.selecionarRadioButtonSimNaoNSR(bg_possui_deficiencia, prisioneiro.isPossuiDeficiencia());
        rbController.selecionarRadioButtonSimNaoNSR(bg_possui_intolerancia, prisioneiro.isPossuiAlergias());
        rbController.selecionarRadioButtonPorValorSimNao(bg_realizou_cirurgia, prisioneiro.isRealizouCirurgias());

        // histórico doenças infecciosas
        rbController.selecionarRadioButtonPorValorSimNao(bg_possui_denca_de_pele, prisioneiro.isDoencaPele());
        //rbController.selecionarRadioButtonPorValorSimNao( bg_med_continuo ,prisioneiro.ismed);

        rbController.selecionarRadioButtonPorTexto(bg_tipo_sangue, prisioneiro.getTipoSanguineo());

        // Saúde da Mulher
        if (saudeMulherAtual != null) {
            rbController.selecionarRadioButtonSimNaoNSR(bg_gestacao_no_momento, saudeMulherAtual.getGestacao());
            rbController.selecionarRadioButtonPorTexto(bg_tipo_met_anticoncepcional, saudeMulherAtual.getQualContraceptivo());
            rbController.selecionarRadioButtonPorValorSimNao(bg_realizou_papanicolau, saudeMulherAtual.isExamePreventivoPapanicolau());
            rbController.selecionarRadioButtonPorValorSimNao(bg_ofertar_continuidade_contraceptivo, saudeMulherAtual.isOfertarContinuidadeContraceptivo());
            rbController.selecionarRadioButtonPorValorSimNao(bg_consultar_exame_preventivo, saudeMulherAtual.isOfertarConsultaPreventivo());
            rbController.selecionarRadioButtonPorValorSimNao(bg_enc_pre_natal, saudeMulherAtual.isEncaminharPreNatal());
        } else {
            bg_gestacao_no_momento.clearSelection();
            bg_tipo_met_anticoncepcional.clearSelection();
            bg_realizou_papanicolau.clearSelection();
            bg_ofertar_continuidade_contraceptivo.clearSelection();
            bg_consultar_exame_preventivo.clearSelection();
            bg_enc_pre_natal.clearSelection();
        }

        // Saúde do Homem
        if (saudeHomemAtual != null) {
            rbController.selecionarRadioButtonPorValorSimNao(bg_exame_prostata, saudeHomemAtual.isRealizouExameProstata());
            rbController.selecionarRadioButtonPorValorSimNao(bg_exame_prostata, saudeHomemAtual.isRealizouExameProstata());
            rbController.selecionarRadioButtonPorValorSimNao(bg_realizou_vasectomia, saudeHomemAtual.isRealizouVasectomia());
            rbController.selecionarRadioButtonPorValorSimNao(bg_parceira_gestante, saudeHomemAtual.isParceiraGestante());
            rbController.selecionarRadioButtonPorValorSimNao(bg_esta_participando_pre_natal, saudeHomemAtual.isParticipaPreNatal());
            rbController.selecionarRadioButtonPorValorSimNao(bg_encaminhar_realiz_vasectomia, saudeHomemAtual.isOfertarEncaminhamentoVasectomia());
            rbController.selecionarRadioButtonPorValorSimNao(bg_encaminhar_pre_natal_do_parceiro, saudeHomemAtual.isOfertarEncaminhamentoPreNatal());
        } else {
            bg_exame_prostata.clearSelection();
            bg_exame_prostata.clearSelection();
            bg_realizou_vasectomia.clearSelection();
            bg_parceira_gestante.clearSelection();
            bg_esta_participando_pre_natal.clearSelection();
            bg_encaminhar_realiz_vasectomia.clearSelection();
            bg_encaminhar_pre_natal_do_parceiro.clearSelection();
        }

        //rbController.selecionarRadioButtonPorValorSimNao( bg_ ,prisioneiro.);
    }

    private void carregarDadosTextField(Prisioneiro prisioneiro) {
        tf_idade.setText(String.valueOf(prisioneiroAtual.getIdade()));
        tf_fam_rec_beneficio_quais.setText(prisioneiro.getBeneficioEspecificado());
        tf_poss_filhos_quantos.setText(String.valueOf(prisioneiroAtual.getQuantosFilhos()));
        //tf_idade_filhos.setText(prisioneiroAtual.get

        tf_possui_outro_dependente_qts.setText(String.valueOf(prisioneiro.getQuantosDependentes()));

        tf_fam_rec_beneficio_quais.setText(prisioneiro.getBeneficioEspecificado());
        tf_possui_intolerancia_quais.setText(prisioneiro.getQuaisAlergias());
        tf_realizou_cirurgia_quais.setText(prisioneiro.getQuaisCirurgias());

        // Condições crônicas
        tf_cond_croni_outra.setText(prisioneiro.getOutrasDoencasCronicas());
        tf_cond_croni_quais.setText(prisioneiro.getObservacaoCondicoesCronicas());

        // histórico doenças infecciosas
        tf_hist_doenc_outra.setText(prisioneiro.getOutrasDoencasInfecciosas());
        tf_hist_doenc_obs.setText(prisioneiro.getObservacaoHistoricoDoencasInfecciosas());
        tf_doenca_pele_quais.setText(prisioneiro.getQuaisDoencasPele());
        tf_med_continuo.setText(prisioneiro.getQuaisMedicamentos());
        //tf_tipo_sangue.setText(prisioneiro.get);

        // Saúde mulher
        //tf_papanicolau_ano.setText(saudeMulherAtual.get);
        // Saúde do Homem
        if (saudeHomemAtual != null) {
            tf_exame_prostata_ano.setText(String.valueOf(saudeHomemAtual.getAnoExameProstata()));
            rb_historico_prostata_familiar.setText(saudeHomemAtual.getQualFamiliarCancerProstata());
        } else {
            tf_exame_prostata_ano.setText("");
            rb_historico_prostata_familiar.setText("");
        }
    }

    private void carregarDadosCheckBox(Prisioneiro prisioneiro) {
        // Condições crônicas
        cb_cond_cron_possui_hipertensao.setSelected(prisioneiro.isHipertencao());
        cb_cond_cron_possui_diabetes.setSelected(prisioneiro.isDiabetes());
        cb_cond_cron_possui_HIV.setSelected(prisioneiro.isHiv());
        cb_cond_cron_possui_autoimune.setSelected(prisioneiro.isAutoimune());
        cb_cond_cron_nao_sabe_responder.setSelected(prisioneiro.isNaoSabeResponderCondicoesCronicas());

        // histórico doenças infecciosas
        cb_hist_doencas_infec_sifilis.setSelected(prisioneiro.isSifilis());
        cb_hist_doencas_infec_hpv.setSelected(prisioneiro.isHpv());
        cb_hist_doencas_infec_tuberculose.setSelected(prisioneiro.isTuberculose());
        cb_hist_doencas_infec_hepatite_b.setSelected(prisioneiro.isHepatiteB());
        cb_hist_doencas_infec_hepatite_c.setSelected(prisioneiro.isHepatiteC());
        cb_hist_doencas_infec_nao_sabe_responder.setSelected(prisioneiro.isNaoSabeResponderDoencasInfecciosas());
    }

    
    private void configSaudePanels() {
        
        if (saudeMulherAtual == null) {
            saude_da_mulher_panel.setVisible(false);
        } else {
            saude_da_mulher_panel.setVisible(true);
        }
        
        if (saudeHomemAtual == null) {
            saude_homem_panel.setVisible(false);
        } else {
            saude_homem_panel.setVisible(true);
        }
    }
    
    private void setComboBoxResponsavel() {
        jComboBoxResponsavel.removeAllItems();
        ArrayList<Atendente> atendentes = atendenteDao.getAtendentes();
        for (Atendente atendente : atendentes) {
            jComboBoxResponsavel.addItem(atendente.getNome());
        }
    }

    private void setComboBoxValue(javax.swing.JComboBox<String> comboBox, String value) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).equalsIgnoreCase(value)) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    public void prepararParaNovoPrisioneiro() {
        // Limpa para um novo prisioneiro
        prisioneiroAtual = null;

        atendimento_panel.setVisible(true);

        // Limpa todos os campos
        tf_nome.setText("");
        tf_nome_social.setText("");
        tf_data_nascimento.setText("");
        tf_cpf.setText("");
        tf_idade.setText("");
        tf_etinia.setText("");

        // Reseta os combo boxes
        cb_sexo_biologico.setSelectedIndex(0);
        cb_identidade_genero.setSelectedIndex(0);
        cb_orientacao_sexual.setSelectedIndex(0);
        jComboBox1.setSelectedIndex(0);
    }

    private Prisioneiro coletarDadosFormulario() {
        try {
            Prisioneiro prisioneiro = new Prisioneiro();

            // Se tiver editando um prisioneiro conhecido
            if (prisioneiroAtual != null) {
                prisioneiro.setId(prisioneiroAtual.getId());
            }

            // Coletar dados básicos
            prisioneiro.setNomeCompleto(tf_nome.getText().trim());
            prisioneiro.setNomeSocial(tf_nome_social.getText().trim());
            prisioneiro.setNomeMae(tf_nome_mae.getText().trim());
            prisioneiro.setCpf(tf_cpf.getText().trim());

            // Processar idade
            try {
                if (!tf_idade.getText().trim().isEmpty()) {
                    prisioneiro.setIdade(Integer.parseInt(tf_idade.getText().trim()));
                }
            } catch (NumberFormatException e) {
                prisioneiro.setIdade(0);
            }

            // Processar data de nascimento
            try {
                String dataTexto = tf_data_nascimento.getText().trim();
                java.time.LocalDate dataNascimento;

                // Tenta diferentes formatos de data
                if (dataTexto.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    // Formato YYYY-MM-DD
                    dataNascimento = java.time.LocalDate.parse(dataTexto);
                } else if (dataTexto.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    // Formato DD/MM/YYYY
                    java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    dataNascimento = java.time.LocalDate.parse(dataTexto, formatter);
                } else {
                    System.out.println("Formato de data inválido. Use DD/MM/YYYY ou YYYY-MM-DD");
                    return null;
                }

                prisioneiro.setDataNascimento(java.sql.Date.valueOf(dataNascimento));
            } catch (Exception e) {
                System.out.println("Erro ao parsear data de nascimento: " + e.getMessage());
                return null;
            }

            // Valores das combo boxes com validação
            if (cb_sexo_biologico.getSelectedItem() != null) {
                prisioneiro.setSexo((String) cb_sexo_biologico.getSelectedItem());
            } else {
                prisioneiro.setSexo("Não informado");
            }

            if (cb_identidade_genero.getSelectedItem() != null) {
                prisioneiro.setGenero((String) cb_identidade_genero.getSelectedItem());
            } else {
                prisioneiro.setGenero("Não informado");
            }

            if (cb_orientacao_sexual.getSelectedItem() != null) {
                String orientacao = (String) cb_orientacao_sexual.getSelectedItem();
                if ("Outra".equals(orientacao) && !tf_orientacao_sexual_outra.getText().trim().isEmpty()) {
                    prisioneiro.setOrientacao(tf_orientacao_sexual_outra.getText().trim());
                } else {
                    prisioneiro.setOrientacao(orientacao);
                }
            } else {
                prisioneiro.setOrientacao("Não informado");
            }

            if (jComboBox1.getSelectedItem() != null) {
                prisioneiro.setRaca((String) jComboBox1.getSelectedItem());
            } else {
                prisioneiro.setRaca("Não informado");
            }

            // Etnia
            prisioneiro.setOutrasDoencasCronicas(tf_etinia.getText().trim());

            // Nacionalidade
            if (rb_nacionalidade_brasileira.isSelected()) {
                prisioneiro.setNacionalidade("Brasileira");
            } else if (rb_nacionalidade_naturalizado.isSelected()) {
                prisioneiro.setNacionalidade("Naturalizado");
            } else if (rb_nacionalidade_estrangeiro.isSelected()) {
                prisioneiro.setNacionalidade("Estrangeiro");
                if (!tf_nacionalidade_qual_pais.getText().trim().isEmpty()) {
                    prisioneiro.setNacionalidade("Estrangeiro - " + tf_nacionalidade_qual_pais.getText().trim());
                }
            } else {
                prisioneiro.setNacionalidade("Não informado");
            }

            // Escolaridade
            if (rb_escolaridade_fund_inc.isSelected()) {
                prisioneiro.setEscolaridade("Fundamental Incompleto");
            } else if (rb_escolaridade_fund_com.isSelected()) {
                prisioneiro.setEscolaridade("Fundamental Completo");
            } else if (rb_escolaridade_med_inc.isSelected()) {
                prisioneiro.setEscolaridade("Médio Incompleto");
            } else if (rb_escolaridade_med_com.isSelected()) {
                prisioneiro.setEscolaridade("Médio Completo");
            } else if (rb_escolaridade_sup_inc.isSelected()) {
                prisioneiro.setEscolaridade("Superior Incompleto");
            } else if (rb_escolaridade_sup_com.isSelected()) {
                prisioneiro.setEscolaridade("Superior Completo");
            } else {
                prisioneiro.setEscolaridade("Não informado");
            }

            // Estado Civil (padrão)
            prisioneiro.setEstadoCivil("Solteiro");

            // Dados sociais
            prisioneiro.setPossuiFilhos(rb_poss_filhos_sim.isSelected());
            if (rb_poss_filhos_sim.isSelected() && !tf_poss_filhos_quantos.getText().trim().isEmpty()) {
                try {
                    prisioneiro.setQuantosFilhos(Integer.parseInt(tf_poss_filhos_quantos.getText().trim()));
                } catch (NumberFormatException e) {
                    prisioneiro.setQuantosFilhos(0);
                }
            }

            prisioneiro.setPossuiDependentes(rb_possui_outro_dependente_sim.isSelected());
            if (rb_possui_outro_dependente_sim.isSelected() && !tf_possui_outro_dependente_qts.getText().trim().isEmpty()) {
                try {
                    prisioneiro.setQuantosDependentes(Integer.parseInt(tf_possui_outro_dependente_qts.getText().trim()));
                } catch (NumberFormatException e) {
                    prisioneiro.setQuantosDependentes(0);
                }
            }

            prisioneiro.setOfertarNeeja(rb_ofertar_neeja_sim.isSelected());
            prisioneiro.setOfertarAssistenciaSocial(rb_conversa_assistencia_social_sim.isSelected());

            // Benefício família
            prisioneiro.setBeneficioFamilia(rb_fam_rec_beneficio_sim.isSelected());
            if (rb_fam_rec_beneficio_sim.isSelected()) {
                prisioneiro.setBeneficioEspecificado(tf_fam_rec_beneficio_quais.getText().trim());
            }

            // Deficiência
            if (rb_possui_deficiencia_sim.isSelected()) {
                prisioneiro.setPossuiDeficiencia(true);
                prisioneiro.setQualDeficiencia(tf_deficiencia_quais.getText().trim());
            } else if (rb_possui_deficiencia_nao.isSelected()) {
                prisioneiro.setPossuiDeficiencia(false);
            } else if (rb_possui_deficiencia_nao_sei.isSelected()) {
                prisioneiro.setPossuiDeficiencia(false);
            }

            // Alergias
            if (rb_possui_alergia_sim.isSelected()) {
                prisioneiro.setPossuiAlergias(true);
                prisioneiro.setQuaisAlergias(tf_possui_intolerancia_quais.getText().trim());
            } else if (rb_possui_alergia_nao.isSelected()) {
                prisioneiro.setPossuiAlergias(false);
            } else if (rb_possui_alergia_nao_sei.isSelected()) {
                prisioneiro.setPossuiAlergias(false);
            }

            // Cirurgias
            prisioneiro.setRealizouCirurgias(rb_realizou_cirurgia_sim.isSelected());
            if (rb_realizou_cirurgia_sim.isSelected()) {
                prisioneiro.setQuaisCirurgias(tf_realizou_cirurgia_quais.getText().trim());
            }
            prisioneiro.setNaoSabeResponderCirurgias(rb_realizou_cirurgia_nao_sei.isSelected());

            // Condições crônicas
            prisioneiro.setHipertencao(cb_cond_cron_possui_hipertensao.isSelected());
            prisioneiro.setDiabetes(cb_cond_cron_possui_diabetes.isSelected());
            prisioneiro.setHiv(cb_cond_cron_possui_HIV.isSelected());
            prisioneiro.setAutoimune(cb_cond_cron_possui_autoimune.isSelected());
            prisioneiro.setNaoSabeResponderCondicoesCronicas(cb_cond_cron_nao_sabe_responder.isSelected());

            // Histórico doenças infecciosas
            prisioneiro.setSifilis(cb_hist_doencas_infec_sifilis.isSelected());
            prisioneiro.setHpv(cb_hist_doencas_infec_hpv.isSelected());
            prisioneiro.setTuberculose(cb_hist_doencas_infec_tuberculose.isSelected());
            prisioneiro.setHepatiteB(cb_hist_doencas_infec_hepatite_b.isSelected());
            prisioneiro.setHepatiteC(cb_hist_doencas_infec_hepatite_c.isSelected());
            prisioneiro.setNaoSabeResponderDoencasInfecciosas(cb_hist_doencas_infec_nao_sabe_responder.isSelected());

            // Doença de pele
            prisioneiro.setDoencaPele(rb_doenca_pele_sim.isSelected());
            if (rb_doenca_pele_sim.isSelected()) {
                prisioneiro.setQuaisDoencasPele(tf_doenca_pele_quais.getText().trim());
            }

            // Medicamentos contínuos
            prisioneiro.setMedicamentosContinuos(rb_usa_med_continuo_sim.isSelected() ? "Sim" : "Não");
            if (rb_usa_med_continuo_sim.isSelected()) {
                prisioneiro.setQuaisMedicamentos(tf_med_continuo.getText().trim());
            }

            // Tipo sanguíneo
            if (rb_tipo_sangue_ap.isSelected()) {
                prisioneiro.setTipoSanguineo("A+");
            } else if (rb_tipo_sangue_am.isSelected()) {
                prisioneiro.setTipoSanguineo("A-");
            } else if (rb_tipo_sangue_bp.isSelected()) {
                prisioneiro.setTipoSanguineo("B+");
            } else if (rb_tipo_sangue_bm.isSelected()) {
                prisioneiro.setTipoSanguineo("B-");
            } else if (rb_tipo_sangue_abp.isSelected()) {
                prisioneiro.setTipoSanguineo("AB+");
            } else if (rb_tipo_sangue_abm.isSelected()) {
                prisioneiro.setTipoSanguineo("AB-");
            } else if (rb_tipo_sangue_op.isSelected()) {
                prisioneiro.setTipoSanguineo("O+");
            } else if (rb_tipo_sangue_om.isSelected()) {
                prisioneiro.setTipoSanguineo("O-");
            } else if (rb_tipo_sangue_nao_sabe.isSelected()) {
                prisioneiro.setTipoSanguineo("Não sabe");
            } else {
                prisioneiro.setTipoSanguineo("Não informado");
            }

            // Saúde mental
            prisioneiro.setVinculoCaps(rb_vinculo_caps_sim.isSelected() ? "Sim" : "Não");
            if (rb_vinculo_caps_sim.isSelected()) {
                prisioneiro.setNomeMunicioCaps(tf_vinculo_caps_municipio.getText().trim());
            }

            prisioneiro.setAnsiedade(cb_possui_ansiedade.isSelected());
            prisioneiro.setDepressao(cb_possui_depressao.isSelected());
            prisioneiro.setBipolaridade(cb_possui_bipolaridade.isSelected());
            prisioneiro.setEsquizofrenia(cb_possui_esquisofrenia.isSelected());
            prisioneiro.setAutismo(cb_possui_autismo.isSelected());
            prisioneiro.setNaoSabeResponderSaudeMental(cb_transt_mentais_n_sabe_resp.isSelected());
            prisioneiro.setOutraSaudeMental(tf_possui_trasntorno_mental_outro.getText().trim());

            // Medicamento controlado
            if (rb_usa_med_controlado_sim.isSelected()) {
                prisioneiro.setMedicamentoControlado("Sim");
                prisioneiro.setQualMedicamentoControlado(tf_usa_med_controlado_qual.getText().trim());
            } else if (rb_usa_med_controlado_nao.isSelected()) {
                prisioneiro.setMedicamentoControlado("Não");
            } else if (rb_usa_med_controlado_nao_sabe.isSelected()) {
                prisioneiro.setMedicamentoControlado("Não sabe");
            }

            // Acompanhamento mental no momento da prisão
            prisioneiro.setAcompanhamentoMentalMomentoPrisao(rb_acompanhamento_saude_mental_no_momento_da_prisao_sim.isSelected());
            if (rb_acompanhamento_saude_mental_no_momento_da_prisao_sim.isSelected()) {
                prisioneiro.setMotivoAcompanhamentoMental(tf_acompanhamento_saude_mental_no_momento_da_prisao_qual.getText().trim());
            }

            // Uso de substâncias
            prisioneiro.setAlcool(cb_faz_uso_de_alccol.isSelected());
            prisioneiro.setCigarro(cb_faz_uso_de_cigarro.isSelected());
            prisioneiro.setMaconha(cb_faz_uso_de_maconha.isSelected());
            prisioneiro.setCrack(cb_faz_uso_de_crack.isSelected());
            prisioneiro.setCocaina(cb_faz_uso_de_cocaina.isSelected());
            prisioneiro.setAnfetaminas(cb_faz_uso_de_anfetaminas.isSelected());
            prisioneiro.setDrogas(cb_faz_uso_de_drogas_k.isSelected());
            prisioneiro.setOutrasDrogas(tf_faz_uso_de_outras.getText().trim());

            // Tratamento reabilitação
            prisioneiro.setTratamentoReabilitacao(rb_ja_realizou_trat_para_cessar_reduzir_uso_sim.isSelected());
            if (rb_ja_realizou_trat_para_cessar_reduzir_uso_sim.isSelected()) {
                prisioneiro.setTratamentoQualSubstancia(tf_ja_realizou_trat_para_cessar_reduzir_uso_qual.getText().trim());
            }

            prisioneiro.setQuerReabilitacao(cb_gostaria_realizar_trat_para_cessar_reduzir_uso.isSelected());
            if (cb_gostaria_realizar_trat_para_cessar_reduzir_uso.isSelected()) {
                prisioneiro.setReabilitacaoQualSubstancia(tf_gostaria_realizar_trat_para_cessar_reduzir_uso_qual_subs.getText().trim());
            }

            // Vacinação
            if (rb_recebeu_vacina_covid_sim.isSelected()) {
                prisioneiro.setVacinaCovid(true);
            } else if (rb_recebeu_vacina_covid_nao.isSelected()) {
                prisioneiro.setVacinaCovid(false);
            } else if (rb_recebeu_vacina_covid_nao_sabe.isSelected()) {
                prisioneiro.setVacinaCovid(false);
            }

            if (rb_recebeu_vacina_influenza_sim.isSelected()) {
                prisioneiro.setVacinaInfluenza(true);
            } else if (rb_recebeu_vacina_influenza_nao.isSelected()) {
                prisioneiro.setVacinaInfluenza(false);
            } else if (rb_recebeu_vacina_influenza_nao_sabe.isSelected()) {
                prisioneiro.setVacinaInfluenza(false);
            }

            if (rb_recebeu_vacina_tetano_sim.isSelected()) {
                prisioneiro.setVacinaTetano(true);
            } else if (rb_recebeu_vacina_tetano_nao.isSelected()) {
                prisioneiro.setVacinaTetano(false);
            } else if (rb_recebeu_vacina_tetano_nao_sabe.isSelected()) {
                prisioneiro.setVacinaTetano(false);
            }

            if (rb_recebeu_vacina_hepatite_b_sim.isSelected()) {
                prisioneiro.setVacinaHepatite(true);
            } else if (rb_recebeu_vacina_hepatite_b_nao.isSelected()) {
                prisioneiro.setVacinaHepatite(false);
            } else if (rb_recebeu_vacina_hepatite_b_nao_sabe.isSelected()) {
                prisioneiro.setVacinaHepatite(false);
            }

            // Ofertar vacinas
            prisioneiro.setOfertarVacinas(true);
            prisioneiro.setOfertarVacinaFebreAmarela(cb_ofertar_vacina_febre_amarela.isSelected());
            prisioneiro.setOfertarVacinaHepatiteB(cb_ofertar_vacina_hepatite_b.isSelected());
            prisioneiro.setOfertarVacinaCovid19(cb_ofertar_vacina_covid.isSelected());
            prisioneiro.setOfertarVacinaInfluenza(cb_ofertar_vacina_influenza.isSelected());
            prisioneiro.setOfertarVacinaDuplaAdulto(cb_ofertar_vacina_dupla_adulto.isSelected());
            prisioneiro.setOfertarVacinaTripliceViral(cb_ofertar_vaciana_triplice_viral.isSelected());
            prisioneiro.setOutraVacina(tf_ofertar_vacina_outra.getText().trim());
            prisioneiro.setOfertarCarteiraVacinacao(rb_ofertar_copia_carteira_vacinacao_sim.isSelected());

            // Saúde da Mulher - dados serão salvos em tabelas separadas
            // prisioneiro.setOfertarContinuidadeContraceptivo(rb_ofertar_continuidade_contraceptivo_sim.isSelected());
            // prisioneiro.setConsultarExamePreventivo(rb_consultar_exame_preventivo_sim.isSelected());
            // prisioneiro.setEncaminharPreNatal(rb_enc_pre_natal_sim.isSelected());
            // prisioneiro.setRealizouPapanicolau(rb_papanicolau_sim.isSelected());
            if (rb_papanicolau_sim.isSelected() && !tf_papanicolau_ano.getText().trim().isEmpty()) {
                prisioneiro.setObservacaoCondicoesCronicas(tf_papanicolau_ano.getText().trim());
            }

            // Saúde do Homem - dados serão salvos em tabelas separadas
            // prisioneiro.setExameProstata(rb_exame_prostata_sim.isSelected());
            if (rb_exame_prostata_sim.isSelected() && !tf_exame_prostata_ano.getText().trim().isEmpty()) {
                prisioneiro.setObservacaoHistoricoDoencasInfecciosas(tf_exame_prostata_ano.getText().trim());
            }
            // prisioneiro.setHistoricoProstataFamilia(rb_historico_prostata_sim.isSelected());
            // prisioneiro.setRealizouVasectomia(rb_realizou_vasectomia_sim.isSelected());
            // prisioneiro.setParceiraGestante(rb_parceira_gestante_sim.isSelected());
            // prisioneiro.setEstaParticipandoPreNatal(rb_esta_participando_pre_natal_sim.isSelected());
            // prisioneiro.setEncaminharRealizVasectomia(rb_encaminhar_realiz_vasectomia_sim.isSelected());
            // prisioneiro.setEncaminharPreNatalParceiro(rb_encaminhar_pre_natal_parceiro_sim.isSelected());

            // Gestação
            // prisioneiro.setGestacaoNoMomento(rb_gestacao_no_momvento_sim.isSelected());
            // Transferência
            prisioneiro.setEncaminhamentosFinais(tf_transferencia.getText().trim());

            // Responsável pelo atendimento
            if (jComboBoxResponsavel.getSelectedItem() != null) {
                prisioneiro.setOutrasDoencasInfecciosas(jComboBoxResponsavel.getSelectedItem().toString());
            }

            return prisioneiro;
        } catch (Exception e) {
            System.out.println("Erro ao coletar dados do formulário: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private boolean validarFormulario() {
        // Validar nome
        if (tf_nome.getText().trim().isEmpty()) {
            System.out.println("Nome é obrigatório");
            return false;
        }

        // Validar CPF
        if (tf_cpf.getText().trim().isEmpty()) {
            System.out.println("CPF é obrigatório");
            return false;
        }

        // Validar data de nascimento
        if (tf_data_nascimento.getText().trim().isEmpty()) {
            System.out.println("Data de nascimento é obrigatória");
            return false;
        }

        // Validar formato da data
        try {
            String dataTexto = tf_data_nascimento.getText().trim();
            if (!dataTexto.matches("\\d{4}-\\d{2}-\\d{2}")
                    && !dataTexto.matches("\\d{2}/\\d{2}/\\d{4}")) {
                System.out.println("Formato de data inválido. Use DD/MM/YYYY ou YYYY-MM-DD");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Erro ao validar formato da data");
            return false;
        }

        // Validar se um atendente foi selecionado
        if (jComboBoxResponsavel.getSelectedItem() == null
                || jComboBoxResponsavel.getSelectedItem().toString().trim().isEmpty()) {
            System.out.println("Atendente responsável é obrigatório");
            return false;
        }

        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        bg_transferencia = new javax.swing.ButtonGroup();
        bg_possui_deficiencia = new javax.swing.ButtonGroup();
        bg_nacionalidade = new javax.swing.ButtonGroup();
        bg_possui_intolerancia = new javax.swing.ButtonGroup();
        bg_realizou_cirurgia = new javax.swing.ButtonGroup();
        bg_hipertencao = new javax.swing.ButtonGroup();
        bg_diabetes = new javax.swing.ButtonGroup();
        bg_possui_denca_de_pele = new javax.swing.ButtonGroup();
        bg_hiv = new javax.swing.ButtonGroup();
        bg_autoimune = new javax.swing.ButtonGroup();
        bg_sifilis = new javax.swing.ButtonGroup();
        bg_hpv = new javax.swing.ButtonGroup();
        bg_tuberculose = new javax.swing.ButtonGroup();
        bg_hepatite_b = new javax.swing.ButtonGroup();
        bg_med_continuo = new javax.swing.ButtonGroup();
        bg_tipo_sangue = new javax.swing.ButtonGroup();
        bg_escolaridade = new javax.swing.ButtonGroup();
        bg_fam_rec_beneficio = new javax.swing.ButtonGroup();
        bg_possui_filhos = new javax.swing.ButtonGroup();
        bg_possuio_outro_dependente = new javax.swing.ButtonGroup();
        bg_neeja = new javax.swing.ButtonGroup();
        bg_conversa_assistencia_social = new javax.swing.ButtonGroup();
        bg_gestacao_no_momento = new javax.swing.ButtonGroup();
        bg_tipo_met_anticoncepcional = new javax.swing.ButtonGroup();
        bg_realizou_papanicolau = new javax.swing.ButtonGroup();
        bg_ofertar_continuidade_contraceptivo = new javax.swing.ButtonGroup();
        bg_consultar_exame_preventivo = new javax.swing.ButtonGroup();
        bg_enc_pre_natal = new javax.swing.ButtonGroup();
        bg_exame_prostata = new javax.swing.ButtonGroup();
        bg_historico_prostata_familia = new javax.swing.ButtonGroup();
        bg_realizou_vasectomia = new javax.swing.ButtonGroup();
        bg_parceira_gestante = new javax.swing.ButtonGroup();
        bg_esta_participando_pre_natal = new javax.swing.ButtonGroup();
        bg_encaminhar_realiz_vasectomia = new javax.swing.ButtonGroup();
        bg_encaminhar_pre_natal_do_parceiro = new javax.swing.ButtonGroup();
        bg_recebeu_vacina_covid = new javax.swing.ButtonGroup();
        bg_recebeu_vacina_influenza = new javax.swing.ButtonGroup();
        bg_recebeu_vacina_tetano = new javax.swing.ButtonGroup();
        bg_recebeu_vacina_hepatite_b = new javax.swing.ButtonGroup();
        cb_ofertar_vaciana_hepatite_b1 = new javax.swing.JCheckBox();
        bg_ofertar_copia_carteira_vacinacao = new javax.swing.ButtonGroup();
        bg_vinculo_caps = new javax.swing.ButtonGroup();
        bg_usa_medicamento_controlado = new javax.swing.ButtonGroup();
        bg_estava_acomp_saude_mental_momento_prisao = new javax.swing.ButtonGroup();
        bg_ja_realizou_trat_para_cessar_reduzir_uso = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        btn_cancelar = new javax.swing.JButton();
        btn_salvar = new javax.swing.JButton();
        atendimento_panel = new javax.swing.JPanel();
        rb_transferencia_sim = new javax.swing.JRadioButton();
        rb_transferencia_nao = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tf_transferencia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxResponsavel = new javax.swing.JComboBox<>();
        jLabelData = new javax.swing.JLabel();
        identificacao_panel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        tf_nome = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tf_nome_social = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tf_data_nascimento = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tf_cpf = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tf_idade = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        tf_etinia = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        cb_sexo_biologico = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        cb_identidade_genero = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        cb_orientacao_sexual = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        rb_nacionalidade_brasileira = new javax.swing.JRadioButton();
        rb_nacionalidade_naturalizado = new javax.swing.JRadioButton();
        rb_nacionalidade_estrangeiro = new javax.swing.JRadioButton();
        tf_nacionalidade_qual_pais = new javax.swing.JTextField();
        tf_nome_mae = new javax.swing.JTextField();
        tf_orientacao_sexual_outra = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        condicoes_de_saude = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        rb_possui_deficiencia_sim = new javax.swing.JRadioButton();
        rb_possui_deficiencia_nao = new javax.swing.JRadioButton();
        tf_deficiencia_quais = new javax.swing.JTextField();
        rb_possui_deficiencia_nao_sei = new javax.swing.JRadioButton();
        jLabel22 = new javax.swing.JLabel();
        rb_possui_alergia_nao_sei = new javax.swing.JRadioButton();
        rb_possui_alergia_nao = new javax.swing.JRadioButton();
        rb_possui_alergia_sim = new javax.swing.JRadioButton();
        tf_possui_intolerancia_quais = new javax.swing.JTextField();
        tf_realizou_cirurgia_quais = new javax.swing.JTextField();
        rb_realizou_cirurgia_nao_sei = new javax.swing.JRadioButton();
        rb_realizou_cirurgia_nao = new javax.swing.JRadioButton();
        rb_realizou_cirurgia_sim = new javax.swing.JRadioButton();
        jLabel23 = new javax.swing.JLabel();
        tf_cond_croni_quais = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        rb_doenca_pele_sim = new javax.swing.JRadioButton();
        rb_doenca_pele_nao = new javax.swing.JRadioButton();
        jLabel34 = new javax.swing.JLabel();
        tf_cond_croni_outra = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        tf_doenca_pele_quais = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        tf_hist_doenc_obs = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        tf_hist_doenc_outra = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        tf_med_continuo = new javax.swing.JTextField();
        rb_usa_med_continuo_nao = new javax.swing.JRadioButton();
        rb_usa_med_continuo_sim = new javax.swing.JRadioButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        rb_tipo_sangue_ap = new javax.swing.JRadioButton();
        rb_tipo_sangue_am = new javax.swing.JRadioButton();
        rb_tipo_sangue_bp = new javax.swing.JRadioButton();
        rb_tipo_sangue_bm = new javax.swing.JRadioButton();
        rb_tipo_sangue_op = new javax.swing.JRadioButton();
        rb_tipo_sangue_om = new javax.swing.JRadioButton();
        rb_tipo_sangue_abp = new javax.swing.JRadioButton();
        rb_tipo_sangue_abm = new javax.swing.JRadioButton();
        rb_tipo_sangue_nao_sabe = new javax.swing.JRadioButton();
        jLabel47 = new javax.swing.JLabel();
        tf_tipo_sangue = new javax.swing.JTextField();
        cb_cond_cron_possui_diabetes = new javax.swing.JCheckBox();
        cb_cond_cron_possui_HIV = new javax.swing.JCheckBox();
        cb_cond_cron_possui_autoimune = new javax.swing.JCheckBox();
        cb_cond_cron_possui_hipertensao = new javax.swing.JCheckBox();
        cb_cond_cron_nao_sabe_responder = new javax.swing.JCheckBox();
        cb_hist_doencas_infec_sifilis = new javax.swing.JCheckBox();
        cb_hist_doencas_infec_hpv = new javax.swing.JCheckBox();
        cb_hist_doencas_infec_tuberculose = new javax.swing.JCheckBox();
        cb_hist_doencas_infec_hepatite_b = new javax.swing.JCheckBox();
        cb_hist_doencas_infec_hepatite_c = new javax.swing.JCheckBox();
        cb_hist_doencas_infec_nao_sabe_responder = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        rb_escolaridade_fund_inc = new javax.swing.JRadioButton();
        rb_escolaridade_fund_com = new javax.swing.JRadioButton();
        rb_escolaridade_med_inc = new javax.swing.JRadioButton();
        rb_escolaridade_med_com = new javax.swing.JRadioButton();
        rb_escolaridade_sup_inc = new javax.swing.JRadioButton();
        rb_escolaridade_sup_com = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel51 = new javax.swing.JLabel();
        rb_fam_rec_beneficio_sim = new javax.swing.JRadioButton();
        rb_fam_rec_beneficio_nao = new javax.swing.JRadioButton();
        jLabel52 = new javax.swing.JLabel();
        tf_fam_rec_beneficio_quais = new javax.swing.JTextField();
        tf_poss_filhos_quantos = new javax.swing.JTextField();
        rb_poss_filhos_nao = new javax.swing.JRadioButton();
        rb_poss_filhos_sim = new javax.swing.JRadioButton();
        jLabel53 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        tf_idade_filhos = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        rb_possui_outro_dependente_sim = new javax.swing.JRadioButton();
        rb_possui_outro_dependente_nao = new javax.swing.JRadioButton();
        tf_possui_outro_dependente_qts = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        rb_ofertar_neeja_nao = new javax.swing.JRadioButton();
        rb_ofertar_neeja_sim = new javax.swing.JRadioButton();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        rb_conversa_assistencia_social_sim = new javax.swing.JRadioButton();
        rb_conversa_assistencia_social_nao = new javax.swing.JRadioButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        saude_da_mulher_panel = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        rb_gestacao_no_momvento_sim = new javax.swing.JRadioButton();
        rb_gestacao_no_momvento_nao = new javax.swing.JRadioButton();
        rb_gestacao_no_momvento_nao_sabe = new javax.swing.JRadioButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel62 = new javax.swing.JLabel();
        rb_escolaridade_fund_com1 = new javax.swing.JRadioButton();
        rb_escolaridade_med_inc1 = new javax.swing.JRadioButton();
        rb_escolaridade_med_com1 = new javax.swing.JRadioButton();
        rb_escolaridade_sup_inc1 = new javax.swing.JRadioButton();
        rb_escolaridade_sup_com1 = new javax.swing.JRadioButton();
        rb_gestacao_no_momvento_nao1 = new javax.swing.JRadioButton();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel64 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel65 = new javax.swing.JLabel();
        rb_ofertar_continuidade_contraceptivo_sim = new javax.swing.JRadioButton();
        rb_ofertar_continuidade_contraceptivo_nao = new javax.swing.JRadioButton();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        rb_consultar_exame_preventivo_sim = new javax.swing.JRadioButton();
        rb_consultar_exame_preventivo_nao = new javax.swing.JRadioButton();
        jLabel68 = new javax.swing.JLabel();
        rb_enc_pre_natal_sim = new javax.swing.JRadioButton();
        rb_enc_pre_natal_nao = new javax.swing.JRadioButton();
        rb_papanicolau_nao = new javax.swing.JRadioButton();
        rb_papanicolau_sim = new javax.swing.JRadioButton();
        jLabel104 = new javax.swing.JLabel();
        tf_papanicolau_ano = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        saude_homem_panel = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        rb_exame_prostata_nao = new javax.swing.JRadioButton();
        rb_exame_prostata_sim = new javax.swing.JRadioButton();
        jLabel63 = new javax.swing.JLabel();
        tf_exame_prostata_ano = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        rb_historico_prostata_nao = new javax.swing.JRadioButton();
        rb_historico_prostata_sim = new javax.swing.JRadioButton();
        jLabel72 = new javax.swing.JLabel();
        rb_historico_prostata_familiar = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        rb_realizou_vasectomia_nao = new javax.swing.JRadioButton();
        rb_realizou_vasectomia_sim = new javax.swing.JRadioButton();
        rb_parceira_gestante_sim = new javax.swing.JRadioButton();
        rb_parceira_gestante_nao = new javax.swing.JRadioButton();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        rb_esta_participando_pre_natal_nao = new javax.swing.JRadioButton();
        rb_esta_participando_pre_natal_sim = new javax.swing.JRadioButton();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        rb_encaminhar_realiz_vasectomia_nao = new javax.swing.JRadioButton();
        rb_encaminhar_realiz_vasectomia_sim = new javax.swing.JRadioButton();
        jLabel78 = new javax.swing.JLabel();
        rb_encaminhar_pre_natal_parceiro_nao = new javax.swing.JRadioButton();
        rb_encaminhar_pre_natal_parceiro_sim = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel79 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel88 = new javax.swing.JLabel();
        rb_vinculo_caps_nao = new javax.swing.JRadioButton();
        rb_vinculo_caps_sim = new javax.swing.JRadioButton();
        jLabel89 = new javax.swing.JLabel();
        tf_vinculo_caps_nome = new javax.swing.JTextField();
        tf_vinculo_caps_municipio = new javax.swing.JTextField();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel92 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        cb_possui_esquisofrenia = new javax.swing.JCheckBox();
        cb_possui_bipolaridade = new javax.swing.JCheckBox();
        cb_possui_depressao = new javax.swing.JCheckBox();
        cb_possui_ansiedade = new javax.swing.JCheckBox();
        cb_possui_autismo = new javax.swing.JCheckBox();
        tf_possui_trasntorno_mental_outro = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel93 = new javax.swing.JLabel();
        tf_usa_med_controlado_qual = new javax.swing.JTextField();
        cb_transt_mentais_n_sabe_resp = new javax.swing.JCheckBox();
        rb_usa_med_controlado_nao = new javax.swing.JRadioButton();
        rb_usa_med_controlado_sim = new javax.swing.JRadioButton();
        jLabel94 = new javax.swing.JLabel();
        rb_usa_med_controlado_nao_sabe = new javax.swing.JRadioButton();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel95 = new javax.swing.JLabel();
        rb_acompanhamento_saude_mental_no_momento_da_prisao_nao = new javax.swing.JRadioButton();
        rb_acompanhamento_saude_mental_no_momento_da_prisao_sim = new javax.swing.JRadioButton();
        jLabel96 = new javax.swing.JLabel();
        tf_acompanhamento_saude_mental_no_momento_da_prisao_qual = new javax.swing.JTextField();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel97 = new javax.swing.JLabel();
        cb_faz_uso_de_cigarro = new javax.swing.JCheckBox();
        cb_faz_uso_de_alccol = new javax.swing.JCheckBox();
        cb_faz_uso_de_maconha = new javax.swing.JCheckBox();
        cb_faz_uso_de_cocaina = new javax.swing.JCheckBox();
        cb_faz_uso_de_crack = new javax.swing.JCheckBox();
        cb_faz_uso_de_drogas_k = new javax.swing.JCheckBox();
        cb_faz_uso_de_anfetaminas = new javax.swing.JCheckBox();
        jLabel99 = new javax.swing.JLabel();
        tf_faz_uso_de_outras = new javax.swing.JTextField();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel100 = new javax.swing.JLabel();
        rb_ja_realizou_trat_para_cessar_reduzir_uso_nao = new javax.swing.JRadioButton();
        rb_ja_realizou_trat_para_cessar_reduzir_uso_sim = new javax.swing.JRadioButton();
        jLabel101 = new javax.swing.JLabel();
        tf_ja_realizou_trat_para_cessar_reduzir_uso_qual = new javax.swing.JTextField();
        cb_gostaria_realizar_trat_para_cessar_reduzir_uso = new javax.swing.JCheckBox();
        tf_gostaria_realizar_trat_para_cessar_reduzir_uso_qual_subs = new javax.swing.JTextField();
        jLabel102 = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel103 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        rb_recebeu_vacina_covid_nao_sabe = new javax.swing.JRadioButton();
        rb_recebeu_vacina_covid_nao = new javax.swing.JRadioButton();
        rb_recebeu_vacina_covid_sim = new javax.swing.JRadioButton();
        jLabel81 = new javax.swing.JLabel();
        rb_recebeu_vacina_influenza_nao_sabe = new javax.swing.JRadioButton();
        rb_recebeu_vacina_influenza_nao = new javax.swing.JRadioButton();
        rb_recebeu_vacina_influenza_sim = new javax.swing.JRadioButton();
        jLabel82 = new javax.swing.JLabel();
        rb_recebeu_vacina_tetano_nao_sabe = new javax.swing.JRadioButton();
        rb_recebeu_vacina_tetano_nao = new javax.swing.JRadioButton();
        rb_recebeu_vacina_tetano_sim = new javax.swing.JRadioButton();
        jLabel83 = new javax.swing.JLabel();
        rb_recebeu_vacina_hepatite_b_nao_sabe = new javax.swing.JRadioButton();
        rb_recebeu_vacina_hepatite_b_nao = new javax.swing.JRadioButton();
        rb_recebeu_vacina_hepatite_b_sim = new javax.swing.JRadioButton();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        cb_ofertar_vacina_hepatite_b = new javax.swing.JCheckBox();
        cb_ofertar_vaciana_nao = new javax.swing.JCheckBox();
        cb_ofertar_vacina_covid = new javax.swing.JCheckBox();
        cb_ofertar_vacina_influenza = new javax.swing.JCheckBox();
        cb_ofertar_vacina_febre_amarela = new javax.swing.JCheckBox();
        cb_ofertar_vacina_dupla_adulto = new javax.swing.JCheckBox();
        cb_ofertar_vaciana_triplice_viral = new javax.swing.JCheckBox();
        tf_ofertar_vacina_outra = new javax.swing.JTextField();
        jLabel87 = new javax.swing.JLabel();
        rb_ofertar_copia_carteira_vacinacao_sim = new javax.swing.JRadioButton();
        rb_ofertar_copia_carteira_vacinacao_nao = new javax.swing.JRadioButton();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel31 = new javax.swing.JLabel();

        jLabel1.setText("Responsável:");

        jLabel2.setText("Data:");

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setText("jLabel5");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        cb_ofertar_vaciana_hepatite_b1.setText("Hepatite B");
        cb_ofertar_vaciana_hepatite_b1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_ofertar_vaciana_hepatite_b1ActionPerformed(evt);
            }
        });

        setBackground(new java.awt.Color(204, 204, 204));

        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        btn_salvar.setText("Salvar");
        btn_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvarActionPerformed(evt);
            }
        });

        atendimento_panel.setForeground(new java.awt.Color(255, 255, 255));

        bg_transferencia.add(rb_transferencia_sim);
        rb_transferencia_sim.setText("Sim");

        bg_transferencia.add(rb_transferencia_nao);
        rb_transferencia_nao.setText("Não");

        jLabel3.setText("Responsável:");

        jLabel4.setText("Data:");

        tf_transferencia.setEditable(false);
        tf_transferencia.setToolTipText("Local");
        tf_transferencia.setEnabled(false);

        jLabel6.setText("Transferência");

        jComboBoxResponsavel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxResponsavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxResponsavelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout atendimento_panelLayout = new javax.swing.GroupLayout(atendimento_panel);
        atendimento_panel.setLayout(atendimento_panelLayout);
        atendimento_panelLayout.setHorizontalGroup(
            atendimento_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, atendimento_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(atendimento_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(atendimento_panelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_transferencia_sim)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_transferencia_nao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_transferencia))
                    .addGroup(atendimento_panelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelData)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(atendimento_panelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxResponsavel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        atendimento_panelLayout.setVerticalGroup(
            atendimento_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(atendimento_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(atendimento_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(atendimento_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabelData))
                .addGap(18, 18, 18)
                .addGroup(atendimento_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_transferencia_sim)
                    .addComponent(rb_transferencia_nao)
                    .addComponent(tf_transferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jLabel7.setText("Nome Completo:");

        tf_nome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_nomeActionPerformed(evt);
            }
        });

        jLabel8.setText("Nome Social:");

        jLabel9.setText("Data de Nascimento:");

        tf_data_nascimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_data_nascimentoActionPerformed(evt);
            }
        });

        jLabel10.setText("CPF:");

        jLabel11.setText("Idade:");

        tf_idade.setColumns(3);

        jLabel12.setText("Raça/Cor:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Branco", "Preto", "Pardo", "Amerelo", "Indígena" }));

        jLabel13.setText("Etnia:");

        jLabel14.setText("Sexo Biológico:");

        cb_sexo_biologico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Feminino", "Masculino", "Intersexo", "Não desejo informar" }));

        jLabel15.setText("Identidade de Gênero:");

        cb_identidade_genero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Homem", "Mulher", "Homem Trans", "Mulher Trans / Travesti", "Não binário", "Não desejo informar" }));

        jLabel16.setText("Orientação Sexual:");

        cb_orientacao_sexual.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Heterossexual", "Homossexual (gay/lésbica)", "Bissexual", "Outra", "Não desejo informar" }));

        jLabel20.setText("Nacionalidade");

        jLabel21.setText("Nome da mãe:");

        bg_nacionalidade.add(rb_nacionalidade_brasileira);
        rb_nacionalidade_brasileira.setText("Brasileira");

        bg_nacionalidade.add(rb_nacionalidade_naturalizado);
        rb_nacionalidade_naturalizado.setText("Naturalizado");
        rb_nacionalidade_naturalizado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_nacionalidade_naturalizadoActionPerformed(evt);
            }
        });

        bg_nacionalidade.add(rb_nacionalidade_estrangeiro);
        rb_nacionalidade_estrangeiro.setText("Estrangeiro");

        tf_nacionalidade_qual_pais.setEditable(false);
        tf_nacionalidade_qual_pais.setEnabled(false);

        tf_orientacao_sexual_outra.setEditable(false);
        tf_orientacao_sexual_outra.setEnabled(false);

        javax.swing.GroupLayout identificacao_panelLayout = new javax.swing.GroupLayout(identificacao_panel);
        identificacao_panel.setLayout(identificacao_panelLayout);
        identificacao_panelLayout.setHorizontalGroup(
            identificacao_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(identificacao_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(identificacao_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(identificacao_panelLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_nome_social))
                    .addGroup(identificacao_panelLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_nome))
                    .addGroup(identificacao_panelLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_etinia))
                    .addGroup(identificacao_panelLayout.createSequentialGroup()
                        .addGroup(identificacao_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(identificacao_panelLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tf_nome_mae, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(identificacao_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(identificacao_panelLayout.createSequentialGroup()
                                    .addComponent(jLabel20)
                                    .addGap(18, 18, 18)
                                    .addComponent(rb_nacionalidade_brasileira)
                                    .addGap(18, 18, 18)
                                    .addComponent(rb_nacionalidade_naturalizado)
                                    .addGap(18, 18, 18)
                                    .addComponent(rb_nacionalidade_estrangeiro))
                                .addComponent(tf_nacionalidade_qual_pais))
                            .addGroup(identificacao_panelLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tf_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(identificacao_panelLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tf_data_nascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tf_idade, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(identificacao_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(identificacao_panelLayout.createSequentialGroup()
                                    .addComponent(jLabel16)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cb_orientacao_sexual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tf_orientacao_sexual_outra))
                                .addGroup(identificacao_panelLayout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cb_sexo_biologico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cb_identidade_genero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        identificacao_panelLayout.setVerticalGroup(
            identificacao_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(identificacao_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(identificacao_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(identificacao_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tf_nome_social, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(identificacao_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(tf_data_nascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(tf_idade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(identificacao_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tf_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(identificacao_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(rb_nacionalidade_brasileira)
                    .addComponent(rb_nacionalidade_naturalizado)
                    .addComponent(rb_nacionalidade_estrangeiro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tf_nacionalidade_qual_pais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(identificacao_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(tf_nome_mae, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(identificacao_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(tf_etinia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(identificacao_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(identificacao_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cb_sexo_biologico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)
                        .addComponent(cb_identidade_genero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(identificacao_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(cb_orientacao_sexual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_orientacao_sexual_outra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("IDENTIFICAÇÃO");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("CONDIÇÕES DE SAÚDE");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addContainerGap())
        );

        jLabel19.setText("Possui alguma deficiencia:");

        bg_possui_deficiencia.add(rb_possui_deficiencia_sim);
        rb_possui_deficiencia_sim.setText("Sim");

        bg_possui_deficiencia.add(rb_possui_deficiencia_nao);
        rb_possui_deficiencia_nao.setText("Não");

        tf_deficiencia_quais.setEditable(false);
        tf_deficiencia_quais.setEnabled(false);

        bg_possui_deficiencia.add(rb_possui_deficiencia_nao_sei);
        rb_possui_deficiencia_nao_sei.setText("Não sabe responder");

        jLabel22.setText("Possui alergias ou intolerância alimentar:");

        bg_possui_intolerancia.add(rb_possui_alergia_nao_sei);
        rb_possui_alergia_nao_sei.setText("Não sabe responder");

        bg_possui_intolerancia.add(rb_possui_alergia_nao);
        rb_possui_alergia_nao.setText("Não");

        bg_possui_intolerancia.add(rb_possui_alergia_sim);
        rb_possui_alergia_sim.setText("Sim");

        tf_possui_intolerancia_quais.setEditable(false);
        tf_possui_intolerancia_quais.setEnabled(false);

        tf_realizou_cirurgia_quais.setEditable(false);
        tf_realizou_cirurgia_quais.setEnabled(false);

        bg_realizou_cirurgia.add(rb_realizou_cirurgia_nao_sei);
        rb_realizou_cirurgia_nao_sei.setText("Não sabe responder");

        bg_realizou_cirurgia.add(rb_realizou_cirurgia_nao);
        rb_realizou_cirurgia_nao.setText("Não");

        bg_realizou_cirurgia.add(rb_realizou_cirurgia_sim);
        rb_realizou_cirurgia_sim.setText("Sim");

        jLabel23.setText("Realizou cirurgias:");

        tf_cond_croni_quais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_cond_croni_quaisActionPerformed(evt);
            }
        });

        jLabel24.setBackground(new java.awt.Color(204, 204, 204));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Condições Crônicas:");
        jLabel24.setOpaque(true);

        jLabel25.setBackground(new java.awt.Color(204, 204, 204));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Histórico doenças infecciosas:");
        jLabel25.setOpaque(true);

        jLabel26.setText("Possui doença de pele:");

        bg_possui_denca_de_pele.add(rb_doenca_pele_sim);
        rb_doenca_pele_sim.setText("Sim");

        bg_possui_denca_de_pele.add(rb_doenca_pele_nao);
        rb_doenca_pele_nao.setText("Não");

        jLabel34.setText("Obs:");

        tf_cond_croni_outra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_cond_croni_outraActionPerformed(evt);
            }
        });

        jLabel35.setText("Outra:");

        tf_doenca_pele_quais.setEditable(false);
        tf_doenca_pele_quais.setEnabled(false);
        tf_doenca_pele_quais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_doenca_pele_quaisActionPerformed(evt);
            }
        });

        jLabel36.setText("Quais:");

        tf_hist_doenc_obs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_hist_doenc_obsActionPerformed(evt);
            }
        });

        jLabel38.setText("Obs:");

        tf_hist_doenc_outra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_hist_doenc_outraActionPerformed(evt);
            }
        });

        jLabel39.setText("Outra:");

        jLabel33.setText("Quais:");

        jLabel45.setText("Quais:");

        jLabel46.setText("Quais:");

        tf_med_continuo.setEditable(false);
        tf_med_continuo.setEnabled(false);
        tf_med_continuo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_med_continuoActionPerformed(evt);
            }
        });

        bg_med_continuo.add(rb_usa_med_continuo_nao);
        rb_usa_med_continuo_nao.setText("Não");

        bg_med_continuo.add(rb_usa_med_continuo_sim);
        rb_usa_med_continuo_sim.setText("Sim");

        jLabel27.setText("Usa medicamento contínuo:");

        jLabel37.setText("Quais:");

        jLabel28.setText("Tipo sanguíneo:");

        bg_tipo_sangue.add(rb_tipo_sangue_ap);
        rb_tipo_sangue_ap.setText("A+");

        bg_tipo_sangue.add(rb_tipo_sangue_am);
        rb_tipo_sangue_am.setText("A-");

        bg_tipo_sangue.add(rb_tipo_sangue_bp);
        rb_tipo_sangue_bp.setText("B+");

        bg_tipo_sangue.add(rb_tipo_sangue_bm);
        rb_tipo_sangue_bm.setText("B-");

        bg_tipo_sangue.add(rb_tipo_sangue_op);
        rb_tipo_sangue_op.setText("O+");

        bg_tipo_sangue.add(rb_tipo_sangue_om);
        rb_tipo_sangue_om.setText("O-");

        bg_tipo_sangue.add(rb_tipo_sangue_abp);
        rb_tipo_sangue_abp.setText("AB+");

        bg_tipo_sangue.add(rb_tipo_sangue_abm);
        rb_tipo_sangue_abm.setText("AB-");

        bg_tipo_sangue.add(rb_tipo_sangue_nao_sabe);
        rb_tipo_sangue_nao_sabe.setText("Não sabe");

        jLabel47.setText("Encaminhamentos:");

        cb_cond_cron_possui_diabetes.setText("Diabetes");

        cb_cond_cron_possui_HIV.setText("HIV");

        cb_cond_cron_possui_autoimune.setText("Autoimune");

        cb_cond_cron_possui_hipertensao.setText("Hipertensão");

        cb_cond_cron_nao_sabe_responder.setText("Não sabe responder");

        cb_hist_doencas_infec_sifilis.setText("Sifilis");

        cb_hist_doencas_infec_hpv.setText("HPV");

        cb_hist_doencas_infec_tuberculose.setText("Tuberculose");
        cb_hist_doencas_infec_tuberculose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_hist_doencas_infec_tuberculoseActionPerformed(evt);
            }
        });

        cb_hist_doencas_infec_hepatite_b.setText("Hepatite B");
        cb_hist_doencas_infec_hepatite_b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_hist_doencas_infec_hepatite_bActionPerformed(evt);
            }
        });

        cb_hist_doencas_infec_hepatite_c.setText("Hepatite C");
        cb_hist_doencas_infec_hepatite_c.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_hist_doencas_infec_hepatite_cActionPerformed(evt);
            }
        });

        cb_hist_doencas_infec_nao_sabe_responder.setText("Não sabe responder");

        javax.swing.GroupLayout condicoes_de_saudeLayout = new javax.swing.GroupLayout(condicoes_de_saude);
        condicoes_de_saude.setLayout(condicoes_de_saudeLayout);
        condicoes_de_saudeLayout.setHorizontalGroup(
            condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_deficiencia_quais))
                    .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_possui_intolerancia_quais))
                    .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                        .addComponent(jLabel46)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_realizou_cirurgia_quais))
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_hist_doenc_obs))
                    .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_hist_doenc_outra))
                    .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_doenca_pele_quais))
                    .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_med_continuo))
                    .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tf_cond_croni_outra))
                            .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cb_cond_cron_nao_sabe_responder)
                                    .addComponent(cb_cond_cron_possui_diabetes)
                                    .addComponent(cb_cond_cron_possui_HIV)
                                    .addComponent(cb_cond_cron_possui_autoimune)
                                    .addComponent(cb_cond_cron_possui_hipertensao))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tf_cond_croni_quais))))
                    .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                        .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_possui_deficiencia_sim)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_possui_deficiencia_nao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_possui_deficiencia_nao_sei))
                            .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_possui_alergia_sim)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_possui_alergia_nao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_possui_alergia_nao_sei))
                            .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_realizou_cirurgia_sim)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_realizou_cirurgia_nao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_realizou_cirurgia_nao_sei))
                            .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_doenca_pele_sim)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_doenca_pele_nao))
                            .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_usa_med_continuo_sim)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_usa_med_continuo_nao))
                            .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_tipo_sangue_ap)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_tipo_sangue_am)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_tipo_sangue_bp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_tipo_sangue_bm)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_tipo_sangue_op)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_tipo_sangue_om)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_tipo_sangue_abp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_tipo_sangue_abm)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_tipo_sangue_nao_sabe))
                            .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(cb_hist_doencas_infec_sifilis)
                                        .addComponent(cb_hist_doencas_infec_hpv, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addComponent(cb_hist_doencas_infec_tuberculose)
                                    .addComponent(cb_hist_doencas_infec_hepatite_c)
                                    .addComponent(cb_hist_doencas_infec_hepatite_b)))
                            .addComponent(cb_hist_doencas_infec_nao_sabe_responder))
                        .addGap(0, 360, Short.MAX_VALUE))
                    .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_tipo_sangue)))
                .addContainerGap())
        );
        condicoes_de_saudeLayout.setVerticalGroup(
            condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(rb_possui_deficiencia_sim)
                    .addComponent(rb_possui_deficiencia_nao)
                    .addComponent(rb_possui_deficiencia_nao_sei))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_deficiencia_quais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addGap(24, 24, 24)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(rb_possui_alergia_sim)
                    .addComponent(rb_possui_alergia_nao)
                    .addComponent(rb_possui_alergia_nao_sei))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_possui_intolerancia_quais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45))
                .addGap(18, 18, 18)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(rb_realizou_cirurgia_sim)
                    .addComponent(rb_realizou_cirurgia_nao)
                    .addComponent(rb_realizou_cirurgia_nao_sei))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_realizou_cirurgia_quais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addGap(18, 18, 18)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cb_cond_cron_possui_hipertensao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_cond_cron_possui_diabetes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_cond_cron_possui_HIV)
                .addGap(6, 6, 6)
                .addComponent(cb_cond_cron_possui_autoimune)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_cond_croni_outra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_cond_cron_nao_sabe_responder)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_cond_croni_quais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGap(20, 20, 20)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cb_hist_doencas_infec_sifilis)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_hist_doencas_infec_hpv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_hist_doencas_infec_tuberculose)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_hist_doencas_infec_hepatite_b)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_hist_doencas_infec_hepatite_c)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_hist_doenc_outra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_hist_doencas_infec_nao_sabe_responder)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_hist_doenc_obs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addGap(18, 18, 18)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(rb_doenca_pele_sim)
                    .addComponent(rb_doenca_pele_nao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(tf_doenca_pele_quais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(rb_usa_med_continuo_sim)
                    .addComponent(rb_usa_med_continuo_nao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(tf_med_continuo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(rb_tipo_sangue_ap)
                    .addComponent(rb_tipo_sangue_am)
                    .addComponent(rb_tipo_sangue_bp)
                    .addComponent(rb_tipo_sangue_bm)
                    .addComponent(rb_tipo_sangue_op)
                    .addComponent(rb_tipo_sangue_om)
                    .addComponent(rb_tipo_sangue_abp)
                    .addComponent(rb_tipo_sangue_abm)
                    .addComponent(rb_tipo_sangue_nao_sabe))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47)
                    .addComponent(tf_tipo_sangue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("DADOS SOCIAIS E ECONÔMICOS");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel48)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 204));

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("SAÚDE DA MULHER");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jLabel49)
                .addContainerGap())
        );

        jLabel50.setText("Escolaridade:");

        bg_escolaridade.add(rb_escolaridade_fund_inc);
        rb_escolaridade_fund_inc.setText("Fundamental incompleto");

        bg_escolaridade.add(rb_escolaridade_fund_com);
        rb_escolaridade_fund_com.setText("Fundamental completo");

        bg_escolaridade.add(rb_escolaridade_med_inc);
        rb_escolaridade_med_inc.setText("Médio incompleto");

        bg_escolaridade.add(rb_escolaridade_med_com);
        rb_escolaridade_med_com.setText("Médio completo");

        bg_escolaridade.add(rb_escolaridade_sup_inc);
        rb_escolaridade_sup_inc.setText("Superior incompleto");

        bg_escolaridade.add(rb_escolaridade_sup_com);
        rb_escolaridade_sup_com.setText("Superior completo");

        jSeparator1.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator1.setOpaque(true);

        jLabel51.setText("Família recebe algum benefício atualmente?");

        bg_fam_rec_beneficio.add(rb_fam_rec_beneficio_sim);
        rb_fam_rec_beneficio_sim.setText("Sim");

        bg_fam_rec_beneficio.add(rb_fam_rec_beneficio_nao);
        rb_fam_rec_beneficio_nao.setText("Não");

        jLabel52.setText("Qual:");

        tf_fam_rec_beneficio_quais.setEditable(false);
        tf_fam_rec_beneficio_quais.setEnabled(false);

        tf_poss_filhos_quantos.setEditable(false);
        tf_poss_filhos_quantos.setToolTipText("Local");
        tf_poss_filhos_quantos.setEnabled(false);

        bg_possui_filhos.add(rb_poss_filhos_nao);
        rb_poss_filhos_nao.setText("Não");

        bg_possui_filhos.add(rb_poss_filhos_sim);
        rb_poss_filhos_sim.setText("Sim");

        jLabel53.setText("Possui filhos?");

        jLabel55.setText("Idade:");

        tf_idade_filhos.setEditable(false);
        tf_idade_filhos.setEnabled(false);

        jLabel56.setText("Possui algum outro dependente?");

        bg_possuio_outro_dependente.add(rb_possui_outro_dependente_sim);
        rb_possui_outro_dependente_sim.setText("Sim");

        bg_possuio_outro_dependente.add(rb_possui_outro_dependente_nao);
        rb_possui_outro_dependente_nao.setText("Não");

        tf_possui_outro_dependente_qts.setEditable(false);
        tf_possui_outro_dependente_qts.setToolTipText("Local");
        tf_possui_outro_dependente_qts.setEnabled(false);
        tf_possui_outro_dependente_qts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_possui_outro_dependente_qtsActionPerformed(evt);
            }
        });

        jLabel58.setText("Encaminhamentos:");

        jSeparator2.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator2.setOpaque(true);

        bg_neeja.add(rb_ofertar_neeja_nao);
        rb_ofertar_neeja_nao.setText("Não");

        bg_neeja.add(rb_ofertar_neeja_sim);
        rb_ofertar_neeja_sim.setText("Sim");

        jLabel59.setText("Ofertar encaminhamento para conhecer ou estudar no NEEJA:");

        jLabel60.setText("Ofertar conversa com a Assistencia social:");

        bg_conversa_assistencia_social.add(rb_conversa_assistencia_social_sim);
        rb_conversa_assistencia_social_sim.setText("Sim");

        bg_conversa_assistencia_social.add(rb_conversa_assistencia_social_nao);
        rb_conversa_assistencia_social_nao.setText("Não");

        jSeparator3.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator3.setOpaque(true);

        jLabel29.setText("Quantos:");

        jLabel30.setText("Quantos:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel60)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_conversa_assistencia_social_sim)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_conversa_assistencia_social_nao))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel59)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_ofertar_neeja_sim)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_ofertar_neeja_nao)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel50)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rb_escolaridade_fund_com)
                                            .addComponent(rb_escolaridade_fund_inc)
                                            .addComponent(rb_escolaridade_med_inc)
                                            .addComponent(rb_escolaridade_med_com)
                                            .addComponent(rb_escolaridade_sup_inc)
                                            .addComponent(rb_escolaridade_sup_com))))
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel52)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tf_fam_rec_beneficio_quais))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel51)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rb_fam_rec_beneficio_sim)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rb_fam_rec_beneficio_nao))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel55)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tf_idade_filhos, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel53)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rb_poss_filhos_sim)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rb_poss_filhos_nao)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel29)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tf_poss_filhos_quantos, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel56)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rb_possui_outro_dependente_sim)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rb_possui_outro_dependente_nao)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel30)
                                                .addGap(18, 18, 18)
                                                .addComponent(tf_possui_outro_dependente_qts, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 5, Short.MAX_VALUE))))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel58)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel51)
                            .addComponent(rb_fam_rec_beneficio_sim)
                            .addComponent(rb_fam_rec_beneficio_nao))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf_fam_rec_beneficio_quais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel52))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rb_poss_filhos_sim)
                            .addComponent(rb_poss_filhos_nao)
                            .addComponent(tf_poss_filhos_quantos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel53)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel55)
                            .addComponent(tf_idade_filhos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rb_possui_outro_dependente_sim)
                            .addComponent(rb_possui_outro_dependente_nao)
                            .addComponent(tf_possui_outro_dependente_qts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel56)
                            .addComponent(jLabel30)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel50)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(rb_escolaridade_fund_inc)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(rb_escolaridade_fund_com)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(rb_escolaridade_med_inc)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(rb_escolaridade_med_com)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(rb_escolaridade_sup_inc)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(rb_escolaridade_sup_com))
                        .addComponent(jSeparator1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel58)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_ofertar_neeja_sim)
                    .addComponent(rb_ofertar_neeja_nao)
                    .addComponent(jLabel59))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_conversa_assistencia_social_sim)
                    .addComponent(rb_conversa_assistencia_social_nao)
                    .addComponent(jLabel60))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel61.setText("Gestação no momento");

        bg_gestacao_no_momento.add(rb_gestacao_no_momvento_sim);
        rb_gestacao_no_momvento_sim.setText("Sim");

        bg_gestacao_no_momento.add(rb_gestacao_no_momvento_nao);
        rb_gestacao_no_momvento_nao.setText("Não");

        bg_gestacao_no_momento.add(rb_gestacao_no_momvento_nao_sabe);
        rb_gestacao_no_momvento_nao_sabe.setText("Não sabe");

        jSeparator4.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator4.setOpaque(true);

        jLabel62.setText("Faz uso de método contraceptivo? se Sim, qual.");

        bg_tipo_met_anticoncepcional.add(rb_escolaridade_fund_com1);
        rb_escolaridade_fund_com1.setText("Anticoncepcional oral");

        bg_tipo_met_anticoncepcional.add(rb_escolaridade_med_inc1);
        rb_escolaridade_med_inc1.setText("DIU/implante");

        bg_tipo_met_anticoncepcional.add(rb_escolaridade_med_com1);
        rb_escolaridade_med_com1.setText("Anticoncepcional injetável");

        bg_tipo_met_anticoncepcional.add(rb_escolaridade_sup_inc1);
        rb_escolaridade_sup_inc1.setText("Ligadura de trompas");

        bg_tipo_met_anticoncepcional.add(rb_escolaridade_sup_com1);
        rb_escolaridade_sup_com1.setText("Histerectomia");

        bg_tipo_met_anticoncepcional.add(rb_gestacao_no_momvento_nao1);
        rb_gestacao_no_momvento_nao1.setText("Não");
        rb_gestacao_no_momvento_nao1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_gestacao_no_momvento_nao1ActionPerformed(evt);
            }
        });

        jSeparator5.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator5.setOpaque(true);

        jLabel64.setText("Já realizou exame preventivo/Papanicolau?");

        jSeparator6.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator6.setOpaque(true);

        jLabel65.setText("Ofertar a continuidade ou início da utilização de método contraceptivo:");

        bg_ofertar_continuidade_contraceptivo.add(rb_ofertar_continuidade_contraceptivo_sim);
        rb_ofertar_continuidade_contraceptivo_sim.setText("Sim");

        bg_ofertar_continuidade_contraceptivo.add(rb_ofertar_continuidade_contraceptivo_nao);
        rb_ofertar_continuidade_contraceptivo_nao.setText("Não");

        jLabel66.setText("Encaminhamentos");

        jLabel67.setText("Ofertar consulta para realizar exame preventivo:");

        bg_consultar_exame_preventivo.add(rb_consultar_exame_preventivo_sim);
        rb_consultar_exame_preventivo_sim.setText("Sim");

        bg_consultar_exame_preventivo.add(rb_consultar_exame_preventivo_nao);
        rb_consultar_exame_preventivo_nao.setText("Não");

        jLabel68.setText("Ecaminhar para pré-natal:");

        bg_enc_pre_natal.add(rb_enc_pre_natal_sim);
        rb_enc_pre_natal_sim.setText("Sim");

        bg_enc_pre_natal.add(rb_enc_pre_natal_nao);
        rb_enc_pre_natal_nao.setText("Não");

        bg_realizou_papanicolau.add(rb_papanicolau_nao);
        rb_papanicolau_nao.setText("Não");

        bg_realizou_papanicolau.add(rb_papanicolau_sim);
        rb_papanicolau_sim.setText("Sim");
        rb_papanicolau_sim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_papanicolau_simActionPerformed(evt);
            }
        });

        jLabel104.setText("Qual foi o ano?");

        tf_papanicolau_ano.setEditable(false);
        tf_papanicolau_ano.setToolTipText("Local");
        tf_papanicolau_ano.setEnabled(false);
        tf_papanicolau_ano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_papanicolau_anoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout saude_da_mulher_panelLayout = new javax.swing.GroupLayout(saude_da_mulher_panel);
        saude_da_mulher_panel.setLayout(saude_da_mulher_panelLayout);
        saude_da_mulher_panelLayout.setHorizontalGroup(
            saude_da_mulher_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saude_da_mulher_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(saude_da_mulher_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator6)
                    .addGroup(saude_da_mulher_panelLayout.createSequentialGroup()
                        .addGroup(saude_da_mulher_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(saude_da_mulher_panelLayout.createSequentialGroup()
                                .addGroup(saude_da_mulher_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel61)
                                    .addComponent(rb_gestacao_no_momvento_sim)
                                    .addComponent(rb_gestacao_no_momvento_nao)
                                    .addComponent(rb_gestacao_no_momvento_nao_sabe))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(saude_da_mulher_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel62)
                                    .addGroup(saude_da_mulher_panelLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(saude_da_mulher_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rb_escolaridade_fund_com1)
                                            .addComponent(rb_escolaridade_med_inc1)
                                            .addComponent(rb_escolaridade_med_com1)
                                            .addComponent(rb_escolaridade_sup_inc1)
                                            .addComponent(rb_escolaridade_sup_com1)
                                            .addComponent(rb_gestacao_no_momvento_nao1)))))
                            .addComponent(jLabel66)
                            .addGroup(saude_da_mulher_panelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(saude_da_mulher_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(saude_da_mulher_panelLayout.createSequentialGroup()
                                        .addComponent(jLabel68)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rb_enc_pre_natal_sim)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rb_enc_pre_natal_nao))
                                    .addGroup(saude_da_mulher_panelLayout.createSequentialGroup()
                                        .addComponent(jLabel65)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rb_ofertar_continuidade_contraceptivo_sim)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rb_ofertar_continuidade_contraceptivo_nao))
                                    .addGroup(saude_da_mulher_panelLayout.createSequentialGroup()
                                        .addComponent(jLabel67)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rb_consultar_exame_preventivo_sim)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rb_consultar_exame_preventivo_nao))))
                            .addGroup(saude_da_mulher_panelLayout.createSequentialGroup()
                                .addComponent(jLabel64)
                                .addGap(18, 18, 18)
                                .addComponent(rb_papanicolau_nao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_papanicolau_sim)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel104)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tf_papanicolau_ano, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        saude_da_mulher_panelLayout.setVerticalGroup(
            saude_da_mulher_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saude_da_mulher_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(saude_da_mulher_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(saude_da_mulher_panelLayout.createSequentialGroup()
                        .addComponent(jLabel62)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_gestacao_no_momvento_nao1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_escolaridade_fund_com1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_escolaridade_med_inc1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_escolaridade_med_com1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_escolaridade_sup_inc1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_escolaridade_sup_com1))
                    .addGroup(saude_da_mulher_panelLayout.createSequentialGroup()
                        .addComponent(jLabel61)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_gestacao_no_momvento_sim)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_gestacao_no_momvento_nao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_gestacao_no_momvento_nao_sabe))
                    .addComponent(jSeparator4))
                .addGap(18, 18, 18)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(saude_da_mulher_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(saude_da_mulher_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rb_papanicolau_nao)
                        .addComponent(tf_papanicolau_ano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel104)
                        .addComponent(rb_papanicolau_sim))
                    .addComponent(jLabel64))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel66)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(saude_da_mulher_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel65)
                    .addGroup(saude_da_mulher_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rb_ofertar_continuidade_contraceptivo_sim)
                        .addComponent(rb_ofertar_continuidade_contraceptivo_nao)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(saude_da_mulher_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel67)
                    .addGroup(saude_da_mulher_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rb_consultar_exame_preventivo_sim)
                        .addComponent(rb_consultar_exame_preventivo_nao)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(saude_da_mulher_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel68)
                    .addGroup(saude_da_mulher_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rb_enc_pre_natal_sim)
                        .addComponent(rb_enc_pre_natal_nao)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 204));

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel69.setText("SAÚDE MENTAL E USO DE SUBSTÂNCIAS");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jLabel69)
                .addContainerGap())
        );

        jLabel70.setText("Já realizou exame preventivo para o câncer de próstata?");

        bg_exame_prostata.add(rb_exame_prostata_nao);
        rb_exame_prostata_nao.setText("Não");

        bg_exame_prostata.add(rb_exame_prostata_sim);
        rb_exame_prostata_sim.setText("Sim");
        rb_exame_prostata_sim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_exame_prostata_simActionPerformed(evt);
            }
        });

        jLabel63.setText("Qual foi o ano?");

        tf_exame_prostata_ano.setEditable(false);
        tf_exame_prostata_ano.setToolTipText("Local");
        tf_exame_prostata_ano.setEnabled(false);
        tf_exame_prostata_ano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_exame_prostata_anoActionPerformed(evt);
            }
        });

        jLabel71.setText("Possui histórico de câncer de próstata na família?");

        bg_historico_prostata_familia.add(rb_historico_prostata_nao);
        rb_historico_prostata_nao.setText("Não");

        bg_historico_prostata_familia.add(rb_historico_prostata_sim);
        rb_historico_prostata_sim.setText("Sim");
        rb_historico_prostata_sim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_historico_prostata_simActionPerformed(evt);
            }
        });

        jLabel72.setText("Qual foi familiar?");

        rb_historico_prostata_familiar.setEditable(false);
        rb_historico_prostata_familiar.setToolTipText("Local");
        rb_historico_prostata_familiar.setEnabled(false);
        rb_historico_prostata_familiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_historico_prostata_familiarActionPerformed(evt);
            }
        });

        jLabel73.setText("Você realizou vasectomia?");

        bg_realizou_vasectomia.add(rb_realizou_vasectomia_nao);
        rb_realizou_vasectomia_nao.setText("Não");

        bg_realizou_vasectomia.add(rb_realizou_vasectomia_sim);
        rb_realizou_vasectomia_sim.setText("Sim");
        rb_realizou_vasectomia_sim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_realizou_vasectomia_simActionPerformed(evt);
            }
        });

        bg_parceira_gestante.add(rb_parceira_gestante_sim);
        rb_parceira_gestante_sim.setText("Sim");
        rb_parceira_gestante_sim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_parceira_gestante_simActionPerformed(evt);
            }
        });

        bg_parceira_gestante.add(rb_parceira_gestante_nao);
        rb_parceira_gestante_nao.setText("Não");

        jLabel74.setText("Sua parceira está gestante?");

        jLabel75.setText("Você está participando do pré-natal?");

        bg_esta_participando_pre_natal.add(rb_esta_participando_pre_natal_nao);
        rb_esta_participando_pre_natal_nao.setText("Não");
        rb_esta_participando_pre_natal_nao.setEnabled(false);

        bg_esta_participando_pre_natal.add(rb_esta_participando_pre_natal_sim);
        rb_esta_participando_pre_natal_sim.setText("Sim");
        rb_esta_participando_pre_natal_sim.setEnabled(false);
        rb_esta_participando_pre_natal_sim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_esta_participando_pre_natal_simActionPerformed(evt);
            }
        });

        jSeparator7.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator7.setOpaque(true);

        jLabel76.setText("Encaminhamentos");

        jLabel77.setText("Ofertar encaminhamento para realizar vasectomia?");

        bg_encaminhar_realiz_vasectomia.add(rb_encaminhar_realiz_vasectomia_nao);
        rb_encaminhar_realiz_vasectomia_nao.setText("Não");

        bg_encaminhar_realiz_vasectomia.add(rb_encaminhar_realiz_vasectomia_sim);
        rb_encaminhar_realiz_vasectomia_sim.setText("Sim");
        rb_encaminhar_realiz_vasectomia_sim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_encaminhar_realiz_vasectomia_simActionPerformed(evt);
            }
        });

        jLabel78.setText("Encaminhar para realizar pré-natal do parceiro?");

        bg_encaminhar_pre_natal_do_parceiro.add(rb_encaminhar_pre_natal_parceiro_nao);
        rb_encaminhar_pre_natal_parceiro_nao.setText("Não");

        bg_encaminhar_pre_natal_do_parceiro.add(rb_encaminhar_pre_natal_parceiro_sim);
        rb_encaminhar_pre_natal_parceiro_sim.setText("Sim");
        rb_encaminhar_pre_natal_parceiro_sim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_encaminhar_pre_natal_parceiro_simActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout saude_homem_panelLayout = new javax.swing.GroupLayout(saude_homem_panel);
        saude_homem_panel.setLayout(saude_homem_panelLayout);
        saude_homem_panelLayout.setHorizontalGroup(
            saude_homem_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saude_homem_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(saude_homem_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(saude_homem_panelLayout.createSequentialGroup()
                        .addGroup(saude_homem_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator7)
                            .addGroup(saude_homem_panelLayout.createSequentialGroup()
                                .addGroup(saude_homem_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(saude_homem_panelLayout.createSequentialGroup()
                                        .addComponent(jLabel70)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rb_exame_prostata_nao)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rb_exame_prostata_sim)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel63)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tf_exame_prostata_ano, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(saude_homem_panelLayout.createSequentialGroup()
                                        .addComponent(jLabel71)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rb_historico_prostata_nao)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rb_historico_prostata_sim)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel72)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rb_historico_prostata_familiar, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(saude_homem_panelLayout.createSequentialGroup()
                                        .addComponent(jLabel73)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rb_realizou_vasectomia_nao)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rb_realizou_vasectomia_sim))
                                    .addGroup(saude_homem_panelLayout.createSequentialGroup()
                                        .addComponent(jLabel74)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rb_parceira_gestante_nao)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rb_parceira_gestante_sim)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel75)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rb_esta_participando_pre_natal_nao)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rb_esta_participando_pre_natal_sim))
                                    .addComponent(jLabel76))
                                .addGap(0, 72, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(saude_homem_panelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(saude_homem_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(saude_homem_panelLayout.createSequentialGroup()
                                .addComponent(jLabel78)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rb_encaminhar_pre_natal_parceiro_nao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_encaminhar_pre_natal_parceiro_sim))
                            .addGroup(saude_homem_panelLayout.createSequentialGroup()
                                .addComponent(jLabel77)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rb_encaminhar_realiz_vasectomia_nao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_encaminhar_realiz_vasectomia_sim)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        saude_homem_panelLayout.setVerticalGroup(
            saude_homem_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saude_homem_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(saude_homem_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(saude_homem_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rb_exame_prostata_nao)
                        .addComponent(tf_exame_prostata_ano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel63)
                        .addComponent(rb_exame_prostata_sim))
                    .addComponent(jLabel70))
                .addGap(18, 18, 18)
                .addGroup(saude_homem_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(saude_homem_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rb_historico_prostata_nao)
                        .addComponent(rb_historico_prostata_familiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel72)
                        .addComponent(rb_historico_prostata_sim))
                    .addComponent(jLabel71))
                .addGap(18, 18, 18)
                .addGroup(saude_homem_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(saude_homem_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rb_realizou_vasectomia_nao)
                        .addComponent(rb_realizou_vasectomia_sim))
                    .addComponent(jLabel73))
                .addGap(18, 18, 18)
                .addGroup(saude_homem_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(saude_homem_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rb_esta_participando_pre_natal_nao)
                        .addComponent(rb_esta_participando_pre_natal_sim))
                    .addGroup(saude_homem_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rb_parceira_gestante_nao)
                        .addComponent(rb_parceira_gestante_sim)
                        .addComponent(jLabel75))
                    .addComponent(jLabel74))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel76)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(saude_homem_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(saude_homem_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rb_encaminhar_realiz_vasectomia_nao)
                        .addComponent(rb_encaminhar_realiz_vasectomia_sim))
                    .addComponent(jLabel77))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(saude_homem_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(saude_homem_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rb_encaminhar_pre_natal_parceiro_nao)
                        .addComponent(rb_encaminhar_pre_natal_parceiro_sim))
                    .addComponent(jLabel78))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 204));

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel79.setText("SAÚDE DO HOMEM");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jLabel79)
                .addContainerGap())
        );

        jLabel88.setText("Possui vinculo com CAPS?");

        bg_vinculo_caps.add(rb_vinculo_caps_nao);
        rb_vinculo_caps_nao.setText("Não");

        bg_vinculo_caps.add(rb_vinculo_caps_sim);
        rb_vinculo_caps_sim.setText("Sim");

        jLabel89.setText("Qual o nome e município desse CAPS");

        tf_vinculo_caps_nome.setEditable(false);
        tf_vinculo_caps_nome.setToolTipText("Local");
        tf_vinculo_caps_nome.setEnabled(false);
        tf_vinculo_caps_nome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_vinculo_caps_nomeActionPerformed(evt);
            }
        });

        tf_vinculo_caps_municipio.setEditable(false);
        tf_vinculo_caps_municipio.setToolTipText("Local");
        tf_vinculo_caps_municipio.setEnabled(false);
        tf_vinculo_caps_municipio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_vinculo_caps_municipioActionPerformed(evt);
            }
        });

        jLabel90.setText("Nome:");

        jLabel91.setText("Município:");

        jSeparator10.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator10.setOpaque(true);

        jLabel92.setText("Possui transtorno mental:");

        jLabel98.setText("Outro:");

        cb_possui_esquisofrenia.setText("Esquisofrenia");

        cb_possui_bipolaridade.setText("Bipolaridade");

        cb_possui_depressao.setText("Depressão");

        cb_possui_ansiedade.setText("Ansisedade");

        cb_possui_autismo.setText("Autismo");

        jSeparator11.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator11.setOpaque(true);

        jLabel93.setText("Qual:");

        tf_usa_med_controlado_qual.setEditable(false);
        tf_usa_med_controlado_qual.setToolTipText("Local");
        tf_usa_med_controlado_qual.setEnabled(false);
        tf_usa_med_controlado_qual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_usa_med_controlado_qualActionPerformed(evt);
            }
        });

        cb_transt_mentais_n_sabe_resp.setText("Não sabe responder");

        bg_usa_medicamento_controlado.add(rb_usa_med_controlado_nao);
        rb_usa_med_controlado_nao.setText("Não");

        bg_usa_medicamento_controlado.add(rb_usa_med_controlado_sim);
        rb_usa_med_controlado_sim.setText("Sim");

        jLabel94.setText("Usa Medicamento controlado?");

        bg_usa_medicamento_controlado.add(rb_usa_med_controlado_nao_sabe);
        rb_usa_med_controlado_nao_sabe.setText("Não sabe responder");

        jSeparator12.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator12.setOpaque(true);

        jLabel95.setText("Estava em acompanhamento em Saúde Mental no momento da Prisão?");

        bg_estava_acomp_saude_mental_momento_prisao.add(rb_acompanhamento_saude_mental_no_momento_da_prisao_nao);
        rb_acompanhamento_saude_mental_no_momento_da_prisao_nao.setText("Não");

        bg_estava_acomp_saude_mental_momento_prisao.add(rb_acompanhamento_saude_mental_no_momento_da_prisao_sim);
        rb_acompanhamento_saude_mental_no_momento_da_prisao_sim.setText("Sim");

        jLabel96.setText("Qual:");

        tf_acompanhamento_saude_mental_no_momento_da_prisao_qual.setEditable(false);
        tf_acompanhamento_saude_mental_no_momento_da_prisao_qual.setToolTipText("Local");
        tf_acompanhamento_saude_mental_no_momento_da_prisao_qual.setEnabled(false);
        tf_acompanhamento_saude_mental_no_momento_da_prisao_qual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_acompanhamento_saude_mental_no_momento_da_prisao_qualActionPerformed(evt);
            }
        });

        jSeparator13.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator13.setOpaque(true);

        jLabel97.setText("Faz uso de:");

        cb_faz_uso_de_cigarro.setText("Cigarro");

        cb_faz_uso_de_alccol.setText("Álcool");

        cb_faz_uso_de_maconha.setText("Maconha");

        cb_faz_uso_de_cocaina.setText("Cocaína");

        cb_faz_uso_de_crack.setText("Crack");

        cb_faz_uso_de_drogas_k.setText("Drogas \"k\"");

        cb_faz_uso_de_anfetaminas.setText("Anfetaminas");

        jLabel99.setText("Outra(s):");

        jSeparator14.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator14.setOpaque(true);

        jLabel100.setText("Já realizou algum tratamento para cessar ou reduzir o uso?");

        bg_ja_realizou_trat_para_cessar_reduzir_uso.add(rb_ja_realizou_trat_para_cessar_reduzir_uso_nao);
        rb_ja_realizou_trat_para_cessar_reduzir_uso_nao.setText("Não");

        bg_ja_realizou_trat_para_cessar_reduzir_uso.add(rb_ja_realizou_trat_para_cessar_reduzir_uso_sim);
        rb_ja_realizou_trat_para_cessar_reduzir_uso_sim.setText("Sim");

        jLabel101.setText("Qual:");

        tf_ja_realizou_trat_para_cessar_reduzir_uso_qual.setEditable(false);
        tf_ja_realizou_trat_para_cessar_reduzir_uso_qual.setToolTipText("Local");
        tf_ja_realizou_trat_para_cessar_reduzir_uso_qual.setEnabled(false);
        tf_ja_realizou_trat_para_cessar_reduzir_uso_qual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_ja_realizou_trat_para_cessar_reduzir_uso_qualActionPerformed(evt);
            }
        });

        cb_gostaria_realizar_trat_para_cessar_reduzir_uso.setText("Gostaria de realizar algum tratamento para cessar ou reduzir o uso?");
        cb_gostaria_realizar_trat_para_cessar_reduzir_uso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_gostaria_realizar_trat_para_cessar_reduzir_usoActionPerformed(evt);
            }
        });

        tf_gostaria_realizar_trat_para_cessar_reduzir_uso_qual_subs.setEditable(false);
        tf_gostaria_realizar_trat_para_cessar_reduzir_uso_qual_subs.setToolTipText("Local");
        tf_gostaria_realizar_trat_para_cessar_reduzir_uso_qual_subs.setEnabled(false);
        tf_gostaria_realizar_trat_para_cessar_reduzir_uso_qual_subs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_gostaria_realizar_trat_para_cessar_reduzir_uso_qual_subsActionPerformed(evt);
            }
        });

        jLabel102.setText("Qual substancia:");

        jSeparator15.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator15.setOpaque(true);

        jLabel103.setText("Encaminhamentos:");

        jCheckBox1.setText("Ofertar consulta com a psicologia?");

        jCheckBox3.setText("Ofertar consulta com a psiquiatra?");

        jCheckBox4.setText("Encaminhar para renovação de receitas?");

        jCheckBox5.setText("Ofertar encaminhamento para grupos de apoio para cessar ou reduzir o uso (se existir)");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel103)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox3)
                                    .addComponent(jCheckBox1)
                                    .addComponent(jCheckBox4)
                                    .addComponent(jCheckBox5))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel88)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rb_vinculo_caps_sim)
                                            .addComponent(rb_vinculo_caps_nao)
                                            .addComponent(jLabel89)
                                            .addGroup(jPanel8Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                                        .addComponent(jLabel91)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(tf_vinculo_caps_municipio))
                                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                                        .addComponent(jLabel90)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(tf_vinculo_caps_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel98)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tf_possui_trasntorno_mental_outro))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cb_transt_mentais_n_sabe_resp)
                                            .addComponent(jLabel92)
                                            .addComponent(cb_possui_esquisofrenia)
                                            .addComponent(cb_possui_bipolaridade)
                                            .addComponent(cb_possui_depressao)
                                            .addComponent(cb_possui_ansiedade)
                                            .addComponent(cb_possui_autismo))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel94)
                                            .addGroup(jPanel8Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(rb_usa_med_controlado_nao_sabe)
                                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                                        .addComponent(rb_usa_med_controlado_sim)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jLabel93)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(tf_usa_med_controlado_qual, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(rb_usa_med_controlado_nao))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel8Layout.createSequentialGroup()
                                                .addComponent(rb_acompanhamento_saude_mental_no_momento_da_prisao_sim)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel96)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tf_acompanhamento_saude_mental_no_momento_da_prisao_qual))
                                            .addGroup(jPanel8Layout.createSequentialGroup()
                                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel95)
                                                    .addComponent(rb_acompanhamento_saude_mental_no_momento_da_prisao_nao))
                                                .addGap(0, 0, Short.MAX_VALUE))))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel8Layout.createSequentialGroup()
                                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel97)
                                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                                        .addGap(6, 6, 6)
                                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(cb_faz_uso_de_cigarro)
                                                            .addComponent(cb_faz_uso_de_alccol)
                                                            .addComponent(cb_faz_uso_de_maconha)
                                                            .addComponent(cb_faz_uso_de_crack)
                                                            .addComponent(cb_faz_uso_de_cocaina)
                                                            .addComponent(cb_faz_uso_de_anfetaminas)
                                                            .addComponent(cb_faz_uso_de_drogas_k))))
                                                .addGap(304, 304, 304))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                                .addComponent(jLabel99)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tf_faz_uso_de_outras, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)))
                                        .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel8Layout.createSequentialGroup()
                                                .addComponent(rb_ja_realizou_trat_para_cessar_reduzir_uso_sim)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel101)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tf_ja_realizou_trat_para_cessar_reduzir_uso_qual))
                                            .addGroup(jPanel8Layout.createSequentialGroup()
                                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel100)
                                                    .addComponent(rb_ja_realizou_trat_para_cessar_reduzir_uso_nao)
                                                    .addComponent(cb_gostaria_realizar_trat_para_cessar_reduzir_uso))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addGroup(jPanel8Layout.createSequentialGroup()
                                                .addComponent(jLabel102)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tf_gostaria_realizar_trat_para_cessar_reduzir_uso_qual_subs))))))
                            .addComponent(jSeparator15))
                        .addContainerGap())))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel92)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_possui_ansiedade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_possui_depressao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_possui_bipolaridade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_possui_esquisofrenia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_possui_autismo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel98)
                            .addComponent(tf_possui_trasntorno_mental_outro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jSeparator10)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel88)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(rb_vinculo_caps_nao)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(rb_vinculo_caps_sim)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel89)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tf_vinculo_caps_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel90))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tf_vinculo_caps_municipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel91)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cb_transt_mentais_n_sabe_resp)
                .addGap(18, 18, 18)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel94)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(rb_usa_med_controlado_nao)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rb_usa_med_controlado_sim)
                                .addComponent(jLabel93)
                                .addComponent(tf_usa_med_controlado_qual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(rb_usa_med_controlado_nao_sabe))
                        .addComponent(jSeparator12))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel95)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_acompanhamento_saude_mental_no_momento_da_prisao_nao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rb_acompanhamento_saude_mental_no_momento_da_prisao_sim)
                            .addComponent(jLabel96)
                            .addComponent(tf_acompanhamento_saude_mental_no_momento_da_prisao_qual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel97)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cb_faz_uso_de_alccol)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_faz_uso_de_cigarro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_faz_uso_de_maconha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_faz_uso_de_crack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_faz_uso_de_cocaina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_faz_uso_de_anfetaminas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_faz_uso_de_drogas_k)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel99)
                            .addComponent(tf_faz_uso_de_outras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel100)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rb_ja_realizou_trat_para_cessar_reduzir_uso_nao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rb_ja_realizou_trat_para_cessar_reduzir_uso_sim)
                            .addComponent(jLabel101)
                            .addComponent(tf_ja_realizou_trat_para_cessar_reduzir_uso_qual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addComponent(cb_gostaria_realizar_trat_para_cessar_reduzir_uso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel102)
                            .addComponent(tf_gostaria_realizar_trat_para_cessar_reduzir_uso_qual_subs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel103)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 204));

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel80.setText("SITUAÇÂO VACINAL");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jLabel80)
                .addContainerGap())
        );

        bg_recebeu_vacina_covid.add(rb_recebeu_vacina_covid_nao_sabe);
        rb_recebeu_vacina_covid_nao_sabe.setText("Não sabe");

        bg_recebeu_vacina_covid.add(rb_recebeu_vacina_covid_nao);
        rb_recebeu_vacina_covid_nao.setText("Não");

        bg_recebeu_vacina_covid.add(rb_recebeu_vacina_covid_sim);
        rb_recebeu_vacina_covid_sim.setText("Sim");

        jLabel81.setText("Recebeu vacina da Covid-19 no ultimo ano?");

        bg_recebeu_vacina_influenza.add(rb_recebeu_vacina_influenza_nao_sabe);
        rb_recebeu_vacina_influenza_nao_sabe.setText("Não sabe");

        bg_recebeu_vacina_influenza.add(rb_recebeu_vacina_influenza_nao);
        rb_recebeu_vacina_influenza_nao.setText("Não");

        bg_recebeu_vacina_influenza.add(rb_recebeu_vacina_influenza_sim);
        rb_recebeu_vacina_influenza_sim.setText("Sim");

        jLabel82.setText("Recebeu vacina da Influenza no ultimo ano?");

        bg_recebeu_vacina_tetano.add(rb_recebeu_vacina_tetano_nao_sabe);
        rb_recebeu_vacina_tetano_nao_sabe.setText("Não sabe");

        bg_recebeu_vacina_tetano.add(rb_recebeu_vacina_tetano_nao);
        rb_recebeu_vacina_tetano_nao.setText("Não");

        bg_recebeu_vacina_tetano.add(rb_recebeu_vacina_tetano_sim);
        rb_recebeu_vacina_tetano_sim.setText("Sim");

        jLabel83.setText("Recebeu vacina da Tétano nos ultimos 10 ano?");

        bg_recebeu_vacina_hepatite_b.add(rb_recebeu_vacina_hepatite_b_nao_sabe);
        rb_recebeu_vacina_hepatite_b_nao_sabe.setText("Não sabe");

        bg_recebeu_vacina_hepatite_b.add(rb_recebeu_vacina_hepatite_b_nao);
        rb_recebeu_vacina_hepatite_b_nao.setText("Não");

        bg_recebeu_vacina_hepatite_b.add(rb_recebeu_vacina_hepatite_b_sim);
        rb_recebeu_vacina_hepatite_b_sim.setText("Sim");

        jLabel84.setText("Recebeu vacina da Hepatite B Alguma vez na vida?");

        jLabel85.setText("Encaminhamentos");

        jLabel86.setText("Ofertar vacinas disponíveis:");

        jSeparator8.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator8.setOpaque(true);

        cb_ofertar_vacina_hepatite_b.setText("Hepatite B");
        cb_ofertar_vacina_hepatite_b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_ofertar_vacina_hepatite_bActionPerformed(evt);
            }
        });

        cb_ofertar_vaciana_nao.setText("Não");

        cb_ofertar_vacina_covid.setText("Covid-19");
        cb_ofertar_vacina_covid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_ofertar_vacina_covidActionPerformed(evt);
            }
        });

        cb_ofertar_vacina_influenza.setText("Influenza");
        cb_ofertar_vacina_influenza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_ofertar_vacina_influenzaActionPerformed(evt);
            }
        });

        cb_ofertar_vacina_febre_amarela.setText("Febre Amarela");
        cb_ofertar_vacina_febre_amarela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_ofertar_vacina_febre_amarelaActionPerformed(evt);
            }
        });

        cb_ofertar_vacina_dupla_adulto.setText("Dupla Adulto (dT)");
        cb_ofertar_vacina_dupla_adulto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_ofertar_vacina_dupla_adultoActionPerformed(evt);
            }
        });

        cb_ofertar_vaciana_triplice_viral.setText("Tríplice Viral (SRC)");
        cb_ofertar_vaciana_triplice_viral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_ofertar_vaciana_triplice_viralActionPerformed(evt);
            }
        });

        jLabel87.setText("Ofertar cópia da carteira de vacinação:");

        bg_ofertar_copia_carteira_vacinacao.add(rb_ofertar_copia_carteira_vacinacao_sim);
        rb_ofertar_copia_carteira_vacinacao_sim.setText("Sim");

        bg_ofertar_copia_carteira_vacinacao.add(rb_ofertar_copia_carteira_vacinacao_nao);
        rb_ofertar_copia_carteira_vacinacao_nao.setText("Não");

        jSeparator9.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator9.setOpaque(true);

        jLabel31.setText("Outra:");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel87)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_ofertar_copia_carteira_vacinacao_sim)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_ofertar_copia_carteira_vacinacao_nao)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator8)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel81)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_recebeu_vacina_covid_sim)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_recebeu_vacina_covid_nao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_recebeu_vacina_covid_nao_sabe))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel82)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_recebeu_vacina_influenza_sim)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_recebeu_vacina_influenza_nao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_recebeu_vacina_influenza_nao_sabe))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel83)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_recebeu_vacina_tetano_sim)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_recebeu_vacina_tetano_nao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_recebeu_vacina_tetano_nao_sabe))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel84)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_recebeu_vacina_hepatite_b_sim)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_recebeu_vacina_hepatite_b_nao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_recebeu_vacina_hepatite_b_nao_sabe))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel85)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel10Layout.createSequentialGroup()
                                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cb_ofertar_vacina_covid)
                                                    .addComponent(cb_ofertar_vacina_hepatite_b)
                                                    .addComponent(cb_ofertar_vacina_influenza))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(cb_ofertar_vacina_dupla_adulto)
                                                            .addComponent(cb_ofertar_vaciana_triplice_viral))
                                                        .addGap(0, 0, Short.MAX_VALUE))
                                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                                        .addComponent(cb_ofertar_vacina_febre_amarela)
                                                        .addGap(37, 37, 37)
                                                        .addComponent(jLabel31)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(tf_ofertar_vacina_outra, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE))))
                                            .addGroup(jPanel10Layout.createSequentialGroup()
                                                .addComponent(jLabel86)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cb_ofertar_vaciana_nao)
                                                .addGap(0, 0, Short.MAX_VALUE))))))
                            .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel81)
                    .addComponent(rb_recebeu_vacina_covid_sim)
                    .addComponent(rb_recebeu_vacina_covid_nao)
                    .addComponent(rb_recebeu_vacina_covid_nao_sabe))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel82)
                    .addComponent(rb_recebeu_vacina_influenza_sim)
                    .addComponent(rb_recebeu_vacina_influenza_nao)
                    .addComponent(rb_recebeu_vacina_influenza_nao_sabe))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel83)
                    .addComponent(rb_recebeu_vacina_tetano_sim)
                    .addComponent(rb_recebeu_vacina_tetano_nao)
                    .addComponent(rb_recebeu_vacina_tetano_nao_sabe))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel84)
                    .addComponent(rb_recebeu_vacina_hepatite_b_sim)
                    .addComponent(rb_recebeu_vacina_hepatite_b_nao)
                    .addComponent(rb_recebeu_vacina_hepatite_b_nao_sabe))
                .addGap(18, 18, 18)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel85)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel86)
                    .addComponent(cb_ofertar_vaciana_nao))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cb_ofertar_vacina_hepatite_b)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cb_ofertar_vacina_febre_amarela)
                        .addComponent(tf_ofertar_vacina_outra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel31)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cb_ofertar_vacina_covid)
                    .addComponent(cb_ofertar_vacina_dupla_adulto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cb_ofertar_vacina_influenza)
                    .addComponent(cb_ofertar_vaciana_triplice_viral))
                .addGap(10, 10, 10)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel87)
                    .addComponent(rb_ofertar_copia_carteira_vacinacao_sim)
                    .addComponent(rb_ofertar_copia_carteira_vacinacao_nao))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(condicoes_de_saude, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(atendimento_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(identificacao_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saude_da_mulher_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saude_homem_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_cancelar)
                        .addGap(18, 18, 18)
                        .addComponent(btn_salvar))
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(atendimento_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(identificacao_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(condicoes_de_saude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saude_da_mulher_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saude_homem_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancelar)
                    .addComponent(btn_salvar))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        parent.showPanel("buscaPanel");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        parent.showPanel("buscaPanel");
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarActionPerformed
        try {
            // Validar formulário
            if (!validarFormulario()) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Por favor, preencha todos os campos obrigatórios.",
                        "Validação",
                        javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Coletar dados do formulário
            Prisioneiro prisioneiro = coletarDadosFormulario();

            if (prisioneiro == null) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Erro ao coletar dados do formulário. Verifique os dados inseridos.",
                        "Erro",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean sucesso = false;
            String mensagem = "";

            if (prisioneiroAtual != null) {
                // Editando prisioneiro existente
                sucesso = prisioneiroDao.updatePrisioneiro(prisioneiro);
                if (sucesso) {
                    mensagem = "Prisioneiro atualizado com sucesso!";
                    System.out.println("Prisioneiro atualizado com sucesso!");
                } else {
                    mensagem = "Erro ao atualizar prisioneiro";
                    System.out.println("Erro ao atualizar prisioneiro");
                }
            } else {
                // Novo cadastro - inserir prisioneiro e atendimento
                int idPrisioneiro = prisioneiroDao.insertPrisioneiro(prisioneiro);

                if (idPrisioneiro > 0) {
                    System.out.println("Prisioneiro inserido com sucesso! ID: " + idPrisioneiro);

                    // Criar e inserir o atendimento
                    Atendimento atendimento = new Atendimento();
                    atendimento.setIdPrisioneiro(idPrisioneiro);

                    // Obter ID do atendente selecionado
                    String nomeAtendente = jComboBoxResponsavel.getSelectedItem().toString();
                    int idAtendente = atendenteDao.getIdAtendenteByName(nomeAtendente);

                    if (idAtendente == -1) {
                        javax.swing.JOptionPane.showMessageDialog(this,
                                "Erro: Atendente não encontrado. Selecione um atendente válido.",
                                "Erro",
                                javax.swing.JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    atendimento.setIdAtendente(idAtendente);
                    atendimento.setDataHora(new java.sql.Timestamp(System.currentTimeMillis()));
                    atendimento.setDataEntradaUnidade(new java.util.Date());
                    atendimento.setIsTransferencia(rb_transferencia_sim.isSelected());
                    atendimento.setProcedencia(tf_transferencia.getText().trim().isEmpty()
                            ? "Não informado" : tf_transferencia.getText().trim());

                    boolean atendimentoSucesso = atendimentoDao.insertAtendimento(atendimento);

                    if (atendimentoSucesso) {
                        mensagem = "Prisioneiro e atendimento cadastrados com sucesso!";
                        System.out.println("Atendimento inserido com sucesso!");
                        sucesso = true;
                    } else {
                        mensagem = "Prisioneiro cadastrado, mas erro ao inserir atendimento";
                        System.out.println("Erro ao inserir atendimento");
                    }
                } else {
                    mensagem = "Erro ao inserir prisioneiro";
                    System.out.println("Erro ao inserir prisioneiro");
                }
            }

            if (sucesso) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        mensagem,
                        "Sucesso",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                parent.showPanel("buscaPanel");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this,
                        mensagem,
                        "Erro",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Erro inesperado: " + e.getMessage(),
                    "Erro",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_salvarActionPerformed

    private void rb_nacionalidade_naturalizadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_nacionalidade_naturalizadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_nacionalidade_naturalizadoActionPerformed

    private void tf_data_nascimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_data_nascimentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_data_nascimentoActionPerformed

    private void tf_hist_doenc_outraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_hist_doenc_outraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_hist_doenc_outraActionPerformed

    private void tf_hist_doenc_obsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_hist_doenc_obsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_hist_doenc_obsActionPerformed

    private void tf_doenca_pele_quaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_doenca_pele_quaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_doenca_pele_quaisActionPerformed

    private void tf_cond_croni_outraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_cond_croni_outraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_cond_croni_outraActionPerformed

    private void tf_cond_croni_quaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_cond_croni_quaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_cond_croni_quaisActionPerformed

    private void tf_med_continuoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_med_continuoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_med_continuoActionPerformed

    private void tf_possui_outro_dependente_qtsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_possui_outro_dependente_qtsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_possui_outro_dependente_qtsActionPerformed

    private void rb_gestacao_no_momvento_nao1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_gestacao_no_momvento_nao1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_gestacao_no_momvento_nao1ActionPerformed

    private void tf_exame_prostata_anoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_exame_prostata_anoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_exame_prostata_anoActionPerformed

    private void rb_exame_prostata_simActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_exame_prostata_simActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_exame_prostata_simActionPerformed

    private void rb_historico_prostata_simActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_historico_prostata_simActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_historico_prostata_simActionPerformed

    private void rb_historico_prostata_familiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_historico_prostata_familiarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_historico_prostata_familiarActionPerformed

    private void rb_realizou_vasectomia_simActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_realizou_vasectomia_simActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_realizou_vasectomia_simActionPerformed

    private void rb_parceira_gestante_simActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_parceira_gestante_simActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_parceira_gestante_simActionPerformed

    private void rb_esta_participando_pre_natal_simActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_esta_participando_pre_natal_simActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_esta_participando_pre_natal_simActionPerformed

    private void rb_encaminhar_realiz_vasectomia_simActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_encaminhar_realiz_vasectomia_simActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_encaminhar_realiz_vasectomia_simActionPerformed

    private void rb_encaminhar_pre_natal_parceiro_simActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_encaminhar_pre_natal_parceiro_simActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_encaminhar_pre_natal_parceiro_simActionPerformed

    private void cb_ofertar_vacina_hepatite_bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_ofertar_vacina_hepatite_bActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_ofertar_vacina_hepatite_bActionPerformed

    private void cb_ofertar_vaciana_hepatite_b1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_ofertar_vaciana_hepatite_b1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_ofertar_vaciana_hepatite_b1ActionPerformed

    private void cb_ofertar_vacina_covidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_ofertar_vacina_covidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_ofertar_vacina_covidActionPerformed

    private void cb_ofertar_vacina_influenzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_ofertar_vacina_influenzaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_ofertar_vacina_influenzaActionPerformed

    private void cb_ofertar_vacina_febre_amarelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_ofertar_vacina_febre_amarelaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_ofertar_vacina_febre_amarelaActionPerformed

    private void cb_ofertar_vacina_dupla_adultoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_ofertar_vacina_dupla_adultoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_ofertar_vacina_dupla_adultoActionPerformed

    private void cb_ofertar_vaciana_triplice_viralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_ofertar_vaciana_triplice_viralActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_ofertar_vaciana_triplice_viralActionPerformed

    private void tf_nomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_nomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_nomeActionPerformed

    private void jComboBoxResponsavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxResponsavelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxResponsavelActionPerformed

    private void tf_vinculo_caps_nomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_vinculo_caps_nomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_vinculo_caps_nomeActionPerformed

    private void tf_vinculo_caps_municipioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_vinculo_caps_municipioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_vinculo_caps_municipioActionPerformed

    private void tf_usa_med_controlado_qualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_usa_med_controlado_qualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_usa_med_controlado_qualActionPerformed

    private void tf_acompanhamento_saude_mental_no_momento_da_prisao_qualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_acompanhamento_saude_mental_no_momento_da_prisao_qualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_acompanhamento_saude_mental_no_momento_da_prisao_qualActionPerformed

    private void tf_ja_realizou_trat_para_cessar_reduzir_uso_qualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_ja_realizou_trat_para_cessar_reduzir_uso_qualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_ja_realizou_trat_para_cessar_reduzir_uso_qualActionPerformed

    private void cb_gostaria_realizar_trat_para_cessar_reduzir_usoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_gostaria_realizar_trat_para_cessar_reduzir_usoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_gostaria_realizar_trat_para_cessar_reduzir_usoActionPerformed

    private void tf_gostaria_realizar_trat_para_cessar_reduzir_uso_qual_subsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_gostaria_realizar_trat_para_cessar_reduzir_uso_qual_subsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_gostaria_realizar_trat_para_cessar_reduzir_uso_qual_subsActionPerformed

    private void cb_hist_doencas_infec_tuberculoseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_hist_doencas_infec_tuberculoseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_hist_doencas_infec_tuberculoseActionPerformed

    private void cb_hist_doencas_infec_hepatite_bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_hist_doencas_infec_hepatite_bActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_hist_doencas_infec_hepatite_bActionPerformed

    private void cb_hist_doencas_infec_hepatite_cActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_hist_doencas_infec_hepatite_cActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_hist_doencas_infec_hepatite_cActionPerformed

    private void rb_papanicolau_simActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_papanicolau_simActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_papanicolau_simActionPerformed

    private void tf_papanicolau_anoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_papanicolau_anoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_papanicolau_anoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel atendimento_panel;
    private javax.swing.ButtonGroup bg_autoimune;
    private javax.swing.ButtonGroup bg_consultar_exame_preventivo;
    private javax.swing.ButtonGroup bg_conversa_assistencia_social;
    private javax.swing.ButtonGroup bg_diabetes;
    private javax.swing.ButtonGroup bg_enc_pre_natal;
    private javax.swing.ButtonGroup bg_encaminhar_pre_natal_do_parceiro;
    private javax.swing.ButtonGroup bg_encaminhar_realiz_vasectomia;
    private javax.swing.ButtonGroup bg_escolaridade;
    private javax.swing.ButtonGroup bg_esta_participando_pre_natal;
    private javax.swing.ButtonGroup bg_estava_acomp_saude_mental_momento_prisao;
    private javax.swing.ButtonGroup bg_exame_prostata;
    private javax.swing.ButtonGroup bg_fam_rec_beneficio;
    private javax.swing.ButtonGroup bg_gestacao_no_momento;
    private javax.swing.ButtonGroup bg_hepatite_b;
    private javax.swing.ButtonGroup bg_hipertencao;
    private javax.swing.ButtonGroup bg_historico_prostata_familia;
    private javax.swing.ButtonGroup bg_hiv;
    private javax.swing.ButtonGroup bg_hpv;
    private javax.swing.ButtonGroup bg_ja_realizou_trat_para_cessar_reduzir_uso;
    private javax.swing.ButtonGroup bg_med_continuo;
    private javax.swing.ButtonGroup bg_nacionalidade;
    private javax.swing.ButtonGroup bg_neeja;
    private javax.swing.ButtonGroup bg_ofertar_continuidade_contraceptivo;
    private javax.swing.ButtonGroup bg_ofertar_copia_carteira_vacinacao;
    private javax.swing.ButtonGroup bg_parceira_gestante;
    private javax.swing.ButtonGroup bg_possui_deficiencia;
    private javax.swing.ButtonGroup bg_possui_denca_de_pele;
    private javax.swing.ButtonGroup bg_possui_filhos;
    private javax.swing.ButtonGroup bg_possui_intolerancia;
    private javax.swing.ButtonGroup bg_possuio_outro_dependente;
    private javax.swing.ButtonGroup bg_realizou_cirurgia;
    private javax.swing.ButtonGroup bg_realizou_papanicolau;
    private javax.swing.ButtonGroup bg_realizou_vasectomia;
    private javax.swing.ButtonGroup bg_recebeu_vacina_covid;
    private javax.swing.ButtonGroup bg_recebeu_vacina_hepatite_b;
    private javax.swing.ButtonGroup bg_recebeu_vacina_influenza;
    private javax.swing.ButtonGroup bg_recebeu_vacina_tetano;
    private javax.swing.ButtonGroup bg_sifilis;
    private javax.swing.ButtonGroup bg_tipo_met_anticoncepcional;
    private javax.swing.ButtonGroup bg_tipo_sangue;
    private javax.swing.ButtonGroup bg_transferencia;
    private javax.swing.ButtonGroup bg_tuberculose;
    private javax.swing.ButtonGroup bg_usa_medicamento_controlado;
    private javax.swing.ButtonGroup bg_vinculo_caps;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_salvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox cb_cond_cron_nao_sabe_responder;
    private javax.swing.JCheckBox cb_cond_cron_possui_HIV;
    private javax.swing.JCheckBox cb_cond_cron_possui_autoimune;
    private javax.swing.JCheckBox cb_cond_cron_possui_diabetes;
    private javax.swing.JCheckBox cb_cond_cron_possui_hipertensao;
    private javax.swing.JCheckBox cb_faz_uso_de_alccol;
    private javax.swing.JCheckBox cb_faz_uso_de_anfetaminas;
    private javax.swing.JCheckBox cb_faz_uso_de_cigarro;
    private javax.swing.JCheckBox cb_faz_uso_de_cocaina;
    private javax.swing.JCheckBox cb_faz_uso_de_crack;
    private javax.swing.JCheckBox cb_faz_uso_de_drogas_k;
    private javax.swing.JCheckBox cb_faz_uso_de_maconha;
    private javax.swing.JCheckBox cb_gostaria_realizar_trat_para_cessar_reduzir_uso;
    private javax.swing.JCheckBox cb_hist_doencas_infec_hepatite_b;
    private javax.swing.JCheckBox cb_hist_doencas_infec_hepatite_c;
    private javax.swing.JCheckBox cb_hist_doencas_infec_hpv;
    private javax.swing.JCheckBox cb_hist_doencas_infec_nao_sabe_responder;
    private javax.swing.JCheckBox cb_hist_doencas_infec_sifilis;
    private javax.swing.JCheckBox cb_hist_doencas_infec_tuberculose;
    private javax.swing.JComboBox<String> cb_identidade_genero;
    private javax.swing.JCheckBox cb_ofertar_vaciana_hepatite_b1;
    private javax.swing.JCheckBox cb_ofertar_vaciana_nao;
    private javax.swing.JCheckBox cb_ofertar_vaciana_triplice_viral;
    private javax.swing.JCheckBox cb_ofertar_vacina_covid;
    private javax.swing.JCheckBox cb_ofertar_vacina_dupla_adulto;
    private javax.swing.JCheckBox cb_ofertar_vacina_febre_amarela;
    private javax.swing.JCheckBox cb_ofertar_vacina_hepatite_b;
    private javax.swing.JCheckBox cb_ofertar_vacina_influenza;
    private javax.swing.JComboBox<String> cb_orientacao_sexual;
    private javax.swing.JCheckBox cb_possui_ansiedade;
    private javax.swing.JCheckBox cb_possui_autismo;
    private javax.swing.JCheckBox cb_possui_bipolaridade;
    private javax.swing.JCheckBox cb_possui_depressao;
    private javax.swing.JCheckBox cb_possui_esquisofrenia;
    private javax.swing.JComboBox<String> cb_sexo_biologico;
    private javax.swing.JCheckBox cb_transt_mentais_n_sabe_resp;
    private javax.swing.JPanel condicoes_de_saude;
    private javax.swing.JPanel identificacao_panel;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBoxResponsavel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JLabel jLabelData;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JRadioButton rb_acompanhamento_saude_mental_no_momento_da_prisao_nao;
    private javax.swing.JRadioButton rb_acompanhamento_saude_mental_no_momento_da_prisao_sim;
    private javax.swing.JRadioButton rb_consultar_exame_preventivo_nao;
    private javax.swing.JRadioButton rb_consultar_exame_preventivo_sim;
    private javax.swing.JRadioButton rb_conversa_assistencia_social_nao;
    private javax.swing.JRadioButton rb_conversa_assistencia_social_sim;
    private javax.swing.JRadioButton rb_doenca_pele_nao;
    private javax.swing.JRadioButton rb_doenca_pele_sim;
    private javax.swing.JRadioButton rb_enc_pre_natal_nao;
    private javax.swing.JRadioButton rb_enc_pre_natal_sim;
    private javax.swing.JRadioButton rb_encaminhar_pre_natal_parceiro_nao;
    private javax.swing.JRadioButton rb_encaminhar_pre_natal_parceiro_sim;
    private javax.swing.JRadioButton rb_encaminhar_realiz_vasectomia_nao;
    private javax.swing.JRadioButton rb_encaminhar_realiz_vasectomia_sim;
    private javax.swing.JRadioButton rb_escolaridade_fund_com;
    private javax.swing.JRadioButton rb_escolaridade_fund_com1;
    private javax.swing.JRadioButton rb_escolaridade_fund_inc;
    private javax.swing.JRadioButton rb_escolaridade_med_com;
    private javax.swing.JRadioButton rb_escolaridade_med_com1;
    private javax.swing.JRadioButton rb_escolaridade_med_inc;
    private javax.swing.JRadioButton rb_escolaridade_med_inc1;
    private javax.swing.JRadioButton rb_escolaridade_sup_com;
    private javax.swing.JRadioButton rb_escolaridade_sup_com1;
    private javax.swing.JRadioButton rb_escolaridade_sup_inc;
    private javax.swing.JRadioButton rb_escolaridade_sup_inc1;
    private javax.swing.JRadioButton rb_esta_participando_pre_natal_nao;
    private javax.swing.JRadioButton rb_esta_participando_pre_natal_sim;
    private javax.swing.JRadioButton rb_exame_prostata_nao;
    private javax.swing.JRadioButton rb_exame_prostata_sim;
    private javax.swing.JRadioButton rb_fam_rec_beneficio_nao;
    private javax.swing.JRadioButton rb_fam_rec_beneficio_sim;
    private javax.swing.JRadioButton rb_gestacao_no_momvento_nao;
    private javax.swing.JRadioButton rb_gestacao_no_momvento_nao1;
    private javax.swing.JRadioButton rb_gestacao_no_momvento_nao_sabe;
    private javax.swing.JRadioButton rb_gestacao_no_momvento_sim;
    private javax.swing.JTextField rb_historico_prostata_familiar;
    private javax.swing.JRadioButton rb_historico_prostata_nao;
    private javax.swing.JRadioButton rb_historico_prostata_sim;
    private javax.swing.JRadioButton rb_ja_realizou_trat_para_cessar_reduzir_uso_nao;
    private javax.swing.JRadioButton rb_ja_realizou_trat_para_cessar_reduzir_uso_sim;
    private javax.swing.JRadioButton rb_nacionalidade_brasileira;
    private javax.swing.JRadioButton rb_nacionalidade_estrangeiro;
    private javax.swing.JRadioButton rb_nacionalidade_naturalizado;
    private javax.swing.JRadioButton rb_ofertar_continuidade_contraceptivo_nao;
    private javax.swing.JRadioButton rb_ofertar_continuidade_contraceptivo_sim;
    private javax.swing.JRadioButton rb_ofertar_copia_carteira_vacinacao_nao;
    private javax.swing.JRadioButton rb_ofertar_copia_carteira_vacinacao_sim;
    private javax.swing.JRadioButton rb_ofertar_neeja_nao;
    private javax.swing.JRadioButton rb_ofertar_neeja_sim;
    private javax.swing.JRadioButton rb_papanicolau_nao;
    private javax.swing.JRadioButton rb_papanicolau_sim;
    private javax.swing.JRadioButton rb_parceira_gestante_nao;
    private javax.swing.JRadioButton rb_parceira_gestante_sim;
    private javax.swing.JRadioButton rb_poss_filhos_nao;
    private javax.swing.JRadioButton rb_poss_filhos_sim;
    private javax.swing.JRadioButton rb_possui_alergia_nao;
    private javax.swing.JRadioButton rb_possui_alergia_nao_sei;
    private javax.swing.JRadioButton rb_possui_alergia_sim;
    private javax.swing.JRadioButton rb_possui_deficiencia_nao;
    private javax.swing.JRadioButton rb_possui_deficiencia_nao_sei;
    private javax.swing.JRadioButton rb_possui_deficiencia_sim;
    private javax.swing.JRadioButton rb_possui_outro_dependente_nao;
    private javax.swing.JRadioButton rb_possui_outro_dependente_sim;
    private javax.swing.JRadioButton rb_realizou_cirurgia_nao;
    private javax.swing.JRadioButton rb_realizou_cirurgia_nao_sei;
    private javax.swing.JRadioButton rb_realizou_cirurgia_sim;
    private javax.swing.JRadioButton rb_realizou_vasectomia_nao;
    private javax.swing.JRadioButton rb_realizou_vasectomia_sim;
    private javax.swing.JRadioButton rb_recebeu_vacina_covid_nao;
    private javax.swing.JRadioButton rb_recebeu_vacina_covid_nao_sabe;
    private javax.swing.JRadioButton rb_recebeu_vacina_covid_sim;
    private javax.swing.JRadioButton rb_recebeu_vacina_hepatite_b_nao;
    private javax.swing.JRadioButton rb_recebeu_vacina_hepatite_b_nao_sabe;
    private javax.swing.JRadioButton rb_recebeu_vacina_hepatite_b_sim;
    private javax.swing.JRadioButton rb_recebeu_vacina_influenza_nao;
    private javax.swing.JRadioButton rb_recebeu_vacina_influenza_nao_sabe;
    private javax.swing.JRadioButton rb_recebeu_vacina_influenza_sim;
    private javax.swing.JRadioButton rb_recebeu_vacina_tetano_nao;
    private javax.swing.JRadioButton rb_recebeu_vacina_tetano_nao_sabe;
    private javax.swing.JRadioButton rb_recebeu_vacina_tetano_sim;
    private javax.swing.JRadioButton rb_tipo_sangue_abm;
    private javax.swing.JRadioButton rb_tipo_sangue_abp;
    private javax.swing.JRadioButton rb_tipo_sangue_am;
    private javax.swing.JRadioButton rb_tipo_sangue_ap;
    private javax.swing.JRadioButton rb_tipo_sangue_bm;
    private javax.swing.JRadioButton rb_tipo_sangue_bp;
    private javax.swing.JRadioButton rb_tipo_sangue_nao_sabe;
    private javax.swing.JRadioButton rb_tipo_sangue_om;
    private javax.swing.JRadioButton rb_tipo_sangue_op;
    private javax.swing.JRadioButton rb_transferencia_nao;
    private javax.swing.JRadioButton rb_transferencia_sim;
    private javax.swing.JRadioButton rb_usa_med_continuo_nao;
    private javax.swing.JRadioButton rb_usa_med_continuo_sim;
    private javax.swing.JRadioButton rb_usa_med_controlado_nao;
    private javax.swing.JRadioButton rb_usa_med_controlado_nao_sabe;
    private javax.swing.JRadioButton rb_usa_med_controlado_sim;
    private javax.swing.JRadioButton rb_vinculo_caps_nao;
    private javax.swing.JRadioButton rb_vinculo_caps_sim;
    private javax.swing.JPanel saude_da_mulher_panel;
    private javax.swing.JPanel saude_homem_panel;
    private javax.swing.JTextField tf_acompanhamento_saude_mental_no_momento_da_prisao_qual;
    private javax.swing.JTextField tf_cond_croni_outra;
    private javax.swing.JTextField tf_cond_croni_quais;
    private javax.swing.JTextField tf_cpf;
    private javax.swing.JTextField tf_data_nascimento;
    private javax.swing.JTextField tf_deficiencia_quais;
    private javax.swing.JTextField tf_doenca_pele_quais;
    private javax.swing.JTextField tf_etinia;
    private javax.swing.JTextField tf_exame_prostata_ano;
    private javax.swing.JTextField tf_fam_rec_beneficio_quais;
    private javax.swing.JTextField tf_faz_uso_de_outras;
    private javax.swing.JTextField tf_gostaria_realizar_trat_para_cessar_reduzir_uso_qual_subs;
    private javax.swing.JTextField tf_hist_doenc_obs;
    private javax.swing.JTextField tf_hist_doenc_outra;
    private javax.swing.JTextField tf_idade;
    private javax.swing.JTextField tf_idade_filhos;
    private javax.swing.JTextField tf_ja_realizou_trat_para_cessar_reduzir_uso_qual;
    private javax.swing.JTextField tf_med_continuo;
    private javax.swing.JTextField tf_nacionalidade_qual_pais;
    private javax.swing.JTextField tf_nome;
    private javax.swing.JTextField tf_nome_mae;
    private javax.swing.JTextField tf_nome_social;
    private javax.swing.JTextField tf_ofertar_vacina_outra;
    private javax.swing.JTextField tf_orientacao_sexual_outra;
    private javax.swing.JTextField tf_papanicolau_ano;
    private javax.swing.JTextField tf_poss_filhos_quantos;
    private javax.swing.JTextField tf_possui_intolerancia_quais;
    private javax.swing.JTextField tf_possui_outro_dependente_qts;
    private javax.swing.JTextField tf_possui_trasntorno_mental_outro;
    private javax.swing.JTextField tf_realizou_cirurgia_quais;
    private javax.swing.JTextField tf_tipo_sangue;
    private javax.swing.JTextField tf_transferencia;
    private javax.swing.JTextField tf_usa_med_controlado_qual;
    private javax.swing.JTextField tf_vinculo_caps_municipio;
    private javax.swing.JTextField tf_vinculo_caps_nome;
    // End of variables declaration//GEN-END:variables
}
