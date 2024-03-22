import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.awt.Rectangle;

public class Card {
    private String suit;
    private String value;
    private String imageFileName;
    private String backImageFileName;
    private boolean show;
    private BufferedImage image;
    private Rectangle cardBox;
    private boolean highlight;

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
        this.imageFileName = "images/card_"+suit+"_"+value+".png";
        this.show = true;
        this.backImageFileName = "images/card_back.png";
        this.image = readImage();
        this.cardBox = new Rectangle(-100, -100, image.getWidth(), image.getHeight());
        this.highlight = false;
    }

    public Rectangle getCardBox() {
        return cardBox;
    }

    public String getSuit() {
        return suit;
    }

    public void setRectangleLocation(int x, int y) {
        cardBox.setLocation(x, y);
    }

    public String getValue() {
        return value;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public String toString() {
        return suit + " " + value;
    }

    public void flipCard() {
        show = !show;
        this.image = readImage();
    }

    public void flipHighlight() {
        highlight = !highlight;
    }

    public boolean getHighlight() {
        return highlight;
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage readImage() {
        try {
            BufferedImage image;
            if (show) {
                image = ImageIO.read(new File(imageFileName));
            }
            else {
                image = ImageIO.read(new File(backImageFileName));
            }
            return image;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public static ArrayList<Card> buildDeck() {
        ArrayList<Card> deck = new ArrayList<Card>();
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        String[] values = {"02", "03", "04", "05", "06", "07", "08", "09", "10", "A", "J", "K", "Q"};
        for (String s : suits) {
            for (String v : values) {
                Card c = new Card(s, v);
                deck.add(c);
            }
        }
        return deck;
    }

    public static boolean hasPossibleMoves(ArrayList<Card> hand) {
        for (int currNum = 0; currNum < hand.size(); currNum ++) {
            for(int num = 0; num < hand.size(); num ++) {
                if (!(num == currNum)) {
                    int sum = getIntValue(hand.get(currNum)) + getIntValue(hand.get(num));
                    if (sum == 11) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //returns an int value, if the value is J, Q, or K return 0
    public static int getIntValue(Card card){
        if (card.getValue().equals("A")) {
            return 1;
        }
        if (card.getValue().equals("02")) {
            return 2;
        }
        if (card.getValue().equals("03")) {
            return 3;
        }
        if (card.getValue().equals("04")) {
            return 4;
        }
        if (card.getValue().equals("05")) {
            return 5;
        }
        if (card.getValue().equals("06")) {
            return 6;
        }
        if (card.getValue().equals("07")) {
            return 7;
        }
        if (card.getValue().equals("08")) {
            return 8;
        }
        if (card.getValue().equals("09")) {
            return 9;
        }
        if (card.getValue().equals("10")) {
            return 10;
        }

        return 0;
    }

    public static Card getCard(ArrayList<Card> deck) {
        int randomNum = (int) (Math.random() * deck.size());
        return deck.remove(randomNum);
    }
    public static ArrayList<Card> buildHand(ArrayList<Card> deck) {
        ArrayList<Card> hand = new ArrayList<Card>();
        for (int i = 0; i < 9; i++) {
            int r = (int)(Math.random()*deck.size());
            Card c = deck.remove(r);
            hand.add(c);
        }
        return hand;
    }

    public static ArrayList<Integer> submit(ArrayList<Card> hand) {
        int sum = 0;
        ArrayList<Integer> index = new ArrayList<Integer>();
        for (int i = 0; i < hand.size(); i ++) {
            if (hand.get(i).getHighlight()) {
                sum += getIntValue(hand.get(i));
                index.add(i);
            }
        }
        if (sum == 11 && index.size() == 2) {
            return index;
        }
        if (sum == 0 && index.size() == 3) {
            if (checkJQK(hand, index)) return index;
        }
        index.clear();
        return index;
    }

    public static boolean checkJQK(ArrayList<Card> hand, ArrayList<Integer> index) {
        boolean J = false;
        boolean Q = false;
        boolean K = false;
        if (hand.get(index.get(0)).getValue().equals("J") && !J) J = true;
        if (hand.get(index.get(0)).getValue().equals("Q") && !Q) Q = true;
        if (hand.get(index.get(0)).getValue().equals("K") && !K) K = true;
        if (hand.get(index.get(1)).getValue().equals("J") && !J) J = true;
        if (hand.get(index.get(1)).getValue().equals("Q") && !Q) Q = true;
        if (hand.get(index.get(1)).getValue().equals("K") && !K) K = true;
        if (hand.get(index.get(2)).getValue().equals("J") && !J) J = true;
        if (hand.get(index.get(2)).getValue().equals("Q") && !Q) Q = true;
        if (hand.get(index.get(2)).getValue().equals("K") && !K) K = true;

        if (J && Q && K) return true;
        return false;
    }
}
