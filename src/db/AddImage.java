package db;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class AddImage {

	public static void setImage(String file) throws FileNotFoundException, IOException, SQLException {
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
			int id = 1;
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
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			
			// convert byte array back to BufferedImage
			InputStream in = new ByteArrayInputStream(person_image);
			BufferedImage img = ImageIO.read(in);
			ps.close();
			return img;

		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
		String file = "/Users/greatwmc/Downloads/Miles Greatwood _ Facebook_files/test.jpg";
		setImage(file);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ImageFrame frame = new ImageFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);

			}
		});
	}
}

class ImageFrame extends JFrame {

	public ImageFrame() {
		setTitle("ImageTest");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		ImageComponent component = new ImageComponent();
		add(component);

	}

	public static final int DEFAULT_WIDTH = 300;
	public static final int DEFAULT_HEIGHT = 200;
}

class ImageComponent extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;

	public ImageComponent() {
		try {
			//File image2 = new File("/Users/greatwmc/Downloads/Miles Greatwood _ Facebook_files/test2.jpg");
			image = AddImage.getImage(1);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		if (image == null)
			return;
		int imageWidth = image.getWidth(this);
		int imageHeight = image.getHeight(this);

		g.drawImage(image, 50, 50, this);

		for (int i = 0; i * imageWidth <= getWidth(); i++)
			for (int j = 0; j * imageHeight <= getHeight(); j++)
				if (i + j > 0)
					g.copyArea(0, 0, imageWidth, imageHeight, i * imageWidth, j * imageHeight);
	}
}
