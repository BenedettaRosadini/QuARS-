package dataModel;

import java.util.Comparator;

public class MyComparator implements Comparator
{ 
	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		int retVal = 0;
		Integer value1 = ((Annotations) o1).getRank();
		Integer value2 = ((Annotations) o2).getRank();
		if(value1 < value2)
		{
			retVal = 1;
		}
		else
		{
			retVal = -1;
		}
		return retVal; 
	}

}
