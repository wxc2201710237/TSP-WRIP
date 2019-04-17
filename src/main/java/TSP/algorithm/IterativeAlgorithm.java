package TSP.algorithm;

import TSP.Route;
import TSP.graph.Graph;
import TSP.order.ConstantOrder;
import TSP.order.OrderGenerator;

import java.util.List;

public class IterativeAlgorithm implements ConstructionAlgorithm {
    ConstructionAlgorithm algorithm;
    int limit=10;

    public IterativeAlgorithm(ConstructionAlgorithm algorithm){
        this.algorithm=algorithm;
    }
    @Override
    public Graph getGraph() {
        return algorithm.getGraph();
    }

    @Override
    public Route execute(Graph graph, OrderGenerator orderGenerator) {
        int count=0;
        long time= System.currentTimeMillis();
        long currentTime=time;
        Route route=algorithm.execute(graph,orderGenerator);
        System.out.println("Time: "+(System.currentTimeMillis()-time)+"ms");
        Route result=execute(graph,route);
        System.out.println("Time: "+(System.currentTimeMillis()-time)+"ms");
        while(true){
            if(equals(route,result)){
                count++;
            }else{
                count=0;
            }
            if(count>=limit){
                break;
            }
            route=result;
            result=execute(graph,route);
            System.out.println("Time: "+(System.currentTimeMillis()-time)+"ms");
        }
        return result;
    }

    @Override
    public ConstructionAlgorithm getInstance() {
        return new IterativeAlgorithm(this.algorithm.getInstance());
    }

    public Route execute(Graph graph, Route route){
        return algorithm.execute(graph,new ConstantOrder(route.vertexSequence));
    }

    public boolean equals(Route r1,Route r2){
        return equals(r1.vertexSequence,r2.vertexSequence);

    }
    public static boolean absoluteEquals(List<Integer> list1,List<Integer> list2){
        for(int i=0;i<list1.size();i++){
            if(list1.get(i)!=list2.get(i)){
               return false;
            }
        }
        return true;
    }
    public static boolean equals(List<Integer> list1,List<Integer> list2){
        int j=0;
        for(int i=0;i<list2.size();i++){
            if(list1.get(0)==list2.get(i)){
                j=i;
                break;
            }
        }
        for(int i=0;i<list1.size();i++){
            if(list1.get(i)!=list2.get(j)){
                return false;
            }
            j=(j+1)%list2.size();
        }
        return true;
    }
}
