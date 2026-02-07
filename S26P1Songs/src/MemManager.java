
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
        freeList = new FreeBlock(0, startSize); //Points to first free block
    } 
    
    
    public int getSize() {
        return poolSize;
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
      //The previous free block is the last block that was 
        // free and is now allocated. The current block is
        // the next free block we will allocate in this method
        FreeBlock prev = null; 
        FreeBlock curr = freeList;
        
        //Until the current block is null, loop through the list of free
        // blocks
        while(curr != null) {
            if(curr.size >= info.length) {
                int allocIndex = curr.start;
                
                for(int i = 0; i < info.length; i++) {
                    memPool[allocIndex + i] = info[i];
                }
            
            
                //If the given array of bytes is exactly the same size 
                // as the free block, then no splitting of blocks occurs
                if(curr.size == info.length) {
                    //If we allocated the first block, then set the beginning
                    // of the free block list to the next block 
                    // previous block to allocate
                    if(prev == null) {
                        freeList = curr.next;
                    }
                    //If we did not allocate the first block, then ensure 
                    // the previous block does not reference the block we 
                    // allocated that is not longer free
                    else {
                        prev.next = curr.next;
                    }
                }
                //If the block is bigger than the given array of bytes, 
                // then split the block to create another free block 
                else {
                    //the start of the next free block should be the end 
                    // of the current block and the size of the next free
                    // block should be the size of this block minus the 
                    // space allocated
                    curr.start += info.length;
                    curr.size -= info.length;
                }
            
                return new MemHandle(allocIndex, info.length);
            }
            
            prev = curr;
            curr = curr.next;
        }
        
        return null;
    }
    
    /**
     * Release the space associated with a record when no longer
     *  needed and return it to the memory manager
     *  
     *  Implements buddy method when there are adjacent free blocks
     * 
     * @param h
     */
    public void release(MemHandle h) {
        //Create a new free block that will represent the block being freed
        FreeBlock releasedBlock = new FreeBlock(h.getOffset(), h.getLength());
        
        //If there are no free blocks in the freeBlock list, then add this
        // released block to the front of the list
        //Or if there is already a free block adjacent to the one being added
        // then when the free blocks are merged, the start of the merged block 
        // corresponds with the lower start address
        if(freeList == null || releasedBlock.start < freeList.start) {
            releasedBlock.next = freeList;
            freeList = releasedBlock;
        } 
        //
        else {
            FreeBlock curr = freeList;
            while(curr.next != null && curr.next.start < releasedBlock.start) {
                curr = curr.next;
            }
            releasedBlock.next = curr.next; 
            curr.next = releasedBlock;
        }
        
        //If adjacent free blocks, merge them using buddy method
        buddy();
        
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
    
    
    /*
     * 
     */
    public void buddy() {
        FreeBlock curr = freeList;
        
        while(curr != null && curr.next != null) {
            if(curr.start + curr.size == curr.next.start) {
                curr.size += curr.next.size;
                curr.next = curr.next.next;
            }
            else {
                curr = curr.next;
            }
        }
    }
}
