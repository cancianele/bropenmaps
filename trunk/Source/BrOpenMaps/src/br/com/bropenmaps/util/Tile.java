package br.com.bropenmaps.util;

/**
 * Classe que contém as operações relacionadas aos quadrantes de imagens da API do Google para mapas 
 * @author Rafael Melo Salum
 *
 */
public class Tile {

	// The point (x,y) for this tile
	private SimplePoint p;
    // The coord (lat,lon) for this tile
    //private Point co;
	private SimplePoint co;
    // Zoom level for this tile
    private int z;

    // ...Constants...
    private double PI = 3.1415926535;
    
    private int tileSize = 256;
    
    private float pixelsPerLonDegree[] = new float[18];
    
    private float pixelsPerLonRadian[] = new float[18];
    
    private int numTiles[] = new int[18];
    
    private SimplePoint bitmapOrigo[] = new SimplePoint[18];
   
    // Note: These variable names are based on the variables names found in the
    //       Google maps.*.js code.
    private int c = 256;
    
    private double bc;
    
    private double Wa;

    // Fill in the constants array
    private void fillInConstants() {
        
    	this.bc = 2*this.PI;
    	
        this.Wa = this.PI/180;
        
        for(int d = 17; d >= 0; --d) {
    	
        	float f1 = (this.c / 360f);
    		
    		this.pixelsPerLonDegree[d] = f1;
    		
    		this.pixelsPerLonRadian[d]=(float) (this.c / this.bc);
    		
            int e = this.c / 2;
            
            this.bitmapOrigo[d] = new SimplePoint(e,e);
            
            this.numTiles[d] = (this.c / 256);
            
            this.c *= 2;

        }
        
    }
    
    /**
     * Contrutor da classe
     * @param latitude
     * @param longitude
     * @param zoomLevel
     */
    public Tile(double latitude, double longitude, int zoomLevel) {

    	this.fillInConstants();
    	
        this.z = zoomLevel;
        
        this.p = this.getTileCoordinate(latitude, longitude, zoomLevel);
        
        this.co = this.getLatLong(latitude, longitude, zoomLevel);
        
    }
    
    /**
     * Ponto do quadrante (x,y)
     * @return
     */
    public SimplePoint getTileCoord() {
      
    	return this.p;
    	
    }

    /**
     * Ponto do quadrante(latitude, longitude)
     * @return
     */
    private SimplePoint getTileLatLong() {
       
    	return this.co;
    	
    }
    
    /**
     * Retorna a string usada como parâmetro para a representação de mapas via satélite
     * @return
     */
    private String getKeyholeString() {
        
    	String s = "";
        
        int myX = this.p.getX();
        
        int myY = this.p.getY();

        for(int i = 17; i > this.z; i--) {
                
    		double rx = (myX % 2);
            
            myX = (int) Math.floor(myX / 2);
            
            double ry = (myY % 2);
            
            myY = (int) Math.floor(myY / 2);
    
            s = this.getKeyholeDirection(rx, ry) + s;
            
        }
        
        return 't' + s;
            
    }

    private String getKeyholeDirection(double x, double y) {
        
    	if(x == 1) {
        
    		if(y == 1) {
                
    			return "s";
    			
            } else if(y == 0) {
                
            	return "r";
            	
            }
    		
        } else if(x == 0) {
            
        	if(y == 1) {
        		
                return "t";
                
            } else if(y == 0) {
                
            	return "q";
            	
            }
        	
        }

        return "";
    }
    
    public SimplePoint getBitmapCoordinate(double a, double b, int c) {
       
    	SimplePoint d = new SimplePoint(0,0);

        double thispplon = this.pixelsPerLonDegree[c];
        
        SimplePoint sp = (SimplePoint) this.bitmapOrigo[c];
        
        int newX = (int) Math.floor(sp.getX() + (b * thispplon));
        
        d.setX(newX);
        
        double e = Math.sin(a * this.Wa);

        if(e > 0.9999) {
        	e = 0.9999;
        }
        
        if(e < -0.9999) {
        	e = -0.9999;
        }

        double thispplonrad = this.pixelsPerLonRadian[c];
        
        int newY = (int) Math.floor(sp.getY() + 0.5 * Math.log((1 + e) / (1 - e)) * -1*(thispplonrad));
        
        d.setY(newY);
        
        return d;
        
    }

	private SimplePoint getTileCoordinate(double a, double b, int c) {
        
		SimplePoint d = this.getBitmapCoordinate(a, b, c);
        
		d.setX((int)(Math.floor(d.getX() / this.tileSize)));
        
		d.setY((int)(Math.floor(d.getY() / this.tileSize)));

        return d;
        
	}
	
	private SimplePoint getLatLong(double a, double b, int c) {
        
		SimplePoint d = new SimplePoint(0, 0);
        
		SimplePoint e = this.getBitmapCoordinate(a, b, c);
        
		a = e.getX();
        
		b = e.getY();

        SimplePoint sp = (SimplePoint) this.bitmapOrigo[c];
        
        double thispplon = this.pixelsPerLonDegree[c];
        
        double thispplonrad = this.pixelsPerLonRadian[c];
        
        int newX = (int) ((a - sp.getX()) / thispplon);
        
        d.setX(newX);
        
        double e2 = ((b - sp.getY()) / (-1*thispplonrad));
        
        d.setY((int)((2 * Math.atan(Math.exp(e2)) - this.PI / 2) / this.Wa));
        
        return d;
        
	}
	
}