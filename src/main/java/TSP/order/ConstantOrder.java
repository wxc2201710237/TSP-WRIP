package TSP.order;

import TSP.algorithm.ConstructionAlgorithm;

import java.util.Iterator;
import java.util.List;

public class ConstantOrder implements OrderGenerator {
    private ConstructionAlgorithm algorithm;
    int[] arr=new int[]{49, 36, 31, 26, 27, 30, 28, 29, 17, 4, 5, 0, 16, 18, 6, 7, 1, 15, 19, 14, 8, 9, 2, 13, 20, 10, 11, 3, 12, 21, 33, 22, 23, 32, 24, 25, 42, 48, 59, 58, 54, 47, 43, 35, 44, 45, 34, 46, 55, 67, 56, 57, 66, 69, 77, 81, 88, 91, 90, 78, 79, 68, 80, 89, 101, 102, 114, 123, 132, 124, 113, 112, 115, 122, 125, 133, 126, 127, 121, 110, 111, 103, 100, 92, 93, 76, 87, 86, 82, 70, 75, 74, 83, 85, 95, 94, 99, 104, 116, 120, 134, 128, 109, 108, 117, 119, 129, 135, 130, 131, 118, 106, 107, 105, 98, 96, 97, 84, 72, 73, 71, 64, 62, 63, 51, 38, 39, 37, 50, 52, 61, 40, 41, 60, 65, 53};

    public ConstantOrder(){
    }
    public ConstantOrder(int[] arr){
        this.arr=arr;
    }
    public ConstantOrder(List<Integer> list){
        this.arr=new int[list.size()];
        for(int i=0;i<arr.length;i++){
            arr[i]=list.get(i);
        }
    }
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
        return new ConstantOrder(this.arr);
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            int i=0;
            @Override
            public boolean hasNext() {
                return i<arr.length;
            }

            @Override
            public Integer next() {
                int result=arr[i++];

                return result;
            }
        };
    }

}
