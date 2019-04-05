package ch.heigvd.pro.a03.algorithm;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AstarTest {

    @Test
    void AstarSimpleTest() {


        //Expected resulting path (from S to E avoiding Bs)
        //      0   1   2   3   4   5   6
        // 0    S   *   *   *   *   -   -
        // 1    -   B   -   B   *   -   -
        // 2    -   -   B   -   E   -   -
        // 3    -   -   -   _   -   -   -
        // 4    -   -   -   -   -   -   -
        // 5    -   -   -   -   -   -   -


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

}
