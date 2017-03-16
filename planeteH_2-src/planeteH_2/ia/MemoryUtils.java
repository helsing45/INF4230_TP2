package planeteH_2.ia;

import planeteH_2.Position;

/**
 * Created by j-c9 on 2017-03-12.
 */
public class MemoryUtils {

    private Position lastPositionPlayed;
    private static MemoryUtils instance;

    private MemoryUtils() {
    }

    public static MemoryUtils getInstance(){
        if(instance == null){
            instance = new MemoryUtils();
        }
        return instance;
    }

    public static Position getLastPositionPlayed(){
        return getInstance().lastPositionPlayed;
    }

    public static void setLastPositionPlayed(Position position){
        getInstance().lastPositionPlayed = position;
    }
}
