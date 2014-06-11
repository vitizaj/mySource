import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

// formuoja kortu atvaizdus
// is grafinio failo

public class CardsImages {

	private List<Image> diamonds;
	private List<Image> hearts;
	private List<Image> spades;
	private List<Image> crosses;

	private int width;
	private int height;
	private BufferedImage img;

	public List<Image> getDiamonds() {
		return diamonds;
	}

	public List<Image> getHearts() {
		return hearts;
	}

	public List<Image> getCrosses() {
		return crosses;
	}

	public List<Image> getSpades() {
		return spades;
	}

	// path grafinio failo kelias
	
	public CardsImages(String path) throws IOException {
		img = ImageIO.read(new File(path));
		width = img.getWidth() / DeckParam.typeCards;
		height = img.getHeight() / DeckParam.types;
	}

	// iskerpa vienos rusies kortas
	// row iskerpamu kortu eilutes numeris

	private List<Image> clip(int row) {
		List<Image> list = new ArrayList<Image>();
		int x = 0;
		int y = (row-1) * height;
		for (int i = 1; i <= DeckParam.typeCards; i++) {
			Image subimage = img.getSubimage(x, y, width, height);
			list.add(subimage);
			x += width;
		}
		return list;
	}

	// visu kortu atvaizdu formavimas is failo atvaizdo
	
	public void makeImages() {
		int diamondsRow = 3;
		diamonds = clip(diamondsRow);
		int heartsRow = 1;
		hearts = clip(heartsRow);
		int spadesRow = 2;
		spades = clip(spadesRow);
		int crossesRow = 4;
		crosses = clip(crossesRow);
	}

	// keicia visu kortu atvaizdu dydi
	
	public void normalizeImagesSize(int width, int height) {
		diamonds = normalizeImagesByType(width, height, diamonds);
		hearts = normalizeImagesByType(width, height, hearts);
		spades = normalizeImagesByType(width, height, spades);
		crosses = normalizeImagesByType(width, height, crosses);
	}

	// keicia vieno tipo kortu atvaizdu dydi
	
	private List<Image> normalizeImagesByType(int width, int height,
			List<Image> list) {
		List<Image> normalized = new ArrayList<Image>();
		for (Image img : list) {
			Image scalled = img.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);
			normalized.add(scalled);
		}
		return normalized;
	}

}
