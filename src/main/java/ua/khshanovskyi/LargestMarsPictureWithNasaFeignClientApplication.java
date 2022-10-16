package ua.khshanovskyi;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@EnableFeignClients
@SpringBootApplication
public class LargestMarsPictureWithNasaFeignClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(LargestMarsPictureWithNasaFeignClientApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(
          HttpClientBuilder.create()
            .setRedirectStrategy(new LaxRedirectStrategy())
            .build())
        );
    }

}
