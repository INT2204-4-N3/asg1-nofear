package entities.powerup;

import graphics.Screen;

import static sprites.SpritesImage.detonatorItem;

public class LiveItem extends PowerUp {

    public LiveItem(int _x, int _y) {
        x = _x;
        y = _y;
        spriteImage = detonatorItem.image;
    }

    @Override
    public void update() {
        if (showingTime <= 600 || isAdded) {
            if (board.getBrick(x, y) == null) {
                showingTime++;
                if (!isAdded) {
                    if (board.player.collide(x, y)) {
                        board.player.lives++;
                        isAdded = true;
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
