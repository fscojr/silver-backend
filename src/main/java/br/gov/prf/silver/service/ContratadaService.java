package br.gov.prf.silver.service;

import br.gov.dprf.motor.cliente.dominio.ListaPessoasJuridicas;
import br.gov.dprf.motor.cliente.dominio.Pessoa;
import br.gov.prf.silver.service.dto.ContratadaDTO;

import java.util.Optional;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface ContratadaService {

    ContratadaDTO salvarContratada(ContratadaDTO dto);

    Optional<ListaPessoasJuridicas> consultarCnpjNaReceita(String cnpj);

    Optional<Pessoa> consultarCPFNaReceita(String cpf);

}
