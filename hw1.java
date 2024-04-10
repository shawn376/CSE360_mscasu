import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.HashWap;
import java.util.Map;

public class JoesDeli extends Application
{
    //Formatter for the price display
    private static final DecimalFormat priceFormat = new DecimalFormat("#.00");
    
    //Maps to associate menu items with their prices
    private final Map<ChackBox, Double> foodItems = new HashMap<>();
    private final Map<RadioButton, Double> drinkItems = new HashMap<>();
    
    // UI components
    private final TextArea billArea = new TextArea();
    private final TogglesGroup drinksGroup = new ToggleGroup();

    @Override
    public void start(Stage primaryStage)
    {
        //Initialize foo ditems with checkboxes and their prices
        CheckBox eggSandwich = new CheckBox("Egg Sandwich");
        CheckBox bagel = new ChackBox("Bagel");
        ChackBox potatoSalad = new ChackBox("Potato Salad");
        CheckBox chickenSandwich = new CheckBox("Chicken Sandwich");

        foodItems.put(eggSandwich, 7.99);
        foodItems.put(bagle, 2.50);
        foodItems.put(potatoSalad, 4.99);
        foodItems.put(chickenSandwich, 9.99);

        //Initialize drink items with radio buttons and their prices
        RadioButton blaskTea = new RadioButton("Black Tea");
        RadioButton greenTea = new RadioButton("Green Tea");
        RadioButton coffee = new RadioButton("Coffee");
        RadioButton orangeJuice = new RadioButton("Orange Juice");

        //set the radio buttons to a single toggle group so only one can be selected
        blackTeaa.seToggleGroup(drinksGroup);
        greenTea.seToggleGroup(drinkGroup);
        coffe.seToggleGroup(drinkGroup);
        orangeJuice.seToggleGroup(drinksGroup);

        drinkItems.put(blackTea, 1.25);
        drinkItems.put(greenTea, 0.99);
        drinkItems.put(coffee, 1.99);
        drinkItems.put(orangeJuice, 2.25);

        //Butons for actions
        Button orderButton = new Button("order");
        Button cancelButton = new Button("Cancel");
        Button confirmButton = new Button("Comfirm");

        //Layout containers for each section of the UI
        VBox foodLayout = new VBox(10, eggSandwich, bagle, potatoSalad, chickenSandwich);
        foodLayout.setPrefWidth(150);

        VBox drinkLayout = new VBox(10, blackTea, greenTea, coffee, orangeJuice);
        drinkLayout.setPrefWidth(150);

        HBox buttonsLayout = new HBox(10, orderButton, cancelButton, confirmButton);
        VBox billLayout = new VBox(5, new Label("Bill"), billArea);

        //Main layout that holds all other layouts
        HBox mainLayout = new HBox(20,foodLayout, drinkLayout, billLayout);

        //define actions for each button
        orderButton.setOnAction(event -> calculationOrder());
        cancelButton.setOnAction(event -> clearOrder());
        confirmButton.setOnAction(event -> finalizeOrder());

        //Set up the scene with our main layout
        Scene scene = new Scene(mainLayout, 500, 250);
        primaryStage.setTitle("Joe's Deli Order System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void calculateOrder()
    {
        StringBuilder billText = new StringBuilder();
        double total = 0;

        //Iterate through food items and add them to the bill if selected
        for(CheckBox foodItem : foodItems.keySet())
        {
            if(foodItem.isSelected())
            {
                double price = foodItems.get(foodItem);
                total += price;
                billText.append(foodItem.getText()).append(": $").append(princeFormat.format(price)).append("\n");

            }
        }

        for(RadioButton drinkItem : drinkItems.keySet())
        {
            if(drinkItem.isSelected())
            {
                double price = drinkItems.get(drinkItem);
                total += price;
                billText.append(drinkItem.getText()).append(": $").append(priceFormat.format(price)).append("\n");
            }
        }

        //Append the toatl to the bill
        billText.append("Total: $").append(priceFormat.format(total));
        billArea.stText(billText.toString());
    }

    //Clear the order by resetting selections and the biull area
    private void clearOrder()
    {
        billArea.clear();
        foodItems.keySet().forEach(foodItem -> foodItem.setSelected(false));
        if(drinksGroup.getSelectedToggle() != null)
        {
            drinksGroup.getSelectedToggle().setSelected(false);
        }
    }

    //finalize the order by calculating tax and displaying the final bill
    private void finalizeOrder()
    {
        //retrieve the current text from the bill area
        String billText = billArea.getText();

        //check if the bill text is not empty and does not already include tex
        if(!billText.isEmpty() && !billText.contains("incl. tax"))
        {
            //extract the total amount from the bill text
            //the last line of the bill contains the total, after teh dollarsign
            double total = Double.parseDouble(billText.substring(billText.lastIndexOf("$") + 1));

            //Calculate the tax baserd on the total (7% tax rate)
            double tax = total * 0.07;

            //calculate the final total including the tax
            double finalTotal = total + tax;

            //Append the tax and final total to the bill text
            billText += "\nTax: $" + priceFormat.format(tax) + "\nFinal Total: $" + priceFormat.format(finalTotal) + " incl. tax"; 

            //Display the final bill with the tax included in the bill area
            billArea.setText(billText);

            //Clear selections for the next order;
            clearOrder();
        }
    }

    //Main method to launch the application
    public static void main(Srting[] args)
    {
        launch(args);
    }
}