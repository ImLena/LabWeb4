package stateless;

import database.PointsDB;
import model.PointEntity;
import model.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.POST;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class Points {
    @EJB
    private PointsDB pointsDB;
    @EJB
    private Login login;

    private PointEntity point = new PointEntity();

    public String addPoint(double x, double y, double r, boolean result, String username) {
        if (validate()) {
            ;
            System.out.println("Point.add works");
            PointEntity pointEntity = new PointEntity();
            User user = login.getUserByUsername(username);
            if (user == null) {
                System.out.println("not authorized");
                return "authorize";
            }
            pointEntity.setUsername(user);
            pointEntity.setX(x);
            pointEntity.setY(y);
            pointEntity.setR(r);
            pointEntity.setResult(result);
            System.out.println("Point.add still works" + pointEntity.getUsername());
            pointsDB.addNewPoint(pointEntity);
            return "added";
        } else {
            System.out.println("not valid");
            return "not valid";
        }
    }

    public int clear(String username) {
        System.out.println("points.clear");
        User user = login.getUserByUsername(username);
        return pointsDB.clear(user);
    }

    private boolean validate() {
        return (point.getX() < 3 && point.getX() > -3) &&
                (point.getY() < 3 && point.getY() > -3) &&
                (point.getR() < 3 && point.getR() > -3);
    }

    public PointEntity getPoint() {
        return point;
    }

    public String getPoints(String username) {
        System.out.println("user: " + username);
        User user = login.getUserByUsername(username);
        List<PointEntity> points = pointsDB.getPoints(user);
        String ans = "<tr>" +
                "<td>X</td>" +
                "<td>Y</td>" +
                "<td>R</td>" +
                "<td>Result</td></tr>";
        if (points.isEmpty()){
            System.out.println("nooooooooooooooooooooo");
        }
        for (PointEntity p : points) {
            ans = ans + "<tr>" +
                    "<td>" + p.getX() + "</td>" +
                    "<td>" + p.getY() + "</td>" +
                    "<td>" + p.getR() + "</td>" +
                    "<td>" + p.isResult() + "</td></tr>";
        }
        // return ans;
        System.out.println(ans);
        return ans;
    }

}
