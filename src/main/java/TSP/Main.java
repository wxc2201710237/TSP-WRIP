package TSP;

import TSP.algorithm.Algorithm;
import TSP.algorithm.ConstructionAlgorithm;
import TSP.algorithm.IterativeAlgorithm;
import TSP.graph.Graph;
import TSP.graph.TSPParser;
import TSP.graph.TourFileParser;
import TSP.graph.TspXmlParser;
import TSP.order.*;
import TSP.viewer.Viewer;
import org.dom4j.DocumentException;

import java.io.FileNotFoundException;


public class Main {
    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        String name = "bier127";
        TspXmlParser parser = new TspXmlParser("/Users/liyang/Documents/TSP/ALL_tsp/" + name + ".xml");
        Graph graph = parser.generateGraph();
        long time= System.currentTimeMillis();
        OrderGenerator orderGenerator = new RandomOrder();
        ConstructionAlgorithm algorithm= new Algorithm();
        IterativeAlgorithm ia=new IterativeAlgorithm(algorithm);
        Route route = ia.execute(graph, orderGenerator);
//        Route route = new TourFileParser("/Users/liyang/Documents/TSP/ALL_tsp/tour/" + name + ".opt.tour").generateTour(graph);
        System.out.println();
        System.out.println(System.currentTimeMillis()-time);
        System.out.print("route:");
        String prefix=" ";
        for (int i : route.vertexSequence) {
            System.out.print(prefix+i);
            prefix=", ";
        }
        System.out.println("\ncost:"+route.getLength());
        TSPParser tspparser = new TSPParser("/Users/liyang/Documents/TSP/ALL_tsp/" + name + ".tsp");
        Viewer.view(tspparser.coordinates, route.vertexSequence);
    }
}
