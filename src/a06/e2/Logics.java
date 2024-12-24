package a06.e2;

import java.util.Map;

public interface Logics {
    Map<Pair<Integer,Integer>, Integer> start();

    Map<Pair<Integer,Integer>, Integer> fire();
    
    public boolean isDone();
}
