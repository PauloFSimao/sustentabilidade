package com.backend.sustentabilidade.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.backend.sustentabilidade.model.DicaSustentavel;

@Repository
public interface DicaSustentavelRepository extends PagingAndSortingRepository<DicaSustentavel, Long>{

}
