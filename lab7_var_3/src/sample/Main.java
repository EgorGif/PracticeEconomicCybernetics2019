package sample;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Optional;
public class Main extends Application {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private TableView<Recipe> recipesTable = new TableView<>();
    private TableView<DishType> typesTable = new TableView<>();
    private static ObservableList<Recipe> recipesData = FXCollections.observableArrayList();
    private static ObservableList<DishType> typesData = FXCollections.observableArrayList();
    private final HBox addBox = new HBox(15);
    private int preferNameColumnWidth = 200;
    private int preferIngredientsColumnWidth = 300;
    private int preferComplexityColumnWidth = 150;
    private int preferCookingTimeColumnWidth = 100;
    private int preferTypeColumnWidth = 200;
    public static void main(String[] args) {
        connectionDB();
        createDB();
        launch(args);
        closeConnectionDB();
    }
    private static void connectionDB() {
        try {
            connection = null;
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:cookbook.db");
            statement = connection.createStatement();
            String selectSQL = "SELECT * FROM recipes";
            resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                Recipe recipe = new Recipe(resultSet.getString("Ф.И.О."), resultSet.getString("Возраст"), resultSet.getString("Пол"), resultSet.getString("Зарплата"), resultSet.getString("Должность"));
                recipesData.add(recipe);
            }
            selectSQL = "SELECT * FROM dishTypes";
            resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                DishType dishType = new DishType(resultSet.getString("Должность"), resultSet.getString("ID"), resultSet.getString("Описание"));
                typesData.add(dishType);
            }
        } catch (Exception e) { System.err.println(e.getMessage()); }
    }
    private static void createDB() {
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS 'dishTypes' " + "('id' INTEGER PRIMARY KEY AUTOINCREMENT,  " + "'type' TEXT, " + "'description' TEXT);");
            statement.execute("CREATE TABLE IF NOT EXISTS 'recipes' " + "('name' TEXT," + " 'ingredients' TEXT, " + " 'complexity' TEXT," + " 'cookingTime' TEXT," + " 'type' TEXT REFERENCES dishTypes(type) DEFERRABLE INITIALLY DEFERRED);");
        } catch (Exception e) { System.err.println(e.getMessage()); }
    }
    private static void closeConnectionDB() {
        try {
            resultSet.close();
            connection.close();
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new Group());
        primaryStage.setTitle("Картотека отдела кадров");
        createRecipesTable();
        createTypesTable();
        createAddBox();
        VBox allBox = new VBox(10);
        allBox.setPadding(new Insets(10, 10, 10, 10));
        allBox.getChildren().addAll(new Label("Картотека отдела кадров"), recipesTable, addBox, typesTable);
        ((Group) scene.getRoot()).getChildren().addAll(allBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void createAddBox() {
        final TextField addedName = new TextField();
        addedName.setMaxWidth(preferNameColumnWidth);
        addedName.setPromptText("Ф.И.О.");
        final TextField addedIngredients = new TextField();
        addedIngredients.setMaxWidth(preferIngredientsColumnWidth);
        addedIngredients.setPromptText("Возраст");
        final TextField addedCookingTime = new TextField();
        addedCookingTime.setMaxWidth(preferCookingTimeColumnWidth);
        addedCookingTime.setPromptText("Зарплата");
        final TextField addedType = new TextField();
        addedType.setMaxWidth(preferTypeColumnWidth);
        addedType.setPromptText("Должность");
        ObservableList<String> options = FXCollections.observableArrayList("Мужской", "Женский");
        final ComboBox<String> complexityBox = new ComboBox<>(options);
        complexityBox.setPrefWidth(preferComplexityColumnWidth + 25);
        complexityBox.setPromptText("Пол");
        final Button addRecipeButton = new Button("Добавить человека");
        addRecipeButton.setOnAction((ActionEvent e) -> {
            String name = addedName.getText();
            String ingredients = addedIngredients.getText();
            String complexity = complexityBox.getSelectionModel().getSelectedItem();
            String cookingTime = addedCookingTime.getText();
            String type = addedType.getText();
            try {
                Recipe recipe = new Recipe(name, ingredients, complexity, cookingTime, type);
                if (!typeExists(type)) {
                    String description = showAddingTypeDescriptionDialog(type);
                    DishType newType = new DishType(type, "?", description);
                    addTypeToDataBase(newType);
                    typesData.add(newType);
                }
                recipesData.add(recipe);
                addRecipeToDatabase(name, ingredients, complexity, cookingTime, type);
                addedName.clear();
                addedIngredients.clear();
                addedCookingTime.clear();
                addedType.clear();
            } catch (Exception exc) { showAddingError(exc.getMessage()); }
        });
        addBox.getChildren().addAll(addedName, addedIngredients, complexityBox, addedCookingTime, addedType, addRecipeButton);
    }
    private void showAddingError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка добавление");
        alert.setHeaderText("Невозможно добавить человека");
        alert.setContentText(message);
        alert.showAndWait();
    }
    private String showAddingTypeDescriptionDialog(String type) {
        TextInputDialog dialog = new TextInputDialog("Введите описание должности");
        dialog.setTitle("Добавление нового должности");
        dialog.setHeaderText("Вы ввели новый должности - '" + type + "'. \nПожалуйста, дайте некоторое описание: ");
        Optional<String> result = dialog.showAndWait();
        return result.orElse("Нет описания");
    }
    private void addTypeToDataBase(DishType newType) {
        String sql = "INSERT INTO dishTypes(type, description) VALUES(?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newType.getType());
            statement.setString(2, newType.getDescription());
            statement.executeUpdate();
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }
    private boolean typeExists(String newType) {
        for (DishType dishType : typesData) if (dishType.getType().equals(newType)) return true;
        return false;
    }
    private void createTypesTable() {
        TableColumn<DishType, String> typeColumn = new TableColumn<>("Должность");
        TableColumn<DishType, String> idColumn = new TableColumn<>("ID");
        TableColumn<DishType, String> descriptionColumn = new TableColumn<>("Описание");
        setTypeColumnValues(typeColumn, 200, "Должность");
        setTypeColumnValues(idColumn, 70, "ID");
        setTypeColumnValues(descriptionColumn, 500, "Описание");
        typesTable.setItems(typesData);
        typesTable.getColumns().addAll(idColumn, typeColumn, descriptionColumn);
        typesTable.setMaxHeight(200);
    }
    private void createRecipesTable() {
        TableColumn<Recipe, String> nameColumn = new TableColumn<>("Ф.И.О.");
        TableColumn<Recipe, String> ingredientsColumn = new TableColumn<>("Возраст");
        TableColumn<Recipe, String> complexityColumn = new TableColumn<>("Пол");
        TableColumn<Recipe, String> cookingTimeColumn = new TableColumn<>("Зарплата");
        setRecipeColumnValues(nameColumn, preferNameColumnWidth, "Ф.И.О.");
        setRecipeColumnValues(ingredientsColumn, preferIngredientsColumnWidth, "Возраст");
        setRecipeColumnValues(complexityColumn, preferComplexityColumnWidth, "Пол");
        setRecipeColumnValues(cookingTimeColumn, preferCookingTimeColumnWidth, "Зарплата");
        TableColumn<Recipe, String> typeColumn = new TableColumn<>("Должность");
        setRecipeColumnValues(typeColumn, preferTypeColumnWidth, "Должность");
        recipesTable.setItems(recipesData);
        recipesTable.getColumns().addAll(nameColumn, ingredientsColumn, complexityColumn, cookingTimeColumn, typeColumn);
        recipesTable.setMaxHeight(300);
    }
    private void setTypeColumnValues(TableColumn<DishType, String> column, int width, String sqlName) {
        column.setPrefWidth(width);
        column.setCellValueFactory(param -> param.getValue().getFieldBySQL(sqlName));
    }
    private void setRecipeColumnValues(TableColumn<Recipe, String> column, int width, String sqlName) {
        column.setPrefWidth(width);
        column.setCellValueFactory(param -> param.getValue().getFieldBySQL(sqlName));
    }
    private void addRecipeToDatabase(String name, String ingredients, String complexity, String cookingTime, String type) {
        String sql = "INSERT INTO recipes(Ф.И.О., Возраст, Пол, Зарплата, Должность) VALUES(?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, ingredients);
            statement.setString(3, complexity);
            statement.setString(4, cookingTime);
            statement.setString(5, type);
            statement.executeUpdate();
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }
    public static class Recipe {
        private SimpleStringProperty name;
        //private ArrayList<SimpleStringProperty> ingredients;
        private SimpleStringProperty ingredients;
        private SimpleStringProperty complexity;
        private SimpleStringProperty cookingTime;
        private SimpleStringProperty type;
        Recipe(String name, String ingredients, String complexity, String cookingTime, String type) throws Exception {
            if (name == null || name.isEmpty()) throw new Exception("Ф.И.О. не введено");
            if (ingredients == null || ingredients.isEmpty()) throw new Exception("Возраста нет");
            if (complexity == null || complexity.isEmpty()) throw new Exception("Пол не выбран");
            if (cookingTime == null || cookingTime.isEmpty()) throw new Exception("Зарплата не введена");
            if (type == null || type.isEmpty()) throw new Exception("Должность не введена");
            this.name = new SimpleStringProperty(name);
            this.complexity = new SimpleStringProperty(complexity);
            this.cookingTime = new SimpleStringProperty(cookingTime);
            this.type = new SimpleStringProperty(type);
            this.ingredients = new SimpleStringProperty(ingredients);
            /*
            for (String ingredient : ingredients.split(",")) {
                this.ingredients.add(new SimpleStringProperty(ingredient));
            }*/
        }
        SimpleStringProperty getFieldBySQL(String sqlName) {
            SimpleStringProperty res;
            switch (sqlName) {
                case "Ф.И.О.": res = this.name; break;
                case "Пол": res = this.complexity; break;
                case "Зарплата": res = this.cookingTime; break;
                case "Должность": res = this.type; break;
                case "Возраст": res = this.ingredients; break;
                default: res = null;
            }
            return res;
        }
        public String getName() {
            return name.get();
        }
        public SimpleStringProperty nameProperty() {
            return name;
        }
        public String getIngredients() {
            return ingredients.get();
        }
        public SimpleStringProperty ingredientsProperty() {
            return ingredients;
        }
        public String getComplexity() {
            return complexity.get();
        }
        public SimpleStringProperty complexityProperty() {
            return complexity;
        }
        public String getCookingTime() {
            return cookingTime.get();
        }
        public SimpleStringProperty cookingTimeProperty() {
            return cookingTime;
        }
        public String getType() {
            return type.get();
        }
        public SimpleStringProperty typeProperty() {
            return type;
        }
    }
    public static class DishType {
        private SimpleStringProperty type;
        private SimpleStringProperty id;
        private SimpleStringProperty description;
        DishType(String type, String id, String description) {
            this.type = new SimpleStringProperty(type);
            this.id = new SimpleStringProperty(id);
            this.description = new SimpleStringProperty(description);
        }
        SimpleStringProperty getFieldBySQL(String sqlName) {
            SimpleStringProperty res;
            switch (sqlName) {
                case "Должность": res = this.type; break;
                case "ID": res = this.id; break;
                case "Описание": res = this.description; break;
                default: res = null;
            }
            return res;
        }
        public String getType() { return type.get(); }
        public SimpleStringProperty typeProperty() {
            return type;
        }
        public String getId() {
            return id.get();
        }
        public SimpleStringProperty idProperty() {
            return id;
        }
        public String getDescription() {
            return description.get();
        }
        public SimpleStringProperty descriptionProperty() {
            return description;
        }
    }
}