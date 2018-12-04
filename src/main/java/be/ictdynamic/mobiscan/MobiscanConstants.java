package be.ictdynamic.mobiscan;

/**
 * Created by wvdbrand on 30/03/2017.
 */
public abstract class MobiscanConstants {
    public static final String LOG_STARTING = ">>>Starting ";
    public static final String LOG_ERROR = "!!!Error ";
    public static final String LOG_ENDING = "<<<Exiting ";

    public static final String DISTANCE_MATRIX_MOCK_RESPONSE_0 = "{\n" +
            "   \"destination_addresses\" : [ \"Liersesteenweg 4, 2800 Mechelen, Belgium\" ],\n" +
            "   \"origin_addresses\" : [ \"Tweebunder 4, Edegem, Belgium\" ],\n" +
            "   \"rows\" : [\n" +
            "      {\n" +
            "         \"elements\" : [\n" +
            "            {\n" +
            "               \"distance\" : {\n" +
            "                  \"text\" : \"17.4 km\",\n" +
            "                  \"value\" : 17383\n" +
            "               },\n" +
            "               \"duration\" : {\n" +
            "                  \"text\" : \"18 mins\",\n" +
            "                  \"value\" : 1054\n" +
            "               },\n" +
            "               \"status\" : \"OK\"\n" +
            "            }\n" +
            "         ]\n" +
            "      }\n" +
            "   ],\n" +
            "   \"status\" : \"OK\"\n" +
            "}";

    public static final String DISTANCE_MATRIX_MOCK_RESPONSE_1 = "{\n" +
            "   \"destination_addresses\" : [ \"Wetstraat 1, Brussel, Belgium\" ],\n" +
            "   \"origin_addresses\" : [ \"Tweebunder 4, Edegem, Belgium\" ],\n" +
            "   \"rows\" : [\n" +
            "      {\n" +
            "         \"elements\" : [\n" +
            "            {\n" +
            "               \"distance\" : {\n" +
            "                  \"text\" : \"50.1 km\",\n" +
            "                  \"value\" : 50123\n" +
            "               },\n" +
            "               \"duration\" : {\n" +
            "                  \"text\" : \"60 mins\",\n" +
            "                  \"value\" : 3600\n" +
            "               },\n" +
            "               \"status\" : \"OK\"\n" +
            "            }\n" +
            "         ]\n" +
            "      }\n" +
            "   ],\n" +
            "   \"status\" : \"OK\"\n" +
            "}";

    public static String DISTANCE_MATRIX_MOCK_RESPONSES[] = new String[]{DISTANCE_MATRIX_MOCK_RESPONSE_0, DISTANCE_MATRIX_MOCK_RESPONSE_1};

}
