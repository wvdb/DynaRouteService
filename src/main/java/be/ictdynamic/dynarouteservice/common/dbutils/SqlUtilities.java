package be.ictdynamic.dynarouteservice.common.dbutils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * Static class containing utility constants and methods for SQL manipulation.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 22-jan-2008
 */
public final class SqlUtilities {
    public static final int BUFFER_SIZE = 50;

    /**
     * Private constructor to prevent this static class from being instantiated.
     */
    private SqlUtilities() {
    }

    /**
     * Returns a wildcard string for an IN condition.
     * <p/>
     * count = 0 returns ""
     * count = 1 returns "?"
     * count = 2 returns "? , ?"
     * ...
     *
     * @param count int referencing the number of wildcards in the IN condition.
     * @return String refrencing the wildcard string for an IN condition.
     */
    public static String getInWildcards(int count) {
        String in;
        if (count > 0) {
            String[] wildcards = new String[count];
            Arrays.fill(wildcards, "?");
            in = StringUtils.join(wildcards, ",");
        } else {
            in = StringUtils.EMPTY;
        }

        return in;
    }

    /**
     * Returns a wildcard string for an IN condition.
     * <p/>
     * count = 0 returns ""
     * count = 1 returns "?"
     * count = 2 returns "? , ?"
     * ...
     *
     * @param values Array of Objects referencing the number of wildcards in the IN condition.
     * @return String refrencing the wildcard string for an IN condition.
     */
    public static String getInWildcards(Object[] values) {
        return SqlUtilities.getInWildcards(values != null ? values.length : 0);
    }

    /**
         * Returns the specified argument as a valid argument for a LIKE ANYWHERE condition.
         * <p/>
         * Surrounds the specified argument with '%'.
         * Returns "%" when the specified argument is empty.
         *
         * @param argument String referencing the argument for the LIKE condition, may be null.
         * @return Returns a valid argument for a LIKE condition.
         */
        public static String getLikeBeginsWith(String argument) {
            String likeArgument;

            if (StringUtils.isNotEmpty(argument)) {
                likeArgument = MessageFormat.format("{0}%", argument);
            } else {
                likeArgument = "%";
            }

            return likeArgument;
        }

    /**
     * Alters the specified query to limit the number of results to the specified number.
     * <p/>
     * Basically replaces the 'select' keyword with 'select first X'.
     * When the specified number of results is 0 or negative, the original query is returned.
     *
     * @param query      String referencing the query to be altered.
     * @param maxResults int referencing the number to limit the result count to, must be a positive.
     * @return String referencing the altered query, the original query when the specified number is 0 or negative.
     */
    public static String limitResults(String query, int maxResults) {
        StringBuilder tmp = new StringBuilder();

        if (maxResults > 0) {
            tmp.insert(0, "select * from (");
            tmp.append(query);
            tmp.append(") where rownum  <= ");
            tmp.append(maxResults);
        } else {
            tmp.append(query);
        }
        return tmp.toString();
    }

    /**
     * Creates and returns the compound and-condition of the specified conditions.
     *
     * @param condition1 String referencing the first condition of the compound and-condition to be created, may be null.
     * @param condition2 String referencing the second condition of the compound and-condition to be created, may be null.
     * @return String referencing the created compound and-condition.
     */
    public static String and(String condition1, String condition2) {
        return SqlUtilities.compoundCondition(condition1, condition2, "AND");
    }

    /**
     * Creates and returns the compound or-condition of the specified conditions.
     *
     * @param condition1 String referencing the first condition of the compound or-condition to be created, may be null.
     * @param condition2 String referencing the second condition of the compound or-condition to be created, may be null.
     * @return String referencing the created compound or-condition.
     */
    @SuppressWarnings({"PMD.ShortMethodName"})
    public static String or(String condition1, String condition2) {
        return SqlUtilities.compoundCondition(condition1, condition2, "OR");
    }

    /**
     * Creates and returns the compound condition of the specified conditions joined by the specified logical operator.
     *
     * @param condition1      String referencing the first condition of the compound condition to be created, may be null.
     * @param condition2      String referencing the second condition of the compound condition to be created, may be null.
     * @param logicalOperator String referencing the logical operation joining the two conditions.
     * @return String referencing the created compound condition.
     */
    private static String compoundCondition(String condition1, String condition2, String logicalOperator) {
        Assert.hasText(logicalOperator, "The logical operator joining the two conditions may not be empty.");
        StringBuilder compound = new StringBuilder(BUFFER_SIZE);

        if (StringUtils.isEmpty(condition1)) {
            compound.append(condition2);
        } else if (StringUtils.isEmpty(condition2)) {
            compound.append(condition1);
        } else {
            compound.append("(");
            compound.append(condition1);
            compound.append(" ");
            compound.append(logicalOperator);
            compound.append(" ");
            compound.append(condition2);
            compound.append(")");
        }

        return compound.toString();
    }

}