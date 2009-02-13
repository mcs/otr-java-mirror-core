package otr.mirror.core.util;

import java.util.*;

/**
 * PHP Serialization support is provided by this abstract class using two public class methods. They allow the
 *  importing and exporting of PHP serialized variable via Java Objects using the following variable type mappings:
 * <p><pre>
 * _PHP Type_   <--------->   _Java Type_
 *    array                            java.util.HashMap
 *    int                                 java.lang.Integer
 *    double                           java.lang.Double
 *    boolean                         java.lang.Boolean
 *    string                            java.lang.String
 * </pre><p>
 * Additionally, just for convenience, the serialize() method also provides the following one way mappings:
 * <p>
 * _PHP Type_   <---------   _Java Type_
 *    array                            java.lang.Object[]
 *    int                                 java.lang.Byte
 *    int                                 java.lang.Short

 *    double                           java.lang.Float
 *    double                           java.lang.Long
 *    double                           java.lang.BigInteger
 *    double                           java.lang.BigDecimal

 *    string                            java.lang.Character
 *    string                            java.lang.CharBuffer
 *    string                            java.lang.StringBuilder
 *
 * The PHP Object type and any Java Classes not listed above are not presently supported

 * <p>
 * Why would you want to do such a crazy thing? Well if you have a PHP webapp storing session data as serialized
 * variables (which is very common) into a db, cookie, form hidden value, file, etc. and you want a J2EE application

 *  to be able to access and understand that data this is the tool for you.
 * <p>
 * Usage Example:
 * <pre>
 *    Map myMap = new HashMap();
 *    myMap.put(0, "hello");
 *    myMap.put(1, "world");
 *    myMap.put(null, null);
 *    myMap.put("foo_bar", true);
 *    myMap.put("value", 0.005f);
 *    myMap.put("me", myMap);
 *
 *    System.out.println("Inputted:");
 *    System.out.println(myMap);
 *    System.out.println("Serialized to:");
 *    String str = PHPSerialization.serialize(myMap);
 *    System.out.println( str );
 *    System.out.println("Unserialized to:");
 *    System.out.println( PHPSerialization.unserialize(str) );
 * </pre>
 *<p>
 * Result:
 *      Inputted:
 *      {value=0.0050, foo_bar=true, null=null, 1=world, me=(this Map), 0=hello}
 *      Serialized to:

 *      a:6:{s:5:"value";d:0.004999999888241291;s:7:"foo_bar";b:1;s:0:"";N;i:1;s:5:"world";s:2:"me";N;i:0;s:5:"hello";}
 *      Unserialized to:
 *      {foo_bar=true, value=0.004999999888241291, 1=world, me=null, =null, 0=hello}
 *<p>
 * Note: This class was requires J2SE >= 5.0 to compile since it uses some autoboxing
 *
 * @version 0.2.0
 * @since    0.1.0
 * @author Kris Dover <krisdover@hotmail.com>

 */
public final class PHPSerialization {

    /* generate a fast and nasty unique key for storing array sizes in hashmaps */
    protected static final String COUNT_KEY = "COUNT_KEY_" + Long.toHexString(new Random().nextLong());

    private PHPSerialization() {
        // static utility class, no instance allowed
    }

    /**
     * Serializes java objects into a String PHP serialized data. Supported
     * object types include null (not really a type), Boolean, any subclass of Number
     * (e.g. Integer, Double), Character, any subclass of CharSequence (e.g. String, StringBuilder),
     * Object[] (arrays) of any these listed types and subclasses of Map with keys and values
     * of any of the listed types. Primatives are implicitly supported through autoboxing
     * <p>
     * It should be noted that this function does not provide a one-to-one mapping
     * between PHP and Java types but instead makes a best effort at facilitate
     * serialized data exchanges between the two.
     *
     * @param obj The java object to be serialized
     * @throws IllegalStateException if an attempt is made to serialize an unsupported
     *                 object type
     */
    public static String serialize(Object obj)
            throws IllegalStateException {
        StringBuilder str = new StringBuilder();
        serialize(obj, str, new ArrayList(), false);

        return str.toString();
    }

    /**
     * This is a recursive helper function for serialize(Object) which employs
     * a StringBuilder generating the serialized output.
     *<p>
     * Circular references are mapped to null and hence lost.

     *
     * @param obj                 The java object to be serialized
     * @param str                  A StringBuilder used for appending together the serialized data
     * @param refs               A List used for detecting circular references

     * @param isArrayKey   A boolean value used for identifying array keys for unique treatment
     * @throws IllegalStateException if an attempt is made to serialize an unsupported
     *                 object type

     */
    private static void serialize(Object obj, StringBuilder str, List refs, boolean isArrayKey)
            throws IllegalStateException {
        boolean isCircular = false;
        // is this a circular references?

        // we only track circular references to arrays and maps
        // since these could lead to recursive loops
        for (Object o : refs) {
            if (o == obj) {
                isCircular = true;
            }
        }
        // map cicular references to null

        if (isCircular) {
            obj = null;
        }

        // map supported object types appropriately
        if (obj == null) {
            if (isArrayKey) {
                str.append("s:0:\"\";");

            } else {
                str.append("N;");
            }
        } else if (obj instanceof Boolean) {
            if (isArrayKey) {
                str.append("i:");
            } else {
                str.append("b:");

            }
            str.append(((Boolean) obj).booleanValue() ? "1;" : "0;");
        } else if (obj instanceof Number) {
            Number num = (Number) obj;
            if (isArrayKey || obj instanceof Integer || obj instanceof Byte || obj instanceof Short) {

                str.append("i:").append(num.intValue()).append(";");
            } else {
                str.append("d:").append(num.doubleValue()).append(";");
            }
        } else if (obj instanceof Character) {

            serialize(obj.toString(), str, refs, isArrayKey);
        } else if (obj instanceof CharSequence) {
            str.append("s:").append(((CharSequence) obj).length()).append(":\"").append(obj.toString()).append("\";");

        } else if (obj instanceof Object[]) {
            if (isArrayKey) {
            }
            refs.add(obj);
            Object[] arr = (Object[]) obj;

            str.append("a:").append(arr.length).append(":{");
            for (int i = 0; i < arr.length; i++) {
                serialize(i, str, refs, true);
                serialize(arr[i], str, refs, false);
            }

            str.append("}");
        } else if (obj instanceof Map) {
            if (isArrayKey) {
                throw new IllegalStateException("Maps cannot be array keys");
            }
            refs.add(obj);

            Map map = (Map) obj;
            str.append("a:").append(map.size()).append(":{");
            for (Object key : map.keySet()) {
                serialize(key, str, refs, true);
                serialize(map.get(key), str, refs, false);

            }
            str.append("}");
        } else {
            throw new IllegalStateException("The object type '" + obj.getClass().getName() + "' is not supported");
        }
    }

    /**
     * The unserialize class method takes a string containing a serialized php variable,
     * unserializes it and returns it as a java object mapped to the appropriate type
     *
     * @param serializedString A String of serialized text as returned by the PHP serialize() function

     * @return A java object representing the PHP variable that was in the serialized string
     * @throws IllegalStateException if an unsupported variable type was encountered in the serialized string
     */
    public static Object unserialize(String serializedString)
            throws IllegalStateException {
        Object var;
        Stack objVars = new Stack();
        StringTokenizer varTokens = new StringTokenizer(serializedString, ":");


        while (varTokens.hasMoreTokens()) {
            /* determine variable type */
            String typeToken = varTokens.nextToken(":{};");

            switch (typeToken.charAt(0)) {
                case 'a':

                    Map array = new HashMap();
                    /* store the array element counts in the map */
                    array.put(COUNT_KEY, Integer.valueOf(varTokens.nextToken(":")));
                    /* this must be an array */

                    objVars.push(array);
                    break;
                case 'b':
                    var = Boolean.valueOf(varTokens.nextToken(":;").equals("1"));
                    if (objVars.empty()) {
                        /* this must be a standalone boolean */

                        return var;
                    } else {
                        /* add this boolean to its array */
                        addVarToArray(objVars, var, 'b');
                    }
                    break;
                case 'd':
                    var = Double.valueOf(varTokens.nextToken(":;"));

                    if (objVars.empty()) {
                        /* this must be a standalone double */
                        return var;
                    } else {
                        /* add this double to its array */
                        addVarToArray(objVars, var, 'd');

                    }
                    break;
                case 'i':
                    var = Integer.valueOf(varTokens.nextToken(":;"));
                    if (objVars.empty()) {
                        /* this must be a standalone integer */
                        return var;

                    } else {
                        /* add this integer to its array */
                        addVarToArray(objVars, var, 'i');
                    }
                    break;
                case 'N':
                    var = null;
                    if (objVars.empty()) {

                        /* this must be a standalone null */
                        return var;
                    } else {
                        /* add this null to its array */
                        addVarToArray(objVars, var, 'N');
                    }
                    break;

                /* for now we disable PHP objects which are not yet implemented
                case 'O':
                // to-do: a simple implementation that maps PHP objects into a Java Map
                break;
                 */
                case 's':

                    int len = Integer.parseInt(varTokens.nextToken(":")) + 2; /* the string length including quotes */
                    StringBuilder varStr = new StringBuilder(varTokens.nextToken(":;"));
                    while (varStr.length() < len) {

                        /* append the last delimiter since it was within the string */
                        varStr.append(";");
                        varStr.append(varTokens.nextToken(";"));
                    }
                    varStr.setLength(varStr.length() - 1); /* strip trailing string quotes */

                    varStr.deleteCharAt(0); /* get rid of leading string quotes */
                    var = varStr;
                    if (objVars.empty()) {
                        /* this must be a standalone string */
                        return var;
                    } else {

                        /* add this string to its array */
                        addVarToArray(objVars, var, 's');
                    }
                    break;
                default:
                    /* we shouldn't be here */
                    throw new IllegalStateException("Unexpected token found: " + typeToken);

            }

            /* build nested arrays or return final array */
            if (objVars.peek().getClass().equals(HashMap.class)) {
                int count = (Integer) ((Map) objVars.peek()).get(COUNT_KEY);
                if (count == 0) {

                    /* get the last array stored */
                    var = objVars.pop();
                    /*we no longer need the element count */
                    ((Map) var).remove(COUNT_KEY);
                    if (objVars.empty()) {
                        /* this must be a standalone array */

                        return var;
                    } else {
                        addVarToArray(objVars, var, 'a');
                    }
                }
            }
        }

        return null;
    }

    /* helper method for unserialize */
    protected static void addVarToArray(Stack objVars, Object var, char varType) {
        /* if the object is a valid key type and previous object was not a key */
        if ((varType == 'i' || varType == 's') && objVars.peek().getClass().equals(HashMap.class)) {

            /* this object must be an array key
             * push it until we get its value
             */
            objVars.push(var);
        } else {
            /* this object must be an array value */
            Object key = objVars.pop();

            Map array = (Map) objVars.peek();
            array.put(key, var);

            /* decrement our array element count */
            int count = (Integer) array.get(COUNT_KEY);
            array.put(COUNT_KEY, new Integer(count - 1));

        }
    }
}