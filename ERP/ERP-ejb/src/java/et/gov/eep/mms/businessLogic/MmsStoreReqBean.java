/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsGrnDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsStorereq;
import et.gov.eep.mms.mapper.MmsStorereqFacade;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsStoreReqBean implements MmsStoreReqBeanLocal {

    @EJB
    MmsStorereqFacade requisitionFacade;

    /**
     *
     * @param papmsStorerq
     */
    @Override
    public void create(MmsStorereq papmsStorerq) {
        requisitionFacade.create(papmsStorerq);
    }

    /**
     *
     * @param PapmsStorereq
     */
    @Override
    public void edit(MmsStorereq PapmsStorereq) {
        requisitionFacade.edit(PapmsStorereq);
    }

    /**
     *
     * @param sr
     * @return
     */
    @Override
    public List<MmsStorereq> searchBySRNo(MmsStorereq sr) {
        return requisitionFacade.searchBySRNumber(sr);
    }

    /**
     *
     * @param selectedSivNo
     * @return
     */
    @Override
    public MmsStorereq getSrInfoBySrvNo(String selectedSivNo) {
        return requisitionFacade.getSrInfoBySrNo(selectedSivNo);
    }

    /**
     *
     * @return
     */
    @Override
    public String getMaxPaymentRequistion() {
        return requisitionFacade.getMaxPaymentRequistion();
    }

    /**
     *
     * @return
     */
    @Override
    public MmsStorereq getLastPaymentNo() {
        return requisitionFacade.getLastPaymentNo();
    }

    /**
     *
     *
     * @param approvedStatus
     * @return
     */
    @Override
    public List<MmsStorereq> findAllwithApprovedStatus(MmsStoreInformation store, int approvedStatus) {

        return requisitionFacade.findAllwithApprovedStatus(store, approvedStatus);

    }

    @Override
    public List<MmsStorereq> findAllwithApprovedStatusForIsiv(MmsStoreInformation store, int approvedStatus) {

        return requisitionFacade.findAllwithApprovedStatusForIsiv(store, approvedStatus);

    }

    /**
     *
     * @param selectedSivNo
     * @return
     */
    @Override
    public MmsStorereq getSrInfo(String selectedSivNo) {
        return requisitionFacade.getBySrnumber(selectedSivNo);
    }

    @Override
    public MmsStorereq searchBySRNos(MmsStorereq sr) {
        return requisitionFacade.searchBySRNO(sr);
    }

    @Override
    public MmsStorereq searchByStoreReqId(MmsStorereq sr) {
        return requisitionFacade.searchByStoreReqId(sr);
    }

    @Override
    public List<MmsStorereq> findAll() {
        return requisitionFacade.findAll();
    }

    @Override
    public List<MmsStorereq> SearchByStoreId(MmsStorereq sr) {
        return requisitionFacade.findAllByStoreId(sr);
    }

    @Override
    public List<MmsStorereq> searchSRByStoreId(MmsStoreInformation storeEntity) {
        return requisitionFacade.searchSRByStoreId(storeEntity);
    }

    @Override
    public List<MmsStorereq> searchBySRNoAndStoreId(MmsStorereq sr, MmsStoreInformation storeEntity) {
        return requisitionFacade.searchBySRNoAndStoreId(sr, storeEntity);
    }

    @Override
    public List<MmsStorereq> searchSRByStoreIdAndProcessedBy(MmsStoreInformation storeEntity, MmsStorereq storereq) {
        return requisitionFacade.searchSRByStoreIdAndProcessedBy(storeEntity, storereq);
    }

    @Override
    public List<MmsStorereq> searchBySRNoAndStoreIdAndProcessedBy(MmsStorereq sr, MmsStoreInformation storeEntity) {
        return requisitionFacade.searchBySRNoAndStoreIdAndProcessedBy(sr, storeEntity);
    }

    @Override
    public List<MmsStorereq> findSrListByWfStatus(int status) {
        return requisitionFacade.findSrListByWfStatus(status);
    }

    @Override
    public List<MmsStorereq> findSrListForCheckerByWfStatus(int prepareStatus, int approverRejectstatus) {
        return requisitionFacade.findSrListForCheckerByWfStatus(prepareStatus, approverRejectstatus);
    }
//processed

    @Override
    public List<MmsStorereq> searchSRByStoreIdAndStatusProcessedAndProcessedBy(MmsStoreInformation storeEntity, MmsStorereq storereq) {
        return requisitionFacade.searchSRByStoreIdAndStatusProcessedAndProcessedBy(storeEntity, storereq);
    }
    //new

    @Override
    public List<MmsStorereq> searchSRByStoreIdAndStatusNewAndProcessedBy(MmsStoreInformation storeEntity, MmsStorereq storereq) {
        return requisitionFacade.searchSRByStoreIdAndStatusNewAndProcessedBy(storeEntity, storereq);
    }

    @Override
    public List<MmsStorereq> findSrList() {
        return requisitionFacade.findSrList();
    }

    @Override
    public List<HrDepartments> DepNameListFromGrn() {
        return requisitionFacade.DepNameListFromGrn();
    }

    @Override
    public List<MmsGrn> searchGrnDepartmentId(HrDepartments hrDepartmentEntity) {
        return requisitionFacade.searchGrnDepartmentId(hrDepartmentEntity);
    }

    @Override
    public List<MmsGrnDetail> getMmsDetailInfoByGrnId(MmsGrn mmsGrn) {
        return requisitionFacade.getMmsDetailInfoByGrnId(mmsGrn);
    }

    @Override
    public List<String> getMmsStorereqColumnNameList() {
        return requisitionFacade.getMmsStorereqColumnNameList();
    }


    

    @Override
    public List<MmsStorereq> getSRListsByParameter(ColumnNameResolver columnNameResolver, MmsStorereq requisition, String columnValue) {
        return requisitionFacade.getSRListsByParameter(columnNameResolver, requisition, columnValue); 
    }

}
