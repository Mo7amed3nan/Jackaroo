package view;

public class Pair implements Comparable {
	public int x;
	public int y;
	public Pair(int x,int y){
		this.x = x;this.y = y;
	}

	public String toString(){
		return x + " " + y;
	}
	public int compareTo(Object o){
		Pair other = (Pair) o;
		
		return ((this.x == other.x) ? this.y - other.y : this.x-(other.x));
	}
}
