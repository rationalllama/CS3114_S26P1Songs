
/**
 * Interface for the Hash Class
 *
 * @author Eva Choberka
 * @version Spring 2026
 *
 */

public interface HashTable {
    
    public int h(String s);
    
    public boolean insert(String s);
    
    public int getSize();
    
    public void resize();
    
    public boolean isDuplicate(MemHandle handle, String s);
    
}
