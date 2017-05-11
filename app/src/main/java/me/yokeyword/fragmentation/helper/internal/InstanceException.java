package me.yokeyword.fragmentation.helper.internal;

/**
 * An {@link RuntimeException} thrown in cases something went wrong inside Tower.
 * <p>
 */
public class InstanceException
		extends RuntimeException {

	public InstanceException(String detailMessage) {

		super(detailMessage);
	}
}
