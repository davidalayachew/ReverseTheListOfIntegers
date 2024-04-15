
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

   public Optional<GameProgress> split(final int index, final int newLeftValue)
   {
   
      if (index < 0)
      {
      
         throw new IllegalArgumentException("index cannot be < 0! index = " + index);
      
      }
   
      if (newLeftValue < 0)
      {
      
         return Optional.empty();
      
      }
   
      final List<Integer> finalRoundList = this.rounds().getLast().list();
   
      if (index >= finalRoundList.size())
      {
      
         return Optional.empty();
      
      }
   
      if (finalRoundList.contains(newLeftValue))
      {
      
         return Optional.empty();
      
      }
   
      final int originalValue = finalRoundList.get(index);
   
      final int newRightValue = originalValue - newLeftValue;
   
      if (newRightValue <= 0)
      {
      
         return Optional.empty();
      
      }
   
      if (newLeftValue == newRightValue)
      {
      
         return Optional.empty();
      
      }
   
      if (finalRoundList.contains(newRightValue))
      {
      
         return Optional.empty();
      
      }
   
      final List<Integer> newRoundList = new ArrayList<>(finalRoundList);
   
      newRoundList.remove(index);
      newRoundList.add(index, newRightValue);
      newRoundList.add(index, newLeftValue);
   
      final Round newRound = new Round(newRoundList);
   
      final GameProgress newGameProgress = this.add(newRound);
   
      return Optional.of(newGameProgress);
   
   }

   public Optional<GameProgress> combine(final int leftIndex)
   {
   
      if (leftIndex < 0)
      {
      
         throw new IllegalArgumentException("leftIndex cannot be < 0! leftIndex = " + leftIndex);
      
      }
      
      final int rightIndex = leftIndex + 1;
   
      final List<Integer> finalRoundList = this.rounds().getLast().list();
   
      if (rightIndex >= finalRoundList.size())
      {
      
         return Optional.empty();
      
      }
   
      final int leftValue = finalRoundList.get(leftIndex);
      final int rightValue = finalRoundList.get(rightIndex);
      
      final int newValue = leftValue + rightValue;
      
      if (finalRoundList.contains(newValue))
      {
      
         return Optional.empty();
      
      }
   
      final List<Integer> newRoundList = new ArrayList<>(finalRoundList);
   
      newRoundList.remove(rightIndex);
      newRoundList.remove(leftIndex);
      newRoundList.add(leftIndex, newValue);
   
      final Round newRound = new Round(newRoundList);
   
      final GameProgress newGameProgress = this.add(newRound);
   
      return Optional.of(newGameProgress);
   
   }

   public boolean hasCompleted()
   {
   
      final List<Integer> finalRoundList = this.rounds().getLast().list();
      final List<Integer> goalList = this.goal().list();
      
      return finalRoundList.equals(goalList);
   
   }

}
