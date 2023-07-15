package com.dev.backend.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.backend.dto.PessoaClienteRequestDTO;
import com.dev.backend.entity.Pessoa;
import com.dev.backend.repository.PessoaClienteRepository;

@Service
public class PessoaClienteService {

    @Autowired
    private PessoaClienteRepository pessoaRepository;

    @Autowired
    private PermissaoPessoaService permissaoPessoaService;

    @Autowired
    private EmailService emailService;

    public Pessoa registrar(PessoaClienteRequestDTO pessoaClienteRequestDTO){
        Pessoa pessoa = new PessoaClienteRequestDTO().converter(pessoaClienteRequestDTO); //Recebe um DTO e converte para pessoa
        
        pessoa.setDataCriacao(new Date());
        Pessoa pessoaNova = pessoaRepository.saveAndFlush(pessoa);
        permissaoPessoaService.vincularPessoaPermissaoCliente(pessoaNova);
        
        //emailService.enviarEmailTexto(pessoaNova.getEmail(), "Cadastro na Loja TechTrove", "O registro na loja foi realizado com sucesso. Em breve você receberá a senha de acesso por e-mail!!");
        Map<String, Object> proPMap = new HashMap<>();
        proPMap.put("nome", pessoaNova.getNome());
        proPMap.put("mensagem", "O registro na loja foi realizado com sucesso. Em breve você receberá a senha de acesso por e-mail!!");

        emailService.enviarEmailTemplate(pessoaNova.getEmail(), "Cadastro na Loja TechTrove", proPMap);

        return pessoaNova;
    }

    
}
