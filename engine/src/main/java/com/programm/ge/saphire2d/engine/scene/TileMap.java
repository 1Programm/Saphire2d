package com.programm.ge.saphire2d.engine.scene;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileMap {

    @RequiredArgsConstructor
    public static class Point {
        public final int x, y, z;
    }

    public final int width;
    public final int height;
    public final int depth;
    public final Integer[][][] tiles;

    public final int tileWidth;
    public final int tileHeight;

    public final Map<Integer, List<Point>> tilesOptimized = new HashMap<>();

    public TileMap(int width, int height, int depth, int tileWidth, int tileHeight) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.tiles = new Integer[width][height][depth];
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public void setTile(int x, int y, int z, int id){
        Integer curTile = tiles[x][y][z];
        if(curTile != null){
            if(id == curTile) return;
            List<Point> points = tilesOptimized.get(curTile);
            for(int i=0;i<points.size();i++){
                if(points.get(i).x == x && points.get(i).y == y && points.get(i).z == z){
                    points.remove(i);
                    break;
                }
            }
            if(points.isEmpty()){
                tilesOptimized.remove(curTile);
            }
        }

        tiles[x][y][z] = id;
        tilesOptimized.computeIfAbsent(id, i -> new ArrayList<>()).add(new Point(x, y, z));
    }

    public Integer getTile(int x, int y, int z){
        return tiles[x][y][z];
    }
}
