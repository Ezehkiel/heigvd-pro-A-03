package ch.heigvd.pro.a03.algorithm;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AstarTest {

    @Test
    void AstarSimpleTest() {


        //Expected resulting path (from S to E avoiding Bs)
        //(The path includes S and E)
        //      0   1   2   3   4   5   6 ...
        // 0    S   *   *   *   *   -   -
        // 1    -   B   -   B   *   -   -
        // 2    -   -   B   -   E   -   -
        // 3    -   -   -   _   -   -   -
        // 4    -   -   -   -   -   -   -
        // 5    -   -   -   -   -   -   -
        // .
        // .
        // .


        Position s = new Position(0, 0);
        Position e = new Position(2, 4);
        int[][] blocksArray = {{1,1}, {1,3}, {2,2}};
        Astar astar = new Astar(20, 12, s, e);
        astar.setBlocks(blocksArray);
        List<Position> positions =  astar.findPath();

        List<Position> expectedPositions = new LinkedList<>();
        expectedPositions.add(new Position(0,0));
        expectedPositions.add(new Position(0,1));
        expectedPositions.add(new Position(0,2));
        expectedPositions.add(new Position(0,3));
        expectedPositions.add(new Position(0,4));
        expectedPositions.add(new Position(1,4));
        expectedPositions.add(new Position(2,4));

        assert(expectedPositions.size() == positions.size());
        assertEquals(expectedPositions, positions);
    }

    @Test
    public void astarSimpleTest2(){

        //Expected resulting path (from S to E avoiding Bs)
        //(The path includes S and E)
        //      0   1   2   3   4   5   6 ...
        // 0    S   *   *   *   *   -   -
        // 1    -   B   -   B   *   -   -
        // 2    -   -   B   -   E   -   -
        // 3    -   -   -   _   -   -   -
        // 4    -   -   -   -   -   -   -
        // 5    -   -   -   -   -   -   -
        // .
        // .
        // .


        Position s = new Position(0, 0);
        Position e = new Position(2, 4);
        int[][] blocksArray = {{1,1}, {1,3}, {2,2}};
        Astar astar = new Astar(20, 12, s, e);
        astar.setBlocks(blocksArray);
        List<Position> positions =  astar.findPath();

        int rows = 6;
        int cols = 7;
        Vec2 startingPoint = new Vec2(0,0);
        Vec2 expectedTarget = new Vec2(2,4);

        List<Vec2> path = new LinkedList<>();
        for(Position p: positions) path.add(new Vec2(p.getRow(), p.getCol()));
        assertPathCorrectness(path, startingPoint, expectedTarget, 7);


    }

    private void assertPathCorrectness(List<Vec2> path, Vec2 startingPoint,
                                       Vec2 endingPoint, int expectedPathLength){
        if(startingPoint.equals(endingPoint) || areNeighbours(startingPoint, endingPoint)){
            if(expectedPathLength != 0) throw new IllegalArgumentException("expectedPathLength should be zero");
            assert(path.isEmpty());
            return;
        }

        assert(path.size() == expectedPathLength);
        assert(!path.isEmpty());
        //assert(areNeighbours(path.get(0), startingPoint));
        assert(path.get(0).equals(startingPoint));
        //assert(areNeighbours(path.get(path.size() - 1), endingPoint));
        assert(path.get(path.size() - 1).equals(endingPoint));
        Iterator<Vec2> it = path.iterator();
        Vec2 tmp = it.next();
        Vec2 tmp2;
        while(it.hasNext()){
            tmp2 = it.next();
            assert(areNeighbours(tmp, tmp2));
            tmp = tmp2;
        }
    }

    private boolean areNeighbours(Vec2 p1, Vec2 p2){
        return abs(p1.getCol() - p2.getCol()) + abs(p1.getRow() - p2.getRow()) == 1;
    }

}
