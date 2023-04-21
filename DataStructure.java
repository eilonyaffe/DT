import java.util.Arrays;
import java.util.Comparator;

public class DataStructure implements DT {


	//////////////// DON'T DELETE THIS CONSTRUCTOR ////////////////
	
	protected Sentinel xSentinel;
	protected Sentinel ySentinel;
	protected int n; 
	protected Comparator<Point> XPointSorter;
	protected Comparator<Point> YPointSorter;

	
	public DataStructure(){
		this.xSentinel = new Sentinel();
		this.ySentinel = new Sentinel();
		this.xSentinel.brother = ySentinel;
		this.ySentinel.brother = xSentinel;
		
		this.XPointSorter = new XPointSorter();;
		this.YPointSorter = new YPointSorter();;

		
		this.n = 0;

	}
	
	
	@Override
	public void addPoint(Point point) {
		Container currCont = new Container(point);
		Container[] contArr = currCont.xyContainers(); //get container for x value, and container for y value
		Container xCont = contArr[0];
		Container yCont = contArr[1];
		
		this.addToList(xSentinel, xCont); //adds the container in a sorted manner into the list of said sentinel
		this.addToList(ySentinel, yCont);
		
		this.n ++;
	}
	
	
	public void addBothWays(Container predeccesor, Container successor) {
		predeccesor.right = successor;
		successor.left = predeccesor;
	}
	
	
	public void addInTheMiddle(Container predeccesor, Container middle, Container successor) {
		predeccesor.right = middle;
		middle.right = successor;
		successor.left = middle;
		middle.left = predeccesor;
	}
	
	
	public void addToList(Sentinel sentinel, Container newCont) {
		if(sentinel.right==null) { //if this is the first addition in the sentinel
			this.addBothWays(sentinel, newCont);
			sentinel.maxPoint = newCont;
			sentinel.minPoint = newCont;
		}
		else {
			if(newCont.value > sentinel.maxPoint.value) { //added is the new max
				this.addBothWays(sentinel.maxPoint, newCont);
				sentinel.maxPoint = newCont;
			}
			else if(newCont.value < sentinel.minPoint.value) { //added is the new min
				this.addInTheMiddle(sentinel, newCont, sentinel.right);
				sentinel.minPoint = newCont;
			}
			else { //will add somewhere in the middle
				if (Math.abs(newCont.value-sentinel.minPoint.value) < Math.abs(newCont.value-sentinel.maxPoint.value)) { //if theoretically closer to the left edge
					this.addFromLeft(sentinel, newCont);
				}
				else this.addFromRight(sentinel, newCont);
			}
		}
	}
	
	
	public void addFromLeft(Sentinel sentinel, Container newCont) {
		int comparator = newCont.value; 
		Container currInSent = sentinel.right;
		
		while(comparator>currInSent.value) { 
			currInSent = currInSent.right;
		}
		this.addInTheMiddle(currInSent.left, newCont, currInSent);
	}
	
	
	public void addFromRight(Sentinel sentinel, Container newCont) {
		int comparator = newCont.value; 
		Container currInSent = sentinel.maxPoint;
		
		while(comparator<currInSent.value) { 
			currInSent = currInSent.left;
		}
		this.addInTheMiddle(currInSent, newCont, currInSent.right);
	}
	

	@Override
	public Point[] getPointsInRangeRegAxis(int min, int max, Boolean axis) {
		
		if(this.xSentinel.right==null)  //if there are no planes
			return new Point[0];
		
		if(axis) {
			return getPointsInSpecificAxis(min,max,xSentinel);
		}
		else return getPointsInSpecificAxis(min,max,ySentinel);		
	}
	
	
	public Point[] getPointsInSpecificAxis(int min, int max, Sentinel specSentinel) { //TODO maybe make it more efficient, like 1.4.9

		int arrLength = n;
		
		Container leftInd = specSentinel.minPoint;
		Container rightInd = specSentinel.maxPoint;
		
		while(rightInd.value>max) {
			rightInd = rightInd.left;
			arrLength--;
		}
		
		while(leftInd.value<min) {
			leftInd = leftInd.right;
			arrLength--;
		}
		
		Point[] pointsArr = new Point[arrLength];
		int i = 0;
		while(leftInd.value<rightInd.value) {
			pointsArr[i]=leftInd.getData();
			leftInd = leftInd.right;
			i++;
		}
		
		pointsArr[i] = rightInd.getData();	
		
		return pointsArr;
	}


	@Override
	public Point[] getPointsInRangeOppAxis(int min, int max, Boolean axis) { //TODO maybe change, i think now it's nlogn
		Point[] ans =  getPointsInRangeRegAxis(min,max,axis);
		
		if(axis) {
			Arrays.sort(ans, YPointSorter);
			return ans;
		}
		else {
			Arrays.sort(ans, XPointSorter);
			return ans;
		}
	}

	@Override
	public double getDensity() { //maybe use some math to make sure numbers don't reach MV? for example (root)n/(x_max-x_min)*(root)n/(y_max-y_min)
		return ((double) n) / ((double) ( (xSentinel.maxPoint.value-xSentinel.minPoint.value) * (ySentinel.maxPoint.value-ySentinel.minPoint.value) ));
	}

	
	@Override
	public void narrowRange(int min, int max, Boolean axis) {
		Sentinel currSent;
		if (axis) currSent = this.xSentinel;
		else currSent = this.ySentinel;
		
		Container leftInd = currSent.minPoint;
		Container rightInd = currSent.maxPoint;
		
		while(rightInd != null && rightInd.value>max) {
			Container rightInd2 = rightInd;
			rightInd = rightInd.left;
			this.delete(rightInd2,currSent);
		}
		
		while(leftInd != null && leftInd.value<min) {
			Container leftInd2 = leftInd;
			leftInd = leftInd.right;
			this.delete(leftInd2,currSent);
		}
	}
	
	
	public void delete(Container contDelete, Sentinel specSentinel) {
		deleter(contDelete,specSentinel);
		deleter(contDelete.brother, (Sentinel) specSentinel.brother);
		n--;
	}

	
	public void deleter(Container contDelete, Sentinel specSentinel) {
		if(contDelete==specSentinel.maxPoint) { //if deleting the max value
			if(n>1) {
				Container predeccessor = contDelete.left;
				specSentinel.maxPoint = predeccessor;
				predeccessor.right = null;
			}

			else {
				specSentinel.maxPoint = null;
				specSentinel.right = null;
			}
		}
			
		else if(contDelete==specSentinel.minPoint) { //if deleting the min value
			Container successor = contDelete.right;
			specSentinel.minPoint = successor;
			this.addBothWays(specSentinel, successor);
		}
		
		else {
			Container predeccessor = contDelete.left;
			Container successor = contDelete.right;
			this.addBothWays(predeccessor, successor);
		}
	}

	
	@Override
	public Boolean getLargestAxis() {
		return (xSentinel.maxPoint.value-xSentinel.minPoint.value > ySentinel.maxPoint.value-ySentinel.minPoint.value);
	}

	@Override
	public Container getMedian(Boolean axis) {
		int median = (int) Math.floor(n/2);
		int counter = 0;
		
		Sentinel currSent;
		if (axis) currSent = this.xSentinel;
		else currSent = this.ySentinel;
		
		Container currCont = currSent.right;
		
		while(counter<median) {
			currCont = currCont.right;
			counter++;
		}
		
		return currCont;
	}

	@Override
	public Point[] nearestPairInStrip(Container container, double width, Boolean axis) { //TODO check why runtime can also be n if min
		
		int minRange = (int) (container.value - Math.floor(width/2)); //the points have natural values
		int maxRange = (int) (container.value + Math.floor(width/2));
				
		Sentinel currSent;
		if (axis) currSent = this.xSentinel;
		else currSent = this.ySentinel;
		
		Container rightWalk = null;
		Container leftWalk = null;
		
		Point[] pointsAns;
		int counter = 1;
		
		if(container.right!=null && container.right.value<=maxRange) {
			rightWalk = container.right;
			counter++;
			
			while(rightWalk.right!=null && rightWalk.right.value<=maxRange) {
				rightWalk = rightWalk.right;
				counter++;
			}
		}
		
		if(container.left!=null && container.left!=currSent && container.left.value>=minRange) {
			leftWalk = container.left;
			counter++;

			while(leftWalk.left!=null && leftWalk.left!=currSent && leftWalk.left.value>=minRange) {
				leftWalk = leftWalk.left;
				counter++;
			}
		}
		
		if(rightWalk==null && leftWalk==null) //only the median is in the range
			return pointsAns = new Point[0];
		
		if(leftWalk==null && rightWalk==container.right) { //only two points are in range
			pointsAns = new Point[2];
			pointsAns[0] = container.getData();
			pointsAns[1] = rightWalk.getData();
			
			return pointsAns;
		}
		
		if(rightWalk==null && leftWalk==container.left) { //only two points are in range
			pointsAns = new Point[2];
			pointsAns[0] = container.getData();
			pointsAns[1] = leftWalk.getData();
			return pointsAns;
		}
		
		pointsAns = new Point[counter]; //reaching only if the are more than two points in range
		int i = 0;
		while(leftWalk != rightWalk.right) {
			pointsAns[i] = leftWalk.getData();
			leftWalk = leftWalk.right;
			i++;
		}
				
		if(axis) Arrays.sort(pointsAns, YPointSorter); //sorted by the other axis, to determine their distances more easily
		else Arrays.sort(pointsAns, XPointSorter);
		
		System.out.println(Arrays.toString(pointsAns));

		
		Point[] closestPair = new Point[2];
		closestPair[0] = pointsAns[0];
		closestPair[1] = pointsAns[1];
		double minD = this.distance(pointsAns[0], pointsAns[1]);
		
		for(int p=0; i<pointsAns.length-1;i++) {
			for(int j=p+1;  j<8 && j<pointsAns.length; j++) { //using a geometric theorem that we need to check for at most 7 points
				double currD = this.distance(pointsAns[p], pointsAns[j]);
				if(currD<minD) {
					closestPair[0] = pointsAns[p];
					closestPair[1] = pointsAns[j];
					minD = currD;
				}
			}
		}

		return closestPair;
	}
	
	
	public double distance (Point pointA, Point pointB) {

		return Math.sqrt( Math.pow((pointA.getX()-pointB.getX()), 2) + Math.pow((pointA.getY()-pointB.getY()),2));
		
	}

	@Override
	public Point[] nearestPair() {
		// TODO Auto-generated method stub
		return null;
	}

	
	//TODO: add members, methods, etc.
	
	public void printall() { //TODO delete later
		
		int[] xarr = new int[100];
		int[] yarr = new int[100];

		Container currx = xSentinel.right;
		Container curry = ySentinel.right;

		int i = 0;
		while(currx!=null) {
			xarr[i] = currx.value;
			yarr[i] = curry.value;

			currx = currx.right;
			curry = curry.right;
			i++;
		}
		
		System.out.println(Arrays.toString(xarr)); 
		System.out.println(Arrays.toString(yarr)); 


		
	}
}

