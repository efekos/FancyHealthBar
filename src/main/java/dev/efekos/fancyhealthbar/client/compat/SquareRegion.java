package dev.efekos.fancyhealthbar.client.compat;

public record SquareRegion(int u1,int u2,int v1,int v2) {

    public SquareRegion add(int u,int v){
        return new SquareRegion(u1+u,u2+u,v1+v,v2+v);
    }

}