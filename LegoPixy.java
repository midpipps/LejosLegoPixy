import lejos.hardware.Button;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.I2CSensor;

public class LegoPixy extends I2CSensor {
	
	/**
	 * Buffer for recieving data
	 */
	private byte[] buffer = new byte[7];
	
	/**
	 * Register for Pixy Version Info
	 */
	private static final byte VERSION = 0x00;
	/**
	 * Register for module name
	 */
	private static final byte MODULE_NAME = 0x08;
	/**
	 * Register for getting another module name
	 */
	private static final byte MODULE_NAME_2 = 0x10;
	/**
	 * Unknown function seems to get the left to right value
	 */
	private static final byte UNKNOWN = 0x42;
	/**
	 * Register for getting the biggest blob data
	 */
	private static final byte BIGGEST_BLOB = 0x50;
	/**
	 * register to get a blob based on a passed in signature value
	 */
	private static final byte GET_BLOB_BASED_ON_SIGNATURE = 0x58;
	/**
	 * Register for the G_ANGLE value of the last blob that was grabbed
	 */
	private static final byte G_ANGLE = 0x60;
	
	/**
	 * Constructor for lego pixy using just the sensorport and using default i2c address
	 * @param port
	 */
	public LegoPixy(Port port)
	{
		super(port, DEFAULT_I2C_ADDRESS);
	}
	
	/**
	 * Get the pixy version info
	 */
	public String getVersion()
	{
		return fetchString(VERSION, 8 ); 
	}
	
	/**
	 * get the pixy module name
	 * @return string of the pixy module name
	 */
	public String getModuleName()
	{
		return fetchString(MODULE_NAME, 8);
	}
	
	/**
	 * get the other pixy module name
	 * @return string of the pixy module name
	 */
	public String getModuleName2()
	{
		return fetchString(MODULE_NAME_2, 8);
	}
	
	/**
	 * seems to get the object position on the horizontal access
	 * @return the position of the object on the horizontal access
	 */
	public int unknown()
	{
		getData(UNKNOWN, buffer, 1);
		return buffer[0] & 0xFF;
	}
	
	/**
	 * get PixyRectangle of the biggest object
	 * @return PixyRectangle of the biggest object
	 */
	public PixyRectangle getBiggestBlob()
	{
		getData(BIGGEST_BLOB, buffer, 6);
		byte [] temp = new byte [1];
		getData(G_ANGLE, temp, 1);
		return new PixyRectangle(((buffer[0] & 0xFF) << 8)|(buffer[1] & 0xFF), (temp[0] & 0xFF), (buffer[2] & 0xFF),(buffer[3] & 0xFF), (buffer[4] & 0xFF), (buffer[5] & 0xFF));
	}
	
	/**
	 * Returns a blob from register at 0x50 + i
	 * @param i the modifier of which blob to get must be greater than 0 and less than 7
	 * @return PixyRectangle of the blob at i
	 */
	public PixyRectangle getBlobAt(int i)
	{
		if (i > 0 && i < 7)
		{
			getData(BIGGEST_BLOB + i, buffer, 5);
			return new PixyRectangle((buffer[0] & 0xFF), (buffer[1] & 0xFF), (buffer[2] & 0xFF), (buffer[3] & 0xFF), (buffer[4] & 0xFF));
		}
		return null;
	}
	
	/**
	 * Get a blob based on a signature value
	 * @param sig the signature value for the blob
	 * @return a pixyRectangle of the blob
	 */
	public PixyRectangle getBlobWithSignature(int sig)
	{
		byte [] tempBuffer = new byte[3];
		tempBuffer[0] = GET_BLOB_BASED_ON_SIGNATURE;
		tempBuffer[1] = (byte) ((buffer[0] & 0xFF) >> 8);
		tempBuffer[2] = (byte) (buffer[1] & 0xFF);
		port.i2cTransaction(address, tempBuffer, 0, 3, buffer, 0, 6);
		return new PixyRectangle((buffer[0] & 0xFF),(buffer[5] & 0xFF), (buffer[1] & 0xFF), (buffer[2] & 0xFF), (buffer[3] & 0xFF), (buffer[4] & 0xFF));
	}

}
