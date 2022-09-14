/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import java.util.HashMap;

/**
 *
 * @author user
 */
public class Singleton {

    private static Singleton uniqueInstance;
//    private static Singleton uniqueInstance = new Singleton();

    /**
     *
     */
    protected Singleton() {
    }

    /**
     *
     * @return
     */
    public static synchronized Singleton getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton();
        }
        return uniqueInstance;
    }
//    public static Singleton getInstance() {
//        if (uniqueInstance == null) {
//            uniqueInstance = new Singleton();
//        }
//        return uniqueInstance;
//    }

    private HashMap param;
    private String format;
    private String fileName;
    private String reportName;
    private String DownloadedReportName;
    private String reportIndex;

    /**
     *
     * @return
     */
    public String getFormat() {
        return format;
    }

    /**
     *
     * @param format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     *
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     *
     * @return
     */
    public HashMap getParam() {
        if (param == null) {
            param = new HashMap();
        }
        return param;
    }

    /**
     *
     * @param param
     */
    public void setParam(HashMap param) {
        this.param = param;
    }

    /**
     *
     * @return
     */
    public String getReportIndex() {
        return reportIndex;
    }

    /**
     *
     * @param reportIndex
     */
    public void setReportIndex(String reportIndex) {
        this.reportIndex = reportIndex;
    }

    /**
     *
     * @return
     */
    public String getReportName() {
        return reportName;
    }

    /**
     *
     * @param reportName
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    /**
     *
     * @return
     */
    public String getDownloadedReportName() {
        return DownloadedReportName;
    }

    /**
     *
     * @param DownloadedReportName
     */
    public void setDownloadedReportName(String DownloadedReportName) {
        this.DownloadedReportName = DownloadedReportName;
    }

    /**
     *
     */
    public void getClearSingletonParameter() {

        param.clear();
        format = null;
        fileName = null;
        DownloadedReportName = null;
        reportIndex = null;
    }
}

