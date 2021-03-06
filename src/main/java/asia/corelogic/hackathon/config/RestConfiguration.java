package asia.corelogic.hackathon.config;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestConfiguration {
    @Value("${corelogic.token}")
    private String token;


    @Bean(name = "corelogicTemplate")
    public RestTemplate getTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Lists.newArrayList(
            new ClientHttpRequestInterceptor() {
                @Override
                public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                    request.getHeaders().set("Content-Type", "application/json");
                    request.getHeaders().set("Authorization", "Bearer " + token);
                    return execution.execute(request, body);
                }
            }
        ));
        restTemplate.setMessageConverters(Lists.newArrayList(new MappingJackson2HttpMessageConverter()));
        return restTemplate;
    }
    @Bean(name = "nabTemplate")
    public RestTemplate getNabTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Lists.newArrayList(
            new ClientHttpRequestInterceptor() {
                @Override
                public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                    request.getHeaders().set("Content-Type", "application/json");
                    request.getHeaders().set("x-nab-key", "0c12ce1a-f5a5-4933-b5aa-c27e14c757d7");
                    if (NabToken.TOKEN.get() != null) {
                        request.getHeaders().set("Authorization", NabToken.TOKEN.get());
                    }
                    return execution.execute(request, body);
                }
            }
        ));
        restTemplate.setMessageConverters(Lists.newArrayList(new MappingJackson2HttpMessageConverter()));
        return restTemplate;
    }

}
