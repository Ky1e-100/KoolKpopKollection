import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Model implements Serializable{
    
    private CardCollection allCollection;
    private CardCollection favoriteCollection;
    private List<Card> result;

    public void initialize() {
        allCollection = new CardCollection("My Photocard Collection");
        favoriteCollection = new CardCollection("Favorites");
        result = new ArrayList<>();

        File directory0 = new File("src/Photos");
        if (!directory0.exists()) {
            directory0.mkdirs();
        }
        File directory1 = new File("src/Data");
        if (!directory1.exists()) {
            directory1.mkdirs();
        }
    }

    public boolean addCard(Card c) {
        return allCollection.addCard(c);
    }

    public boolean addFavorite(Card c) {
        return favoriteCollection.addCard(c);
    }

    public boolean deleteCard(Card c) {
        if (allCollection.containsCard(c)) {
            allCollection.deleteCard(c);
            if (favoriteCollection.containsCard(c)) {
                favoriteCollection.deleteCard(c);
            }
            return true;
        }
        return false;
    }

    public List<Card> getAllCards() {
        result = allCollection.getAll();
        return getResult();
    }

    public List<Card> getGroupCards(String group) {
        result = allCollection.getAllGroup(group);
        return getResult();
    }

    public List<Card> getMembeCards(String group, String member) {
        result = allCollection.getAllMember(group, member);
        return getResult();
    }

    public List<Card> getAllFavCards() {
        result = favoriteCollection.getAll();
        return getResult();
    }

    public List<Card> getGroupFavCards(String group) {
        result = favoriteCollection.getAllGroup(group);
        return getResult();
    }

    public List<Card> getMemberFavCards(String group, String member) {
        result = favoriteCollection.getAllMember(group, member);
        return getResult();
    }

    public List<Card> getResult() {
        return result;
    }

    public int numCards() {
        return allCollection.numCards();
    }

    public void removeFavorite(Card c) {
        favoriteCollection.deleteCard(c);
    }

}
