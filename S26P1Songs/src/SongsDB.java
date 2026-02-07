import java.io.IOException;

/**
 * The database implementation for this project.
 * We have two hash tables and a memory manager.
 *
 * @author Eva Choberka
 * @version Spring 2026
 */
public class SongsDB implements Songs
{

    private MemManager manager; //Manager that allocates memory for the HT's
    private Hash artists; //Hash table of artists
    private Hash songs; //Hash table of songs
    private int initManagerSize; //The initial size of the manager - for reinitialize
    private int initHashSize; //The initial size of hash tables - for reinitialize
    
    
    // ----------------------------------------------------------
    /**
     * Create a new SongsDB object.
     * But don't set anything -- that gets done by "create"
     */
    public SongsDB()
    {
        
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
        if(inHash <= 0) {
            return "Initial hash table size must be positive";
        }
        else if(inMemMan <= 0) {
            return "Initial memory manager size must be positive";
        }
        else if(inMemMan%2 != 0) {
            return "Initial memory manager size must be a power of 2";
        }
        
        initManagerSize = inMemMan;
        initHashSize = inHash;
        
        manager = new MemManager(inMemMan);
        artists = new Hash(initHashSize/2, manager);
        songs = new Hash(initManagerSize/2, manager);
        return ""; 
    }


    /**
     * Re-initialize the database
     * @return true on successful clear of database
     */
    public boolean clear() {
        if(manager == null) {
            return false;
        }
        
        manager = new MemManager(initManagerSize);
        artists = new Hash(initHashSize/2, manager);
        songs = new Hash(initHashSize, manager);
        
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
        //If the object itself is not initialized, there is
        // no tables to add objects to, and therefore causes
        // an exception
        if(manager == null) {
            return "Database not initialized";
        }
        
        //If the strings are null or empty, they can't be added
        if(artistString == null || songString == null) {
            return "Input strings cannot be null or empty";
        }
        if(artistString.equals("") || songString.equals("")) {
            return "Input strings cannot be null or empty";
        }
        
        //After bypassing improper insert cases, proceed to insert the
        // artistString to the artists HT and the songString to songsHT
        boolean insertedArtist = artists.insert(artistString);
        boolean insertedSong = songs.insert(songString);
        if(insertedArtist) {
            return "";
        }
        if(insertedSong) {
            return "";
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
        
        //If the object itself is not initialized, there is
        // nothing to remove and therefore causes an exception
        if(manager == null) {
            return "Database not initialized";
        }
        
        if(type == null || nameString == null) {
            return "Input strings cannot be null or empty";
        }
        if(type.equals("") || nameString.equals("")) {
            return "Input strings cannot be null or empty";
        }
        
        //If the given type does not specify a song or artist
        // then there is nothing to remove from either table 
        if(!type.equals("song") && !type.equals("artist")) {
            return "Bad type value |" + type + "| on remove";
        }
        
        //After bypassing improper remove cases, search for nameString
        // in the HT that corresponds type and remove that handle from
        // the HT
        if(type.equals("song")) {
            boolean deleted = songs.delete(nameString);
            if(deleted == false) {
                return "|" + nameString + "| does not exist in the Song database";
            }
        }
        else {
            boolean deleted = artists.delete(nameString);
            if(deleted == false) {
                return "|" + nameString + "| does not exist in the Artist database";
            }
        }
        
        return "";
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
        
        //If the object itself is not initialized, there is 
        // nothing to print and therefore causes an exception
        if(manager == null) {
            return "Database not initialized";
        }
        
        if(type == null || type.equals("")) {
            return "Input strings cannot be null or empty";
        }
        
        if(!type.equals("song") && !type.equals("artist")) {
            return "Bad print parameter";
        }
        
        if(type.equals("song")) {
            if(songs.getSize() == 0) {
                return "total songs: " + songs.getSize();
            }
            
            //print all the songs
        }
        else if(type.equals("artist")){
            if(artists.getSize() == 0) {
                return "total artists: " + artists.getSize();
            }
        }
        else {
            if(manager.) {
                
            }
        }
        
        return "";
    }
}
