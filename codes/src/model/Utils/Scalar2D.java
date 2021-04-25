package model.Utils;

import java.io.Serializable;

public class Scalar2D implements Serializable {
    private int[] scalar2D = new int[2];

    // Création d'un tableau a 2 entiers
    public Scalar2D(int x, int y)
    {
        scalar2D[0]=x;
        scalar2D[1]=y;
    }

    public int[] getScalar2D() { return scalar2D; }
    public int getScalar2DCol(){ return scalar2D[0]; }
    public int getScalar2DLine(){ return scalar2D[1]; }
    public void setScalar2D(int[] pos) { this.scalar2D = pos; }
}