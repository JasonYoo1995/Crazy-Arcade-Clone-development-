package map;

import item.Item;

public class ItemBlock extends Block{
	Item item;
	ItemBlock(Item item){
		this.item = item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
}
