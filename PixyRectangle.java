import lejos.robotics.geometry.RectangleInt32;

public class PixyRectangle extends RectangleInt32 {

	private int signatureOrNumberMatchingBlocks;
	private int angle;
	
	public PixyRectangle(int signature, int angle, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.angle = angle;
		this.signatureOrNumberMatchingBlocks = signature;
	}
	
	public PixyRectangle(int signature, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.angle = 0;
		this.signatureOrNumberMatchingBlocks = signature;
	}
	
	/**
	 * This can be either the signature or the number of matching blocks depending on which Register call was made
	 * @return signatureOrNumberMatchingBlocks
	 */
	public int getSignatureOrNumberMatchingBlocks()
	{
		return signatureOrNumberMatchingBlocks;
	}
	
	/**
	 * Set the signature or number matching blocks variable this var can be either
	 * @param signatureOrNumberMatchingBlocks a number of whichever version you would like
	 */
	public void setSignatureOrNumberMatchingBlocks(int signatureOrNumberMatchingBlocks)
	{
		this.signatureOrNumberMatchingBlocks = signatureOrNumberMatchingBlocks;
	}
	
	/**
	 * this is only set on the main call to get maxblob and signature get
	 * @return int of the angle to the block
	 */
	public int getAngle()
	{
		return angle;
	}
	
	/**
	 * set the angle 
	 * @param angle the angle you would like to set
	 */
	public void setAngle(int angle)
	{
		this.angle = angle;
	}
}
