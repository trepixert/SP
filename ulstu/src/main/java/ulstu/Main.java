package ulstu;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "0123456789"
            + "abcdefghijklmnopqrstuvxyz";

    public static String getAlphaNumericString(int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index
                    = (int)(ALPHA_NUMERIC_STRING.length()
                    * Math.random());
            sb.append(ALPHA_NUMERIC_STRING
                    .charAt(index));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int initialCapacity = 200;
        ArrayList<String> stringArrayList = IntStream.range(0, initialCapacity)
                .mapToObj(i -> getAlphaNumericString(32))
                .collect(Collectors.toCollection(() -> new ArrayList<>(initialCapacity)));

        int size = 400;

        Rehashing rehashing = new Rehashing(size);
        ChainingMethod chainingMethod = new ChainingMethod(size);

        System.out.println("Добавление - метод цепочки");
        long start = System.currentTimeMillis();
        stringArrayList.forEach(chainingMethod::add);
        long end = System.currentTimeMillis();

        System.out.println((end - start) + " мс");

        System.out.println("Добавление - рехэширование");
        start = System.currentTimeMillis();
        stringArrayList.forEach(rehashing::add);
        end = System.currentTimeMillis();

        System.out.println((end - start) + " мс");

    }
}
