import java.util.LinkedList;

/**
 * Implement a hash table.
 * This class is a hash table of handles that hold strings
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
    private MemHandle[] handles; //the table of nodes that reference the stored strings
    private int capacity; //Size of table
    private MemManager manager; //
    private int size; //Number of handles stored
    
    
    
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

    public int getSize() {
        return size;
    }
    
    /**
     * Compute the hash function. Uses the "sfold" method from the OpenDSA
     * module on hash functions
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
     * @throws IllegalArgumentException
     */
    public boolean insert(String key) throws IllegalArgumentException{
        //If the string to add is null, then throw an illegal argument exception
        if(key == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        
        //If the hash table is half full, then before another insertion,
        //resize the table first
        if(size + 1 > capacity/2) {
            resize();
        }
        
        
        //determine the index for placement based on the given string to add
        int home = h(key, capacity); 
        int pos = home;
        int offset = 1;
        int firstTombstone = -1;
        
        
        while(handles[pos] != null) { 
            if(handles[pos].isTombstone() && firstTombstone == -1) {
                firstTombstone = pos;
            }
            else if(isDuplicate(handles[pos],key)) { 
                return false;
            }
            pos = (home + offset*offset) % capacity;
            offset++;
        }
        
        if(firstTombstone != -1) {
            pos = firstTombstone;
        }
        
        //convert string to array of bytes to insert to MemManager
        byte[] strBytes = key.getBytes();
        handles[pos] = manager.insert(strBytes);
        size++;
        return true;
    }
    
    
    /**
     * 
     * @param handle
     *          The handle in which the string we are looking for is in
     * @return
     *          The string that corresponds to the given handle
     * @throws IllegalArgumentException
     */
    public String getString(MemHandle handle) throws IllegalArgumentException{
            //use get record from the memory manager to recover the string 
        if(handle == null) {
            throw new IllegalArgumentException("Data can't be null"); 
        }
        byte[] data = manager.getRecord(handle);
        String str = new String(data);
        
        return str;
    }
    
    /**
     * Resizes the hash table to double the size when the
     * current one is half full
     * 
     * @return
     */
    public void resize() {
        //double the capacity of the hash table and create a new hash table of doubled size
        int oldCapacity = capacity;
        capacity *= 2;
        MemHandle[] oldHandles = handles;
        handles = new MemHandle[capacity];
        size = 0;
        
        //copy the old hash table over to the new one and set this hash table to the new hash table created
        for(int i = 0; i < oldCapacity; i++) {
            if(oldHandles[i] != null && !oldHandles[i].isTombstone()) {
                byte[] data = manager.getRecord(oldHandles[i]);
                String s = new String(data);
                insert(s);
            }
        }
        
    } 
    
    /**
     * Search for the handle associated with a specified string. Search follows the same
     * ideology as insert, look through the handles in the memory manager until a
     * duplicate is found. If no duplicate is found, then the key does not exist
     * 
     * @param key
     * @return
     */
    public MemHandle search(String key) {
        //Find the initial home position of where the string corresponds to 
        int pos = h(key, capacity);
        int offset = 1;
        
        //Loop through the handles until the corresponding handle is found
        while(handles[pos] != null) {
            
            if(isDuplicate(handles[pos], key)) {
                return handles[pos];
            }
            
            //Follow the collision resolution method to determine the next index to look at
            pos = (pos + offset*offset) % capacity;
            offset++;
        }
        
        //If no duplicate is found in the handles, then the key does not exist
        return null;
    }
    
    //print method
    
    //delete method - will use tombstones
    /**
     * @param key
     *          The string we are looking to remove from the hash table
     * 
     * @return true if successful deletion and false otherwise
     */
    public boolean delete(String key) throws IllegalArgumentException{
        //search for the "key" or string to delete
            //continue searching until null is found cuz could come across tombstone
            //after deleting the handle from the HT, then release the memory in
            //the manager as well
        //Search the memory manager to determine if a handle corresponds to the given key
        MemHandle thisHandle = search(key);
        
        //If thisHandle is null, the handle did not exist and there is nothing to return
        if(thisHandle == null) {
            return false;
        }
        
        //if found --> release the space in the memory pool associated with this handle
        // and create tombstone over the key that was once there and return true
        thisHandle.makeTombstone();
        manager.release(thisHandle);
        
        return true;
    }
    
    //check for duplication method
    public boolean isDuplicate(MemHandle handle, String key) {
        byte[] data = manager.getRecord(handle);
        String check = new String(data);
        return check.equals(key);
    }
}
