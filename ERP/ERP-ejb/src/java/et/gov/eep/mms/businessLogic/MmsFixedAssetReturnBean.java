
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.mms.entity.MmsFixedassetRegstration;
import et.gov.eep.mms.mapper.MmsFixedAssetReturnFacade;
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
public class MmsFixedAssetReturnBean implements MmsFixedAssetReturnBeanLocal {

    @EJB
    MmsFixedAssetReturnFacade returnFacade;

    @Override
    public ArrayList<MmsFixedAssetReturn> searchByReturnNo(MmsFixedAssetReturn returnEntity) {
        return returnFacade.searchByReturnNo(returnEntity);
    }

    @Override
    public MmsFixedAssetReturn getLastReturnId() {
        return returnFacade.getLastReturnId();
    }

    @Override
    public List<MmsFixedAssetReturn> searchReturnByParameterPrefix(MmsFixedAssetReturn returnEntity) {
        return returnFacade.searchReturnByParameterPrefix(returnEntity);

    }
  @Override
    public List<MmsFixedAssetReturn> searchReturnByParameterPrefixAndProcessedBy(MmsFixedAssetReturn returnEntity) {
        return returnFacade.searchReturnByParameterPrefixAndProcessedBy(returnEntity);

    }
    
    
    @Override
    public void edit(MmsFixedAssetReturn returnEntity) {
        returnFacade.edit(returnEntity);
    }

    @Override
    public void create(MmsFixedAssetReturn returnEntity) {
        returnFacade.create(returnEntity);
    }

    @Override
    public List<MmsFixedAssetReturn> findAllDept() {
        return returnFacade.findAll();
    }

    @Override
    public List<MmsFixedAssetReturn> findAllInfo() {
        return returnFacade.findAll();
    }

    @Override
    public List<MmsFixedAssetReturn> searchByDept(MmsFixedAssetReturn returnEntity) {
        return returnFacade.searchByDept(returnEntity);
    }

    @Override
    public MmsFixedAssetReturn getSelectedRequest(Integer farId) {
        return returnFacade.getSelectedRequest(farId);
    }

    @Override
    public List<MmsFixedAssetReturn> findFarListByWfStatus(int status) {
        return returnFacade.findFarListByWfStatus(status);
    }

    @Override
    public List<MmsFixedAssetReturn> findFarListForCheckerByWfStatus(int prepareStatus, int approverRejectstatus) {
        return returnFacade.findFarListForCheckerByWfStatus(prepareStatus, approverRejectstatus);
    }

    @Override
    public List<MmsFixedAssetReturn> searchAllFarInfoByPreparerId(Integer processedBy) {
        return returnFacade.searchAllFarInfoByPreparerId(processedBy); 
    }

    @Override
    public List<MmsFixedassetRegstDetail> getReturnByDepId(MmsFixedassetRegstration fixedassetRegstration) {
        return returnFacade.getReturnByDepId(fixedassetRegstration);
    }

    @Override
    public List<Integer>  getEmpIdByName(String returnby) {
        return returnFacade.getEmpIdByName(returnby);
    }

    @Override
    public MmsFixedassetRegstDetail gettingTagInfo(String tingOthersByTagNo) {
        return returnFacade.gettingTagInfo(tingOthersByTagNo); 
    }
 @Override
    public List<MmsFixedAssetReturn> getReturnListsByParameter(MmsFixedAssetReturn returnEntity) {
        return returnFacade.getReturnListsByParameter(returnEntity);
    }

    @Override
    public List<ColumnNameResolver> getColumnNameList() {
        return returnFacade.getColumnNameList();
    }

}
