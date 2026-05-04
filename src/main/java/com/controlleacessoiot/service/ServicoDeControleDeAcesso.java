package com.controlleacessoiot.service;

import com.controlleacessoiot.model.RespostaDeStatus;
import org.springframework.stereotype.Service;


@Service
public class ServicoDeControleDeAcesso {

    private int limiteMaximoPermitido = 5; //limite inicial
    private int quantidadeDePessoasPresentes = 0; // Começa com o ambiente vazio

    // Método para registrar uma ENTRADA
    // "synchronized" garante que duas pessoas não entrem ao mesmo tempo e causem erro
    public synchronized RespostaDeStatus registrarEntrada() {
        String acaoRealizada;

        // REGRA: Só permite entrar se a quantidade atual for MENOR que o limite
        if (this.quantidadeDePessoasPresentes < this.limiteMaximoPermitido) {
            this.quantidadeDePessoasPresentes = this.quantidadeDePessoasPresentes + 1;
            acaoRealizada = "ENTRADA_PERMITIDA";
        } else {
            acaoRealizada = "ENTRADA_BLOQUEADA";
        }

        return this.montarResposta(acaoRealizada);
    }

    // Método para registrar uma SAÍDA
    public synchronized RespostaDeStatus registrarSaida() {
        String acaoRealizada;

        // REGRA: Só permite sair se tiver alguém no ambiente (maior que zero)
        if (this.quantidadeDePessoasPresentes > 0) {
            this.quantidadeDePessoasPresentes = this.quantidadeDePessoasPresentes - 1;
            acaoRealizada = "SAIDA_REGISTRADA";
        } else {
            acaoRealizada = "AMBIENTE_JA_VAZIO";
        }

        return this.montarResposta(acaoRealizada);
    }

    // Método para mudar o limite de capacidade
    public synchronized RespostaDeStatus configurarLimite(int novoLimite) {
        if (novoLimite >= 0) { // Evita números negativos
            this.limiteMaximoPermitido = novoLimite;
        }
        return this.montarResposta("LIMITE_ATUALIZADO");
    }

    // Método para zerar tudo (resetar o sistema)
    public synchronized RespostaDeStatus resetarSistema() {
        this.quantidadeDePessoasPresentes = 0;
        return this.montarResposta("SISTEMA_RESETADO");
    }

    // Método para consultar o estado sem alterar nada
    public synchronized RespostaDeStatus consultarStatus() {
        return this.montarResposta("CONSULTA_REALIZADA");
    }

    // Método privado auxiliar: monta o objeto JSON para devolver ao cliente
    private RespostaDeStatus montarResposta(String acaoRealizada) {
        String estadoDoAmbiente;

        // Se a quantidade for maior ou igual ao limite, está LOTADO
        if (this.quantidadeDePessoasPresentes >= this.limiteMaximoPermitido) {
            estadoDoAmbiente = "LOTADO";
        } else {
            estadoDoAmbiente = "DISPONIVEL";
        }

        // Cria e retorna o objeto que virará JSON
        return new RespostaDeStatus(
            this.quantidadeDePessoasPresentes,
            this.limiteMaximoPermitido,
            estadoDoAmbiente,
            acaoRealizada
        );
    }
}