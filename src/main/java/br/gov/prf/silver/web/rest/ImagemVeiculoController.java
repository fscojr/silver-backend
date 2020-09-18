package br.gov.prf.silver.web.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.gov.prf.silver.exceptions.BusinessException;
import br.gov.prf.silver.service.ImagemVeiculoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

/**
 * Review by jonatas.carvalho.prestador on 01/2020
 */
@RestController
@RequestMapping("/imagem-veiculos")
@Api(value = "/imagem-veiculos", tags = {"Imagem Veiculo"})
@SwaggerDefinition(tags = {
    @Tag(name = "Imagem Veiculo", description = "Imagem Veiculo")
})
public class ImagemVeiculoController {

    @Autowired
    private ImagemVeiculoService imagemVeiculoService;

    @PostMapping(value = "/{type}", consumes = {"multipart/form-data"})
    public ResponseEntity<Map<String, String>> importQuestion(@PathVariable("type") Long type, @RequestParam("file") MultipartFile multipart) throws IOException, BusinessException {
        if (!multipart.getContentType().contains("image")) {
            throw new BusinessException("Esse tipo de Arquivo não é permitido");
        }
        Map<String, String> response = new HashMap<>();
        response.put("url", this.imagemVeiculoService.save(type, multipart));

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/img/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> findImg(@PathVariable("id") Long id) throws IOException {
        return ResponseEntity.ok(this.imagemVeiculoService.findImg(id));
    }

    @DeleteMapping(value = "/remove/{id}")
    public ResponseEntity remove(@PathVariable("id") Long id) {
        this.imagemVeiculoService.remove(id);
        return ResponseEntity.ok().build();
    }
}

