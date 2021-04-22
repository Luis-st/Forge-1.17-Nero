package net.luis.industry.common.inventory;

import java.util.ArrayList;
import java.util.List;

import net.luis.industry.api.inventory.IRecipeInventory;
import net.luis.industry.api.util.ItemStackList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;

public class MilestoneInventory implements IRecipeInventory {
	
	private ItemStackList input;
	private ItemStackList output;
	
	public MilestoneInventory(int sizeInput, int sizeOutput) {
		this.input = ItemStackList.withSize(sizeInput, ItemStack.EMPTY);
		this.output = ItemStackList.withSize(sizeOutput, ItemStack.EMPTY);
	}
	
	public MilestoneInventory(ItemStackList input, ItemStackList output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public ItemStackList getInput() {
		return this.input;
	}

	@Override
	public ItemStackList getOutput() {
		return this.output;
	}

	@Override
	public int sizeInput() {
		return this.input.size();
	}

	@Override
	public int sizeOutput() {
		return this.output.size();
	}
	
	public void setInputSize(int size) {
		this.input = ItemStackList.withSize(size, ItemStack.EMPTY);
	}
	
	public void setOutputSize(int size) {
		this.output = ItemStackList.withSize(size, ItemStack.EMPTY);
	}
	
	@Override
	public boolean hasEmptySlots(ItemStackList inventory) {
		
		boolean hasEmptySlots = false;
		
		for (int i = 0; i < inventory.size(); i++) {
			
			if (this.isSlotEmpty(inventory, i)) {
				
				hasEmptySlots = true;
				break;
				
			}
			
		}
		
		return hasEmptySlots;
		
	}

	@Override
	public boolean isSlotEmpty(ItemStackList inventory, int slot) {
		return inventory.get(slot).isEmpty();
	}
	
	@Override
	public ItemStack insert(int slot, ItemStack itemStack, ItemStackList inventory) {
		
		if (itemStack.isEmpty()) {
			return ItemStack.EMPTY;
		}
		
		if (slot >= 0) {
			
			ItemStack inventoryStack = this.input.get(slot);
			
			if (inventoryStack.isEmpty()) {
				
				inventory.set(slot, itemStack);
				return ItemStack.EMPTY;
				
			} else if (inventoryStack.getItem() == itemStack.getItem()) {
				
				int count = inventoryStack.getCount() + itemStack.getCount();
				
				if (inventoryStack.getCount() == inventoryStack.getMaxStackSize()) {
					
					return itemStack;
					
				} else {
					
					if (inventoryStack.getMaxStackSize() >= count) {
						
						inventory.set(slot, new ItemStack(inventoryStack.getItem(), count));
						return ItemStack.EMPTY;
						
					} else {
						
						int leftCount = count - inventoryStack.getMaxStackSize();
						Item leftItem = inventoryStack.getItem();
						inventory.set(slot, new ItemStack(inventoryStack.getItem(), inventoryStack.getMaxStackSize()));
						return new ItemStack(leftItem, leftCount);
						
					}
					
				}
				
			}
			
		} else {
			
			return this.tryInsert(itemStack, inventory);
			
		}
		
		return ItemStack.EMPTY;
		
	}
	
	@Override
	public ItemStack insertAll(int slot, List<ItemStack> itemStacks, ItemStackList inventory) {
		
		ItemStack leftStack = ItemStack.EMPTY;
		
		for (ItemStack itemStack : inventory) {
			
			leftStack = this.insert(slot, itemStack, inventory);
			
			if (!leftStack.isEmpty()) {
				
				break;
				
			}
			
		}
		
		return leftStack;
	}
	
	@Override
	public ItemStack extract(int slot, ItemStack itemStack, ItemStackList inventory) {
		
		if (slot > 0) {
			
			return this.tryExtract(slot, itemStack, inventory);
			
		}
		
		return this.tryExtractItemStack(itemStack, inventory);

	}
	
	@Override
	public  List<ItemStack> extractAll(List<ItemStack> itemStacks, ItemStackList inventory) {
		
		List<ItemStack> extractItemStacks = new ArrayList<ItemStack>();
		
		for (int i = 0; i < itemStacks.size(); i++) {
			
			for (int j = 0; j < inventory.size(); j++) {
				
				ItemStack itemStack = itemStacks.get(i);
				ItemStack inventoryStack = inventory.get(j);
				
				if (this.equalsItemStack(itemStack, inventoryStack, false)) {
					
					extractItemStacks.add(inventoryStack);
					inventory.setDefault(j);
					
				}
				
			}
			
		}
		
		return extractItemStacks;
		
	}
	
	protected ItemStack tryInsert(ItemStack itemStack, ItemStackList inventory) {
		
		ItemStack inventoryStack = ItemStack.EMPTY;
		
		for (int i = 0; i < inventory.size(); i++) {
			
			inventoryStack = inventory.get(i);
			
			if (inventoryStack.isEmpty()) {
				
				inventory.set(i, itemStack);
				return ItemStack.EMPTY;
				
			} else if (inventoryStack.getItem() == itemStack.getItem()) {
				
				int count = inventoryStack.getCount() + itemStack.getCount();
				
				if (inventoryStack.getCount() == inventoryStack.getMaxStackSize()) {
					
					continue;
					
				} else {
					
					if (inventoryStack.getMaxStackSize() >= count) {
						
						inventory.set(i, new ItemStack(inventoryStack.getItem(), count));
						return ItemStack.EMPTY;
						
					} else {
						
						Item leftItem = inventoryStack.getItem();
						int leftCount = count - inventoryStack.getMaxStackSize();
						inventory.set(i, new ItemStack(inventoryStack.getItem(), inventoryStack.getMaxStackSize()));
						itemStack = new ItemStack(leftItem, leftCount);
						
					}
					
				}
				
			}
			
		}
		
		return itemStack;
		
	}
	
	protected ItemStack tryExtract(int slot, ItemStack itemStack, ItemStackList inventory) {
		
		for (int i = 0; i < inventory.size(); i++) {
			
			ItemStack inventoryStack = inventory.get(i);
			
			if (!inventoryStack.isEmpty()) {
				
				if (i == slot) {
					
					if (itemStack.getCount() >= inventoryStack.getCount()) {
						
						inventory.setDefault(slot);
						return inventoryStack;
						
					} else {
						
						Item item = inventoryStack.getItem();
						int leftCount = inventoryStack.getCount() - itemStack.getCount();
						inventory.set(i, new ItemStack(item, leftCount));
						return new ItemStack(item, itemStack.getCount());
						
					}
					
				}
				
			}
			
		}
		
		return ItemStack.EMPTY;
		
	}
	
	protected ItemStack tryExtractItemStack(ItemStack itemStack, ItemStackList inventory) {
		
		for (int i = 0; i < inventory.size(); i++) {
			
			ItemStack inventoryStack = inventory.get(i);
			
			if (!itemStack.isEmpty() && this.equalsItemStack(itemStack, inventoryStack, true)) {
				
				if (itemStack.getCount() >= inventoryStack.getCount()) {
					
					inventory.setDefault(i);
					return inventoryStack;
					
				} else {
					
					Item item = inventoryStack.getItem();
					int leftCount = inventoryStack.getCount() - itemStack.getCount();
					inventory.set(i, new ItemStack(item, leftCount));
					return new ItemStack(item, itemStack.getCount());
					
				}
				
			}
			
		}
		
		return ItemStack.EMPTY;
		
	}
	
	private boolean equalsItemStack(ItemStack itemStack, ItemStack toCheck, boolean ignoreTags) {
		
		if (itemStack.getItem() == toCheck.getItem()) {
			
			return toCheck.getCount() >= itemStack.getCount() || ignoreTags;
			
		}
		
		return false;
	}
	
	@Override
	public void clearInput() {
		this.input.clear();
	}

	@Override
	public void clearOutput() {
		this.output.clear();
	}

	@Override
	public ItemStackList clearAndGetInput() {
		ItemStackList tempList = this.getInput();
		this.clearInput();
		return tempList;
	}

	@Override
	public ItemStackList clearAndGetOutput() {
		ItemStackList tempList = this.getOutput();
		this.clearOutput();
		return tempList;
	}

	@Override
	public CompoundNBT serializeNBT(CompoundNBT nbt) {
		
		ListNBT inputList = new ListNBT();
		ListNBT outputList = new ListNBT();
		
		for (int i = 0; i < this.input.size(); i++) {
			
			if (!this.input.get(i).isEmpty()) {
				
				CompoundNBT itemTag = new CompoundNBT();
				itemTag.putInt("slot", i);
				input.get(i).save(itemTag);
				inputList.add(itemTag);
				
			}
			
		}
		
		for (int i = 0; i < this.output.size(); i++) {
			
			if (!this.output.get(i).isEmpty()) {
				
				CompoundNBT itemTag = new CompoundNBT();
				itemTag.putInt("slot", i);
				output.get(i).save(itemTag);
				outputList.add(itemTag);
				
			}
			
		}
		
		nbt.put("input", inputList);
		nbt.putInt("inputSize", input.size());
		nbt.put("output", outputList);
		nbt.putInt("outputSize", output.size());
		
		return nbt;
		
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		
		this.setInputSize(nbt.getInt("inputSize"));
		this.setOutputSize(nbt.getInt("outputSize"));
		ListNBT inputList = nbt.getList("input", Constants.NBT.TAG_COMPOUND);
		ListNBT outputList = nbt.getList("input", Constants.NBT.TAG_COMPOUND);
		
		for (int i = 0; i < inputList.size(); i++) {
			
			CompoundNBT itemTags = inputList.getCompound(i);
			int slot = itemTags.getInt("slot");

			if (slot >= 0 && slot < this.input.size()) {
				
				this.input.set(slot, ItemStack.of(itemTags));
				
			}
			
		}
		
		for (int i = 0; i < outputList.size(); i++) {
			
			CompoundNBT itemTags = outputList.getCompound(i);
			int slot = itemTags.getInt("slot");

			if (slot >= 0 && slot < this.output.size()) {
				
				this.output.set(slot, ItemStack.of(itemTags));
				
			}
			
		}
		
	}

}