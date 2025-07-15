/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_carcerario.controller;

import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author 138824
 */
public class RadioButtonController {

    // Ativa um campo de texto quando a opção "Sim" for marcada.
    // Se for "Não" ou "Talvez", desativa o campo.
    // Evita repetir código colocando tudo num só método.
    // OBS: Não precisa ser sim o texto do Radio button (btnSim), mas como 
    // quase todos o SIM habilita o campo de texto eu deixei assim pa dar de 
    // entender melhor
    public void configurarRadioGroup(
            ButtonGroup buttonGroup,
            JRadioButton btnSim,
            JRadioButton btnNao,
            JRadioButton btnTalvez,
            JTextField campoTexto
    ) {

        ActionListener listenerGrupo = e -> {

            ButtonModel selecionado = buttonGroup.getSelection();

            if (selecionado == btnSim.getModel()) {
                campoTexto.setEnabled(true);
                campoTexto.setEditable(true);
            } else {
                campoTexto.setEnabled(false);
                campoTexto.setEditable(false);
                campoTexto.setText("");
            }
        };

        btnSim.addActionListener(listenerGrupo);
        btnNao.addActionListener(listenerGrupo);
        btnTalvez.addActionListener(listenerGrupo);
    }

    // Faz o mesmo que o de cima mas não inclui a opção talvez
    // é uma sobrecarga de método
    public void configurarRadioGroup(
            ButtonGroup buttonGroup,
            JRadioButton btnSim,
            JRadioButton btnNao,
            JTextField campoTexto
    ) {

        ActionListener listenerGrupo = e -> {

            ButtonModel selecionado = buttonGroup.getSelection();

            if (selecionado == btnSim.getModel()) {
                campoTexto.setEnabled(true);
                campoTexto.setEditable(true);
            } else {
                campoTexto.setEnabled(false);
                campoTexto.setEditable(false);
                campoTexto.setText("");
            }
        };

        btnSim.addActionListener(listenerGrupo);
        btnNao.addActionListener(listenerGrupo);
    }

    // Faz o mesmo que o de cima mas inclui mais um campo de texto que pode ser habilitado
    // é uma sobrecarga de método
    public void configurarRadioGroup(
            ButtonGroup buttonGroup,
            JRadioButton btnSim,
            JRadioButton btnNao,
            JTextField campoTexto1,
            JTextField campoTexto2
    ) {

        ActionListener listenerGrupo = e -> {

            ButtonModel selecionado = buttonGroup.getSelection();

            if (selecionado == btnSim.getModel()) {
                campoTexto1.setEnabled(true);
                campoTexto1.setEditable(true);

                campoTexto2.setEnabled(true);
                campoTexto2.setEditable(true);
            } else {
                campoTexto1.setEnabled(false);
                campoTexto1.setEditable(false);
                campoTexto1.setText("");

                campoTexto2.setEnabled(false);
                campoTexto2.setEditable(false);
                campoTexto2.setText("");
            }
        };

        btnSim.addActionListener(listenerGrupo);
        btnNao.addActionListener(listenerGrupo);
    }

    // Faz o mesmo que o de cima mas inclui mais um campo de texto que pode ser habilitado
    // é uma sobrecarga de método
    public void configurarRadioGroup(
            ButtonGroup buttonGroup,
            JRadioButton btnSim,
            JRadioButton btnNao,
            JRadioButton radioBtn1,
            JRadioButton radioBtn2,
            ButtonGroup buttonGroup2
    ) {

        ActionListener listenerGrupo = e -> {

            ButtonModel selecionado = buttonGroup.getSelection();

            if (selecionado == btnSim.getModel()) {
                radioBtn1.setEnabled(true);
                radioBtn2.setEnabled(true);
            } else {
                radioBtn1.setEnabled(false);
                radioBtn2.setEnabled(false);
                buttonGroup2.clearSelection();
            }
        };

        btnSim.addActionListener(listenerGrupo);
        btnNao.addActionListener(listenerGrupo);
    }

    // Métodos de preencher valor
    public void selecionarRadioButtonPorValor(ButtonGroup grupo, String valor) {
        String unformatedValue = valor.trim().replaceAll("\\s+", " ").toLowerCase();

        Enumeration<AbstractButton> botoes = grupo.getElements();
        while (botoes.hasMoreElements()) {
            AbstractButton botao = botoes.nextElement();
            if (botao.getText().equalsIgnoreCase(unformatedValue)) {
                botao.setSelected(true);
            } else {
                botao.setSelected(false);
            }
        }
    }

    public void selecionarRadioButtonPorValorSimNao(ButtonGroup grupo, int valor) {
        // Define os textos esperados
        String textoDesejado = valor == 1 ? "Sim" : "Não";

        String textoNormalizado = normalizarTexto(textoDesejado);

        Enumeration<AbstractButton> botoes = grupo.getElements();
        while (botoes.hasMoreElements()) {
            AbstractButton botao = botoes.nextElement();
            String textoBotao = normalizarTexto(botao.getText());

            if (textoBotao.equals(textoNormalizado)) {
                botao.setSelected(true);
            } else {
                botao.setSelected(false);
            }
        }
    }

    public void selecionarRadioButtonPorValorSimNao(ButtonGroup grupo, boolean valor) {
        String textoDesejado = valor ? "Sim" : "Não";
        String textoNormalizado = normalizarTexto(textoDesejado);

        Enumeration<AbstractButton> botoes = grupo.getElements();
        while (botoes.hasMoreElements()) {
            AbstractButton botao = botoes.nextElement();
            String textoBotao = normalizarTexto(botao.getText());

            if (textoBotao.equals(textoNormalizado)) {
                botao.setSelected(true);
            } else {
                botao.setSelected(false); // opcional
            }
        }
    }

    public void selecionarRadioButtonSimNaoNSR(ButtonGroup grupo, int valor) {
        String textoDesejado;

        switch (valor) {
            case 1:
                textoDesejado = "Sim";
                break;
            case 0:
                textoDesejado = "Não";
                break;
            case 2:
                textoDesejado = "Não Sabe Responder";
                break;
            default:
                textoDesejado = ""; // valor inválido
        }

        String textoNormalizado = normalizarTexto(textoDesejado);

        Enumeration<AbstractButton> botoes = grupo.getElements();
        while (botoes.hasMoreElements()) {
            AbstractButton botao = botoes.nextElement();
            String textoBotao = normalizarTexto(botao.getText());

            if (textoBotao.equals(textoNormalizado)) {
                botao.setSelected(true);
            } else {
                botao.setSelected(false); // opcional
            }
        }
    }

    public void selecionarRadioButtonSimNaoNSR(ButtonGroup grupo, Boolean valor) {
        String textoDesejado;

        if (valor == null) {
            textoDesejado = "Não Sabe Responder";
        } else if (valor) {
            textoDesejado = "Sim";
        } else {
            textoDesejado = "Não";
        }

        String textoNormalizado = normalizarTexto(textoDesejado);

        Enumeration<AbstractButton> botoes = grupo.getElements();
        while (botoes.hasMoreElements()) {
            AbstractButton botao = botoes.nextElement();
            String textoBotao = normalizarTexto(botao.getText());

            botao.setSelected(textoBotao.equals(textoNormalizado));
        }
    }

    private String normalizarTexto(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.trim().replaceAll("\\s+", " ").toLowerCase();
    }

    public void selecionarRadioButtonPorTexto(ButtonGroup grupo, String textoDesejado) {
        String textoNormalizado = normalizarTexto(textoDesejado);

        Enumeration<AbstractButton> botoes = grupo.getElements();
        while (botoes.hasMoreElements()) {
            AbstractButton botao = botoes.nextElement();
            String textoBotao = normalizarTexto(botao.getText());

            botao.setSelected(textoBotao.equals(textoNormalizado));
        }
    }
}
