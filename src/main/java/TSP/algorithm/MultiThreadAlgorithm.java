package TSP.algorithm;

import TSP.Parallel.AlgorithmTask;
import TSP.graph.Graph;
import TSP.order.OrderGenerator;
import java.util.concurrent.ForkJoinPool;

public class MultiThreadAlgorithm {
    public Record excecute(Graph graph,OrderGenerator orderGenerator,int threadNum,Integer optimaLength){
        ConstructionAlgorithm algorithm = new Algorithm();
        Record record = new Record();
        record.setOptimaLength(optimaLength);
        ForkJoinPool pool = new ForkJoinPool(5);
        pool.invoke(new AlgorithmTask(graph,algorithm,orderGenerator,record,threadNum,System.currentTimeMillis()));
        return record;
    }
}
