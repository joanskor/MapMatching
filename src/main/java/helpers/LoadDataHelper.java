package helpers;

import com.hs.gpxparser.GPXParser;
import com.hs.gpxparser.modal.GPX;
import com.hs.gpxparser.modal.Track;
import com.hs.gpxparser.modal.TrackSegment;
import com.hs.gpxparser.modal.Waypoint;
import model.PointGPX;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class LoadDataHelper {

    private static GPX getGPXDataFromFile(String fileName) {
        GPXParser gpxParser = new GPXParser();
        {
            try {
                FileInputStream fileInputStream = new FileInputStream(fileName);
                return gpxParser.parseGPX(fileInputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static List<PointGPX> getPointGPXData(String fileName) {
        List<PointGPX> pointGPXList = new ArrayList<>();
        GPX gpx = getGPXDataFromFile(fileName);
        if (gpx != null && gpx.getTracks() != null) {
            for (Track track : gpx.getTracks()) {
                for (TrackSegment trackSegment : track.getTrackSegments()) {
                    System.out.println("Size of waypoints: " + trackSegment.getWaypoints().size());
                    for (Waypoint waypoint : trackSegment.getWaypoints())
                        pointGPXList.add(new PointGPX(waypoint.getLatitude(), waypoint.getLongitude(), waypoint.getTime()));
                }
            }
        }
        return pointGPXList;
    }

    public static List<PointGPX> getFirstData() {
        return getPointGPXData(System.getProperty("user.dir") + "\\src\\main\\files\\1431627.gpx");
    }

    public static List<PointGPX> getSecondData() {
        return getPointGPXData(System.getProperty("user.dir") + "\\src\\main\\files\\1384589.gpx");
    }
}
