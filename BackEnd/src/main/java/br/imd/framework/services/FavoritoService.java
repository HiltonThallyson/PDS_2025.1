package br.imd.framework.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.imd.framework.DTOs.ProdutoDTO;
import br.imd.framework.enums.ProdutoStatus;
import br.imd.mybookplace.DTOS.FavoriteBookResponseDTO;

@Service
public abstract class FavoritoService {
    abstract public List<ProdutoDTO> listarFavoritosPorUsuario(Long userId);

    abstract public ProdutoDTO adicionarProdutoFavorito(Long userId, ProdutoDTO produtoDTO);

    abstract public void removerProdutoFavorito(Long userId, Long produtoId);
  
    abstract public Map<ProdutoStatus, List<FavoriteBookResponseDTO>> listarProdutosFavoritosPorStatus(Long userId);
    
    abstract public void atualizarStatusLeitura(Long userId, Long produtoId, ProdutoStatus novoStatus);

}
