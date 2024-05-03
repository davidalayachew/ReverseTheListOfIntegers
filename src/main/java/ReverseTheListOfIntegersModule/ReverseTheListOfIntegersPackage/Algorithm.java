
package ReverseTheListOfIntegersPackage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public final class Algorithm
{

   public static GameProgress findASolutionWithTheShortestLengthPossible(final Goal goal)
   {
   
      Objects.requireNonNull(goal);
   
      final GameProgress gameProgress = GameProgress.start(goal);
   
      final Map<Round, Integer> cache = new HashMap<>();
      cache.put(gameProgress.rounds().getLast(), gameProgress.rounds().size());
   
      final GameProgress result = Algorithm.findASolutionWithTheShortestLengthPossibleRecursive(cache, gameProgress).orElseThrow();
   
      return result;
   
   }

   public static Optional<GameProgress> findASolutionWithTheShortestLengthPossibleNonRecursive(final Map<Round, Integer> cache, final GameProgress gameProgress)
   {
   
      Objects.requireNonNull(cache);
      Objects.requireNonNull(gameProgress);
   
      final List<Integer> finalRoundList = gameProgress.rounds().getLast().list();
   
      final List<GameProgress> successfulSubResults = new ArrayList<>();
   
      for (int i = 0; i < finalRoundList.size(); i++)
      {
      
         final int index = i;
      
         final Result combineResult = gameProgress.combine(index);
      
         switch (combineResult)
         {
         
            case  Success success   -> successfulSubResults.add(success.gameProgress());
            case  Failure _         -> {}
         
         }
      
         final int originalValue = finalRoundList.get(index);
      
         for (int j = originalValue - 1; j > 0; j--)
         {
         
            final int newValue = j;
         
            final Result splitResult = gameProgress.split(index, newValue);
         
            switch (splitResult)
            {
            
               case  Success success   -> successfulSubResults.add(success.gameProgress());
               case  Failure _         -> {}
            
            }
         
         }
      
      }
   
      final List<GameProgress> finalResults = new ArrayList<>();
   
      LOOP:
      for (final GameProgress eachResult : successfulSubResults)
      {
      
         final int cacheValue = Algorithm.updateCache(cache, eachResult);
      
         if (cacheValue < eachResult.rounds().size())
         {
         
            continue LOOP;
         
         }
      
         if (eachResult.hasCompleted())
         {
         
            finalResults.add(eachResult);
         
         }
         
         else
         {
         
            final Optional<GameProgress> subBranch =
               Algorithm.findASolutionWithTheShortestLengthPossibleRecursive(cache, eachResult);
         
            if (subBranch.isPresent())
            {
            
               finalResults.add(subBranch.orElseThrow());
            
            }
         
         }
      
      }
   
      final Optional<GameProgress> mostOptimalSolution =
         finalResults
            .stream()
            .min(Comparator.comparingInt(eachGameProgress -> eachGameProgress.rounds().size()))
            ;
   
      return mostOptimalSolution;
   
   }

   public static Optional<GameProgress> findASolutionWithTheShortestLengthPossibleRecursive(final Map<Round, Integer> cache, final GameProgress gameProgress)
   {
   
      Objects.requireNonNull(cache);
      Objects.requireNonNull(gameProgress);
   
      final List<Integer> finalRoundList = gameProgress.rounds().getLast().list();
   
      final List<GameProgress> successfulSubResults = new ArrayList<>();
   
      for (int i = 0; i < finalRoundList.size(); i++)
      {
      
         final int index = i;
      
         final Result combineResult = gameProgress.combine(index);
      
         switch (combineResult)
         {
         
            case  Success success   -> successfulSubResults.add(success.gameProgress());
            case  Failure _         -> {}
         
         }
      
         final int originalValue = finalRoundList.get(index);
      
         for (int j = originalValue - 1; j > 0; j--)
         {
         
            final int newValue = j;
         
            final Result splitResult = gameProgress.split(index, newValue);
         
            switch (splitResult)
            {
            
               case  Success success   -> successfulSubResults.add(success.gameProgress());
               case  Failure _         -> {}
            
            }
         
         }
      
      }
   
      final List<GameProgress> finalResults = new ArrayList<>();
   
      LOOP:
      for (final GameProgress eachResult : successfulSubResults)
      {
      
         final int cacheValue = Algorithm.updateCache(cache, eachResult);
      
         if (cacheValue < eachResult.rounds().size())
         {
         
            continue LOOP;
         
         }
      
         if (eachResult.hasCompleted())
         {
         
            finalResults.add(eachResult);
         
         }
         
         else
         {
         
            final Optional<GameProgress> subBranch =
               Algorithm.findASolutionWithTheShortestLengthPossibleRecursive(cache, eachResult);
         
            if (subBranch.isPresent())
            {
            
               finalResults.add(subBranch.orElseThrow());
            
            }
         
         }
      
      }
   
      final Optional<GameProgress> mostOptimalSolution =
         finalResults
            .stream()
            .min(Comparator.comparingInt(eachGameProgress -> eachGameProgress.rounds().size()))
            ;
   
      return mostOptimalSolution;
   
   }

   public static GameProgress findASolutionQuickly(final Goal goal)
   {
   
      Objects.requireNonNull(goal);
   
      final GameProgress gameProgress = GameProgress.start(goal);
   
      final Map<Round, Integer> cache = new HashMap<>();
      cache.put(gameProgress.rounds().getLast(), gameProgress.rounds().size());
   
      final GameProgress result = Algorithm.findASolutionQuicklyRecursive(cache, gameProgress).orElseThrow();
   
      return result;
   
   }

   public static Optional<GameProgress> findASolutionQuicklyRecursive(final Map<Round, Integer> cache, final GameProgress gameProgress)
   {
   
      Objects.requireNonNull(cache);
      Objects.requireNonNull(gameProgress);
   
      final List<Integer> finalRoundList = gameProgress.rounds().getLast().list();
   
      final List<GameProgress> successfulResults = new ArrayList<>();
   
      for (int i = 0; i < finalRoundList.size(); i++)
      {
      
         final int index = i;
      
         final Result combineResult = gameProgress.combine(index);
      
         switch (combineResult)
         {
         
            case  Success success   -> successfulResults.add(success.gameProgress());
            case  Failure _         -> {}
         
         }
      
         final int originalValue = finalRoundList.get(index);
      
         for (int j = originalValue - 1; j > 0; j--)
         {
         
            final int newValue = j;
         
            final Result splitResult = gameProgress.split(index, newValue);
         
            switch (splitResult)
            {
            
               case  Success success   -> successfulResults.add(success.gameProgress());
               case  Failure _         -> {}
            
            }
         
         }
      
      }
   
      LOOP:
      for (final GameProgress eachResult : successfulResults)
      {
      
         final int cacheValue = Algorithm.updateCache(cache, eachResult);
      
         if (cacheValue < eachResult.rounds().size())
         {
         
            continue LOOP;
         
         }
      
         if (eachResult.hasCompleted())
         {
         
            return Optional.of(eachResult);
         
         }
      
         final Optional<GameProgress> subBranch =
            Algorithm.findASolutionQuicklyRecursive(cache, eachResult);
      
         if (subBranch.isPresent())
         {
         
            return subBranch;
         
         }
      
      }
   
      return Optional.empty();
   
   }

   private static int updateCache(final Map<Round, Integer> cache, final GameProgress gameProgress)
   {
   
      Objects.requireNonNull(cache);
      Objects.requireNonNull(gameProgress);
   
      final List<Round> rounds = gameProgress.rounds();
   
      final Integer result =
         cache
            .merge
            (
               rounds.getLast(),
               rounds.size(),
               Integer::min
            )
            ;
   
      return result;
   
   }

   private static <T> T completeFuture(final Future<T> future)
   {
   
      try
      {
      
         return future.get();
      
      }
      
      catch (final Exception exception)
      {
      
         throw new RuntimeException(exception);
      
      }
   
   }

}
