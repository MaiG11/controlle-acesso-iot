package com.controlleacessoiot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// @Controller: Avisa ao Spring que esta classe retorna PÁGINAS (não JSON)
@Controller
public class PaginasController {

    // Quando alguém acessar http://localhost:8080/
    // Retorna a página index.html (da pasta templates/)
    @GetMapping("/")
    public String paginaInicial() {
        return "index";  // Procura templates/index.html
    }
}