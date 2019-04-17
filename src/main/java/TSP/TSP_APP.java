package TSP;

import TSP.algorithm.MultiThreadAlgorithm;
import TSP.algorithm.Record;
import TSP.graph.Graph;
import TSP.graph.TSPParser;
import TSP.graph.TspXmlParser;
import TSP.order.OrderGenerator;
import TSP.order.RandomOrder;
import TSP.util.OutputUtil;
import TSP.viewer.Viewer;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.FileNotFoundException;

public class TSP_APP {
    public static void main(String[] args) throws FileNotFoundException, DocumentException {
//        String[] names = new String[]{"kroE100","eil101","lin105","pr107","gr120","pr124","ch130","gr96"};
//        int[] optimas = new int[]{22068,629,14379,44303,6942,59030,6110,55209};

        String[] names = new String[]{"hk48"};
        int[] optimas = new int[]{11461};
        int[] time = new int[]{1};
        String dir = "/Users/liyang/Documents/TSP/ALL_tsp/";

        for (int i = 0; i < names.length; i++) {
            solve(dir, names[i], optimas[i], time[i]);
        }
    }

    public static void solve(String path, String name, int optimaLength, int time) throws FileNotFoundException, DocumentException {
        TspXmlParser parser = new TspXmlParser(path + name + ".xml");
        TSPParser tspparser = new TSPParser(path + name + ".tsp");
        OutputUtil.setOutputFile(new File(path + name + "_result.txt"));
        Graph graph = parser.generateGraph();
        for (int i = 0; i < time; i++) {
            OutputUtil.println(i + 1 + "#########################################################################################################");
            MultiThreadAlgorithm multiSeedAlgorithm = new MultiThreadAlgorithm();
            OrderGenerator orderGenerator = new RandomOrder();
            long beginTime = System.currentTimeMillis();
            Record record = multiSeedAlgorithm.excecute(graph, orderGenerator, 5, optimaLength);
            OutputUtil.println("time: " + (System.currentTimeMillis() - beginTime));
            OutputUtil.println("optima: " + record.getOptimaLength());
            Route optima = record.getOptima().get(0);
            OutputUtil.println(optima.vertexSequence);
            try {
                Viewer.view(tspparser.coordinates, optima.vertexSequence);
            } catch (Exception e) {
                System.out.println("No coordinate");
            }
        }
        OutputUtil.close();
    }
}
