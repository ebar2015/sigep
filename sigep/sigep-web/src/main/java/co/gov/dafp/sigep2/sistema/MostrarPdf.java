/**
 * 
 */
package co.gov.dafp.sigep2.sistema;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

public class MostrarPdf extends HttpServlet implements Serializable {
    private static final long serialVersionUID = 4638096717114000276L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	Base64 b64 = new Base64();
	String pdfFileName = request.getParameter("path");
	try {
	    String contextPath = new String(b64.decode(pdfFileName.getBytes()));
	    File pdfFile = new File(contextPath);
	    if (pdfFile.exists()) {
		performTask(request, response, pdfFile);
	    } else {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		pw.println("<HTML><HEAD><TITLE>Leyendo parámetros</TITLE></HEAD>");
		pw.println("<BODY BGCOLOR=\"#F2F2F2\">");
		pw.println("<H2>El Archivo Que Intenta abrir no Existe</H2><P>");
		pw.println("</BODY></HTML>");
		pw.close();
	    }
	} catch (NullPointerException e) {
	    response.setContentType("text/html");
	    PrintWriter pw = response.getWriter();
	    pw.println("<HTML><HEAD><TITLE>Sigep-Web</TITLE></HEAD>");
	    pw.println("<BODY BGCOLOR=\"#F2F2F2\">");
	    pw.println("<H2>Error al Leer la ruta del Archivo</H2><P>");
	    pw.println("</BODY></HTML>");
	    pw.close();
	}

    }

    private void performTask(HttpServletRequest request, HttpServletResponse response, File pdfFile)
	    throws ServletException, IOException {
	response.setContentType("application/pdf");
	response.addHeader("Content-Disposition", "filename=" + pdfFile.getName());
	response.setContentLength((int) pdfFile.length());
	FileInputStream fileInputStream = new FileInputStream(pdfFile);
	OutputStream responseOutputStream = response.getOutputStream();
	int bytes;
		while ((bytes = fileInputStream.read()) != -1) {
		    responseOutputStream.write(bytes);
		}
    }

}
