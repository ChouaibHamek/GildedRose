package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

// Example scenarios for testing
//   Item("+5 Dexterity Vest", 10, 20));
//   Item("Aged Brie", 2, 0));
//   Item("Elixir of the Mongoose", 5, 7));
//   Item("Sulfuras, Hand of Ragnaros", 0, 80));
//   Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
//   Item("Conjured Mana Cake", 3, 6));

	@Test
	public void testGildedRose_Preserves_Quality_If_DayNotPassed() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Item0", 50, 49) );
		assertEquals(49, store.getItems().get(0).getQuality());
	}
	
	@Test
	public void testGildedRose_Preserves_SellIn_If_DayNotPassed() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Item0", 15, 49) );
		assertEquals(15, store.getItems().get(0).getSellIn());
	}
	
	@Test
	public void testUpdateEndOfDay_Normal_Items_decrease_SellIn() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Item0", 15, 29) );
		store.updateEndOfDay();
		assertEquals(14, store.getItems().get(0).getSellIn());
	}
	
	@Test
	public void testUpdateEndOfDay_Normal_Items_decreaseToZero_SellIn() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Item0", 1, 29) );
		store.updateEndOfDay();
		assertEquals(0, store.getItems().get(0).getSellIn());
	}
	
	@Test
	public void testUpdateEndOfDay_Normal_Items_degrade_Quality() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Item0", 15, 29) );
		store.updateEndOfDay();
		assertEquals(28, store.getItems().get(0).getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_Normal_Items_degradeToZero_Quality() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Item0", 15, 1) );
		store.updateEndOfDay();
		assertEquals(0, store.getItems().get(0).getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_Normal_Items_SellInPassed_2xQuality() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Item0", -1, 15) );
		store.updateEndOfDay();
		assertEquals(13, store.getItems().get(0).getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_Normal_Items_NotNegative_Quality() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Item0", 15, 0) );
		store.updateEndOfDay();
		boolean NotNegative = (store.getItems().get(0).getQuality() >= 0); 
		assertEquals(true, NotNegative);
	}
	
	@Test
	public void testUpdateEndOfDay_Normal_Items_NotMoreThanFifty_Quality() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Item0", 15, 50) );
		store.updateEndOfDay();
		boolean NotMoreThanFifty = (store.getItems().get(0).getQuality() <= 50); 
		assertEquals(true, NotMoreThanFifty);
	}
	
	@Test
	public void testUpdateEndOfDay_AgedBrie_Decrease_SellIn() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 15, 29) );
		store.updateEndOfDay();
		assertEquals(14, store.getItems().get(0).getSellIn());
	}
	
	@Test
	public void testUpdateEndOfDay_AgedBrie_Increase_Quality() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 15, 29) );
		store.updateEndOfDay();
		assertEquals(30, store.getItems().get(0).getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_Sulfuras_Decrease_SellIn() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Sulfuras", 15, 80) );
		store.updateEndOfDay();
		assertEquals(14, store.getItems().get(0).getSellIn());
	}
	
	@Test
	public void testUpdateEndOfDay_Sulfuras_Never_Sold() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Sulfuras", 3, 80) );
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		assertNotEquals(null, store.getItems().get(0));
		assertEquals("Sulfuras",store.getItems().get(0).getName());
	}

	@Test
	public void testUpdateEndOfDay_Sulfuras_Constant80_Quality() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Sulfuras", 3, 80) );
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		store.updateEndOfDay();
		assertEquals("Quality of Sulfuras not constant",80,store.getItems().get(0).getQuality());
	}

	@Test
	public void testUpdateEndOfDay_Backstage_Increase_Quality() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 29) );
		store.updateEndOfDay();
		assertEquals(30, store.getItems().get(0).getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_Backstage_Increase_2xQuality() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 8, 30) );
		store.updateEndOfDay();
		assertEquals(32, store.getItems().get(0).getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_Backstage_Increase_3xQuality() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 3, 30) );
		store.updateEndOfDay();
		assertEquals(33, store.getItems().get(0).getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_Backstage_SellInExpired_0Quality() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", -1, 30) );
		store.updateEndOfDay();
		assertEquals(0, store.getItems().get(0).getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_Normal_Items_random_0_1_Quality() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Item0", 0, 1) );
		store.updateEndOfDay();
		assertEquals(0, store.getItems().get(0).getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_Normal_Items_random_0_1_SellIn() {
		GildedRose store = new GildedRose();
		store.addItem(new Item("Item0", 0, 1) );
		store.updateEndOfDay();
		assertEquals(-1, store.getItems().get(0).getSellIn());
	}
	
	@Test
	public void testUpdateEndOfDay_AgedBrie_Quality_10_11() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 2, 10) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		assertEquals(11, itemBrie.getQuality());
	}

}
