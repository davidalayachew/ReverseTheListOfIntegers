
package ReverseTheListOfIntegersPackage;

import java.util.List;

public class Main
{

   public static void main(final String[] args)
   {
   
      System.out.println("ReverseTheListOfIntegers");
   
      final Goal goal = new Goal(List.of(3, 5, 7));
   
      System.out.println("--------------------------------------");
      System.out.println("        FIND A SOLUTION QUICKLY       ");
   
      Algorithm.findASolutionQuickly(goal)
         .rounds()
         .forEach(System.out::println)
         ;
   
      System.out.println("--------------------------------------");
   
      System.out.println("++++++++++++++++++++++++++++++++++++++");
      System.out.println("SOLUTION WITH SHORTEST LENGTH POSSIBLE");
   
      Algorithm.findASolutionWithTheShortestLengthPossible(goal)
         .rounds()
         .forEach(System.out::println)
         ;
   
      System.out.println("++++++++++++++++++++++++++++++++++++++");
   
   }

}
