package dataModel;

import java.util.ArrayList;
import java.util.Comparator;



public class comparator_gen implements Comparator
{ 
	Integer value1;
	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		
		Integer value1 = ((AnnotationGeneral) o1).getRank();		
		Integer value2 = ((AnnotationGeneral) o2).getRank();
		
		return value2.compareTo(value1); 
	}
	

}
