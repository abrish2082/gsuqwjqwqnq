/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsStoreInformation;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kimmyo
 */
@Local
public interface MmsStoreInformationBeanLocal {

    /**
     *
     * @param papmsStoreInformation
     */
    void create(MmsStoreInformation papmsStoreInformation);

    /**
     *
     * @param papmsStoreInformation
     */
    void edit(MmsStoreInformation papmsStoreInformation);

    /**
     *
     * @param storeInformation
     * @return
     */
    ArrayList<MmsStoreInformation> searchStoreInformation(MmsStoreInformation storeInformation);

    /**
     *
     * @param storeInformation
     * @return
     */
    MmsStoreInformation getPapmsStoreInformation(MmsStoreInformation storeInformation);

    /**
     *
     * @param storeInformation
     * @return
     */
    boolean getPapmsStoreInformationDup(MmsStoreInformation storeInformation);

    /**
     *
     * @return
     */
    public List<MmsStoreInformation> findAllStoreInfo();

    public List<MmsStoreInformation> searchStoreByParameterPrefix(MmsStoreInformation storeEntity);

    public List<MmsStoreInformation> searchAllStoreInfo();

    public MmsStoreInformation getLastStoreNo();

    public MmsStoreInformation searchByStoreId(MmsStoreInformation storeInfoEntity);

    public List<MmsStoreInformation> searchStoreByParameterStoreNo(MmsStoreInformation storeInfoEntity);

    public List<MmsStoreInformation> searchStoreByParameterDepartmentId(HrDepartments depId);

    public List<MmsStoreInformation> searchStoreInformationByNameAndUserId(MmsStoreInformation storeEntity);

    public List<MmsStoreInformation> searchStoreByParameterStoreNoAndPreparerId(MmsStoreInformation storeInfoEntity);

    public List<MmsStoreInformation> searchAllStoreInfoByPreparerId(MmsStoreInformation storeInfoEntity);

//    public List<MmsStoreInformation> findAllwithApprovedStatus(MmsStoreInformation store, int qa);

    public List<MmsStoreInformation> searchStoreByParameterStoreNoAndStoreNameAndPreparerId(MmsStoreInformation storeInfoEntity);

    public List<String> getMmsStoreInfColumnNameList();

    public List<MmsStoreInformation> getStoreListsByParameter(ColumnNameResolver columnNameResolver, MmsStoreInformation storeInfoEntity, String columnValue);

}
