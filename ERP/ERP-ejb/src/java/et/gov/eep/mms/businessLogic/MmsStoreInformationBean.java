/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

//import et.gov.insa.eep.erp.pamps.entity.MmsStoreInformation;
//import et.gov.insa.eep.erp.pamps.mapper.MmsStoreInformationFacade;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsStoreInformationFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.SessionBean;
import javax.ejb.Stateless;

/**
 *
 * @author kimmyo
 */
@Stateless
public class MmsStoreInformationBean implements MmsStoreInformationBeanLocal {

    @EJB
    MmsStoreInformationFacade storeInfoFacade;

    /**
     *
     * @param StoreInformation
     */
    @Override
    public void create(MmsStoreInformation StoreInformation) {
        storeInfoFacade.create(StoreInformation);
    }

    /**
     *
     * @param StoreInformation
     */
    @Override
    public void edit(MmsStoreInformation StoreInformation) {
        storeInfoFacade.edit(StoreInformation);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    /**
     *
     * @param storeInformation
     * @return
     */
    
    @Override
    public ArrayList<MmsStoreInformation> searchStoreInformation(MmsStoreInformation storeInformation) {
        return storeInfoFacade.searchStoreInformation(storeInformation);
    }

    /**
     *
     * @param storeInformation
     * @return
     */
    @Override
    public MmsStoreInformation getPapmsStoreInformation(MmsStoreInformation storeInformation) {
      return  storeInfoFacade.getPapmsStoreInformation(storeInformation);
    }

    /**
     *
     * @param storeInformation
     * @return
     */
    @Override
    public boolean getPapmsStoreInformationDup(MmsStoreInformation storeInformation) {
        return storeInfoFacade.getPapmsStoreInformationDup(storeInformation);
    }

    /**
     *
     * @return
     */
    @Override
    public List<MmsStoreInformation> findAllStoreInfo() {
        return storeInfoFacade.findAll();
    }

    @Override
    public List<MmsStoreInformation> searchStoreByParameterPrefix(MmsStoreInformation storeEntity) {
        return storeInfoFacade.searchStoreInformation(storeEntity);
    }

    @Override
    public List<MmsStoreInformation> searchAllStoreInfo() {
       return storeInfoFacade.findAll();
    }

    @Override
    public MmsStoreInformation getLastStoreNo() {
        return storeInfoFacade.getLastStorenumber();
    }

    @Override
    public MmsStoreInformation searchByStoreId(MmsStoreInformation storeInfoEntity) {
        return storeInfoFacade.searchByStoreId(storeInfoEntity);
    }

    @Override
    public List<MmsStoreInformation> searchStoreByParameterStoreNo(MmsStoreInformation storeInfoEntity) {
        return storeInfoFacade.searchStoreByParameterStoreNo(storeInfoEntity);
    }
     @Override
    public List<MmsStoreInformation> searchStoreByParameterDepartmentId(HrDepartments depId) {
        return storeInfoFacade.searchStoreByParameterStoreNo(depId);
    }
    @Override
    public List<MmsStoreInformation> searchStoreInformationByNameAndUserId(MmsStoreInformation storeEntity) {
        return storeInfoFacade.searchStoreInformationByNameAndUserId(storeEntity);
    }
     @Override
    public List<MmsStoreInformation> searchStoreByParameterStoreNoAndPreparerId(MmsStoreInformation storeInfoEntity) {
        return storeInfoFacade.searchStoreByParameterStoreNoAndPreparerId(storeInfoEntity);
    }
    @Override
    public List<MmsStoreInformation> searchAllStoreInfoByPreparerId(MmsStoreInformation storeInfoEntity) {
       return storeInfoFacade.searchAllStoreInfoByPreparerId(storeInfoEntity);
    }

    @Override
    public List<MmsStoreInformation> searchStoreByParameterStoreNoAndStoreNameAndPreparerId(MmsStoreInformation storeInfoEntity) {
       return storeInfoFacade.searchByParameterStoreNoAndStoreNameAndPreparerId(storeInfoEntity);
    }

    @Override
    public List<String> getMmsStoreInfColumnNameList() {
        return storeInfoFacade.getMmsStoreInfColumnNameList();
    }

    

    @Override
    public List<MmsStoreInformation> getStoreListsByParameter(ColumnNameResolver columnNameResolver, MmsStoreInformation storeInfoEntity, String columnValue) {
        return storeInfoFacade.getStoreListsByParameter(columnNameResolver,storeInfoEntity,columnValue);
    }
}
