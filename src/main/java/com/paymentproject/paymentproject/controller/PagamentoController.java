package com.paymentproject.paymentproject.controller;


import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PagamentoController {

    @Value("${stripe.public.key}")
    private String stripePublicKey;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("stripePublicKey", stripePublicKey);
        return "pagamento"; // template Thymeleaf
    }

    @PostMapping("/charge")
    @ResponseBody
    public String realizarPagamento(@RequestBody Map<String, Object> dados) {
        System.out.println("POST /charge chamado"); // debug
        try {
            String stripeToken = (String) dados.get("stripeToken");
            System.out.println("token: " + stripeToken);
            Double valor = ((Number) dados.get("valor")).doubleValue();

            System.out.println("Token: " + stripeToken);
            System.out.println("Valor: " + valor);

            Map<String, Object> params = new HashMap<>();
            params.put("amount", (int) (valor * 100));
            params.put("currency", "brl");
            params.put("description", "Compra via Stripe + Spring Boot");
            params.put("source", stripeToken);

            Charge charge = Charge.create(params);
            return "Pagamento aprovado! ID: " + charge.getId();
        } catch (StripeException e) {
            e.printStackTrace();
            return "Erro no pagamento: " + e.getMessage();
        }
    }
}