import java.util.*;
public class Match {
 
    //This class is used to handle string matching for the dungeon game. 
    public Match() {
         
    }
    // below are functions that takes in a string and compares and checks if there's a match in the treemap, ignores case
    // function is overloaded for each map we have
 
    public Artifact contains(String source, Map<String, Artifact> artifacts ) { // returns the artifact for which contains yielded true
        for ( Map.Entry<String, Artifact> entry: artifacts.entrySet() ) {
            if ( source.equalsIgnoreCase(entry.getValue().name() ) ) {
                return entry.getValue();
            }
        }
        return null;
    }   
}