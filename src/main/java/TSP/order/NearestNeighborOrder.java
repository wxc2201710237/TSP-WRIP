package TSP.order;

import TSP.algorithm.ConstructionAlgorithm;
import TSP.graph.Graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class NearestNeighborOrder implements OrderGenerator {
    boolean[] selectedVertices;
    List<Integer> order;
    Graph graph;

    ConstructionAlgorithm algorithm;

    @Override
    public void setAlgorithm(ConstructionAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public ConstructionAlgorithm getAlgorithm() {
        return this.algorithm;
    }

    @Override
    public OrderGenerator getInstance() {
        return new NearestNeighborOrder();
    }

    public void a1(Graph graph) {
        Random random = new Random();
        put(random.nextInt(graph.getVertexCount()));
        while (order.size() < graph.getVertexCount()) {
            int id = order.get(random.nextInt(order.size()));
            put(getNearestVertex(id));
        }
    }

    public void a2(Graph graph) {
        Random random = new Random();
        put(random.nextInt(graph.getVertexCount()));
        while (order.size() < graph.getVertexCount()) {
            int id = order.get(order.size() - 1);
            put(getNearestVertex(id));
        }
    }

    public void a3(Graph graph) {
        Random random = new Random();
        put(random.nextInt(graph.getVertexCount()));
        while (order.size() < graph.getVertexCount()) {
            put(getNearestVertex(order));
        }
    }

    public void a4(Graph graph) {
        int first = 16;
        put(first);
        while (order.size() < graph.getVertexCount()) {
            put(getNearestVertex(order));
        }
    }

    private int[] getOrder() {
        int[] arr = new int[this.order.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = this.order.get(i);
        }
        return arr;
    }

    private void resetSelectedVertices() {
        if (selectedVertices == null || selectedVertices.length != graph.getVertexCount()) {
            selectedVertices = new boolean[graph.getVertexCount()];
        }
        for (int i = 0; i < selectedVertices.length; i++) {
            selectedVertices[i] = false;
        }
    }

    private int getNearestVertex(int id) {
        int minCost = Integer.MAX_VALUE;
        int nearestVertex = 0;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            if (i != id && !isSelected(i)) {
                int cost = graph.getCost(id, i);
                if (cost < minCost) {
                    minCost = cost;
                    nearestVertex = i;
                }
            }

        }
        return nearestVertex;
    }

    private int getNearestVertex(List<Integer> list) {
        int minCost = Integer.MAX_VALUE;
        int nearestVertex = 0;
        for (int id : list) {
            int nv = getNearestVertex(id);
            int cost = graph.getCost(nv, id);
            if (cost < minCost) {
                minCost = cost;
                nearestVertex = nv;
            }
        }
        return nearestVertex;
    }

    private void put(int id) {
        order.add(id);
        selectedVertices[id] = true;
    }

    private boolean isSelected(int i) {
        return selectedVertices[i];
    }

    @Override
    public Iterator<Integer> iterator() {
        this.graph = algorithm.getGraph();
        resetSelectedVertices();
        order = new ArrayList<>(graph.getVertexCount());
        a2(graph);
        return order.iterator();
    }
}
