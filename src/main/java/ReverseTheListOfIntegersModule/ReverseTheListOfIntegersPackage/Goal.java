
package ReverseTheListOfIntegersPackage;

import java.util.List;
import java.util.Set;

public record Goal(List<Integer> list)
{

   public Goal
   {
   
      list = Validator.sanitizeList(list);
   
      if (list.stream().anyMatch(eachNumber -> eachNumber <= 0))
      {
      
         throw new IllegalArgumentException("List cannot contain negative numbers!");
      
      }
      
      if (Set.copyOf(list).size() != list.size())
      {
      
         throw new IllegalArgumentException("List cannot contain duplicates! list = " + list);
      
      }
   
   }

}
