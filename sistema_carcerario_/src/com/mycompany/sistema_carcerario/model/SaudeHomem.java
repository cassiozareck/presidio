package com.mycompany.sistema_carcerario.model;

public class SaudeHomem {
    private int id;
    private int idPrisioneiro;
    
    // DADOS SOBRE A SAÚDE HOMEM
    private boolean realizouExameProstata;
    private Integer anoExameProstata;
    private boolean historicoCancerProstataFamilia;
    private String qualFamiliarCancerProstata;
    private boolean realizouVasectomia;
    private boolean parceiraGestante;
    private boolean participaPreNatal;
    
    // ENCAMINHAMENTOS
    private boolean ofertarEncaminhamentoVasectomia;
    private boolean ofertarEncaminhamentoPreNatal;

    // Construtor padrão
    public SaudeHomem() {
    }

    // Construtor completo
    public SaudeHomem(int id, int idPrisioneiro, boolean realizouExameProstata, Integer anoExameProstata,
                      boolean historicoCancerProstataFamilia, String qualFamiliarCancerProstata,
                      boolean realizouVasectomia, boolean parceiraGestante, boolean participaPreNatal,
                      boolean ofertarEncaminhamentoVasectomia, boolean ofertarEncaminhamentoPreNatal) {
        this.id = id;
        this.idPrisioneiro = idPrisioneiro;
        this.realizouExameProstata = realizouExameProstata;
        this.anoExameProstata = anoExameProstata;
        this.historicoCancerProstataFamilia = historicoCancerProstataFamilia;
        this.qualFamiliarCancerProstata = qualFamiliarCancerProstata;
        this.realizouVasectomia = realizouVasectomia;
        this.parceiraGestante = parceiraGestante;
        this.participaPreNatal = participaPreNatal;
        this.ofertarEncaminhamentoVasectomia = ofertarEncaminhamentoVasectomia;
        this.ofertarEncaminhamentoPreNatal = ofertarEncaminhamentoPreNatal;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPrisioneiro() {
        return idPrisioneiro;
    }

    public void setIdPrisioneiro(int idPrisioneiro) {
        this.idPrisioneiro = idPrisioneiro;
    }

    public boolean isRealizouExameProstata() {
        return realizouExameProstata;
    }

    public void setRealizouExameProstata(boolean realizouExameProstata) {
        this.realizouExameProstata = realizouExameProstata;
    }

    public Integer getAnoExameProstata() {
        return anoExameProstata;
    }

    public void setAnoExameProstata(Integer anoExameProstata) {
        this.anoExameProstata = anoExameProstata;
    }

    public boolean isHistoricoCancerProstataFamilia() {
        return historicoCancerProstataFamilia;
    }

    public void setHistoricoCancerProstataFamilia(boolean historicoCancerProstataFamilia) {
        this.historicoCancerProstataFamilia = historicoCancerProstataFamilia;
    }

    public String getQualFamiliarCancerProstata() {
        return qualFamiliarCancerProstata;
    }

    public void setQualFamiliarCancerProstata(String qualFamiliarCancerProstata) {
        this.qualFamiliarCancerProstata = qualFamiliarCancerProstata;
    }

    public boolean isRealizouVasectomia() {
        return realizouVasectomia;
    }

    public void setRealizouVasectomia(boolean realizouVasectomia) {
        this.realizouVasectomia = realizouVasectomia;
    }

    public boolean isParceiraGestante() {
        return parceiraGestante;
    }

    public void setParceiraGestante(boolean parceiraGestante) {
        this.parceiraGestante = parceiraGestante;
    }

    public boolean isParticipaPreNatal() {
        return participaPreNatal;
    }

    public void setParticipaPreNatal(boolean participaPreNatal) {
        this.participaPreNatal = participaPreNatal;
    }

    public boolean isOfertarEncaminhamentoVasectomia() {
        return ofertarEncaminhamentoVasectomia;
    }

    public void setOfertarEncaminhamentoVasectomia(boolean ofertarEncaminhamentoVasectomia) {
        this.ofertarEncaminhamentoVasectomia = ofertarEncaminhamentoVasectomia;
    }

    public boolean isOfertarEncaminhamentoPreNatal() {
        return ofertarEncaminhamentoPreNatal;
    }

    public void setOfertarEncaminhamentoPreNatal(boolean ofertarEncaminhamentoPreNatal) {
        this.ofertarEncaminhamentoPreNatal = ofertarEncaminhamentoPreNatal;
    }

    @Override
    public String toString() {
        return "SaudeHomem{" +
                "id=" + id +
                ", idPrisioneiro=" + idPrisioneiro +
                ", realizouExameProstata=" + realizouExameProstata +
                ", anoExameProstata=" + anoExameProstata +
                ", historicoCancerProstataFamilia=" + historicoCancerProstataFamilia +
                ", qualFamiliarCancerProstata='" + qualFamiliarCancerProstata + '\'' +
                ", realizouVasectomia=" + realizouVasectomia +
                ", parceiraGestante=" + parceiraGestante +
                ", participaPreNatal=" + participaPreNatal +
                ", ofertarEncaminhamentoVasectomia=" + ofertarEncaminhamentoVasectomia +
                ", ofertarEncaminhamentoPreNatal=" + ofertarEncaminhamentoPreNatal +
                '}';
    }
} 