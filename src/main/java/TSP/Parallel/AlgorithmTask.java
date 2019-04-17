package TSP.Parallel;

import TSP.Route;
import TSP.algorithm.ConstructionAlgorithm;
import TSP.algorithm.Record;
import TSP.graph.Graph;
import TSP.order.ConstantOrder;
import TSP.order.OrderGenerator;
import TSP.util.OutputUtil;

import java.util.concurrent.RecursiveAction;

public class AlgorithmTask extends RecursiveAction {
    Graph graph;
    int num;
    ConstructionAlgorithm algorithm;
    OrderGenerator orderGenerator;
    Record record;
    long beginTime;

    public AlgorithmTask(Graph graph, ConstructionAlgorithm algorithm, OrderGenerator orderGenerator, Record record, int num, long beginTime) {
        this.graph = graph;
        this.algorithm = algorithm;
        this.record = record;
        this.orderGenerator = orderGenerator;
        this.num = num;
        this.beginTime = beginTime;
    }

    @Override
    protected void compute() {
        if (num < 2) {
//            OrderGenerator orderGenerator= this.orderGenerator;
            Route route = null;
            do {
                if (record.isSuccess(route)) {
                    break;
                }
                route = algorithm.execute(graph, orderGenerator);
                if (record.contains(route)) {
                    break;
                }
                record.add(route);
                orderGenerator = new ConstantOrder(route.vertexSequence);
                int length = route.getLength();
                System.out.println("cost: " + length);
            } while (true);
        } else {
            AlgorithmTask task1 = new AlgorithmTask(graph, algorithm.getInstance(), orderGenerator.getInstance(), record, num / 2, beginTime);
            AlgorithmTask task2 = new AlgorithmTask(graph, algorithm.getInstance(), orderGenerator.getInstance(), record, num - num / 2, beginTime);
            invokeAll(task1, task2);
        }

    }
}
