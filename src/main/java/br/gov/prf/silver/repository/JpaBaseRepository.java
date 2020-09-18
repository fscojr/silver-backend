package br.gov.prf.silver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface JpaBaseRepository<T, PK> extends JpaRepository<T, PK>, JpaSpecificationExecutor<T> {

}
