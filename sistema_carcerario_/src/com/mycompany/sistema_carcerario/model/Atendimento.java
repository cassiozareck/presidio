package com.mycompany.sistema_carcerario.model;

import java.time.LocalDateTime;

public class Atendimento {
    private int id;
    private int idAtendente;
    private int idPrisioneiro;
    private LocalDateTime dataHora;
    private LocalDateTime dataEntradaNaUnidade;
    private boolean isTransferencia;
    private String procedencia;

    // Construtor completo
    public Atendimento(int idAtendimento, int idAtendente, int idPrisioneiro, LocalDateTime dataHora,
                       LocalDateTime dataEntradaNaUnidade, boolean isTransferencia,
                       String procedencia) {
        this.id = idAtendimento;
        this.idAtendente = idAtendente;
        this.idPrisioneiro = idPrisioneiro;
        this.dataHora = dataHora;
        this.dataEntradaNaUnidade = dataEntradaNaUnidade;
        this.isTransferencia = isTransferencia;
        this.procedencia = procedencia;
    }

    // Construtor padrão
    public Atendimento() {
    }

    // Getters e Setters
    public int getIdAtendimento() {
        return id;
    }

    public void setIdAtendimento(int idAtendimento) {
        this.id = idAtendimento;
    }

    public int getIdAtendente() {
        return idAtendente;
    }

    public void setIdAtendente(int idAtendente) {
        this.idAtendente = idAtendente;
    }

    public int getIdPrisioneiro() {
        return idPrisioneiro;
    }

    public void setIdPrisioneiro(int idPrisioneiro) {
        this.idPrisioneiro = idPrisioneiro;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public LocalDateTime getDataEntradaNaUnidade() {
        return dataEntradaNaUnidade;
    }

    public void setDataEntradaNaUnidade(LocalDateTime dataEntradaNaUnidade) {
        this.dataEntradaNaUnidade = dataEntradaNaUnidade;
    }

    public boolean isTransferencia() {
        return isTransferencia;
    }

    public void setTransferencia(boolean transferencia) {
        isTransferencia = transferencia;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    @Override
    public String toString() {
        return "Atendimento{" +
                "idAtendimento=" + id +
                ", idAtendente=" + idAtendente +
                ", idPrisioneiro=" + idPrisioneiro +
                ", dataHora=" + dataHora +
                ", dataEntradaNaUnidade=" + dataEntradaNaUnidade +
                ", isTransferencia=" + isTransferencia +
                ", procedencia='" + procedencia + '\'' +
                '}';
    }
}
