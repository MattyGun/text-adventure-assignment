package pocketgems.mud;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.security.auth.DestroyFailedException;

import pocketgems.mud.components.DescriptionComponent;
import pocketgems.mud.components.IdentityComponent;
import pocketgems.mud.exceptions.ComponentNotFoundException;
import pocketgems.mud.exceptions.EntityNotFoundException;

/*
 * World
 * =====
 * World contains the game state, which consists of a collection of entities. World also exposes
 * convenient and efficient methods to modify and retrieve the game state.
 */
public class World {
	private HashMap<String, Entity> entitiesById;
	private Entity player;

	public World(Entity player) {
		entitiesById = new HashMap<String, Entity>();
		this.player = player;
		AddEntity(player);
	}

	public void AddEntity(Entity entity) {
		IdentityComponent identityComponent = entity.getComponentOrNull(IdentityComponent.class);
		if (identityComponent != null) {
			entitiesById.put(identityComponent.id, entity);
		}
	}

	public Entity GetEntity(String id) throws EntityNotFoundException {
		Entity e = entitiesById.get(id);
		if (e == null) {
			throw new EntityNotFoundException(id);
		}
		return e;
	}

	public Entity GetEntity(String id, Boolean alsoCheckKeywords)
			throws EntityNotFoundException, ComponentNotFoundException {
		Entity e = entitiesById.get(id);
		if (e == null && alsoCheckKeywords) {
				e = GetEntityFromKeyword(id);
		}
		
		if (e == null) {
			throw new EntityNotFoundException(id);
		}
		return e;
	}
	
	public Entity GetEntityFromKeyword(String keyword)
			throws EntityNotFoundException, ComponentNotFoundException {
		Iterator<Map.Entry<String, Entity>> itor = entitiesById.entrySet().iterator();
		while (itor.hasNext()) {
			Map.Entry<String, Entity> pair = (Map.Entry<String, Entity>)itor.next();
			DescriptionComponent description = pair.getValue().getDescriptionComponent();
			if (description != null && description.keywords.contains(keyword)) {
				return pair.getValue();
			}
		}
		throw new EntityNotFoundException(keyword);
	}

	// Return the entity that either matches the passes in id or the entity that has a keyword that matches the id.
	public Entity GetEntityFromPlayerInventory(String id)
			throws EntityNotFoundException, ComponentNotFoundException {
		for (String invId : player.getInventoryComponent().itemIds) {
			Entity e = entitiesById.get(invId);
			// If the ID's are matching we have the expected item.
			if (id.equals(invId)) {
				return e;
			}
			if (e != null) {
				DescriptionComponent description = e.getDescriptionComponent();
				if (description != null && description.keywords.contains(id)) {
					return e;
				}
			}
		}
		return null;
	}

	public Entity GetPlayer() {
		return player;
	}
}
