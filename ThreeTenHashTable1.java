class ThreeTenHashTable1<K, V> {
    //you must use this storage for the hash table
    //and you may not alter this variable's name, type, etc.
    private TableEntry<K, V>[] storage;
    private int currSize;

    /* +++++++++++++++++++ YOUR CODE HERE +++++++++++++++++++ */
    //Add additional instance variables here!
    //You may also add additional _private_ methods if you'd like.

    @SuppressWarnings("unchecked")
    public ThreeTenHashTable1(int size) {
        //Create a hash table where the size of the storage is
        //the provided size (number of "slots" in the table)
        //You may assume size is >= 2
        storage = (TableEntry<K, V>[]) new TableEntry[size];
        currSize = 0;
        //Remember... if you want an array of ClassWithGeneric<V>, the following format ___SHOULD NOT___ be used:
        //         ClassWithGeneric<V>[] items = (ClassWithGeneric<V>,[]) Object[10];
        //instead, use this format:
        //         ClassWithGeneric<V>[] items = (ClassWithGeneric<V>,[]) ClassWithGeneric[10];
    }

    public int getCapacity() {
        //return how many "slots" are in the table
        //O(1)
        return storage.length;
    }

    public int size() {
        //return the number of elements in the table
        //O(1)
        return currSize;
    }

    public void put(K k, V v) {
        //Place value v at the location of key k.
        //Use linear probing if that location is in use.
        //You may assume both k and v will not be null.

        //Hint: Make a TableEntry to store in storage
        //and use the absolute value of k.hashCode() for
        //the probe start.

        //If the key already exists in the table
        //replace the current value with v.

        //If the load on the table is >= 80% _after_ adding
        //expand the table to twice the size.

        //Worst case: O(n), Average case: O(1)
        insert(storage, k, v, getCapacity());

        if ((float) size() / getCapacity() >= 0.8) {
            rehash(2 * getCapacity());
        }

    }
    private void insert(TableEntry<K,V> [] myStorage, K k, V v, int capacity){
        boolean startFromFront = true;
        boolean replacement = false;
        int keyHashCode = Math.abs(k.hashCode());
        int hashIndex = keyHashCode % capacity;
        TableEntry<K, V> newEntry = new TableEntry<>(k, v);
        if (myStorage[hashIndex] == null) {
            myStorage[hashIndex] = newEntry;
            startFromFront = false;
        }
        else {
            for (int i = hashIndex; i < myStorage.length; i++) {
                if (myStorage[i] == null) {
                    myStorage[i] = newEntry;
                    startFromFront = false;
                    break;
                }
                if (myStorage[i].getKey().equals(k)) {
                    myStorage[i] = newEntry;
                    startFromFront = false;
                    replacement = true;
                    break;
                }
            }
        }
        if (startFromFront) {
            for (int i = 0; i < hashIndex; i++) {
                if (myStorage[i] == null || myStorage[i].getKey().equals(k)) {
                    myStorage[i] = newEntry;
                    break;
                }
            }
        }
        if (!replacement)
            currSize++;

    }

    public V remove(K k) {
        //Remove the given key (and associated value)
        //from the table. Return the value removed.
        //If the value is not in the table, return null.
        V returnedVal = null;
        boolean iterateFromFront = true;
        TableEntry<K,V> tombStone = new TableEntry<>(null, null);
        int keyHashCode = Math.abs(k.hashCode());
        int hashIndex = keyHashCode % getCapacity();

        if(storage[hashIndex].getKey().equals(k)){
            returnedVal = storage[hashIndex].getValue();
            storage[hashIndex] = tombStone;
            return returnedVal;
        }
        else{
            for(int i = hashIndex; i < getCapacity(); i++){
                if(storage[i] == null){
                    return null;
                }
                if(storage[i].equals(k)){
                    returnedVal = storage[i].getValue();
                    iterateFromFront = false;
                    break;
                }
            }
        }
        if(iterateFromFront){
            for(int i = 0; i < hashIndex; i++){
                if(storage[i] == null){
                    return null;
                }
                if(storage[i].equals(k)){
                    returnedVal = storage[i].getValue();
                    break;
                }
            }
        }
        //Hint 1: Remember to leave a tombstone!
        //Hint 2: Does it matter what a tombstone is?
        //   Yes and no... You need to be able to tell
        //   the difference between an empty spot and
        //   a tombstone and you also need to be able
        //   to tell the difference between a "real"
        //   element and a tombstone.

        //Worst case: O(n), Average case: O(1)

        return returnedVal;
    }

    public V get(K k) {
        //Given a key, return the value from the table.
        V returnVal = null;
        int keyHashCode = Math.abs(k.hashCode());
        int hashIndex = keyHashCode % getCapacity();
        int i = 0;
        if(storage[hashIndex] == null)
            return null;
        if (storage[hashIndex].getKey().equals(k))
            return storage[hashIndex].getValue();
            //If the value is not in the table, return null.
        else {
            for (TableEntry<K, V> currEntry : storage) {
                if (currEntry == null) {
                    i++;
                    continue;
                }
                if (currEntry.getKey().equals(k)) {
                    returnVal = storage[i].getValue();
                    break;
                }
                i++;
            }
        }
        //Worst case: O(n), Average case: O(1)
        return returnVal;
    }

    public boolean isTombstone(int loc) {
        //this is a helper method needed for printing

        //return whether or not there is a tombstone at the
        //given index

        //O(1)
        return !(storage[loc].getKey() == null) ;
    }

    @SuppressWarnings("unchecked")
    public boolean rehash(int size) {
        //Increase or decrease the size of the storage,
        //rehashing all values.
        int i = 0;
        TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[size];
        for (TableEntry<K, V> currEntry : storage) {
            if(currEntry == null)
                continue;
            insert(newTable, currEntry.getKey(), currEntry.getValue(), size);
            i++;
        }
        storage = newTable;
        currSize = i;
        //Note: you should start at the beginning of the
        //old table and go to the end (linear traversal)
        //to move items into the new table. If you go
        //backwards, etc. your elements will end up out
        //of order compared to the expected order.

        //If the new size won't fit all the elements,
        //with at least _one_ empty space, return false
        //and do not rehash. Return true if you were
        //able to rehash.

        return true;
    }

    //--------------------------------------------------------
    // testing code goes here... edit this as much as you want!
    //--------------------------------------------------------

    public static void main(String[] args) {
        //main method for testing, edit as much as you want
        ThreeTenHashTable1<String, String> st1 = new ThreeTenHashTable1<>(10);
        ThreeTenHashTable1<String, Integer> st2 = new ThreeTenHashTable1<>(5);

        if (st1.getCapacity() == 10 && st2.getCapacity() == 5 && st1.size() == 0 && st2.size() == 0) {
            System.out.println("Yay 1");
        }

        st1.put("a", "apple");
        st1.put("b", "banana");
        st1.put("banana", "b");
        st1.put("b", "butter");
        System.out.println(st1.toString());
        System.out.println(st1.toStringDebug());

        if (st1.toString().equals("a:apple\nb:butter\nbanana:b") && st1.toStringDebug().equals("[0]: null\n[1]: null\n[2]: null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a:apple\n[8]: b:butter\n[9]: banana:b")) {
            System.out.println("Yay 2");
        }

        if (st1.getCapacity() == 10 && st1.size() == 3 && st1.get("a").equals("apple") && st1.get("b").equals("butter") && st1.get("banana").equals("b")) {
            System.out.println("Yay 3");
        }

        st2.put("a", 1);
        st2.put("b", 2);
        st2.put("e", 3);
        st2.put("y", 4);

        if (st2.toString().equals("e:3\ny:4\na:1\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: e:3\n[2]: y:4\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a:1\n[8]: b:2\n[9]: null")) {
            System.out.println("Yay 4");
        }

        if (st2.getCapacity() == 10 && st2.size() == 4 && st2.get("a").equals(1) && st2.get("b").equals(2) && st2.get("e").equals(3) && st2.get("y").equals(4)) {
            System.out.println("Yay 5");
        }
        System.out.println(st2.remove("e").equals(3));
        System.out.println(st2.getCapacity() == 10);
        System.out.println(st2.size() == 3 );
        System.out.println(st2.get("e") == null);
        System.out.println(st2.get("y").equals(4));

        if (st2.remove("e").equals(3) && st2.getCapacity() == 10 && st2.size() == 3 && st2.get("e") == null && st2.get("y").equals(4)) {
            System.out.println("Yay 6");
        }

//        if (st2.toString().equals("y:4\na:1\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: tombstone\n[2]: y:4\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a:1\n[8]: b:2\n[9]: null")) {
//            System.out.println("Yay 7");
//        }
//
//        if (st2.rehash(2) == false && st2.size() == 3 && st2.getCapacity() == 10) {
//            System.out.println("Yay 8");
//        }
//
//        if (st2.rehash(4) == true && st2.size() == 3 && st2.getCapacity() == 4) {
//            System.out.println("Yay 9");
//        }
//
//        if (st2.toString().equals("y:4\na:1\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: y:4\n[2]: a:1\n[3]: b:2")) {
//            System.out.println("Yay 10");
//        }
//
//        ThreeTenHashTable1<String, String> st3 = new ThreeTenHashTable1<>(2);
//        st3.put("a", "a");
//        st3.remove("a");
//
//        if (st3.toString().equals("") && st3.toStringDebug().equals("[0]: null\n[1]: tombstone")) {
//            st3.put("a", "a");
//            if (st3.toString().equals("a:a") && st3.toStringDebug().equals("[0]: null\n[1]: a:a")) {
//                System.out.println("Yay 11");
//            }
//        }
    }

    //********************************************************************************
    //   DO NOT EDIT ANYTHING BELOW THIS LINE (except to add the JavaDocs)
    //********************************************************************************

    public String toString() {
        //THIS METHOD IS PROVIDED, DO NOT CHANGE IT
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null && !isTombstone(i)) {
                s.append(storage[i]);
                s.append("\n");
            }
        }
        return s.toString().trim();
    }

    public String toStringDebug() {
        //THIS METHOD IS PROVIDED, DO NOT CHANGE IT
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < storage.length; i++) {
            if (!isTombstone(i)) {
                s.append("[" + i + "]: " + storage[i] + "\n");
            } else {
                s.append("[" + i + "]: tombstone\n");
            }

        }
        return s.toString().trim();
    }
}