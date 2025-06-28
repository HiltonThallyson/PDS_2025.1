package br.imd.framework.repositories;

import org.springframework.data.repository.CrudRepository;

import br.imd.framework.entities.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Long> {
    
}
