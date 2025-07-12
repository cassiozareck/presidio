/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_carcerario.controller;

import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

/**
 *
 * @author gabriel
 */
public class CheckBoxController {

    // Configura o comportamento de exclusividade entre checkboxes.
    // Quando o checkbox "dontKnowCheck" for marcado, todos os checkboxes da lista "selectionChecks" serão desmarcados.
    // E quando qualquer checkbox da lista for marcado, o "dontKnowCheck" será desmarcado.
    public void configurarCheckBox(
            JCheckBox dontKnowCheck, // Esse é o chack box que se for marcado vai desmarcar os demais
            List<JCheckBox> selectionChecks // passa aqui uma lista de checks para desmarcar (assim fica mais fácil de reutilizar o método)
    ) {
        dontKnowCheck.addActionListener(e -> {
            if (dontKnowCheck.isSelected()) {
                for (JCheckBox check : selectionChecks) {
                    check.setSelected(false);

                }
            }
        });

        // Quando qualquer outro for selecionado, desmarca o especial
        for (JCheckBox check : selectionChecks) {
            check.addActionListener(e -> {
                if (check.isSelected()) {
                    dontKnowCheck.setSelected(false);
                }
            });
        }
    }

    // Ativa ou desativa um campo de texto se o checkbox (checkBox) estiver 
    // marcado (ativado).
    
    // Quando o checkbox estiver marcado, o campo de texto vai ficar habilitado. (usuario pode digitar dai)
    // Quando o checkbox for desmarcado, o campo de texto será desabilitado. 
    // Também apaga o que estava escrito (não permite digitar nem selecionar)
    public void configurarCheckBoxECampoDeTexto(JCheckBox checkBox, JTextField campoTexto) {
        checkBox.addActionListener(e -> {
            boolean marcado = checkBox.isSelected();
            campoTexto.setEnabled(marcado);
            campoTexto.setEditable(marcado); 
            campoTexto.setText("");
        });
    }

}
