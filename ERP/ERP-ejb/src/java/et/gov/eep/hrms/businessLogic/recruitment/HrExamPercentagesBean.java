/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.recruitment;

import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import et.gov.eep.hrms.entity.recruitment.HrExamPercentages;
import et.gov.eep.hrms.mapper.recruitment.HrExamPercentagesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Benin
 */
@Stateless
public class HrExamPercentagesBean implements HrExamPercentagesBeanLocal {

    @Inject
    HrExamPercentages hrExamPercentages;

    @EJB
    HrExamPercentagesFacade hrExamPercentagesFacade;

    @Override
    public List<HrAdvertisements> batchCodes(String type) {
        return hrExamPercentagesFacade.batchCodes(type);
    }

    @Override
    public HrExamPercentages selectExamPrecentage(HrAdvertisements hrAdvertisements) {
        return hrExamPercentagesFacade.selectExamPrecentage(hrAdvertisements);
    }

    @Override
    public HrExamPercentages selectExamPrecentageDetail(HrExamPercentages hrExamPercentages) {
        return hrExamPercentagesFacade.selectExamPrecentageDetail(hrExamPercentages);
    }

    @Override
    public void save(HrExamPercentages hrExamPercentages) {
        hrExamPercentagesFacade.create(hrExamPercentages);
    }

    @Override
    public void update(HrExamPercentages hrExamPercentages) {
        hrExamPercentagesFacade.edit(hrExamPercentages);
    }
}
