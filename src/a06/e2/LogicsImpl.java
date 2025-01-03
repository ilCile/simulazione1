package a06.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LogicsImpl implements Logics{

    private final Map<Pair<Integer,Integer>, Integer> cells;
    private final int size;
    private boolean doneSomething;
    private final static int BLANK = -1;

    public LogicsImpl(int size) {
        this.size = size;
        this.cells = new HashMap<>(size);
    }

    @Override
    public Map<Pair<Integer,Integer>, Integer> start() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                int value = new Random().nextInt(2) + 1;
                this.cells.put(new Pair<>(i,j), value);
            }
        }
        return new HashMap<>(this.cells);
    }

    @Override
    public Map<Pair<Integer, Integer>, Integer> fire() {
        int prec, current;
        doneSomething = false;
        for (int j = 0; j < this.size; j++) {
            prec = 0;
            for (int i = this.size - 1; i >= 0; i--) {
                current = this.cells.get(new Pair<>(i, j));
                if (current == prec && current != BLANK) {
                    this.cells.put(new Pair<>(i, j), BLANK);
                    this.cells.put(new Pair<>(i + 1, j), prec * 2);
                    doneSomething = true;
                    break;
                }
                prec = current;
            }
        }
        move();
        return new HashMap<>(this.cells);
    }

    /**
     * move down all the cells that need it (the ones above a blank one)
     */
    public void move() {
        int prec, current;
        for (int j = 0; j < this.size; j++) {
            prec = 0;
            for (int i = this.size - 1; i >= 0; i--) {
                current = this.cells.get(new Pair<>(i, j));
                if (prec == BLANK) {
                    this.cells.put(new Pair<>(i, j), BLANK);
                    this.cells.put(new Pair<>(i + 1, j), current);
                    current = BLANK;
                }
                prec = current;
            }
        }
    }

    public boolean isDone() {
        return !doneSomething;
    }
}
