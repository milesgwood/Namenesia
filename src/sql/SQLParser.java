package sql;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SQLParser {

	/**
	 * Create the needed SQL strings from a saved sql file.
	 * @param sqlFile
	 * @return
	 */
	public static String sqlStringCreation(String sqlFile) {

		BufferedReader br = null;
		String sqlString = "";

		try {
			String sCurrentLine;
			StringBuilder f = new StringBuilder();
			br = new BufferedReader(new FileReader("./src/sql/" + sqlFile));
			while ((sCurrentLine = br.readLine()) != null) {
				f.append(sCurrentLine);
			}
			sqlString = f.toString();
			System.out.println(sqlString);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sqlString;
	}
	
	/**
	public static String sqlPrintForCopy(String sqlFile) {

		BufferedReader br = null;
		String sqlString = "";

		try {
			String sCurrentLine;
			StringBuilder f = new StringBuilder();
			br = new BufferedReader(new FileReader("./src/sql/" + sqlFile));
			while ((sCurrentLine = br.readLine()) != null) {
				f.append(" \" " + sCurrentLine + "\" + \n");
			}
			sqlString = f.substring(0, f.length() - 3);
			System.out.println(sqlString);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sqlString;
	}
	**/
}
