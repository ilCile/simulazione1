package a06.e2;

import java.util.Map;

public interface Logics {

    /**
     * 
     * @return the initialized map
     */
    Map<Pair<Integer,Integer>, Integer> start();

    /**
     * 
     * @return the modified map
     */
    Map<Pair<Integer,Integer>, Integer> fire();
    
    /**
     * 
     * @return true if the program is done, false otherwise
     */
    public boolean isDone();
}
