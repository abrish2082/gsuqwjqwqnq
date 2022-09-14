/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import javax.ejb.Local;
import java.util.ArrayList;
import java.util.List;
import et.gov.eep.mms.entity.MmsRmg;

/**
 *
 * @author Minab
 */
@Local
public interface MmsRmgBeanLocal {

    /**
     *
     * @param rmg
     */
    public void create(MmsRmg rmg);

    /**
     *
     * @param rmg
     */
    public void edit(MmsRmg rmg);

    /**
     *
     * @return
     */
    public MmsRmg getLastrmgNo();

    /**
     *
     * @param rmgNo
     * @return
     */
    public ArrayList<MmsRmg> searchByrmgNo(MmsRmg rmgNo);

    public ArrayList<MmsRmg> approvedRmgList();

    public MmsRmg getByRmgId(MmsRmg rmgEntity);

    public ArrayList<MmsRmg> searchByrmgNoAndProcessedBy(MmsRmg rmgNo);

    public List<MmsRmg> findByStatus(int Stataus);

    public List<MmsRmg> findRmgPreparedList();

    public List<MmsRmg> findRmgListForCheckerByWfStatus(int prepareStatus, int approverRejectstatus);

    public ArrayList<MmsRmg> approvedRmgListByStoreAndStatus(MmsRmg rmg, int status);

    public ArrayList<MmsRmg> searchRMGByProcessedBy(MmsRmg rmgNo);

    public List<String> getMmsRmgColumnNameList();

    public List<MmsRmg> getRMGListsByParameter(MmsRmg rmgentity);

    public List<MmsRmg> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver,MmsRmg rmgentity, String columnValue);

}
