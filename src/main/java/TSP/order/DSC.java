package TSP.order;

import TSP.algorithm.ConstructionAlgorithm;
import TSP.graph.Graph;

import java.util.Iterator;

public class DSC implements OrderGenerator {
    private ConstructionAlgorithm algorithm;
    @Override
    public void setAlgorithm(ConstructionAlgorithm algorithm) {
        this.algorithm=algorithm;
    }

    @Override
    public ConstructionAlgorithm getAlgorithm() {
        return this.algorithm;
    }

    @Override
    public OrderGenerator getInstance() {
        return new DSC();
    }

    public int[] generate(ConstructionAlgorithm algorithm) {
        int[] order = new int [algorithm.getGraph().getVertexCount()];
        for(int i=0;i<order.length;i++){
            order[i]=order.length-1-i;
        }
        return order;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            int i=algorithm.getGraph().getVertexCount()-1;
            @Override
            public boolean hasNext() {
                return i>=0;
            }

            @Override
            public Integer next() {
                return i--;
            }
        };
    }
}
