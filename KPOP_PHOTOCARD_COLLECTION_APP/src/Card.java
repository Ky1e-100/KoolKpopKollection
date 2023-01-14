import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Card implements Serializable, Comparable<Card>{

    private String name;
    private String member;
    private String group;
    private String album;
    private int stock;
    private File image;
    private transient Image img;

    public Card(String name, String member, String group, String album, int stock, File image) {
        this.name = name;
        this.member = member;
        this.group = group;
        this.album = album;
        this.stock = stock;
        this.image = image;
        img = new Image(image.getAbsolutePath());
    }

    public String getName() {
        return name;
    }

    public String getMember() {
        return member;
    }

    public String getGroup() {
        return group;
    }

    public String getAlbum() {
        return album;
    }

    public int getStock() {
        return stock;
    }

    public String getImage() {
        return image.getAbsolutePath();
    }

    public Image getImg() {
        return img;
    }

    public void editCard(String name, String member, String group, String album, int stock, File image) {
        this.name = name;
        this.member = member;
        this.group = group;
        this.album = album;
        this.stock = stock;
        this.image = image;
    }

    public String getInfo() {
        return "Card info\nName: " + name + "\nGroup: " + group + "\nAlbum: " + album + "\nMember Name: " + member
                + "\nNumber of Cards: " + stock;
    }

    public int compareTo(Card c) {
        return this.getGroup().compareTo(c.getGroup());
    }

    public String toString() {
        return name;
    }


    //Bottom two read/write methods from:https://stackoverflow.com/questions/48973656/javafx-save-an-object-with-an-image-property-in-a-binary-file
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeBoolean(img != null);
        if (img != null) {
            ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", out);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (in.readBoolean()) {
            img = new Image(in);
        } else {
            img = null;
        }
    }

}
