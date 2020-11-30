package higor.mybooks.domain.book;

import feign.RequestInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@EnableFeignClients
public class BooksRestRepoIT {

  @SpringBootConfiguration
  @EnableAutoConfiguration
  @ComponentScan(excludeFilters = { @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
      @ComponentScan.Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
  public static class TestConfig {

    @Bean
    public RequestInterceptor requestInterceptor(OAuth2ProtectedResourceDetails details) {
      return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), details);
    }
  }

  @Autowired
  private BookClient bookClient;

  @Test
  void shouldGetBooks() {
    PagedModel<Book> books = bookClient.getBooks(PageRequest.of(0, 10, Sort.Direction.ASC, "title"));
    assertFalse(books.getContent().isEmpty());
  }

}
