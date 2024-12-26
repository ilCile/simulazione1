package a06.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class FluentParserFactoryImpl implements FluentParserFactory {

    @Override
    public FluentParser<Integer> naturals() {
        return new FluentParserAbstract<Integer>() {
            @Override
            public boolean isOk(Integer value) {
                int lastValue = this.prev.getLast() + 1;
                return value.equals(lastValue);
            } 
        };
    }

    @Override
    public FluentParser<List<Integer>> incrementalNaturalLists() {
        return new FluentParserAbstract<List<Integer>>() {
            @Override
            public boolean isOk(List<Integer> value) {
                if (this.prev.getLast().isEmpty() && value.equals(List.of(0))) return true;
                if (value.size() != (this.prev.getLast().size() + 1)) return false;
                for (int i = 0; i < this.prev.getLast().size(); i++) {
                    if (!this.prev.getLast().get(i).equals(value.get(i))) return false;
                }
                int lastValue = this.prev.getLast().getLast() + 1;
                return value.getLast().equals(lastValue);
            }
        };
    }

    @Override
    public FluentParser<Integer> repetitiveIncrementalNaturals() {
        return new FluentParserAbstract<Integer>() {
            private int counter = 0;
            @Override
            public boolean isOk(Integer value) {
                if (counter == 0) {
                    this.counter = this.prev.size();
                    this.prev.clear();
                    return value.equals(0);
                }
                this.counter--;
                int lastValue = this.prev.getLast() + 1;
                return value.equals(lastValue);
            }
        };
    }

    @Override
    public FluentParser<String> repetitiveIncrementalStrings(String s) {
        return new FluentParserAbstract<String>() {
            private int counter = 0;
            @Override
            public boolean isOk(String value) {
                if (counter == 0) {
                    this.counter = this.prev.size();
                    this.prev.clear();
                    return value.equals(s);
                }
                this.counter--;
                String lastValue = this.prev.getLast() + s;
                return value.equals(lastValue);
            }
            
        };
    }

    @Override
    public FluentParser<Pair<Integer, List<String>>> incrementalPairs(int i0, UnaryOperator<Integer> op, String s) {
        return new FluentParserAbstract<Pair<Integer,List<String>>>() {
            @Override
            public boolean isOk(Pair<Integer, List<String>> value) {
                if (value.getY().size() != value.getX() ||
                !value.getX().equals(op.apply(this.prev.getLast().getX()))) {
                    return false;
                }
                for(int i = 0; i < value.getY().size(); i++) {
                    if(!value.getY().get(i).equals(s)) {
                        return false;
                    }
                }
                return true;
            }   
        };
    }

    public abstract class FluentParserAbstract<T> implements FluentParser<T> {

        public final List<T> prev = new ArrayList<>();

        @Override
        public FluentParser<T> accept(T value) {
            if (prev.isEmpty() || isOk(value)) {
                this.prev.addLast(value);
                return this;
            }
            throw new IllegalStateException();
        }

        public abstract boolean isOk(T value);
    }

}
