package higor.mybooks.domain.book;

import higor.mybooks.domain.RemoteRepositoryClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "books", path = "/v1/books")
public interface BookClient extends RemoteRepositoryClient<Book> {

  @GetMapping(path = "/search/find-by-term")
  PagedModel<EntityModel<Book>> findByTerm(@RequestParam("term") String term, Pageable page);
}
