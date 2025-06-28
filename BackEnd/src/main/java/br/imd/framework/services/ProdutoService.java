package br.imd.framework.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.imd.framework.DTOs.ProdutoDTO;

@Service
public abstract class ProdutoService {

    abstract public List<ProdutoDTO> buscarPorNome(String titulo);

    abstract public List<ProdutoDTO> buscarPorQuantidade(int qtdPorCategoria);
}
