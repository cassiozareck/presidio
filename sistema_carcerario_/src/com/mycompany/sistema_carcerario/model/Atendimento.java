package com.mycompany.sistema_carcerario.model;

import java.sql.Timestamp;
import java.util.Date;

public class Atendimento {
    // DADOS DO ATENDIMENTO
    private int id;
    private int idAtendente;
    private int idPrisioneiro;
    private Timestamp dataHora;
    private Date dataEntradaUnidade;
    private boolean isTransferencia;
    private String procedencia;

    // ATENDIMENTO CLÍNICO
    private int peso;
    private float altura;
    private float imc;
    private String pa;
    private float fc;
    private float sat;
    private float temp;

    private boolean tosse;
    private boolean coriza;
    private boolean espirros;
    private boolean febre;
    private boolean calafrios;

    private String outrosSistemasRespiratorios;
    private Date dataSintomas;

    private boolean apresentaLesoes;
    private String localLesoes;
    private String condutaLesoesClinica;

    private String hiv12Lote;
    private Date hiv12Validade;
    private Boolean hiv12Reativo;

    private String hiv22Lote;
    private Date hiv22Validade;
    private Boolean hiv22Reativo;

    private String sifilisLote;
    private Date sifilisValidade;
    private Boolean sifilisReativo;

    private String hepatiteBLote;
    private Date hepatiteBValidade;
    private Boolean hepatiteBReativo;

    private String hepatiteCLote;
    private Date hepatiteCValidade;
    private Boolean hepatiteCReativo;

    private String covidLote;
    private Date covidValidade;
    private Boolean covidReativo;

    private Boolean testeGravidez;
    private boolean coletaEscarro;

    private boolean apresentaQueixasTesteRapido;
    private String queixaTesteRapido;
    private String condutaTesteRapido;
    private String condutaClinica;

    private boolean temQueixaOdontologica;
    private String queixaOdontologica;
    private boolean necessitaDentista;
    private String condutaOdontologica;
    
    private String encaminhamentosFinais;

    // Construtor padrão
    public Atendimento() {
    }

    // Construtor completo
    public Atendimento(int id, int idAtendente, int idPrisioneiro, Timestamp dataHora,
                       Date dataEntradaUnidade, boolean isTransferencia, String procedencia) {
        this.id = id;
        this.idAtendente = idAtendente;
        this.idPrisioneiro = idPrisioneiro;
        this.dataHora = dataHora;
        this.dataEntradaUnidade = dataEntradaUnidade;
        this.isTransferencia = isTransferencia;
        this.procedencia = procedencia;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEncaminhamentosFinais(String encaminhamentosFinais) {
        this.encaminhamentosFinais = encaminhamentosFinais;
    }

    public String getEncaminhamentosFinais() {
        return encaminhamentosFinais;
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

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public Date getDataEntradaUnidade() {
        return dataEntradaUnidade;
    }

    public void setDataEntradaUnidade(Date dataEntradaUnidade) {
        this.dataEntradaUnidade = dataEntradaUnidade;
    }

    public boolean isTransferencia() {
        return isTransferencia;
    }

    public void setIsTransferencia(boolean isTransferencia) {
        this.isTransferencia = isTransferencia;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getImc() {
        return imc;
    }

    public void setImc(float imc) {
        this.imc = imc;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public float getFc() {
        return fc;
    }

    public void setFc(float fc) {
        this.fc = fc;
    }

    public float getSat() {
        return sat;
    }

    public void setSat(float sat) {
        this.sat = sat;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public boolean isTosse() {
        return tosse;
    }

    public void setTosse(boolean tosse) {
        this.tosse = tosse;
    }

    public boolean isCoriza() {
        return coriza;
    }

    public void setCoriza(boolean coriza) {
        this.coriza = coriza;
    }

    public boolean isEspirros() {
        return espirros;
    }

    public void setEspirros(boolean espirros) {
        this.espirros = espirros;
    }

    public boolean isFebre() {
        return febre;
    }

    public void setFebre(boolean febre) {
        this.febre = febre;
    }

    public boolean isCalafrios() {
        return calafrios;
    }

    public void setCalafrios(boolean calafrios) {
        this.calafrios = calafrios;
    }

    public String getOutrosSistemasRespiratorios() {
        return outrosSistemasRespiratorios;
    }

    public void setOutrosSistemasRespiratorios(String outrosSistemasRespiratorios) {
        this.outrosSistemasRespiratorios = outrosSistemasRespiratorios;
    }

    public Date getDataSintomas() {
        return dataSintomas;
    }

    public void setDataSintomas(Date dataSintomas) {
        this.dataSintomas = dataSintomas;
    }

    public boolean isApresentaLesoes() {
        return apresentaLesoes;
    }

    public void setApresentaLesoes(boolean apresentaLesoes) {
        this.apresentaLesoes = apresentaLesoes;
    }

    public String getLocalLesoes() {
        return localLesoes;
    }

    public void setLocalLesoes(String localLesoes) {
        this.localLesoes = localLesoes;
    }

    public String getCondutaLesoesClinica() {
        return condutaLesoesClinica;
    }

    public void setCondutaLesoesClinica(String condutaLesoesClinica) {
        this.condutaLesoesClinica = condutaLesoesClinica;
    }

    public String getHiv12Lote() {
        return hiv12Lote;
    }

    public void setHiv12Lote(String hiv12Lote) {
        this.hiv12Lote = hiv12Lote;
    }

    public Date getHiv12Validade() {
        return hiv12Validade;
    }

    public void setHiv12Validade(Date hiv12Validade) {
        this.hiv12Validade = hiv12Validade;
    }

    public Boolean getHiv12Reativo() {
        return hiv12Reativo;
    }

    public void setHiv12Reativo(Boolean hiv12Reativo) {
        this.hiv12Reativo = hiv12Reativo;
    }

    public String getHiv22Lote() {
        return hiv22Lote;
    }

    public void setHiv22Lote(String hiv22Lote) {
        this.hiv22Lote = hiv22Lote;
    }

    public Date getHiv22Validade() {
        return hiv22Validade;
    }

    public void setHiv22Validade(Date hiv22Validade) {
        this.hiv22Validade = hiv22Validade;
    }

    public Boolean getHiv22Reativo() {
        return hiv22Reativo;
    }

    public void setHiv22Reativo(Boolean hiv22Reativo) {
        this.hiv22Reativo = hiv22Reativo;
    }

    public String getSifilisLote() {
        return sifilisLote;
    }

    public void setSifilisLote(String sifilisLote) {
        this.sifilisLote = sifilisLote;
    }

    public Date getSifilisValidade() {
        return sifilisValidade;
    }

    public void setSifilisValidade(Date sifilisValidade) {
        this.sifilisValidade = sifilisValidade;
    }

    public Boolean getSifilisReativo() {
        return sifilisReativo;
    }

    public void setSifilisReativo(Boolean sifilisReativo) {
        this.sifilisReativo = sifilisReativo;
    }

    public String getHepatiteBLote() {
        return hepatiteBLote;
    }

    public void setHepatiteBLote(String hepatiteBLote) {
        this.hepatiteBLote = hepatiteBLote;
    }

    public Date getHepatiteBValidade() {
        return hepatiteBValidade;
    }

    public void setHepatiteBValidade(Date hepatiteBValidade) {
        this.hepatiteBValidade = hepatiteBValidade;
    }

    public Boolean getHepatiteBReativo() {
        return hepatiteBReativo;
    }

    public void setHepatiteBReativo(Boolean hepatiteBReativo) {
        this.hepatiteBReativo = hepatiteBReativo;
    }

    public String getHepatiteCLote() {
        return hepatiteCLote;
    }

    public void setHepatiteCLote(String hepatiteCLote) {
        this.hepatiteCLote = hepatiteCLote;
    }

    public Date getHepatiteCValidade() {
        return hepatiteCValidade;
    }

    public void setHepatiteCValidade(Date hepatiteCValidade) {
        this.hepatiteCValidade = hepatiteCValidade;
    }

    public Boolean getHepatiteCReativo() {
        return hepatiteCReativo;
    }

    public void setHepatiteCReativo(Boolean hepatiteCReativo) {
        this.hepatiteCReativo = hepatiteCReativo;
    }

    public String getCovidLote() {
        return covidLote;
    }

    public void setCovidLote(String covidLote) {
        this.covidLote = covidLote;
    }

    public Date getCovidValidade() {
        return covidValidade;
    }

    public void setCovidValidade(Date covidValidade) {
        this.covidValidade = covidValidade;
    }

    public Boolean getCovidReativo() {
        return covidReativo;
    }

    public void setCovidReativo(Boolean covidReativo) {
        this.covidReativo = covidReativo;
    }

    public Boolean getTesteGravidez() {
        return testeGravidez;
    }

    public void setTesteGravidez(Boolean testeGravidez) {
        this.testeGravidez = testeGravidez;
    }

    public boolean isColetaEscarro() {
        return coletaEscarro;
    }

    public void setColetaEscarro(boolean coletaEscarro) {
        this.coletaEscarro = coletaEscarro;
    }

    public boolean isApresentaQueixasTesteRapido() {
        return apresentaQueixasTesteRapido;
    }

    public void setApresentaQueixasTesteRapido(boolean apresentaQueixasTesteRapido) {
        this.apresentaQueixasTesteRapido = apresentaQueixasTesteRapido;
    }

    public String getQueixaTesteRapido() {
        return queixaTesteRapido;
    }

    public void setQueixaTesteRapido(String queixaTesteRapido) {
        this.queixaTesteRapido = queixaTesteRapido;
    }

    public String getCondutaTesteRapido() {
        return condutaTesteRapido;
    }

    public void setCondutaTesteRapido(String condutaTesteRapido) {
        this.condutaTesteRapido = condutaTesteRapido;
    }

    public String getCondutaClinica() {
        return condutaClinica;
    }

    public void setCondutaClinica(String condutaClinica) {
        this.condutaClinica = condutaClinica;
    }

    public boolean isTemQueixaOdontologica() {
        return temQueixaOdontologica;
    }

    public void setTemQueixaOdontologica(boolean temQueixaOdontologica) {
        this.temQueixaOdontologica = temQueixaOdontologica;
    }

    public String getQueixaOdontologica() {
        return queixaOdontologica;
    }

    public void setQueixaOdontologica(String queixaOdontologica) {
        this.queixaOdontologica = queixaOdontologica;
    }

    public boolean isNecessitaDentista() {
        return necessitaDentista;
    }

    public void setNecessitaDentista(boolean necessitaDentista) {
        this.necessitaDentista = necessitaDentista;
    }

    public String getCondutaOdontologica() {
        return condutaOdontologica;
    }

    public void setCondutaOdontologica(String condutaOdontologica) {
        this.condutaOdontologica = condutaOdontologica;
    }

    @Override
    public String toString() {
        return "Atendimento{" +
                "id=" + id +
                ", idAtendente=" + idAtendente +
                ", idPrisioneiro=" + idPrisioneiro +
                ", dataHora=" + dataHora +
                ", dataEntradaUnidade=" + dataEntradaUnidade +
                ", isTransferencia=" + isTransferencia +
                ", procedencia='" + procedencia + '\'' +
                ", peso=" + peso +
                ", altura=" + altura +
                ", imc=" + imc +
                ", pa='" + pa + '\'' +
                ", fc=" + fc +
                ", sat=" + sat +
                ", temp=" + temp +
                ", tosse=" + tosse +
                ", coriza=" + coriza +
                ", espirros=" + espirros +
                ", febre=" + febre +
                ", calafrios=" + calafrios +
                ", outrosSistemasRespiratorios='" + outrosSistemasRespiratorios + '\'' +
                ", dataSintomas=" + dataSintomas +
                ", apresentaLesoes=" + apresentaLesoes +
                ", localLesoes='" + localLesoes + '\'' +
                ", condutaLesoesClinica='" + condutaLesoesClinica + '\'' +
                ", hiv12Lote='" + hiv12Lote + '\'' +
                ", hiv12Validade=" + hiv12Validade +
                ", hiv12Reativo=" + hiv12Reativo +
                ", hiv22Lote='" + hiv22Lote + '\'' +
                ", hiv22Validade=" + hiv22Validade +
                ", hiv22Reativo=" + hiv22Reativo +
                ", sifilisLote='" + sifilisLote + '\'' +
                ", sifilisValidade=" + sifilisValidade +
                ", sifilisReativo=" + sifilisReativo +
                ", hepatiteBLote='" + hepatiteBLote + '\'' +
                ", hepatiteBValidade=" + hepatiteBValidade +
                ", hepatiteBReativo=" + hepatiteBReativo +
                ", hepatiteCLote='" + hepatiteCLote + '\'' +
                ", hepatiteCValidade=" + hepatiteCValidade +
                ", hepatiteCReativo=" + hepatiteCReativo +
                ", covidLote='" + covidLote + '\'' +
                ", covidValidade=" + covidValidade +
                ", covidReativo=" + covidReativo +
                ", testeGravidez=" + testeGravidez +
                ", coletaEscarro=" + coletaEscarro +
                ", apresentaQueixasTesteRapido=" + apresentaQueixasTesteRapido +
                ", queixaTesteRapido='" + queixaTesteRapido + '\'' +
                ", condutaTesteRapido='" + condutaTesteRapido + '\'' +
                ", condutaClinica='" + condutaClinica + '\'' +
                ", temQueixaOdontologica=" + temQueixaOdontologica +
                ", queixaOdontologica='" + queixaOdontologica + '\'' +
                ", necessitaDentista=" + necessitaDentista +
                ", condutaOdontologica='" + condutaOdontologica + '\'' +
                '}';
    }

}
