package br.com.trabalho.romulo.api.trabalhoRomulo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexControl {

    // trata requisição GET da raiz do site
    @GetMapping({ "", "/" })
    public String index() {
        return "index";
    }
}
