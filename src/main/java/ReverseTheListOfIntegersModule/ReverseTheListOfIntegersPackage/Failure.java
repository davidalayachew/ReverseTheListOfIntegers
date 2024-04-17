
package ReverseTheListOfIntegersPackage;

import java.util.Objects;

public record Failure(String reason) implements Result
{

   public Failure
   {
   
      Objects.requireNonNull(reason);
      
      if (reason.isBlank())
      {
      
         throw new IllegalArgumentException("message is blank!");
      
      }
   
   }

}
