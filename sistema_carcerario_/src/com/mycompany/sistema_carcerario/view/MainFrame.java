package com.mycompany.sistema_carcerario.view;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
    //CardLayout é O CardLayout funciona como um baralho de painéis (JPanels) 
    //dentro de um único contêiner, onde somente um painel é visível por vez.
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);
    
    private static final String ATENDIMENTO_PANEL = "atendimentoPanel";
    private static final String BUSCA_PANEL = "buscaPanel";
    
    // Store references to panels
    private AtendimentoPanel atendimentoPanel;
    private BuscarPanel buscaPanel;
    
    public MainFrame(){
        super("Painel principal");
        this.setSize(500,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        atendimentoPanel = new AtendimentoPanel(this);
        buscaPanel = new BuscarPanel(this);
        
        cardPanel.add(atendimentoPanel, ATENDIMENTO_PANEL);
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
    
}