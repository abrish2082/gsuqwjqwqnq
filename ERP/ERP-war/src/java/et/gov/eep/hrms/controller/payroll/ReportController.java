/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author user
 */
@Named(value = "reportController")
@ViewScoped
public class ReportController implements Serializable {

    /**
     * Creates a new instance of ReportController
     */
    public ReportController() {
    }

    /**
     *
     */
    private static final long serialVersionUID = 10200;

//@EJB
//private ServiceLocal service;
    private int code;
    private String name;
//private Grade grade = new Grade();

//public GradeManagedBean(){
//
//}
//public Grade getGrade() {
//    return grade;
//}
//public void setGrade(Grade grade) {
//    this.grade = grade;
//}
//public ServiceLocal getService() {
//    return service;
//}
//public void setService(Service service) {
//    this.service = service;
//}
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public void showReports(){
      String sourceFileName = 
         "C:\\Users\\user\\Desktop\\report2.jasper";
      JRBeanCollectionDataSource beanColDataSource =
      new JRBeanCollectionDataSource(null);
      Map parameters = new HashMap();      /**
       * Passing ReportTitle and Author as parameters
       */
      parameters.put("ReportTitle", "List of Contacts");
      parameters.put("Author", "Prepared By Manisha");
      try {
         JasperFillManager.fillReportToFile(
         sourceFileName, parameters, beanColDataSource);
      } catch (JRException e) {
         e.printStackTrace();
      }
   }
   
    public  void exportReportToPDF( )
        throws JRException, SQLException {
          HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
         File reportFile=new File("C://Users//user//Desktop//report2.jasper");
          File dFile=new File("D://Projects");
          HashMap hm=new HashMap();
 
        response.setContentType("application/pdf");
 
        java.io.FileInputStream fileInputStream = null;
 
        try {
            ServletOutputStream outputStream;
 
            int i;
 
            outputStream = response.getOutputStream();
 
            while ((i = fileInputStream.read()) != -1) {
                outputStream.write(i);
            }
 
            outputStream.flush();
 
            fileInputStream.close();
            outputStream.close();
        } catch (IOException e) {
        }
    }
    
    
    
    public void ret() throws JRException {
        Map<String, Object> parameters = new HashMap<String, Object>();
        JRDataSource dataSource = new JREmptyDataSource();
        JasperCompileManager.compileReport("C:\\Users\\user\\Desktop\\report2..jrxml");
        
            
    JasperPrint print = JasperFillManager.fillReport("C:\\Users\\user\\Desktop\\report2.jasper",parameters, new JREmptyDataSource());
    
      
        File outDir = new File("C://jasperoutput");
        outDir.mkdirs();
// Export to PDF.
        JasperExportManager.exportReportToPdfFile(print, "D://StyledTextReport.pdf");

    }
    
    
    public void genereateAfter(){
    
    }

    public void generate() throws JRException, IOException{
    
     File file=new File("C://Users//user//Desktop//report2.jasper");
     HashMap hm=new HashMap();
      System.out.println("Test 2"+FacesContext.getCurrentInstance().getExternalContext());
     String fileName = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Report3.jasper");
    System.out.println("Test 2");
    File jasper = new File("C://Users//user//Desktop//report2.jasper");
    System.out.println("Test 2"+jasper.getPath());
    JasperPrint print = JasperFillManager.fillReport("C://Users//user//Desktop//report2.jasper",hm, new JREmptyDataSource());
    System.out.println("Test 3");
    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    System.out.println("Test 4");
    response.addHeader("Content-disposition", "attachment; filename=Report2.pdf");
    System.out.println("Test 5");
    ServletOutputStream stream = response.getOutputStream();
    System.out.println("Test 6");
    JasperExportManager.exportReportToPdfStream(print, stream);
    System.out.println("Test 7");
    stream.flush();
    stream.close();
    System.out.println("Test 8");
    FacesContext.getCurrentInstance().responseComplete();
    System.out.println("Test 9");
     
    }
    
    
    public void printReport() throws ClassNotFoundException, IOException, JRException {
        Map parameterMap = new HashMap();
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
        InputStream reportStream = ctx.getExternalContext().getResourceAsStream("/reports/test.jasper");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.flush();
        response.setContentType("application/pdf");
        servletOutputStream.flush();
        servletOutputStream.close();
        ctx.responseComplete();
    }

}
