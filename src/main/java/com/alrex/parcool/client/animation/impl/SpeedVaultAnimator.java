package com.alrex.parcool.client.animation.impl;

import com.alrex.parcool.client.animation.Animator;
import com.alrex.parcool.client.animation.PlayerModelRotator;
import com.alrex.parcool.client.animation.PlayerModelTransformer;
import com.alrex.parcool.common.capability.Parkourability;
import net.minecraft.entity.player.PlayerEntity;

import static com.alrex.parcool.utilities.MathUtil.lerp;
import static com.alrex.parcool.utilities.MathUtil.squaring;

public class SpeedVaultAnimator extends Animator {
	private static final int MAX_TIME = 11;

	@Override
	public boolean shouldRemoved(PlayerEntity player, Parkourability parkourability) {
		return getTick() >= MAX_TIME;
	}

	@Override
	public void rotate(PlayerEntity player, Parkourability parkourability, PlayerModelRotator rotator) {
		float phase = (getTick() + rotator.getPartial()) / MAX_TIME;
		float factor = -squaring(((getTick() + rotator.getPartial()) - MAX_TIME / 2f) / (MAX_TIME / 2f)) + 1;

		rotator
				.startBasedCenter()
				.rotateRightward(factor * 70 * (type == Type.Right ? -1 : 1))
				.End();
	}

	@Override
	public void animatePost(PlayerEntity player, Parkourability parkourability, PlayerModelTransformer transformer) {
		float phase = (getTick() + transformer.getPartialTick()) / MAX_TIME;
		float factor = -squaring(((getTick() + transformer.getPartialTick()) - MAX_TIME / 2f) / (MAX_TIME / 2f)) + 1;
		switch (type) {
			case Right:
				transformer
						.rotateLeftArm(
								(float) Math.toRadians(lerp(-45, 45, phase)),
								0,
								(float) -Math.toRadians(factor * 70)
						)
						.End();
				break;

			case Left:
				transformer
						.rotateRightArm(
								(float) Math.toRadians(lerp(-45, 45, phase)),
								0,
								(float) Math.toRadians(factor * 70)
						)
						.End();
				break;
		}
	}

	public enum Type {Right, Left}

	private Type type;

	public SpeedVaultAnimator(Type type) {
		this.type = type;
	}
}
