package path_finder;

import model.CoordinatesModel;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.List;

public class PathFinder {

    public static Graph findPath(List<CoordinatesModel> coordinatesList) {
        DefaultDirectedWeightedGraph graph = createDirectedGraph(coordinatesList);
        return graph;
    }

    private static DefaultDirectedWeightedGraph createDirectedGraph(List<CoordinatesModel> coordinatesModels) {
        for (CoordinatesModel model : coordinatesModels) {
            System.out.println("lat: " + model.getLatitude() + "; lon: " + model.getLongitude());
        }

        DefaultDirectedWeightedGraph<CoordinatesModel, DefaultWeightedEdge> directedGraph
                = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        for (int i = 0; i < coordinatesModels.size(); i++) {
            directedGraph.addVertex(coordinatesModels.get(i));
            if (i > 0) {
                CoordinatesModel node1 = coordinatesModels.get(i - 1);
                CoordinatesModel node2 = coordinatesModels.get(i);
                double distance = countDistance(node1, node2);

                directedGraph.setEdgeWeight(directedGraph.addEdge(node1, node2), distance);
            }
        }

        return directedGraph;
    }

    public static double countDistance(CoordinatesModel node1, CoordinatesModel node2) {

        final int R = 6371; // Radius of the earth
        double lat1 = node1.getLatitude();
        double lat2 = node2.getLatitude();
        double lon1 = node1.getLongitude();
        double lon2 = node2.getLongitude();
        double el1 = node1.getAltitude();
        double el2 = node2.getAltitude();

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
