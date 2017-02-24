package asia.corelogic.hackathon.config;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestConfiguration {
    @Value("${corelogic.token}")
    private String token;


    @Bean
    public RestTemplate getTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Lists.newArrayList(
            new ClientHttpRequestInterceptor() {
                @Override
                public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                    System.err.println("********Token" + token);
                    request.getHeaders().set("Authorization", "Bearer " + token);
                    return execution.execute(request, body);
                }
            }
        ));
       // restTemplate.setMessageConverters(Lists.newArrayList(JsonMess));
        return restTemplate;
    }

}
