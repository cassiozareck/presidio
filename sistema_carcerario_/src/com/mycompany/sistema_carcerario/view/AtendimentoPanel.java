/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.sistema_carcerario.view;

import DAL.AtendenteDao;
import DAL.PrisioneiroDao;
import DAL.AtendimentoDao;
import com.mycompany.sistema_carcerario.model.Atendente;
import com.mycompany.sistema_carcerario.model.Prisioneiro;
import com.mycompany.sistema_carcerario.model.Atendimento;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author m138824
 */
public class AtendimentoPanel extends javax.swing.JPanel {
    final MainFrame parent;
    private final PrisioneiroDao prisioneiroDao = new PrisioneiroDao();
    private final AtendimentoDao atendimentoDao = new AtendimentoDao();
    private Prisioneiro prisioneiroAtual;
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
        
    }
    
    // Retorna uma String contendo a data atual no formato "dd/MM/yyyy"
    public String getDataAtual(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = LocalDate.now().format(formatter);
        return dataFormatada;
    }
    
    // Função responsável por carregar um prisioneiro existente pelo ID
    // Vai chamar query no DAO e preencher UI
    public void carregarPrisioneiro(int idPrisioneiro) {
        prisioneiroAtual = prisioneiroDao.buscarPrisioneiroPorId(idPrisioneiro);
        
        if (prisioneiroAtual != null) {
            // Esconde painel quando carrega novo prisioneiro
            atendimento_panel.setVisible(false);
            
            tf_nome.setText(prisioneiroAtual.getNome());
            tf_nome_social.setText(prisioneiroAtual.getNome()); // Assuming nome social is the same as nome for now
            tf_data_nascimento.setText(prisioneiroAtual.getDataNascimento().toString());
            tf_cpf.setText(prisioneiroAtual.getCpf());
            tf_idade.setText(String.valueOf(prisioneiroAtual.calcularIdade()));
            tf_etinia.setText(prisioneiroAtual.getRaca());
            
            setComboBoxValue(cb_sexo_biologico, prisioneiroAtual.getSexo());
            setComboBoxValue(cb_identidade_genero, prisioneiroAtual.getGenero());
            setComboBoxValue(cb_orientacao_sexual, prisioneiroAtual.getOrientacao());
            setComboBoxValue(jComboBox1, prisioneiroAtual.getRaca());
        }
    }
    
    private void setComboBoxResponsavel(){
        jComboBoxResponsavel.removeAllItems();
        ArrayList <Atendente> atendentes = atendenteDao.getAtendentes();
        for(Atendente atendente : atendentes){
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
        Prisioneiro prisioneiro = new Prisioneiro();
        
        // Se tiver editando um prisioneiro conhecido
        if (prisioneiroAtual != null) {
            prisioneiro.setId(prisioneiroAtual.getId());
        }
        
        // Colete dados
        prisioneiro.setNome(tf_nome.getText());
        prisioneiro.setNomeMae(tf_nome_social.getText()); 
        prisioneiro.setCpf(tf_cpf.getText());
        
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
            
            prisioneiro.setDataNascimento(dataNascimento);
        } catch (Exception e) {
            System.out.println("Erro ao parsear data de nascimento: " + e.getMessage());
            return null;
        }
        
        // Valores das combo boxes
        prisioneiro.setSexo((String) cb_sexo_biologico.getSelectedItem());
        prisioneiro.setGenero((String) cb_identidade_genero.getSelectedItem());
        prisioneiro.setOrientacao((String) cb_orientacao_sexual.getSelectedItem());
        prisioneiro.setRaca((String) jComboBox1.getSelectedItem());
        
        // Valores padrão para campos obrigatórios
        prisioneiro.setNacionalidade("Brasileira");
        prisioneiro.setEstadoCivil("Solteiro");
        prisioneiro.setEscolaridade("Fundamental");
        prisioneiro.setUf("SP"); // Valor padrão para UF
        
        return prisioneiro;
    }
    
    private boolean validarFormulario() {
        if (tf_nome.getText().trim().isEmpty()) {
            System.out.println("Nome é obrigatório");
            return false;
        }
        if (tf_cpf.getText().trim().isEmpty()) {
            System.out.println("CPF é obrigatório");
            return false;
        }
        if (tf_data_nascimento.getText().trim().isEmpty()) {
            System.out.println("Data de nascimento é obrigatória");
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
        btn_cancelar = new javax.swing.JButton();
        btn_salvar = new javax.swing.JButton();
        atendimento_panel = new javax.swing.JPanel();
        rb_transferencia_sim = new javax.swing.JRadioButton();
        rb_transferencia_nao = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
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
        tf_possui_intolerancia_quais1 = new javax.swing.JTextField();
        rb_realizou_cirurgia_nao_sei = new javax.swing.JRadioButton();
        rb_realizou_cirurgia_nao = new javax.swing.JRadioButton();
        rb_realizou_cirurgia_sim = new javax.swing.JRadioButton();
        jLabel23 = new javax.swing.JLabel();
        tf_possui_intolerancia_quais2 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        rb_doenca_pele_sim = new javax.swing.JRadioButton();
        rb_doenca_pele_nao = new javax.swing.JRadioButton();
        jLabel29 = new javax.swing.JLabel();
        rb_condicoes_cronicas_nao1 = new javax.swing.JRadioButton();
        rb_condicoes_cronicas_sim1 = new javax.swing.JRadioButton();
        jLabel30 = new javax.swing.JLabel();
        rb_condicoes_cronicas_sim2 = new javax.swing.JRadioButton();
        rb_condicoes_cronicas_nao2 = new javax.swing.JRadioButton();
        jLabel31 = new javax.swing.JLabel();
        rb_condicoes_cronicas_sim3 = new javax.swing.JRadioButton();
        rb_condicoes_cronicas_nao3 = new javax.swing.JRadioButton();
        jLabel32 = new javax.swing.JLabel();
        rb_condicoes_cronicas_sim4 = new javax.swing.JRadioButton();
        rb_condicoes_cronicas_nao4 = new javax.swing.JRadioButton();
        rb_condicoes_cronicas_sim6 = new javax.swing.JRadioButton();
        jLabel34 = new javax.swing.JLabel();
        tf_possui_intolerancia_quais7 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        tf_possui_intolerancia_quais8 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        tf_possui_intolerancia_quais10 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        rb_condicoes_cronicas_sim7 = new javax.swing.JRadioButton();
        tf_possui_intolerancia_quais11 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        rb_condicoes_cronicas_sim8 = new javax.swing.JRadioButton();
        rb_condicoes_cronicas_nao6 = new javax.swing.JRadioButton();
        rb_condicoes_cronicas_nao7 = new javax.swing.JRadioButton();
        rb_condicoes_cronicas_sim9 = new javax.swing.JRadioButton();
        rb_condicoes_cronicas_sim10 = new javax.swing.JRadioButton();
        rb_condicoes_cronicas_nao8 = new javax.swing.JRadioButton();
        rb_condicoes_cronicas_nao9 = new javax.swing.JRadioButton();
        rb_condicoes_cronicas_sim11 = new javax.swing.JRadioButton();
        rb_condicoes_cronicas_sim12 = new javax.swing.JRadioButton();
        rb_condicoes_cronicas_nao10 = new javax.swing.JRadioButton();
        jLabel33 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        tf_med_continuo = new javax.swing.JTextField();
        rb_doenca_pele_nao1 = new javax.swing.JRadioButton();
        rb_doenca_pele_sim1 = new javax.swing.JRadioButton();
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

        jTextField3.setEditable(false);
        jTextField3.setToolTipText("Local");
        jTextField3.setEnabled(false);

        jLabel6.setText("Transferência");

        jComboBoxResponsavel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                        .addComponent(jTextField3))
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
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jLabel7.setText("Nome Completo:");

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

        tf_possui_intolerancia_quais1.setEditable(false);
        tf_possui_intolerancia_quais1.setEnabled(false);

        bg_realizou_cirurgia.add(rb_realizou_cirurgia_nao_sei);
        rb_realizou_cirurgia_nao_sei.setText("Não sabe responder");

        bg_realizou_cirurgia.add(rb_realizou_cirurgia_nao);
        rb_realizou_cirurgia_nao.setText("Não");

        bg_realizou_cirurgia.add(rb_realizou_cirurgia_sim);
        rb_realizou_cirurgia_sim.setText("Sim");

        jLabel23.setText("Realizou cirurgias:");

        tf_possui_intolerancia_quais2.setEditable(false);
        tf_possui_intolerancia_quais2.setEnabled(false);
        tf_possui_intolerancia_quais2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_possui_intolerancia_quais2ActionPerformed(evt);
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

        jLabel29.setText("Hipertensão");

        bg_hipertencao.add(rb_condicoes_cronicas_nao1);
        rb_condicoes_cronicas_nao1.setText("Não");

        bg_hipertencao.add(rb_condicoes_cronicas_sim1);
        rb_condicoes_cronicas_sim1.setText("Sim");

        jLabel30.setText("Diabetes");

        bg_diabetes.add(rb_condicoes_cronicas_sim2);
        rb_condicoes_cronicas_sim2.setText("Sim");

        bg_diabetes.add(rb_condicoes_cronicas_nao2);
        rb_condicoes_cronicas_nao2.setText("Não");

        jLabel31.setText("HIV");

        bg_hiv.add(rb_condicoes_cronicas_sim3);
        rb_condicoes_cronicas_sim3.setText("Sim");

        bg_hiv.add(rb_condicoes_cronicas_nao3);
        rb_condicoes_cronicas_nao3.setText("Não");

        jLabel32.setText("Autoimune");

        bg_autoimune.add(rb_condicoes_cronicas_sim4);
        rb_condicoes_cronicas_sim4.setText("Sim");

        bg_autoimune.add(rb_condicoes_cronicas_nao4);
        rb_condicoes_cronicas_nao4.setText("Não");

        bg_hipertencao.add(rb_condicoes_cronicas_sim6);
        rb_condicoes_cronicas_sim6.setText("Não sabe responder");
        rb_condicoes_cronicas_sim6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_condicoes_cronicas_sim6ActionPerformed(evt);
            }
        });

        jLabel34.setText("Obs:");

        tf_possui_intolerancia_quais7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_possui_intolerancia_quais7ActionPerformed(evt);
            }
        });

        jLabel35.setText("Outra:");

        tf_possui_intolerancia_quais8.setEditable(false);
        tf_possui_intolerancia_quais8.setEnabled(false);
        tf_possui_intolerancia_quais8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_possui_intolerancia_quais8ActionPerformed(evt);
            }
        });

        jLabel36.setText("Quais:");

        tf_possui_intolerancia_quais10.setEditable(false);
        tf_possui_intolerancia_quais10.setEnabled(false);
        tf_possui_intolerancia_quais10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_possui_intolerancia_quais10ActionPerformed(evt);
            }
        });

        jLabel38.setText("Obs:");

        bg_hipertencao.add(rb_condicoes_cronicas_sim7);
        rb_condicoes_cronicas_sim7.setText("Não sabe responder");
        rb_condicoes_cronicas_sim7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_condicoes_cronicas_sim7ActionPerformed(evt);
            }
        });

        tf_possui_intolerancia_quais11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_possui_intolerancia_quais11ActionPerformed(evt);
            }
        });

        jLabel39.setText("Outra:");

        jLabel40.setText("Hepatite C");

        jLabel41.setText("Hepatite B");

        jLabel42.setText("Tuberculoe");

        jLabel43.setText("HPV");

        jLabel44.setText("Sifilis");

        bg_sifilis.add(rb_condicoes_cronicas_sim8);
        rb_condicoes_cronicas_sim8.setText("Sim");

        bg_sifilis.add(rb_condicoes_cronicas_nao6);
        rb_condicoes_cronicas_nao6.setText("Não");

        bg_hpv.add(rb_condicoes_cronicas_nao7);
        rb_condicoes_cronicas_nao7.setText("Não");

        bg_hpv.add(rb_condicoes_cronicas_sim9);
        rb_condicoes_cronicas_sim9.setText("Sim");

        bg_tuberculose.add(rb_condicoes_cronicas_sim10);
        rb_condicoes_cronicas_sim10.setText("Sim");

        bg_tuberculose.add(rb_condicoes_cronicas_nao8);
        rb_condicoes_cronicas_nao8.setText("Não");

        bg_hepatite_b.add(rb_condicoes_cronicas_nao9);
        rb_condicoes_cronicas_nao9.setText("Não");

        bg_hepatite_b.add(rb_condicoes_cronicas_sim11);
        rb_condicoes_cronicas_sim11.setText("Sim");

        bg_hipertencao.add(rb_condicoes_cronicas_sim12);
        rb_condicoes_cronicas_sim12.setText("Sim");

        bg_hipertencao.add(rb_condicoes_cronicas_nao10);
        rb_condicoes_cronicas_nao10.setText("Não");

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

        bg_med_continuo.add(rb_doenca_pele_nao1);
        rb_doenca_pele_nao1.setText("Não");

        bg_med_continuo.add(rb_doenca_pele_sim1);
        rb_doenca_pele_sim1.setText("Sim");

        jLabel27.setText("Usa medicamento contínuo:");

        jLabel37.setText("Quais:");

        jLabel28.setText("Usa medicamento contínuo:");

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
                        .addComponent(tf_possui_intolerancia_quais1))
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_possui_intolerancia_quais2))
                    .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_possui_intolerancia_quais7))
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_possui_intolerancia_quais10))
                    .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_possui_intolerancia_quais11))
                    .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_possui_intolerancia_quais8, javax.swing.GroupLayout.DEFAULT_SIZE, 749, Short.MAX_VALUE))
                    .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_med_continuo, javax.swing.GroupLayout.DEFAULT_SIZE, 749, Short.MAX_VALUE))
                    .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                        .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                            .addComponent(rb_condicoes_cronicas_sim6)
                            .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, condicoes_de_saudeLayout.createSequentialGroup()
                                    .addComponent(jLabel32)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rb_condicoes_cronicas_sim4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(rb_condicoes_cronicas_nao4))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, condicoes_de_saudeLayout.createSequentialGroup()
                                    .addComponent(jLabel31)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rb_condicoes_cronicas_sim3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(rb_condicoes_cronicas_nao3))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, condicoes_de_saudeLayout.createSequentialGroup()
                                    .addComponent(jLabel30)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rb_condicoes_cronicas_sim2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(rb_condicoes_cronicas_nao2))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, condicoes_de_saudeLayout.createSequentialGroup()
                                    .addComponent(jLabel29)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(rb_condicoes_cronicas_sim1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(rb_condicoes_cronicas_nao1)))
                            .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                                .addComponent(jLabel43)
                                .addGap(18, 18, 18)
                                .addComponent(rb_condicoes_cronicas_sim9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_condicoes_cronicas_nao7))
                            .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                                .addComponent(jLabel44)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rb_condicoes_cronicas_sim8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_condicoes_cronicas_nao6))
                            .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rb_condicoes_cronicas_sim10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_condicoes_cronicas_nao8))
                            .addComponent(rb_condicoes_cronicas_sim7)
                            .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rb_condicoes_cronicas_sim12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_condicoes_cronicas_nao10))
                            .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                                .addComponent(jLabel41)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rb_condicoes_cronicas_sim11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_condicoes_cronicas_nao9))
                            .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_doenca_pele_sim)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_doenca_pele_nao))
                            .addGroup(condicoes_de_saudeLayout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_doenca_pele_sim1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rb_doenca_pele_nao1))
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
                                .addComponent(jLabel47)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tf_tipo_sangue)))
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                .addGap(18, 18, 18)
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
                    .addComponent(tf_possui_intolerancia_quais1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addGap(18, 18, 18)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rb_condicoes_cronicas_sim1)
                        .addComponent(rb_condicoes_cronicas_nao1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(rb_condicoes_cronicas_sim2)
                    .addComponent(rb_condicoes_cronicas_nao2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rb_condicoes_cronicas_sim3)
                        .addComponent(rb_condicoes_cronicas_nao3))
                    .addComponent(jLabel31))
                .addGap(6, 6, 6)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rb_condicoes_cronicas_sim4)
                        .addComponent(rb_condicoes_cronicas_nao4))
                    .addComponent(jLabel32))
                .addGap(8, 8, 8)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_possui_intolerancia_quais7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rb_condicoes_cronicas_sim6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_possui_intolerancia_quais2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGap(18, 18, 18)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44)
                    .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rb_condicoes_cronicas_sim8)
                        .addComponent(rb_condicoes_cronicas_nao6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(rb_condicoes_cronicas_sim9)
                    .addComponent(rb_condicoes_cronicas_nao7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42)
                    .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rb_condicoes_cronicas_sim10)
                        .addComponent(rb_condicoes_cronicas_nao8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41)
                    .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rb_condicoes_cronicas_sim11)
                        .addComponent(rb_condicoes_cronicas_nao9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40)
                    .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rb_condicoes_cronicas_sim12)
                        .addComponent(rb_condicoes_cronicas_nao10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_possui_intolerancia_quais11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rb_condicoes_cronicas_sim7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_possui_intolerancia_quais10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addGap(18, 18, 18)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(rb_doenca_pele_sim)
                    .addComponent(rb_doenca_pele_nao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(tf_possui_intolerancia_quais8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(condicoes_de_saudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(rb_doenca_pele_sim1)
                    .addComponent(rb_doenca_pele_nao1))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(condicoes_de_saude, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(atendimento_panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(identificacao_panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_cancelar)
                        .addGap(18, 18, 18)
                        .addComponent(btn_salvar))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(condicoes_de_saude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
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

        if (!validarFormulario()) {
            return;
        }
        
        Prisioneiro prisioneiro = coletarDadosFormulario();
        
        if (prisioneiro == null) {
            System.out.println("Erro ao coletar dados do formulário");
            return;
        }
        
        boolean sucesso = false;
        
        if (prisioneiroAtual != null) {
            // Editando prisioneiro existente
            sucesso = prisioneiroDao.updatePrisioneiro(prisioneiro);
            if (sucesso) {
                System.out.println("Prisioneiro atualizado com sucesso!");
            } else {
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
                atendimento.setIdAtendente(atendenteDao.getIdAtendenteByName(jComboBoxResponsavel.getSelectedItem().toString())); // ID padrão do atendente
                atendimento.setDataHora(LocalDateTime.now());
                atendimento.setDataEntradaNaUnidade(LocalDateTime.now());
                atendimento.setTransferencia(rb_transferencia_sim.isSelected()); // Se o radio button "Sim" estiver selecionado
                atendimento.setProcedencia(jTextField3.getText().trim().isEmpty() ? "Não informado" : jTextField3.getText().trim());
                
                boolean atendimentoSucesso = atendimentoDao.insertAtendimento(atendimento);
                
                if (atendimentoSucesso) {
                    System.out.println("Atendimento inserido com sucesso!");
                    sucesso = true;
                } else {
                    System.out.println("Erro ao inserir atendimento");
                }
            } else {
                System.out.println("Erro ao inserir prisioneiro");
            }
        }
        
        if (sucesso) {
            parent.showPanel("buscaPanel");
        }
    }//GEN-LAST:event_btn_salvarActionPerformed

    private void rb_nacionalidade_naturalizadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_nacionalidade_naturalizadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_nacionalidade_naturalizadoActionPerformed

    private void tf_data_nascimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_data_nascimentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_data_nascimentoActionPerformed

    private void tf_possui_intolerancia_quais11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_possui_intolerancia_quais11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_possui_intolerancia_quais11ActionPerformed

    private void rb_condicoes_cronicas_sim7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_condicoes_cronicas_sim7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_condicoes_cronicas_sim7ActionPerformed

    private void tf_possui_intolerancia_quais10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_possui_intolerancia_quais10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_possui_intolerancia_quais10ActionPerformed

    private void tf_possui_intolerancia_quais8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_possui_intolerancia_quais8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_possui_intolerancia_quais8ActionPerformed

    private void tf_possui_intolerancia_quais7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_possui_intolerancia_quais7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_possui_intolerancia_quais7ActionPerformed

    private void rb_condicoes_cronicas_sim6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_condicoes_cronicas_sim6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_condicoes_cronicas_sim6ActionPerformed

    private void tf_possui_intolerancia_quais2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_possui_intolerancia_quais2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_possui_intolerancia_quais2ActionPerformed

    private void tf_med_continuoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_med_continuoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_med_continuoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel atendimento_panel;
    private javax.swing.ButtonGroup bg_autoimune;
    private javax.swing.ButtonGroup bg_diabetes;
    private javax.swing.ButtonGroup bg_hepatite_b;
    private javax.swing.ButtonGroup bg_hipertencao;
    private javax.swing.ButtonGroup bg_hiv;
    private javax.swing.ButtonGroup bg_hpv;
    private javax.swing.ButtonGroup bg_med_continuo;
    private javax.swing.ButtonGroup bg_nacionalidade;
    private javax.swing.ButtonGroup bg_possui_deficiencia;
    private javax.swing.ButtonGroup bg_possui_denca_de_pele;
    private javax.swing.ButtonGroup bg_possui_intolerancia;
    private javax.swing.ButtonGroup bg_realizou_cirurgia;
    private javax.swing.ButtonGroup bg_sifilis;
    private javax.swing.ButtonGroup bg_tipo_sangue;
    private javax.swing.ButtonGroup bg_transferencia;
    private javax.swing.ButtonGroup bg_tuberculose;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_salvar;
    private javax.swing.JComboBox<String> cb_identidade_genero;
    private javax.swing.JComboBox<String> cb_orientacao_sexual;
    private javax.swing.JComboBox<String> cb_sexo_biologico;
    private javax.swing.JPanel condicoes_de_saude;
    private javax.swing.JPanel identificacao_panel;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JComboBox<String> jComboBox1;
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelData;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JRadioButton rb_condicoes_cronicas_nao1;
    private javax.swing.JRadioButton rb_condicoes_cronicas_nao10;
    private javax.swing.JRadioButton rb_condicoes_cronicas_nao2;
    private javax.swing.JRadioButton rb_condicoes_cronicas_nao3;
    private javax.swing.JRadioButton rb_condicoes_cronicas_nao4;
    private javax.swing.JRadioButton rb_condicoes_cronicas_nao6;
    private javax.swing.JRadioButton rb_condicoes_cronicas_nao7;
    private javax.swing.JRadioButton rb_condicoes_cronicas_nao8;
    private javax.swing.JRadioButton rb_condicoes_cronicas_nao9;
    private javax.swing.JRadioButton rb_condicoes_cronicas_sim1;
    private javax.swing.JRadioButton rb_condicoes_cronicas_sim10;
    private javax.swing.JRadioButton rb_condicoes_cronicas_sim11;
    private javax.swing.JRadioButton rb_condicoes_cronicas_sim12;
    private javax.swing.JRadioButton rb_condicoes_cronicas_sim2;
    private javax.swing.JRadioButton rb_condicoes_cronicas_sim3;
    private javax.swing.JRadioButton rb_condicoes_cronicas_sim4;
    private javax.swing.JRadioButton rb_condicoes_cronicas_sim6;
    private javax.swing.JRadioButton rb_condicoes_cronicas_sim7;
    private javax.swing.JRadioButton rb_condicoes_cronicas_sim8;
    private javax.swing.JRadioButton rb_condicoes_cronicas_sim9;
    private javax.swing.JRadioButton rb_doenca_pele_nao;
    private javax.swing.JRadioButton rb_doenca_pele_nao1;
    private javax.swing.JRadioButton rb_doenca_pele_sim;
    private javax.swing.JRadioButton rb_doenca_pele_sim1;
    private javax.swing.JRadioButton rb_nacionalidade_brasileira;
    private javax.swing.JRadioButton rb_nacionalidade_estrangeiro;
    private javax.swing.JRadioButton rb_nacionalidade_naturalizado;
    private javax.swing.JRadioButton rb_possui_alergia_nao;
    private javax.swing.JRadioButton rb_possui_alergia_nao_sei;
    private javax.swing.JRadioButton rb_possui_alergia_sim;
    private javax.swing.JRadioButton rb_possui_deficiencia_nao;
    private javax.swing.JRadioButton rb_possui_deficiencia_nao_sei;
    private javax.swing.JRadioButton rb_possui_deficiencia_sim;
    private javax.swing.JRadioButton rb_realizou_cirurgia_nao;
    private javax.swing.JRadioButton rb_realizou_cirurgia_nao_sei;
    private javax.swing.JRadioButton rb_realizou_cirurgia_sim;
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
    private javax.swing.JTextField tf_cpf;
    private javax.swing.JTextField tf_data_nascimento;
    private javax.swing.JTextField tf_deficiencia_quais;
    private javax.swing.JTextField tf_etinia;
    private javax.swing.JTextField tf_idade;
    private javax.swing.JTextField tf_med_continuo;
    private javax.swing.JTextField tf_nacionalidade_qual_pais;
    private javax.swing.JTextField tf_nome;
    private javax.swing.JTextField tf_nome_mae;
    private javax.swing.JTextField tf_nome_social;
    private javax.swing.JTextField tf_orientacao_sexual_outra;
    private javax.swing.JTextField tf_possui_intolerancia_quais;
    private javax.swing.JTextField tf_possui_intolerancia_quais1;
    private javax.swing.JTextField tf_possui_intolerancia_quais10;
    private javax.swing.JTextField tf_possui_intolerancia_quais11;
    private javax.swing.JTextField tf_possui_intolerancia_quais2;
    private javax.swing.JTextField tf_possui_intolerancia_quais7;
    private javax.swing.JTextField tf_possui_intolerancia_quais8;
    private javax.swing.JTextField tf_tipo_sangue;
    // End of variables declaration//GEN-END:variables
}
