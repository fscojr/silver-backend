package br.gov.prf.silver.service.impl;

import br.gov.prf.silver.domain.recolhimento.ImagemVeiculo;
import br.gov.prf.silver.repository.ImagemVeiculoRepository;
import br.gov.prf.silver.repository.TipoImagemVeiculoRepository;
import br.gov.prf.silver.service.ImagemVeiculoService;
import br.gov.prf.silver.service.dto.EstadoVeiculoDTO;
import br.gov.prf.silver.service.dto.ImagemVeiculoDTO;
import br.gov.prf.silver.service.mapper.EstadoVeiculoMapper;
import br.gov.prf.silver.service.mapper.ImagemVeiculoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */
@Service
@Transactional
public class ImagemVeiculoServiceImpl implements ImagemVeiculoService {
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ImagemVeiculoMapper imagemVeiculoMapper;

    @Autowired
    private ImagemVeiculoRepository imagemVeiculoRepository;

    @Autowired
    private TipoImagemVeiculoRepository tipoImagemVeiculoRepository;

    @Autowired
    private EstadoVeiculoMapper estadoVeiculoMapper;

    public List<ImagemVeiculoDTO> saveAll(List<ImagemVeiculoDTO> imagemList, EstadoVeiculoDTO estadoVeiculo) {
        List<ImagemVeiculo> imagemVeiculoCollection = new ArrayList<>();

        for (ImagemVeiculoDTO dto : imagemList) {
            ImagemVeiculo imagemVeiculo = imagemVeiculoMapper.toEntity(dto);

            imagemVeiculo.setEstadoVeiculo(estadoVeiculoMapper.toEntity(estadoVeiculo));
            imagemVeiculo.setDhInclusao(LocalDateTime.now());
            this.imagemVeiculoRepository.save(imagemVeiculo);
            imagemVeiculoCollection.add(imagemVeiculo);
        }
        return imagemVeiculoMapper.toDto(imagemVeiculoCollection);
    }

    public String save(Long type, MultipartFile multipart) throws IOException {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
        String fileName = date + multipart.getOriginalFilename();

        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
        String[] path = new String[]{"src", "main", "resources", "tmp-files", fileName};
        String filePath = Paths.get(root.toString(), path).toString();
        File dest = new File(filePath);
        multipart.transferTo(dest);

        ImagemVeiculo img = new ImagemVeiculo();
        img.setArquivo(String.join("/", path));
        img.setDescricao("Insert");
        img.setExtensao(multipart.getContentType().split("/")[0]);
        img.setDhInclusao(LocalDateTime.now());
        img.setTipoImagemVeiculo(this.tipoImagemVeiculoRepository.findById(type).orElse(null));
        ImagemVeiculo saved = this.imagemVeiculoRepository.save(img);

        return String.valueOf(ServletUriComponentsBuilder.fromCurrentContextPath().pathSegment(
            "imagem-veiculo", "img", String.valueOf(saved.getId())
        ).build());
    }

    public byte[] findImg(Long id) throws IOException {
        Optional<ImagemVeiculo> img = this.imagemVeiculoRepository.findById(id);
        if (img.isPresent()) {
            Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
            RandomAccessFile f = new RandomAccessFile(root.toString().concat("/").concat(img.get().getArquivo()), "r");
            byte[] b = new byte[(int) f.length()];
            f.readFully(b);

            return b;
        }
        return null;
    }

    public void remove(Long id) {
        Optional<ImagemVeiculo> img = this.imagemVeiculoRepository.findById(id);
        if (img.isPresent()) {
            Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
            File file = new File(root.toString().concat("/").concat(img.get().getArquivo()));
            file.delete();
            this.imagemVeiculoRepository.delete(img.get());
        }
    }

}
