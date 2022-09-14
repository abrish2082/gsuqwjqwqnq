/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.EJB;
import et.gov.eep.mms.entity.MmsRmg;
import et.gov.eep.mms.mapper.MmsRmgFacade;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsRmgBean implements MmsRmgBeanLocal {

    @EJB
    MmsRmgFacade rmgfacade;

    /**
     *
     * @param rmg
     */
    @Override
    public void create(MmsRmg rmg) {
        rmgfacade.create(rmg);
    }

    /**
     *
     * @return
     */
    @Override
    public MmsRmg getLastrmgNo() {
        return rmgfacade.getLastRmgnNo();
    }

    /**
     *
     * @param rmg
     */
    @Override
    public void edit(MmsRmg rmg) {
        rmgfacade.edit(rmg);
    }

    /**
     *
     * @param rmgNo
     * @return
     */
    @Override
    public ArrayList<MmsRmg> searchByrmgNo(MmsRmg rmgNo) {
        return rmgfacade.searchByRmgNo(rmgNo);
    }

    @Override
    public ArrayList<MmsRmg> searchByrmgNoAndProcessedBy(MmsRmg rmgNo) {
        return rmgfacade.searchByRmgNoAndProcessedBy(rmgNo);
    }

    @Override
    public ArrayList<MmsRmg> searchRMGByProcessedBy(MmsRmg rmgNo) {
        return rmgfacade.searchRMGByProcessedBy(rmgNo);
    }

    @Override
    public ArrayList<MmsRmg> approvedRmgList() {
        return rmgfacade.getapprovedRmgList();
    }

    @Override
    public ArrayList<MmsRmg> approvedRmgListByStoreAndStatus(MmsRmg rmg, int status) {
        return rmgfacade.approvedRmgListByStoreAndStatus(rmg, status);
    }

    @Override
    public MmsRmg getByRmgId(MmsRmg rmgEntity) {
        return rmgfacade.getRmgInfoByRmgId(rmgEntity);
    }

    @Override
    public List<MmsRmg> findRmgPreparedList() {
        return rmgfacade.findRmgPreparedList();
    }

    @Override
    public List<MmsRmg> findByStatus(int Stataus) {
        return rmgfacade.findByStatus(Stataus);
    }

    @Override
    public List<MmsRmg> findRmgListForCheckerByWfStatus(int prepareStatus, int approverRejectstatus) {
        return rmgfacade.findRmgListForCheckerByWfStatus(prepareStatus, approverRejectstatus);
    }

    @Override
    public List<String> getMmsRmgColumnNameList() {
        return rmgfacade.getMmsRmgColumnNameList();
    }

    @Override
    public List<MmsRmg> getRMGListsByParameter(MmsRmg rmgentity) {
        return rmgfacade.getRMGListsByParameter(rmgentity);
    }

    @Override
    public List<MmsRmg> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver,MmsRmg rmgentity, String columnValue) {
        return rmgfacade.searchByCol_NameAndCol_Value(columnNameResolver,rmgentity, columnValue);
    }

}
