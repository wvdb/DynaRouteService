package be.ictdynamic.dynarouteservice.common.lang;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class RtfCharacterMapping.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 6-aug-2005
 */
public final class RtfCharacterMapping {

    /**
     * Value of RtfCharacterMapping.RTF_CHARACTERS
     */
    private static final Collection<RtfCharacterMapping> RTF_CHARACTERS = new ArrayList<RtfCharacterMapping>(1);

    static {
        RTF_CHARACTERS.add(new RtfCharacterMapping("\\n", "\n\\\\par "));
    }

    public static final int STRING_SIZE = 8;
    public static final int CHAR_BOUND = 127;

    /**
     * The origin character.
     */
    private final String originCharacter;

    /**
     * The replacement character.
     */
    private final String replacementCharacter;

    /**
     * Constructs a new instance of RtfCharacterMapping.
     *
     * @param originCharacter      The String value of originCharacter
     * @param replacementCharacter The  String value of replacementCharacter
     */
    private RtfCharacterMapping(String originCharacter, String replacementCharacter) {
        this.originCharacter = originCharacter;
        this.replacementCharacter = replacementCharacter;
    }

    /**
     * Gets the value of originCharacter.
     *
     * @return The resulting String value
     */
    public String getOriginCharacter() {
        return originCharacter;
    }

    /**
     * Gets the value of replacementCharacter.
     *
     * @return The resulting String value
     */
    public String getReplacementCharacter() {
        return replacementCharacter;
    }

    /**
     * Escapes RTF characters.
     *
     * @param str The String value of str
     * @return The resulting String value
     */
    public static String escapeRtfCharacters(String str) {
        String updatedStr = str;
        if (updatedStr != null) {
            StringBuilder builder = new StringBuilder(updatedStr.length() * STRING_SIZE);
            char[] chars = updatedStr.toCharArray();
            for (char c : chars) {
                int intValue = (int) c;
                if (intValue > CHAR_BOUND) {
                    builder.append("\\u").append(intValue).append("\\'3f");
                } else {
                    builder.append(c);
                }
            }
            updatedStr = builder.toString();
            for (RtfCharacterMapping entry : RTF_CHARACTERS) {
                updatedStr = updatedStr.replaceAll(entry.getOriginCharacter(), entry.getReplacementCharacter());
            }
        }
        return updatedStr;
    }
}
