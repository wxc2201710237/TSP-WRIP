package TSP;

import java.util.ArrayList;
import java.util.List;

public class Combination {
    public static int[][] getCombination(int lb,int ub){
        int size= (ub-lb)*(ub-lb+1)/2;
        int[][] result=new int[size][];
        int index=0;
        for(int i=lb;i<ub;i++){
            for(int j=i+1;j<=ub;j++){
                result[index]=new int[]{i,j};
                index++;
            }
        }
        return result;
    }
    public static <T> List<List<T>> getCombination(List<T> list){
        int[][] indexCombination=getCombination(0,list.size()-1);
        List<List<T>> result= new ArrayList<>(indexCombination.length);
        for(int i=0;i<indexCombination.length;i++){
            List<T> combination= new ArrayList<>(2);
            combination.add(list.get(indexCombination[i][0]));
            combination.add(list.get(indexCombination[i][1]));
            result.add(combination);
        }
        return result;
    }}
