package model.Utils;

public class Pos2D {
    private int[] pos = new int[2];

    public Pos2D(int x, int y)
    {
        pos[0]=x;
        pos[1]=y;
    }

    public int[] getPos() { return pos; }
    public int getPosX(){ return pos[0]; }
    public int getPosY(){ return pos[1]; }
    public void setPos(int[] pos) { this.pos = pos; }
}
