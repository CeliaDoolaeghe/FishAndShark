package iagl.idl.simulation.graph;

import iagl.idl.simulation.mas.MAS;
import iagl.idl.simulation.mas.agent.fishandsharks.FishAndSharkAgent;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class PopulationLogger {

    protected final MAS mas;
    protected final PrintWriter writer;

    public PopulationLogger(MAS mas, String filePath) throws FileNotFoundException {
        this.mas = mas;
        this.writer = new PrintWriter(filePath);
    }

    public void write() {
//        Map<Integer, Integer> fish = new HashMap<Integer, Integer>();
//        Map<Integer, Integer> sharks = new HashMap<Integer, Integer>();
//
//        Map<Integer, Integer> currentMap;
//        int age;
//
//        // search age for each agent
//        for (FishAndSharkAgent fishAndSharkAgent : mas.getEnvironment().getAllAgents()) {
//            age = fishAndSharkAgent.getAge();
//            if (fishAndSharkAgent.isEatable()) {
//                currentMap = fish;
//            } else {
//                currentMap = sharks;
//            }
//
//            if (currentMap.containsKey(age)) {
//                currentMap.put(age, currentMap.get(age) + 1);
//            } else {
//                currentMap.put(age, 1);
//            }
//        }
//
//        // Merge fish & sharks age
//        Set<Integer> keys = new HashSet<Integer>();
//        keys.addAll(fish.keySet());
//        keys.addAll(sharks.keySet());
//        int nbFish, nbSharks;
//        for (Integer key : keys) {
//            if(fish.containsKey(key)) {
//                nbFish = fish.get(key);
//            } else {
//                nbFish = 0;
//            }
//
//            if(sharks.containsKey(key)) {
//                nbSharks = sharks.get(key);
//            } else {
//                nbSharks = 0;
//            }
//
//            // Write ages
//            writer.write(String.format("%d %d %d \n", key, nbFish, nbSharks));
//        }
//        writer.close();
    }
}
