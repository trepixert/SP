package ulstu;

import lombok.Value;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

@Value
public class ChainingMethod {
    List<LinkedList<String>> buckets;

    public ChainingMethod(int size) {
        buckets = new ArrayList<>(size);
        IntStream.range(0, size).forEach(i -> buckets.add(i, new LinkedList<>()));
    }

    public void add(String str) {
        int hashcode = HashCode.hashCode(str) % buckets.size();
        this.buckets.get(hashcode).add(str);
    }

    public boolean contains(String str){
        return buckets.get(HashCode.hashCode(str)).contains(str);
    }
}
