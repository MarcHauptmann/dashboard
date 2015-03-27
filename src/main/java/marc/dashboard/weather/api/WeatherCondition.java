package marc.dashboard.weather.api;

import java.util.Optional;

import static java.util.Arrays.asList;

enum WeatherCondition {
    thunderstorm_with_light_rain(200),
    thunderstorm_with_rain(201),
    thunderstorm_with_heavy_rain(202),
    light_thunderstorm(210),
    thunderstorm(211),
    heavy_thunderstorm(212),
    ragged_thunderstorm(221),
    thunderstorm_with_light_drizzle(230),
    thunderstorm_with_drizzle(231),
    thunderstorm_with_heavy_drizzle(232),

    light_intensity_drizzle(300),
    drizzle(301),
    heavy_intensity_drizzle(302),
    light_intensity_drizzle_rain(310),
    drizzle_rain(311),
    heavy_intensity_drizzle_rain(312),
    shower_rain_and_drizzle(313),
    heavy_shower_rain_and_drizzl(314),
    shower_drizzle(321),

    light_rain(500),
    moderate_rain(501),
    heavy_intensity_rain(502),
    very_heavy_rain(503),
    extreme_rain(504),
    freezing_rain(511),
    light_intensity_shower_rain(520),
    shower_rain(521),
    heavy_intensity_shower_rain(522),
    ragged_shower_rain(531),

    light_snow(600),
    snow(601),
    heavy_snow(602),
    sleet(611),
    shower_sleet(612),
    light_rain_and_snow(615),
    rain_and_snow(616),
    light_shower_snow(620),
    shower_snow(621),
    heavy_shower_snow(622),

    mist(701),
    smoke(711),
    haze(721),
    sand_dust_whirls(731),
    fog(741),
    sand(751),
    dust(761),
    volcanic_ash(762),
    squalls(771),

    clear_sky(800),
    few_clouds(801),
    scattered_clouds(802),
    broken_clouds(803),
    overcast_clouds(804),

    tornado(900),
    tropical_storm(901),
    hurricane(902),
    cold(903),
    hot(904),
    windy(905),
    hail(906),

    calm(951),
    light_breeze(952),
    gentle_breeze(953),
    moderate_breeze(954),
    fresh_breeze(955),
    strong_breeze(956),
    high_wind_near_gale(957),
    gale(958),
    severe_gale(959),
    storm(960),
    violent_storm(961);

    int id;

    WeatherCondition(int id) {
        this.id = id;
    }

    public static WeatherCondition fromInt(int value) {
        Optional<WeatherCondition> myCondition = asList(values()).stream().filter(
                weatherCondition -> weatherCondition.id == value).findAny();

        if (myCondition.isPresent()) {
            return myCondition.get();
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
