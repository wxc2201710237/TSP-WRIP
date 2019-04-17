package TSP.viewer;

import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class Viewer {
    public static void view(List<double[]> coordinates, List<Integer> vertices){

        List<double[]> vcdns=new ArrayList<double[]>(coordinates.size());
        double size=1;
        for(double[] cdn:coordinates){
//            输出坐标
//            System.out.println(cdn[0]+" "+cdn[1]);
            size=Math.max(Math.max(cdn[0],cdn[1]),size);
        }
//        size*=1.05;
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.setPenRadius(0.01);
        for(double[] cdn:coordinates){
            vcdns.add(new double[]{cdn[0]/size,cdn[1]/size});
            StdDraw.point(cdn[0]/size,cdn[1]/size);
            StdDraw.setPenColor(StdDraw.BLUE);
        }
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.003);
        for(int i=0;i<vertices.size();i++){
            int id1=vertices.get(i);
            int id2=vertices.get((i+1)%vcdns.size());
            StdDraw.line(vcdns.get(id1)[0],vcdns.get(id1)[1],vcdns.get(id2)[0],vcdns.get(id2)[1]);
        }

    }
}
