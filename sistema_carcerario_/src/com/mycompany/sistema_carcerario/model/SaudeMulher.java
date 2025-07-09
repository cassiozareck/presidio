package com.mycompany.sistema_carcerario.model;

import java.util.Objects;

public class SaudeMulher {
    private int id;
    private int idPrisioneiro;
    
    // DADOS SOBRE A SAÚDE MULHER
    private int gestacao;
    private Integer idadeGestacional;
    private String qualContraceptivo;
    private boolean examePreventivoPapanicolau;
    private Integer examePreventivoPapanicolauAno;
    
    // ENCAMINHAMENTOS
    private boolean ofertarContinuidadeContraceptivo;
    private boolean ofertarConsultaPreventivo;
    private boolean encaminharPreNatal;

    // Construtor padrão
    public SaudeMulher() {
    }

    // Construtor completo
    public SaudeMulher(int id, int idPrisioneiro, int gestacao, Integer idadeGestacional,
                       String qualContraceptivo, boolean examePreventivoPapanicolau,
                       Integer examePreventivoPapanicolauAno, boolean ofertarContinuidadeContraceptivo,
                       boolean ofertarConsultaPreventivo, boolean encaminharPreNatal) {
        this.id = id;
        this.idPrisioneiro = idPrisioneiro;
        this.gestacao = gestacao;
        this.idadeGestacional = idadeGestacional;
        this.qualContraceptivo = qualContraceptivo;
        this.examePreventivoPapanicolau = examePreventivoPapanicolau;
        this.examePreventivoPapanicolauAno = examePreventivoPapanicolauAno;
        this.ofertarContinuidadeContraceptivo = ofertarContinuidadeContraceptivo;
        this.ofertarConsultaPreventivo = ofertarConsultaPreventivo;
        this.encaminharPreNatal = encaminharPreNatal;
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

    public int getGestacao() {
        return gestacao;
    }

    public void setGestacao(int gestacao) {
        this.gestacao = gestacao;
    }

    public Integer getIdadeGestacional() {
        return idadeGestacional;
    }

    public void setIdadeGestacional(Integer idadeGestacional) {
        this.idadeGestacional = idadeGestacional;
    }

    public String getQualContraceptivo() {
        return qualContraceptivo;
    }

    public void setQualContraceptivo(String qualContraceptivo) {
        this.qualContraceptivo = qualContraceptivo;
    }

    public boolean isExamePreventivoPapanicolau() {
        return examePreventivoPapanicolau;
    }

    public void setExamePreventivoPapanicolau(boolean examePreventivoPapanicolau) {
        this.examePreventivoPapanicolau = examePreventivoPapanicolau;
    }

    public Integer getExamePreventivoPapanicolauAno() {
        return examePreventivoPapanicolauAno;
    }

    public void setExamePreventivoPapanicolauAno(Integer examePreventivoPapanicolauAno) {
        this.examePreventivoPapanicolauAno = examePreventivoPapanicolauAno;
    }

    public boolean isOfertarContinuidadeContraceptivo() {
        return ofertarContinuidadeContraceptivo;
    }

    public void setOfertarContinuidadeContraceptivo(boolean ofertarContinuidadeContraceptivo) {
        this.ofertarContinuidadeContraceptivo = ofertarContinuidadeContraceptivo;
    }

    public boolean isOfertarConsultaPreventivo() {
        return ofertarConsultaPreventivo;
    }

    public void setOfertarConsultaPreventivo(boolean ofertarConsultaPreventivo) {
        this.ofertarConsultaPreventivo = ofertarConsultaPreventivo;
    }

    public boolean isEncaminharPreNatal() {
        return encaminharPreNatal;
    }

    public void setEncaminharPreNatal(boolean encaminharPreNatal) {
        this.encaminharPreNatal = encaminharPreNatal;
    }

    @Override
    public String toString() {
        return "SaudeMulher{" +
                "id=" + id +
                ", idPrisioneiro=" + idPrisioneiro +
                ", gestacao=" + gestacao +
                ", idadeGestacional=" + idadeGestacional +
                ", qualContraceptivo='" + qualContraceptivo + '\'' +
                ", examePreventivoPapanicolau=" + examePreventivoPapanicolau +
                ", examePreventivoPapanicolauAno=" + examePreventivoPapanicolauAno +
                ", ofertarContinuidadeContraceptivo=" + ofertarContinuidadeContraceptivo +
                ", ofertarConsultaPreventivo=" + ofertarConsultaPreventivo +
                ", encaminharPreNatal=" + encaminharPreNatal +
                '}';
    }
} 