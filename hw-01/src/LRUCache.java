import java.util.HashMap;

public class LRUCache<K, V> {

    private final int capacity;
    private final HashMap<K, V> map = new HashMap<>();
    private final CustomLinkedSet<K> keyLinkedSet = new CustomLinkedSet<>();

    public LRUCache(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Expected capacity > 0");
        this.capacity = capacity;
    }

    public boolean add(K key, V value) {
        assert keySyncAssertion(key);
        if (map.containsKey(key)) {
            return false;
        } else {
            map.put(key, value);
            keyLinkedSet.addFirst(key);
            if (map.size() > capacity) {
                map.remove(keyLinkedSet.removeLast());
            }
            assert sizeSyncAssertion();
            assert sizeAssertion();
            return true;
        }
    }

    public void put(K key, V value) {
        assert keySyncAssertion(key);
        if (map.containsKey(key)) {
            map.remove(key);
            keyLinkedSet.remove(key);
        }
        map.put(key, value);
        keyLinkedSet.addFirst(key);
        if (map.size() > capacity) {
            map.remove(keyLinkedSet.removeLast());
        }
        assert sizeSyncAssertion();
        assert sizeAssertion();
    }

    public V getOrPut(K key, V defaultValue) {
        assert keySyncAssertion(key);
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            put(key, defaultValue);
            return defaultValue;
        }
    }

    public V get(K key) {
        assert keySyncAssertion(key);
        return map.getOrDefault(key, null);
    }


    private boolean keySyncAssertion(K key) {
        return map.containsKey(key) == keyLinkedSet.contains(key);
    }

    private boolean sizeSyncAssertion() {
        return map.size() == keyLinkedSet.size();
    }

    private boolean sizeAssertion() {
        return map.size() <= capacity;
    }

}
