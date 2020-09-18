package br.gov.prf.silver.service.impl;

import br.gov.dprf.motor.cliente.WSMotorPessoas;
import br.gov.dprf.motor.cliente.WSMotorPessoasJuridicas;
import br.gov.dprf.motor.cliente.dominio.ListaPessoasJuridicas;
import br.gov.dprf.motor.cliente.dominio.Pessoa;
import br.gov.prf.silver.domain.contratoPatio.Contratada;
import br.gov.prf.silver.repository.ContratadaRepository;
import br.gov.prf.silver.service.ContratadaService;
import br.gov.prf.silver.service.dto.ContratadaDTO;
import br.gov.prf.silver.service.mapper.ContratadaMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */
@Service
@Transactional
public class ContratadaServiceImpl implements ContratadaService {

    @Value("${prf.matricula}")
    private String matricula;

    @Value("${prf.cpf}")
    private String cpf;

    @Autowired private ContratadaMapper contratadaMapper;
    @Autowired private ContratadaRepository contratadaRepository;

    
    @Override
    public ContratadaDTO salvarContratada(ContratadaDTO dto) {
        dto.setCnpj(removeCarecteresCnpj(dto.getCnpj()));
        Contratada contratada = contratadaMapper.toEntity(dto);
        verificaCnpj(contratada);
        if (contratada.getId() == null) {
            contratada.setAtivo(true);
            contratada.setDhInclusao(LocalDateTime.now());
            contratadaRepository.save(contratada);
        }
        dto.setId(contratada.getId());
        return dto;
    }

    public Optional<ListaPessoasJuridicas> consultarCnpjNaReceita(String cnpj) {
        String cnpjWithoutPoints = removeCarecteresCnpj(cnpj);

        return Optional.ofNullable(WSMotorPessoasJuridicas.getInstance().pesquisaPessoaJuridicaPorCnpj(Integer.parseInt(this.matricula), this.cpf, cnpjWithoutPoints));
    }

    public Optional<Pessoa> consultarCPFNaReceita(String cpf) {
        String cpfWithoutPoints = removeCarecteresCnpj(cpf);

        return Optional.ofNullable(WSMotorPessoas.getInstance().pesquisaPessoaPorCpf(Integer.parseInt(this.matricula), this.cpf, cpfWithoutPoints));
    }

    private String removeCarecteresCnpj(String cnpj) {
        return cnpj.replace(".", "").replace("/", "").replace("-", "");
    }

    private Contratada verificaCnpj(Contratada contratada) {
        Contratada contratadaResult = contratadaRepository.findByCnpj(contratada.getCnpj());
        if (contratadaResult != null) {
            BeanUtils.copyProperties(contratadaResult, contratada);
        }
        return contratada;
    }

}
