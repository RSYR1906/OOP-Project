package org.sc2002.repository;

import org.sc2002.entity.Entity;
import org.sc2002.entity.LineMapper;
import org.sc2002.utils.exception.DuplicateEntityExistsException;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class Repository{

    /**
     * The list of entity objects stored in the repository.
     */
    List<Entity> listOfEntityObjects;

    protected abstract Function<Entity,String> formatter();

    protected abstract LineMapper<Entity> mapper();

    /**
     * Creates a new instance of the Repository class.
     */
    public Repository() {
        listOfEntityObjects = new ArrayList<>();
    }

    /**
     * Gets the path of the repository file.
     *
     * @return the path of the repository file
     */
    public abstract String getFilePath();

    /**
     * Gets the list of entity objects stored in the repository
     *
     * @return the list of entity objects
     */
    public List<Entity> getAll(){
        return listOfEntityObjects;
    }


    /**
     * Gets an entity object by ID
     *
     * @param entityID the ID of the entity object to find
     * @return the entity object with the given ID
     */
    public Entity getByID(String entityID) throws EntityNotFoundException {
        for (Entity entity : listOfEntityObjects) {
            if (entity.getID().equalsIgnoreCase(entityID)) {
                return entity;
            }
        }
        throw new EntityNotFoundException("No entity with ID " + entityID + " exists.");
    }

    /**
     * Checks if the repository contains an entity with the given ID.
     *
     * @param entityID the ID of the entity object to check
     * @return true if the repository contains an entity with the given ID, false otherwise
     */
    public boolean contains(String entityID) {
        try {
            getByID(entityID);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    /**
     * Adds an entity to the repository.
     *
     * @param entity the entity to add
     * @throws DuplicateEntityExistsException if an entity with the same ID already exists in the repository
     */
    public void add(Entity entity) throws DuplicateEntityExistsException {
        if (contains(entity.getID())) {
            throw new DuplicateEntityExistsException("An entity with ID " + entity.getID() + " already exists.");
        } else {
            listOfEntityObjects.add(entity);
            save();
            System.out.println("Entity saved");
        }
    }

    /**
     * Removes an entity from the repository by ID.
     *
     * @param entityID the ID of the entity to remove
     * @throws EntityNotFoundException if the entity with the given ID does not exist
     */
    public void remove(String entityID) throws EntityNotFoundException {
        listOfEntityObjects.remove(getByID(entityID));
        save();
    }

    /**
     * Checks whether the repository is empty.
     *
     * @return true if the repository is empty, false otherwise
     */
    public boolean isEmpty() {
        return listOfEntityObjects.isEmpty();
    }

    /**
     * Gets the size of the repository.
     *
     * @return the size of the repository
     */
    public int size() {
        return listOfEntityObjects.size();
    }

    /**
     * Removes all entities from this repository.
     */
    public void clear() {
        listOfEntityObjects.clear();
        save();
    }

    /**
     * Updates the specified entity in the repository.
     *
     * @param newEntity the entity to update
     * @throws EntityNotFoundException if the specified entity is not found in the repository
     */
    public void update(Entity newEntity) throws EntityNotFoundException {
        Entity oldEntity = getByID(newEntity.getID());
        listOfEntityObjects.set(listOfEntityObjects.indexOf(oldEntity), newEntity);
        save();
    }

    /**
     * Updates all entities in the repository with the specified list of entities.
     *
     * @param updatedEntities the list of entities to update
     */
    public void updateAll(List<Entity> updatedEntities) {
        listOfEntityObjects = updatedEntities;
        save();
    }

    /**
     * Loads the list of entities from the repository file.
     */
    public void load() {
        try{
            this.listOfEntityObjects = DBcsv.read(getFilePath(), mapper());
        } catch (IOException e){
            System.out.println("IOException > " + e.getMessage());
        }
    }

    /**
     * Saves the list of entities to the repository file.
     */
    public void save() {
        try{
            DBcsv.save(getFilePath(), this.listOfEntityObjects, formatter());
        } catch (IOException e){
            System.out.println("IOException > " + e.getMessage());
        }

    }
}
