
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsDisposalItems;
import et.gov.eep.mms.entity.MmsDisposalItemsDtl;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.mapper.MmsDisposalItemsFacade;
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
public class MmsDisposalItemBean implements MmsDisposalItemBeanLocal {

    @EJB
    MmsDisposalItemsFacade disposalFacade;
    
    @Override
    public List<MmsDisposalItems> searchDisposalByParameterPrefix(MmsDisposalItems disposalEntity) {
        return disposalFacade.searchDisposalByParameterPrefix(disposalEntity);
    }

    @Override
    public ArrayList<MmsDisposalItems> searchByDispNo(MmsDisposalItems disposalEntity) {
        return disposalFacade.searchByDispNo(disposalEntity);
    }

    @Override
    public MmsDisposalItems getLastDisposalId() {
        return disposalFacade.getLastDisposalId();
    }

    @Override
    public void create(MmsDisposalItems disposalEntity) {
        disposalFacade.create(disposalEntity);
    }

    @Override
    public void edit(MmsDisposalItems disposalEntity) {
       disposalFacade.edit(disposalEntity);
    }

    @Override
    public List<MmsDisposalItems> findAllInfo() {
      return disposalFacade.findAll();
    }

    @Override
    public List<MmsDisposalItems> findAllDispInfo() {
        return disposalFacade.findAll();
    }

    
    
    @Override
    public List<MmsDisposalItems> getDispInfoByDispNo(MmsDisposalItems dispNo) {
        return disposalFacade.getDispInfoByDispNo1(dispNo);
    
    }

    @Override
    public MmsDisposalItems getSelectedRequest(BigDecimal disposalId) {
        return disposalFacade.getSelectedRequest(disposalId);
    }

    @Override
    public List<MmsDisposalItems> searchDisposalByParameterPrefixAndItemDispPrep(MmsDisposalItems disposalEntity) {
       return disposalFacade.searchDisposalByParameterPrefixAndItemDispPrep(disposalEntity);
    }

    @Override
    public List<MmsDisposalItems> findDispListByWfStatus(int dStatus) {
        return disposalFacade.findDispListByWfStatus(dStatus);
    }

    @Override
    public List<MmsDisposalItems> searchAllTransmissionsInfoByPreparerId(Integer requestedBy) {
       return disposalFacade.searchAllTransmissionsInfoByPreparerId(requestedBy);
    }

    @Override
    public List<MmsDisposalItemsDtl> getTagNoLists(MmsDisposalItems dispColectionEntity) {
        return disposalFacade.getTagNoLists(dispColectionEntity);
    }

    @Override
    public List<MmsDisposalItems> findAllbyApproveStatus(int Status) {
    return disposalFacade.findAllbyApproveStatus(Status);
    }

    @Override
    public MmsDisposalItemsDtl findByTag(MmsDisposalItemsDtl dispCollectionDtlEntity) {
        return disposalFacade.findByTag(dispCollectionDtlEntity);
    }

    @Override
    public List<MmsDisposalItems> getItemdisposalListsByParameter(MmsDisposalItems disposalEntity) {
        return disposalFacade.getItemdisposalListsByParameter(disposalEntity);
    }

    @Override
    public List<ColumnNameResolver> getColumnNameList() {
        return disposalFacade.getColumnNameList();
    }

}
