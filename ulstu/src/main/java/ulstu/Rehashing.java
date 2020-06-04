package ulstu;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.stream.IntStream;

@Data
public class Rehashing {
    private final int size;
    private ArrayList<String> buckets;

    public Rehashing(int size) {
        this.size = size;
        buckets = new ArrayList<>(size);
        IntStream.range(0, size).forEach(i -> buckets.add(i, ""));
    }

    public void add(String str) {
        int hash = HashCode.hashCode(str) % buckets.size();
        if (StringUtils.isEmpty(buckets.get(hash)) || buckets.get(hash).equals(str)) {
            buckets.add(hash, str);
            return;
        }

        int i = 1;
        while (true) {
            int rehash = (hash + i++) % buckets.size();
            if (rehash == hash) {
                rebuildBuckets();
                add(str);
            }

            if (StringUtils.isEmpty(buckets.get(hash)) || buckets.get(hash).equals(str)) {
                buckets.add(hash, str);
                return;
            }
        }
    }

    private void rebuildBuckets() {
        Rehashing rehashing = new Rehashing(size * 2);
        buckets.forEach(rehashing::add);
        buckets = rehashing.getBuckets();
    }

    public boolean contains(String str) {
        int hashCode = HashCode.hashCode(str) % buckets.size();
        String s = buckets.get(hashCode);
        if (StringUtils.isEmpty(s)) return false;

        if (str.equals(s)) return true;

        var i = 1;
        while (true) {
            int rehash = (hashCode + i++) % buckets.size();
            if (rehash == hashCode) {
                return false;
            }
            if (buckets.get(hashCode).equals(str)) {
                return true;
            }
        }

    }
}
