/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsSiv;
import et.gov.eep.mms.entity.MmsSivDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;

/**
 *
 * @author Minab
 */
@Local
public interface MmsSivBeanLocal {

    /**
     *
     * @param mmsiv
     */
    void create(MmsSiv mmsiv);

    /**
     *
     * @param mmsis
     */
    void edit(MmsSiv mmsis);

    /**
     *
     * @param siv
     * @return
     */
    public ArrayList<MmsSiv> searchBySivNo(MmsSiv siv);

    /**
     *
     * @param selectedSivNo
     * @return
     */
    public MmsSiv getSivInfoBySivNo(String selectedSivNo);

    /**
     *
     * @return
     */
    public MmsSiv getLastSIvNo();

    /**
     *
     * @return
     */
    public List<MmsSiv> findSivList();
    //for sadik

    /**
     *
     * @param selectedSivNo
     * @return
     */
    public MmsSiv getSivInfoBySivNo(MmsSiv selectedSivNo);

    /**
     *
     * @return
     */
    public ArrayList<MmsSiv> approvedSivList();

    public ArrayList<MmsSiv> approvedStoreSivList(MmsStoreInformation storeinfo, int status);

    /**
     *
     * @return
     */
    public ArrayList<MmsSiv> approvedSivListForLocationManagement();

    public MmsSiv getbySivID(MmsSiv sivEntity);

    public List<MmsSiv> searchByStoreId(MmsStoreInformation storeEntity);

    public List<MmsSiv> searchBySivNoAndStoreId(MmsSiv siv, MmsStoreInformation storeEntity);

    public List<MmsSiv> searchBySivNoAndStoreIdAndProcessedBy(MmsSiv siv, MmsStoreInformation storeEntity);

    public List<MmsSiv> searchSivByIssuedToAndStoreId(MmsSiv siv);

    public List<MmsSiv> searchByStoreId2(MmsSiv sivEntity);

    public List<MmsSiv> searchByStoreId2AndProcessedBy(MmsSiv sivEntity);

    public List<MmsSiv> searchSivByIssuedToAndStoreIdAndProcessedBy(MmsSiv siv);

    public List<MmsSiv> findSivListByWfStatus(int prepareStatus);

    public List<MmsSiv> findByStatus(int Stataus);

    public List<MmsSiv> searchByStoreIdAndprocessedBy(MmsStoreInformation storeEntity, MmsSiv siv);

    public List<MmsSiv> findAllwithApprovedStatus(MmsStoreInformation storeInfoEntity, int approvedStatus);

    public List<MmsSiv> getSivNoByDepIdAndStroeId(MmsStoreInformation storeInfoEntity, HrDepartments hrDepartmentsEntity);

    public List<MmsSivDetail> getItemCodelistWhenRecierver(MmsSiv sivEntity);

    public List<MmsSiv> searchBySivNosForRollBack(MmsStoreInformation storeInformation);

    public void remove(MmsSiv siv);

//    public List<MmsSiv> findSivList(int APPROVE_VALUE);
    public MmsSiv findByMasterId(int id);

    public List<String> getMmsSivColumnNameList();

    public List<MmsSiv> getSivListsByParameter(ColumnNameResolver columnNameResolver, MmsSiv siv, String columnValue);

    

}
