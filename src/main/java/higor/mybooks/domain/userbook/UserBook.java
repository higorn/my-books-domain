package higor.mybooks.domain.userbook;

import higor.mybooks.domain.BaseEntity;
import higor.mybooks.domain.book.Book;
import higor.mybooks.domain.user.User;

import javax.persistence.*;

@Entity
@Table(name = "account_book")
public class UserBook implements BaseEntity<UserBook, Integer> {
  @Id
  @SequenceGenerator(name = "UserBook_SEQ", sequenceName = "account_book_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserBook_SEQ")
  private Integer id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  private User    user;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id")
  private Book    book;
  @Column(name = "is_read")
  private boolean read;

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public boolean isRead() {
    return read;
  }

  public void setRead(boolean read) {
    this.read = read;
  }

  @Override
  public UserBook id(Integer id) {
    this.id = id;
    return this;
  }

  public UserBook user(User user) {
    this.user = user;
    return this;
  }

  public UserBook book(Book book) {
    this.book = book;
    return this;
  }

  public UserBook read(boolean read) {
    this.read = read;
    return this;
  }
}
