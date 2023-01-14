import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class View extends Pane {

    private Model model;
    private TextField groupField;
    private TextField memberField;
    private Button favoriteButton;
    private Button deleteButton;
    private Button newCardButton;
    private Button saveCollectionButton;
    private Button loadCollectionButton;
    private Button editCardButton;
    private ListView<Card> cardList;
    private CheckBox favoriteCheck;
    private Label infoLabel;
    private ImageView imageView1;

    public TextField getGroupField() {
        return groupField;
    }

    public TextField getMemberField() {
        return memberField;
    }

    public Button getFavoriteButton() {
        return favoriteButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getNewCardButton() {
        return newCardButton;
    }

    public Button getSaveCollecButton() {
        return saveCollectionButton;
    }

    public Button getLoadCollectiButton() {
        return loadCollectionButton;
    }

    public Button getEditButton() {
        return editCardButton;
    }

    public ListView<Card> getCardList() {
        return cardList;
    }

    public CheckBox getFavoritBox() {
        return favoriteCheck;
    }

    public View(Model model) {
        this.model = model;

        // labels
        Label titleLabel = new Label("Kool Kpop Kard Kollection (づ｡◕‿‿◕｡)づ");
        titleLabel.setFont(new Font("Comic Sans MS", 45));
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        titleLabel.relocate(50, 50);
        titleLabel.setPrefSize(850, 100);

        Label favoritesLabel = new Label("Favorites");
        favoritesLabel.relocate(300, 205);

        Label filterLabel = new Label("Filters: ");
        filterLabel.relocate(25, 205);

        infoLabel = new Label();
        infoLabel.relocate(700, 600);

        // Text Fields
        groupField = new TextField();
        groupField.setPromptText("Group");
        groupField.relocate(75, 200);
        groupField.setPrefSize(100, 25);

        memberField = new TextField();
        memberField.setPromptText("Member");
        memberField.relocate(175, 200);
        memberField.setPrefSize(100, 25);

        // Buttons
        favoriteButton = new Button("Favorite");
        favoriteButton.relocate(425, 600);
        favoriteButton.setPrefSize(100, 50);

        deleteButton = new Button("Delete");
        deleteButton.relocate(525, 600);
        deleteButton.setPrefSize(100, 50);

        newCardButton = new Button("New Card");
        newCardButton.relocate(425, 650);
        newCardButton.setPrefSize(100, 50);

        editCardButton = new Button("Edit Card");
        editCardButton.relocate(525, 650);
        editCardButton.setPrefSize(100, 50);

        saveCollectionButton = new Button("Save Collection");
        saveCollectionButton.relocate(450, 200);
        saveCollectionButton.setPrefSize(150, 25);

        loadCollectionButton = new Button("Load Collection");
        loadCollectionButton.relocate(450, 225);
        loadCollectionButton.setPrefSize(150, 25);

        // List View
        cardList = new ListView<>();
        cardList.relocate(50, 250);
        cardList.setPrefSize(350, 450);

        // CheckBox
        favoriteCheck = new CheckBox();
        favoriteCheck.relocate(350, 190);
        favoriteCheck.setPrefSize(50, 50);

        // Image
        Image image = new Image("Photos/Title.jpg");
        imageView1 = new ImageView();
        imageView1.setImage(image);
        imageView1.setFitHeight(400);
        imageView1.setFitWidth(300);
        imageView1.relocate(650, 200);

        getChildren().addAll(titleLabel, favoritesLabel, filterLabel, groupField, memberField, favoriteButton,
                deleteButton, newCardButton, cardList, favoriteCheck, imageView1, saveCollectionButton, editCardButton,
                loadCollectionButton, infoLabel);
        this.setStyle("-fx-background-color: lavender;"); // dimgrey
    }

    public void updateInfo() {
        if (getCardList().getSelectionModel().getSelectedIndex() != -1) {
            List<Card> all = new ArrayList<>();
            all = model.getAllCards();
            int index = getCardList().getSelectionModel().getSelectedIndex();
            infoLabel.setText(all.get(index).getInfo());
        }
    }

    public void updateImage() {
        if (getCardList().getSelectionModel().getSelectedIndex() != -1) {
            List<Card> all = new ArrayList<>();
            all = model.getAllCards();
            int index = getCardList().getSelectionModel().getSelectedIndex();
            // Image updatedImage = new Image(all.get(index).getImage());
            Image updatedImage = all.get(index).getImg();
            imageView1.setImage(updatedImage);
            imageView1.setRotate(90);
            imageView1.setFitHeight(300);
            imageView1.setFitWidth(400);
            imageView1.relocate(625, 200);

        }
    }

    public void update() {
        if (model.getAllCards() != null) {
            cardList.setItems(FXCollections.observableArrayList(new ArrayList<>()));
        }

        // Favorite Cards
        if (favoriteCheck.isSelected()) {
            if (getGroupField().getText().isEmpty() && getMemberField().getText().isEmpty()) {
                cardList.setItems(FXCollections.observableArrayList(model.getAllFavCards()));
            } else if (!getGroupField().getText().isEmpty() && getMemberField().getText().isEmpty()) {
                if (model.getGroupFavCards(getGroupField().getText()) != null) {
                    cardList.setItems(
                            FXCollections.observableArrayList(model.getGroupFavCards(getGroupField().getText())));
                } else {
                    return;
                }
            } else if (!getGroupField().getText().isEmpty() && !getMemberField().getText().isEmpty()) {
                if (model.getMemberFavCards(getGroupField().getText(), getMemberField().getText()) != null) {
                    cardList.setItems(FXCollections.observableArrayList(
                            model.getMemberFavCards(getGroupField().getText(), getMemberField().getText())));
                } else {
                    return;
                }
            }
            // All Cards
        } else {
            if (getGroupField().getText().isEmpty() && getMemberField().getText().isEmpty()) {
                cardList.setItems(FXCollections.observableArrayList(model.getAllCards()));
            } else if (!getGroupField().getText().isEmpty() && getMemberField().getText().isEmpty()) {
                if (model.getGroupCards(getGroupField().getText()) != null) {
                    cardList.setItems(
                            FXCollections.observableArrayList(model.getGroupCards(getGroupField().getText())));
                } else {
                    return;
                }
            } else if (!getGroupField().getText().isEmpty() && !getMemberField().getText().isEmpty()) {
                if (model.getMembeCards(getGroupField().getText(), getMemberField().getText()) != null) {
                    cardList.setItems(FXCollections.observableArrayList(
                            model.getMembeCards(getGroupField().getText(), getMemberField().getText())));
                }
            }
        }
    }
}
