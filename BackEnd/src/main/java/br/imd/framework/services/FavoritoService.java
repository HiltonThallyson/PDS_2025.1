package br.imd.framework.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.imd.framework.entities.Produto;
import br.imd.framework.enums.ProdutoStatus;

@Service
public abstract class FavoritoService {
    abstract public List<Produto> listarFavoritosPorUsuario(Long userId);

    abstract public Produto adicionarProdutoFavorito(Long userId, Produto produtoDTO);

    abstract public void removerProdutoFavorito(Long userId, Long produtoId);
  
    abstract public Map<ProdutoStatus, List<Produto>> listarProdutosFavoritosPorStatus(Long userId);
    
    abstract public void atualizarStatusLeitura(Long userId, Long produtoId, ProdutoStatus novoStatus);

}
