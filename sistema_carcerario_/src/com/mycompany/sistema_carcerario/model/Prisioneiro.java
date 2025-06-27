package com.mycompany.sistema_carcerario.model;

import java.time.LocalDate;

public class Prisioneiro {
    private int id;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private int idOrientacao;
    private int idGenero;
    private int idSexo;
    private int idRaca;
    private int idNacionalidade;
    private int idEstadoCivil;
    private int idEscolaridade;

    // Construtor completo
    public Prisioneiro(int idPrisioneiro, LocalDate dataNascimento, String nome, String cpf,
                       int idOrientacao, int idGenero, int idSexo, int idRaca,
                       int idNacionalidade, int idEstadoCivil, int idEscolaridade) {
        this.id = idPrisioneiro;
        this.dataNascimento = dataNascimento;
        this.nome = nome;
        this.cpf = cpf;
        this.idOrientacao = idOrientacao;
        this.idGenero = idGenero;
        this.idSexo = idSexo;
        this.idRaca = idRaca;
        this.idNacionalidade = idNacionalidade;
        this.idEstadoCivil = idEstadoCivil;
        this.idEscolaridade = idEscolaridade;
    }

    // Construtor vazio (padrão)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    
    public Prisioneiro() {
    }

    // Getters e Setters
    public int getIdPrisioneiro() {
        return id;
    }

    public void setIdPrisioneiro(int idPrisioneiro) {
        this.id = idPrisioneiro;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getIdOrientacao() {
        return idOrientacao;
    }

    public void setIdOrientacao(int idOrientacao) {
        this.idOrientacao = idOrientacao;
    }

    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    public int getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(int idSexo) {
        this.idSexo = idSexo;
    }

    public int getIdRaca() {
        return idRaca;
    }

    public void setIdRaca(int idRaca) {
        this.idRaca = idRaca;
    }

    public int getIdNacionalidade() {
        return idNacionalidade;
    }

    public void setIdNacionalidade(int idNacionalidade) {
        this.idNacionalidade = idNacionalidade;
    }

    public int getIdEstadoCivil() {
        return idEstadoCivil;
    }

    public void setIdEstadoCivil(int idEstadoCivil) {
        this.idEstadoCivil = idEstadoCivil;
    }

    public int getIdEscolaridade() {
        return idEscolaridade;
    }

    public void setIdEscolaridade(int idEscolaridade) {
        this.idEscolaridade = idEscolaridade;
    }

    @Override
    public String toString() {
        return "Prisioneiro{" +
                "idPrisioneiro=" + id +
                ", dataNascimento=" + dataNascimento +
                ", cpf='" + cpf + '\'' +
                ", idOrientacao=" + idOrientacao +
                ", idGenero=" + idGenero +
                ", idSexo=" + idSexo +
                ", idRaca=" + idRaca +
                ", idNacionalidade=" + idNacionalidade +
                ", idEstadoCivil=" + idEstadoCivil +
                ", idEscolaridade=" + idEscolaridade +
                '}';
    }
}
