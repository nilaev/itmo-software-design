import org.junit.jupiter.api.Test;

public class LRUCacheTest {

    @Test
    public void mapTest() {
        LRUCache<String, Integer> cache = new LRUCache<>(1000);
        for (int i = 1; i <= 10; i++)
            assert cache.add(stars(i), i);

        assert cache.get("***") == 3;
        cache.add("***", 100);
        assert cache.get("***") == 3;
        cache.put("***", 200);
        assert cache.get("***") == 200;

        assert cache.get("") == null;
        assert cache.getOrPut("", 0) == 0;
        assert cache.get("") == 0;

        assert cache.get("***") == 200;
        assert cache.get("*****") == 5;
    }

    @Test
    public void cacheOverflowTest1() {
        LRUCache<Integer, String> cache = new LRUCache<>(100);
        for (int i = 1; i <= 200; i++)
            assert cache.add(i, stars(i));

        assert cache.get(1) == null;
        assert cache.get(50) == null;
        assert cache.get(100) == null;
        assert cache.get(101).equals(stars(101));
        assert cache.get(150).equals(stars(150));
        assert cache.get(200).equals(stars(200));
    }

    @Test
    public void cacheOverflowTest2() {
        LRUCache<String, Integer> cache = new LRUCache<>(500);
        assert cache.getOrPut("", -1) == -1;

        for (int i = 1; i <= 499; i++)
            cache.add(stars(i), i);
        assert cache.getOrPut("", -2) == -1;

        cache.put(stars(250), -250);
        assert cache.getOrPut("", -3) == -1;

        cache.put(stars(500), -500);
        assert cache.getOrPut("", -4) == -4;
    }

    @Test
    public void stressTest() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(1_000_000);
        for (int i = 0; i < 1_000_000; i++)
            cache.add(i, 2 * i);
        assert cache.get(300_000) == 600_000;
        assert cache.get(1_100_000) == null;

        for (int i = 500_000; i < 1_200_000; i++)
            cache.put(i, 3 * i);
        assert cache.get(100_000) == null;          // removed as LRU element
        assert cache.get(250_000) == 500_000;       // not removed
        assert cache.get(700_000) == 2_100_000;     // updated with .put()
        assert cache.get(1_100_000) == 3_300_000;   // added as new element
    }


    private static String stars(int n) {
        return "*".repeat(n);
    }

}
