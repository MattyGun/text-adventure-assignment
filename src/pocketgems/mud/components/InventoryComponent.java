package pocketgems.mud.components;

import java.util.HashSet;

/*
 * RoomComponent
 * =============
 * Room entities should have a RoomComponent, which allows them to contain inhabitants and exits to
 * other rooms.
 */
public class InventoryComponent extends Component {
	public HashSet<String> itemIds;
	
	public InventoryComponent() {
		itemIds = new HashSet<String>();
	}
}
