package com.rekindled.embers.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class BlockStateContext implements Container {

	public BlockState state;

	public BlockStateContext(BlockState state) {
		this.state = state;
	}

	//useless methods from container that I have to implement to make a recipe
	@Override
	@Deprecated
	public void clearContent() { }

	@Override
	@Deprecated
	public int getContainerSize() {
		return 0;
	}

	@Override
	@Deprecated
	public boolean isEmpty() {
		return true;
	}

	@Override
	@Deprecated
	public ItemStack getItem(int pSlot) {
		return ItemStack.EMPTY;
	}

	@Override
	@Deprecated
	public ItemStack removeItem(int pSlot, int pAmount) {
		return ItemStack.EMPTY;
	}

	@Override
	@Deprecated
	public ItemStack removeItemNoUpdate(int pSlot) {
		return ItemStack.EMPTY;
	}

	@Override
	@Deprecated
	public void setItem(int pSlot, ItemStack pStack) { }

	@Override
	@Deprecated
	public void setChanged() { }

	@Override
	@Deprecated
	public boolean stillValid(Player pPlayer) {
		return false;
	}
}
