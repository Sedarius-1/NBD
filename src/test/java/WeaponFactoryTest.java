import org.ibd.enums.WeaponTypeEnum;
import org.ibd.factory.WeaponFactory;
import org.ibd.model.weapons.Weapon;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class WeaponFactoryTest {
    @ParameterizedTest
    @ArgumentsSource(MapBadArgumentsProvider.class)
    void BadArgumentTest(Map<String, String> badParams) {
        WeaponTypeEnum type = WeaponTypeEnum.valueOf(badParams.get("type"));
        badParams.remove("type");
        Weapon weapon = WeaponFactory.manufactureWeapon(type, badParams);
        assertNull(weapon);
    }

    static class MapBadArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            // Rifle
            Map<String, String> map1 = new HashMap<>();
            map1.put("type", WeaponTypeEnum.RIFLE.toString());
            map1.put("manufacturer", "Ruger");
            map1.put("name", "PPC");
            map1.put("price", "4000");
            map1.put("caliber", "22LR");
            //map1.put("length", "15.6");

            // Pistol
            Map<String, String> map2 = new HashMap<>();
            map2.put("type", WeaponTypeEnum.PISTOL.toString());
            map2.put("manufacturer", "Glock");
            map2.put("name", "Glock 19");
            map2.put("price", "2500");
            //map2.put("caliber", "9mm");

            // Grenade
            Map<String, String> map3 = new HashMap<>();
            map3.put("type", WeaponTypeEnum.HANDGRENADE.toString());
            map3.put("manufacturer", "Smolinus Inc.");
            map3.put("name", "Ovirt mk 2");
            map3.put("price", "2135");
            map3.put("power", "9001");
            //map3.put("grenadeType", GrenadeType.Fag.toString());

            // McNuke
            Map<String, String> map4 = new HashMap<>();
            map4.put("type", WeaponTypeEnum.MCNUKE.toString());
            map4.put("manufacturer", "Smolinus Inc.");
            map4.put("name", "VIRTuL");
            map4.put("price", "69420");
            //map4.put("power", "0");

            return Stream.of(
                    Arguments.of(map1),
                    Arguments.of(map2),
                    Arguments.of(map3),
                    Arguments.of(map4)
            );
        }
    }
}


