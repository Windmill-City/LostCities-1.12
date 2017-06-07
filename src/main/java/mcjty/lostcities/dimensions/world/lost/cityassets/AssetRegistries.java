package mcjty.lostcities.dimensions.world.lost.cityassets;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class AssetRegistries {

    public static final AbstractAssetRegistry<BuildingPart> PARTS = new AbstractAssetRegistry<>();
    public static final AbstractAssetRegistry<Building> BUILDINGS = new AbstractAssetRegistry<>();
    public static final AbstractAssetRegistry<CityStyle> CITYSTYLES = new AbstractAssetRegistry<>();
    public static final AbstractAssetRegistry<MultiBuilding> MULTI_BUILDINGS = new AbstractAssetRegistry<>();
    public static final AbstractAssetRegistry<Style> STYLES = new AbstractAssetRegistry<>();
    public static final AbstractAssetRegistry<Palette> PALETTES = new AbstractAssetRegistry<>();

    public static final void reset() {
        System.out.println("AssetRegistries.reset");
        PARTS.reset();
        BUILDINGS.reset();
        CITYSTYLES.reset();
        MULTI_BUILDINGS.reset();
        STYLES.reset();
        PALETTES.reset();
    }

    public static void load(InputStream inputstream) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputstream, "UTF-8"));
            reset();
            System.out.println("AssetRegistries.load");
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(br);
            for (JsonElement entry : element.getAsJsonArray()) {
                JsonObject object = entry.getAsJsonObject();
                String type = object.get("type").getAsString();
                if ("style".equals(type)) {
                    STYLES.register(new Style(object));
                } else if ("palette".equals(type)) {
                    PALETTES.register(new Palette(object));
                } else if ("citystyle".equals(type)) {
                    CITYSTYLES.register(new CityStyle(object));
                } else if ("part".equals(type)) {
                    PARTS.register(new BuildingPart(object));
                } else if ("building".equals(type)) {
                    BUILDINGS.register(new Building(object));
                } else if ("multibuilding".equals(type)) {
                    MULTI_BUILDINGS.register(new MultiBuilding(object));
                } else {
                    throw new RuntimeException("Unknown type '" + type + "'!");
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}