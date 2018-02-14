package mapmatching;

import helpers.LoadDataHelper;
import model.PointGPX;

import java.util.List;

import db.DatabaseSession;
import model.EdgesEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        List<PointGPX> points = LoadDataHelper.getFirstData();
        //List<PointGPX> points = LoadDataHelper.getSecondData();


        final Session session = DatabaseSession.getSession();
        org.hibernate.Transaction tr = session.beginTransaction();

        Set<Long> edges = new HashSet<>();
        for (PointGPX point : points) {
            final Query query = session.createNativeQuery("select a.* from (select e.osm_id " +
                    ", st_distance(e.geom_way,st_geomfromtext('POINT(" +  point.getLongitude() + " " + point.getLatitude() + ")',4326)) as tempdistance " +
                    "from osm_2po_4pgr as e ) as a" +
                    " order by tempdistance limit 3");
            List<Object[]> list = query.list();
            for (Object[] o : list) {
                Long e =((BigInteger)o[0]).longValue();
                edges.add(e);
                point.way_distances.put(e,(Double)o[1]);
                //System.out.println(point.way_distances.get(e.getOsmId()));
            }
        }
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
        for(int i=0; i<points.size(); i+=4){
            int end=i+4;
            if(end>points.size()-1)
                end=points.size()-1;
            Query pathQuery = session.createNativeQuery(
                    " select edge FROM pgr_dijkstra(" +
                            "'select osm_id as id,osm_source_id as source,osm_target_id as target" +
                            ",a.cost*COALESCE(w.cost,1) as cost" +
                            ",reverse_cost*COALESCE(w.cost,1) as reverse_cost" +
                            " from osm_2po_4pgr a left join waysCosts w on (w.way_id=a.osm_id)'" +
                            ",(select osm_source_id from osm_2po_4pgr order by st_distance(geom_way" +
                            ",st_geomfromtext('POINT(" + points.get(i).getLongitude() + " " + points.get(i).getLatitude() + ")',4326)) limit 1)" +
                            ",(select osm_target_id from osm_2po_4pgr order by st_distance(geom_way" +
                            ",st_geomfromtext('POINT(" + points.get(end).getLongitude() + " " + points.get(end).getLatitude() + ")',4326)) limit 1)" +
                            ") order by seq");
            List<BigInteger> result = pathQuery.list();
            for (BigInteger o : result) {
                if (o != BigInteger.valueOf(-1)) {
                    List<EdgesEntity> entity = session.createQuery("from EdgesEntity e where e.osmId =" + o).list();
                    System.out.println(entity.get(0));
                }
            }
        }
        session.createNativeQuery("truncate table waysCosts").executeUpdate();
        tr.commit();
        session.close();
    }
}
