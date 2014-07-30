package com.thoughtworks.game_of_life.core;

import java.util.*;

import static com.thoughtworks.game_of_life.core.Location.*;

public class World {

    private final int width;
    private final int height;
    private Map<Location, Cell> cells;

    public World(int width, int height)  {
        this.width = width;
		this.height = height;
		cells = initCells();
    }

    public void randomize() {
    	Random random = new Random();
    	
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (random.nextFloat() > .7) {
			        setLiving(at(x,y));
				}
			}
		}
    }
    
    public void advance() {
        Map<Location, Cell> newCells = initCells();

        for (Location location : allWorldLocations(width, height)) {
            if (cells.get(location).willBeAlive(numberOfAliveNeighbours(location))){
                newCells.put(location, new AliveCell());
            }
        }
        cells = newCells;
    }

    public boolean isEmpty() {
        for (Cell cell: cells.values()) {
            if (cell.isAlive()){
                return false;
            }
        }
        return true;
    }

    public void setLiving(Location location) {
        cells.put(location, new AliveCell());
    }

    public boolean isAlive(Location location) {
        return cells.get(location).isAlive();
    }

    private Map<Location,Cell> initCells() {
        Map<Location, Cell> cells = new HashMap<Location, Cell>();
        for (Location location : allWorldLocations(width, height)) {
            cells.put(location, new DeadCell());
        }
        return cells;
    }

    public int numberOfAliveNeighbours(Location l) {
        int aliveNeighbours = 0;

        for (Location location : l.allNeighbours(width, height)){
            if (cells.get(location).isAlive()){
                aliveNeighbours++;
            }
        }
        return aliveNeighbours;
    }

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
