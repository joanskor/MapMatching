package mapmatching;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequenceFactory;
import model.NodesEntity;
import model.PointGPX;

import java.util.*;

import db.DatabaseSession;
import model.EdgesEntity;
import model.ResultsEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.math.BigInteger;


public class Matcher {

     Set<Long> edges = new HashSet<>();;
     ArrayList<Coordinate> pathcoordinates = new ArrayList<>();
    ArrayList<EdgesEntity> pathedges=new ArrayList<>();
     //points: lista punktów
     //edgeNumber: liczba krawędzi którym obniżane są koszty
     //segmentNumber: liczba segmentów, na które dzielona jest trasa
    public LineString getPath(List<PointGPX> points, int edgeNumber, int segmentNumber){
        final Session session = DatabaseSession.getSession();
        org.hibernate.Transaction tr = session.beginTransaction();
        //tabelka pomocnicza
        session.createNativeQuery("create table IF NOT EXISTS waysCosts (way_id decimal,cost decimal); create index IF NOT EXISTS idx_costs_way on waysCosts (way_id)").executeUpdate();
        //tabelka z wynikami
//        session.createNativeQuery("create table IF NOT EXISTS results (id varchar(200));").executeUpdate();
//        //jeśli tworzona, to dodaj geometry
//            session.createNativeQuery("SELECT AddGeometryColumn('results', 'path', 4326, 'LINESTRING', 2);").list();
        tr.commit();
        tr = session.beginTransaction();
        // dla każdego punktu znajdujemy n najbliższych krawędzi
        for (PointGPX point : points) {
            final Query query = session.createNativeQuery("select a.* from (select e.id " +
                    ", st_distance(e.geom_way,st_geomfromtext('POINT(" +  point.getLongitude() + " " + point.getLatitude() + ")',4326)) as tempdistance " +
                    "from osm_2po_4pgr as e ) as a" +
                    " order by tempdistance limit "+ edgeNumber);
            List<Object[]> list = query.list();
            for (Object[] o : list) {
                //zapis krawędzi i odległości
                Long e =((Integer)o[0]).longValue();
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
        //trasa liczona co n punktów, żeby obejść pętle
        long lastNodeId=0l;
        for(int i=0; i<points.size(); i+=segmentNumber){
            //System.out.println(i);
            int end=i+segmentNumber;
            if(end>points.size()-1)
                end=points.size()-1;
            String query=" select edge,node FROM pgr_dijkstra(" +
                    "'select id as id,osm_source_id as source,osm_target_id as target" +
                    //koszt pomnożony przez wagę odległości
                    ",a.cost*COALESCE(w.cost,1) as cost" +
                    ",case when reverse_cost<>1000000 then reverse_cost*COALESCE(w.cost,1) else reverse_cost end as reverse_cost" +
                    " from osm_2po_4pgr a left join waysCosts w on (w.way_id=a.id)'";
            if(lastNodeId==0l)
                //jako punkt startowy, brany jest punkt startowy najbliższej krawędzi początkowego punktu gpx
                query+=",(select osm_source_id from osm_2po_4pgr order by st_distance(geom_way" +
                        ",st_geomfromtext('POINT(" + points.get(i).getLongitude() + " " + points.get(i).getLatitude() + ")',4326)) limit 1)";
            else
                query+="," +lastNodeId;

            //jako punkt końcowy, brany jest punkt końcowy najbliższej krawędzi końcowego punktu gpx
            query+=",(select osm_target_id from osm_2po_4pgr order by st_distance(geom_way" +
                    ",st_geomfromtext('POINT(" + points.get(end).getLongitude() + " " + points.get(end).getLatitude() + ")',4326)) limit 1)" +
                    ") order by seq";

            Query pathQuery = session.createNativeQuery(query);
            List<Object[]> result = pathQuery.list();
            for (Object[] o : result) {
                //node końcowy ma krawędź o id = -1 więc trzeba ją wykluczyć
                if (((BigInteger)o[0]) != BigInteger.valueOf(-1)) {
//                    pobieranie z bazy krawędzi
                    List<EdgesEntity> edge = session.createQuery("from EdgesEntity e where e.id =" + (BigInteger) o[0]).list();
                    //eliminacja duplikacji krawędzi
                    if (pathedges.isEmpty()||(!edge.isEmpty() && pathedges.get(pathedges.size()-1)!=edge.get(0))){
                        pathedges.add(edge.get(0));
                        //sprawdzenie orientacji krawędzi
                        if (edge.get(0).getOsmSourceId()==((BigInteger)o[1]).longValue())
                            for (int j=0;j<edge.get(0).getGeomWay().getNumPoints();j++) {
                                pathcoordinates.add(edge.get(0).getGeomWay().getCoordinateN(j));
                            }
                        else{
                            for (int j=edge.get(0).getGeomWay().getNumPoints()-1;j>=0;j--) {
                                pathcoordinates.add(edge.get(0).getGeomWay().getCoordinateN(j));
                            }
                        }
                    }

                }
                //ustawienie końcowego wierzchołka segmentu.
                lastNodeId=((BigInteger)o[1]).longValue();
            }
        }
        //konstrukcja linestring
        LineString path = new LineString(CoordinateArraySequenceFactory.instance().create(pathcoordinates.toArray(new Coordinate[pathcoordinates.size()])),new GeometryFactory(new PrecisionModel(),4326));
        System.out.println(path);
        //zapis do bazy
        ResultsEntity result = new ResultsEntity(new Date().toString(),path);
        session.save(result);
        session.createNativeQuery("drop table waysCosts").executeUpdate();
        tr.commit();
        session.close();
        return path;
    };
}
