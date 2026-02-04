
/**
 * Interface for MemHandle
 */
public interface MemoryHandle {
    public int getOffset();
    
    public int getLength();
    
    public void makeTombstone();
    
    public boolean isTombstone();
}
