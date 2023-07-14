package com.dev.backend.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.backend.entity.Pessoa;
import com.dev.backend.repository.PessoaRepository;

@Service
public class PessoaGerenciamentoService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EmailService emailService;

    public String solicitarCodigo(String email){
       Pessoa pessoa = pessoaRepository.findByEmail(email);
       pessoa.setCodigoRecuperacaoSenha(getCodigoRecuperacaoSenha(pessoa.getId()));
       pessoa.setDataEnvioCodigo(new Date());
       pessoaRepository.saveAndFlush(pessoa);
       emailService.enviarEmailTexto(pessoa.getEmail(), "Código de Recuperação de Senha", 
                "Olá, seu código de recuperação de senha é o seguinte: "+pessoa.getCodigoRecuperacaoSenha()); //Envio de email

        return "Código Enviado!";
    }
    
    public String alterarSenha(Pessoa pessoa){
        Pessoa pessoaBanco = pessoaRepository.findByEmailAndCodigoRecuperacaoSenha(pessoa.getEmail(), pessoa.getCodigoRecuperacaoSenha());
        if(pessoaBanco!= null){
        
        
        //Calcular tempo atual - tempo que foi feito a solicitação do código
        Date diferenca = new Date(new Date().getTime() - pessoaBanco.getDataEnvioCodigo().getTime());

        //15min - se usuário solicitar depois do tempo
        if(diferenca.getTime()/1000 < 900){
            pessoaBanco.setSenha(pessoa.getSenha());
            pessoaBanco.setCodigoRecuperacaoSenha(null);
            pessoaRepository.saveAndFlush(pessoaBanco);

            return "Senha alterada com sucesso!";

        } else{
            return "Tempo expirado, solicite um novo código";
        }
    } else{
            return "E-mail ou código não encontrado!";
    }

    }

    private String getCodigoRecuperacaoSenha(Long id){
        DateFormat format = new SimpleDateFormat("ddMMyyyyHHmmssmm");

        return format.format(new Date())+id; //garantindo que o código seja único

    }
    
}
