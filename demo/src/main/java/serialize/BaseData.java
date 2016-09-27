package serialize;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2016/9/27
 * Time: 22:10
 * Desc:
 */
public class BaseData implements Serializable{
    private static final long serialVersionUID = -6942821026215577899L;

    private int i;

    private String name;

    private double d;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }
}
