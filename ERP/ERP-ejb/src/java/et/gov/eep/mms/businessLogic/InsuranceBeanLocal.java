
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsInsurance;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author w_station
 */
@Local
public interface InsuranceBeanLocal {

    /**
     *
     * @param insuranceEntity
     * @return
     */
    public ArrayList<MmsInsurance> searchByInsuranceNo(MmsInsurance insuranceEntity);

    /**
     *
     * @param insuranceEntity
     * @return
     */
    public List<MmsInsurance> searchInsuranceByParameterPrefix(MmsInsurance insuranceEntity);

    /**
     *
     * @return
     */
    public MmsInsurance getLastInsuranceId();

    /**
     *
     * @param insuranceEntity
     */
    public void edit(MmsInsurance insuranceEntity);

    /**
     *
     * @param insuranceEntity
     */
    public void create(MmsInsurance insuranceEntity);

    public MmsInsurance getSelectedRequest(Integer insId);

    public List<MmsInsurance> findInsListByWfStatus(int insStatus);

    public List<MmsInsurance> searchAllTransmissionsInfoByPreparerId(Integer authorizedBy);

    public List<MmsInsurance> findbyAll();
    public List<MmsInsurance> getInsuranceListsByParameter(MmsInsurance insuranceEntity);

    public List<ColumnNameResolver> getColumnNameList();

}
