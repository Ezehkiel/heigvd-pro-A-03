package ch.heigvd.pro.a03.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UI {

    private static FreeTypeFontGenerator fontGenerator = null;

    /* LibGDX Scene2D Skin */
    public static Skin createSkin() {

        if (fontGenerator == null) {
            fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ui/pixelart.ttf"));
        }

        // generate fonts
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        BitmapFont font = fontGenerator.generateFont(parameter);
        parameter.size = 64;
        BitmapFont bigFont = fontGenerator.generateFont(parameter);

        // create skin
        Skin skin = new Skin();

        skin.add("font", font, BitmapFont.class);
        skin.add("font-big", bigFont, BitmapFont.class);

        skin.addRegions(new TextureAtlas(Gdx.files.internal("ui/skin.atlas")));
        skin.load(Gdx.files.internal("ui/skin.json"));

        return skin;
    }

    /* UI SIZES */
    public static final int SPACING = 50;
    public static final int BUTTON_WIDTH = 250;
    public static final int BUTTON_HEIGHT = 50;
    public static final int TEXT_FIELD_HEIGHT = 25;
}
