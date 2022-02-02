package hibernateQueriesSample;

import javax.persistence.*;

@Entity
@Table(name = "user",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE
//    )
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false, precision = 10)
    public int id;
    @Column(name = "name", length = 20, nullable = true)
    public String name;
    @Column(name = "email", length = 20, nullable = true)
    public String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
