/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsGrn;
import et.gov.eep.mms.entity.MmsGrnDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.entity.MmsStorereq;

/**
 *
 * @author Minab
 */
@Local
public interface MmsStoreReqBeanLocal {

    /**
     *
     * @param PapmsStorereq
     */
    void create(MmsStorereq PapmsStorereq);

    /**
     *
     * @param PapmsStorereq
     */
    void edit(MmsStorereq PapmsStorereq);

    /**
     *
     * @param sr
     * @return
     */
    public List<MmsStorereq> searchBySRNo(MmsStorereq sr);

    /**
     *
     * @param selectedSivNo
     * @return
     */
    public MmsStorereq getSrInfoBySrvNo(String selectedSivNo);

    /**
     *
     * @return
     */
    public String getMaxPaymentRequistion();

    /**
     *
     * @return
     */
    public MmsStorereq getLastPaymentNo();

    /**
     *
     * @param store
     * @param qa
     * @return
     */
    public List<MmsStorereq> findAllwithApprovedStatus(MmsStoreInformation store, int qa);

    /**
     *
     * @param selectedSivNo
     * @return
     */
    public MmsStorereq getSrInfo(String selectedSivNo);

    public MmsStorereq searchBySRNos(MmsStorereq sr);

    public MmsStorereq searchByStoreReqId(MmsStorereq sr);

    public List<MmsStorereq> findAll();

    public List<MmsStorereq> SearchByStoreId(MmsStorereq sr);

    public List<MmsStorereq> searchSRByStoreId(MmsStoreInformation storeEntity);

    public List<MmsStorereq> searchBySRNoAndStoreId(MmsStorereq sr, MmsStoreInformation storeEntity);

    public List<MmsStorereq> searchSRByStoreIdAndProcessedBy(MmsStoreInformation storeEntity, MmsStorereq storereq);

    public List<MmsStorereq> searchBySRNoAndStoreIdAndProcessedBy(MmsStorereq sr, MmsStoreInformation storeEntity);

    public List<MmsStorereq> findSrListByWfStatus(int status);

    public List<MmsStorereq> findSrListForCheckerByWfStatus(int prepareStatus, int approverRejectstatus);

    public List<MmsStorereq> findAllwithApprovedStatusForIsiv(MmsStoreInformation store, int approvedStatus);

    public List<MmsStorereq> searchSRByStoreIdAndStatusNewAndProcessedBy(MmsStoreInformation storeEntity, MmsStorereq storereq);

    public List<MmsStorereq> searchSRByStoreIdAndStatusProcessedAndProcessedBy(MmsStoreInformation storeEntity, MmsStorereq storereq);

    public List<MmsStorereq> findSrList();

    public List<HrDepartments> DepNameListFromGrn();

    public List<MmsGrn> searchGrnDepartmentId(HrDepartments hrDepartmentEntity);

    public List<MmsGrnDetail> getMmsDetailInfoByGrnId(MmsGrn mmsGrn);

    public List<String> getMmsStorereqColumnNameList();
    
    public List<MmsStorereq> getSRListsByParameter(ColumnNameResolver columnNameResolver, MmsStorereq requisition, String columnValue);
}
