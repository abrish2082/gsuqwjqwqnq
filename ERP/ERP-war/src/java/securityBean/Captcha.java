/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securityBean;

import securityBean.SessionBean;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.SecureRandom;
import java.text.AttributedString;
import java.util.Arrays;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Hilwa
 */
@Named("captcha")
@RequestScoped

public class Captcha implements Serializable{
    
    @Inject
    SessionBean sessionBean;
    
    private String txtCaptcha;

    public String getTxtCaptcha() {
        return txtCaptcha;
    }

    public void setTxtCaptcha(String txtCaptcha) {
        this.txtCaptcha = txtCaptcha;
    }
    
    private StreamedContent imageCaptcha ;

    public StreamedContent getImageCaptcha() {
        return imageCaptcha;
    }

    public void setImageCaptcha(StreamedContent imageCaptcha) {
        this.imageCaptcha = imageCaptcha;
    } 
    public void setCaptchaImage() {
        imageCaptcha = null;
        setImageCaptcha (new DefaultStreamedContent(null, "image/png"));
        ServletContext theApplicationsServletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = theApplicationsServletContext.getRealPath("resources/images");
        
        setImageCaptcha(new DefaultStreamedContent(createCaptcha(realPath), "image/png"));
         
    }

    public InputStream createCaptcha(String realPath) {
        try {
            SecureRandom rdm = new SecureRandom();
            int rl = rdm.nextInt();
            String hash1 = Integer.toHexString(rl);
            int beginIndex = 0;
            int endIndex = 7;
            if (hash1.length() < 7) {
                endIndex = hash1.length();
            }
            String capstr = hash1.substring(beginIndex, endIndex);
            sessionBean.setCaptcha(capstr);
            
            
            
            int width = 200;
            int height = 60;
            Color background = new Color(255, 255, 148);
            Color currentColor = new Color(0, 0, 0);
            Font font = new Font("Bleed", Font.ITALIC, 40);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics newImage = image.createGraphics();
            newImage.setColor(background);
            newImage.fillRect(0, 0, width, height);
            newImage.setColor(currentColor);
            AttributedString attributedString = new AttributedString(capstr);
            attributedString.addAttribute(TextAttribute.FONT, font);
            attributedString.addAttribute(TextAttribute.STRIKETHROUGH,
                    TextAttribute.STRIKETHROUGH_ON);
            newImage.drawString(attributedString.getIterator(), 5, 50);
            newImage.drawLine(1, 47, 169, 22);
            newImage.setColor(background);

            File captcha = new File(realPath + File.separatorChar + "Captcha.jpg");
            ImageIO.write(image, "jpg", captcha);
            
            return new FileInputStream(captcha);
        } catch (Exception ex) {
//            ErrorLogWriter.writeError(ex);
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * get bytes from file
     * @param file
     * @return the contents of the file in a byte array.
     * @throws java.io.IOException
     */
    public static byte[] getBytesFromFile(File file) throws IOException {
          InputStream is = null;
        try {
         is = new FileInputStream(file);
        // Get the size of the file
        long length = file.length();
        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            throw new IOException(" File is too large " + file.getName());
        }
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        
        return bytes;
         } catch (Exception ex) {
//            ErrorLogWriter.writeError(ex);
             ex.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public void btnRefresh_processAction(ActionEvent ae) {
        
        setCaptchaImage();
    }
    
}
