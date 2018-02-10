package mock;

import model.CoordinatesModel;

import java.util.ArrayList;
import java.util.List;

public class CoordinatesMock {

    public static List<CoordinatesModel> getCoordinatesList() {
        List<CoordinatesModel> coordinatesList = new ArrayList<>();
        coordinatesList.add(new CoordinatesModel(50.061389, 19.938333, 214));
        coordinatesList.add(new CoordinatesModel(50.061217, 19.938183, 214));
        coordinatesList.add(new CoordinatesModel(50.061059, 19.938097, 214));
        coordinatesList.add(new CoordinatesModel(50.060866, 19.937947, 214));
        coordinatesList.add(new CoordinatesModel(50.060570, 19.937711, 214));
        coordinatesList.add(new CoordinatesModel(50.060384, 19.937647, 214));
        coordinatesList.add(new CoordinatesModel(50.060053, 19.937690, 214));
        coordinatesList.add(new CoordinatesModel(50.059737, 19.937754, 214));
        coordinatesList.add(new CoordinatesModel(50.059461, 19.937797, 214));
        coordinatesList.add(new CoordinatesModel(50.059144, 19.937840, 214));
        coordinatesList.add(new CoordinatesModel(50.059117, 19.938312, 214));
        coordinatesList.add(new CoordinatesModel(50.059117, 19.939020, 214));

        return coordinatesList;
    }
}
