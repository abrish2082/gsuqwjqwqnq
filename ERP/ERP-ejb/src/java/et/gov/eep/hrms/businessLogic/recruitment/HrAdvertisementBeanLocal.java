/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.recruitment;

import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import et.gov.eep.hrms.entity.lookup.HrLuMediaTypes;
import et.gov.eep.hrms.entity.recruitment.HrRecruitmentRequests;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface HrAdvertisementBeanLocal {

    /**
     *
     * @param batchCode
     * @return
     */
    public List<Object[]> readApprovedRecruitments(String batchCode);

    /**
     *
     * @return
     */
    public List<String> distinctbatchCode();

    /**
     *
     * @return
     */
    public ArrayList<HrLuMediaTypes> findhrluMedias();

    /**
     *
     * @param advert
     */
    public void save(HrAdvertisements advert);

    /**
     *
     * @param advert
     */
    public void edit(HrAdvertisements advert);

    public void edit(HrRecruitmentRequests hrRecruitmentRequests);

    /**
     *
     * @param md
     * @return
     */
    public HrLuMediaTypes findByMediaName(HrLuMediaTypes md);

    public HrAdvertisements findByAdvertimnetId(Integer valueOf);

    public HrAdvertisements findByBatchCode(String bachCode);

    public List<HrAdvertisements> findAllDistinict();

    public List<HrAdvertisements> findAll();

    public void findByBatchCodeAndEdit(HrRecruitmentRequests hrRecruitmentRequests);

    public void save(HrLuMediaTypes mediaType);

    public void edit(HrLuMediaTypes mediaType);

    public List<HrLuMediaTypes> findall();

    public ArrayList<HrLuMediaTypes> searchByMediaType(HrLuMediaTypes hrLuMediaTypes);

    public HrLuMediaTypes getByMediaType(HrLuMediaTypes mediaTypes);

    public boolean isMediaTypeExist(HrLuMediaTypes mediaTypes);
}
