package higor.mybooks.domain.userbook;

import higor.mybooks.domain.RemoteRepositoryClient;
import higor.mybooks.domain.book.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-books", path = "/v1/userBooks")
public interface UserBookClient extends RemoteRepositoryClient<UserBook> {
  @GetMapping(path = "/search/findByUserId")
  PagedModel<EntityModel<UserBook>> findByUserId(@RequestParam("userId") Integer userId, Pageable page);
  @GetMapping(path = "/{id}/book")
  EntityModel<Book> getBookByUserId(@PathVariable("id") int id);
}
