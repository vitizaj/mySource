import java.util.List;

/**
 * realizuoja perkelimo logika i kortu konteinerius
 */
public class ToDeckLogic {

	private DestContainer[] tempCont;

	public ToDeckLogic(DestContainer[] tempCont) {
		this.tempCont = tempCont;
	}

	// patikrina ar makred korta gali dengti dest korta
	private boolean sourceCoverDest(Card source, Card dest) {
		String destType = dest.getType();
		String sourceType = source.getType();

		// galimi kortu deriniai
		// juoda dengia raudona korta arba raudona dengia juoda
		boolean firstRedSecondBlack = firstRedSecondBlack(destType, sourceType);
		boolean firstBlackSecondRed = firstBlackSecondRed(destType, sourceType);

		if (firstRedSecondBlack || firstBlackSecondRed) {
			return CardsComparator.sourceLowerThanDest(source, dest);
		}

		// negalimi kortu deriniai
		return false;
	}

	private boolean diamondsOrHearts(String destType) {
		return destType.equals("diamonds") || destType.equals("hearts");
	}

	private boolean spadessOrCrosses(String markedType) {
		return markedType.equals("spades") || markedType.equals("crosses");
	}

	private boolean firstRedSecondBlack(String destType, String sourceType) {
		return diamondsOrHearts(destType) && spadessOrCrosses(sourceType);
	}

	private boolean firstBlackSecondRed(String destType, String sourceType) {
		return spadessOrCrosses(destType) && diamondsOrHearts(sourceType);
	}

	
	// patikrina ar galima perkelti kortas is markedCon konteinerio i destCon
	public boolean fromDeckContToDeckCont(DeckContainer markedCont,
			DeckContainer DeckCont) {

		List<Card> marked = markedCont.getMarked();
		if (marked.size() == 0)
			return false;

		if (compareFreeCellsWithMarked(marked.size(), getFreeCells())) {
			boolean canMoveCards = canMoveCards(marked, DeckCont);
			boolean overfull = canOverfull(marked, DeckCont);
			return canMoveCards && !overfull;
		}
		return false;
	}

	private int getFreeCells() {
		int free = 0;
		for (DestContainer cell : tempCont) {
			if (cell.getComponentCount() == 0)
				free++;
		}
		return free;
	}

	// ar galima perkelti pazymetas kortas i kortu konteineri
	private boolean canMoveCards(List<Card> marked, DeckContainer deckCont) {
		// kortu paskirties konteineris turi kortu
		if (deckCont.getComponentCount() > 0) {
			int highest = deckCont.highestLayer();
			Card lastOfDest = (Card) deckCont.getComponent(highest);
			Card firstOfMarked = marked.get(0);
			return sourceCoverDest(firstOfMarked, lastOfDest);
		}

		return true;
	}

	// tikriname ar gali kortu konteineris persipildyti
	private boolean canOverfull(List<Card> marked, DeckContainer deckCont) {
		return marked.size() > deckCont.getMaxCardsCount()
				- deckCont.getComponentCount();
	}

	// is vieno kortu konteinerio i kita leidziama parkelti
	// 1 korta daugiau negu yra laisvu lasteliu
	private boolean compareFreeCellsWithMarked(int markedCount, int freeCells) {
		return markedCount <= freeCells + 1;
	}

	
	
	public boolean fromTempContToDeckCont(DestContainer temp,
			DeckContainer deckCont) {
		if (temp.getComponentCount() == 1) {
			boolean notFull = isDeckContNotFull(deckCont);
			boolean canMoveCard = canMoveCard(deckCont, temp.getCard());
			return notFull && canMoveCard;
		}
		return false;
	}

	// ar galima perkelti viena korta i kortu konteineri
	private boolean canMoveCard(DeckContainer deckCont, Card card) {
		if (deckCont.getComponentCount() > 0) {
			int highest = deckCont.highestLayer();
			Card lastOfDest = (Card) deckCont.getComponent(highest);
			return sourceCoverDest(card, lastOfDest);
		}
		return true;
	}

	private boolean isDeckContNotFull(DeckContainer deckCont) {
		return deckCont.getMaxCardsCount() != deckCont.getComponentCount();
	}

}
