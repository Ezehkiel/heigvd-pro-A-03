package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.scenes.PopSceneCommand;
import ch.heigvd.pro.a03.server.HttpServerUtils;
import ch.heigvd.pro.a03.users.Score;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class ScoreBoardScene extends Scene {

    public ScoreBoardScene() {

        Container<Table> tableContainer = new Container<Table>();

        float sw = Gdx.graphics.getWidth();
        float sh = Gdx.graphics.getHeight();

        float cw = sw * 0.7f;
        float ch = sh * 0.5f;

        tableContainer.setSize(cw, ch);
        tableContainer.setPosition((sw - cw) / 2.0f, (sh - ch) / 2.0f);
        tableContainer.fillX();

        Table table = new Table(getSkin());



        Label row1 = new Label("Username", getSkin());
        Label row2 = new Label("Parties jouées", getSkin());
        Label row3 = new Label("Partie gagnées", getSkin());

        table.add(row1).expandX().fillX();
        table.add(row2).expandX().fillX();
        table.add(row3).expandX().fillX();
        table.row().expandX().fillX();;

        for(Score score : HttpServerUtils.allScore()){
            Label username = new Label(score.getUsername(), getSkin());
            Label playedGame = new Label(Integer.toString(score.getNbPartieJoue()) , getSkin());
            Label wonGame = new Label(Integer.toString(score.getNbPartieGagne()), getSkin());
            table.add(username).expandX().fillX();
            table.add(playedGame).expandX().fillX();
            table.add(wonGame).expandX().fillX();
            table.row().expandX().fillX();;

            // table.add(scorePlayer).prefWidth(UI.BUTTON_WIDTH);
        }

        TextButton backButton = new TextButton("Back", getSkin());
        backButton.align(Align.center);
        backButton.addListener(new ButtonCommand(new PopSceneCommand(GameLauncher.getInstance())));

        table.add(backButton).prefWidth(UI.BUTTON_WIDTH).prefHeight(UI.BUTTON_HEIGHT);

        tableContainer.setActor(table);
        getStage().addActor(tableContainer);


    }
}
