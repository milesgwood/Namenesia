package db;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;

import javax.imageio.ImageIO;

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
		} 
	}

	public static Image getImage(int id) throws FileNotFoundException, IOException, SQLException {

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
			
			BufferedImage bf;
			WritableImage wr;			
			
			// convert byte array back to BufferedImage
			InputStream in = new ByteArrayInputStream(person_image);
			bf = ImageIO.read(in);
			//Convert BufferedImage to Scene Image
			wr = new WritableImage(bf.getWidth(), bf.getHeight());
			SwingFXUtils.toFXImage(bf, wr);
			ps.close();
			return wr;

		} catch (NullPointerException e) {
			return getDefaultImage();
		} 
	}

	/** 
	 * Gets the default image that is stored at id 0 in the img database.
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	private static Image getDefaultImage() throws SQLException, IOException {
		
		Connection c = Connector.c;
		if (c == null || c.isClosed())
			c = Connector.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		byte[] person_image = null;

		c = Connector.getConnection();
		ps = c.prepareStatement("SELECT image FROM img WHERE id = 0");
		rs = ps.executeQuery();
		if (rs.next()) {
			person_image = rs.getBytes(1);
		}
		ps.close();
		
		// convert byte array back to BufferedImage
		InputStream in = new ByteArrayInputStream(person_image);
		BufferedImage bf = ImageIO.read(in);
		//Convert BufferedImage to Scene Image
		WritableImage wr = new WritableImage(bf.getWidth(), bf.getHeight());
		SwingFXUtils.toFXImage(bf, wr);
		return wr;
	}
}
