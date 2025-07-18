package br.imd.framework.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.imd.framework.DTOs.ProdutoDTO;
import br.imd.framework.entities.Produto;
import br.imd.framework.enums.ProdutoStatus;
import br.imd.framework.exceptions.BusinessException;

@Service
public abstract class FavoritoService<T extends ProdutoDTO> {
    abstract public List<T> listarFavoritosPorUsuario(Long userId) throws BusinessException;

    abstract public Produto adicionarProdutoFavorito(Long userId, T produtoDTO) throws BusinessException;

    abstract public void removerProdutoFavorito(Long userId, Long produtoId) throws BusinessException;
  
    abstract public Map<ProdutoStatus, List<T>> listarFavoritosAgrupadosPorStatus(Long userId) throws BusinessException;
    
    
    abstract public void atualizarStatusLeitura(Long userId, Long produtoId, ProdutoStatus novoStatus) throws BusinessException;

}
