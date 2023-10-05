package com.backend.sustentabilidade.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.backend.sustentabilidade.model.Postagem;

@Repository
public interface PostagemRepository extends PagingAndSortingRepository<Postagem, Long>{

}
