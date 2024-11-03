package br.com.trabalho.romulo.api.trabalhoRomulo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepositery extends JpaRepository<Pessoas, Long> {

}