package co.gov.dafp.sigep2.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;

public class ImageBase64Utils {
	public static final String ESPANIOL = "ES";
	
	protected ImageBase64Utils() {
		super();
	}
	
	public static String converterImgBase64(String ruta) {
		
		BufferedImage bImage;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		File foto;
		
		try {
			foto = new File(ConfigurationBundleConstants.getRutaArchivo(ruta));
			bImage = ImageIO.read(foto);
			String ext = FilenameUtils.getExtension(ruta);
			ImageIO.write( bImage, ext, baos );
			baos.flush();
			byte[] imageInByteArray = baos.toByteArray();
			baos.close();
			String b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
			return "data:image/jpg;base64,"+b64;
		} catch (IOException e) {
			return null;
		}
	}
}