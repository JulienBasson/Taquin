public abstract class Heuristic {
    public abstract double distance(State a, State b);
    public double costLeft(State a){
        return distance(a, State.goal(a.size()));
    }
}
