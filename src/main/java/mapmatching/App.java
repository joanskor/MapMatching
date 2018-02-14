package mapmatching;

import helpers.LoadDataHelper;
import model.TemporaryPointGPX;

import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        List<TemporaryPointGPX> pointGPXList1 = LoadDataHelper.getFirstData();
        List<TemporaryPointGPX> pointGPXList2 = LoadDataHelper.getSecondData();

        System.out.println("FIRST:");
        for (TemporaryPointGPX point1 : pointGPXList1) {
            System.out.println("Lat: " + point1.getLatitude()
                    + "; long: " + point1.getLongitude()
                    + "; time: " + point1.getTime());
        }

        System.out.println("SECOND:");
        for (TemporaryPointGPX point2 : pointGPXList2) {
            System.out.println("Lat: " + point2.getLatitude()
                    + "; long: " + point2.getLongitude()
                    + "; time: " + point2.getTime());
        }
    }
}
