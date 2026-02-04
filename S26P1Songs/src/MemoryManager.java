//Memory Manager abstract class

/**
 * 
 * 
 * @author Eva Choberka
 * @version Spring 2026
 */
public interface MemoryManager {
    //Store a record and return a handle to it
    // ----------------------------------------------------------
    /**
     * Inserts an array of bytes that represent a string into the memory pool
     * 
     * @param info
     *          The byte version of a string to be inserted
     * @return
     */
    public MemHandle insert(byte[] info);
    
    // ----------------------------------------------------------
    /**
     * Releases the memory associated with a record in the memory pool
     * 
     * @param h
     *          The handle in which the memory will be released for
     */
    public void release(MemHandle h);
    
    // ----------------------------------------------------------
    /**
     * Recovers a specified handle from the list of memory
     * 
     * @param h
     *          The handle to be recovered from the array of bytes
     * @return the array of bytes that correspond with the specified handle
     */
    public byte[] getRecord(MemHandle h);
    
    // ----------------------------------------------------------
    /**
     * Resizes the memory pool when it is full
     */
    public void resize();
}
