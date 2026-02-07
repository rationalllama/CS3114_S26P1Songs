//Class for communication between MemManager and Hash Tables


/**
 * A MemHandle represents a reference to a record stored in the MemManager. * 
 * The hash table stores the actual handle
 * The memory manager stores the offset and length
 * 
 * @author Eva Choberka
 * @version Spring 2026
 */
public class MemHandle implements MemoryHandle{
    private int offset; //the offset of the handle in the hash table (where record begins)
    private int length; //the length of the string (length of record in bytes)
    
    /**
     * Creates a new handle pointing to a record stored in the memory pool.
     *
     * @param offset  the starting position of the record in the pool
     * @param length  the number of bytes in the record
     */
    public MemHandle(int offset, int length) {
        this.offset = offset;
        this.length = length;
    }
    
    
    public int getOffset() {
        return offset;
    }
    
    public int getLength() {
        return length;
    }
    
    /**
     * Marks this handle as tombstone -- record used to be here but was deleted.
     */
    public void makeTombstone() {
        offset = -1;
        length = -1;
    }
    
    /**
     * Checks if this handle is a tombstone (has been marked as deleted).
     */
    public boolean isTombstone() {
        return offset == -1;
    }

    /** 
     * Provides a readable representation of the handle.
     */ 
    @Override 
    public String toString() { 
        if (isTombstone()) { 
            return "TOMBSTONE"; 
        } 
        return "(" + offset + ", " + length + ")"; }
}
