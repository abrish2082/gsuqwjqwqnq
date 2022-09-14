/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author user
 */
@Named(value = "pdfFromXmlFile")
@ViewScoped
public class PdfFromXmlFile implements Serializable{

    /**
     * Creates a new instance of PdfFromXmlFile
     */
    public PdfFromXmlFile() {
    }

    public void ret() throws JRException {
        JasperReport jasperReport = JasperCompileManager.compileReport("C://Users//user//Desktop//report2.jrxml");
        // Parameters for report
        Map<String, Object> parameters = new HashMap<String, Object>();
        JRDataSource dataSource = new JREmptyDataSource();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                parameters, dataSource);
        File outDir = new File("C:/jasperoutput");
        outDir.mkdirs();
// Export to PDF.
        JasperExportManager.exportReportToPdfFile(jasperPrint, "D://StyledTextReport.pdf");

    }
}
