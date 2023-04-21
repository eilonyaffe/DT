
//Don't change the class name
public class Container{
	private Point data;//Don't delete or change this field;
	
	//additions
	protected Container right;
	protected Container left;
	protected Container brother;
	protected int value;

	
	public Container() {
		this.data = null;
	}
	
	public Container(Point data) {
		this.data = data;
	}
	
	public Container[] xyContainers () {
		Container xCont = new Container(data);
		xCont.value = data.getX();
		Container yCont = new Container(data);
		yCont.value = data.getY();
		xCont.brother = yCont;
		yCont.brother = xCont;
		Container[] retVal = {xCont,yCont};
		return retVal;
	}
	
	
	//Don't delete or change this function
	public Point getData()
	{
		return data;
	}
}
