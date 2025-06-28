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
        btn_cancelar = new javax.swing.JButton();
        btn_salvar = new javax.swing.JButton();
        atendimento_panel = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxResponsavel = new javax.swing.JComboBox<>();
        jLabelData = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
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

        jRadioButton1.setText("Sim");

        jRadioButton2.setText("Não");

        jLabel3.setText("Responsável:");

        jLabel4.setText("Data:");

        jTextField3.setEditable(false);
        jTextField3.setToolTipText("Local");
        jTextField3.setEnabled(false);

        jLabel6.setText("Transferência");

        jComboBoxResponsavel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabelData.setText("jLabel17");

        javax.swing.GroupLayout atendimento_panelLayout = new javax.swing.GroupLayout(atendimento_panel);
        atendimento_panel.setLayout(atendimento_panelLayout);
        atendimento_panelLayout.setHorizontalGroup(
            atendimento_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(atendimento_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(atendimento_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(atendimento_panelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE))
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
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setText("Nome:");

        jLabel8.setText("Nome Social:");

        jLabel9.setText("Data de Nascimento:");

        jLabel10.setText("CPF:");

        jLabel11.setText("Idade:");

        jLabel12.setText("Raça/Cor:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Branco", "Preto", "Pardo", "Amerelo", "Indígena" }));

        jLabel13.setText("Etnia:");

        jLabel14.setText("Sexo Biológico:");

        cb_sexo_biologico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel15.setText("Identidade de Gênero:");

        cb_identidade_genero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel16.setText("Orientação Sexual:");

        cb_orientacao_sexual.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_nome))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_nome_social))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_data_nascimento))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_cpf))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(tf_idade))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_etinia))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_sexo_biologico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_identidade_genero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_orientacao_sexual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tf_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tf_nome_social, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(tf_data_nascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tf_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(tf_idade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(tf_etinia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cb_sexo_biologico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)
                        .addComponent(cb_identidade_genero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)
                        .addComponent(cb_orientacao_sexual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(atendimento_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_cancelar)
                        .addGap(18, 18, 18)
                        .addComponent(btn_salvar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(atendimento_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                atendimento.setTransferencia(jRadioButton1.isSelected()); // Se o radio button "Sim" estiver selecionado
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel atendimento_panel;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_salvar;
    private javax.swing.JComboBox<String> cb_identidade_genero;
    private javax.swing.JComboBox<String> cb_orientacao_sexual;
    private javax.swing.JComboBox<String> cb_sexo_biologico;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelData;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField tf_cpf;
    private javax.swing.JTextField tf_data_nascimento;
    private javax.swing.JTextField tf_etinia;
    private javax.swing.JTextField tf_idade;
    private javax.swing.JTextField tf_nome;
    private javax.swing.JTextField tf_nome_social;
    // End of variables declaration//GEN-END:variables
}
