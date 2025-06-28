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
    
    // Refêrencias aos painéis
    private AtendimentoPanel atendimentoPanel;
    private BuscarPanel buscaPanel;
    
    public MainFrame(){
        super("Painel principal");
        this.setSize(800,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        atendimentoPanel = new AtendimentoPanel(this);
        buscaPanel = new BuscarPanel(this);
        
        // Painel de rolagem
        JScrollPane scrollAtendimento = new JScrollPane(atendimentoPanel);
        // Condições em que a rolagem será disponibilizada
        scrollAtendimento.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollAtendimento.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        // Adicionando scollAtendimento ao cardPanel e associando seu nome a ATENDIMENTO_PANEL
        cardPanel.add(scrollAtendimento, ATENDIMENTO_PANEL);
        // Adicionando buscaPanel ao cardPanel e associando seu nome a BUSCA_PANEL
        cardPanel.add(buscaPanel, BUSCA_PANEL);
        
        this.add(cardPanel);
        showPanel(BUSCA_PANEL);
          
        this.setVisible(true);
    }
    
    void showPanel(String name){
        cardLayout.show(cardPanel, name);
    }
    
    void showAtendimentoPanel(int prisioneiroId) {
        atendimentoPanel.carregarPrisioneiro(prisioneiroId);
        showPanel(ATENDIMENTO_PANEL);
    }
    
    void showAtendimentoPanelParaNovo() {
        atendimentoPanel.prepararParaNovoPrisioneiro();
        showPanel(ATENDIMENTO_PANEL);
    }
    
}