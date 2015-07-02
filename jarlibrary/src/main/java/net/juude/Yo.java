package net.juude;

import java.util.Properties;

public class Yo {
	public static void main(String[] args) {
		System.out.println("Yo");
	}

	public static String Yo() {
        StringBuilder sb = new StringBuilder();
        Properties properties = System.getProperties();
        for (Object key : properties.keySet()) {
            sb.append(" " + key + " : " + properties.get(key) + "\n");
        }
        return "Yo" + sb.toString();
	}
}
