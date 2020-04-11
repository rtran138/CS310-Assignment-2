class ThreeTenHashTable2<K, V> {
    //you must use this storage for the hash table
    //and you may not alter this variable's name, type, etc.
    private Node<K, V>[] storage;
    private int capacity;
    private int currSize;

    /* +++++++++++++++++++ YOUR CODE HERE +++++++++++++++++++ */
    //Add additional instance variables here!
    //You may also add additional _private_ methods if you'd like.

    @SuppressWarnings("unchecked")
    public ThreeTenHashTable2(int size) {
        //Create a hash table where the size of the storage is
        //the provided size (number of "slots" in the table)
        //You may assume size is >= 2
        storage = (Node<K, V>[]) new Node[size];
        capacity = size;
        currSize = 0;
        //Remember... if you want an array of ClassWithGeneric<V>, the following format ___SHOULD NOT___ be used:
        //         ClassWithGeneric<V>[] items = (ClassWithGeneric<V>,[]) Object[10];
        //instead, use this format:
        //         ClassWithGeneric<V>[] items = (ClassWithGeneric<V>,[]) ClassWithGeneric[10];
    }

    public int getCapacity() {
        //return how many "slots" are in the table
        //O(1)
        return capacity;
    }

    public int size() {
        //return the number of elements in the table
        //O(1)
        return storage.length;
    }

    public void put(K k, V v) {
        //Place value v at the location of key k.
        //Use separate chaining if that location is in use.
        //You may assume both k and v will not be null.

        //Hint 1: Make a TableEntry to store in storage
        //and use the absolute value of k.hashCode() for
        //the location of the entry.
        int keyHashCode = Math.abs(k.hashCode());
        int hashIndex = keyHashCode % getCapacity();
        TableEntry<K, V> newEntry = new TableEntry<>(k, v);
        Node<K, V> newNode = new Node<>(newEntry, null);
        Node<K, V> last = storage[hashIndex];
        boolean replace = false;
        //If an item with the same hashIndex is found, it will be added to the end of the LinkedList.
        if (storage[hashIndex] == null) {
            storage[hashIndex] = newNode;
        }
        //If the same key is found, update the value associated with that key.
        else if (storage[hashIndex].entry.getKey().equals(k)) {
            storage[hashIndex].entry = newEntry;
            replace = true;
        }
        //Add something here?
        else if (storage[hashIndex] != null) {
            while (storage[hashIndex].next != null) {
                if (storage[hashIndex].entry.getKey().equals(k)) {
                    storage[hashIndex].entry = newEntry;
                    break;
                }
                if(storage[hashIndex].next == null){
                    storage[hashIndex].next = newNode;
                    break;
                }
                storage[hashIndex] = storage[hashIndex].next;
            }
            replace = true;
        }
        //Append to the end of the linked list if same hash index is used but keys are not are not the same.

        if (!replace)
            currSize++;
    }

    //Hint 2: Remember that you're dealing with an array
    //of linked lists in this part of the project, not
    //an array of table entries.

    //If the key already exists in the table
    //replace the current value with v.

    //If the key does not exist in the table, add
    //the new node to the _end_ of the linked list.

    //If the load on the table is >= 80% _after_ adding,
    //expand the table to twice the size and rehash
    //repeatedly _until_ the load is less than 80%
//        if((float)storage.length /capacity >=0.8)
//
//    {
//        rehash(capacity * 2);
//    }
    //Worst case: O(n), Average case: O(1)


    public V remove(K k) {
        //Remove the given key (and associated value)
        //from the table. Return the value removed.
        //If the value is not in the table, return null.

        //Hint: Remember there are no tombstones for
        //separate chaining! Don't leave empty nodes!

        //Worst case: O(n), Average case: O(1)

        return null;
    }

    public V get(K k) {
        //Given a key, return the value from the table.

        //If the value is not in the table, return null.

        //Worst case: O(n), Average case: O(1)
        int keyHashCode = Math.abs(k.hashCode());
        int hashIndex = keyHashCode % getCapacity();
        Node<K, V> copy = storage[hashIndex];
        V returnValue = null;
        if (storage[hashIndex] == null)
            return null;
        if (storage[hashIndex].entry.getKey().equals(k)) {
            return storage[hashIndex].entry.getValue();
        } else {
            while (copy.next != null) {
                if (copy.entry.getKey().equals(k)) {
                    returnValue = copy.entry.getValue();
                }
                copy = copy.next;
            }
        }
        return returnValue;
    }

    @SuppressWarnings("unchecked")
    public boolean rehash(int size) {
        //Increase or decrease the size of the storage,
        //rehashing all values.

        //Note: you should start at the beginning of the
        //old table and go through each linked list in order
        //(start to end) to move items into the new table.
        //If you go backwards, etc. your elements will end up
        //out of order compared to the expected order.

        //If the new size is less than 1, return false.
        //Return true if you were able to rehash.

        return false;
    }

    //--------------------------------------------------------
    // testing code goes here... edit this as much as you want!
    //--------------------------------------------------------

    public static void main(String[] args) {
        //main method for testing, edit as much as you want
        ThreeTenHashTable2<String, String> st1 = new ThreeTenHashTable2<>(10);
        ThreeTenHashTable2<String, Integer> st2 = new ThreeTenHashTable2<>(5);

        if (st1.getCapacity() == 10 && st2.getCapacity() == 5 && st1.size() == 0 && st2.size() == 0) {
            System.out.println("Yay 1");
        }

        st1.put("a", "apple1");
       // st1.put("a", "apple2");
        //st1.put("b", "banana");
        st1.put("banana", "b");
        //st1.put("b", "butter");

//        if (st1.toString().equals("a:apple\nbanana:b\nb:butter") && st1.toStringDebug().equals("[0]: null\n[1]: null\n[2]: null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: [a:apple]->[banana:b]->null\n[8]: [b:butter]->null\n[9]: null")) {
//            System.out.println("Yay 2");
//        }
//
//        if (st1.getCapacity() == 10 && st1.size() == 3 && st1.get("a").equals("apple") && st1.get("b").equals("butter") && st1.get("banana").equals("b")) {
//            System.out.println("Yay 3");
//        }
//
//        st2.put("a", 1);
//        st2.put("b", 2);
//        st2.put("e", 3);
//        st2.put("y", 4);
//
//        if (st2.toString().equals("e:3\ny:4\na:1\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: [e:3]->[y:4]->null\n[2]: null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: [a:1]->null\n[8]: [b:2]->null\n[9]: null")) {
//            System.out.println("Yay 4");
//        }
//
//        if (st2.getCapacity() == 10 && st2.size() == 4 && st2.get("a").equals(1) && st2.get("b").equals(2) && st2.get("e").equals(3) && st2.get("y").equals(4)) {
//            System.out.println("Yay 5");
//        }
//
//        if (st2.remove("e").equals(3) && st2.getCapacity() == 10 && st2.size() == 3 && st2.get("e") == null && st2.get("y").equals(4)) {
//            System.out.println("Yay 6");
//        }
//
//        if (st2.toString().equals("y:4\na:1\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: [y:4]->null\n[2]: null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: [a:1]->null\n[8]: [b:2]->null\n[9]: null")) {
//            System.out.println("Yay 7");
//        }
//
//        if (st2.rehash(0) == false && st2.size() == 3 && st2.getCapacity() == 10) {
//            System.out.println("Yay 8");
//        }
//
//        if (st2.rehash(4) == true && st2.size() == 3 && st2.getCapacity() == 4) {
//            System.out.println("Yay 9");
//        }
//
//        if (st2.toString().equals("y:4\na:1\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: [y:4]->[a:1]->null\n[2]: [b:2]->null\n[3]: null")) {
//            System.out.println("Yay 10");
//        }
//
//        ThreeTenHashTable2<String, String> st3 = new ThreeTenHashTable2<>(2);
//        st3.put("a", "a");
//        st3.remove("a");
//
//        if (st3.toString().equals("") && st3.toStringDebug().equals("[0]: null\n[1]: null")) {
//            st3.put("a", "a");
//            if (st3.toString().equals("a:a") && st3.toStringDebug().equals("[0]: null\n[1]: [a:a]->null")) {
//                System.out.println("Yay 11");
//            }
//        }
    }

//********************************************************************************
//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to add the JavaDocs)
//********************************************************************************

    public static class Node<K, V> {
        public TableEntry<K, V> entry;
        public Node<K, V> next;

        public Node(TableEntry<K, V> entry) {
            this.entry = entry;
        }

        public Node(TableEntry<K, V> entry, Node<K, V> next) {
            this(entry);
            this.next = next;
        }

        public String toString() {
            return "[" + entry.toString() + "]->";
        }

    }

    public String toString() {
        //THIS METHOD IS PROVIDED, DO NOT CHANGE IT
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < storage.length; i++) {
            Node<K, V> curr = storage[i];
            if (curr == null) continue;

            while (curr != null) {
                s.append(curr.entry.toString());
                s.append("\n");
                curr = curr.next;
            }
        }
        return s.toString().trim();
    }

    public String toStringDebug() {
        //THIS METHOD IS PROVIDED, DO NOT CHANGE IT
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < storage.length; i++) {
            Node<K, V> curr = storage[i];

            s.append("[" + i + "]: ");
            while (curr != null) {
                s.append(curr.toString());
                curr = curr.next;
            }
            s.append("null\n");
        }
        return s.toString().trim();
    }
}