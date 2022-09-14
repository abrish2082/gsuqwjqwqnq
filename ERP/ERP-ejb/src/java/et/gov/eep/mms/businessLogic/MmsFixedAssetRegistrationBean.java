
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.fcms.entity.fixedasset.FmsDprOfficeAsset;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.mms.entity.MmsFixedassetRegstration;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsSiv;
import et.gov.eep.mms.entity.MmsSivDetail;
import et.gov.eep.mms.entity.MmsStorereq;
import et.gov.eep.mms.mapper.MmsFixedassetRegstrationFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsFixedAssetRegistrationBean implements MmsFixedAssetRegistrationBeanLocal {

    @EJB
    private MmsFixedassetRegstrationFacade farRegistrationFacade;

    /**
     *
     * @return
     */
    @Override
    public MmsFixedassetRegstration getLastFARNo() {
        return farRegistrationFacade.getLastFARNo();
    }

    
    /**
     *
     * @param fixedAssetInformation
     */
    @Override
    public void create(MmsFixedassetRegstration fixedAssetInformation) {
       
        farRegistrationFacade.create(fixedAssetInformation);
    }

    /**
     *
     * @param fixedAssetInformation
     */
    @Override
    public void edit(MmsFixedassetRegstration fixedAssetInformation) {
        farRegistrationFacade.edit(fixedAssetInformation);
    }

    @Override
    public List<MmsFixedassetRegstration> getFixedAssetInfoByDept(MmsFixedassetRegstration fixedassetRegstration) {
        return farRegistrationFacade.getFixedAssetInfoByDept(fixedassetRegstration);
    }

    @Override
    public List<MmsFixedassetRegstration> getFixedAssetInfoByRecivedBy(MmsFixedassetRegstration fixedassetRegstration) {
        return farRegistrationFacade.getFixedAssetInfoByRecivedBy(fixedassetRegstration);
    }

    @Override
    public List<MmsFixedassetRegstration> searchFARByParameterPrefix(MmsFixedassetRegstration fixedAssetRegEntity) {
        return farRegistrationFacade.searchFARByParameterPrefix(fixedAssetRegEntity);
    }

    @Override
    public MmsFixedassetRegstration getSelectedRequest(Integer id) {
        return farRegistrationFacade.getSelectedRequest(id);
    }

    @Override
    public List<MmsFixedassetRegstration> findALL() {
        return farRegistrationFacade.findAll();
    }

    @Override
    public MmsFixedassetRegstration findByMasterId(MmsFixedassetRegstration fixedAsserRegEntity) {
        return farRegistrationFacade.findByMasterId(fixedAsserRegEntity);
    }

    @Override
    public MmsFixedassetRegstration findByNameAndTagNo(MmsFixedassetRegstration fixedassetRegstration, MmsFixedassetRegstDetail fixedAssetRegDtlEntity) {
        return farRegistrationFacade.findByNameAndTagNo(fixedassetRegstration, fixedAssetRegDtlEntity);
    }

    @Override
    public List<MmsFixedassetRegstration> findFarListByWfStatus(int PREPARE_VALUE) {
        return farRegistrationFacade.findFarListByWfStatus(PREPARE_VALUE);
    }

    @Override
    public List<MmsFixedassetRegstration> searchAllFarInfoByPreparerId(Integer approvedBy) {
        return farRegistrationFacade.searchAllFarInfoByPreparerId(approvedBy);
    }

    @Override
    public List<FmsDprOfficeAsset> findBookvalue(String tagNo) {
        return farRegistrationFacade.findBookvalue(tagNo);
    }

    @Override
    public MmsSiv getFixedSrNo(MmsSiv sivs) {
        return farRegistrationFacade.getFixedSrNo(sivs);
    }

    @Override
    public MmsItemRegistration getcode(MmsItemRegistration itemRegistrationEntity) {
        return farRegistrationFacade.getcode(itemRegistrationEntity);
    }

    @Override
    public List<MmsSiv> getSrNoLists(MmsSiv sivs) {
        return farRegistrationFacade.getSrNoLists(sivs);
    }

    @Override
    public List<MmsSivDetail> getItemBySivId(MmsSiv sivs) {
        return farRegistrationFacade.getItemBySivId(sivs);
    }

    @Override
    public List<MmsStorereq> getsrNoListsByTag(HrDepartments hrDepartmentsEntity) {
        return farRegistrationFacade.getsrNoListsByTag(hrDepartmentsEntity);
    }

    @Override
    public List<MmsFixedassetRegstration> getMmsFixedAssetRColumnNameList() {
       return farRegistrationFacade.getMmsFixedAssetRColumnNameList();
    }

    @Override
    public List<MmsFixedassetRegstration> getFAListsByParameter(MmsFixedassetRegstration fixedAssetRegEntity) {
        return farRegistrationFacade.getFAListsByParameter(fixedAssetRegEntity);
    }
@Override
    public List<MmsFixedassetRegstration> getAssetregstrationListsByParameter(MmsFixedassetRegstration fixedAssetRegEntity) {
        return farRegistrationFacade.getAssetregstrationListsByParameter(fixedAssetRegEntity);
    }

    @Override
    public List<ColumnNameResolver> getColumnNameList() {
        return farRegistrationFacade.getColumnNameList();
    }
}
