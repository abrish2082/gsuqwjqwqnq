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
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsGrnFacade;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsGRNBEan implements MmsGRNBEanLocal {

    @EJB
    MmsGrnFacade grnfacade;

    /**
     *
     * @param mmsgrn
     */
    @Override
    public void create(MmsGrn mmsgrn) {
        grnfacade.create(mmsgrn);
    }

    /**
     *
     * @param grn
     * @return
     */
    @Override
    public ArrayList<MmsGrn> searchByGrn(MmsGrn grn) {
        return grnfacade.searchGrnNumber(grn);
    }

    /**
     *
     * @param grnNo
     * @return
     */
    @Override
    public MmsGrn getbyGrnNo(MmsGrn grnNo) {
        return grnfacade.getByGrnNo(grnNo);
    }

    /**
     *
     * @param selectedGrnNo
     * @return
     */
    @Override
    public MmsGrn getGrnInfoByGrnNo(String selectedGrnNo) {
        return grnfacade.getGrnInfoByGrnNo(selectedGrnNo);
    }

    /**
     *
     * @param mmsgrn
     */
    @Override
    public void edit(MmsGrn mmsgrn) {
        grnfacade.edit(mmsgrn);
    }

    /**
     *
     * @return
     */
    @Override
    public MmsGrn getLastinspectionNo() {
        return grnfacade.getLastGrnNo();
    }

    /**
     *
     * @param selectedGrnNo
     * @return
     */
    @Override
    public String getAllDatawithinspectionResult(String selectedGrnNo) {
        return grnfacade.getAllinspectionData(selectedGrnNo);

    }

    /**
     *
     * @param storeInformation
     * @return
     */
    @Override
    public ArrayList<MmsGrn> approvedGrnList(MmsStoreInformation storeInformation) {
        return grnfacade.approvedGrnList(storeInformation);
    }

    @Override
    public ArrayList<MmsGrn> approvedGrnListByStoreAndStatus(MmsGrn grn, int status) {
        return grnfacade.approvedGrnListByStoreAndStatus(grn, status);
    }

    @Override
    public MmsGrn getbyGrnID(MmsGrn grnId) {
        return grnfacade.findByGrnId(grnId);
    }

    @Override
    public List<MmsGrn> searchByParameterStore(MmsStoreInformation storeInfoEntity) {
        return grnfacade.searchByParameterStore(storeInfoEntity);
    }

    @Override
    public List<MmsGrn> searchByParameterStoreAndProcessedBy(MmsStoreInformation storeInfoEntity) {
        return grnfacade.searchByParameterStoreAndProcessedBy(storeInfoEntity);
    }

    @Override
    public List<MmsGrn> searchByParameterStoreAndGrnNo(MmsStoreInformation storeInfoEntity, MmsGrn mmsgrn) {
        return grnfacade.searchByParameterStoreAndGrnNo(storeInfoEntity, mmsgrn);
    }

    @Override
    public List<MmsGrn> searchByParameterStoreAndGrnNoAndProcessedBy(MmsStoreInformation storeInfoEntity, MmsGrn mmsgrn) {
        return grnfacade.searchByParameterStoreAndGrnNoAndProcessedBy(storeInfoEntity, mmsgrn);
    }

    @Override
    public List<MmsGrn> findAll() {
        return grnfacade.findAll();
    }

    @Override
    public List<MmsGrn> findGRNListByWfStatus(int status) {
        return grnfacade.findGRNListByWfStatus(status);
    }

    @Override
    public List<String> getMmsGrnColumnNameList() {
       return grnfacade.getMmsGrnColumnNameList();
    }

   

    @Override
    public List<MmsGrn> getGrnListsByParameter(ColumnNameResolver columnNameResolver, MmsGrn mmsgrn, String columnValue) {
        return grnfacade.getGrnListsByParameter(columnNameResolver,  mmsgrn, columnValue);
    }
}
