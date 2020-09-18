package br.gov.prf.silver.service;

import br.gov.prf.silver.service.dto.EstadoVeiculoDTO;
import br.gov.prf.silver.service.dto.ImagemVeiculoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface ImagemVeiculoService {

    List<ImagemVeiculoDTO> saveAll(List<ImagemVeiculoDTO> dto, EstadoVeiculoDTO estadoVeiculo);

    String save(Long type, MultipartFile multipart) throws IOException;

    byte[] findImg(Long id) throws IOException;

    void remove(Long id);
}
