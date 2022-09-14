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
import et.gov.eep.mms.entity.MmsIsiv;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsIsivFacade;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsIsivBean implements MmsIsivBeanLocal {

    @EJB
    MmsIsivFacade transferfacade;

    /**
     *
     * @return
     */
    @Override
    public MmsIsiv getLastTransferNo() {
        return transferfacade.getLastTransferNo();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    /**
     *
     * @param mat
     */
    @Override
    public void create(MmsIsiv mat) {
        transferfacade.create(mat);
    }

    /**
     *
     * @param IsivNums
     * @return
     */
    @Override
    public MmsIsiv getIsivInfoByIsivNo(MmsIsiv IsivNums) {
        return transferfacade.getIsivInfoByIsivNo(IsivNums);
    }

    /**
     *
     * @return
     */
    @Override
    public List<MmsIsiv> findIsivList() {
        return transferfacade.findIsivNOList();
    }

    /**
     *
     * @param Isivs
     */
    @Override
    public void edit(MmsIsiv Isivs) {
        transferfacade.edit(Isivs);
    }

    /**
     *
     * @param selectedSivNo
     * @return
     */
    @Override
    public MmsIsiv getISivInfoBySivNo(String selectedSivNo) {
        return transferfacade.getISivInfoBySivNo(selectedSivNo);
    }

    /**
     *
     * @param isiv
     * @return
     */
    @Override
    public ArrayList<MmsIsiv> searchByIsIvNo(MmsIsiv isiv) {
        return transferfacade.searchByIsivNo(isiv);
    }

    @Override
    public List<MmsIsiv> SearchByStoreAndIsivNo(MmsIsiv ivEntity) {
        return transferfacade.searchByStoreAndIsivNo(ivEntity);
    }

    @Override
    public List<MmsIsiv> SearchByStoreAndIsivNoAndProcessedBy(MmsIsiv ivEntity) {
        return transferfacade.searchByStoreAndIsivNoAndProcessedBy(ivEntity);
    }

    @Override
    public List<MmsIsiv> searchByStoreId(MmsIsiv ivEntity) {
        return transferfacade.searchByStoreID(ivEntity);
    }

    @Override
    public List<MmsIsiv> searchByStoreIdAndProcessedBy(MmsIsiv ivEntity) {
        return transferfacade.searchByStoreIDAndProcessedBy(ivEntity);
    }

    @Override
    public List<MmsIsiv> searchISIVByStoreIdAndProcessedBy(MmsIsiv ivEntity) {
        return transferfacade.searchISIVByStoreIdAndProcessedBy(ivEntity);
    }

    @Override
    public List<MmsIsiv> approvedIsivList(MmsStoreInformation store, int Status) {
        return transferfacade.getapprovedISIVRecievableList(store, Status);
    }

    @Override
    public List<MmsIsiv> approvedIsivListByWfStatusAndStoreId(MmsStoreInformation store, int Status) {
        return transferfacade.findIsivListByWfStatusAndStoeId(store, Status);
    }

    @Override
    public ArrayList<MmsIsiv> approvedIsivIssuedList() {
        return transferfacade.getapprovedISIVIssuedList();
    }

    @Override
    public MmsIsiv getByTransferId(MmsIsiv ivEntity) {

        return transferfacade.getbyTransferId(ivEntity);
    }

    @Override
    public List<MmsIsiv> findIsivListByWfStatus(int StatusWf) {
        return transferfacade.findIsivListByWfStatus(StatusWf);
    }

    @Override
    public List<String> getMmsIsivColumnNameList() {
        return transferfacade.getMmsIsivColumnNameList();
    }

    @Override
    public List<MmsIsiv> getIsivListsByParameter(ColumnNameResolver columnNameResolver, MmsIsiv isivEntity, String columnValue) {
        return transferfacade.getIsivListsByParameter(columnNameResolver, isivEntity,columnValue);
    }
}
