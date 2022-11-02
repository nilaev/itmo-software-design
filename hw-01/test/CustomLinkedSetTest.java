import org.junit.jupiter.api.Test;

public class CustomLinkedSetTest {

    @Test
    public void sampleRemoveLastTest() {
        CustomLinkedSet<Integer> set = new CustomLinkedSet<>();
        for (int i = 1; i <= 3; i++)
            assert set.addFirst(i);
        assert set.size() == 3;
        for (int i = 1; i <= 3; i++)
            assert set.contains(i);
        assert set.removeLast() == 1;
        assert set.removeLast() == 2;
        assert set.removeLast() == 3;
        assert set.size() == 0;
    }

    @Test
    public void sampleRemoveTest() {
        CustomLinkedSet<Integer> set = new CustomLinkedSet<>();
        for (int i = 1; i <= 3; i++)
            assert set.addFirst(i);
        assert set.size() == 3;
        for (int i = 1; i <= 3; i++)
            assert set.contains(i);
        assert set.remove(3);
        assert set.remove(1);
        assert set.remove(2);
        assert set.size() == 0;
    }

    @Test
    public void sampleMixedRemoveTest() {
        CustomLinkedSet<String> set = new CustomLinkedSet<>();
        for (int i = 1; i <= 4; i++)
            assert set.addFirst(stars(i));
        assert set.size() == 4;
        for (int i = 1; i <= 4; i++)
            assert set.contains(stars(i));
        assert set.remove("****");
        assert set.removeLast().equals("*");
        assert set.remove("**");
        assert set.removeLast().equals("***");
        assert set.size() == 0;
    }

    @Test
    public void stressTest1() {
        CustomLinkedSet<Integer> set = new CustomLinkedSet<>();
        for (int i = 0; i < 3_000_000; i++)
            assert set.addFirst(i);
        // set = 0..2 999 999
        assert set.size() == 3_000_000;
        for (int i = 999_999; i >= 0; i--)
            assert set.remove(i);
        // set = 1 000 000..2 999 999
        for (int i = 0; i < 1_000_000; i++)
            assert set.removeLast().equals(1_000_000 + i);
        // set = 2 000 000..2 999 999
        assert set.size() == 1_000_000;
        for (int i = 0; i < 3_000_000; i++)
            assert (i < 2_000_000) != set.remove(i);
        assert set.size() == 0;
    }

    @Test
    public void stressTest2() {
        CustomLinkedSet<String> set = new CustomLinkedSet<>();
        for (int i = 1; i <= 25000; i++)
            set.addFirst(stars(i));
        assert set.size() == 25000;
        assert set.removeLast().equals("*");
        assert set.removeLast().equals("**");
        assert set.removeLast().equals("***");
        assert set.size() == 24997;
        assert !set.contains("**");
        for (int i = 4; i <= 25000; i++)
            assert set.remove(stars(i));
        assert set.size() == 0;
    }

    private static String stars(int n) {
        return "*".repeat(n);
    }
}
