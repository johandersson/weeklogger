package se.johanandersson.weeklogger;

import java.util.Random;

/* 
 * @author Java Practices , under BSD license
 * http://www.javapractices.com/topic/TopicAction.do?Id=62
 *
 */

public final class RandomGeneratorHelperClass {
  
  public static int generateRandomInteger(int aStart, int aEnd){
    if ( aStart > aEnd ) {
      throw new IllegalArgumentException("Start cannot exceed End.");
    }
    //get the range, casting to long to avoid overflow problems
    long range = (long)aEnd - (long)aStart + 1;
    // compute a fraction of the range, 0 <= frac < range
    long fraction = (long)(range * getRandom().nextDouble());
    int randomNumber =  (int)(fraction + aStart);    
    return randomNumber;
  }

private static Random getRandom() {
	// TODO Auto-generated method stub
	return new Random();
}
 
} 
