package br.imd.framework.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.imd.framework.entities.Produto;

@Repository
public interface ProdutoFavoritoRepository extends CrudRepository<Produto, Long> {
    
}
