/**
 * Implement a hash table.
 * Data: Strings
 * Hash function: sfold
 * Collision Resolution: Quadratic probing
 *
 * @author <Your name(s) here
 * @version <Put something here>
 */


public class Hash
{
    private String[] table;
    private boolean[] tombstone;
    private int size;
    /**
     * Create a new Hash object.
     *
     * @param init
     *            Initial size for table
     * @param m
     *            Memory manager used by this table to store objects
     */
    public Hash(int init) // public Hash(int init, MemManager m)
    {
        table = new String[init];
        tombstone = new boolean[init];
        size = 0;
    }
    
    /**
     * Insert a string into the hash table.
     * @return true if inserted, false if duplicate
     */
    public boolean insert(String key) {
        int m = table.length;
        int home = h(key, m);

        for (int i = 0; i < m; i++) {
            int idx = (home + i * i) % m;

            // Empty slot (and not a tombstone)
            if (table[idx] == null) {
                table[idx] = key;
                size++;
                return true;
            }

            // Duplicate
            if (table[idx].equals(key)) {
                return false;
            }
        }
        return false; // Table full
    }

    /**
     * Find a string in the hash table.
     */
    public boolean find(String key) {
        int m = table.length;
        int home = h(key, m);

        for (int i = 0; i < m; i++) {
            int idx = (home + i * i) % m;

            if (table[idx] == null && !tombstone[idx]) {
                return false;
            }
            if (table[idx] != null && table[idx].equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Print the table contents.
     */
    public String print(String label) {
        StringBuilder sb = new StringBuilder();
        int count = 0;

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                sb.append(i + ": |" + table[i] + "|\r\n");
                count++;
            }
        }

        sb.append("total " + label + ": " + count);
        return sb.toString();
    }

    /**
     * Compute the hash function. Uses the "sfold" method from the OpenDSA
     * module on hash functions
     *
     * @param s
     *            The string that we are hashing
     * @param m
     *            The size of the hash table
     * @return The home slot for that string
     */
    public int h(String s, int m) {
        long sum = 0;
        long mult = 1;
        for (int i = 0; i < s.length(); i++) {
            mult = (i % 4 == 0) ? 1 : mult * 256;
            sum += s.charAt(i) * mult;
        }
        return (int)(Math.abs(sum) % m);
    }
}
