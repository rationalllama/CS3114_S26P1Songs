import java.util.LinkedList;

/**
 * Implement a hash table.
 * This class is a hash table of MemHandles, which point to strings 
 * stored in the MemManager. The hash table itself never stores 
 * the actual strings.
 * Data: Strings
 * Hash function: sfold
 * 
 * 
 * 
 * Collision Resolution: Quadratic probing
 *
 * @author Eva Choberka
 * @version Spring 2026
 */


public class Hash
{
    /** Array of handles stored in the hash table. */
    private MemHandle[] handles;

    /** Current capacity of the table (always a power of 2). 
     * If m is not a power of 2, quadratic probing can get 
     * stuck in a loop and never check the entire table.
    */
    private int capacity;

    /** Memory manager that stores the actual string bytes. */
    private MemManager manager;

    /** Number of active (non-tombstone) elements stored. */
    private int size;


    
    /**
     * Create a new Hash object.
     *
     * @param init
     *            Initial size for table
     * @param m
     *            Memory manager used by this table to store objects
     */
    public Hash(int init, MemManager m)
    {
        // Put stuff here
        capacity = init;
        manager = m;
        size = 0;
        handles = new MemHandle[capacity];
    }

    
    
    /**
     * Compute the hash function. Uses the "sfold" method from the OpenDSA
     * module on hash functions
     * Breaks the string into 4-byte chunks and folds them into a long.
     *
     * @param s
     *            The string that we are hashing
     * @param m
     *            The size of the hash table
     * @return The home slot for that string
     */
    public int h(String s, int m) {
        long sum = 0;
        long mult = 1;
        for (int i = 0; i < s.length(); i++) {
            mult = (i % 4 == 0) ? 1 : mult * 256;
            sum += s.charAt(i) * mult;
        }
        return (int)(Math.abs(sum) % m);
    }
    
    
    
    /**
     * Insert a string into the hash table using the collision 
     * resolution method with quadratic probing
     * 
     * @param s
     *              The string we are inserting
     * @param m
     *              The size of the hash table
     * @return true if insertion is successful and false if otherwise
     */
    public boolean insert(String key) throws IllegalArgumentException{
        //If the string to add is null, then throw an illegal argument exception
        if(key == null) {
            throw new IllegalArgumentException("Input strings cannot be null or empty");
        }
        
        // If the hash table is half full, then before another insertion,
        // resize the table first
        // must be done before insertion because if we insert first, 
        // then we will be over capacity and won't be able to resize
        if(size + 1 > capacity/2) {
            resize();
        }
        
        
        //determine the index for placement based on the given string to add
        int home = h(key, capacity); 
        int pos = home;
        int offset = 1;
        int firstTombstone = -1;
        
        // this slot is occupied, so we need to check for duplicates and find the next open slot using quadratic probing
        while(handles[pos] != null) {
            // if we come across a tombstone, we want to save that index for possible insertion
            if(handles[pos].isTombstone()) {
                firstTombstone = pos;
            }
            else if(isDuplicate(handles[pos],key)) { 
                return false;
            }
            // quadratic probing
            pos = (home + offset*offset) % capacity;
            offset++;
        }
        
        // Use the tombstone if one was found; otherwise use the first empty slot.
        int insertPos = (firstTombstone != -1) ? firstTombstone : pos;

        // Convert string to bytes and store in memory manager
        byte[] bytes = key.getBytes();
        handles[insertPos] = manager.insert(bytes);

        size++;
        return true;
    }
    
    
    //find method
//    public String search(Key k, Elem e) {
            //use get record from the memory manager to recover the string 
//        return false;
//    }
    
    /**
     * Resizes the hash table by doubling its capacity.
     * All non-tombstone elements are reinserted into the new table.
     * Required because quadratic probing depends on table size.
     * 
     * Resizing at half-full keeps performance predictable, prevents
     * infinite loops during probing.
     */
    private void resize() {
        int oldCapacity = capacity;
        capacity *= 2;

        MemHandle[] oldHandles = handles;
        handles = new MemHandle[capacity];
        size = 0; // reinserting will rebuild size

        // Reinsert all valid records
        for (int i = 0; i < oldCapacity; i++) {
            // Only reinsert if it's not null and not a tombstone
            if (oldHandles[i] != null && !oldHandles[i].isTombstone()) {
                byte[] data = manager.getRecord(oldHandles[i]);
                String s = new String(data);
                insert(s);
            }
        }
    }

    
    //print method
    
    //delete method - will use tombstones
    /**
     * @param key
     *          The string we are looking to remove from the hash table
     * 
     * @return true if successful deletion and false otherwise
     */
    public boolean delete(String key) {
        //search for the "key" or string to delete
            //continue searching until null is found cuz could come across tombstone
        
        //if not found --> return false
        //if found --> create tombstone over the key that was once there and return true
        return false;
    }
    
    /**
     * Checks whether a handle corresponds to the given key.
     *
     * @param handle the handle to compare
     * @param key    the string to compare against
     * @return true if they match
     */
    private boolean isDuplicate(MemHandle handle, String key) {
        byte[] data = manager.getRecord(handle);
        String check = new String(data); // correct byte-to-string conversion
        return check.equals(key);
    }
}
