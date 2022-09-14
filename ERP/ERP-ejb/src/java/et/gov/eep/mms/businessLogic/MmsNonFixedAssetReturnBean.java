
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsNonFixedAssetReturn;
import et.gov.eep.mms.entity.MmsSivDetail;
import et.gov.eep.mms.entity.MmsStoreInformation;
import et.gov.eep.mms.mapper.MmsNonFixedAssetReturnFacade;
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
public class MmsNonFixedAssetReturnBean implements MmsNonFixedAssetReturnBeanLocal {

    @EJB
    MmsNonFixedAssetReturnFacade NFARetFacade;

    @Override
    public MmsNonFixedAssetReturn getLastReturnId() {
        return NFARetFacade.getLastReturnId();
    }

    @Override
    public void create(MmsNonFixedAssetReturn nonFxdRetEntity) {
        NFARetFacade.create(nonFxdRetEntity);
    }

    @Override
    public void edit(MmsNonFixedAssetReturn nonFxdRetEntity) {
        NFARetFacade.edit(nonFxdRetEntity);
    }

    @Override
    public ArrayList<MmsNonFixedAssetReturn> searchByReturnNo(MmsNonFixedAssetReturn nonFxdRetEntity) {
        return NFARetFacade.searchByReturnNo(nonFxdRetEntity);

    }

    @Override
    public List<MmsNonFixedAssetReturn> searchNFReturnByParameterPrefix(MmsNonFixedAssetReturn nonFxdRetEntity) {
        return NFARetFacade.searchNFReturnByParameterPrefix(nonFxdRetEntity);
    }

    @Override
    public MmsNonFixedAssetReturn getSelectedRequest(BigDecimal nfaId) {
        return NFARetFacade.getSelectedRequest(nfaId);
    }

    @Override
    public List<MmsNonFixedAssetReturn> findNFAListByWfStatus(int CHECK_APPROVE_VALUE) {
        return NFARetFacade.findNFAListByWfStatus(CHECK_APPROVE_VALUE);
    }

    @Override
    public List<MmsNonFixedAssetReturn> findNFAListForCheckerByWfStatus(int PREPARE_VALUE, int APPROVE_REJECT_VALUE) {
        return NFARetFacade.findNFAListForCheckerByWfStatus(PREPARE_VALUE, APPROVE_REJECT_VALUE);
    }

    @Override
    public List<MmsNonFixedAssetReturn> findAllInfo() {
        return NFARetFacade.findAll();
    }

    @Override
    public List<MmsSivDetail> getItemCodeLists(MmsStoreInformation storeInfoEntity) {
        return NFARetFacade.getItemCodeLists(storeInfoEntity);
    }
@Override
    public List<MmsNonFixedAssetReturn> getNonreturnListsByParameter(MmsNonFixedAssetReturn nonFxdRetEntity) {
        return NFARetFacade.getNonreturnListsByParameter(nonFxdRetEntity);
    }

    @Override
    public List<ColumnNameResolver> getColumnNameList() {
        return NFARetFacade.getColumnNameList();
    }

}
