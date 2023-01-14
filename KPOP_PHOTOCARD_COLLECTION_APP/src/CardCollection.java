import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CardCollection implements Serializable{

    private String name;
    private int totalCards;
    private HashSet<Card> collectionAll;
    private HashMap<String, ArrayList<Card>> collectionGroup; // map containing a list of all cards that are by one
                                                              // group
    private HashMap<String, HashMap<String, ArrayList<Card>>> collectionMember; // map that has all of the cards of a
                                                                                // certain member of a group

    public CardCollection(String name) {
        this.name = name;
        totalCards = 0;
        collectionAll = new HashSet<>();
        collectionGroup = new HashMap<>();
        collectionMember = new HashMap<>();
    }

    public boolean addCard(Card c) {
        try {
            collectionAll.add(c);
            totalCards++;

            if (!collectionGroup.keySet().contains(c.getGroup())) {
                ArrayList<Card> groupCollection = new ArrayList<>();
                groupCollection.add(c);
                collectionGroup.put(c.getGroup(), groupCollection);

                HashMap<String, ArrayList<Card>> memberMap = new HashMap<>();
                ArrayList<Card> memberCardList = new ArrayList<>();
                memberCardList.add(c);
                memberMap.put(c.getMember(), memberCardList);
                collectionMember.put(c.getGroup(), memberMap);

            } else {
                ArrayList<Card> groupCollection = new ArrayList<>();
                groupCollection = collectionGroup.get(c.getGroup());
                groupCollection.add(c);
                collectionGroup.put(c.getGroup(), groupCollection);

                if (collectionMember.get(c.getGroup()) == null) {
                    collectionMember.put(c.getGroup(), new HashMap<String, ArrayList<Card>>());
                }
                if (!collectionMember.get(c.getGroup()).containsKey(c.getMember())) {
                    ArrayList<Card> memberCardList = new ArrayList<>();
                    memberCardList.add(c);
                    HashMap<String, ArrayList<Card>> memberMap = new HashMap<>();
                    memberMap.put(c.getMember(), memberCardList);
                    collectionMember.get(c.getGroup()).put(c.getMember(), memberCardList);
                } else {
                    if (collectionMember.get(c.getGroup()).containsKey(c.getMember())) {
                        collectionMember.get(c.getGroup()).get(c.getMember()).add(c);
                    } else {
                        ArrayList<Card> membeCardList = new ArrayList<>();
                        membeCardList.add(c);
                        collectionMember.get(c.getGroup()).put(c.getMember(), membeCardList);
                    }
                }
            }
            return true;

        } catch (Exception e) {

            return false;
        }
    }

    public boolean deleteCard(Card c) {
        try {
            collectionAll.remove(c);
            collectionGroup.get(c.getGroup()).remove(c);
            collectionMember.get(c.getGroup()).get(c.getMember()).remove(c);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Card> getAll() {
        List<Card> cards = new ArrayList<>();
        for (Card c : collectionAll) {
            cards.add(c);
        }
        Collections.sort(cards);
        return cards;
    }

    public List<Card> getAllGroup(String group) {
        if (!collectionGroup.containsKey(group)) {
            return null;
        }
        List<Card> cards = new ArrayList<>();
        for (Card c : collectionGroup.get(group)) {
            cards.add(c);
        }
        Collections.sort(cards);
        return cards;
    }

    public List<Card> getAllMember(String group, String member) {
        if (!collectionGroup.containsKey(group) || !collectionMember.get(group).containsKey(member)) {
            return null;
        }
        List<Card> cards = new ArrayList<>();
        for (Card c : collectionMember.get(group).get(member)) {
            cards.add(c);
        }
        Collections.sort(cards);
        return cards;
    }

    public boolean containsCard(Card c) {
        if (collectionAll.contains(c)) {
            return true;
        }
        return false;
    }

    public int numCards() {
        return collectionAll.size();
    }

    public String toString() {
        return name + " with " + totalCards + " cards";
    }

}
