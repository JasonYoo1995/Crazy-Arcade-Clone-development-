package item;

import java.util.ArrayList;
import java.util.Random;

import map.Block;
import map.ItemBlock;
import map.Map;

public class ItemConnector {
	ArrayList<ItemBlock> list;
	int items[];
	
	ItemConnector(Map map){
		items = map.items;
	}
	
	public void extractItemBlock(Map map) {
		for (int i = 0; i < map.height ; i++) {
			for (int j = 0; j < map.width; j++) {
				Block block = map.block[i][j];
				if(block!=null && block instanceof ItemBlock) {
					list.add((ItemBlock) block);
				}
			}
		}
	}
	
	public void inputItem() {
		Random rand = new Random();
		for (int i = 0; i < items[0]; i++) {
			int j = rand.nextInt(list.size());
			list.get(j).setItem(new Bubble());
			list.remove(j);
		}
		for (int i = 0; i < items[1]; i++) {
			int j = rand.nextInt(list.size());
			list.get(j).setItem(new Fluid());
			list.remove(j);
		}
		for (int i = 0; i < items[2]; i++) {
			int j = rand.nextInt(list.size());
			list.get(j).setItem(new Roller());
			list.remove(j);
		}
	}
}
