package ch.heigvd.pro.a03.commands.game;

import ch.heigvd.pro.a03.Map;
import ch.heigvd.pro.a03.TowerDefense;
import ch.heigvd.pro.a03.warentities.Base;
import ch.heigvd.pro.a03.warentities.WarEntityType;
import ch.heigvd.pro.a03.warentities.turrets.Turret;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;

public class ShowMapsCommand extends TowerDefenseCommand {

    public ShowMapsCommand(TowerDefense receiver) {
        super(receiver);
    }

    @Override
    public void execute(Object... args) {

        JSONArray maps = new JSONArray((String) args[0]);
        for (int i = 0; i < maps.length(); ++i) {
            JSONObject map = maps.getJSONObject(i);
            if (i == getReceiver().getGameClient().getPlayer().ID) {
                JSONArray units = map.getJSONArray("units");
                System.out.println(units);
                getReceiver().getScene().getGameMenu().updateIncomingUnitsMenu(units);
            }
            System.out.println();
            getReceiver().setMap(i, mapFromJson(map));
        }

        getReceiver().getScene().updateMaps();
    }

    private Map mapFromJson(JSONObject json) {

        Map map = new Map(json.getInt("height"), json.getInt("width"),
                Base.fromJson(json.getJSONObject("base").toString()),
                pointFromJson(json.getJSONObject("spawn").toString()),
                json.getInt("id")
        );

        JSONArray turrets = json.getJSONArray("turrets");
        for (int i = 0; i < turrets.length(); ++i) {
            JSONObject turretJson = turrets.getJSONObject(i);
            Turret turret = WarEntityType.TurretType.valueOf(turretJson.getString("type"))
                    .createTurret(pointFromJson(turretJson.getJSONObject("position").toString()));
            turret.setId(turretJson.getInt("id"));
            map.setStructureAt(turret, turret.getPosition().y, turret.getPosition().x);
        }

        JSONArray units = json.getJSONArray("units");
        for (int i = 0; i < units.length(); ++i) {
            JSONObject unitJson = units.getJSONObject(i);
            for (int j = 0; j < unitJson.getInt("quantity"); ++j) {
                map.addUnit(WarEntityType.UnitType.valueOf(unitJson.getString("type"))
                        .createUnit(map.getSpawnPoint())
                );
            }
        }

        return map;
    }

    private Point pointFromJson(String json) {
        JSONObject point = new JSONObject(json);
        return new Point(point.getInt("x"), point.getInt("y"));
    }
}
