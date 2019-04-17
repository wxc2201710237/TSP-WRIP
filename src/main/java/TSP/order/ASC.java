package TSP.order;

import TSP.algorithm.ConstructionAlgorithm;

import java.util.Iterator;

public class ASC implements OrderGenerator {
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
        return new ASC();
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            int i=0;
            @Override
            public boolean hasNext() {
                return i<algorithm.getGraph().getVertexCount();
            }

            @Override
            public Integer next() {
                return i++;
            }
        };
    }
}
