package puzzles.common.solver;

import java.util.*;

public class Solver {
    private LinkedList<Configuration> queue;
    private HashMap<Configuration,Boolean> visited;

    public Solver(Configuration start){
        this.queue = new LinkedList<>();
        queue.add(start);
        this.visited = new HashMap<>();
    }

    public Configuration solve() {
        while (!queue.isEmpty()) {
            Configuration current = queue.removeLast();
            if (!current.isSolution()) {
                if (visited.get(current) == null) {
                    Collection<Configuration> neighbors = current.getNeighbors();
                    for (Configuration c : neighbors) {
                        if (visited.get(c) == null) {
                            queue.add(c);
                        }
                    }
                }
                visited.put(current, true);
            }else{
                System.out.println("Solution: " + current);
                return current;
            }
        }
        return null;
    }
}

