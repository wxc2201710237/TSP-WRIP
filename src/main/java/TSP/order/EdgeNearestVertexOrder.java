package TSP.order;

import TSP.RandomUtil;
import TSP.Route;
import TSP.algorithm.Algorithm;
import TSP.algorithm.ConstructionAlgorithm;
import TSP.algorithm.RouteArchive;
import TSP.graph.Graph;
import edu.princeton.cs.algs4.In;

import java.util.Iterator;
import java.util.List;

public class EdgeNearestVertexOrder implements OrderGenerator {

    Graph graph;
    boolean[] selectedVertices;
    Algorithm algorithm;

    @Override
    public void setAlgorithm(ConstructionAlgorithm algorithm) {
        this.algorithm = (Algorithm) algorithm;
    }

    @Override
    public ConstructionAlgorithm getAlgorithm() {
        return this.algorithm;
    }

    @Override
    public OrderGenerator getInstance() {
        return new EdgeNearestVertexOrder();
    }

    @Override
    public Iterator<Integer> iterator() {
        this.graph=algorithm.getGraph();
        selectedVertices = new boolean[graph.getVertexCount()];
        for (int i = 0; i < selectedVertices.length; i++) {
            selectedVertices[i] = false;
        }
        return new Iterator<Integer>() {

            int i = 0;
            Integer[] sequence=new Integer[graph.getVertexCount()];

            @Override
            public boolean hasNext() {
                if (i < graph.getVertexCount()) {
                    return true;
                }
                return false;
            }

            @Override
            public Integer next() {
                return get(i++);
            }

            private Integer get(int i) {
                if(sequence[i]!=null){
                    return sequence[i];
                }
                int result=0;
                if (i == 0) {
                    result = RandomUtil.RandomInt(graph.getVertexCount());
//                    int cost= Integer.MAX_VALUE;
//                    for(int j=0;j<graph.getVertexCount()-1;j++){
//                        for(int k=j+1;k<graph.getVertexCount();k++){
//                            if(graph.getCost(j,k)<cost){
//                                result=k;
//                                cost=graph.getCost(j,k);
//                            }
//                        }
//                    }

                } else if (i == 1) {
//                    result = second = RandomUtil.RandomInt(graph.getVertexCount());
                    result =getNearestVertex(get(0));
                } else if (i == 2) {
                    result=getNearestVertex(get(0),get(1));
                } else {
                    result = getOptimaVertex(algorithm.getArchive(),get(i-1));
                }
                selectedVertices[result] = true;
                sequence[i]=result;
                return result;
            }
        };
    }

    private int getNearestVertex(int id) {
        int minCost = Integer.MAX_VALUE;
        int nearestVertex = 0;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            if (i != id && !selectedVertices[i]) {
                int cost = graph.getCost(id, i);
                if (cost < minCost) {
                    minCost = cost;
                    nearestVertex = i;
                }
            }

        }
        return nearestVertex;
    }

    private int getNearestVertex(Route route) {
        int result=0;
        int cost=Integer.MAX_VALUE;
        for(int[] edge:route.getEdges()){
            for(int i=0;i<graph.getVertexCount();i++){
                if(!selectedVertices[i]&&getCost(edge[0],edge[1],i)<cost){
                    cost=getCost(edge[0],edge[1],i);
                    result=i;
                }
            }
        }
        return result;
    }
    private int getNearestVertex(RouteArchive archive) {
        int result=0;
        int cost=Integer.MAX_VALUE;
        List<int[]> edges=archive.getKeys();
        for(int[] edge:edges){
            for(int i=0;i<graph.getVertexCount();i++){
                if(!selectedVertices[i]&&getCost(edge[0],edge[1],i)<cost){
                    cost=getCost(edge[0],edge[1],i);
                    result=i;
                }
            }
        }
        return result;
    }
    private int getOptimaVertex(RouteArchive archive) {
        int result=0;
        int cost=Integer.MAX_VALUE;
        List<int[]> edges=archive.getKeys();
        for(int[] edge:edges){
            for(int i=0;i<graph.getVertexCount();i++){
                if(!selectedVertices[i]&&getCost(edge[0],edge[1],i)+archive.get(edge[0],edge[1])[0].getLength()<cost){
                    cost=getCost(edge[0],edge[1],i)+archive.get(edge[0],edge[1])[0].getLength();
                    result=i;
                }
            }
        }
        return result;
    }
    private int getOptimaVertex(RouteArchive archive,int lastVertex) {
        int result=0;
        int cost=Integer.MAX_VALUE;
        List<int[]> edges=archive.getKeys();
        for(int[] edge:edges){
            if(edge[0]==lastVertex||edge[1]==lastVertex){
                for(int i=0;i<graph.getVertexCount();i++){
                    if(!selectedVertices[i]&&getCost(edge[0],edge[1],i)+archive.get(edge[0],edge[1])[0].getLength()<cost){
                        cost=getCost(edge[0],edge[1],i)+archive.get(edge[0],edge[1])[0].getLength();
                        result=i;
                    }
                }
            }

        }
        return result;
    }


    private int getNearestVertex(int v1, int v2) {
        int result=0;
        int cost=Integer.MAX_VALUE;
        for(int i=0;i<graph.getVertexCount();i++){
            if(!selectedVertices[i]&&getCost(v1,v2,i)<cost){
                cost=getCost(v1,v2,i);
                result=i;
            }

        }
        return result;
    }

    private int getCost(int v1, int v2,int insertV){
        return graph.getCost(insertV,v1)+graph.getCost(insertV,v2)-graph.getCost(v1,v2);
    }
}
