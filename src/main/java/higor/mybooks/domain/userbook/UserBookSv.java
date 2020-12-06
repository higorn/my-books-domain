package higor.mybooks.domain.userbook;

public class UserBookSv {
  public String user;
  public String book;
  public boolean read;

  public UserBookSv() {
  }

  public UserBookSv(UserBook userBook) {
    this.user = "http://localhost:8081/v1/users/" + userBook.getUser().getId();
    this.book = "http://localhost:8081/v1/books/" + userBook.getBook().getId();
    this.read = userBook.isRead();
  }
}
