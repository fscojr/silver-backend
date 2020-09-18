package br.gov.prf.silver.web.filters;

import br.gov.prf.silver.beans.RequestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestContextInitializationFilter extends GenericFilterBean {

    /**
     * The Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(RequestContextInitializationFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
            this.logger.debug(servletResponse.toString());
        } catch (RuntimeException e) {
            RequestResponse response = new RequestResponse();
            response.setError(e.getMessage());
            response.setErrorCode(Integer.toString(e.hashCode()));
            response.setStatus("error");
            response.setType(e.getClass().toString());
            HttpServletResponse hsr = (HttpServletResponse) servletResponse;
            hsr.setStatus(500);
            servletResponse.getWriter().write(this.convertObjectToJson(response));
            this.logger.error("Ocorreu uma excessão!", e);
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
