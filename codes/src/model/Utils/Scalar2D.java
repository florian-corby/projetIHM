package model.Utils;

public class Scalar2D {
    private int[] scalar2D = new int[2];

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