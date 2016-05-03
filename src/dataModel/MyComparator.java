package dataModel;

import java.util.Comparator;

public class MyComparator implements Comparator
{ 
	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		Integer value1 = ((Annotations) o1).getRank();
		Integer value2 = ((Annotations) o2).getRank();
		return value1.compareTo(value2); 
	}

}
