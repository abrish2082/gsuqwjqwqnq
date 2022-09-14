
package et.gov.eep.mms.businessLogic;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.mms.entity.MmsFixedassetRegstration;
import et.gov.eep.mms.mapper.MmsFixedassetRegstDetailFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsFixedAssetRegistrationDtlBean implements MmsFixedAssetRegistrationDtlBeanLocal {

    @EJB
    MmsFixedassetRegstDetailFacade fxdAssetDtlFacade;

    @Override
    public List<MmsFixedassetRegstDetail> findAllTagNo() {
        return fxdAssetDtlFacade.findAll();
    }

    @Override
    public List<MmsFixedassetRegstDetail> findAll() {
        return fxdAssetDtlFacade.findAll();
    }

    @Override
    public MmsFixedassetRegstDetail findByTag(MmsFixedassetRegstDetail fixedassetRegstDetail) {
        return fxdAssetDtlFacade.findByTag(fixedassetRegstDetail);
    }

    @Override
    public List<MmsFixedassetRegstDetail> findAllTagNoForOld() {
        return fxdAssetDtlFacade.findAllTagNoForOld();
    }

    @Override
    public MmsFixedassetRegstDetail findByDetailId(MmsFixedassetRegstDetail fixedAssetRegDtlEntity) {
        return fxdAssetDtlFacade.findByDetailId(fixedAssetRegDtlEntity);
    }

    @Override
    public void edit(MmsFixedassetRegstDetail fixedAssetRegDtlEntity) {
        fxdAssetDtlFacade.edit(fixedAssetRegDtlEntity);
    }

    @Override
    public List<MmsFixedassetRegstDetail> findNewItems() {
        return fxdAssetDtlFacade.findNewItems();
    }

    @Override
    public List<MmsFixedassetRegstDetail> findByTagNo2(MmsFixedassetRegstDetail fixedAssetRegDtlEntity) {
        return fxdAssetDtlFacade.findByTagNo2(fixedAssetRegDtlEntity);
    }

    @Override
    public List<MmsFixedassetRegstDetail> getTagNo() {
        return fxdAssetDtlFacade.getTagNo();
    }

    @Override
    public MmsFixedassetRegstDetail getTagInfoBtyId(MmsFixedassetRegstDetail mmsFixedassetRegstDetail) {
        return fxdAssetDtlFacade.getTagInfoBtyId(mmsFixedassetRegstDetail);
    }

    @Override
    public List<MmsFixedassetRegstDetail> getFixedAssetInfoByRecivedBy(HrDepartments hrDepartmentsEntity) {
        return fxdAssetDtlFacade.getFixedAssetInfoByRecivedBy(hrDepartmentsEntity);
    }

    @Override
    public MmsFixedassetRegstDetail getFixedAssetInfoByRecivedBytagno(MmsFixedassetRegstDetail selectedReturndeBy) {
        return fxdAssetDtlFacade.getFixedAssetInfoByRecivedBy(selectedReturndeBy); 
    }

    @Override
    public List<MmsFixedassetRegstDetail> getTagNoByRequester(MmsFixedassetRegstDetail fixedAssetRegDtlEntity) {
        return fxdAssetDtlFacade.getTagNoByRequester(fixedAssetRegDtlEntity);
    }

    @Override
    public List<MmsFixedassetRegstDetail> getTagNoByRequester1(List<Integer> ids) {
        return fxdAssetDtlFacade.getTagNoByRequester1(ids); 
    }

    @Override
    public List<String> findAllItemStatuses() {
        return fxdAssetDtlFacade.findAllItemStatuses();
    }

    @Override
    public int ConutBYItemType(String get) {
        return fxdAssetDtlFacade.ConutBYItemType(get);
    }

    }

