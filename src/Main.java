import processing.core.PApplet;
import processing.core.PImage;

public class Main extends PApplet {

	Cliente cliente;
	
	PImage ryu;
	PImage ken;
	
	PImage street;
	PImage lifeHUDL;
	PImage lifeHUDR;
	PImage life100;
	PImage life80;
	PImage life60;
	PImage life40;
	PImage life20;
	PImage life00;
	PImage kenDead;
	PImage ryuDead;
	
	Animation kenStand;
	Animation kenAttack;
	Animation kenWalkingF;
	Animation kenHit;
	Animation kenLost;
	Animation kenTaunt;
	
	Animation ryuStand;
	Animation ryuAttack;
	Animation ryuWalkingF;
	Animation ryuHit;
	Animation ryuLost;
	Animation ryuTaunt;
	
	int countA = 0;
	int countB = 0;
	boolean contarA = false;
	boolean contarB = false;
	
	public static void main(String[] args) {
		PApplet.main("Main");
	}

		public void settings(){
			size(800,533);
		}
		
		public void setup(){
			cliente = new Cliente("localhost", 7298);
			cliente.iniciarFluxos();
			cliente.start();
			
			surface.setTitle("Ultimate Kombat Online");
			street = loadImage("img/street.png");
			
			ryu = loadImage("img/ryu.png");
			ken = loadImage("img/ken.png");
			
			lifeHUDL = loadImage("img/lifeBar/lifeBarL.png");
			lifeHUDR = loadImage("img/lifeBar/lifeBarR.png");
			life100 = loadImage("img/lifeBar/life100.png");
			life80 = loadImage("img/lifeBar/life80.png");
			life60 = loadImage("img/lifeBar/life60.png");
			life40 = loadImage("img/lifeBar/life40.png");
			life20 = loadImage("img/lifeBar/life20.png");
			life00 = loadImage("img/lifeBar/life00.png");
			kenDead = loadImage("img/kenDead.png");
			ryuDead = loadImage("img/ryuDead.png");
			
			kenStand = new Animation("img/ken_stand/frame_", 10);
			kenAttack = new Animation("img/ken_attack/frame_", 06);
			kenWalkingF = new Animation("img/ken_walkingf/frame_", 11);
			kenHit = new Animation("img/ken_hit/frame_", 8);
			kenLost = new Animation("img/ken_died/frame_", 27);
			kenTaunt = new Animation("img/ken_taunt/frame_", 9);
			
			ryuStand = new Animation("img/ryu_stand/frame_", 10);
			ryuAttack = new Animation("img/ryu_attack/frame_", 13);
			ryuWalkingF = new Animation("img/ryu_walkingf/frame_", 11);
			ryuHit = new Animation("img/ryu_hit/frame_", 15);
			ryuLost = new Animation("img/ryu_died/frame_", 24);
			ryuTaunt = new Animation("img/ryu_taunt/frame_", 18);
			
			
			background(street);
		}
		
		
		public void draw(){
			frameRate(20);
			background(street);
			
			if (contarA) {
				countA++;
			}
			if (contarB) {
				countB++;
			}
			int ryuPhotoX = 714;
			int ryuPhotoY = 18;
			int kenPhotoX = 40;
			int kenPhotoY = 20;
			
			int lifeHUDWidth = 436;
			int lifeHUDHeight = 74;
			int lifeHUDRPosX = 370;
			int lifeHUDRPosY = 5;
			int lifeHUDLPosX = -30;
			int lifeHUDLPosY = 5;
			
			int lifeBarWidth = 280;
			int lifeBarHeight = 25;
			int lifeBarRPosX = 421;
			int lifeBarRPosY = 40;
			int lifeBarLPosX = 99;
			int lifeBarLPosY = 43;
			
			boolean switchSides = false;
			for (Personagem p : Cliente.getPersonagens()) {
				if (!switchSides) {
					image(lifeHUDL, lifeHUDLPosX, lifeHUDLPosY, lifeHUDWidth, lifeHUDHeight);
					image(getLifeBarImage(p.getVida()), lifeBarLPosX, lifeBarLPosY, lifeBarWidth, lifeBarHeight);
					image(ken, kenPhotoX, kenPhotoY, 40, 40);
					switchSides = true;
					lifeHUDLPosY += lifeHUDHeight;
					lifeBarLPosY += lifeHUDHeight;
					kenPhotoY += lifeHUDHeight;
				} else {
					image(lifeHUDR, lifeHUDRPosX, lifeHUDRPosY, lifeHUDWidth, lifeHUDHeight);
					image(getLifeBarImage(p.getVida()), lifeBarRPosX, lifeBarRPosY, lifeBarWidth, lifeBarHeight);
					image(ryu, ryuPhotoX, ryuPhotoY, 40, 40);
					switchSides = false;
					lifeHUDRPosY += lifeHUDHeight;
					lifeBarRPosY += lifeHUDHeight;
					ryuPhotoY += lifeHUDHeight;
				}
				
				if (p.getAparencia() == 1) {
					switch (p.getAction()) {
						case ATACANDO:
							contarA = true;
							kenAttack.display(p.getPosX()+2, p.getPosY()+13);
							if (countA > 10 ) {
								contarA = false;
								countA = 0;
								p.setAction(Action.PARADO);
							}
							break;
						case ANDANDO_TRAS:
						case ANDANDO_FRENTE:
							contarA = true;
							kenWalkingF.display(p.getPosX()-20, p.getPosY());
							if (countA > 10 ) {
								contarA = false;
								countA = 0;
								p.setAction(Action.PARADO);
							}
							break;
						case APANHANDO:
							frameRate(15);
							contarA = true;
							kenHit.display(p.getPosX()- 35, p.getPosY());
							if (countA > 5 ) {
								contarA = false;
								countA = 0;
								p.setAction(Action.PARADO);
							}
							break;
						case PERDEU:
							contarA = true;
							kenLost.display(p.getPosX()-35, p.getPosY());
							if (countA > 5 ) {
								contarA = false;
								countA = 0;
								p.setAction(Action.MORTO);
							}
							break;
						case MORTO:
							image(kenDead, p.getPosX()-35, p.getPosY(), 181, 120);
							break;
						case NEW:
							kenTaunt.display(p.getPosX(), p.getPosY());
							break;
						case GANHOU:
						case PARADO:
							contarA = false;
							kenStand.display(p.getPosX(), p.getPosY());
							break;
						}
				} else {
					switch (p.getAction()) {
						case ATACANDO:
							contarB = true;
							ryuAttack.display(p.getPosX()-15, p.getPosY()-12);
							if (countB > 10 ) {
								contarB = false;
								countB = 0;
								p.setAction(Action.PARADO);
							}
							break;
						case ANDANDO_TRAS:
						case ANDANDO_FRENTE:
							contarB = true;
							ryuWalkingF.display(p.getPosX()-20, p.getPosY());
							if (countB > 10 ) {
								contarB = false;
								countB = 0;
								p.setAction(Action.PARADO);
							}
							break;
						case APANHANDO:
							frameRate(15);
							contarB = true;
							ryuHit.display(p.getPosX()-35, p.getPosY());
							if (countB > 5 ) {
								contarB = false;
								countB = 0;
								p.setAction(Action.PARADO);
							}
							break;
						case PERDEU:
							contarB = true;
							ryuLost.display(p.getPosX()-35, p.getPosY());
							if (countB > 5 ) {
								contarB = false;
								countB = 0;
								p.setAction(Action.MORTO);
							}
							break;
						case MORTO:
							image(ryuDead, p.getPosX()-35, p.getPosY(), 181, 120);
							break;
						case NEW:
							ryuTaunt.display(p.getPosX(), p.getPosY());
							break;
						case GANHOU:
						case PARADO:
							contarB = false;
							ryuStand.display(p.getPosX(), p.getPosY());
							break;
						}
				}
			}
		}
		
		public void keyPressed() {
			cliente.keyPressed(keyCode);			  
		}
		
		class Animation {
			  PImage[] images;
			  int imageCount;
			  int frame;
			  
			  Animation(String imagePrefix, int count) {
			    imageCount = count;
			    images = new PImage[imageCount];

			    for (int i = 0; i < imageCount; i++) {
			      // Use nf() to number format 'i' into two digits
			      String filename = imagePrefix + nf(i, 2) + ".gif";
			      images[i] = loadImage(filename);
			    }
			  }

			  void display(float xpos, float ypos) {
			    frame = (frame+1) % imageCount;
			    image(images[frame], xpos, ypos);
			  }
			  
			  int getWidth() {
			    return images[0].width;
			  }
		}
		
		public PImage getLifeBarImage(int life) {
			switch (life) {
				case 100: return life100;
				case 80: return life80;
				case 60: return life60;
				case 40: return life40;
				case 20: return life20;
				case 0: return life00;
			}
			return life00;
		}
		
}
