//********************************************************************************
//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to add the JavaDocs)
//********************************************************************************

class TableEntry<K,V> {
	private K key;
	private V value;
	
	public TableEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public K getKey() {
		return key;
	}
	
	public V getValue() {
		return value;
	}
	
	public String toString() {
		return key.toString()+":"+value.toString();
	}
}