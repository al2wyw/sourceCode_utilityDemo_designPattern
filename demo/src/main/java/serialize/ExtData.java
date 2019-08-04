package serialize;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2016/9/27
 * Time: 22:13
 * Desc:
 */
public class ExtData extends BaseData {
    private String ext;

    public ExtData(String ext) {
        System.out.println("ext args const");
        this.ext = ext;
    }

    public ExtData() {
        System.out.println("ext no-args const");
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
