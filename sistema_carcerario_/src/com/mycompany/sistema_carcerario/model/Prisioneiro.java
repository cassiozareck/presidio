package com.mycompany.sistema_carcerario.model;

public class Prisioneiro {
 // DADOS PRIMÁRIOS
    private int id;
    private String nomeCompleto;
    private String nomeSocial;
    private java.sql.Date dataNascimento;
    private int idade;
    private String cpf;
    private String nacionalidade;
    private String nomeMae;
    private String estadoCivil;
    private String raca;
    private String orientacao;
    private String genero;
    private String sexo;

    // DADOS SOCIAIS
    private String escolaridade;
    private boolean beneficioFamilia;
    private String beneficioEspecificado;
    private boolean possuiFilhos;
    private int quantosFilhos;
    private boolean possuiDependentes;
    private int quantosDependentes;
    private boolean ofertarNeeja;
    private boolean ofertarAssistenciaSocial;
    private int possuiDeficiencia;
    private String qualDeficiencia;
    private int possuiAlergias;
    private String quaisAlergias;
    private boolean realizouCirurgias;
    private String quaisCirurgias;
    private boolean naoSabeResponderCirurgias;

    // CONDIÇÕES CRÔNICAS
    private boolean hipertencao;
    private boolean diabetes;
    private boolean hiv;
    private boolean autoimune;
    private String outrasDoencasCronicas;
    private boolean naoSabeResponderCondicoesCronicas;
    private String observacaoCondicoesCronicas;

    // HISTÓRICO DOENÇAS INFECCIOSAS
    private boolean sifilis;
    private boolean hpv;
    private boolean tuberculose;
    private boolean hepatiteB;
    private boolean hepatiteC;
    private String outrasDoencasInfecciosas;
    private boolean naoSabeResponderDoencasInfecciosas;
    private String observacaoHistoricoDoencasInfecciosas;

    // DOENÇA DE PELE
    private boolean doencaPele;
    private String quaisDoencasPele;
    private boolean naoSabeResponderDoencasPele;
    private String observacaoHistoricoDoencasPele;

    private boolean medicamentosContinuos;
    private String quaisMedicamentos;
    private String tipoSanguineo;

    // SAÚDE MENTAL
    private boolean vinculoCaps;
    private String nomeMunicioCaps;
    private boolean ansiedade;
    private boolean depressao;
    private boolean bipolaridade;
    private boolean esquizofrenia;
    private boolean autismo;
    private String outraSaudeMental;
    private boolean naoSabeResponderSaudeMental;
    private int medicamentoControlado;
    private String qualMedicamentoControlado;
    private boolean acompanhamentoMentalMomentoPrisao;
    private String motivoAcompanhamentoMental;

    // USO DE SUBSTÂNCIAS
    private boolean alcool;
    private boolean cigarro;
    private boolean maconha;
    private boolean crack;
    private boolean cocaina;
    private boolean anfetaminas;
    private boolean drogas;
    private String outrasDrogas;
    private boolean tratamentoReabilitacao;
    private String tratamentoQualSubstancia;
    private boolean querReabilitacao;
    private String reabilitacaoQualSubstancia;
    private boolean ofertarPsicologa;
    private boolean ofertarPsiquiatra;
    private boolean encaminharReceitas;
    private boolean encaminharGrupoApoio;

    // VACINAÇÃO
    private int vacinaCovid;
    private int vacinaInfluenza;
    private int vacinaTetano;
    private int vacinaHepatite;
    private boolean ofertarVacinas;
    private boolean febreAmarela;
    private boolean hepatiteBVacina;
    private boolean covid19;
    private boolean influenza;
    private boolean duplaAdulto;
    private boolean tripliceViral;
    private String outraVacina;
    private boolean ofertarCarteiraVacinacao;

    private String encaminhamentosFinais;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public java.sql.Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(java.sql.Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
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

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public boolean isBeneficioFamilia() {
        return beneficioFamilia;
    }

    public void setBeneficioFamilia(boolean beneficioFamilia) {
        this.beneficioFamilia = beneficioFamilia;
    }

    public String getBeneficioEspecificado() {
        return beneficioEspecificado;
    }

    public void setBeneficioEspecificado(String beneficioEspecificado) {
        this.beneficioEspecificado = beneficioEspecificado;
    }

    public boolean isPossuiFilhos() {
        return possuiFilhos;
    }

    public void setPossuiFilhos(boolean possuiFilhos) {
        this.possuiFilhos = possuiFilhos;
    }

    public int getQuantosFilhos() {
        return quantosFilhos;
    }

    public void setQuantosFilhos(int quantosFilhos) {
        this.quantosFilhos = quantosFilhos;
    }

    public boolean isPossuiDependentes() {
        return possuiDependentes;
    }

    public void setPossuiDependentes(boolean possuiDependentes) {
        this.possuiDependentes = possuiDependentes;
    }

    public int getQuantosDependentes() {
        return quantosDependentes;
    }

    public void setQuantosDependentes(int quantosDependentes) {
        this.quantosDependentes = quantosDependentes;
    }

    public boolean isOfertarNeeja() {
        return ofertarNeeja;
    }

    public void setOfertarNeeja(boolean ofertarNeeja) {
        this.ofertarNeeja = ofertarNeeja;
    }

    public boolean isOfertarAssistenciaSocial() {
        return ofertarAssistenciaSocial;
    }

    public void setOfertarAssistenciaSocial(boolean ofertarAssistenciaSocial) {
        this.ofertarAssistenciaSocial = ofertarAssistenciaSocial;
    }

    public int getPossuiDeficiencia() {
        return possuiDeficiencia;
    }

    public void setPossuiDeficiencia(int possuiDeficiencia) {
        this.possuiDeficiencia = possuiDeficiencia;
    }

    public String getQualDeficiencia() {
        return qualDeficiencia;
    }

    public void setQualDeficiencia(String qualDeficiencia) {
        this.qualDeficiencia = qualDeficiencia;
    }

    public int getPossuiAlergias() {
        return possuiAlergias;
    }

    public void setPossuiAlergias(int possuiAlergias) {
        this.possuiAlergias = possuiAlergias;
    }

    public String getQuaisAlergias() {
        return quaisAlergias;
    }

    public void setQuaisAlergias(String quaisAlergias) {
        this.quaisAlergias = quaisAlergias;
    }

    public boolean isRealizouCirurgias() {
        return realizouCirurgias;
    }

    public void setRealizouCirurgias(boolean realizouCirurgias) {
        this.realizouCirurgias = realizouCirurgias;
    }

    public String getQuaisCirurgias() {
        return quaisCirurgias;
    }

    public void setQuaisCirurgias(String quaisCirurgias) {
        this.quaisCirurgias = quaisCirurgias;
    }

    public boolean isNaoSabeResponderCirurgias() {
        return naoSabeResponderCirurgias;
    }

    public void setNaoSabeResponderCirurgias(boolean naoSabeResponderCirurgias) {
        this.naoSabeResponderCirurgias = naoSabeResponderCirurgias;
    }

    public boolean isHipertencao() {
        return hipertencao;
    }

    public void setHipertencao(boolean hipertencao) {
        this.hipertencao = hipertencao;
    }

    public boolean isDiabetes() {
        return diabetes;
    }

    public void setDiabetes(boolean diabetes) {
        this.diabetes = diabetes;
    }

    public boolean isHiv() {
        return hiv;
    }

    public void setHiv(boolean hiv) {
        this.hiv = hiv;
    }

    public boolean isAutoimune() {
        return autoimune;
    }

    public void setAutoimune(boolean autoimune) {
        this.autoimune = autoimune;
    }

    public String getOutrasDoencasCronicas() {
        return outrasDoencasCronicas;
    }

    public void setOutrasDoencasCronicas(String outrasDoencasCronicas) {
        this.outrasDoencasCronicas = outrasDoencasCronicas;
    }

    public boolean isNaoSabeResponderCondicoesCronicas() {
        return naoSabeResponderCondicoesCronicas;
    }

    public void setNaoSabeResponderCondicoesCronicas(boolean naoSabeResponderCondicoesCronicas) {
        this.naoSabeResponderCondicoesCronicas = naoSabeResponderCondicoesCronicas;
    }

    public String getObservacaoCondicoesCronicas() {
        return observacaoCondicoesCronicas;
    }

    public void setObservacaoCondicoesCronicas(String observacaoCondicoesCronicas) {
        this.observacaoCondicoesCronicas = observacaoCondicoesCronicas;
    }

    public boolean isSifilis() {
        return sifilis;
    }

    public void setSifilis(boolean sifilis) {
        this.sifilis = sifilis;
    }

    public boolean isHpv() {
        return hpv;
    }

    public void setHpv(boolean hpv) {
        this.hpv = hpv;
    }

    public boolean isTuberculose() {
        return tuberculose;
    }

    public void setTuberculose(boolean tuberculose) {
        this.tuberculose = tuberculose;
    }

    public boolean isHepatiteB() {
        return hepatiteB;
    }

    public void setHepatiteB(boolean hepatiteB) {
        this.hepatiteB = hepatiteB;
    }

    public boolean isHepatiteC() {
        return hepatiteC;
    }

    public void setHepatiteC(boolean hepatiteC) {
        this.hepatiteC = hepatiteC;
    }

    public String getOutrasDoencasInfecciosas() {
        return outrasDoencasInfecciosas;
    }

    public void setOutrasDoencasInfecciosas(String outrasDoencasInfecciosas) {
        this.outrasDoencasInfecciosas = outrasDoencasInfecciosas;
    }

    public boolean isNaoSabeResponderDoencasInfecciosas() {
        return naoSabeResponderDoencasInfecciosas;
    }

    public void setNaoSabeResponderDoencasInfecciosas(boolean naoSabeResponderDoencasInfecciosas) {
        this.naoSabeResponderDoencasInfecciosas = naoSabeResponderDoencasInfecciosas;
    }

    public String getObservacaoHistoricoDoencasInfecciosas() {
        return observacaoHistoricoDoencasInfecciosas;
    }

    public void setObservacaoHistoricoDoencasInfecciosas(String observacaoHistoricoDoencasInfecciosas) {
        this.observacaoHistoricoDoencasInfecciosas = observacaoHistoricoDoencasInfecciosas;
    }

    public boolean isDoencaPele() {
        return doencaPele;
    }

    public void setDoencaPele(boolean doencaPele) {
        this.doencaPele = doencaPele;
    }

    public String getQuaisDoencasPele() {
        return quaisDoencasPele;
    }

    public void setQuaisDoencasPele(String quaisDoencasPele) {
        this.quaisDoencasPele = quaisDoencasPele;
    }

    public boolean isNaoSabeResponderDoencasPele() {
        return naoSabeResponderDoencasPele;
    }

    public void setNaoSabeResponderDoencasPele(boolean naoSabeResponderDoencasPele) {
        this.naoSabeResponderDoencasPele = naoSabeResponderDoencasPele;
    }

    public String getObservacaoHistoricoDoencasPele() {
        return observacaoHistoricoDoencasPele;
    }

    public void setObservacaoHistoricoDoencasPele(String observacaoHistoricoDoencasPele) {
        this.observacaoHistoricoDoencasPele = observacaoHistoricoDoencasPele;
    }

    public boolean isMedicamentosContinuos() {
        return medicamentosContinuos;
    }

    public void setMedicamentosContinuos(boolean medicamentosContinuos) {
        this.medicamentosContinuos = medicamentosContinuos;
    }

    public String getQuaisMedicamentos() {
        return quaisMedicamentos;
    }

    public void setQuaisMedicamentos(String quaisMedicamentos) {
        this.quaisMedicamentos = quaisMedicamentos;
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public boolean isVinculoCaps() {
        return vinculoCaps;
    }

    public void setVinculoCaps(boolean vinculoCaps) {
        this.vinculoCaps = vinculoCaps;
    }

    public String getNomeMunicioCaps() {
        return nomeMunicioCaps;
    }

    public void setNomeMunicioCaps(String nomeMunicioCaps) {
        this.nomeMunicioCaps = nomeMunicioCaps;
    }

    public boolean isAnsiedade() {
        return ansiedade;
    }

    public void setAnsiedade(boolean ansiedade) {
        this.ansiedade = ansiedade;
    }

    public boolean isDepressao() {
        return depressao;
    }

    public void setDepressao(boolean depressao) {
        this.depressao = depressao;
    }

    public boolean isBipolaridade() {
        return bipolaridade;
    }

    public void setBipolaridade(boolean bipolaridade) {
        this.bipolaridade = bipolaridade;
    }

    public boolean isEsquizofrenia() {
        return esquizofrenia;
    }

    public void setEsquizofrenia(boolean esquizofrenia) {
        this.esquizofrenia = esquizofrenia;
    }

    public boolean isAutismo() {
        return autismo;
    }

    public void setAutismo(boolean autismo) {
        this.autismo = autismo;
    }

    public String getOutraSaudeMental() {
        return outraSaudeMental;
    }

    public void setOutraSaudeMental(String outraSaudeMental) {
        this.outraSaudeMental = outraSaudeMental;
    }

    public boolean isNaoSabeResponderSaudeMental() {
        return naoSabeResponderSaudeMental;
    }

    public void setNaoSabeResponderSaudeMental(boolean naoSabeResponderSaudeMental) {
        this.naoSabeResponderSaudeMental = naoSabeResponderSaudeMental;
    }

    public int getMedicamentoControlado() {
        return medicamentoControlado;
    }

    public void setMedicamentoControlado(int medicamentoControlado) {
        this.medicamentoControlado = medicamentoControlado;
    }

    public String getQualMedicamentoControlado() {
        return qualMedicamentoControlado;
    }

    public void setQualMedicamentoControlado(String qualMedicamentoControlado) {
        this.qualMedicamentoControlado = qualMedicamentoControlado;
    }

    public boolean isAcompanhamentoMentalMomentoPrisao() {
        return acompanhamentoMentalMomentoPrisao;
    }

    public void setAcompanhamentoMentalMomentoPrisao(boolean acompanhamentoMentalMomentoPrisao) {
        this.acompanhamentoMentalMomentoPrisao = acompanhamentoMentalMomentoPrisao;
    }

    public String getMotivoAcompanhamentoMental() {
        return motivoAcompanhamentoMental;
    }

    public void setMotivoAcompanhamentoMental(String motivoAcompanhamentoMental) {
        this.motivoAcompanhamentoMental = motivoAcompanhamentoMental;
    }

    public boolean isAlcool() {
        return alcool;
    }

    public void setAlcool(boolean alcool) {
        this.alcool = alcool;
    }

    public boolean isCigarro() {
        return cigarro;
    }

    public void setCigarro(boolean cigarro) {
        this.cigarro = cigarro;
    }

    public boolean isMaconha() {
        return maconha;
    }

    public void setMaconha(boolean maconha) {
        this.maconha = maconha;
    }

    public boolean isCrack() {
        return crack;
    }

    public void setCrack(boolean crack) {
        this.crack = crack;
    }

    public boolean isCocaina() {
        return cocaina;
    }

    public void setCocaina(boolean cocaina) {
        this.cocaina = cocaina;
    }

    public boolean isAnfetaminas() {
        return anfetaminas;
    }

    public void setAnfetaminas(boolean anfetaminas) {
        this.anfetaminas = anfetaminas;
    }

    public boolean isDrogas() {
        return drogas;
    }

    public void setDrogas(boolean drogas) {
        this.drogas = drogas;
    }

    public String getOutrasDrogas() {
        return outrasDrogas;
    }

    public void setOutrasDrogas(String outrasDrogas) {
        this.outrasDrogas = outrasDrogas;
    }

    public boolean isTratamentoReabilitacao() {
        return tratamentoReabilitacao;
    }

    public void setTratamentoReabilitacao(boolean tratamentoReabilitacao) {
        this.tratamentoReabilitacao = tratamentoReabilitacao;
    }

    public String getTratamentoQualSubstancia() {
        return tratamentoQualSubstancia;
    }

    public void setTratamentoQualSubstancia(String tratamentoQualSubstancia) {
        this.tratamentoQualSubstancia = tratamentoQualSubstancia;
    }

    public boolean isQuerReabilitacao() {
        return querReabilitacao;
    }

    public void setQuerReabilitacao(boolean querReabilitacao) {
        this.querReabilitacao = querReabilitacao;
    }

    public String getReabilitacaoQualSubstancia() {
        return reabilitacaoQualSubstancia;
    }

    public void setReabilitacaoQualSubstancia(String reabilitacaoQualSubstancia) {
        this.reabilitacaoQualSubstancia = reabilitacaoQualSubstancia;
    }

    public boolean isOfertarPsicologa() {
        return ofertarPsicologa;
    }

    public void setOfertarPsicologa(boolean ofertarPsicologa) {
        this.ofertarPsicologa = ofertarPsicologa;
    }

    public boolean isOfertarPsiquiatra() {
        return ofertarPsiquiatra;
    }

    public void setOfertarPsiquiatra(boolean ofertarPsiquiatra) {
        this.ofertarPsiquiatra = ofertarPsiquiatra;
    }

    public boolean isEncaminharReceitas() {
        return encaminharReceitas;
    }

    public void setEncaminharReceitas(boolean encaminharReceitas) {
        this.encaminharReceitas = encaminharReceitas;
    }

    public boolean isEncaminharGrupoApoio() {
        return encaminharGrupoApoio;
    }

    public void setEncaminharGrupoApoio(boolean encaminharGrupoApoio) {
        this.encaminharGrupoApoio = encaminharGrupoApoio;
    }

    public int getVacinaCovid() {
        return vacinaCovid;
    }

    public void setVacinaCovid(int vacinaCovid) {
        this.vacinaCovid = vacinaCovid;
    }

    public int getVacinaInfluenza() {
        return vacinaInfluenza;
    }

    public void setVacinaInfluenza(int vacinaInfluenza) {
        this.vacinaInfluenza = vacinaInfluenza;
    }

    public int getVacinaTetano() {
        return vacinaTetano;
    }

    public void setVacinaTetano(int vacinaTetano) {
        this.vacinaTetano = vacinaTetano;
    }

    public int getVacinaHepatite() {
        return vacinaHepatite;
    }

    public void setVacinaHepatite(int vacinaHepatite) {
        this.vacinaHepatite = vacinaHepatite;
    }

    public boolean isOfertarVacinas() {
        return ofertarVacinas;
    }

    public void setOfertarVacinas(boolean ofertarVacinas) {
        this.ofertarVacinas = ofertarVacinas;
    }

    public boolean isFebreAmarela() {
        return febreAmarela;
    }

    public void setFebreAmarela(boolean febreAmarela) {
        this.febreAmarela = febreAmarela;
    }

    public boolean isHepatiteBVacina() {
        return hepatiteBVacina;
    }

    public void setHepatiteBVacina(boolean hepatiteBVacina) {
        this.hepatiteBVacina = hepatiteBVacina;
    }

    public boolean isCovid19() {
        return covid19;
    }

    public void setCovid19(boolean covid19) {
        this.covid19 = covid19;
    }

    public boolean isInfluenza() {
        return influenza;
    }

    public void setInfluenza(boolean influenza) {
        this.influenza = influenza;
    }

    public boolean isDuplaAdulto() {
        return duplaAdulto;
    }

    public void setDuplaAdulto(boolean duplaAdulto) {
        this.duplaAdulto = duplaAdulto;
    }

    public boolean isTripliceViral() {
        return tripliceViral;
    }

    public void setTripliceViral(boolean tripliceViral) {
        this.tripliceViral = tripliceViral;
    }

    public String getOutraVacina() {
        return outraVacina;
    }

    public void setOutraVacina(String outraVacina) {
        this.outraVacina = outraVacina;
    }

    public boolean isOfertarCarteiraVacinacao() {
        return ofertarCarteiraVacinacao;
    }

    public void setOfertarCarteiraVacinacao(boolean ofertarCarteiraVacinacao) {
        this.ofertarCarteiraVacinacao = ofertarCarteiraVacinacao;
    }

    public String getEncaminhamentosFinais() {
        return encaminhamentosFinais;
    }

    public void setEncaminhamentosFinais(String encaminhamentosFinais) {
        this.encaminhamentosFinais = encaminhamentosFinais;
    }
   

}

