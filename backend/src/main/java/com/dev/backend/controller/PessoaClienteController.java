package com.dev.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.dev.backend.dto.PessoaClienteRequestDTO;
import com.dev.backend.entity.Pessoa;
import com.dev.backend.service.PessoaClienteService;


@RestController
@RequestMapping("/api/cliente")
public class PessoaClienteController {

    @Autowired
    private PessoaClienteService pessoaService;

        
    @PostMapping("/") 
    public Pessoa registrar(@RequestBody PessoaClienteRequestDTO pessoaClienteRequestDTO){
       
        return pessoaService.registrar(pessoaClienteRequestDTO);

    }

   
}
