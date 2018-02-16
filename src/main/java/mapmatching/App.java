package mapmatching;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequenceFactory;
import db.DatabaseSession;
import helpers.LoadDataHelper;
import helpers.SaveDataHelper;
import model.EdgesEntity;
import model.NodesEntity;
import model.PointGPX;
import model.ResultsEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Hello world!
 */
public class App {
    static List<PointGPX> points;
    static Set<Long> edges = new HashSet<>();;
    static Set<EdgesEntity> pathedges=new HashSet<>();
    static ArrayList<NodesEntity> pathnodes=new ArrayList<>();

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));

//        points = LoadDataHelper.getFirstData();
//        points = LoadDataHelper.getSecondData();
        points = LoadDataHelper.getThirdData();

        final Session session = DatabaseSession.getSession();
        org.hibernate.Transaction tr = session.beginTransaction();
        //tabelka pomocnicza
        session.createNativeQuery("create table IF NOT EXISTS waysCosts (way_id decimal,cost decimal); create index IF NOT EXISTS idx_costs_way on waysCosts (way_id)").executeUpdate();
        //tabelka z wynikami
        //session.createNativeQuery("create table IF NOT EXISTS results (filename varchar(200));").executeUpdate();
        //jeśli tworzona, to dodaj geometry
        //session.createNativeQuery("SELECT AddGeometryColumn('results', 'path', 4326, 'LINESTRING', 2);").list();
        tr.commit();
        tr = session.beginTransaction();
        // dla każdego punktu znajdujemy 3 najbliższe krawędzie
        for (PointGPX point : points) {
            final Query query = session.createNativeQuery("select a.* from (select e.osm_id " +
                    ", st_distance(e.geom_way,st_geomfromtext('POINT(" +  point.getLongitude() + " " + point.getLatitude() + ")',4326)) as tempdistance " +
                    "from osm_2po_4pgr as e ) as a" +
                    " order by tempdistance limit 3");
            List<Object[]> list = query.list();
            for (Object[] o : list) {
                //zapis krawędzi i odległości
                Long e =((BigInteger)o[0]).longValue();
                edges.add(e);
                point.way_distances.put(e,(Double)o[1]);
                //System.out.println(point.way_distances.get(e.getOsmId()));
            }
        }
        // obliczanie kosztów dla krawędzi na podstawie odległości (średnia odległości od uwzględniających daną krawędź punktów)
        for (Long e : edges){
            ArrayList<Double> distances = new ArrayList<>();
            for (PointGPX point : points){
                if (point.way_distances.get(e)!=null){
                    distances.add(point.way_distances.get(e));
                }
            }
            Double sum=0d;
            for(Double d : distances){
                sum+=d;
            }
            double distanceCost=sum/distances.size();

            session.createNativeQuery("insert into waysCosts values (" + e + ", " + distanceCost +")").executeUpdate();

        }
        tr.commit();
        tr = session.beginTransaction();
        //liczenie prawdopodobnej trasy między dwoma punktami za pomocą djikstra
        //trasa liczona co 4 punkty, żeby obejść pętle
        for(int i=0; i<points.size(); i+=20){
            //System.out.println(i);
            int end=i+20;
            if(end>points.size()-1)
                end=points.size()-1;
            String query=" select edge,node FROM pgr_dijkstra(" +
                    "'select osm_id as id,osm_source_id as source,osm_target_id as target" +
                    //koszt pomnożony przez wagę odległości
                    ",a.cost*COALESCE(w.cost,1) as cost" +
                    ",reverse_cost*COALESCE(w.cost,1) as reverse_cost" +
                    " from osm_2po_4pgr a left join waysCosts w on (w.way_id=a.osm_id)'";
            if(i==0||pathnodes.isEmpty())
                    //jako punkt startowy, brany jest punkt startowy najbliższej krawędzi początkowego punktu gpx
                query+=",(select osm_source_id from osm_2po_4pgr order by st_distance(geom_way" +
                    ",st_geomfromtext('POINT(" + points.get(i).getLongitude() + " " + points.get(i).getLatitude() + ")',4326)) limit 1)";
            else
                query+="," +pathnodes.get(pathnodes.size()-1).getId();

                    //jako punkt końcowy, brany jest punkt końcowy najbliższej krawędzi początkowego punktu gpx
            query+=",(select osm_target_id from osm_2po_4pgr order by st_distance(geom_way" +
                    ",st_geomfromtext('POINT(" + points.get(end).getLongitude() + " " + points.get(end).getLatitude() + ")',4326)) limit 1)" +
                    ") order by seq";

            Query pathQuery = session.createNativeQuery(query);
            List<Object[]> result = pathQuery.list();
            for (Object[] o : result) {
                //zwracało mi jakąś krawędź o id=-1 stąd warunek
                //if (((BigInteger)o[0]) != BigInteger.valueOf(-1)) {
                    //pobieranie z bazy krawędzi
                   // List<EdgesEntity> edge = session.createQuery("from EdgesEntity e where e.osmId =" + (BigInteger)o[0]).list();
                    //pathedges.add(edge.get(0));
                    //pobieranie z bazy nodów
                    List<NodesEntity> node = session.createQuery("from NodesEntity e where e.id =" + (BigInteger)o[1]).list();
                    //usuwanie powtórzeń nodów jeden po drugim
                    if (pathnodes.isEmpty() || pathnodes.get(pathnodes.size()-1)!=node.get(0))
                        //zapis ścieżki w nodach
                        pathnodes.add(node.get(0));
                   // System.out.println(edge.get(0));
               // }
            }
        }
        //**********tworzenie linestringa
        //zmiana nodów na koordynaty
        ArrayList<Coordinate> coordinates =new ArrayList<>();
        for (NodesEntity n : pathnodes){
            //System.out.println(n);
            coordinates.add(n.getGeom().getCoordinate());
        }
        //konstrukcja linestring
        LineString path = new LineString(CoordinateArraySequenceFactory.instance().create(coordinates.toArray(new Coordinate[coordinates.size()])),new GeometryFactory(new PrecisionModel(),4326));
        System.out.println(path);
        //zapis do bazy
        ResultsEntity result = new ResultsEntity("first",path);
        session.save(result);
        //zapis danych do pliku .gpx
        SaveDataHelper.saveLinestring(path);
        session.createNativeQuery("drop table waysCosts").executeUpdate();
        tr.commit();
        session.close();
    }
}
