
package ReverseTheListOfIntegersPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record GameProgress(List<Round> rounds, Goal goal)
{

   public GameProgress
   {
   
      Objects.requireNonNull(rounds, "rounds cannot be null!");
      Objects.requireNonNull(goal, "goal cannot be null!");
   
      rounds = Validator.sanitizeList(rounds);
   
   }

   public static GameProgress start(final Goal goal)
   {
   
      Objects.requireNonNull(goal);
   
      final List<Integer> startingListOfIntegers = goal.list().reversed();
   
      final Round firstRound = new Round(startingListOfIntegers);
   
      return new GameProgress(List.of(firstRound), goal);
   
   }

   private GameProgress add(final Round round)
   {
   
      Objects.requireNonNull(round);
   
      final List<Round> list = new ArrayList<>(this.rounds);
   
      list.add(round);
   
      return new GameProgress(list, this.goal);
   
   }

   public Result split(final int index, final int newLeftValue)
   {
   
      if (index < 0)
      {
      
         return new Failure("index cannot be < 0! index = " + index);
      
      }
   
      if (newLeftValue < 0)
      {
      
         return new Failure("New values after a split must contain positive numbers! You chose " + newLeftValue);
      
      }
   
      final List<Integer> finalRoundList = this.rounds().getLast().list();
   
      if (index >= finalRoundList.size())
      {
      
         return
            new
               Failure
               (
                  "index must be <= finalRoundList.size()!"
                  + " index = " + index
                  + " finalRoundList.size() = " + finalRoundList.size()
               )
               ;
      
      }
   
      if (finalRoundList.contains(newLeftValue))
      {
      
         return new Failure("The list of numbers cannot contain duplicates!"
            + " You entered " + newLeftValue + ", which already exists in the list!");
      
      }
   
      final int originalValue = finalRoundList.get(index);
   
      final int newRightValue = originalValue - newLeftValue;
   
      if (newRightValue <= 0)
      {
      
         return 
            new 
               Failure
               (
                  "All numbers in the list must be positive!"
                  + " After splitting " + originalValue + ", you will end up with "
                  + newLeftValue + " and " + newRightValue + "!"
               )
               ;
      
      }
   
      if (newLeftValue == newRightValue)
      {
      
         return
            new
               Failure
               (
                  "The list of numbers cannot contain duplicates!"
                  + " After splitting " + originalValue + ", you will end up with "
                  + newLeftValue + " and " + newRightValue + "!"
               )
               ;
      
      }
   
      if (finalRoundList.contains(newRightValue))
      {
      
         return
            new
               Failure
               (
                  "The list of numbers cannot contain duplicates!"
                  + " After splitting " + originalValue + ", you will end up with "
                  + newLeftValue + " and " + newRightValue 
                  + ", and " + newRightValue + " already exists in the list!"
               )
               ;
      
      }
   
      final List<Integer> newRoundList = new ArrayList<>(finalRoundList);
   
      newRoundList.remove(index);
      newRoundList.add(index, newRightValue);
      newRoundList.add(index, newLeftValue);
   
      final Round newRound = new Round(newRoundList);
   
      final GameProgress newGameProgress = this.add(newRound);
   
      return new Success(newGameProgress);
   
   }

   public Result combine(final int leftIndex)
   {
   
      if (leftIndex < 0)
      {
      
         return new Failure("leftIndex cannot be < 0! leftIndex = " + leftIndex);
      
      }
   
      final int rightIndex = leftIndex + 1;
   
      final List<Integer> finalRoundList = this.rounds().getLast().list();
   
      if (rightIndex >= finalRoundList.size())
      {
      
         return
            new
               Failure
               (
                  "rightIndex must be < finalRoundList.size()!"
                  + " rightIndex = " + rightIndex
                  + " finalRoundList.size() = " + finalRoundList.size()
               )
               ;
      
      }
   
      final int leftValue = finalRoundList.get(leftIndex);
      final int rightValue = finalRoundList.get(rightIndex);
   
      final int newValue = leftValue + rightValue;
   
      if (finalRoundList.contains(newValue))
      {
      
         return
            new
               Failure
               (
                  "The list of numbers cannot contain duplicates!"
                  + " After combining " + leftValue + " and " + rightValue
                  + ", you will end up with " + newValue + "! "
                  + newValue + " already exists in the list!"
               )
               ;
      
      }
   
      final List<Integer> newRoundList = new ArrayList<>(finalRoundList);
   
      newRoundList.remove(rightIndex);
      newRoundList.remove(leftIndex);
      newRoundList.add(leftIndex, newValue);
   
      final Round newRound = new Round(newRoundList);
   
      final GameProgress newGameProgress = this.add(newRound);
   
      return new Success(newGameProgress);
   
   }

   public boolean hasCompleted()
   {
   
      final List<Integer> finalRoundList = this.rounds().getLast().list();
      final List<Integer> goalList = this.goal().list();
   
      return finalRoundList.equals(goalList);
   
   }

}
