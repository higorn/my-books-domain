package higor.mybooks.domain;

import feign.RequestInterceptor;
import higor.mybooks.domain.book.Book;
import higor.mybooks.domain.userbook.UserBook;
import higor.mybooks.domain.userbook.UserBookClient;
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
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@EnableFeignClients
public class UserBooksRestRepoIT {

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
  private UserBookClient userBookClient;

//  @Test
  void shouldGetUserBooks() {
    PagedModel<EntityModel<UserBook>> pagedUserBooks = userBookClient
        .findByUserId(3, PageRequest.of(0, 10, Sort.Direction.ASC, "email"));
    assertNotNull(pagedUserBooks);

    List<UserBook> userBooks = pagedUserBooks.getContent().stream()
        .map(e -> {
          int id = getIdFromLink(e.getLink("self"));
          return e.getContent().id(id);
//          return new UserBook().id(id).book(getBook(id));
        }).collect(Collectors.toList());
    assertFalse(userBooks.isEmpty());
  }

  private Book getBook(int id) {
    EntityModel<Book> book = userBookClient.getBookByUserId(id);
    return book.getContent().id(getIdFromLink(book.getLink("self")));
  }

  private int getIdFromLink(Optional<Link> self2) {
    String self = self2.get().getHref();
    return Integer.parseInt(self.substring(self.lastIndexOf("/") + 1));
  }

}
