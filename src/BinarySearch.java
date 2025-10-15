import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;

public class BinarySearch<T>{

    private boolean useHashCode = false;

    public T searchByOneField(ArrayList <? extends T> list, T obj, Comparator<? super T> comparator){
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

    private int compareHashCode(Object obj1, Object obj2){
        return Integer.compare(obj1.hashCode(), obj2.hashCode());
    }

    private int compareToString(Object obj1, Object obj2) {
        return obj1.toString().compareTo(obj2.toString());
    }

    private int compareFields(Object obj1, Object obj2){
        int classCompare = obj1.getClass().getName().compareTo(obj2.getClass().getName());
        if (classCompare != 0) return classCompare;

        try {
            Field[] fields = obj1.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value1 = field.get(obj1);
                Object value2 = field.get(obj2);

                int fieldCompare = compareFieldValues(value1, value2);
                if (fieldCompare != 0) return fieldCompare;

            }
            return 0;
        } catch (IllegalAccessException e) {
            // Если не удалось получить доступ к полям, используем toString
            return compareToString(obj1, obj2);
        }
//
    }


    @SuppressWarnings("unchecked")
    private int compareFieldValues(Object value1, Object value2) {
        if (value1 == null && value2 == null) return 0;
        if (value1 == null) return -1;
        if (value2 == null) return 1;

        if (value1 instanceof Comparable && value2 instanceof Comparable) {
            try {
                return ((Comparable<Object>) value1).compareTo(value2);
            } catch (ClassCastException e) {
                // Продолжаем
            }
        }

        return value1.toString().compareTo(value2.toString());
    }


    public T search(ArrayList <? extends T> list, T obj, Comparator<? super T> comparator){
        // находим 1-е и последнее вхождение элемента по заданному компаратору
        int first = findFirstOccurance(list,obj,comparator);
        int last = findLastOccurance(list,obj,comparator);


        if (first == -1){
            return null;
        }

        if (first == last){
            if(isUseHashCode()){
                if (compareHashCode(list.get(first),obj)==0){
                    if(compareFields(list.get(first),obj)==0){
                        return list.get(first);
                    }
                }
            }else{
                if(compareFields(list.get(first),obj)==0){
                    return list.get(first);
                }
            }
        }

        for(int i=first;i<=last;i++){
            if(isUseHashCode()){
                if (compareHashCode(list.get(i),obj)==0){
                    if(compareFields(list.get(i),obj)==0){
                        return list.get(i);
                    }
                }
            }else{
                if(compareFields(list.get(i),obj)==0){
                    return list.get(i);
                }
            }
        }
        return null;
    }

    public int findFirstOccurance(ArrayList <? extends T> list, T obj, Comparator<? super T> comparator){
        int firstIndex = 0;
        int lastIndex = list.size()-1;

        int firstOccuranceIndex = -1;
        int middle=0;
        while (firstIndex<=lastIndex){
            middle = (firstIndex+lastIndex)/2;

            int compareResult = comparator.compare(obj,list.get(middle));
            if (compareResult==0){
                firstOccuranceIndex = middle;
                lastIndex = middle-1;
            }
            if (compareResult>0){
                firstIndex = middle+1;
            }
            if (compareResult<0){
                lastIndex = middle-1;
            }
        }
        return firstOccuranceIndex;
    };

    public int findLastOccurance(ArrayList <? extends T> list, T obj, Comparator<? super T> comparator){
        int firstIndex = 0;
        int lastIndex = list.size()-1;

        int lastOccuranceIndex = -1;
        int middle=0;
        while (firstIndex<=lastIndex){
            middle = (firstIndex+lastIndex)/2;

            int compareResult = comparator.compare(obj,list.get(middle));
            if (compareResult==0){
                lastOccuranceIndex = middle;
                firstIndex = middle+1;
            }
            if (compareResult>0){
                firstIndex = middle+1;
            }
            if (compareResult<0){
                lastIndex = middle-1;
            }
        }
        return lastOccuranceIndex;
    };

    public boolean isUseHashCode() {
        return useHashCode;
    }

    public BinarySearch<T> setUseHashCode(boolean useHashCode) {
        this.useHashCode = useHashCode;
        return this;
    }
}
