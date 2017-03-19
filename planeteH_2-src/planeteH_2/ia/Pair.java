package planeteH_2.ia;

public class Pair<L,R> {

  private final L first;
  private final R second;

  public Pair(L first, R second) {
    this.first = first;
    this.second = second;
  }

  public L getFirst() { return first; }
  public R getSecond() { return second; }

  @Override
  public int hashCode() { return first.hashCode() ^ second.hashCode(); }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Pair)) return false;
    Pair pairo = (Pair) o;
    return this.first.equals(pairo.getFirst()) &&
           this.second.equals(pairo.getSecond());
  }

}