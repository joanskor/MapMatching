import javafx.application.Application;
import javafx.stage.Stage;
import mock.CoordinatesMock;
import model.CoordinatesModel;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import path_finder.PathFinder;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        List<CoordinatesModel> coordinatesModelList = CoordinatesMock.getCoordinatesList();

        Graph graph = PathFinder.findPath(coordinatesModelList);

        System.out.println("\n\n\nMAIN:");

        List<CoordinatesModel> vertexList = new ArrayList<>(graph.vertexSet());
        for (int i = 0; i < graph.vertexSet().size(); i++) {
            System.out.println("NODE: lat: " + vertexList.get(i).getLatitude()
                    + ", lon: " + vertexList.get(i).getLongitude());

            if (i + 1 < graph.vertexSet().size()) {
                DefaultWeightedEdge edge = (DefaultWeightedEdge) graph.getEdge(vertexList.get(i), vertexList.get(i + 1));
                System.out.println("EDGE: " + graph.getEdgeWeight(edge));
            }
        }

    }
}
