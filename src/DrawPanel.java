import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

class DrawPanel extends JPanel implements MouseListener {

    private ArrayList<Card> hand;
    private ArrayList<Card> deck;
    private Rectangle button;

    public DrawPanel() {
        button = new Rectangle(147, 300, 160, 26);
        deck = Card.buildDeck();
        this.addMouseListener(this);
        hand = Card.buildHand(deck);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int card = 0;
        int x = 105;
        int y = 10;
        for (int i = 0; i < hand.size()/3; i++) {
            for (int col = 0; col < 3; col++) {
                Card c = hand.get(card);
                if (c.getHighlight()) {
                    g.drawRect(x, y, c.getImage().getWidth(), c.getImage().getHeight());
                }
                c.setRectangleLocation(x, y);
                g.drawImage(c.getImage(), x, y, null);
                x = x + c.getImage().getWidth() + 25;
                card ++;
            }
            y = y + 100;
            x = 105;
        }
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("GET NEW CARDS", 150, 320);
        g.drawRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());
    }

    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();

        if (e.getButton() == 1) {
            if (button.contains(clicked)) {
                deck = Card.buildDeck();
                hand = Card.buildHand(deck);
            }

            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipCard();
                }
            }
        }

        if (e.getButton() == 3) {
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked) && hand.get(i).getHighlight() && deck.size() != 0) {
                    Card card = Card.getCard(deck);
                    hand.set(i, card);
                }
                else if (box.contains(clicked)) {
                    hand.get(i).flipHighlight();
                }
            }
        }


    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}