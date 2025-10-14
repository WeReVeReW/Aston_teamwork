import java.util.ArrayList;
import java.util.Comparator;

public class BinarySearch<T>{
    public T search(ArrayList <? extends T> list, T obj, Comparator<? super T> comparator){
        int firstIndex = 0;
        int lastIndex = list.size()-1;

        int middle;
        while (firstIndex<=lastIndex){
            middle = (firstIndex+lastIndex)/2;

            int compareResult = comparator.compare(obj,list.get(middle));
            if (compareResult==0){
                return list.get(middle);
            }
            if (compareResult>0){
                firstIndex = middle+1;
            }
            if (compareResult<0){
                lastIndex = middle-1;
            }

        }
        return null;
    }
}
