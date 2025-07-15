package com.mycompany.sistema_carcerario.view;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainFrame extends JFrame {
    //CardLayout é O CardLayout funciona como um baralho de painéis (JPanels) 
    //dentro de um único contêiner, onde somente um painel é visível por vez.
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);
    
    private static final String ATENDIMENTO_PANEL = "atendimentoPanel";
    private static final String BUSCA_PANEL = "buscaPanel";
    private static final String CADASTRO_ATENDIMENTO_PANEL = "cadastroAtendimentoPanel";
    private static final String CONSULTAR_ATENDIMENTO_PANEL = "consultarAtendimentoPanel";
    
    // Refêrencias aos painéis
    private AtendimentoPanel atendimentoPanel;
    private BuscarPanel buscaPanel;
    private CadastroAtendimentoPanel cadastroAtendimentoPanel;
    private ConsultarAtendimentoPanel consultarAtendimentoPanel;
    
    public MainFrame(){
        super("Painel principal");
        this.setSize(800,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        atendimentoPanel = new AtendimentoPanel(this);
        buscaPanel = new BuscarPanel(this);
        cadastroAtendimentoPanel = new CadastroAtendimentoPanel(this);
        consultarAtendimentoPanel = new ConsultarAtendimentoPanel(this);
        
        // Painel de rolagem para AtendimentoPanel
        JScrollPane scrollAtendimento = new JScrollPane(atendimentoPanel);
        // Condições em que a rolagem será disponibilizada
        scrollAtendimento.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollAtendimento.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        // Painel de rolagem para CadastroAtendimentoPanel
        JScrollPane scrollCadastroAtendimento = new JScrollPane(cadastroAtendimentoPanel);
        // Condições em que a rolagem será disponibilizada
        scrollCadastroAtendimento.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollCadastroAtendimento.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        // Adicionando scrollAtendimento ao cardPanel e associando seu nome a ATENDIMENTO_PANEL
        cardPanel.add(scrollAtendimento, ATENDIMENTO_PANEL);
        // Adicionando buscaPanel ao cardPanel e associando seu nome a BUSCA_PANEL
        cardPanel.add(buscaPanel, BUSCA_PANEL);
        // Adicionando scrollcadastroAtendimentoPanel ao cardPanel e associando seu nome a CADASTRO_ATENDIMENTO_PANEL
        cardPanel.add(scrollCadastroAtendimento, CADASTRO_ATENDIMENTO_PANEL);
        // Adicionando consultarAtendimentoPanel ao cardPanel e associando seu nome a CONSULTAR_ATENDIMENTO_PANEL
        cardPanel.add(consultarAtendimentoPanel, CONSULTAR_ATENDIMENTO_PANEL);
        
        this.add(cardPanel);
        showPanel(BUSCA_PANEL);
        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);  
        this.setVisible(true);
    }
    
    void showPanel(String name){
        cardLayout.show(cardPanel, name);
    }
    
    void showAtendimentoPanel(int prisioneiroId) {
        atendimentoPanel.carregarPrisioneiro(prisioneiroId);
        showPanel(ATENDIMENTO_PANEL);
    }
    
    void showCadastroAtendimentoPanel() {
        atendimentoPanel = new AtendimentoPanel(this);
        JScrollPane scrollAtendimento = new JScrollPane(atendimentoPanel);
        // Condições em que a rolagem será disponibilizada
        scrollAtendimento.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollAtendimento.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // Adicionando scrollAtendimento ao cardPanel e associando seu nome a ATENDIMENTO_PANEL
        cardPanel.add(scrollAtendimento, ATENDIMENTO_PANEL);
        showPanel(CADASTRO_ATENDIMENTO_PANEL);
    }
    
    void showCadastroAtendimentoPanelModoVisualizarAtendimento(int atendimentoId) {
        cadastroAtendimentoPanel.carregarAtendimento(atendimentoId);
        showPanel(CADASTRO_ATENDIMENTO_PANEL);
    }
    
    void showAtendimentoPanelParaNovo() {
        atendimentoPanel = new AtendimentoPanel(this);
        JScrollPane scrollAtendimento = new JScrollPane(atendimentoPanel);
        // Condições em que a rolagem será disponibilizada
        scrollAtendimento.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollAtendimento.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // Adicionando scrollAtendimento ao cardPanel e associando seu nome a ATENDIMENTO_PANEL
        cardPanel.add(scrollAtendimento, ATENDIMENTO_PANEL);
        showPanel(ATENDIMENTO_PANEL);
    }
    
    void showConsultarAtendimentoPanel(int id_prisioneiro) {
        consultarAtendimentoPanel = new ConsultarAtendimentoPanel(this, id_prisioneiro);
        cardPanel.add(consultarAtendimentoPanel, CONSULTAR_ATENDIMENTO_PANEL);
        this.add(cardPanel);
        showPanel(CONSULTAR_ATENDIMENTO_PANEL);
    }
    
    
}