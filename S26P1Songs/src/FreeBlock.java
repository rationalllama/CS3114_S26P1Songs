

/**
 * Represents blocks of free space in the memory pool
 */
public class FreeBlock {
    int start;
    int size;
    
    public FreeBlock(int start, int size) {
        this.start = start;
        this.size = size;
    }
}
