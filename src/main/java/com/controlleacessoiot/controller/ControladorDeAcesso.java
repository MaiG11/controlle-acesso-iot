package com.controlleacessoiot.controller;

import com.controlleacessoiot.model.RespostaDeStatus;
import com.controlleacessoiot.service.ServicoDeControleDeAcesso;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @RestController: transforma esta classe em um endpoint de API REST
// @RequestMapping("/api"): todas as URLs desta classe começarão com /api
@RestController
@RequestMapping("/api")
public class ControladorDeAcesso {

    // O Spring vai injetar automaticamente o serviço aqui
    private final ServicoDeControleDeAcesso servicoDeControleDeAcesso;

    // Construtor: recebe o serviço quando a aplicação sobe
    public ControladorDeAcesso(ServicoDeControleDeAcesso servicoDeControleDeAcesso) {
        this.servicoDeControleDeAcesso = servicoDeControleDeAcesso;
    }

    // URL: POST http://localhost:8080/api/entrada
    @PostMapping("/entrada")
    public ResponseEntity<RespostaDeStatus> receberEntrada() {
        RespostaDeStatus resposta = this.servicoDeControleDeAcesso.registrarEntrada();
        return ResponseEntity.ok(resposta);
    }

    // URL: POST http://localhost:8080/api/saida
    @PostMapping("/saida")
    public ResponseEntity<RespostaDeStatus> receberSaida() {
        RespostaDeStatus resposta = this.servicoDeControleDeAcesso.registrarSaida();
        return ResponseEntity.ok(resposta);
    }

    // URL: GET http://localhost:8080/api/status
    @GetMapping("/status")
    public ResponseEntity<RespostaDeStatus> receberStatus() {
        RespostaDeStatus resposta = this.servicoDeControleDeAcesso.consultarStatus();
        return ResponseEntity.ok(resposta);
    }

    // URL: POST http://localhost:8080/api/config/limite?limite=10
    @PostMapping("/config/limite")
    public ResponseEntity<RespostaDeStatus> receberConfiguracaoDeLimite(@RequestParam int limite) {
        RespostaDeStatus resposta = this.servicoDeControleDeAcesso.configurarLimite(limite);
        return ResponseEntity.ok(resposta);
    }

    // URL: POST http://localhost:8080/api/reset
    @PostMapping("/reset")
    public ResponseEntity<RespostaDeStatus> receberReset() {
        RespostaDeStatus resposta = this.servicoDeControleDeAcesso.resetarSistema();
        return ResponseEntity.ok(resposta);
    }
}