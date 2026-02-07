import student.TestCase;

/**
 * 
 * 
 * @author Eva Choberka
 * @version Spring 2026
 */


public class HashTest extends TestCase{
    private Hash test1;
    private MemManager m;
    
    public void setUp() {
        
    }
    
//    public void testBadInput() throws Exception {
//        m = new MemManager(36);
//        test1 = new Hash(10, m);
//        try {
//            test1.insert(null);
//        } catch (IllegalArgumentException e) {
//            System.out.println("Invalid argument: null insertions not accepted");
//        }
//    }
    
    /**
     * Accounts for testing the insert method, resize method, and the
     * isDuplicate method
     */
    public void testInsert() {
        m = new MemManager(36);
        test1 = new Hash(4, m);
        assertTrue(test1.insert("Ariana Grande"));
        assertFalse(test1.insert("Ariana Grande"));
        assertTrue(test1.insert("Laufey"));
        try {
            test1.insert(null);
        } catch (IllegalArgumentException e){
            assertEquals("Data can't be null", e.getMessage());
        }
        assertTrue(test1.insert("Etta James"));
    }
    
    /**
     * Accounts for testing the search method
     */
    public void testSearch() {
        
    }
    
    /**
     * Accounts for testing the delete method
     */
    public void testDelete() {
        
    }
}
