package com.dev.backend.entity;

import java.util.Date;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;



@Entity
@Table (name = "pessoa")
@Data
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome ;
    private String cpf;
    private String email;
    private String codigoRecuperacaoSenha;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEnvioCodigo;
    
    private String senha;
    private String endereco;
    private String cep;

    @ManyToOne
    @JoinColumn(name = "idCidade")
    private Cidade cidade;

    @OneToMany(mappedBy = "pessoa", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //passa para o mapeamento o nome do atributo
    @Setter(value = AccessLevel.NONE) // desabilitando o setter que o lombok gera
    private List<PermissaoPessoa> permissaoPessoa;

    //Armazenar data e hora no banco
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    public void setPermissaoPessoa(List<PermissaoPessoa> pp){
        for(PermissaoPessoa p:pp){
            p.setPessoa(this);
        }
        this.permissaoPessoa = pp;
    }

    
}
