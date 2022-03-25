
package BookOfCook;

import java.util.*;
import java.io.*;

public class Cookbook implements recipeContainer {
    private String name;
    private int recipeAmount;
    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    private ArrayList<Category> categories = new ArrayList<Category>();

    // *CONSTRUCTOR
    public Cookbook(String name) {
        setName(name);
        this.recipeAmount = 0;                                                          // amount of recipes in cookbook is always 0 at start
    }

    // *RECIPE ADD AND REMOVE METHODS
    // adds recipe to cookbook
    public void addRecipe(Recipe recipe) {
        duplicateRecipeCheck(recipe);                                                   // checks if recipe already exists
        duplicateRecipeNameCheck(recipe);                                               // checks for recipes with duplicate names
        recipes.add(recipe);                                                            // adds recipe to cookbook
        recipeAmount++;                                                                 // updates amount of recipes in cookbook
    }

    // removes recipe from cookbook and updates recipe amount
    public void removeRecipe(Recipe recipe) {
        checkIfRecipeExists(recipe);                        // checks if recipe exists
        recipes.remove(recipe);                             // removes recipe from cookbook
        recipeAmount--;                                     // updates amount of recipes in cookbook
    }

    //*SETTERS
    // sets name of cookbook
    public void setName(String name) {
        nameIsValid(name);                                  // checks if name is valid
        this.name = name;                                   // sets name of cookbook                   
    }

    // *GETTERS
    // ? er disse nødvendige
    // returns cookbook name
    public String getName() {
        return name; // returns cookbook name
    }

    // returns amount of recipes in cookbook
    public int getAmount() {
        return recipeAmount;                                // returns amount of recipes in cookbook
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;                                     // returns recipes in cookbook
    }

    public ArrayList<Category> getCategories() {
        return categories;                                  // returns categories in cookbook
    }

    // *VALIDATION METHODS
    // checks if cookbook contains recipe
    private void duplicateRecipeCheck(Recipe recipe) {
        if (recipes.contains(recipe)) {                                                             // checks if recipe already exists in cookbook
            throw new IllegalArgumentException("Recipe already exists in cookbook");                // describes problem in console and throws ERROR
        }
    }

    // checks if recipe name already exists in cookbook
    private void duplicateRecipeNameCheck(Recipe recipe){
        for (Recipe r : recipes) {                                                                      // loops through recipes in cookbook
            if (r.getName().equals(recipe.getName())) {                                                 // checks there is another recipe with identical name in// cookbook
                throw new IllegalArgumentException("Recipe with same name already exists in cookbook"); // describes problem in console;                             
            }
        }
    }


    private void checkIfRecipeExists(Recipe recipe) {
        if (!recipes.contains(recipe)) {                                                                 // checks if recipe exists in cookbook
            throw new IllegalArgumentException("Recipe does not exist in cookbook");                     // describes problem in console;
        }
    }


    private void nameIsValid(String name) {
        if (name.isEmpty()) {                                                                           // checks if name is empty
            throw new IllegalArgumentException("Name cannot be empty");                                 // describes problem in console;
        }
        // checks if name contains numbers
        if (name.matches(".*\\d+.*")) {                                                                 // checks if name contains numbers
            throw new IllegalArgumentException("Name cannot contain numbers");                           // describes problem in console;
        }

        // checks if name contains special characters
        if (name.matches(".*[^a-zA-Z0-9 ].*")) {                                                       // checks if name contains special characters
            throw new IllegalArgumentException("Name cannot contain special characters");                // describes problem in console;
        }

        // checks if name contains spaces
        if (name.contains(" ")) {                                                                      // checks if name contains spaces
            throw new IllegalArgumentException("Name cannot contain spaces");                           // describes problem in console;
        }

        //checks if name starts or ends with whitespace
        if (name.startsWith(" ") || name.endsWith(" ")) {                                              // checks if name starts or ends with whitespace
            throw new IllegalArgumentException("Name cannot start or end with whitespace");             // describes problem in console;
        }

        //? checks if name is too short
        if (name.length() < 3) {                                                                       // checks if name is too short
            throw new IllegalArgumentException("Name must be at least 3 characters long");              // describes problem in console;
        }
    }


    //! *WRITE TO FILE .txt ikke klar funksjon enda
    // write recipes in cookbook as parsed strings to file
    public void writeToFile() {
        try {                                                       // prøv å skrive filen
            FileWriter fileWriter = new FileWriter(name + ".txt");  // lager et filskriver objekt som spesifiserer at filen skal hete "navnet til cookbook".txt

            for (Recipe recipe : recipes) {                         // looper gjennom alle matoppskrifter i cookbook
                fileWriter.write(recipe.parsedRecipe());            // skriver matoppskriften til filen som en parset string gjennom parsedRecipe() metoden
            }

            fileWriter.close();                                     // lukker skriveren og indikerer at filen er ferdigskrevet.
        }catch (IOException exception) {                            // dersom det oppstår en feil ved skriving av filen fanger den opp feilen
            exception.printStackTrace();                            // skriver ut feilen til konsollen
        }
    }

    // *GET RECIPES IN CATEGORIES METHODS

    public ArrayList<Recipe> getSortedRecipes(ArrayList<Category> categories){  // categories as parameter
        ArrayList<Recipe> sortedRecipes = new ArrayList<>();                    // create an output arraylist
        for (Recipe recipe : recipes) {                                         // loops through all recipes in cookbook
            for (Category category : categories) {                              // loops through all categories
                if(recipe.getCategories().contains(category)){                  // checks if recipe has this category
                    sortedRecipes.add(recipe);                                  // if true: add recipe to sortedRecipes
                }
            }
        }
        return sortedRecipes;
    }

    public void categCollect(){
        ArrayList<Category> collectedCategories = new ArrayList<>();    // create an output arraylist

        for (Recipe recipe : recipes) {                                 // lopps through all recipes
            for (Category category : recipe.getCategories()) {          // loops through all categories in recipe
                if(!collectedCategories.contains(category)){            // if not category already collected
                    collectedCategories.add(category);                  // collect category
                    //System.out.println("collected " + category);
                }
            }
        }
    
        categories = collectedCategories;
    }

    //*RECIPE SEARCH METHODS
    // searches for recipes in cookbook with name containing searchString:
    public ArrayList<Recipe> searchRecipes(String searchString) {
        ArrayList<Recipe> searchResults = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getName().toLowerCase().contains(searchString.toLowerCase())) {
                searchResults.add(recipe);
            } 
        }
        return searchResults;
    }

    // *TOSTRING METHOD
    @Override
    public String toString() {
        return "Cookbook [categories=" + categories + ", name=" + name + ", recipeAmount=" + recipeAmount + ", recipes="
                + recipes + "]";
    }

    // *MAIN METHOD
    public static void main(String[] args) {

        Cookbook cookbook = new Cookbook("Cookbook");
        Recipe pasta_carbonara = new Recipe("pasta carbonara", 2);
        Recipe pasta_bolognese = new Recipe("pasta bolognese", 4);
        Recipe cheeseburger = new Recipe("cheeseburger", 1);
        Recipe vegetarburger = new Recipe("vegetarburger", 1);
        Recipe kyllingburger = new Recipe("kyllingburger", 2);

        Category italiensk = new Category("italiensk");
        Category burger = new Category("burger");
        Category vegetar = new Category("vegetar");
        Category kylling = new Category("kylling");

        pasta_carbonara.addCategory(italiensk);
        pasta_bolognese.addCategory(italiensk);
        cheeseburger.addCategory(burger);
        vegetarburger.addCategory(burger);
        vegetarburger.addCategory(vegetar);
        kyllingburger.addCategory(kylling);
        kyllingburger.addCategory(burger);

        cookbook.addRecipe(pasta_carbonara);
        cookbook.addRecipe(pasta_bolognese);
        cookbook.addRecipe(cheeseburger);
        cookbook.addRecipe(vegetarburger);
        cookbook.addRecipe(kyllingburger);

        ArrayList<Category> categories = new ArrayList<>(Arrays.asList(italiensk, vegetar)); // lager en arraylist med kategorier jeg vil sortere etter

        System.out.println(cookbook.getSortedRecipes(categories));
        
        System.out.println(cookbook.categories);
        cookbook.categCollect();
        System.out.println(cookbook.categories);
    }
}