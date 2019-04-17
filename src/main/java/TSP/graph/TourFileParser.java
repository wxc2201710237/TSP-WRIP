package TSP.graph;

import TSP.Route;

import java.io.*;
import java.util.*;

public class TourFileParser {
    InputStream in;
    Map<String,String> properties;
    List<Integer> vertexSequence;
    public TourFileParser(String path) throws FileNotFoundException {
        this(new File(path));
    }
    public TourFileParser(File file) throws FileNotFoundException{
        this(new FileInputStream(file));
    }
    public TourFileParser(InputStream in){
        this.in = in;
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(in),1024*5));
        properties=new HashMap<String, String>();
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            if(line.contains(":")){
                String[] entry= line.split(":");
                properties.put(entry[0].trim(),entry[1].trim());
            }else{
                if(line.trim().equals("TOUR_SECTION")){
                    this.vertexSequence=loadData(scanner);
                }
            }
        }
    }

    public List<Integer> loadData(Scanner scanner){
        List<Integer> vertexSequence=new ArrayList<Integer>();
        while(scanner.hasNext()){
            int i=scanner.nextInt();
            if(i!=-1){
                vertexSequence.add(i-1);
            }else{
                return vertexSequence;
            }
        }
        return vertexSequence;
    }
    public Route generateTour(Graph graph){
        return new Route(graph,this.vertexSequence);
    }



}
