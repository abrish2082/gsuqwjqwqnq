package et.gov.eep.commonApplications.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.commons.io.FileUtils;
import org.insa.client.DmsHandler;
import org.insa.model.DocList;
import org.insa.model.DocumentModel;
import org.primefaces.component.fileupload.FileUploadRenderer;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.DefaultStreamedContent;
import et.gov.eep.commonApplications.entity.HrLuDmArchive;
import et.gov.eep.commonApplications.entity.MmsLuDmArchive;
import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import org.primefaces.event.SelectEvent;
import org.insa.util.StringDateManipulation;
import insa.org.et.security.Utility;
import javax.inject.Inject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author admin
 */
@Named("dataUpload")
@ViewScoped
public class DataUpload extends FileUploadRenderer implements Serializable {

    @Inject
    HrLuDmArchive hrLuDmArchive;
    @Inject
    PrmsLuDmArchive prmsLuDmArchive;
    @Inject
    MmsLuDmArchive mmsLuDmArchive;

    StreamedContent file;
    StreamedContent hrmsFileRefNumber;
    StreamedContent prmsFileRefNumber;
    StreamedContent mmsFileRefNumber;

    public HrLuDmArchive getHrLuDmArchive() {
        if (hrLuDmArchive == null) {
            hrLuDmArchive = new HrLuDmArchive();
        }
        return hrLuDmArchive;
    }

    public void setHrLuDmArchive(HrLuDmArchive hrLuDmArchive) {
        this.hrLuDmArchive = hrLuDmArchive;
    }

    public PrmsLuDmArchive getPrmsLuDmArchive() {
        if (prmsLuDmArchive != null) {
            prmsLuDmArchive = new PrmsLuDmArchive();
        }
        return prmsLuDmArchive;
    }

    public void setPrmsLuDmArchive(PrmsLuDmArchive prmsLuDmArchive) {
        this.prmsLuDmArchive = prmsLuDmArchive;
    }

    public MmsLuDmArchive getMmsLuDmArchive() {
        if (mmsLuDmArchive == null) {
            mmsLuDmArchive = new MmsLuDmArchive();
        }
        return mmsLuDmArchive;
    }

    public void setMmsLuDmArchive(MmsLuDmArchive mmsLuDmArchive) {
        this.mmsLuDmArchive = mmsLuDmArchive;
    }

// @PostConstruct
    public void _init() {
        populateData();

    }
    // <editor-fold defaultstate="collapsed" desc="DMS service">

    DocumentModel documentModel = new DocumentModel();
    DataUpload uploadSelection;

    public DocumentModel getDocumentModel() {
        return documentModel;
    }

    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }

    public DataUpload getUploadSelection() {
        if (uploadSelection == null) {
            uploadSelection = new DataUpload();
        }
        return uploadSelection;
    }

    public void setUploadSelection(DataUpload uploadSelection) {
        this.uploadSelection = uploadSelection;
    }

    DataModel<DocumentModel> docValueModel;

    public void populateData() {
        DmsHandler dmsHandler = new DmsHandler();
        DocumentModel documentModelInt = new DocumentModel();
        documentModelInt.setUserId(Long.valueOf("2"));
//        documentModelInt.setCatagoryId(Utility.CATAGORY_ID);

        DocList docList = dmsHandler.getDocuments(documentModelInt);
        if (docList != null) {
            docValueModel = new ListDataModel(docList.getDocList());
        }
    }

//    public StreamedContent selectFileByDocId(int docId) throws Exception {
//        System.out.println("1");
//        DmsHandler dmsHandler = new DmsHandler();
//        System.out.println("2----");
//        documentModel = dmsHandler.getDocument(docId);
//        System.out.println("3---" + documentModel);
//        return getFile();
//    }
    public DataModel<DocumentModel> selectListOfFileByDocId(List<Integer> lstdocId) {
        DmsHandler dmsHandler = new DmsHandler();
        DocList doclst;
        List<String> listOfDoc = new ArrayList<>();
        for (int i = 0; i < lstdocId.size(); i++) {
            doclst = new DocList();
            System.out.println("U sending Id No===" + lstdocId.get(i));
//            DL=dmsHandler.equals(documentModel.getName());
//            System.out.println("gxvcxv----" + dmsHandler.getDocument(lstdocId.get(i)));
//            docModel = dmsHandler.getDocument(lstdocId.get(i));
            listOfDoc.add(lstdocId.get(i).toString());
            doclst.setDocIds(listOfDoc);
//            docModel=dmsHandler.getDocument(lstdocId.get(i));
            System.out.println("DBMS decided to Look file by Ur ID===" + lstdocId.get(i).toString());
            System.out.println(" doc model===" + doclst);
            //listDoc.add(docModel);
            DocList docListNew = dmsHandler.getDocumentsById(doclst);
//            DocList docListNew = comLuDmArchive.setUploadFile(doclst);
//            docValueModel = new ListDataModel(listDoc);
            if (docListNew != null) {
                docValueModel = new ListDataModel(docListNew.getDocList());
                System.out.println("number of my file----------" + listOfDoc.size());
                System.out.println("u are my file------------" + docValueModel);
            }
        }
        return getDocValueModel();
    }

    public DataModel<DocumentModel> getDocValueModel() {
        if (docValueModel == null) {
            docValueModel = new ArrayDataModel<>();
        }
        return docValueModel;
    }

    public void setDocValueModel(DataModel<DocumentModel> docValueModel) {
        this.docValueModel = docValueModel;
    }

    public void onRowSelect(SelectEvent event) {
        documentModel = (DocumentModel) event.getObject();
    }

//    public int uploadListener1(FileUploadEvent event) {
//        try {
//            DmsHandler dmsHandler = new DmsHandler();
//            documentModel = new DocumentModel();
//            documentModel.setDocFormat(event.getFile().getFileName().split("\\.")[1]);
//            documentModel.setName(event.getFile().getFileName().split("\\.")[0]);
//            documentModel.setUserId(Long.valueOf("2"));
//            documentModel.setCatagoryId(Utility.CATAGORY_ID);
//            documentModel.setCreater("from client");
//             File fileDoc = new File(event.getFile().getFileName());
//            FileUtils.writeByteArrayToFile(fileDoc, dmsHandler.extractByteArray1(event.getFile().getInputstream()));
//            documentModel.setDate(StringDateManipulation.todayInEC());
//            documentModel.setFile(fileDoc);
//            documentModel.setByteFile(dmsHandler.extractByteArray1(event.getFile().getInputstream()));
//            
//            int x = dmsHandler.saveDocument(documentModel);
//            System.out.println("====doc id===="+x);
////            populateData();
//            return x;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return -1;
//        }
//    }
    public int uploadListener(InputStream fileByteFile_,
            String docFormat, String fileName, String fileNameWzExtent, String categoryBundle) {
        try {
            System.out.println("hello");
            DmsHandler dmsHandler = new DmsHandler();
            documentModel = new DocumentModel();
            documentModel.setDocFormat(docFormat);
            documentModel.setName(fileName);
            documentModel.setUserId(Long.valueOf("2"));
            documentModel.setApp(Utility.getBundleValue(categoryBundle, "categoryName"));
            System.out.println("----App----" + documentModel.getApp());
//            documentModel.setApp(Utility.getBundleValue(categoryBundle, "categoryName"));
//            documentModel.setCatagoryId(Utility.CATAGORY_ID);
            documentModel.setCreater("Prmss");
            File fileDoc = new File(fileNameWzExtent);
            FileUtils.writeByteArrayToFile(fileDoc, dmsHandler.extractByteArray1(fileByteFile_));
            documentModel.setDate(StringDateManipulation.todayInEC());
            documentModel.setFile(fileDoc);
            documentModel.setByteFile(dmsHandler.extractByteArray1(fileByteFile_));
            System.out.println("here is");
            int x = dmsHandler.saveDocument(documentModel);
            System.out.println("====doc id====" + x);
//            populateData();
            return x;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public StreamedContent getFile(DocumentModel documentModel) throws Exception {
        System.out.println("12");
        if (documentModel != null) {
            System.out.println("22");
            File fileDoc = new File(""
                    + documentModel.getName() + "."
                    + documentModel.getDocFormat());
            System.out.println(" File Name---" + documentModel.getName() + " and " + " DocType--" + documentModel.getDocFormat() + "Full Document---" + fileDoc);
            if (documentModel.getByteFile() != null) {
                FileUtils.writeByteArrayToFile(fileDoc, documentModel.getByteFile());
                System.out.println("file byte---" + documentModel.getByteFile());
            }
            System.out.println("Btw no file byte(0 byte)");
            InputStream input = new FileInputStream(fileDoc);
            System.out.println("33---" + fileDoc);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            System.out.println("34" + fileDoc.getName());
            System.out.println("35" + documentModel.getName());
            System.out.println("36" + documentModel.getDocFormat());
//             file = new DefaultStreamedContent(input, externalContext.getMimeType(fileDoc.getName()), documentModel.getDocFormat());
            file = new DefaultStreamedContent(input, externalContext.getMimeType(fileDoc.getName()), documentModel.getName());
            System.out.println("file---" + file);
            return file;

        }
        return null;
    }

    public byte[] extractByteArray(InputStream inputStream) {
        try {
            System.out.println("Extract Method");
            byte[] byteFile = null;
            int length = 0;
            length = inputStream.available();
            byteFile = new byte[length];
            inputStream.read(byteFile, 0, length);
            System.out.println("byteFile " + byteFile);
            return byteFile;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public StreamedContent getHrmsFileRefNumber(HrLuDmArchive hrmsLuDmArchive) {
        try {
            if (hrmsLuDmArchive != null) {
                System.out.println("possesed Document Id == " + hrmsLuDmArchive);
                File filePath = new File(""
                        + hrmsLuDmArchive.getFileName() + "."
                        + hrmsLuDmArchive.getFileType());
                if (hrmsLuDmArchive.getUploadFile() != null) {
                    FileUtils.writeByteArrayToFile(filePath, hrmsLuDmArchive.getUploadFile());
                    System.out.println("Byte File " + hrmsLuDmArchive.getUploadFile() + " File Name With its Extension " + filePath);
                }
                InputStream input = new FileInputStream(filePath);
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                hrmsFileRefNumber = new DefaultStreamedContent(input, externalContext.getMimeType(filePath.getName()), hrmsLuDmArchive.getFileName() + "." + hrmsLuDmArchive.getFileType());
                System.out.println("file===" + hrmsFileRefNumber);
            }
            return hrmsFileRefNumber;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public void setHrmsFileRefNumber(StreamedContent hrmsFileRefNumber) {
        this.hrmsFileRefNumber = hrmsFileRefNumber;
    }

    public StreamedContent getPrmsFileRefNumber(PrmsLuDmArchive prmsLuDmArchive) {
        try {
            if (prmsLuDmArchive != null) {
                System.out.println("possesed Document Id == " + prmsLuDmArchive);
                File filePath = new File(""
                        + prmsLuDmArchive.getFileName() + "."
                        + prmsLuDmArchive.getFileType());
                if (prmsLuDmArchive.getUploadFile() != null) {
                    FileUtils.writeByteArrayToFile(filePath, prmsLuDmArchive.getUploadFile());
                    System.out.println("Byte File " + prmsLuDmArchive.getUploadFile() + " File Name With its Extension " + filePath);
                }
                InputStream input = new FileInputStream(filePath);
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                prmsFileRefNumber = new DefaultStreamedContent(input, externalContext.getMimeType(filePath.getName()), prmsLuDmArchive.getFileName());
                System.out.println("file===" + prmsFileRefNumber);
            }
            return prmsFileRefNumber;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public void setPrmsFileRefNumber(StreamedContent prmsFileRefNumber) {
        this.prmsFileRefNumber = prmsFileRefNumber;
    }

    public StreamedContent getMmsFileRefNumber(MmsLuDmArchive mmsLuDmArchive) {
        try {
            if (mmsLuDmArchive != null) {
                System.out.println("possesed Document Id == " + mmsLuDmArchive);
                File filePath = new File(""
                        + mmsLuDmArchive.getFileName() + "."
                        + mmsLuDmArchive.getFileType());
                if (mmsLuDmArchive.getUploadFile() != null) {
                    FileUtils.writeByteArrayToFile(filePath, mmsLuDmArchive.getUploadFile());
                    System.out.println("Byte File " + mmsLuDmArchive.getUploadFile() + " File Name With its Extension " + filePath);
                }
                InputStream input = new FileInputStream(filePath);
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                mmsFileRefNumber = new DefaultStreamedContent(input, externalContext.getMimeType(filePath.getName()), mmsLuDmArchive.getFileName());
                System.out.println("file===" + mmsFileRefNumber);
            }
            return mmsFileRefNumber;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setMmsFileRefNumber(StreamedContent mmsFileRefNumber) {
        this.mmsFileRefNumber = mmsFileRefNumber;
    }

    public void remove(DocumentModel document) {
        DmsHandler dmsHandler = new DmsHandler();
        dmsHandler.getRemove(document);
        populateData();
    }
    // </editor-fold>

}
