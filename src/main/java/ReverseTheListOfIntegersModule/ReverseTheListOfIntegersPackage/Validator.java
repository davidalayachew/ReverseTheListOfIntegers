
package ReverseTheListOfIntegersPackage;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class Validator
{

   public static <T> List<T> sanitizeList(final List<T> list)
   {
   
      Objects.requireNonNull(list, "List cannot be null!");
   
      if (list.isEmpty())
      {
      
         throw new IllegalArgumentException("List cannot be empty!");
      
      }
   
      if (list.stream().anyMatch(Objects::isNull))
      {
      
         throw new IllegalArgumentException("List cannot contain nulls!");
      
      }
   
      return List.copyOf(list);
   
   }

   public static <T> void validateIndex(final int index, final Collection<T> collection)
   {
   
      Objects.requireNonNull(collection);
   
      if (collection.isEmpty())
      {
      
         throw new IllegalStateException("Cannot validate index if collection is empty!");
      
      }
   
      if (index < 0)
      {
      
         throw new IllegalArgumentException("index cannot be < 0! index = " + index);
      
      }
   
      if (index >= collection.size())
      {
      
         throw new IllegalArgumentException("index cannot be >= collection.size()! index = " + index
            + " collection.size() = " + collection.size());
      
      }
   
   }

   public static <T> void validateIndex(final int index, final T[] array)
   {
   
      Objects.requireNonNull(array);
   
      if (array.length == 0)
      {
      
         throw new IllegalStateException("Cannot validate index if array is empty!");
      
      }
   
      if (index < 0)
      {
      
         throw new IllegalArgumentException("index cannot be < 0! index = " + index);
      
      }
   
      if (index >= array.length)
      {
      
         throw new IllegalArgumentException("index cannot be >= array.length! index = " + index
            + " array.length = " + array.length);
      
      }
   
   }

}
