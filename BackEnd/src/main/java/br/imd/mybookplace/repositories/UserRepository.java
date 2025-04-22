package br.imd.mybookplace.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.imd.mybookplace.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<UserDetails> findByUsername(String username);
    Optional<UserDetails> findByEmail(String email);
}
