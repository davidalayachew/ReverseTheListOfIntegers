
package ReverseTheListOfIntegersPackage;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.*;
import java.util.function.*;
import java.util.regex.*;

public final class GUI
{

   private final Runnable UPDATE_SCREEN;
   private final Consumer<String> POPUP;
   private final Consumer<GameProgress> NEXT_ROUND;
   private final List<JComponent> GUI_COMPONENTS_TO_DEACTIVATE_WHEN_GAME_ENDS = new ArrayList<>();
   private final Runnable DEACTIVATE_GUI =
      () -> GUI_COMPONENTS_TO_DEACTIVATE_WHEN_GAME_ENDS.forEach(each -> each.setEnabled(false));

   private List<GameProgress> state = new ArrayList<>();
   private int index;

   public GUI(final Goal goal)
   {
   
      Objects.requireNonNull(goal);
   
      this.state.add(GameProgress.start(goal));
      index = 0;
   
      final JFrame frame = new JFrame();
      frame.setTitle("Reverse the List of Integers!");
   
      final JPanel mainPanel = new JPanel();
      mainPanel.add(this.populateFrame());
   
      this.UPDATE_SCREEN =
         () ->
         {
         
            System.out.println(this.state.get(this.index));
            mainPanel.removeAll();
            mainPanel.add(this.populateFrame());
            frame.revalidate();
            frame.repaint();
            frame.pack();
         
         }
         ;
   
      this.POPUP = message -> JOptionPane.showMessageDialog(frame, message);
   
         this.NEXT_ROUND =
         newGameProgress ->
         {
         
            this.state.add(newGameProgress);
            this.index++;
         
            this.UPDATE_SCREEN.run();
         
         }
         ;
   
      frame.add(mainPanel);
   
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setLocationByPlatform(true);
      frame.setVisible(true);
   
   }

   public static void createGUI()
   {
   
      final int MIN_VALUE = 1;
      final int MAX_VALUE = 999999;
   
      final Pattern numberListRegex = Pattern.compile("^(?:\\s*[0-9]{1,6}\\s*\\,)*\\s*[0-9]{1,6}\\s*$");
   
      boolean isValidListOfNumbers;
      String response = "";
      List<Integer> numberList = new ArrayList<>();
      DO_WHILE_LOOP:
      do
      {
      
         isValidListOfNumbers = true;
      
         response =
            JOptionPane
               .showInputDialog
               (
                  """
                  <html>
                     Enter your list of numbers! For example -- 7, 5, 3  <br>
                     All numbers must be positive!<br>
                     No duplicate numbers!
                  </html>
                  """
               )
               ;
      
         if (response == null)
         {
         
            return;
         
         }
      
         if (!numberListRegex.matcher(response).matches())
         {
         
            isValidListOfNumbers = false;
         
            continue DO_WHILE_LOOP;
         
         }
      
         response = response.replaceAll("\\s", "");
      
         final String[] array = response.split("\\,", -1);
      
         System.out.println(Arrays.toString(array));
      
         numberList =
            Arrays
               .stream(array)
               .map(Integer::parseInt)
               .toList()
               ;
      
         isValidListOfNumbers &= numberList.stream().noneMatch(number -> number <= 0);
      
         isValidListOfNumbers &= Set.copyOf(numberList).size() == numberList.size();
      
      }
      
      while (!isValidListOfNumbers);
   
      System.out.println(numberList);
   
      final Goal goal = new Goal(numberList);
   
      new GUI(goal);
   
   }

   private JPanel populateFrame()
   {
   
      final GUI gui = this;
   
      final JPanel mainPanel = new JPanel();
      mainPanel.setLayout(new BorderLayout());
   
      final JPanel interactionPanel = new JPanel();
      interactionPanel.setLayout(new GridLayout(2, 0));
   
      final GameProgress currentGameProgress = gui.state.get(gui.index);
      final Round currentRound = currentGameProgress.rounds().getLast();
   
      System.out.println(currentRound);
   
      final int upperLimit = (currentRound.list().size() * 2) - 1;
   
      for (int i = 0; i < upperLimit; i++)
      {
      
         final JLabel label;
      
         if (i % 2 == 0) //Show the value
         {
         
            final int currentRoundIndex = i / 2;
         
            final int currentRoundValue = currentRound.list().get(currentRoundIndex);
         
            final String labelText = String.valueOf(currentRoundValue);
         
            label = new JLabel(labelText, SwingConstants.CENTER);
            label.setBorder(BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY));
         
         }
         
         else //Show a gap
         {
         
            label = new JLabel();
         
         }
      
         interactionPanel.add(label);
      
      }
   
      for (int i = 0; i < upperLimit; i++)
      {
      
         final JButton button;
         final String actionCommand;
         final ActionListener actionListener;
         final int roundIndex = i;
      
         if (roundIndex % 2 == 0) //Show the value
         {
         
            button = new JButton("SPLIT");
            actionListener = _ -> gui.split(roundIndex/2);
         
         }
         
         else //Show a gap
         {
         
            button = new JButton("COMBINE");
            actionListener = _ -> gui.combine(roundIndex/2);
         
         }
      
         button.addActionListener(actionListener);
         this.GUI_COMPONENTS_TO_DEACTIVATE_WHEN_GAME_ENDS.add(button);
      
         interactionPanel.add(button);
      
      }
   
      final JButton abcButton = new JButton("XYZ");
      abcButton
         .addActionListener
         (
            _ -> 
            {
            
               final var cache = new HashMap<Round, Integer>();
               
               final var gameProgressThusFar = gui.state.get(gui.index);
            
               final var potentialSolution =
                  Algorithm.findASolutionWithTheShortestLengthPossibleRecursive(cache, gameProgressThusFar);
               
               System.out.println("-------");
               
               potentialSolution
                  .ifPresentOrElse
                  (
                     solution ->
                        solution
                           .rounds()
                           .stream()
                           .map(Round::list)
                           .forEach(System.out::println),
                     () -> System.out.println("NO SOLUTION POSSIBLE FROM THIS POINT!")
                  )
                  ;
            
            }
         )
         ;
   
      mainPanel.add(interactionPanel, BorderLayout.CENTER);
      mainPanel.add(abcButton, BorderLayout.SOUTH);
   
      return mainPanel;
   
   }

   private void split(final int index)
   {
   
      final String response = JOptionPane.showInputDialog("Choose your new left value!");
   
      if (response == null || response.isBlank())
      {
      
         return;
      
      }
   
      final int leftValue = Integer.parseInt(response);
   
      final Result result = this.state.get(this.index).split(index, leftValue);
   
      switch (result)
      {
      
         case  Failure(final String errorMessage)     -> this.POPUP.accept(errorMessage);
         case  Success(final GameProgress newState)   -> this.NEXT_ROUND.accept(newState);
      
      }
   
      if (this.state.get(this.index).hasCompleted())
      {
      
         this.POPUP.accept("You won! Took you " + (this.index) + " moves!");
         this.DEACTIVATE_GUI.run();
      
      }
   
   }

   private void combine(final int leftIndex)
   {
   
      final Result result = this.state.get(this.index).combine(leftIndex);
   
      switch (result)
      {
      
         case  Failure(final String errorMessage)     -> this.POPUP.accept(errorMessage);
         case  Success(final GameProgress newState)   -> this.NEXT_ROUND.accept(newState);
      
      }
      
      if (this.state.get(this.index).hasCompleted())
      {
      
         this.POPUP.accept("You won! Took you " + (this.index) + " moves!");
         this.DEACTIVATE_GUI.run();
      
      }
   
   }

}
