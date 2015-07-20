package bp.contrib.bfs;

public class NamingConventions {

	/*
	 * Naming conventions for using Java reflection.
	 */

	public static String toFieldName(String varName) {
		Character c = Character.toLowerCase(varName.charAt(0));
		return c + varName.substring(1);
	}

	public static String toClassName(String varName) {
		Character c = Character.toUpperCase(varName.charAt(0));
		return c + varName.substring(1);
	}

	public static String updateMethodName(String varName) {
		Character c = Character.toUpperCase(varName.charAt(0));
		String methodName = "update" + c + varName.substring(1);

		return methodName;
	}

	public static String getMethodName(String varName) {
		Character c = Character.toUpperCase(varName.charAt(0));
		String methodName = "get" + c + varName.substring(1);

		return methodName;
	}

	public static String pkgName(String varName) {
		Character c = Character.toLowerCase(varName.charAt(0));
		String pkgName = "events.LingVarEvents." + c + varName.substring(1) + ".";

		return pkgName;
	}
}
