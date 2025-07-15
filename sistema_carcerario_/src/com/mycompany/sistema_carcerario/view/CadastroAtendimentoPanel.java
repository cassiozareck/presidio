/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.sistema_carcerario.view;

import DAL.AtendenteDao;
import DAL.AtendimentoDao;
import DAL.PrisioneiroDao;
import com.mycompany.sistema_carcerario.controller.CheckBoxController;
import com.mycompany.sistema_carcerario.controller.RadioButtonController;
import com.mycompany.sistema_carcerario.model.Atendente;
import com.mycompany.sistema_carcerario.model.Atendimento;
import com.mycompany.sistema_carcerario.model.Prisioneiro;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

/**
 *
 * @author fernando
 */
public class CadastroAtendimentoPanel extends javax.swing.JPanel {
    final MainFrame parent;
    private final RadioButtonController rbController = new RadioButtonController();
    private final CheckBoxController cbController = new CheckBoxController();
    
    private final AtendenteDao atendenteDao = new AtendenteDao();
    private final PrisioneiroDao prisioneiroDao = new PrisioneiroDao();
    private final AtendimentoDao atendimentoDao = new AtendimentoDao();

    /**
     * Creates new form CadastroAtendimentoPanel
     */
    public CadastroAtendimentoPanel(MainFrame parent) {
        initComponents();
        this.parent = parent;
        setComboBoxResponsavel();
        setComboBoxDetento();
        configurarTextFieldCondicionais();
    }
        
    // Popula o combobox jComboBoxResponsavel com nome dos atendentes cadastrados.
    private void setComboBoxResponsavel(){
        jComboBoxResponsavel.removeAllItems();
        ArrayList <Atendente> atendentes = atendenteDao.getAtendentes();
        for(Atendente atendente : atendentes){
            jComboBoxResponsavel.addItem(atendente.getNome());
        }
    }

    // Popula o combobox jComboBoxDetento com nome dos detentos cadastrados.
    private void setComboBoxDetento(){
        jComboBoxDetento.removeAllItems();
        ArrayList <Prisioneiro> prisioneiros = prisioneiroDao.getNomesPrisioneiros();
        for(Prisioneiro prisioneiro : prisioneiros){
            jComboBoxDetento.addItem(prisioneiro.getNomeCompleto());
        }
    }     
    
    private void configurarTextFieldCondicionais() {

        // Identificação
        rbController.configurarRadioGroup(bg_transferencia, rb_transferencia_sim, rb_transferencia_nao, tf_transferencia);
        rbController.configurarRadioGroup(bg_possui_lesoes_ferimentos, rb_apresenta_lesoes_sim, rb_apresenta_lesoes_nao, tf_lesoes_locais);
        rbController.configurarRadioGroup(bg_apresenta_outra_queixa, rb_queixa_sim, rb_queixa_nao, tf_quais_queixas);
        rbController.configurarRadioGroup(bg_apresenta_queixa_odontologica, rb_queixa_odontologica_sim, rb_queixa_odontologica_nao, tf_quais_queixas_odontologicas);        
    }

    public void carregarAtendimento (int idAtendimento){
        Atendimento atendimentoAtual = atendimentoDao.buscarAtendimentoPorId(idAtendimento);
        
        if (atendimentoAtual == null) {
            return;
        }
        
        setComboBoxValue(jComboBoxResponsavel, atendenteDao.getNomeAtendenteByID(atendimentoAtual.getIdAtendente()));
        LocalDateTime dataHora = atendimentoAtual.getDataHora().toLocalDateTime();
        String dataFormatada = dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        label_data.setText(dataFormatada);
        //bg_transferencia
        setComboBoxValue(jComboBoxDetento, atendenteDao.getNomeAtendenteByID(atendimentoAtual.getIdPrisioneiro()));
        //Antropometria
        tf_peso.setText(String.valueOf(atendimentoAtual.getPeso()));
        tf_altura.setText(String.valueOf(atendimentoAtual.getAltura()));
        tf_imc.setText(String.valueOf(atendimentoAtual.getImc()));
        //Sinais vitais
        tf_pa.setText(atendimentoAtual.getPa());
        tf_fc.setText(String.valueOf(atendimentoAtual.getFc()));
        tf_sat.setText(String.valueOf(atendimentoAtual.getSat()));
        tf_temp.setText(String.valueOf(atendimentoAtual.getTemp()));
        
        //Apresenta sintomas resporatórios?
        // ISSO AQUI QUEBRA O CODIGO -> rbController.selecionarRadioButtonPorValor(bg_possui_tosse, String.valueOf(atendimentoAtual.isTosse()));
        //tosse   (BG)
        //corriza (BG)
        //espirros (BG)
        //febre (BG)
        //calafrios (BG)
        tf_atendimento_clinico_outros.setText(atendimentoAtual.getOutrosSistemasRespiratorios());
        tf_inicio_simtomas.setText(dataFormatada);
        
        //apresenta lesoes (BG) Onde?
        ta_atendimento_clinico_conduta.setText(atendimentoAtual.getCondutaClinica());
        
        //HIV(1/2)
        jt_lote_hiv1_2.setText(atendimentoAtual.getHiv12Lote());
        //tf_validade_hiv1_2.setText(atendimentoAtual.getHiv12Validade());
        //R NR E NÃO REALIZADO (BG)
        
        //HIV(2/2)
        jt_lote_hiv2_2.setText(atendimentoAtual.getHiv22Lote());
        //tf_validade_hiv2_2.setText(atendimentoAtual.getHiv22Validade());
        //R NR E NÃO REALIZADO (BG)
        
        //Sifilis
        jt_lote_sifilis.setText(atendimentoAtual.getSifilisLote());
        //tf_validade_sifilis.setText(atendimentoAtual.getSifilisValidade());
        //R NR E NÃO REALIZADO (BG)
        
        //Hepatite B
        jt_lote_hepatite_b.setText(atendimentoAtual.getHepatiteBLote());
        //tf_validade_hepatite_b.setText(atendimentoAtual.getHepatiteBValidade());
        //R NR E NÃO REALIZADO (BG)
        
        //Hepatite C
        jt_lote_hepatite_c.setText(atendimentoAtual.getHepatiteCLote());
        //tf_validade_hepatite_c.setText(atendimentoAtual.getHepatiteCValidade());
        //R NR E NÃO REALIZADO (BG)
        
        //Covid
        jt_lote_covid.setText(atendimentoAtual.getCovidLote());
        //tf_validade_covid.setText(atendimentoAtual.getCovidValidade());
        //R NR E NÃO REALIZADO (BG)
        
        //Teste de gravides (BG)
        
        //Coleta de escarro (BG)
        
    }
    
    private void setComboBoxValue(javax.swing.JComboBox<String> comboBox, String value) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).equalsIgnoreCase(value)) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private String formatarData(Date data) {
    return data.toInstant()
               .atZone(ZoneId.systemDefault())
               .toLocalDate()
               .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg_transferencia = new javax.swing.ButtonGroup();
        bg_possui_tosse = new javax.swing.ButtonGroup();
        bg_possui_coriza = new javax.swing.ButtonGroup();
        jLabel35 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        dataBase1 = new com.mycompany.sistema_carcerario.model.DataBase();
        bg_possui_febre = new javax.swing.ButtonGroup();
        bg_possui_espirros = new javax.swing.ButtonGroup();
        bg_possui_calafrios = new javax.swing.ButtonGroup();
        bg_possui_lesoes_ferimentos = new javax.swing.ButtonGroup();
        bg_tst_rap_hiv_1 = new javax.swing.ButtonGroup();
        bg_tst_rap_hiv_2 = new javax.swing.ButtonGroup();
        bg_tst_rap_sifilis = new javax.swing.ButtonGroup();
        bg_tst_rap_hepatite_b = new javax.swing.ButtonGroup();
        bg_tst_rap_hepatite_c = new javax.swing.ButtonGroup();
        bg_tst_rap_covid = new javax.swing.ButtonGroup();
        bg_tst_gravidez = new javax.swing.ButtonGroup();
        bg_coleta_escarro = new javax.swing.ButtonGroup();
        bg_apresenta_outra_queixa = new javax.swing.ButtonGroup();
        bg_apresenta_queixa_odontologica = new javax.swing.ButtonGroup();
        bg_necessita_aval_imediat_dentista = new javax.swing.ButtonGroup();
        atendimento_panel = new javax.swing.JPanel();
        jLabelData = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tf_peso = new javax.swing.JTextField();
        tf_altura = new javax.swing.JTextField();
        tf_imc = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        tf_pa = new javax.swing.JTextField();
        tf_fc = new javax.swing.JTextField();
        tf_sat = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        tf_temp = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        tf_atendimento_clinico_outros = new javax.swing.JTextField();
        rb_tosse_sim = new javax.swing.JRadioButton();
        rb_tosse_nao = new javax.swing.JRadioButton();
        rb_coriza_sim = new javax.swing.JRadioButton();
        rb_coriza_nao = new javax.swing.JRadioButton();
        rb_espirros_sim = new javax.swing.JRadioButton();
        rb_espirros_nao = new javax.swing.JRadioButton();
        rb_febre_sim = new javax.swing.JRadioButton();
        rb_febre_nao = new javax.swing.JRadioButton();
        rb_calafrios_sim = new javax.swing.JRadioButton();
        rb_calafrios_nao = new javax.swing.JRadioButton();
        jLabel29 = new javax.swing.JLabel();
        tf_inicio_simtomas = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        rb_apresenta_lesoes_sim = new javax.swing.JRadioButton();
        rb_apresenta_lesoes_nao = new javax.swing.JRadioButton();
        jLabel33 = new javax.swing.JLabel();
        tf_lesoes_locais = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_atendimento_clinico_conduta = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        rb_hiv1_2_reativo = new javax.swing.JRadioButton();
        rb_hiv1_2_nao_reativo = new javax.swing.JRadioButton();
        rb_hiv1_2_nao_realizado = new javax.swing.JRadioButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        tf_validade_hiv1_2 = new javax.swing.JTextField();
        tf_validade_hiv2_2 = new javax.swing.JTextField();
        tf_validade_sifilis = new javax.swing.JTextField();
        tf_validade_hepatite_b = new javax.swing.JTextField();
        tf_validade_hepatite_c = new javax.swing.JTextField();
        tf_validade_covid = new javax.swing.JTextField();
        jt_lote_hiv1_2 = new javax.swing.JTextField();
        jt_lote_hiv2_2 = new javax.swing.JTextField();
        jt_lote_sifilis = new javax.swing.JTextField();
        jt_lote_hepatite_b = new javax.swing.JTextField();
        jt_lote_hepatite_c = new javax.swing.JTextField();
        jt_lote_covid = new javax.swing.JTextField();
        rb_hiv2_2_nao_realizado = new javax.swing.JRadioButton();
        rb_hiv2_2_nao_reativo = new javax.swing.JRadioButton();
        rb_hiv2_2_reativo = new javax.swing.JRadioButton();
        rb_sifilis_nao_realizado = new javax.swing.JRadioButton();
        rb_sifilis_nao_reativo = new javax.swing.JRadioButton();
        rb_sifilis_reativo = new javax.swing.JRadioButton();
        rb_hepatite_b_nao_realizado = new javax.swing.JRadioButton();
        rb_hepatite_b_nao_reativo = new javax.swing.JRadioButton();
        rb_hepatite_b_reativo = new javax.swing.JRadioButton();
        rb_hepatite_c_nao_realizado = new javax.swing.JRadioButton();
        rb_hepatite_c_nao_reativo = new javax.swing.JRadioButton();
        rb_hepatite_c_reativo = new javax.swing.JRadioButton();
        rb_covid_nao_realizado = new javax.swing.JRadioButton();
        rb_covid_nao_reativo = new javax.swing.JRadioButton();
        rb_covid_reativo = new javax.swing.JRadioButton();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        rb_gravidez_negativo = new javax.swing.JRadioButton();
        rb_gravidez_nao_realizado = new javax.swing.JRadioButton();
        rb_gravidez_positivo = new javax.swing.JRadioButton();
        jLabel55 = new javax.swing.JLabel();
        rb_coleta_escarro_sim = new javax.swing.JRadioButton();
        rb_coleta_escarro_nao = new javax.swing.JRadioButton();
        jLabel56 = new javax.swing.JLabel();
        rb_queixa_sim = new javax.swing.JRadioButton();
        rb_queixa_nao = new javax.swing.JRadioButton();
        jLabel57 = new javax.swing.JLabel();
        tf_quais_queixas = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tf_conduta_testes_rapidos = new javax.swing.JTextArea();
        jLabel59 = new javax.swing.JLabel();
        rb_queixa_odontologica_nao = new javax.swing.JRadioButton();
        rb_queixa_odontologica_sim = new javax.swing.JRadioButton();
        jLabel60 = new javax.swing.JLabel();
        tf_quais_queixas_odontologicas = new javax.swing.JTextField();
        rb_necessita_dentista_nao = new javax.swing.JRadioButton();
        jLabel61 = new javax.swing.JLabel();
        rb_necessita_dentista_sim = new javax.swing.JRadioButton();
        jLabel62 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tf_conduta_odontologica = new javax.swing.JTextArea();
        jLabel63 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tf_encaminhamentos_finais = new javax.swing.JTextArea();
        bt_salvar = new javax.swing.JButton();
        bt_cancelar = new javax.swing.JButton();
        jComboBoxResponsavel = new javax.swing.JComboBox<>();
        rb_transferencia_sim = new javax.swing.JRadioButton();
        rb_transferencia_nao = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tf_transferencia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jComboBoxDetento = new javax.swing.JComboBox<>();
        label_data = new javax.swing.JLabel();

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("ATENDIMENTO CLÍNCO");

        atendimento_panel.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout atendimento_panelLayout = new javax.swing.GroupLayout(atendimento_panel);
        atendimento_panel.setLayout(atendimento_panelLayout);
        atendimento_panelLayout.setHorizontalGroup(
            atendimento_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, atendimento_panelLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabelData)
                .addContainerGap(975, Short.MAX_VALUE))
        );
        atendimento_panelLayout.setVerticalGroup(
            atendimento_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(atendimento_panelLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabelData)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("ATENDIMENTO CLÍNCO");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 1022, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addContainerGap())
        );

        jLabel1.setText("ANTROPOMETRIA");

        jLabel2.setText("Peso:");

        jLabel5.setText("Altura:");

        jLabel7.setText("IMC:");

        tf_peso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_pesoActionPerformed(evt);
            }
        });

        jLabel8.setText("kg");

        jLabel9.setText("cm");

        jLabel10.setText("kg/m²");

        jLabel11.setText("kg/m²");

        jLabel12.setText("PA:");

        jLabel13.setText("FC:");

        jLabel14.setText("SAT:");

        tf_pa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_paActionPerformed(evt);
            }
        });

        jLabel15.setText("kg");

        jLabel16.setText("cm");

        jLabel18.setText("SINAIS VITAIS");

        jLabel19.setText("Temp:");

        jLabel20.setText("ºC");

        jSeparator1.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator1.setOpaque(true);

        jLabel22.setText("Apresenta sintomas resporatórios?");

        jLabel23.setText("Tosse");

        jLabel24.setText("Coriza");

        jLabel25.setText("Espirros");

        jLabel26.setText("Febre");

        jLabel27.setText("Calafrios");

        jLabel28.setText("Outros:");

        bg_possui_tosse.add(rb_tosse_sim);
        rb_tosse_sim.setText("Sim");
        rb_tosse_sim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_tosse_simActionPerformed(evt);
            }
        });

        bg_possui_tosse.add(rb_tosse_nao);
        rb_tosse_nao.setText("Não");

        bg_possui_coriza.add(rb_coriza_sim);
        rb_coriza_sim.setText("Sim");
        rb_coriza_sim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_coriza_simActionPerformed(evt);
            }
        });

        bg_possui_coriza.add(rb_coriza_nao);
        rb_coriza_nao.setText("Não");

        bg_possui_espirros.add(rb_espirros_sim);
        rb_espirros_sim.setText("Sim");

        bg_possui_espirros.add(rb_espirros_nao);
        rb_espirros_nao.setText("Não");

        bg_possui_febre.add(rb_febre_sim);
        rb_febre_sim.setText("Sim");

        bg_possui_febre.add(rb_febre_nao);
        rb_febre_nao.setText("Não");
        rb_febre_nao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_febre_naoActionPerformed(evt);
            }
        });

        bg_possui_calafrios.add(rb_calafrios_sim);
        rb_calafrios_sim.setText("Sim");

        bg_possui_calafrios.add(rb_calafrios_nao);
        rb_calafrios_nao.setText("Não");
        rb_calafrios_nao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_calafrios_naoActionPerformed(evt);
            }
        });

        jLabel29.setText("Data do inicio dos sintomas:");

        jLabel30.setText("OBS: em caso positivo, deve ser ofertado a coleta de escarro para Tuberculose");

        jLabel31.setText("e teste para Covid-19.");

        jLabel32.setText("Apresenta lesões / ferimentos no corpo?");

        bg_possui_lesoes_ferimentos.add(rb_apresenta_lesoes_sim);
        rb_apresenta_lesoes_sim.setText("Sim");

        bg_possui_lesoes_ferimentos.add(rb_apresenta_lesoes_nao);
        rb_apresenta_lesoes_nao.setText("Não");

        jLabel33.setText("Locais:");

        tf_lesoes_locais.setEditable(false);
        tf_lesoes_locais.setEnabled(false);

        jLabel34.setText("Conduta:");

        ta_atendimento_clinico_conduta.setColumns(20);
        ta_atendimento_clinico_conduta.setRows(5);
        jScrollPane1.setViewportView(ta_atendimento_clinico_conduta);

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("TESTES RÁPIDOS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(169, 169, 169))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jLabel36)
                .addContainerGap())
        );

        jLabel37.setText("HIV(1/2)");

        jLabel38.setText("HIV(2/2)");

        jLabel39.setText("Sífilis");

        jLabel40.setText("Hepatite B");

        jLabel41.setText("Hepatite C");

        jLabel42.setText("Covid");

        jLabel43.setText("Lote:");

        jLabel44.setText("Lote:");

        jLabel45.setText("Lote:");

        jLabel46.setText("Lote:");

        jLabel47.setText("Lote:");

        jLabel48.setText("Lote:");

        jLabel49.setText("Validade:");

        jLabel50.setText("Validade:");

        jLabel51.setText("Validade:");

        jLabel52.setText("Validade:");

        jLabel53.setText("Validade:");

        jLabel54.setText("Validade:");

        bg_tst_rap_hiv_1.add(rb_hiv1_2_reativo);
        rb_hiv1_2_reativo.setText("R");
        rb_hiv1_2_reativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_hiv1_2_reativoActionPerformed(evt);
            }
        });

        bg_tst_rap_hiv_1.add(rb_hiv1_2_nao_reativo);
        rb_hiv1_2_nao_reativo.setText("NR");

        bg_tst_rap_hiv_1.add(rb_hiv1_2_nao_realizado);
        rb_hiv1_2_nao_realizado.setText("Não realizado");

        jSeparator2.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator2.setOpaque(true);

        jSeparator3.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator3.setOpaque(true);

        jSeparator4.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator4.setOpaque(true);

        bg_tst_rap_hiv_2.add(rb_hiv2_2_nao_realizado);
        rb_hiv2_2_nao_realizado.setText("Não realizado");

        bg_tst_rap_hiv_2.add(rb_hiv2_2_nao_reativo);
        rb_hiv2_2_nao_reativo.setText("NR");

        bg_tst_rap_hiv_2.add(rb_hiv2_2_reativo);
        rb_hiv2_2_reativo.setText("R");
        rb_hiv2_2_reativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_hiv2_2_reativoActionPerformed(evt);
            }
        });

        bg_tst_rap_sifilis.add(rb_sifilis_nao_realizado);
        rb_sifilis_nao_realizado.setText("Não realizado");

        bg_tst_rap_sifilis.add(rb_sifilis_nao_reativo);
        rb_sifilis_nao_reativo.setText("NR");

        bg_tst_rap_sifilis.add(rb_sifilis_reativo);
        rb_sifilis_reativo.setText("R");
        rb_sifilis_reativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_sifilis_reativoActionPerformed(evt);
            }
        });

        bg_tst_rap_hepatite_b.add(rb_hepatite_b_nao_realizado);
        rb_hepatite_b_nao_realizado.setText("Não realizado");

        bg_tst_rap_hepatite_b.add(rb_hepatite_b_nao_reativo);
        rb_hepatite_b_nao_reativo.setText("NR");

        bg_tst_rap_hepatite_b.add(rb_hepatite_b_reativo);
        rb_hepatite_b_reativo.setText("R");
        rb_hepatite_b_reativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_hepatite_b_reativoActionPerformed(evt);
            }
        });

        bg_tst_rap_hepatite_c.add(rb_hepatite_c_nao_realizado);
        rb_hepatite_c_nao_realizado.setText("Não realizado");

        bg_tst_rap_hepatite_c.add(rb_hepatite_c_nao_reativo);
        rb_hepatite_c_nao_reativo.setText("NR");

        bg_tst_rap_hepatite_c.add(rb_hepatite_c_reativo);
        rb_hepatite_c_reativo.setText("R");
        rb_hepatite_c_reativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_hepatite_c_reativoActionPerformed(evt);
            }
        });

        bg_tst_rap_covid.add(rb_covid_nao_realizado);
        rb_covid_nao_realizado.setText("Não realizado");

        bg_tst_rap_covid.add(rb_covid_nao_reativo);
        rb_covid_nao_reativo.setText("NR");

        bg_tst_rap_covid.add(rb_covid_reativo);
        rb_covid_reativo.setText("R");
        rb_covid_reativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_covid_reativoActionPerformed(evt);
            }
        });

        jLabel21.setText("Teste de gravidez:");

        bg_tst_gravidez.add(rb_gravidez_negativo);
        rb_gravidez_negativo.setText("Negativo");

        bg_tst_gravidez.add(rb_gravidez_nao_realizado);
        rb_gravidez_nao_realizado.setText("Não realizado");

        bg_tst_gravidez.add(rb_gravidez_positivo);
        rb_gravidez_positivo.setText("Positivo");

        jLabel55.setText("Coleta de escarro:");

        bg_coleta_escarro.add(rb_coleta_escarro_sim);
        rb_coleta_escarro_sim.setText("Sim");

        bg_coleta_escarro.add(rb_coleta_escarro_nao);
        rb_coleta_escarro_nao.setText("Não");

        jLabel56.setText("Apresenta alguma outra queixa?");

        bg_apresenta_outra_queixa.add(rb_queixa_sim);
        rb_queixa_sim.setText("Sim");

        bg_apresenta_outra_queixa.add(rb_queixa_nao);
        rb_queixa_nao.setText("Não");

        jLabel57.setText("Qual(is):");

        tf_quais_queixas.setEditable(false);
        tf_quais_queixas.setEnabled(false);

        jLabel58.setText("Condutas:");

        tf_conduta_testes_rapidos.setColumns(20);
        tf_conduta_testes_rapidos.setRows(5);
        jScrollPane2.setViewportView(tf_conduta_testes_rapidos);

        jLabel59.setText("Apresenta alguma queixa odontológica?");

        bg_apresenta_queixa_odontologica.add(rb_queixa_odontologica_nao);
        rb_queixa_odontologica_nao.setText("Não");

        bg_apresenta_queixa_odontologica.add(rb_queixa_odontologica_sim);
        rb_queixa_odontologica_sim.setText("Sim");

        jLabel60.setText("Qual(is):");

        tf_quais_queixas_odontologicas.setEditable(false);
        tf_quais_queixas_odontologicas.setEnabled(false);

        bg_necessita_aval_imediat_dentista.add(rb_necessita_dentista_nao);
        rb_necessita_dentista_nao.setText("Não");

        jLabel61.setText("Necessita avaliação imediata de dentista:");

        bg_necessita_aval_imediat_dentista.add(rb_necessita_dentista_sim);
        rb_necessita_dentista_sim.setText("Sim");

        jLabel62.setText("Conduta:");

        tf_conduta_odontologica.setColumns(20);
        tf_conduta_odontologica.setRows(5);
        jScrollPane3.setViewportView(tf_conduta_odontologica);

        jLabel63.setText("ENCAMINHAMENTOS FINAIS:");

        tf_encaminhamentos_finais.setColumns(20);
        tf_encaminhamentos_finais.setRows(5);
        jScrollPane4.setViewportView(tf_encaminhamentos_finais);

        bt_salvar.setText("Salvar");
        bt_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_salvarActionPerformed(evt);
            }
        });

        bt_cancelar.setText("Cancelar");
        bt_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cancelarActionPerformed(evt);
            }
        });

        jComboBoxResponsavel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setText("IDENTIFICAÇÃO DO DETENTO");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jLabel64)
                .addContainerGap())
        );

        jLabel65.setText("Nome completo:");

        jComboBoxDetento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxDetento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDetentoActionPerformed(evt);
            }
        });

        label_data.setText("jLabel66");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rb_apresenta_lesoes_sim)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_apresenta_lesoes_nao)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tf_lesoes_locais))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel34)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 772, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 239, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabel19)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(tf_temp))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel14)
                                                    .addComponent(jLabel12)
                                                    .addComponent(jLabel13))
                                                .addGap(24, 24, 24)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(tf_fc, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(tf_sat)
                                                    .addComponent(tf_pa, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel20)
                                            .addComponent(jLabel15)
                                            .addComponent(jLabel16)
                                            .addComponent(jLabel11)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addGap(18, 18, 18)
                                                .addComponent(tf_imc))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel2)
                                                    .addComponent(jLabel5))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(tf_peso)
                                                    .addComponent(tf_altura, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel10)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(80, 80, 80)
                                        .addComponent(jLabel18)))
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(76, 76, 76)
                                        .addComponent(jLabel22))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel28)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(tf_atendimento_clinico_outros, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel23)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel27)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(rb_calafrios_sim))
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel25)
                                                                .addComponent(jLabel26))
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addGroup(layout.createSequentialGroup()
                                                                        .addGap(12, 12, 12)
                                                                        .addComponent(rb_espirros_sim))
                                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                            .addComponent(rb_coriza_sim, javax.swing.GroupLayout.Alignment.TRAILING)
                                                                            .addComponent(rb_tosse_sim, javax.swing.GroupLayout.Alignment.TRAILING))))
                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addComponent(rb_febre_sim))))
                                                        .addComponent(jLabel24)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(rb_tosse_nao)
                                                    .addComponent(rb_espirros_nao)
                                                    .addComponent(rb_febre_nao)
                                                    .addComponent(rb_calafrios_nao)
                                                    .addComponent(rb_coriza_nao)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel29)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tf_inicio_simtomas))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel31)
                                            .addComponent(jLabel30))))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(atendimento_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42)
                            .addComponent(jLabel41)
                            .addComponent(jLabel40)
                            .addComponent(jLabel39)
                            .addComponent(jLabel38)
                            .addComponent(jLabel37))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel46, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel43))
                                    .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jt_lote_hiv1_2)
                                    .addComponent(jt_lote_hiv2_2)
                                    .addComponent(jt_lote_sifilis)
                                    .addComponent(jt_lote_hepatite_b)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel47)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jt_lote_hepatite_c))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel48)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jt_lote_covid, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel50)
                            .addComponent(jLabel51)
                            .addComponent(jLabel52)
                            .addComponent(jLabel53)
                            .addComponent(jLabel54)
                            .addComponent(jLabel49))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tf_validade_hiv1_2)
                            .addComponent(tf_validade_hiv2_2)
                            .addComponent(tf_validade_sifilis)
                            .addComponent(tf_validade_hepatite_b)
                            .addComponent(tf_validade_hepatite_c)
                            .addComponent(tf_validade_covid, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rb_hiv1_2_reativo)
                                .addGap(38, 38, 38)
                                .addComponent(rb_hiv1_2_nao_reativo)
                                .addGap(33, 33, 33)
                                .addComponent(rb_hiv1_2_nao_realizado))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rb_hiv2_2_reativo)
                                .addGap(38, 38, 38)
                                .addComponent(rb_hiv2_2_nao_reativo)
                                .addGap(33, 33, 33)
                                .addComponent(rb_hiv2_2_nao_realizado))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rb_sifilis_reativo)
                                .addGap(38, 38, 38)
                                .addComponent(rb_sifilis_nao_reativo)
                                .addGap(33, 33, 33)
                                .addComponent(rb_sifilis_nao_realizado))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rb_hepatite_b_reativo)
                                .addGap(38, 38, 38)
                                .addComponent(rb_hepatite_b_nao_reativo)
                                .addGap(33, 33, 33)
                                .addComponent(rb_hepatite_b_nao_realizado))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rb_hepatite_c_reativo)
                                .addGap(38, 38, 38)
                                .addComponent(rb_hepatite_c_nao_reativo)
                                .addGap(33, 33, 33)
                                .addComponent(rb_hepatite_c_nao_realizado))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rb_covid_reativo)
                                .addGap(38, 38, 38)
                                .addComponent(rb_covid_nao_reativo)
                                .addGap(33, 33, 33)
                                .addComponent(rb_covid_nao_realizado)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(jSeparator9)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rb_queixa_odontologica_nao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_queixa_odontologica_sim)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel60)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tf_quais_queixas_odontologicas))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(18, 18, 18)
                        .addComponent(rb_gravidez_negativo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rb_gravidez_positivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rb_gravidez_nao_realizado))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel55)
                        .addGap(18, 18, 18)
                        .addComponent(rb_coleta_escarro_sim)
                        .addGap(18, 18, 18)
                        .addComponent(rb_coleta_escarro_nao))
                    .addComponent(jLabel56)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rb_queixa_sim)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rb_queixa_nao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel57)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_quais_queixas, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel58)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane3)
                    .addComponent(jLabel59)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel61)
                        .addGap(18, 18, 18)
                        .addComponent(rb_necessita_dentista_nao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rb_necessita_dentista_sim))
                    .addComponent(jLabel62)
                    .addComponent(jLabel63)
                    .addComponent(jScrollPane4))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bt_cancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_salvar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_transferencia_sim)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rb_transferencia_nao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_transferencia))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_data)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxResponsavel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel65)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxDetento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(label_data))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_transferencia_sim)
                    .addComponent(rb_transferencia_nao)
                    .addComponent(tf_transferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(atendimento_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(jComboBoxDetento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(tf_peso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(tf_altura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(tf_imc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(tf_pa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(tf_fc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(tf_sat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tf_temp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel20))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel22)
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel24)
                                    .addComponent(rb_coriza_sim)
                                    .addComponent(rb_coriza_nao))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel25)
                                    .addComponent(rb_espirros_sim)
                                    .addComponent(rb_espirros_nao))
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel26)
                                    .addComponent(rb_febre_sim)
                                    .addComponent(rb_febre_nao))
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel27)
                                    .addComponent(rb_calafrios_sim)
                                    .addComponent(rb_calafrios_nao)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rb_tosse_nao)
                                    .addComponent(rb_tosse_sim))
                                .addGap(112, 112, 112)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(tf_atendimento_clinico_outros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(tf_inicio_simtomas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel31)))
                .addGap(112, 112, 112)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_apresenta_lesoes_sim)
                    .addComponent(rb_apresenta_lesoes_nao)
                    .addComponent(jLabel33)
                    .addComponent(tf_lesoes_locais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47)
                            .addComponent(jt_lote_hepatite_c, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel48)
                            .addComponent(jt_lote_covid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel43)
                                    .addComponent(jt_lote_hiv1_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel44)
                                    .addComponent(jt_lote_hiv2_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel45)
                                    .addComponent(jt_lote_sifilis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel46)
                                    .addComponent(jt_lote_hepatite_b, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel37)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel38)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel39)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel40)))
                        .addGap(17, 17, 17)
                        .addComponent(jLabel41)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel42))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel49)
                                .addComponent(tf_validade_hiv1_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel50)
                                .addComponent(tf_validade_hiv2_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel51)
                                .addComponent(tf_validade_sifilis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel52)
                                .addComponent(tf_validade_hepatite_b, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel53)
                                .addComponent(tf_validade_hepatite_c, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel54)
                                .addComponent(tf_validade_covid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rb_hiv1_2_reativo)
                                .addComponent(rb_hiv1_2_nao_reativo)
                                .addComponent(rb_hiv1_2_nao_realizado))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rb_hiv2_2_reativo)
                                .addComponent(rb_hiv2_2_nao_reativo)
                                .addComponent(rb_hiv2_2_nao_realizado))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rb_sifilis_reativo)
                                .addComponent(rb_sifilis_nao_reativo)
                                .addComponent(rb_sifilis_nao_realizado))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rb_hepatite_b_reativo)
                                .addComponent(rb_hepatite_b_nao_reativo)
                                .addComponent(rb_hepatite_b_nao_realizado))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rb_hepatite_c_reativo)
                                .addComponent(rb_hepatite_c_nao_reativo)
                                .addComponent(rb_hepatite_c_nao_realizado))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rb_covid_reativo)
                                .addComponent(rb_covid_nao_reativo)
                                .addComponent(rb_covid_nao_realizado)))
                        .addComponent(jSeparator3)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(rb_gravidez_negativo)
                    .addComponent(rb_gravidez_positivo)
                    .addComponent(rb_gravidez_nao_realizado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(rb_coleta_escarro_sim)
                    .addComponent(rb_coleta_escarro_nao))
                .addGap(14, 14, 14)
                .addComponent(jLabel56)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_queixa_sim)
                    .addComponent(rb_queixa_nao)
                    .addComponent(jLabel57)
                    .addComponent(tf_quais_queixas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel58)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel59)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rb_queixa_odontologica_nao)
                    .addComponent(rb_queixa_odontologica_sim)
                    .addComponent(jLabel60)
                    .addComponent(tf_quais_queixas_odontologicas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(rb_necessita_dentista_nao)
                    .addComponent(rb_necessita_dentista_sim))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel63)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_salvar)
                    .addComponent(bt_cancelar))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tf_pesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_pesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_pesoActionPerformed

    private void tf_paActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_paActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_paActionPerformed

    private void rb_tosse_simActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_tosse_simActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_tosse_simActionPerformed

    private void rb_coriza_simActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_coriza_simActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_coriza_simActionPerformed

    private void rb_calafrios_naoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_calafrios_naoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_calafrios_naoActionPerformed

    private void rb_febre_naoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_febre_naoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_febre_naoActionPerformed

    private void rb_hiv1_2_reativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_hiv1_2_reativoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_hiv1_2_reativoActionPerformed

    private void rb_hiv2_2_reativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_hiv2_2_reativoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_hiv2_2_reativoActionPerformed

    private void rb_sifilis_reativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_sifilis_reativoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_sifilis_reativoActionPerformed

    private void rb_hepatite_b_reativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_hepatite_b_reativoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_hepatite_b_reativoActionPerformed

    private void rb_hepatite_c_reativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_hepatite_c_reativoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_hepatite_c_reativoActionPerformed

    private void rb_covid_reativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_covid_reativoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_covid_reativoActionPerformed

    private void bt_salvarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        parent.showPanel("buscaPanel");
    }
    
    private void bt_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cancelarActionPerformed
        parent.showPanel("buscaPanel");
    }//GEN-LAST:event_bt_cancelarActionPerformed

    private void jComboBoxDetentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDetentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxDetentoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel atendimento_panel;
    private javax.swing.ButtonGroup bg_apresenta_outra_queixa;
    private javax.swing.ButtonGroup bg_apresenta_queixa_odontologica;
    private javax.swing.ButtonGroup bg_coleta_escarro;
    private javax.swing.ButtonGroup bg_necessita_aval_imediat_dentista;
    private javax.swing.ButtonGroup bg_possui_calafrios;
    private javax.swing.ButtonGroup bg_possui_coriza;
    private javax.swing.ButtonGroup bg_possui_espirros;
    private javax.swing.ButtonGroup bg_possui_febre;
    private javax.swing.ButtonGroup bg_possui_lesoes_ferimentos;
    private javax.swing.ButtonGroup bg_possui_tosse;
    private javax.swing.ButtonGroup bg_transferencia;
    private javax.swing.ButtonGroup bg_tst_gravidez;
    private javax.swing.ButtonGroup bg_tst_rap_covid;
    private javax.swing.ButtonGroup bg_tst_rap_hepatite_b;
    private javax.swing.ButtonGroup bg_tst_rap_hepatite_c;
    private javax.swing.ButtonGroup bg_tst_rap_hiv_1;
    private javax.swing.ButtonGroup bg_tst_rap_hiv_2;
    private javax.swing.ButtonGroup bg_tst_rap_sifilis;
    private javax.swing.JButton bt_cancelar;
    private javax.swing.JButton bt_salvar;
    private com.mycompany.sistema_carcerario.model.DataBase dataBase1;
    private javax.swing.JComboBox<String> jComboBoxDetento;
    private javax.swing.JComboBox<String> jComboBoxResponsavel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
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
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelData;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField jt_lote_covid;
    private javax.swing.JTextField jt_lote_hepatite_b;
    private javax.swing.JTextField jt_lote_hepatite_c;
    private javax.swing.JTextField jt_lote_hiv1_2;
    private javax.swing.JTextField jt_lote_hiv2_2;
    private javax.swing.JTextField jt_lote_sifilis;
    private javax.swing.JLabel label_data;
    private javax.swing.JRadioButton rb_apresenta_lesoes_nao;
    private javax.swing.JRadioButton rb_apresenta_lesoes_sim;
    private javax.swing.JRadioButton rb_calafrios_nao;
    private javax.swing.JRadioButton rb_calafrios_sim;
    private javax.swing.JRadioButton rb_coleta_escarro_nao;
    private javax.swing.JRadioButton rb_coleta_escarro_sim;
    private javax.swing.JRadioButton rb_coriza_nao;
    private javax.swing.JRadioButton rb_coriza_sim;
    private javax.swing.JRadioButton rb_covid_nao_realizado;
    private javax.swing.JRadioButton rb_covid_nao_reativo;
    private javax.swing.JRadioButton rb_covid_reativo;
    private javax.swing.JRadioButton rb_espirros_nao;
    private javax.swing.JRadioButton rb_espirros_sim;
    private javax.swing.JRadioButton rb_febre_nao;
    private javax.swing.JRadioButton rb_febre_sim;
    private javax.swing.JRadioButton rb_gravidez_nao_realizado;
    private javax.swing.JRadioButton rb_gravidez_negativo;
    private javax.swing.JRadioButton rb_gravidez_positivo;
    private javax.swing.JRadioButton rb_hepatite_b_nao_realizado;
    private javax.swing.JRadioButton rb_hepatite_b_nao_reativo;
    private javax.swing.JRadioButton rb_hepatite_b_reativo;
    private javax.swing.JRadioButton rb_hepatite_c_nao_realizado;
    private javax.swing.JRadioButton rb_hepatite_c_nao_reativo;
    private javax.swing.JRadioButton rb_hepatite_c_reativo;
    private javax.swing.JRadioButton rb_hiv1_2_nao_realizado;
    private javax.swing.JRadioButton rb_hiv1_2_nao_reativo;
    private javax.swing.JRadioButton rb_hiv1_2_reativo;
    private javax.swing.JRadioButton rb_hiv2_2_nao_realizado;
    private javax.swing.JRadioButton rb_hiv2_2_nao_reativo;
    private javax.swing.JRadioButton rb_hiv2_2_reativo;
    private javax.swing.JRadioButton rb_necessita_dentista_nao;
    private javax.swing.JRadioButton rb_necessita_dentista_sim;
    private javax.swing.JRadioButton rb_queixa_nao;
    private javax.swing.JRadioButton rb_queixa_odontologica_nao;
    private javax.swing.JRadioButton rb_queixa_odontologica_sim;
    private javax.swing.JRadioButton rb_queixa_sim;
    private javax.swing.JRadioButton rb_sifilis_nao_realizado;
    private javax.swing.JRadioButton rb_sifilis_nao_reativo;
    private javax.swing.JRadioButton rb_sifilis_reativo;
    private javax.swing.JRadioButton rb_tosse_nao;
    private javax.swing.JRadioButton rb_tosse_sim;
    private javax.swing.JRadioButton rb_transferencia_nao;
    private javax.swing.JRadioButton rb_transferencia_sim;
    private javax.swing.JTextArea ta_atendimento_clinico_conduta;
    private javax.swing.JTextField tf_altura;
    private javax.swing.JTextField tf_atendimento_clinico_outros;
    private javax.swing.JTextArea tf_conduta_odontologica;
    private javax.swing.JTextArea tf_conduta_testes_rapidos;
    private javax.swing.JTextArea tf_encaminhamentos_finais;
    private javax.swing.JTextField tf_fc;
    private javax.swing.JTextField tf_imc;
    private javax.swing.JTextField tf_inicio_simtomas;
    private javax.swing.JTextField tf_lesoes_locais;
    private javax.swing.JTextField tf_pa;
    private javax.swing.JTextField tf_peso;
    private javax.swing.JTextField tf_quais_queixas;
    private javax.swing.JTextField tf_quais_queixas_odontologicas;
    private javax.swing.JTextField tf_sat;
    private javax.swing.JTextField tf_temp;
    private javax.swing.JTextField tf_transferencia;
    private javax.swing.JTextField tf_validade_covid;
    private javax.swing.JTextField tf_validade_hepatite_b;
    private javax.swing.JTextField tf_validade_hepatite_c;
    private javax.swing.JTextField tf_validade_hiv1_2;
    private javax.swing.JTextField tf_validade_hiv2_2;
    private javax.swing.JTextField tf_validade_sifilis;
    // End of variables declaration//GEN-END:variables
}
