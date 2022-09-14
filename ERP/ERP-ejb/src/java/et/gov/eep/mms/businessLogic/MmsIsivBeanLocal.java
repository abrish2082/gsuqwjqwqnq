/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.List;
import java.util.ArrayList;
import javax.ejb.Local;
import et.gov.eep.mms.entity.MmsIsiv;
import et.gov.eep.mms.entity.MmsSiv;
import et.gov.eep.mms.entity.MmsStoreInformation;

/**
 *
 * @author Minab
 */
@Local
public interface MmsIsivBeanLocal {

    /**
     *
     * @param mat
     */
    public void create(MmsIsiv mat);

    /**
     *
     * @return
     */
    public MmsIsiv getLastTransferNo();

    /**
     *
     * @param IsivNums
     * @return
     */
    public MmsIsiv getIsivInfoByIsivNo(MmsIsiv IsivNums);

    /**
     *
     * @return
     */
    public List<MmsIsiv> findIsivList();

    /**
     *
     * @param Isivs
     */
    public void edit(MmsIsiv Isivs);

    /**
     *
     * @param isiv
     * @return
     */
    public ArrayList<MmsIsiv> searchByIsIvNo(MmsIsiv isiv);

    /**
     *
     * @param selectedSivNo
     * @return
     */
    public MmsIsiv getISivInfoBySivNo(String selectedSivNo);

    public List<MmsIsiv> SearchByStoreAndIsivNo(MmsIsiv ivEntity);

    public List<MmsIsiv> searchByStoreId(MmsIsiv ivEntity);

    public List<MmsIsiv> approvedIsivList(MmsStoreInformation store, int Status);

    public ArrayList<MmsIsiv> approvedIsivIssuedList();

    public MmsIsiv getByTransferId(MmsIsiv ivEntity);

    public List<MmsIsiv> SearchByStoreAndIsivNoAndProcessedBy(MmsIsiv ivEntity);

    public List<MmsIsiv> searchByStoreIdAndProcessedBy(MmsIsiv ivEntity);

    public List<MmsIsiv> findIsivListByWfStatus(int StatusWf);

    public List<MmsIsiv> searchISIVByStoreIdAndProcessedBy(MmsIsiv ivEntity);

    public List<MmsIsiv> approvedIsivListByWfStatusAndStoreId(MmsStoreInformation store, int Status);

    public List<String> getMmsIsivColumnNameList();

    public List<MmsIsiv> getIsivListsByParameter(ColumnNameResolver columnNameResolver, MmsIsiv isivEntity, String columnValue);

}
