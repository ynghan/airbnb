package jpabook.start.domain.user;

import jpabook.start.domain.booking.Book;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Guest {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "guest_id")
  private Long id;

  @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
  private List<Book> book = new ArrayList<>();

  private String name;
  private int age;
  private String gender;


}
