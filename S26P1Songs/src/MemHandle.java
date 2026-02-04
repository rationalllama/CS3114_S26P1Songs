//Class for communication between MemManager and Hash Tables


/**
 * 
 * 
 * The hash table stores the actual handle
 * The memory manager stores the offset and length
 * 
 * @author Eva Choberka
 * @version Spring 2026
 */
public class MemHandle implements MemoryHandle{
    private int offset; //the offset of the handle in the hash table
    private int length; //the length of the string
    
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
    
    public void makeTombstone() {
        offset = -1;
        length = 0;
    }
    
    public boolean isTombstone() {
        if(offset == -1) {
            return true;
        }
        return false;
    }
}
