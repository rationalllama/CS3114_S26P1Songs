
/**
 * Memory Manager class.
 * This version uses an array in memory.
 * This version implements the buddy method.
 *
 * @author Spring 2026
 */

public class MemManager implements MemoryManager{
    
    private int poolSize;
    private byte[] memPool; //need to allocate memory for this array and dk if its bytes, it can be any data type
    private int freePos;
    
     /**
     * Create a new MemManager object.
     *
     * @param startSize
     *            Initial size of the memory pool
     */
    public MemManager(int startSize) {
        // Put stuff here
//        if() { //make sure startSize is a power of 2
//            
//        }
        poolSize = 0; 
        memPool = new byte[startSize];
        freePos = 0;
    }
    
    
    
  /**
   * Store a record and return a handle to it
   * 
   * @param info
   *            An array of bytes that represents a string
   * @return
   */
    public MemHandle insert(byte[] info) {
        if(freePos + info.length > poolSize) {
            resize();
        }
        
        int offset = freePos;
        for(int i = 0; i < info.length; i++) {
            memPool[freePos + i] = info[i];
        }
        freePos += info.length;
        poolSize++;
        
        return new MemHandle(offset, info.length);
    }
    
    /**
     * Release the space associated with a record
     * 
     * @param h
     */
    public void release(MemHandle h) {
        //implement this
    }
    
    /**
     * Get back a copy of a stored record
     * 
     * @param h
     *          The handle that is requesting its string
     * @return
     */
    public byte[] getRecord(MemHandle h) {
        int offset = h.getOffset();
        byte[] copy = new byte[h.getLength()];
        for(int i = 0; i < h.getLength(); i++) {
            copy[i] = memPool[offset + i];
        }
        return memPool;
    }
    
    /**
     * If the memory pool is full, double the size of the array
     * 
     */
    public void resize() {
        
    }
}
