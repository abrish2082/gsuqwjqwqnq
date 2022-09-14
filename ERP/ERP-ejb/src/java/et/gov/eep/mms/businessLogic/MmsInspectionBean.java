/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.mms.entity.MmsInspection;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsInspectionFacade;

/**
 *
 * @author user
 */
@Stateless
public class MmsInspectionBean implements MmsInspectionBeanLocal {

    @EJB
    MmsInspectionFacade inspectionFacade;

    /**
     *
     * @param inspection
     */
    @Override
    public void create(MmsInspection inspection) {
        inspectionFacade.create(inspection);
    }

    /**
     *
     * @param papmsInspection
     */
    @Override
    public void edit(MmsInspection papmsInspection) {
        inspectionFacade.edit(papmsInspection);
    }

    /**
     *
     * @param storeinfo
     * @return
     */
    @Override
    public ArrayList<MmsStoreInformation> searchStoreName(MmsStoreInformation storeinfo) {
        return inspectionFacade.searchStoreInformation(storeinfo);
    }

    /**
     *
     * @param inspection
     * @return
     */
    @Override
    public ArrayList<MmsInspection> searchByinspectionNumber(MmsInspection inspection) {
        return inspectionFacade.searchByinspectionNo(inspection);

    }

    @Override
    public ArrayList<MmsInspection> searchByinspectionNoAndProcessedBy(MmsInspection inspection) {
        return inspectionFacade.searchByinspectionNoAndProcessedBy(inspection);

    }

    /**
     *
     * @return
     */
    @Override
    public MmsInspection getLastinspectionNo() {
        return inspectionFacade.getLastinspectionNo();
    }

    /**
     *
     * @param inspection
     * @return
     */
    @Override
    public List<MmsInspection> searchAllByPreparerId(MmsInspection inspection) {
        return inspectionFacade.searchAllByPreparerId(inspection);
    }

    /**
     *
     * @param az
     * @return
     */
    @Override
    public List<MmsInspection> findAllByStatus(int az) {
        return inspectionFacade.findAllByStatus(az);
    }

    /**
     *
     * @param selectedInspNo
     * @return
     */
    @Override
    public MmsInspection getInspectionInfoByInspNo(String selectedInspNo) {
        return inspectionFacade.getByInspectionNumber(selectedInspNo);
    }

    /**
     *
     * @param inspection
     * @return
     */
    @Override
    public ArrayList<MmsInspection> searchByinspectionNumber(String inspection) {
        return inspectionFacade.searchByinspectionNo(inspection);
    }

    /**
     *
     * @param a
     * @return
     */
    @Override
    public String getinspectionIdbyInspectionNo(String a) {
        return inspectionFacade.getInpsectionId(a);

    }

    @Override
    public List<MmsInspection> searchInspectionByParameterInspectionNo(MmsInspection inspection) {
        return inspectionFacade.searchInspectionByParameterInspectionNo(inspection);
    }

    @Override
    public MmsInspection getInspectionInfoByInspId(MmsInspection inspection) {
        return inspectionFacade.getInspectionInfoByInspId(inspection);
    }

//    @Override
//    public List<MmsInspection> findAll() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    @Override
    public List<MmsInspection> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MmsInspection> findInspectionListByWfStatus(int PREPARE_VALUE) {
        return inspectionFacade.findInspectionListByWfStatus(PREPARE_VALUE);
    }

    @Override
    public List<MmsInspection> findinspactionNo(MmsInspection inspaction) {
        return inspectionFacade.findinspactionNo(inspaction);
    }

    @Override
    public List<MmsInspection> findAllinsNo() {
        return inspectionFacade.findAll();
    }

    @Override
    public List<String> getMmsInspectionColumnNameList() {
        return inspectionFacade.getMmsInspectionColumnNameList();
    }

    @Override
    public List<MmsInspection> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, MmsInspection inspection, String columnValue) {
        return inspectionFacade.searchByCol_NameAndCol_Value(columnNameResolver,inspection, columnValue);
    }

    

}
