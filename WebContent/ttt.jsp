<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-store");
response.setDateHeader("Expires", 0);
%>

<%@ page import="java.awt.Color,java.awt.Font,java.awt.Graphics2D, java.awt.font.FontRenderContext, java.awt.geom.Rectangle2D, java.awt.image.BufferedImage"%>

<%@ page import="java.io.File"%>
<%@ page import="javax.imageio.ImageIO"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%
out.print(System.getProperty("sun.jnu.encoding"));
render();
%>

<%!
public  void render() throws Exception {  
    int width = 100;  
    int height = 100;  
      
    String s1 = "时段";  
//  String s2 = new String("你好".getBytes(System.getProperty("sun.jnu.encoding")), "UTF-8");  
//  String s3 = new String("你好".getBytes("GBK"), System.getProperty("sun.jnu.encoding"));  
//  String s4 = new String("你好".getBytes(), System.getProperty("sun.jnu.encoding"));  

    File file = new File("/web/a05.jpg");  

    Font font = new Font("Serif", Font.BOLD, 10);  
    BufferedImage bi = new BufferedImage(width, height,  
            BufferedImage.TYPE_INT_RGB);  
    Graphics2D g2 = (Graphics2D) bi.getGraphics();  
    g2.setBackground(Color.WHITE);  
    g2.clearRect(0, 0, width, height);  
    g2.setPaint(Color.RED);  

 

    g2.drawString(s1, (int) 50, (int) 50);  

    ImageIO.write(bi, "jpg", file);  
}  


%>
