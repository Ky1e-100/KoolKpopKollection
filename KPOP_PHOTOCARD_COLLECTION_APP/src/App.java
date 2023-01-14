import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class App extends Application {
    private Model model;
    private View view;
    private Stage stage;
    private ExtensionFilter pictures;
    private ExtensionFilter loadFile;

    public void start(Stage primaryStage) {
        stage = primaryStage;
        model = new Model();
        view = new View(model);
        pictures = new ExtensionFilter("Images (.JPG, .PNG, .GIF)", "*.JPG", "*.PNG", "*.GIF");
        loadFile = new ExtensionFilter("Kpop Kard Kollection (.kkk)", "*.kkk");

        model.initialize();

        primaryStage.setTitle("Kool Kpop Kard Kollection (づ｡◕‿‿◕｡)づ");
        primaryStage.setScene(new Scene(view, 1000, 800));
        primaryStage.show();

        actions(view, primaryStage);

        view.update();
    }

    public void handleNewCard() {
        String name = javax.swing.JOptionPane.showInputDialog("Photocard name: ");
        String group = javax.swing.JOptionPane.showInputDialog("Group name: ");
        String member = javax.swing.JOptionPane.showInputDialog("Member name: ");
        String Album = javax.swing.JOptionPane.showInputDialog("Album name: ");
        int stock = Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Number of copies: "));

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open photocard Image");
        fileChooser.getExtensionFilters().addAll(pictures);
        File image = fileChooser.showOpenDialog(stage);
        if (image == null) {
            return;
        }

        BufferedImage imageOut = null;
        try {
            imageOut = ImageIO.read(image);
        } catch (IOException e) {
            return;
        }

        File newFile = null;
        try {
            newFile = new File("src/Photos/" + name + member + ".jpg");
            ImageIO.write(imageOut, "jpg", newFile);
        } catch (IOException e) {
            return;
        }

        Card c = new Card(name, member, group, Album, stock, newFile);
        model.addCard(c);
        view.update();

    }

    public void handleEdit() {
        handleDelete();
        handleNewCard();
    }

    public void handleDelete() {
        List<Card> all = new ArrayList<>();
        all = model.getAllCards();
        int index = view.getCardList().getSelectionModel().getSelectedIndex();
        model.deleteCard(all.get(index));
        view.update();
    }

    public void handleFavorite() {
        List<Card> all = new ArrayList<>();
        all = model.getAllCards();
        int index = view.getCardList().getSelectionModel().getSelectedIndex();
        if (!model.getAllFavCards().contains(all.get(index))) {
            model.addFavorite(all.get(index));
        } else {
            model.removeFavorite(all.get(index));
        }

        view.update();
    }

    public void handleSaveCollection() {
        String name = javax.swing.JOptionPane.showInputDialog("Collection name:  ");

        ObjectOutputStream save;
        try {
            save = new ObjectOutputStream(
                    new FileOutputStream("src" + File.separator + "Data" + File.separator + name + ".kkk"));
            save.writeObject(model);
            save.close();
        } catch (FileNotFoundException e) {
            return;
        } catch (IOException e) {
            return;
        }

    }

    public void actions(View a, Stage primaryStage) {

        a.getGroupField().setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                view.update();
            }
        });

        a.getMemberField().setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                view.update();
            }
        });

        a.getNewCardButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleNewCard();
            }
        });

        a.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleDelete();
            }
        });

        a.getFavoriteButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleFavorite();
            }
        });

        a.getEditButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleEdit();
            }
        });

        a.getSaveCollecButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleSaveCollection();
            }
        });

        a.getCardList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                a.updateInfo();
                a.updateImage();
            }
        });

        a.getLoadCollectiButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Collection");
                fileChooser.getExtensionFilters().addAll(loadFile);
                File load = fileChooser.showOpenDialog(stage);
                if (load == null) {
                    return;
                }

                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(load));
                    Model loadCol = (Model) in.readObject();
                    in.close();
                    model = loadCol;
                    view = new View(loadCol);
                    primaryStage.setScene(new Scene(view, 1000, 800));
                    view.update();
                    actions(view, primaryStage);

                } catch (ClassNotFoundException e) {
                    System.out.println("error 1");
                } catch (FileNotFoundException e) {
                    System.out.println("error 2");
                } catch (IOException e) {
                    System.out.println(e);

                }
            }
        });

        a.getFavoritBox().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                view.update();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
