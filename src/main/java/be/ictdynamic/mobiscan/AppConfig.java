package be.ictdynamic.mobiscan;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

    @Value("${es.httpHosts}")
    private String httpHostsProperty;

    @Bean
    public RestHighLevelClient elasticRestClient () {
        String[] httpHosts = httpHostsProperty.split(";");
        HttpHost[] httpHostsAsArray = new HttpHost[httpHosts.length];
        int index = 0;

        for (String httpHostAsString : httpHosts) {
            HttpHost httpHost = new HttpHost(httpHostAsString.split(":")[0], new Integer(httpHostAsString.split(":")[1]), "http");
            httpHostsAsArray[index++] = httpHost;
        }

        return new RestHighLevelClient(RestClient.builder(httpHostsAsArray));
    }

}
