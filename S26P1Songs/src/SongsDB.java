import java.io.IOException;

/**
 * The database implementation for this project.
 * We have two hash tables and a memory manager.
 *
 * @author <Your name(s) here
 * @version <Put something here>
 */
public class SongsDB implements Songs
{
    private boolean initialized;
    private int hashSize;
    private int memSize;
    
    private Hash artistTable;
    private Hash songTable;


    // ----------------------------------------------------------
    /**
     * Create a new SongsDB object.
     * But don't set anything -- that gets done by "create"
     */
    public SongsDB()
    {
        initialized = false;
    }


    /**
     * Create a brave new World.
     *
     * @param inHash
     *            Initial size for hash tables
     * @param inMemMan
     *            Initial size for the memory manager
     * @return Error messages if appropriate
     */
    public String create(int inHash, int inMemMan)
    {
        if (inHash <= 0) {
            return "Initial hash table size must be positive";
        }
        
        if (inMemMan <= 0) {
            return "Initial memory manager size must be positive";
        }
        
        if ((inMemMan & (inMemMan - 1)) != 0) { 
            return "Initial memory manager size must be a power of 2"; 
        }
        
        // valid initialization
        initialized = true;
        hashSize = inHash;
        memSize = inMemMan;
        
        artistTable = new Hash(hashSize);
        songTable = new Hash(hashSize);
        
        return "";
    }


    /**
     * Re-initialize the database
     * @return true on successful clear of database
     */
    public boolean clear() {
        if (!initialized) {
            return false;
        }
        return true;
    }


    // ----------------------------------------------------------
    /**
     * Insert to the hash table
     *
     * @param artistString
     *            Artist string to insert
     * @param songString
     *            Song string to insert
     * @return Error message if appropriate
     * @throws IOException
     */
    public String insert(String artistString, String songString)
        throws IOException
    {
        if (!initialized) {
            return "Database not initialized";
        }
        
        if (artistString == null || songString == null ||
            artistString.isEmpty() || songString.isEmpty()) {
            return "Input strings cannot be null or empty";
        }

        boolean a = artistTable.insert(artistString);
        boolean s = songTable.insert(songString);

        // Duplicate
        if (!a) {
            return "|" + artistString +
                   "| duplicates a record already in the Artist database";
        }
        if (!s) {
            return "|" + songString +
                   "| duplicates a record already in the Song database";
        }

        return "";
    }


    // ----------------------------------------------------------
    /**
     * Remove from the hash table
     *
     * @param type
     *            The table to be removed
     * @param nameString
     *            The string to be removed from the table
     * @return Error message if appropriate
     * @throws IOException
     */
    public String remove(String type, String nameString) throws IOException {
        if (!initialized) {
            return "Database not initialized";
        }

        if (type == null || nameString == null ||
            type.isEmpty() || nameString.isEmpty()) {
            return "Input strings cannot be null or empty";
        }

        if (!type.equalsIgnoreCase("artist") &&
            !type.equalsIgnoreCase("song")) {
            return "Bad type value |" + type + "| on remove";
        }

        if (type.equalsIgnoreCase("artist")) {
            return "|" + nameString + "| does not exist in the Artist database";
        }
        else {
            return "|" + nameString + "| does not exist in the song database";
        }
        // return "";
    }


    // ----------------------------------------------------------
    /**
     * Print out the hash table contents
     *
     * @param type
     *            Controls what object is being printed
     * @return The string that was printed
     * @throws IOException
     */
    public String print(String type)
        throws IOException {
        if (!initialized) {
            return "Database not initialized";
        }
        
        if (type == null || type.isEmpty()) {
            return "Input strings cannot be null or empty";
        }
        
        switch(type.toLowerCase()) {
            case "artist":
                return artistTable.print("artists");
            case "song":
                return songTable.print("songs");
            case "blocks":
                return "No free blocks are available.";
            default:
                return "Bad print parameter";
        }
    }
}
