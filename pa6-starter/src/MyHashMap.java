import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyHashMap<K, V> implements DefaultMap<K, V> {
	public static final double DEFAULT_LOAD_FACTOR = 0.75;
	public static final int DEFAULT_INITIAL_CAPACITY = 16;
	public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";
	public static final String ILLEGAL_ARG_LOAD_FACTOR = "Load Factor must be positive";
	public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";
	
	private double loadFactor;
	private int capacity;
	private int size;

	// Use this instance variable for Separate Chaining conflict resolution
	private List<HashMapEntry<K, V>>[] buckets;  
	
	// Use this instance variable for Linear Probing
	//private HashMapEntry<K, V>[] entries; 	

	public MyHashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
	}
	
	/**
	 * 
	 * @param initialCapacity the initial capacity of this MyHashMap
	 * @param loadFactor the load factor for rehashing this MyHashMap
	 * @throws IllegalArgumentException if initialCapacity is negative or loadFactor not
	 * positive
	 */
	public MyHashMap(int initialCapacity, double loadFactor) throws IllegalArgumentException {
		// TODO Finish initializing instance fields
		
		// If initialCapacity is negative, throws IllegalArgumentException
		if( initialCapacity < 0 )
		{
			throw new IllegalArgumentException( ILLEGAL_ARG_CAPACITY );
		}else 
		{
			this.capacity = initialCapacity;
		}
		// If loadFactor is not positive, throws IllegalArgumentException.
		if( loadFactor <= 0 )
		{
			throw new IllegalArgumentException( ILLEGAL_ARG_LOAD_FACTOR );
		}else 
		{
			this.loadFactor = loadFactor;
		}
		this.size = 0;
		
		// if you use Separate Chaining
		buckets = (List<HashMapEntry<K, V>>[]) new List<?>[capacity];

		// if you use Linear Probing
		//entries = (HashMapEntry<K, V>[]) new HashMapEntry<?, ?>[initialCapacity];
	}
	// expandCapacity() double the size of the List, and put all the elements
	// into the new List. 
	void expandCapacity() {
		
		int sizeArray = this.capacity;
		// create new list with the doubled size
		List<HashMapEntry<K, V>>[] newBuckets = (List<HashMapEntry<K, V>>[]) new List<?>[ capacity *2 ];
		this.capacity = this.capacity * 2;
		List<MyHashMap.HashMapEntry<K, V>>[] oldBuckets = buckets;
		buckets = newBuckets;
		this.size = 0;
		K key = null;
		V value = null;
		
		// add the original elements into the new List 
		for( int i = 0; i < sizeArray; i++) 
		{
			if( oldBuckets[ i ] != null) 
			{
				int sizeSC = oldBuckets[ i ].size();
				for ( int j = 0; j < sizeSC; j++) 
				{
					key = oldBuckets[ i ].get( j ).key;
					value = oldBuckets[ i ].get( j ).value;
					int index = getIndex(key);
					
					HashMapEntry<K,V> element = new HashMapEntry<K,V>( key, value );
					if( buckets[ index ] == null )
					{
						buckets[ index ] = new ArrayList<>();
					}
					this.size += 1;
					buckets[ index ].add( element );
					
				}
			}
		}
	}
	@Override
	public boolean put(K key, V value) throws IllegalArgumentException {
		// If the key == null, throw exception
		if( key == null )
		{
			throw new IllegalArgumentException( ILLEGAL_ARG_NULL_KEY );
		}
		// get the current load factor
		double currentLoadFactor =( ( double )this.size )/ this.capacity;
		
		// if the current load factor is larger than 0.75, call expandcapacity()
		if( currentLoadFactor > this.loadFactor) 
		{
			expandCapacity();
		}
		int index = getIndex(key);
		HashMapEntry<K,V> element = new HashMapEntry<K,V>( key, value );
		if( buckets[ index ] == null )
		{
			buckets[ index ] = new ArrayList<>();
		}
		// if bucket contains key, return false
		if ( containsKey( key ) ) {
			return false;
		}else
		{
			// If bucket does not contain key, add the entry into the bucket
			this.size += 1;
			return buckets[ index ].add( element );
		}
	}

	@Override
	public boolean replace(K key, V newValue) throws IllegalArgumentException {
		if ( key == null ) 
		{
			throw new IllegalArgumentException( ILLEGAL_ARG_NULL_KEY );
		}
		int index = getIndex(key);
		int size = buckets[ index ].size();
		
		// find the current Entry
		for( int i = 0; i < size; i++ ) {
			HashMapEntry< K, V> current = this.buckets[ index ].get( i );
			K currentKey = current.getKey();
			if( currentKey.equals( key ) ) {
				// set the new value
				current.setValue( newValue );
			}
		}
		// if contains key, return true
		return containsKey( key );
	}

	@Override
	public boolean remove(K key) throws IllegalArgumentException {
		if ( key == null ) 
		{
			throw new IllegalArgumentException( ILLEGAL_ARG_NULL_KEY );
		}
		int index = getIndex(key);
		int size = buckets[ index ].size();
		// if bucket contains key, return true and remove it
		boolean result = containsKey( key );
		for( int i = 0; i < size; i++ ) {
			if( buckets [ index ].get( i ).getKey().equals( key ) ) 
			{
				buckets [index].remove(i);
				this.size -= 1;
			}
		}
		return result;
	}

	@Override
	public void set(K key, V value) throws IllegalArgumentException {
		if ( key == null ) 
		{
			throw new IllegalArgumentException( ILLEGAL_ARG_NULL_KEY );
		}
		double currentLoadFactor = ( double )this.size / this.capacity;
		if( currentLoadFactor > this.loadFactor) 
		{
			expandCapacity();
		}else 
		{
			// if contains the key, update the value
			if ( containsKey( key )) 
			{
				replace( key, value);
			}else
			{
				// if does not contains the key, add the new entry
				int index = getIndex(key);
				HashMapEntry<K,V> element = new HashMapEntry<K,V>( key, value );
				if( buckets[ index ] == null )
				{
					buckets[ index ] = new ArrayList<>();
				}
				this.size += 1;
				buckets[ index ].add( element );
			}
		}
	}

	@Override
	public V get(K key) throws IllegalArgumentException {
		if ( key == null ) 
		{
			throw new IllegalArgumentException( ILLEGAL_ARG_NULL_KEY );
		}
		int index = getIndex(key);
		if ( containsKey ( key )) {
			int size = buckets[ index ].size();
			for( int i = 0; i < size; i++ ) {
				HashMapEntry< K, V> current = this.buckets[ index ].get( i );
				K currentKey = current.getKey();
				if( currentKey.equals( key ) ) {
					// if the entry contains the key, return value
					return current.getValue();
				}
			}
		}
		// if the entry does not contain the key, return null
		return null;
	}

	@Override
	public int size() {
		
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		if( size() == 0 ) 
		{
			return true;
		}else 
		{
			return false;
		}
	}

	@Override
	public boolean containsKey(K key) throws IllegalArgumentException {
		if( key == null )
		{
			throw new IllegalArgumentException( ILLEGAL_ARG_NULL_KEY );
		}
		
		int index = getIndex(key);
		
		// if the arraylist is null, the key is not exist, return false
		if ( buckets[ index ] == null) {
			return false;
		}
		
		int size = buckets[ index ].size();
		for( int i = 0; i < size; i++ ) {
			HashMapEntry< K, V> current = this.buckets[ index ].get( i );
			K currentKey = current.getKey();
			if( currentKey.equals( key ) ) {
				return true;
			}
		}
	
		return false;
		
	}
	@Override
	public List<K> keys() {
		ArrayList<K> list = new ArrayList<>();
		K key = null;
		for( int i = 0; i < this.capacity; i++) 
		{
			if( buckets[ i ] != null) 
			{
				int sizeSC = buckets[ i ].size();
				for ( int j = 0; j < sizeSC; j++) 
				{
					HashMapEntry<K, V> current = buckets[ i ].get( j );
					key = current.getKey();
					list.add( key );
				}
			}
		}
		return list;
	}
	
	// This helper method is to get the index of the key in the List
	public int getIndex(K key) {
		int index = 0;
		int keyHash = Objects.hashCode( key );
		
		// In Ecllipse, the keyHash can be negative
		if ( keyHash < 0) 
		{
			index = keyHash % capacity;
			
			// if index < 0, add the capacity
			if( index < 0)
			{
				index = index + capacity;
			}
		}else 
		{
			index = keyHash % capacity;
		}
		return index;
	}
	
	private static class HashMapEntry<K, V> implements DefaultMap.Entry<K, V> {
	
		K key;
		V value;
		
		private HashMapEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}
		
		@Override
		public void setValue(V value) {
			this.value = value;
		}
	}
}
