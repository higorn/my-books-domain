package higor.mybooks.domain.userbook;

import higor.mybooks.domain.BaseEntity;
import higor.mybooks.domain.book.Book;
import higor.mybooks.domain.user.User;

public class UserBook implements BaseEntity<UserBook, Integer> {
  private Integer id;
  private User    user;
  private Book    book;
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
