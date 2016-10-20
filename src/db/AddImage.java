package db;

import java.awt.EventQueue;
import java.awt.Graphics;
//import java.awt.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class AddImage {

	public static void setImage(String file, int id) throws FileNotFoundException, IOException, SQLException {
		Connection c = Connector.c;
		if (c == null || c.isClosed())
			c = Connector.getConnection();
		PreparedStatement ps = null;

		int s = 0;
		byte[] person_image = null;
		File image = new File(file);
		FileInputStream fis = new FileInputStream(image);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];

		try {
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum);
				System.out.println("read " + readNum + " bytes,");
			}
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		person_image = bos.toByteArray();

		try {
			c = Connector.getConnection();
			ps = c.prepareStatement("INSERT INTO img (id, image) VALUES (?, ?)");
			ps.setInt(1, id);
			ps.setBytes(2, person_image);
			s = ps.executeUpdate();
			if (s > 0) {
				System.out.println("Image Uploaded");
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static WritableImage getImage(int id) throws FileNotFoundException, IOException, SQLException {

		Connection c = Connector.c;
		if (c == null || c.isClosed())
			c = Connector.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		byte[] person_image = null;
		try {
			c = Connector.getConnection();
			ps = c.prepareStatement("SELECT image FROM img WHERE id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				person_image = rs.getBytes(1);
			}
			ps.close();
			
			// convert byte array back to BufferedImage
			InputStream in = new ByteArrayInputStream(person_image);
			BufferedImage img = ImageIO.read(in);
			ps.close();
			WritableImage wi = null;
			SwingFXUtils.toFXImage(img, wi);
			return wi;

		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

}
