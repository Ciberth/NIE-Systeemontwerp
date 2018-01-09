package be.ugent.intec.domainmodel.invoice;

/**
 * Class containing the information about the room associated with an {@link Invoice}.
 * 
 * @author student
 *
 */
public class RoomInfo {
	
	public enum RoomType{
		SINGLE, SHARED
	}
	
	public final String roomNumber;
	public final RoomType roomType;
	
	public RoomInfo(String roomNumber, RoomType roomType) {
		super();
		this.roomNumber = roomNumber;
		this.roomType = roomType;
	}
	
	
	
}
