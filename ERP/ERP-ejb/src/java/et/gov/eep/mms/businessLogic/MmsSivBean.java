/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsSiv;
import et.gov.eep.mms.entity.MmsSivDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsSivFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class MmsSivBean implements MmsSivBeanLocal {

    @EJB
    MmsSivFacade sivFacade;

    /**
     *
     * @param mmsiv
     */
    @Override
    public void create(MmsSiv mmsiv) {
        sivFacade.create(mmsiv);

    }

    /**
     *
     * @param mmsis
     */
    @Override
    public void edit(MmsSiv mmsis) {
        sivFacade.edit(mmsis);
    }

    /**
     *
     * @param siv
     * @return
     */
    @Override
    public ArrayList<MmsSiv> searchBySivNo(MmsSiv siv) {
        return sivFacade.searchSivNumber(siv);
    }

    /**
     *
     * @param selectedSivNo
     * @return
     */
    @Override
    public MmsSiv getSivInfoBySivNo(String selectedSivNo) {
        return sivFacade.getSivInfoBySivNo(selectedSivNo);
    }

    /**
     *
     * @return
     */
    @Override
    public MmsSiv getLastSIvNo() {
        return sivFacade.getLastSiVNo();
    }

    /**
     *
     * @return
     */
    @Override
    public List<MmsSiv> findSivList() {
        return sivFacade.findSivNOList();
    }

    /**
     *
     * @param selectedSivNo
     * @return
     */
    @Override
    public MmsSiv getSivInfoBySivNo(MmsSiv selectedSivNo) {
        return sivFacade.getSivObjectBySivNo(selectedSivNo);
    }

    /**
     *
     * @return
     */
    @Override
    public ArrayList<MmsSiv> approvedSivList() {
        return sivFacade.approvedSivList();
    }

    /**
     *
     * @return
     */
    @Override
    public ArrayList<MmsSiv> approvedSivListForLocationManagement() {
        return sivFacade.approvedSivListForLocationMangement();
    }

    @Override
    public MmsSiv getbySivID(MmsSiv sivEntity) {
        return sivFacade.getSivInfoBySivId(sivEntity);
    }

    @Override
    public List<MmsSiv> searchByStoreId(MmsStoreInformation storeEntity) {
        return sivFacade.searchByStoreId(storeEntity);
    }

    @Override
    public List<MmsSiv> searchBySivNoAndStoreId(MmsSiv siv, MmsStoreInformation storeEntity) {
        return sivFacade.searchBySivNoAndStoreId(siv, storeEntity);
    }

    @Override
    public List<MmsSiv> searchSivByIssuedToAndStoreId(MmsSiv siv) {
        return sivFacade.searchSivByIssuedToAndStoreId(siv);
    }

    @Override
    public List<MmsSiv> searchByStoreId2(MmsSiv sivEntity) {
        return sivFacade.searchByStoreId2(sivEntity);
    }

    @Override
    public ArrayList<MmsSiv> approvedStoreSivList(MmsStoreInformation storeinfo, int Status) {
        return sivFacade.approvedStoreSivList(storeinfo, Status);
    }

    @Override
    public List<MmsSiv> searchByStoreIdAndprocessedBy(MmsStoreInformation storeEntity, MmsSiv siv) {
        return sivFacade.searchByStoreIdAndprocessedBy(storeEntity, siv);
    }

    @Override
    public List<MmsSiv> searchBySivNoAndStoreIdAndProcessedBy(MmsSiv siv, MmsStoreInformation storeEntity) {
        return sivFacade.searchBySivNoAndStoreIdAndProcessedBy(siv, storeEntity);
    }

    @Override
    public List<MmsSiv> searchSivByIssuedToAndStoreIdAndProcessedBy(MmsSiv siv) {
        return sivFacade.searchSivByIssuedToAndStoreIdAndProcessedBy(siv);
    }

    @Override
    public List<MmsSiv> searchByStoreId2AndProcessedBy(MmsSiv sivEntity) {
        return sivFacade.searchByStoreId2AndProcessedBy(sivEntity);
    }

    @Override
    public List<MmsSiv> findSivListByWfStatus(int prepareStatus) {
        return sivFacade.findSivListByWfStatus(prepareStatus);
    }

    @Override
    public List<MmsSiv> findAllwithApprovedStatus(MmsStoreInformation storeInfoEntity, int approvedStatus) {

        return sivFacade.findAllwithApprovedStatus(storeInfoEntity, approvedStatus);

    }

    @Override
    public List<MmsSiv> getSivNoByDepIdAndStroeId(MmsStoreInformation storeInfoEntity, HrDepartments hrDepartmentsEntity) {
        return sivFacade.getSivNoByDepIdAndStroeId( storeInfoEntity,  hrDepartmentsEntity);
}

    @Override
    public List<MmsSivDetail> getItemCodelistWhenRecierver(MmsSiv sivNo) {
        return sivFacade.getItemCodelistWhenRecierver(sivNo);
    }
    @Override
    public List<MmsSiv> searchBySivNosForRollBack(MmsStoreInformation storeInformation) {
        return sivFacade.searchBySivNosForRollBack(storeInformation);
    }

    @Override
    public void remove(MmsSiv siv) {
        sivFacade.remove(siv);
    }

    @Override
    public MmsSiv findByMasterId(int id) {
     return sivFacade.findById(id);
    }

    @Override
    public List<MmsSiv> findByStatus(int Stataus) {
       return sivFacade.findByStatus(Stataus);
    }

    @Override
    public List<String> getMmsSivColumnNameList() {
       return sivFacade.getMmsSivColumnNameList();
    }

   

    @Override
    public List<MmsSiv> getSivListsByParameter(ColumnNameResolver columnNameResolver, MmsSiv siv, String columnValue) {
        return sivFacade.getSivListsByParameter(columnNameResolver,siv,columnValue);
    }
}
