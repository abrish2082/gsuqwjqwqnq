
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsInsurance;
import et.gov.eep.mms.mapper.MmsInsuranceFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author w_station
 */
@Stateless
public class InsuranceBean implements InsuranceBeanLocal {

    @EJB
    MmsInsuranceFacade insuranceFacade;

    /**
     *
     * @param insuranceEntity
     * @return
     */
    @Override
    public ArrayList<MmsInsurance> searchByInsuranceNo(MmsInsurance insuranceEntity) {
        return insuranceFacade.searchByInsuranceNo(insuranceEntity);
    }

    /**
     *
     * @param insuranceEntity
     * @return
     */
    @Override
    public List<MmsInsurance> searchInsuranceByParameterPrefix(MmsInsurance insuranceEntity) {
       return insuranceFacade.searchInsuranceByParameterPrefix(insuranceEntity);
    }

    /**
     *
     * @return
     */
    @Override
    public MmsInsurance getLastInsuranceId() {
       
        return insuranceFacade.getLastInsuranceId();
    }

    /**
     *
     * @param insuranceEntity
     */
    @Override
    public void edit(MmsInsurance insuranceEntity) {
       insuranceFacade.edit(insuranceEntity);
    }

    /**
     *
     * @param insuranceEntity
     */
    @Override
    public void create(MmsInsurance insuranceEntity) {
        insuranceFacade.create(insuranceEntity);
    }

    @Override
    public MmsInsurance getSelectedRequest(Integer insId) {
       return insuranceFacade.getSelectedRequest(insId);
    }

    @Override
    public List<MmsInsurance> findInsListByWfStatus(int insStatus) {
         return insuranceFacade.findInsListByWfStatus(insStatus);
    }

    @Override
    public List<MmsInsurance> searchAllTransmissionsInfoByPreparerId(Integer authorizedBy) {
      return insuranceFacade.searchAllTransmissionsInfoByPreparerId(authorizedBy);
    }

    @Override
    public List<MmsInsurance> findbyAll() {
       return insuranceFacade.findAll();
    }
    
@Override
    public List<MmsInsurance> getInsuranceListsByParameter(MmsInsurance insuranceEntity) {
        return insuranceFacade.getInsuranceListsByParameter(insuranceEntity);
    }

    @Override
    public List<ColumnNameResolver> getColumnNameList() {
        return insuranceFacade.getColumnNameList();
    }
    
}
