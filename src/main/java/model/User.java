package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "user")
@Table(name = "users")
@NamedQuery(name = "user.FindUser", query = "select user from user user where user.username = :username")
public class User implements Serializable {
    @SequenceGenerator(name = "sequence", sequenceName = "SEQUENCE", allocationSize = 1, initialValue = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "username")
    private List<PointEntity> points;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<PointEntity> getPoints() {
        return points;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPoints(List<PointEntity> points) {
        this.points = points;
    }
}
