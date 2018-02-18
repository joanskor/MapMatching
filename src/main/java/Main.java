import helpers.LoadDataHelper;
import mapmatching.Matcher;
import model.PointGPX;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<PointGPX> points;
        System.out.println(System.getProperty("user.dir"));
         points = LoadDataHelper.getFirstData();
//        points = LoadDataHelper.getSecondData();
//         points = LoadDataHelper.getThirdData();
        new Matcher().getPath(points,3,20);
    };
}
