import java.util.Arrays;

public class Main {
	
	public static void main(String[] args) {
		DataStructure dt = new DataStructure();
		
		Point p1 = new Point(-4,-6,"p1");
		Point p2 = new Point(1,-3,"p2");
		Point p3 = new Point(0,-7,"p3");
		Point p4 = new Point(5,2,"p4");
		Point p5 = new Point(3,12,"p5");
		Point p6 = new Point(4,6,"p6");
//		Point p7 = new Point(30,3,"p7");


		dt.addPoint(p1);
		dt.addPoint(p2);
		dt.addPoint(p3);
		dt.addPoint(p4);
		dt.addPoint(p5);
		dt.addPoint(p6);
//		dt.addPoint(p7);

		
		dt.printall();
		
		Container medianY = dt.getMedian(false);
		System.out.println("median is: " + medianY.value);
		Point[] closestPair = dt.nearestPairInStrip(medianY,12.0,false);
		
		System.out.println(Arrays.toString(closestPair));

	}

}







//public void deleter(Container contDelete, Sentinel specSentinel) {
//	if(contDelete.right==null) { //if deleting the max value in the list
//		specSentinel.maxPoint = contDelete.left;
//		contDelete.left.right = null;
////		contDelete.left = null;
//		
//		if(contDelete.brother!=null) {
//			this.deleter(contDelete.brother, (Sentinel) specSentinel.brother); //deleting in the corresponding list
//		}
//	}
//	else {
//		if(contDelete.value==specSentinel.minPoint.value)
//			specSentinel.minPoint = contDelete.right;
//		Container predeccessor = contDelete.left;
//		Container successor = contDelete.right;
//		this.addBothWays(predeccessor,successor);
//		contDelete.left = null;
//		contDelete.right = null;
//
//		if(contDelete.brother!=null) {
//			this.deleter(contDelete.brother, (Sentinel) specSentinel.brother);
//		}			
//	}
//}



//public Point[] nearestPairInStrip(Container container, double width, Boolean axis) {
//	
//	Sentinel currSent;
//	if (axis) currSent = this.xSentinel;
//	else currSent = this.ySentinel;
//	
//	int minRange = (int) (container.value - (width/2)); //the points have natural values
//	int maxRange = (int) (container.value + (width/2));
//	
//	Point[] pointsAns;
//	
//	Point[] pointsInRange = new Point[n]; //n is maximum value it can have //TODO maybe change later to have better allocation
//	pointsInRange[0] = container.getData(); //median will always be in the range
//	int pointsInd = 1; //index for the array
//	
//	if(container.right!=null) {
//		Container rightWalk = container.right;
//		
//		while(rightWalk.value<=maxRange) {
//			pointsInRange[pointsInd] = rightWalk.getData();
//			pointsInd++;
//			
//			if(rightWalk.right != null)
//				rightWalk = rightWalk.right;
//			else break; //can't go right anymore
//		}
//	}
//	
//	if(container.left!=null) {
//		Container leftWalk = container.left;
//		
//		while(leftWalk.value>=minRange) {
//			pointsInRange[pointsInd] = leftWalk.getData();
//			pointsInd++;
//			
//			if(leftWalk.left != null)
//				leftWalk = leftWalk.left;
//			else break; //can't go left anymore
//		}
//	}
//
//			
//	if(pointsInd == 1) { //no need to make comparisons
//		pointsAns = new Point[0];
//		return pointsAns;
//	}
//	
//	if(pointsInd == 2) { //no need to make comparisons
//		pointsAns = new Point[2];
//		pointsAns[0] = container.getData();
//		pointsAns[1] = pointsInRange[1];
//		return pointsAns;
//	}
//	
//	
//	
//	
//	return null;
//}
