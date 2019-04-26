package ch.heigvd.pro.a03.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UI {

    private static BitmapFont font = null;
    private static BitmapFont bigFont = null;

    private static void generateFonts() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/pixelart.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 16;
        font = generator.generateFont(parameter);

        parameter.size = 64;
        bigFont = generator.generateFont(parameter);

        generator.dispose();
    }

    /* LibGDX Scene2D Skin */
    public static Skin getSkin() {

        if (font == null) {
            generateFonts();
        }

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
