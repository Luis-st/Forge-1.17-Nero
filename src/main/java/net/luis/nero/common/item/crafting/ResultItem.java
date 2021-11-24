package net.luis.nero.common.item.crafting;

import java.util.concurrent.ThreadLocalRandom;

import net.luis.nero.common.util.Chance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ResultItem {
	
	protected final Item item;
	protected final int min;
	protected final int max;
	protected final Chance chance;
	
	public static final ResultItem EMPTY = new ResultItem(ItemStack.EMPTY, Chance.NULL);
	
	public ResultItem(Item item, int chance) {
		this(item, 1, chance);
	}
	
	public ResultItem(Item item, int min, int max, int chance) {
		this(item, min, max, new Chance(chance));
	}
	
	public ResultItem(Item item, int count, int chance) {
		this(item, count, count, new Chance(chance));
	}
	
	public ResultItem(ItemStack itemStack, int chance) {
		this(itemStack, new Chance(chance));
	}
	
	public ResultItem(ItemStack itemStack, Chance chance) {
		this(itemStack.getItem(), itemStack.getCount(), itemStack.getCount(), chance);
	}
	
	public ResultItem(Item item, int min, int max, Chance chance) {
		this.item = item;
		this.min = min;
		this.max = max;
		this.chance = chance;
	}
	
	public Item getItem() {
		return this.item;
	}
	
	public int getMin() {
		return this.min;
	}
	
	public int getMax() {
		return this.max;
	}
	
	public Chance getChance() {
		return this.chance;
	}

	public ItemStack getItemStack() {
		int count = this.max > this.min ? ThreadLocalRandom.current().nextInt(this.min, this.max + 1) : min;
		return new ItemStack(this.item, count);
	}

	public boolean isResult() {
		return this.chance.getChance();
	}
	
	public boolean equals(ResultItem resultStack, boolean ignoreTags, boolean ignoreChance) {
		if (resultStack.getItemStack().getItem() == this.getItemStack().getItem()) {
			if (resultStack.getItemStack().getCount() >= this.getItemStack().getCount() || ignoreTags) {
				return resultStack.getChance().equals(this.getChance()) || ignoreChance;
			}
		}
		return false;
	}

}