

/**
 * Represents blocks of free space in the memory pool
 */
public class FreeBlock {
    int start; //The start of the allocated block
    int size; //The size of the allocated block
    FreeBlock next; //The next free block in the list of blocks
    
    /**
     * When creating a free block, provide the starting index
     * of the block and the size of it
     * 
     * @param start
     *          The starting index of the free block in the list
     *          
     * @param size
     *          The size of the free block
     */
    public FreeBlock(int start, int size) {
        this.start = start;
        this.size = size;
        next = null;
    }
}
