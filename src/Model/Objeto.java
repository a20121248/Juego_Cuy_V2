package Model;

public class Objeto implements Dibujable{
	char sprite;
	int posX,posY; //esquina superior izquierda
	int ancho;
	int altura;
	
	public Objeto(int posX,int posY,int ancho,int altura,char sprite){
		this.posX = posX;
		this.posY = posY;
		this.ancho = ancho;
		this.altura = altura;
		this.sprite = sprite;
	}
	public int getPosX(){return posX;}
	public int getPosY(){return posY;}
	public int getAncho(){return ancho;}
	public int getAltura(){return altura;}
	public char getSprite(){return sprite;}
}