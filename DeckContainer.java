import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLayeredPane;

/**
 * kortu komponentu konteineris
 */


public class DeckContainer extends JLayeredPane {
	private static final long serialVersionUID = 1L;

	private Image background;
	private final int maxCardsCount = 15;
	private List<Card> marked;

	// kortos postumis x ir y asyse
	private final int displaceX = 0;
	private final int displaceY = 20;
	private Point cardPos;

	public List<Card> getMarked() {
		return marked;
	}

	public int getMaxCardsCount() {
		return maxCardsCount;
	}

	public DeckContainer(Image image) {
		Dimension dimension = getDimension();
		setPreferredSize(dimension);
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);

		background = image;
		cardPos = new Point();

		marked = new ArrayList<Card>();
	}

	private Dimension getDimension() {
		Dimension dim = new Dimension();
		dim.width = CardDimension.width + maxCardsCount * displaceX;
		dim.height = CardDimension.height + maxCardsCount * displaceY;
		return dim;
	}

	public void init(List<Card> cards) {
		removeAll();
		add(cards);

	}

	// ikelia kortu sarasa i konteineri
	public void add(List<Card> list) {
		if (getComponentCount() > 0) {
			addToFilled(list);
		} else {
			addToEmpty(list);
		}
	}

	// uzpildo kortomis tuscia konteineri
	private void addToEmpty(List<Card> list) {
		cardPos.setLocation(0, 0);
		for (int layer = 0; layer < list.size(); layer++) {
			Card card = setCardParam(list, layer);
			add(card, new Integer(layer));
			cardPos.translate(displaceX, displaceY);
		}
	}

	// kortos iterpiamos po paskutinio konteinerio sluoksnio
	private void addToFilled(List<Card> list) {
		int highest = highestLayer();
		int listIndex = 0;
		for (int layer = highest + 1; layer < highest + 1 + list.size(); layer++) {
			Card card = setCardParam(list, listIndex++);
			add(card, new Integer(layer));
			cardPos.translate(displaceX, displaceY);
		}
	}

	// nustato kortos dydi ir padeti
	private Card setCardParam(List<Card> list, int index) {
		Card card = list.get(index);
		card.setSize(CardDimension.width, CardDimension.height);
		card.setLocation(cardPos);
		return card;
	}

	@Override
	public void paint(Graphics gr) {
		gr.drawImage(background, 0, 0, null);
		super.paint(gr);
	}

	@Override
	protected void processMouseEvent(MouseEvent e) {
		if (e.getID() == MouseEvent.MOUSE_PRESSED) {
			marked.clear();
			Component comp = getComponentAt(e.getPoint());
			if (comp instanceof Card) {
				Component mark = (Card) comp;
				int markedLayer = getMarkedLayer(mark);
				addToMarkedList(markedLayer);

				recolorMarked();
			}
		} else if (e.getID() == MouseEvent.MOUSE_RELEASED) {
			removeMarkedRecolor();
			marked.clear();
		}
	}

	private Integer getMarkedLayer(Component marked) {
		return getComponentCount() - getComponentZOrder(marked) - 1;
	}

	private void addToMarkedList(int layerOfmark) {
		for (int i = layerOfmark; i < highestLayer() + 1; i++) {
			Component[] card = getComponentsInLayer(i);
			marked.add((Card) card[0]);
		}
	}

	public void recolorMarked() {
		for (Card card : marked) {
			card.setSelection(true);
		}
	}

	public void removeMarkedRecolor() {
		for (Card card : marked) {
			card.setSelection(false);
		}
	}

}
