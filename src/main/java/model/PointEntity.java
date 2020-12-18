package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "pointEntity")
@Table(name = "pointEntity")
@NamedQuery(name = "pointEntity.FindByUser", query = "select point from pointEntity point where point.username = :username")
@NamedQuery(name="aaaa", query = "SELECT a FROM pointEntity a WHERE a.username = :username")
@NamedQuery(name = "pointEntity.Clear", query = "delete from pointEntity point where point.username = :username")
public class PointEntity implements Serializable {

    @SequenceGenerator(name = "sequence", sequenceName = "SEQUENCE", allocationSize = 1, initialValue = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    private long id;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User username;

    private double x;
    private double y;
    private double r;
    private boolean result;

    public PointEntity() {
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public boolean isResult() {
        return result;
    }

    public long getId() {
        return this.id;
    }

    public User getUsername() {
        return username;
    }

    public boolean check(double x, double y, double r) {
        return (x >= 0 && y >= 0 && x <= r && y <= r) ||
                (x >= 0 && y <= 0 && x * x + y * y <= Math.pow(r, 2)) || (y <= (2 * x + r) && y >= 0 && x <= 0);
    }


    @Override
    public String toString() {
        return +x + " " + y + " " + r + " " + result;
    }
}
