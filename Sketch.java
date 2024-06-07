import processing.core.PApplet;


public class Sketch extends PApplet {
  // Arrays for the (x, y) coordinates of the snowflakes
  float[] snowX = new float[15];
  float[] snowY = new float[15];
  boolean[] snowHideStatus = new boolean[15];
  int snowDiameter = 15;
  float snowSpeed = 3;
 
  // Player variables
  float playerX, playerY;
  float playerDiameter = 20;
  int playerLives = 3;
 
  // Game over flag
  boolean gameOver = false;


  public void settings() {
    size(400, 400);
  }


  public void setup() {
    background(0);
   
    // Initialize player position
    playerX = width / 2;
    playerY = height - playerDiameter;


    // Generate random x- and y-values for snowflakes
    for (int i = 0; i < snowX.length; i++) {
      snowX[i] = random(width);
      snowY[i] = random(-height, 0);
      snowHideStatus[i] = false;
    }
  }


  public void draw() {
    background(0);


    if (!gameOver) {
      // Draw snow
      snow();
     
      // Draw player
      fill(0, 0, 255);
      circle(playerX, playerY, playerDiameter);


      // Draw lives
      drawLives();


      // Check for collisions
      checkCollisions();


      // Player movement
      playerMovement();
    } else {
      // Game over screen
      background(255);
    }
  }


  public void snow() {
    for (int i = 0; i < snowX.length; i++) {
      if (!snowHideStatus[i]) {
        fill(255);
        circle(snowX[i], snowY[i], snowDiameter);


        snowY[i] += snowSpeed;


        // Reset snowflakes to the top
        if (snowY[i] > height) {
          snowY[i] = random(-height, 0);
        }
      }
    }
  }


  public void keyPressed() {
    // Adjust snow speed with arrow keys
    if (keyCode == DOWN) {
      snowSpeed += 1;
    } else if (keyCode == UP) {
      snowSpeed -= 1;
      if (snowSpeed < 1) {
        snowSpeed = 1;
      }
    }
  }


  public void mousePressed() {
    for (int i = 0; i < snowX.length; i++) {
      if (!snowHideStatus[i] && dist(mouseX, mouseY, snowX[i], snowY[i]) < snowDiameter / 2) {
        snowHideStatus[i] = true;
      }
    }
  }


  public void drawLives() {
    fill(255, 0, 0);
    for (int i = 0; i < playerLives; i++) {
      rect(width - (i + 1) * 30, 10, 20, 20);
    }
  }


  public void playerMovement() {
    if (keyPressed) {
      if (key == 'w' || key == 'W') {
        playerY -= 5;
      }
      if (key == 's' || key == 'S') {
        playerY += 5;
      }
      if (key == 'a' || key == 'A') {
        playerX -= 5;
      }
      if (key == 'd' || key == 'D') {
        playerX += 5;
      }


      // Prevent player from moving out of bounds
      if (playerX < playerDiameter / 2) playerX = playerDiameter / 2;
      if (playerX > width - playerDiameter / 2) playerX = width - playerDiameter / 2;
      if (playerY < playerDiameter / 2) playerY = playerDiameter / 2;
      if (playerY > height - playerDiameter / 2) playerY = height - playerDiameter / 2;
    }
  }


  public void checkCollisions() {
    for (int i = 0; i < snowX.length; i++) {
      if (!snowHideStatus[i] && dist(playerX, playerY, snowX[i], snowY[i]) < (playerDiameter + snowDiameter) / 2) {
        playerLives--;
        snowHideStatus[i] = true;
        if (playerLives <= 0) {
          gameOver = true;
        }
      }
    }
  }


  public static void main(String[] args) {
    PApplet.main("Sketch");
  }
}
