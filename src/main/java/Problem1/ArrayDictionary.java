package Problem1;

import java.util.LinkedList;

public class ArrayDictionary implements Dictionary {
    private KVEntry[] entries;

    public ArrayDictionary(int capacity) {
        entries = new KVEntry[capacity];
    }

    @Override
    public boolean isEmpty() {
        // NOT IMPLEMENTED YET
        return false;
    }

    private int hashFunction(String key) {
        // not ideal
        return key.length();
    }

    @Override
    public void put(String key, String value) {

        int hashedKey = hashFunction(key);

        // when there's no entry yet
        if (entries[hashedKey] == null) {
            entries[hashedKey] = new KVEntry(key, value);
            return;
        }

        KVEntry ptr = entries[hashedKey];
        while (ptr.next != null) {
            // update value if key already exists
            if (ptr.key.equals(key)) {
                ptr.value = value;
                return;
            }
            ptr = ptr.next;
        }

        // add an entry to the end of the chain
        ptr.next = new KVEntry(key, value);
    }

    @Override
    public void remove(String key) {
        if (contains(key)) { //perform operation if key is in dict; if not, do nothing
            int hash = hashFunction(key);

            //if there are no entries, do nothing
            if (entries[hash] == null) {
                return;
            }

            KVEntry temp = entries[hash]; //key's value to check
            if (temp.key.equals(key)) { //check first KVEntry's key
                entries[hash] = temp.next;
            }

            while (temp.next != null) { //traverse till find same key
                if (temp.next.key.equals(key)) { //if key matches
                    KVEntry toKeep = temp.next.next; //skip over the KVEntry with matching key
                    temp.next = toKeep;
                    return;
                }
                temp = temp.next;
            }
        }
    }

    @Override
    public String get(String key) {
        int hashedKey = hashFunction(key);

        if (entries[hashedKey] == null) {
            return null;
        }

        KVEntry ptr = entries[hashedKey];
        while (ptr != null) {
            if (ptr.key.equals(key)) {
                return ptr.value;
            }
            ptr = ptr.next;
        }
        return null;
    }

    @Override
    public boolean contains(String key) {
        int hashedKey = hashFunction(key);

        if (hashedKey >= entries.length) {
            return false;
        }

        if (entries[hashedKey] == null) {
            return false;
        }

        KVEntry ptr = entries[hashedKey];
        while (ptr != null) {
            if (ptr.key.equals(key)) {
                return true;
            }
            ptr = ptr.next;
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] == null) {
                builder.append("dictionary[" + i + "] = null\n");
                continue;
            }
            KVEntry ptr = entries[i];
            builder.append("dictionary[" + i + "] = {");
            while (ptr != null) {
                builder.append("(" + ptr.key + ", " + ptr.value + ")");
                ptr = ptr.next;
            }
            builder.append("}\n");
        }
        return builder.toString();
    }
}
