package br.gov.prf.silver.util;

import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.prf.silver.domain.EnderecoPatio;
import br.gov.prf.silver.util.domains.CorreioCEP;
import br.gov.prf.wsclient.servo2.consulta.ConsultaMunicipio;
import br.gov.prf.wsclient.servo2.dominio.Municipio;

public class CorreiosClient {

    /**
     * @param cep
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static EnderecoPatio buscaEnderecoByCEP(String cep) throws IOException, InterruptedException {
        EnderecoPatio endereco = new EnderecoPatio();
        ObjectMapper mapper = new ObjectMapper();
        String out = null;
        try {
            out = new Scanner(new URL("https://viacep.com.br/ws/" + cep + "/json").openStream(), "UTF-8").useDelimiter("\\A").next();
        } catch (IOException e) {
            return null;
        }
        CorreioCEP correioCEP = mapper.readValue(out, CorreioCEP.class);
        String parsedLocaldiade = Normalizer.normalize(correioCEP.getLocalidade(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();

        ConsultaMunicipio consultaMunicipio = new ConsultaMunicipio();
        List<Municipio> municipios = consultaMunicipio.setUf(correioCEP.getUf()).getTudo();

        for (Municipio municipio : municipios) {
            if (municipio.getNome().toUpperCase().equals(parsedLocaldiade)) {
                endereco.setMunicipio(municipio.getId());
                endereco.setUfSigla(municipio.getUf().getSigla());
                break;
            }
        }

        endereco.setBairro(correioCEP.getBairro());
        endereco.setCep(correioCEP.getCep());
        endereco.setEndereco(correioCEP.getLogradouro());

        return endereco;
    }

}
