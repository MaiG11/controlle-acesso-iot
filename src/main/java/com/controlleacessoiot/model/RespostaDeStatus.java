package com.controlleacessoiot.model;

// Classe que define o formato da resposta JSON que a API vai enviar
public class RespostaDeStatus {

    private int quantidadeDePessoasPresentes;
    private int limiteMaximoPermitido;
    private String estadoDoAmbiente;   // "DISPONIVEL" ou "LOTADO"
    private String acaoRealizada;      // ex: "ENTRADA_PERMITIDA"

    // Construtor: método chamado ao criar um novo objeto
    public RespostaDeStatus(int quantidadeDePessoasPresentes, 
                           int limiteMaximoPermitido, 
                           String estadoDoAmbiente, 
                           String acaoRealizada) {
        this.quantidadeDePessoasPresentes = quantidadeDePessoasPresentes;
        this.limiteMaximoPermitido = limiteMaximoPermitido;
        this.estadoDoAmbiente = estadoDoAmbiente;
        this.acaoRealizada = acaoRealizada;
    }
    
    public int getQuantidadeDePessoasPresentes() {
        return quantidadeDePessoasPresentes;
    }

    public int getLimiteMaximoPermitido() {
        return limiteMaximoPermitido;
    }

    public String getEstadoDoAmbiente() {
        return estadoDoAmbiente;
    }

    public String getAcaoRealizada() {
        return acaoRealizada;
    }
}