package com.mycompany.sistema_carcerario.view;

import com.mycompany.sistema_carcerario.view.AtendimentoPanel;
import com.mycompany.sistema_carcerario.view.BuscarPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
    //CardLayout é O CardLayout funciona como um baralho de painéis (JPanels) 
    //dentro de um único contêiner, onde somente um painel é visível por vez.
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);
    private String username;
    
    private static final String ATENDIMENTO_PANEL = "atendimentoPanel";
    private static final String BUSCA_PANEL = "buscaPanel";
    
    public MainFrame(){
        super("Painel principal");
        this.setSize(500,400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

        AtendimentoPanel atendimentoPanel = new AtendimentoPanel(this);
        BuscarPanel buscaPanel = new BuscarPanel(this);
        
        cardPanel.add(atendimentoPanel, ATENDIMENTO_PANEL);
        cardPanel.add(buscaPanel, BUSCA_PANEL);
        
        this.add(cardPanel);
        showPanel(BUSCA_PANEL);
          
    }
    
    void showPanel(String name){
        cardLayout.show(cardPanel, name);
    }
    
}