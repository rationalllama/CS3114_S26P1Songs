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
        
        //convert string to array of bytes to insert to MemManager
        byte[] strBytes = key.getBytes();
        handles[pos] = manager.insert(strBytes);
        size++;
        return true;
    }
    
    
    //find method
//    public String search(Key k, Elem e) {
            //use get record from the memory manager to recover the string 
//        return false;
//    }
    
    /**
     * Resizes the hash table to double the size when the
     * current one is half full
     * 
     * @return
     */
    public void resize() {
        //double the capacity of the hash table and create a new hash table of doubled size
        capacity *= 2;
        MemHandle[] newHandles = new MemHandle[this.capacity];
        
        //copy the old hash table over to the new one and set this hash table to the new hash table created
        for(int i = 0; i < capacity/2; i++) {
            newHandles[i] = handles[i];
        }
        handles = newHandles;
        
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
    
    //check for duplication method
    public boolean isDuplicate(MemHandle handle, String key) {
        byte[] data = manager.getRecord(handle);
        String check = data.toString();
        return check.equals(key);
    }
}
