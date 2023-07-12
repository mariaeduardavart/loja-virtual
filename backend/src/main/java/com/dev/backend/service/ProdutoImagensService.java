package com.dev.backend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dev.backend.entity.Produto;
import com.dev.backend.entity.ProdutoImagens;
import com.dev.backend.repository.ProdutoImagensRepository;
import com.dev.backend.repository.ProdutoRepository;

@Service
public class ProdutoImagensService {

    @Autowired
    private ProdutoImagensRepository produtoImagensRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoImagens>buscarTodos(){
        return produtoImagensRepository.findAll();

    }

    public ProdutoImagens inserir(Long idProduto, MultipartFile file){
        Produto produto = produtoRepository.findById(idProduto).get();
        ProdutoImagens imagem = new ProdutoImagens();

        try{
            if(!file.isEmpty()){
                byte[] bytes = file.getBytes(); //Arquivo em bytes
                String nomeImagem =  String.valueOf(produto.getId() + file.getOriginalFilename());
                Path caminho = Paths.get("C:/imagens/"+nomeImagem);
                Files.write(caminho, bytes); //Imagem será salva no caminho determinado
                imagem.setNome(nomeImagem);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        imagem.setProduto(produto);
        imagem.setDataCriacao(new Date());
        imagem = produtoImagensRepository.saveAndFlush(imagem); //salvar no banco
        
        return imagem;
    }

     public ProdutoImagens alterar(ProdutoImagens produtoImagens){
        produtoImagens.setDataAtualizacao(new Date());
        return produtoImagensRepository.saveAndFlush(produtoImagens);
        
    }

     public void excluir(Long id){
         ProdutoImagens produtoImagens = produtoImagensRepository.findById(id).get();
         produtoImagensRepository.delete(produtoImagens);
    }
    
}
