import java.util.Comparator;
import java.util.List;

public interface SortingStrategy {
    public <T> List<T> sort(List<T> list, Comparator<? super T> comparator);
}
