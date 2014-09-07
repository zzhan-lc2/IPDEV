package com.ipdev.apps;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ArgsUtils {
    /** Appends the given argument onto the args array, returning a new args String[]. */
    public static String[] addArgument(String[] existingArgs, String additionalArgument) {
        List<String> newArgs = new ArrayList<String>();
        if (existingArgs != null) {
            newArgs.addAll(Arrays.asList(existingArgs));
        } else {
            existingArgs = new String[] {};
        }
        newArgs.add(additionalArgument);
        return newArgs.toArray(existingArgs);
    }

    /**
     * Appends the given additionalArguments, returning a new args String[].
     */
    public static String[] addArguments(String[] existingArgs, String... additionalArgs) {
        String[] result = existingArgs;
        for (String arg : additionalArgs) {
            result = addArgument(result, arg);
        }
        return result;
    }

    /**
     * Return true if the given arg is provided; false otherwise. The given argument list is checked for anything equal
     * to '-arg', '--arg', or starting with '-arg=' or '--arg='.
     */
    public static boolean argumentProvided(String[] args, String arg) {
        if (arg == null || arg.trim().equals("")) {
            return false;
        }

        for (String currentArg : args) {
            if (currentArg.equals("--" + arg) || currentArg.equals("-" + arg) ||
                currentArg.startsWith("--" + arg + "=") || currentArg.startsWith("-" + arg + "=")) {
                return true;
            }
        }

        return false;
    }

    /** Return true if the given arg is not provided in args; false otherwise. */
    public static boolean argumentNotProvided(String[] args, String arg) {
        return !argumentProvided(args, arg);
    }

    /** Applies default arguments, if they are not already given in the arguments list. **/
    public static String[] addDefaultArguments(String[] args, Map<String, String> defaults) {
        for (String key : defaults.keySet()) {
            if (argumentNotProvided(args, key)) {
                String value = defaults.get(key);
                args = addArgument(args, "--" + key + "=" + value);
            }
        }
        return args;
    }

    public static void putEnvVar(String key, String value) {

        try {
            Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
            Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
            theEnvironmentField.setAccessible(true);

            @SuppressWarnings("unchecked")
            Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
            Field theCaseInsensitiveEnvironmentField = processEnvironmentClass
                .getDeclaredField("theCaseInsensitiveEnvironment");
            theCaseInsensitiveEnvironmentField.setAccessible(true);

            @SuppressWarnings("unchecked")
            Map<String, String> cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);

            env.put(key, value);
            cienv.put(key, value);
        } catch (NoSuchFieldException e) {
            try {
                Class<?>[] classes = Collections.class.getDeclaredClasses();
                Map<String, String> env = System.getenv();
                for (Class<?> cl : classes) {
                    if ("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
                        Field field = cl.getDeclaredField("m");
                        field.setAccessible(true);
                        Object obj = field.get(env);

                        @SuppressWarnings("unchecked")
                        Map<String, String> map = (Map<String, String>) obj;
                        map.put(key, value);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
