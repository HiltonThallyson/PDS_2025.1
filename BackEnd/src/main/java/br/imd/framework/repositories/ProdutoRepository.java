package br.imd.framework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import br.imd.framework.entities.Produto;

@NoRepositoryBean
public interface ProdutoRepository<T extends Produto, ID> extends JpaRepository<T, ID> {
    
}
