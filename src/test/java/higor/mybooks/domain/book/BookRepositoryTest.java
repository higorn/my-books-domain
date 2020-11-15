package higor.mybooks.domain.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest()
@EnableAutoConfiguration
@ContextConfiguration(classes = BookRepository.class)
class BookRepositoryTest {

  @Autowired
  private BookRepository repository;

  @Test
  void whenSaved_thenFindsByTitle() {
    Book savedBook = repository.save(
        new Book().id(1).title("Effective Java").subtitle("Programming Language Guide")
            .author("Joshua Bloch").publishingCompaty("Addison-Wesley").pages(252));
    assertNotNull(savedBook.getId());
  }
}