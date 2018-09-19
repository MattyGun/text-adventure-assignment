package pocketgems.mud;

import pocketgems.mud.components.DescriptionComponent;
import pocketgems.mud.components.IdentityComponent;
import pocketgems.mud.components.InventoryComponent;
import pocketgems.mud.components.LocationComponent;
import pocketgems.mud.components.PortalComponent;
import pocketgems.mud.components.RoomComponent;
import pocketgems.mud.exceptions.ComponentNotFoundException;

public abstract class EntityFactory {
	public static Entity createRoom() {
		Entity entity = new Entity();
		entity.addComponent(new IdentityComponent());
		entity.addComponent(new DescriptionComponent());
		entity.addComponent(new RoomComponent());
		return entity;
	}
	
	public static Entity createThing()
			throws ComponentNotFoundException {
		Entity entity = new Entity();
		entity.addComponent(new IdentityComponent());
		entity.addComponent(new DescriptionComponent());
		entity.addComponent(new LocationComponent());
		// Mark this thing as not in the room's inventory.
		entity.getLocationComponent().inInventory = false;
		return entity;
	}

	public static Entity createExit() {
		Entity entity = new Entity();
		entity.addComponent(new IdentityComponent());
		entity.addComponent(new DescriptionComponent());
		entity.addComponent(new PortalComponent());
		return entity;
	}
	
	public static Entity createItem()
			throws ComponentNotFoundException {
		Entity entity = new Entity();
		entity.addComponent(new IdentityComponent());
		entity.addComponent(new DescriptionComponent());
		entity.addComponent(new LocationComponent());
		// Mark this item as being in the room's inventory.
		entity.getLocationComponent().inInventory = true;
		return entity;
	}
	
	// Based on the assumption that there is only one player in a world, we are hardcoding some of the parameters here for convenience.
	public static Entity createPlayer() {
		Entity entity = new Entity();
		
		IdentityComponent identityComponent = new IdentityComponent();
		identityComponent.id = "player";
		entity.addComponent(identityComponent);

		DescriptionComponent descriptionComponent = new DescriptionComponent();
		descriptionComponent.name = "You";
		descriptionComponent.description = "You";
		entity.addComponent(descriptionComponent);
		
		entity.addComponent(new LocationComponent());
		
		entity.addComponent(new InventoryComponent());
		
		return entity;
	}
}
