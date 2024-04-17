
package ReverseTheListOfIntegersPackage;

import java.util.Objects;

public record Success(GameProgress gameProgress) implements Result
{

   public Success
   {
   
      Objects.requireNonNull(gameProgress);
   
   }

}
