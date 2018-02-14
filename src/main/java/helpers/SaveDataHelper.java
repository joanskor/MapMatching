package helpers;

import com.hs.gpxparser.GPXWriter;
import com.hs.gpxparser.modal.*;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class SaveDataHelper {

    public static void saveLinestring(LineString lineString) {

        ArrayList<Waypoint> waypointList = new ArrayList<>();
        ArrayList<TrackSegment> trackSegments = new ArrayList<>();
        HashSet<Track> tracks = new HashSet<>();
        TrackSegment trackSegment = new TrackSegment();
        Track track = new Track();
        GPX gpx = new GPX();
        Bounds bounds = new Bounds(50.057289, 50.092066, 19.799959, 19.961568);
        Metadata metadata = new Metadata();
        gpx.setCreator("MapCreator");
        HashSet<Link> links = new HashSet<>();
        Link link = new Link("www.agh.edu.pl");
        link.setText("AGH");
        links.add(link);
        metadata.setLinks(links);
        metadata.setTime(new Date());
        metadata.setBounds(bounds);
        gpx.setMetadata(metadata);
        System.out.println("Coordinates json:");

        String linestringJson = "{\"linestring\":[";

        for (Coordinate coordinate : lineString.getCoordinates()) {
            Waypoint waypoint = new Waypoint(coordinate.y, coordinate.x);
            waypoint.setTime(new Date());
            waypoint.setElevation(coordinate.z);
            waypointList.add(waypoint);
            linestringJson += "{\"latitude\":\"" + coordinate.y + "\",\"longitude\":\"" + coordinate.x + "\"},";
        }
        linestringJson = linestringJson.substring(0, linestringJson.length() - 1);
        linestringJson += "]}";

        System.out.println(linestringJson);

        trackSegment.setWaypoints(waypointList);
        trackSegments.add(trackSegment);
        track.setName("OUTPUT TRACK");
        track.setTrackSegments(trackSegments);
        tracks.add(track);
        gpx.setTracks(tracks);
        GPXWriter writer = new GPXWriter();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(System.getProperty("user.dir") + "\\src\\main\\files\\outputData.gpx");
            writer.writeGPX(gpx, out);
            out.close();
            System.out.println("Data saved");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
