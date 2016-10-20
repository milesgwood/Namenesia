package db;

import java.awt.Image;
import java.io.*;
import java.sql.*;

public class AddImage {
	
	public static void setImage(String file) throws FileNotFoundException, IOException, SQLException
    {
        Connection c = Connector.c;
        if (c == null || c.isClosed()) c = Connector.getConnection();
        PreparedStatement ps = null;
        
        int s = 0;
        byte[] person_image = null;
        File image = new File(file);
        FileInputStream fis = new FileInputStream(image);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];

        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;)
            {
                bos.write(buf, 0, readNum);
                //no doubt here is 0
                /*Writes len bytes from the specified byte array starting at offset
                off to this byte array output stream.*/
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        person_image = bos.toByteArray();

        try {
        	int id = 1;
        	c = Connector.getConnection();
            ps = c.prepareStatement("INSERT INTO img (id, image) VALUES (?, ?)");
            ps.setInt(1, id);
            ps.setBytes(2, person_image);
            s = ps.executeUpdate();
            if (s > 0)
            {
                System.out.println("Image Uploaded");
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	
	public static void main(String args[]) throws FileNotFoundException, IOException, SQLException
	{	String file = "/Users/greatwmc/Downloads/Miles Greatwood _ Facebook_files/test.jpg";
		setImage(file);
		
		
		Connection c = Connector.c;
        if (c == null || c.isClosed()) c = Connector.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
		byte[] person_image = null;
		try {
        	int id = 1;
        	c = Connector.getConnection();
            ps = c.prepareStatement("SELECT image FROM img WHERE id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next())
            {
            	person_image = rs.getBytes(1);
            }
            ps.close();
            //GET THE IMAGE BACK HERE
            Image img = new ImageIcon(b).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
	
	
}
