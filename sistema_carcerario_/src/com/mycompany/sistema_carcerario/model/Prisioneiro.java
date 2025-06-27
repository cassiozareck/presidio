package com.mycompany.sistema_carcerario.model;

import java.time.LocalDate;
import java.time.Period;

public class Prisioneiro {

    private int id;
    private LocalDate dataNascimento;
    private String nome;
    private String nomeMae;
    private String cpf;
    private String uf;
    private String orientacao;
    private String genero;
    private String sexo;
    private String raca;
    private String nacionalidade;
    private String estadoCivil;
    private String escolaridade;

    public Prisioneiro() {
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getOrientacao() {
        return orientacao;
    }

    public void setOrientacao(String orientacao) {
        this.orientacao = orientacao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public int calcularIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

//    @Override
//    public String toString() {
//        return "Prisioneiro{" +
//                "idPrisioneiro=" + id +
//                ", dataNascimento=" + dataNascimento +
//                ", cpf='" + cpf + '\'' +
//                ", idOrientacao=" + idOrientacao +
//                ", idGenero=" + idGenero +
//                ", idSexo=" + idSexo +
//                ", idRaca=" + idRaca +
//                ", idNacionalidade=" + idNacionalidade +
//                ", idEstadoCivil=" + idEstadoCivil +
//                ", idEscolaridade=" + idEscolaridade +
//                '}';
//    }
}
