package entities.powerup;

import entities.Entity;
import graphics.Screen;

import static sprites.SpritesImage.wallPassItem;

public class WallPassItem extends PowerUp {

    int updateTime = 0;
    boolean isPlayerInBrick = false;

    public WallPassItem(int _x, int _y) {
        x = _x;
        y = _y;
        spriteImage = wallPassItem.image;
        duration = 1800;
    }

    @Override
    public boolean collide(Entity entity) {
        return false;
    }

    @Override
    public void update() {
        if (showingTime <= 600 || isAdded) {
            if (board.getBrick(x, y) == null) {
                showingTime++;

                if (!isAdded) {
                    if (board.player.collide(x, y)) {
                        board.player.canPassWall = true;
                        isAdded = true;
                    }
                } else {
                    updateTime++;
                    if (updateTime >= duration) {
                        for (int i = 0; i < board.bricks.size(); i++) {
                            if (board.bricks.get(i).collide(board.player.x, board.player.y)) {
                                isPlayerInBrick = true;
                                break;
                            }
                            else if (i == board.bricks.size() - 1) isPlayerInBrick = false;
                        }
                        if (!isPlayerInBrick) {
                            board.player.canPassWall = false;
                            isRemoved = true;
                            isAdded = false;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(Screen screen) {
        if (showingTime <= 600 && !isAdded) screen.renderEntity(x, y, this);
    }
}
