//Memory Manager abstract class

/**
 * 
 * 
 * @author Eva Choberka
 * @version Spring 2026
 */
public interface MemoryManager {
    //Store a record and return a handle to it
    public MemHandle insert(byte[] info);
    
    //Release the space associated with a record
    public void release(MemHandle h);
    
    //Get back a copy of a stored record
    public byte[] getRecord(MemHandle h);
    
    public void resize();
}
