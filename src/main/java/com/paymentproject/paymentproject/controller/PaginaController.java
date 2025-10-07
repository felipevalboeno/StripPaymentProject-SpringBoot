package com.paymentproject.paymentproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaController {

    @GetMapping("/pagamento")
    public String pagamentoPage() {
        return "pagamento"; // retorna pagamento.html de /templates/
    }
}