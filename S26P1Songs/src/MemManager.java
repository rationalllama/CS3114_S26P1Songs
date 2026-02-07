
/**
 * Memory Manager class.
 * This version uses an array in memory.
 * This version implements the buddy method.
 * 
 * Job of class is to find a contiguous block of locations of at least 
 *  the requested size from somewhere within memory pool
 *
 * @author Spring 2026
 */

public class MemManager implements MemoryManager{
    
    private int poolSize;
    private byte[] memPool; //A large block of contiguous memory locations
    private FreeBlock freeList; //The free blocks linked together
    private int freePos;
    
     /**
     * Create a new MemManager object.
     *
     * @param startSize
     *            Initial size of the memory pool
     */
    public MemManager(int startSize) {
        poolSize = startSize; 
        memPool = new byte[startSize];
        freePos = 0;
        freeList = new FreeBlock(0, startSize);
    } 
    
    
    public int getSize() {
        return poolSize;
    }
    
    public int getNumFreeBlocks() {
        
    }
    
    
    
  /**
   * Store a record and return a handle to it
   * 
   * Search for a free block large enough to handle the insert
   *  request using the sequential fit method
   * 
   * @param info
   *            An array of bytes that represents a string
   * @return
   */
    public MemHandle insert(byte[] info) {
        FreeBlock prev = null;
        FreeBlock curr = freeList;
        
        while(curr != null) {
            if(curr.size >= info.length) {
                
            }
        }
        
        if(freePos + info.length > poolSize) {
            resize();
        }
        
        int offset = freePos;
        for(int i = 0; i < info.length; i++) {
            memPool[freePos + i] = info[i];
        }
        freePos += info.length;
        //poolSize++;
        
        return new MemHandle(offset, info.length);
    }
    
    /**
     * Release the space associated with a record when no longer
     *  needed and return it to the memory manager
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
        return copy;
    }
    
    /**
     * If the memory pool is full, double the size of the array
     * NEED TO FIX HOW THIS IS IMPLEMENTED!!!!!
     */
    public void resize() {
        int oldSize = poolSize;
        poolSize *= 2;
        byte[] newPool = new byte[poolSize];
        for(int i = 0; i < oldSize; i++) {
            newPool[i] = memPool[i];
        }   
        memPool = newPool;        
    }
}
