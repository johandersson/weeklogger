package se.johanandersson.weeklogger;

public class TimeLoggerUtil {
	public static Object[] addElementToArray(Object[] arrayToAddElementTo, Object elementToAdd){
		int lengthOfNewArray = arrayToAddElementTo.length+1;
		Object[] newArray = new Object[lengthOfNewArray];
		
		for(int i=0;i<arrayToAddElementTo.length;i++){
			newArray[i] = arrayToAddElementTo[i];
		}
		
		int arrToReturnSize = newArray.length+1;
		newArray[lengthOfNewArray] = elementToAdd;
		
		return newArray;
		
	}

}
