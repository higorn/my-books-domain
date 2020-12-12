package higor.mybooks.domain.book;

import feign.RequestInterceptor;
import higor.mybooks.domain.user.User;
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
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

import static org.junit.jupiter.api.Assertions.*;

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
  private BookClient     bookClient;

//  @Test
  void shouldGetBooks() {
    PagedModel<EntityModel<Book>> books = bookClient.findByTerm("a", PageRequest.of(0, 10, Sort.Direction.ASC, "title"));
    assertFalse(books.getContent().isEmpty());

    EntityModel<Book> book = bookClient.findById(1);
    assertNotNull(book);
  }

//  @Test
  void shouldCreateANewBook() {
    EntityModel<Book> book = bookClient.create(new Book()
        .title("My book30")
        .subtitle("A new book")
        .author("Higor")
        .publishingCompaty("My self")
        .pages(50));
    assertNotNull(book);

    String self = book.getLink("self").get().getHref();
    int id = Integer.parseInt(self.substring(self.lastIndexOf("/")+1));
    book = bookClient.findById(id);
    assertNotNull(book);
    assertEquals("My book30", book.getContent().getTitle());

    PagedModel<EntityModel<User>> users = bookClient.findBookUsers(id);
    assertTrue(users.getContent().isEmpty());

    ResponseEntity<Void> response = bookClient.updateBookUsers(id, "/v1/users/3");
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    users = bookClient.findBookUsers(id);
    assertFalse(users.getContent().isEmpty());
  }

}
