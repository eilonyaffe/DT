import java.util.Comparator;

public class YPointSorter implements Comparator<Point>{

	public int compare(Point a, Point b) {		
		if(a.getY()>b.getY())
			return 1;
		
		else if(a.getY()<b.getY())
			return -1;
		
		else return 0;
	}
}
